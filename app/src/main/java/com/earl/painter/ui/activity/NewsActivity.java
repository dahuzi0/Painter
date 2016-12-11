package com.earl.painter.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.earl.painter.R;
import com.earl.painter.adapter.NewsPagerAdapter;
import com.earl.painter.base.BaseActivity;
import com.earl.painter.ui.fragment.NewsTabFragment;
import com.earl.painter.utils.GlideImageLoader;
import com.flyco.tablayout.CommonTabLayout;
import com.lzy.ninegrid.NineGridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新闻模块
 */
public class NewsActivity extends BaseActivity {

    @BindView(R.id.mTabLayout)
    CommonTabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mTabLayout.setTabData(mTabEntities);//给tab设置数据
        mTabLayout.setCurrentTab(1);//第二页
        listener(mTabLayout);
        initToolBar(toolbar, false, "新闻");
        NineGridView.setImageLoader(new GlideImageLoader());//初始化图片加载工具

        ArrayList<NewsTabFragment> fragments = new ArrayList<>();
        NewsTabFragment fragment1 = NewsTabFragment.newInstance();
        fragment1.setTitle("国内最新");
        fragments.add(fragment1);
        NewsTabFragment fragment2 = NewsTabFragment.newInstance();
        fragment2.setTitle("游戏焦点");
        fragments.add(fragment2);
        NewsTabFragment fragment3 = NewsTabFragment.newInstance();
        fragment3.setTitle("娱乐焦点");
        fragments.add(fragment3);
        NewsPagerAdapter adapter = new NewsPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.size());
        tab.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.fab)
    public void fab(View view){
        WebActivity.runActivity(this, "我的Github,欢迎star", "https://github.com/EARL8888/Painter");
    }
}
