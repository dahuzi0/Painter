package com.earl.painter.ui.activity;

import android.os.Bundle;

import com.earl.painter.R;
import com.earl.painter.base.BaseActivity;
import com.flyco.tablayout.CommonTabLayout;

import butterknife.BindView;

/**
 * 新闻模块
 */
public class NewsActivity extends BaseActivity {

    @BindView(R.id.mTabLayout)
    CommonTabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mTabLayout.setTabData(mTabEntities);//给tab设置数据
        mTabLayout.setCurrentTab(1);//第二页
        listener(mTabLayout);
    }
}
