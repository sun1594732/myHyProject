package com.hyzs.onekeyhelp.help.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.help.fragment.BangZhuFragment;
import com.hyzs.onekeyhelp.help.fragment.QiuZhuFragment;
import com.hyzs.onekeyhelp.widget.SyncHorizontalScrollView;

/**
 * Created by Administrator on 2017/3/29.
 */

public class HelpListActivity extends BaseActivity implements View.OnClickListener{
    private ImageView back;
    private TextView title;
    private RelativeLayout rl_nav;
    private SyncHorizontalScrollView mHsv;
    private RadioGroup rg_nav_content;
    private ImageView iv_nav_indicator;
    private TextView confirm;

    private ViewPager mViewPager;
    private int indicatorWidth; // 指示器宽度

    private LayoutInflater mInflater;
    private TabFragmentPagerAdapter mAdapter;
    private int currentIndicatorLeft = 0;
    public static String[] tabTitle = {"我的帮助", "我的求助"} ;// 标题

    public ProgressDialog PDialog;
    @Override
    protected void assignView() {
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        rl_nav = (RelativeLayout) findViewById(R.id.help_rl_nav);
        mHsv = (SyncHorizontalScrollView) findViewById(R.id.help_mHsv);
        rg_nav_content = (RadioGroup) findViewById(R.id.help_rg_nav_content);
        iv_nav_indicator = (ImageView) findViewById(R.id.help_iv_nav_indicator);
        confirm = (TextView) findViewById(R.id.help_list_confirm);
        mViewPager = (ViewPager) findViewById(R.id.help_viewpager);
    }

    @Override
    protected void initView() {
        title.setText("一键帮助");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取屏幕信息
        indicatorWidth = dm.widthPixels / 2;// 指示器宽度为屏幕宽度的2/1

        ViewGroup.LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
        cursor_Params.width = indicatorWidth;// 初始化滑动下标的宽
        iv_nav_indicator.setLayoutParams(cursor_Params);

        mHsv.setSomeParam(rl_nav, this,
                dm.widthPixels);
        // 获取布局填充器
        mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        initNavigationHSV();
        mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.help_list_confirm:
                startActivity(new Intent(this,HelpActivity.class));
                finish();
                break;
        }
    }

    public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

        public TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg) {
            Fragment ft = null;
            switch (arg) {
                case 0:
                    ft = new BangZhuFragment();
                    break;
                case 1:
                    ft = new QiuZhuFragment();
                    break;

            }
            return ft;
        }
        @Override
        public int getCount() {
            return tabTitle.length;
        }
    }

        private void initNavigationHSV() {

        rg_nav_content.removeAllViews();

        for (int i = 0; i < tabTitle.length; i++) {


            RadioButton rb = (RadioButton) getLayoutInflater().inflate(R.layout.nav_radiogroup_car_manarge, null);
            rb.setId(i);
            rb.setText(tabTitle[i]);
            rb.setLayoutParams(new ViewGroup.LayoutParams(indicatorWidth,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            rb.setBackgroundColor(getResources().getColor(R.color.color_f));
            rg_nav_content.addView(rb);
        }
    }

    @Override
    protected void initListener() {
        confirm.setOnClickListener(this);
        back.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                if (rg_nav_content != null
                        && rg_nav_content.getChildCount() > position) {
                    ((RadioButton) rg_nav_content.getChildAt(position))
                            .performClick();
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        rg_nav_content
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        if (rg_nav_content.getChildAt(checkedId) != null) {

                            TranslateAnimation animation = new TranslateAnimation(
                                    currentIndicatorLeft,
                                    ((RadioButton) rg_nav_content
                                            .getChildAt(checkedId)).getLeft(),
                                    0f, 0f);
                            animation.setInterpolator(new LinearInterpolator());
                            animation.setDuration(100);
                            animation.setFillAfter(true);

                            // 执行位移动画
                            iv_nav_indicator.startAnimation(animation);

                            mViewPager.setCurrentItem(checkedId); // ViewPager
                            // 跟随一起 切换

                            // 记录当前 下标的距最左侧的 距离
                            currentIndicatorLeft = ((RadioButton) rg_nav_content
                                    .getChildAt(checkedId)).getLeft();

                            mHsv.smoothScrollTo(
                                    (checkedId > 1 ? ((RadioButton) rg_nav_content
                                            .getChildAt(checkedId)).getLeft()
                                            : 0)
                                            - ((RadioButton) rg_nav_content
                                            .getChildAt(1)).getLeft(),
                                    0);
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_help;
    }

    @Override
    protected void initData() {

    }
}
