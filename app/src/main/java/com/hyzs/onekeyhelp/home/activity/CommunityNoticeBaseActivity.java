package com.hyzs.onekeyhelp.home.activity;

import android.app.Dialog;
import android.support.v4.app.Fragment;

import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;


import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;

import com.hyzs.onekeyhelp.home.adapter.FmPagerAdapter;
import com.hyzs.onekeyhelp.home.bean.CommunityNoticeTypeBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommunityNoticeBaseActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle;
    private TextView mToolBarRight;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> list;
    private FmPagerAdapter mAdapter;
    private CommunityNoticeTypeBean typeBean;
    private String[] mTitles;
    private Dialog dialog;

    private int[] ids;

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolBarRight = (TextView) findViewById(R.id.toolbar_right);
        mTabLayout = (SlidingTabLayout) findViewById(R.id.activity_circle_tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.activity_circle_viewPager);
    }

    @Override
    protected void initView() {
        mTitle.setText("社区公告");

    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_notice_base;
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void initAdapter() {
        mAdapter = new FmPagerAdapter(getSupportFragmentManager(), list, mTitles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setViewPager(mViewPager);
    }

    private void fillFragment() {
        list = new ArrayList<>();
        for (int i = 0; i < typeBean.getCommunityNotice2Classify().size(); i++) {
            CommunityNoticeFragment fragment = new CommunityNoticeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", ids[i]);
            fragment.setArguments(bundle);
            list.add(fragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    public void requestData() {
        dialog = ProgressDialog.createProgressLoading(this);
        dialog.show();
        Map<String, String> param = new ArrayMap<>();
        param.put("pageIndex", "1");
        param.put("pageSize", "10");
        NetRequest.ParamPostRequest(PortUtil.CommunityNoticeType, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    typeBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CommunityNoticeTypeBean.class);
                    mTitles = new String[typeBean.getCommunityNotice2Classify().size()];
                    ids = new int[typeBean.getCommunityNotice2Classify().size()];
                    for (int i = 0; i < typeBean.getCommunityNotice2Classify().size(); i++) {
                        mTitles[i] = typeBean.getCommunityNotice2Classify().get(i).getCNC_Name();
                        ids[i] = typeBean.getCommunityNotice2Classify().get(i).getId();
                    }
                    mViewPager.setOffscreenPageLimit(mTitles.length);//设置缓存所有
                    fillFragment();
                    initAdapter();
                } catch (Exception e) {
                    Toast.makeText(CommunityNoticeBaseActivity.this, "获取数据失败，请重试！", Toast.LENGTH_SHORT).show();
                }finally {
                    dialog.dismiss();
                }
            }
        });

    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            mViewPager.setCurrentItem(Integer.parseInt(getIntent().getStringExtra("type")));
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}