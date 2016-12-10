package com.earl.painter.ui.activity;

import android.os.Bundle;

import com.earl.painter.R;
import com.earl.painter.base.BaseActivity;
import com.flyco.tablayout.CommonTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 更多模块
 */
public class MoreActivity extends BaseActivity {

    @BindView(R.id.mTabLayout)
    CommonTabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        mTabLayout.setTabData(mTabEntities);//给tab设置数据
        mTabLayout.setCurrentTab(3);//第四页
        listener(mTabLayout);
    }
}
