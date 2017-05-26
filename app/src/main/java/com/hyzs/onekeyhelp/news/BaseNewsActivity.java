package com.hyzs.onekeyhelp.news;


import android.app.Dialog;
import android.support.v4.app.Fragment;

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
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.umeng.analytics.MobclickAgent;


import java.util.ArrayList;
import java.util.List;

public class BaseNewsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle;
    private TextView mToolBarRight;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> list;
    private FmPagerAdapter mAdapter;
    private NewsTypeBean typeBean;
    private String[] mTitles ;

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
        mTitle.setText("新闻资讯");

    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle;
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
        for (int i = 0; i < typeBean.getData().size(); i++) {
            NewsInterestFragment fragment = new NewsInterestFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", typeBean.getData().get(i).getId());
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


    public void requestData(){
        NetRequest.ParamPostRequest(PortUtil.HomeNewType, null, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    typeBean = new Gson().fromJson(data, NewsTypeBean.class);
                    mTitles = new String[typeBean.getData().size()];
                    for (int i = 0; i < typeBean.getData().size(); i++) {
                        mTitles[i] = typeBean.getData().get(i).getName();
                    }
                    mViewPager.setOffscreenPageLimit(mTitles.length);//设置缓存所有

                    fillFragment();
                    initAdapter();
                }catch (Exception e){
                    Toast.makeText(BaseNewsActivity.this, "获取数据失败，请重试！", Toast.LENGTH_SHORT).show();
                    return;
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