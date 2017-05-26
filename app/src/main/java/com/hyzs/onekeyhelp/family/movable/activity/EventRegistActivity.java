package com.hyzs.onekeyhelp.family.movable.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.movable.fragment.MovableDetailsFragment;
import com.hyzs.onekeyhelp.family.movable.fragment.MovableDynamicFragment;
import com.hyzs.onekeyhelp.family.movable.fragment.MovableMemberFragment;
import com.hyzs.onekeyhelp.home.adapter.FmPagerAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class EventRegistActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle;
    private TextView mRight;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> list;
    private FmPagerAdapter mAdapter;
    private String[] mTitles = {
            "活动详情", "活动成员", "活动动态"
    };
    public static boolean releaseFlag=false;

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTabLayout = (SlidingTabLayout) findViewById(R.id.activity_circle_tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.activity_circle_viewPager);
        mRight = (TextView) findViewById(R.id.toolbar_right);
    }

    @Override
    protected void initView() {
        mTitle.setText(getIntent().getStringExtra("title"));
        mRight.setText("发布");
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mRight.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==2)
                    mRight.setVisibility(View.VISIBLE);
                else mRight.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event_regist;
    }

    @Override
    protected void initData() {
        fillFragment();
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new FmPagerAdapter(getSupportFragmentManager(),list,mTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setViewPager(mViewPager);
    }

    private void fillFragment() {
        list = new ArrayList<>();
        list.add(new MovableDetailsFragment());
        list.add(new MovableMemberFragment());
        list.add(new MovableDynamicFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:
                if (-1 == getIntent().getIntExtra("movableId", -1)) {
                    return;
                }
                Intent intent = new Intent(this, MovableRelease.class);
                intent.putExtra("movableId",getIntent().getIntExtra("movableId", -1));
                startActivity(intent);
                releaseFlag = true;
                break;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
