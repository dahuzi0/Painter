package com.earl.painter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.earl.painter.ui.fragment.NewsTabFragment;

import java.util.List;


/**
 * 作者：earl on 16/12/11 14:03
 * 邮箱：z604458675@gmail.com
 * 描述：Painter viewPage的适配器
 */

public class NewsPagerAdapter extends FragmentPagerAdapter {
    private List<NewsTabFragment> fragments;

    public NewsPagerAdapter(FragmentManager fm, List<NewsTabFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
