package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.module.housekeeping.bean.AgencyServiceBean;
import com.hyzs.onekeyhelp.module.housekeeping.bean.MineServiceBean;
import com.hyzs.onekeyhelp.module.housekeeping.fragment.AgencyServiceFragment;
import com.hyzs.onekeyhelp.module.housekeeping.fragment.MineServiceFragment;
import com.hyzs.onekeyhelp.module.housekeeping.search.activity.SearchServiceActivity;
import com.hyzs.onekeyhelp.widget.CanBanScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */

public class HousekeepingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private ImageView mScrollLine, search;
    private TextView title, right, btnLeft, btnRight;
    private CanBanScrollViewPager viewPager;
    private List<Fragment> fragmentList;
    private TabFragmentPagerAdapter mAdapter;
    private int indicatorWidth;
    private int currentIndicatorLeft;
    private AgencyServiceFragment agencyServiceFragment;
    private MineServiceFragment mineServiceFragment;

    @Override
    protected void assignView() {
        search = (ImageView) findViewById(R.id.activity_housekeeping_layout_search);
        btnLeft = (TextView) findViewById(R.id.activity_housekeeping_layout_left);
        btnRight = (TextView) findViewById(R.id.activity_housekeeping_layout_right);
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        right = (TextView) findViewById(R.id.toolbar_right);
        mScrollLine = (ImageView) findViewById(R.id.scroll_line);
        viewPager = (CanBanScrollViewPager) findViewById(R.id.activity_housekeeping_layout_viewpager);
    }

    @Override
    protected void initView() {
        title.setText("生活服务");
        right.setText("管理");
        right.setVisibility(View.VISIBLE);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取屏幕信息
        indicatorWidth = dm.widthPixels / 2;// 指示器宽度为屏幕宽度的2/1
        ViewGroup.LayoutParams layoutParams = mScrollLine.getLayoutParams();
        layoutParams.width = indicatorWidth;
        mScrollLine.setLayoutParams(layoutParams);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected void initListener() {
        search.setOnClickListener(this);
        back.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        right.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                anim(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_housekeeping_layout;
    }

    @Override
    protected void initData() {
        fragmentList = new ArrayList<>();
        agencyServiceFragment = new AgencyServiceFragment();
        fragmentList.add(agencyServiceFragment);
        mineServiceFragment = new MineServiceFragment();
        fragmentList.add(mineServiceFragment);
        mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
    }

    private void anim(int position) {
        TranslateAnimation animation = new TranslateAnimation(currentIndicatorLeft, indicatorWidth * position,
                0f, 0f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(100);
        animation.setFillAfter(true);
        // 执行位移动画
        mScrollLine.startAnimation(animation);
        // 记录当前 下标的距最左侧的 距离
        currentIndicatorLeft = indicatorWidth * position;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:
                Intent intent = new Intent(this, ManagerActivity.class);
                intent.putExtra("type", viewPager.getCurrentItem());
                startActivity(intent);
                break;
            case R.id.activity_housekeeping_layout_left:
                viewPager.setCurrentItem(0);
                break;
            case R.id.activity_housekeeping_layout_right:
                viewPager.setCurrentItem(1);
                break;
            case R.id.activity_housekeeping_layout_search:
                intent = new Intent(this, SearchServiceActivity.class);
                intent.putExtra("index", viewPager.getCurrentItem());
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (viewPager.getCurrentItem() == 0) {
                AgencyServiceBean b = (AgencyServiceBean) data.getSerializableExtra("bean");
                agencyServiceFragment.setListData(b.getLifeServiceOrganization());
            } else {
                MineServiceBean b = (MineServiceBean) data.getSerializableExtra("bean");
                mineServiceFragment.setListData(b.getLifeServicePersonal());
            }
        }
    }

    public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

        public TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg) {
            Fragment ft = null;
            ft = fragmentList.get(arg);
            return ft;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
