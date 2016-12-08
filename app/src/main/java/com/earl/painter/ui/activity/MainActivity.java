package com.earl.painter.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.earl.painter.R;
import com.earl.painter.bean.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar(toolbar,false,"");

    }

    @OnClick(R.id.fab)
    public void fab(View view) {
        //        WebActivity.runActivity(this, "我的Github,欢迎star", "https://github.com/jeasonlzy");
        showToast("ceshishi");
    }
}
