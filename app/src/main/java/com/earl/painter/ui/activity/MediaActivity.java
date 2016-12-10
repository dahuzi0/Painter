package com.earl.painter.ui.activity;

import android.os.Bundle;

import com.earl.painter.R;
import com.earl.painter.base.BaseActivity;
import com.flyco.tablayout.CommonTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 媒体模块
 */
public class MediaActivity extends BaseActivity {

    @BindView(R.id.mTabLayout)
    CommonTabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);
        mTabLayout.setTabData(mTabEntities);//给tab设置数据
        mTabLayout.setCurrentTab(2);//第三页
        listener(mTabLayout);
    }
}
