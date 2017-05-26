package com.hyzs.onekeyhelp.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.ToastUtils;

import java.util.List;


public class FmPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private String[] mTitles;

    public FmPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public FmPagerAdapter(FragmentManager fm, List<Fragment> list, String[] mTitles) {
        super(fm);
        this.list = list;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {

        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
