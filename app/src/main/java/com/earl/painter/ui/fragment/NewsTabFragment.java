package com.earl.painter.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.earl.painter.R;
import com.earl.painter.adapter.NewsItemAdapter;
import com.earl.painter.api.Urls;
import com.earl.painter.base.BaseFragment;
import com.earl.painter.entity.NewsModel;
import com.earl.painter.presenter.news.NewsCallback;
import com.earl.painter.presenter.news.NewsResponse;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：earl on 16/12/11 14:25
 * 邮箱：z604458675@gmail.com
 * 描述：Painter 新闻Fragment
 */

public class NewsTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private NewsItemAdapter newsItemAdapter;
    private Context context;
    private int currentPage;
    private boolean isInitCache = false;

    public static NewsTabFragment newInstance() {
        return new NewsTabFragment();
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        //第一个是资源文件xml，第二个是父ViewGroup,第三个参数是否返回父Group
        View view = inflater.inflate(R.layout.fragment_news_tab, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        newsItemAdapter = new NewsItemAdapter(null);
        newsItemAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        newsItemAdapter.isFirstOnly(false);
        recyclerView.setAdapter(newsItemAdapter);

        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        refreshLayout.setOnRefreshListener(this);
        newsItemAdapter.setOnLoadMoreListener(this);

        //开启loading,获取数据
        setRefreshing(true);
        onRefresh();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        OkGo.get(Urls.NEWS)//
                .params("channelName", fragmentTitle)//
                .params("page", 1)                              //初始化或者下拉刷新,默认加载第一页
                .cacheKey("TabFragment_" + fragmentTitle)       //由于该fragment会被复用,必须保证key唯一,否则数据会发生覆盖
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)  //缓存模式先使用缓存,然后使用网络数据
                .execute(new NewsCallback<NewsResponse<NewsModel>>() {
                    @Override
                    public void onSuccess(NewsResponse<NewsModel> newsResponse, Call call, Response response) {
                        NewsModel newsModel = newsResponse.showapi_res_body;
                        currentPage = newsModel.pagebean.currentPage;
                        newsItemAdapter.setNewData(newsModel.pagebean.contentlist);
                    }

                    @Override
                    public void onCacheSuccess(NewsResponse<NewsModel> newsResponse, Call call) {
                        //一般来说,只需呀第一次初始化界面的时候需要使用缓存刷新界面,以后不需要,所以用一个变量标识
                        if (!isInitCache) {
                            //一般来说,缓存回调成功和网络回调成功做的事情是一样的,所以这里直接回调onSuccess
                            onSuccess(newsResponse, call, null);
                            isInitCache = true;
                        }
                    }

                    @Override
                    public void onCacheError(Call call, Exception e) {
                        //获取缓存失败的回调方法,一般很少用到,需要就复写,不需要不用关心
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        //网络请求失败的回调,一般会弹个Toast
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onAfter(@Nullable NewsResponse<NewsModel> newsResponse, @Nullable Exception e) {
                        super.onAfter(newsResponse, e);
                        //可能需要移除之前添加的布局
                        newsItemAdapter.removeAllFooterView();
                        //最后调用结束刷新的方法
                        setRefreshing(false);
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        OkGo.get(Urls.NEWS)//
                .params("channelName", fragmentTitle)//
                .params("page", currentPage + 1)     //上拉加载更多
                .cacheMode(CacheMode.NO_CACHE)       //上拉不需要缓存
                .execute(new NewsCallback<NewsResponse<NewsModel>>() {
                    @Override
                    public void onSuccess(NewsResponse<NewsModel> newsResponse, Call call, Response response) {
                        NewsModel newsModel = newsResponse.showapi_res_body;
                        currentPage = newsModel.pagebean.currentPage;
                        newsItemAdapter.addData(newsModel.pagebean.contentlist);
                        //显示没有更多数据
                        if (newsModel.pagebean.allPages == currentPage) {
                            newsItemAdapter.loadComplete();         //加载完成
                            View noDataView = inflater.inflate(R.layout.item_no_data, (ViewGroup) recyclerView.getParent(), false);
                            newsItemAdapter.addFooterView(noDataView);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        //显示数据加载失败,点击重试
                        newsItemAdapter.showLoadMoreFailedView();
                        //网络请求失败的回调,一般会弹个Toast
                        showToast(e.getMessage());
                    }
                });
    }


    public void showToast(String msg) {
        Snackbar.make(recyclerView, msg, Snackbar.LENGTH_SHORT).show();
    }

    public void setRefreshing(final boolean refreshing) {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(refreshing);
            }
        });
    }
}
