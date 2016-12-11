package com.earl.painter.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.earl.painter.R;
import com.earl.painter.entity.NewsModel;
import com.earl.painter.ui.activity.WebActivity;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.earl.painter.R.id.nineGrid;

/**
 * 作者：earl on 16/12/11 15:39
 * 邮箱：z604458675@gmail.com
 * 描述：Painter 新闻的Fragment中的RecyclerView的适配器
 */

public class NewsItemAdapter extends BaseQuickAdapter<NewsModel.ContentList> {
    public NewsItemAdapter(List<NewsModel.ContentList> data) {
        super(R.layout.item_news, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final NewsModel.ContentList contentList) {
        baseViewHolder.setText(R.id.title, contentList.title)//
                .setText(R.id.desc, contentList.desc)//
                .setText(R.id.pubDate, contentList.pubDate)//
                .setText(R.id.source, contentList.source);
        View view = baseViewHolder.getConvertView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebActivity.runActivity(mContext, contentList.title, contentList.link);
            }
        });
        NineGridView nineGridView = baseViewHolder.getView(nineGrid);
        ArrayList<ImageInfo> imageInfos = new ArrayList<>();//设置图片集合
        List<NewsModel.NewsImage> images = contentList.imageurls;//返回图片数据是个集合
        if (images != null) {
            for (NewsModel.NewsImage image : images) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(image.url);
                info.setBigImageUrl(image.url);
                imageInfos.add(info);
            }
        }

        //给控件填充图片资源
        nineGridView.setAdapter(new NineGridViewClickAdapter(mContext, imageInfos));
        //设置一张图片的宽高
        if (images != null && images.size() == 1) {
            nineGridView.setSingleImageRatio(images.get(0).width * 1.0f / images.get(0).height);
        }
    }
}
