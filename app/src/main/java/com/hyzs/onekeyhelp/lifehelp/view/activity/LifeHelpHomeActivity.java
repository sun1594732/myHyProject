package com.hyzs.onekeyhelp.lifehelp.view.activity;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.lifehelp.bean.LifeHelpOtherBean;
import com.hyzs.onekeyhelp.lifehelp.fragment.HelpMeFragment;
import com.hyzs.onekeyhelp.lifehelp.fragment.HelpOtherFragment;
import com.hyzs.onekeyhelp.lifehelp.view.SyncHorizontalScrollView;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.JointGetUrl;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;


public class LifeHelpHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rl_nav;
    private SyncHorizontalScrollView mHsv;
    private RadioGroup rg_nav_content;
    private ImageView iv_nav_indicator;
    private LifeHelpOtherBean lifeHelpOtherBean;
    private ViewPager mViewPager;
    private int indicatorWidth; // 指示器宽度

    private LayoutInflater mInflater;
    private TabFragmentPagerAdapter mAdapter;
    private int currentIndicatorLeft = 0;
    public static String[] tabTitle = {"我的帮助", "我的求助"}; // 标题

    private ImageView mBack;
    private TextView mTitle, mToolbarRight;
    HelpOtherFragment otherFragment;
    private OkHttpClient okHttpClient;
    private Request request;
    private String WEBUrl = PortUtil.LifeHelpList;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_help);
        mDialog = com.hyzs.onekeyhelp.util.ProgressDialog.createProgressLoading(this);
        okHttpClient = new OkHttpClient();
        findViewById();
//        initView();
        mDialog.show();
        if (MySharedPreferences.isLogin(this)) {
            getWebInfo2Adapter();
            initView();
            setListener();
        } else {
            Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
            mDialog.dismiss();
        }
    }

    /**
     * 初始化InmageLoader
     */

    private void findViewById() {
        rl_nav = (RelativeLayout) findViewById(R.id.rl_nav);
        mHsv = (SyncHorizontalScrollView) findViewById(R.id.mHsv);
        rg_nav_content = (RadioGroup) findViewById(R.id.rg_nav_content);
        iv_nav_indicator = (ImageView) findViewById(R.id.iv_nav_indicator);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarRight = (TextView) findViewById(R.id.toolbar_right);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mBack.setOnClickListener(this);
        mToolbarRight.setOnClickListener(this);
        mToolbarRight.setVisibility(View.VISIBLE);
        mTitle.setText("生活求助");
        mToolbarRight.setText("发布");
    }

    private void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取屏幕信息
        indicatorWidth = dm.widthPixels / 2;// 指示器宽度为屏幕宽度的2/1
        ViewGroup.LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
        cursor_Params.width = indicatorWidth;// 初始化滑动下标的宽
        iv_nav_indicator.setLayoutParams(cursor_Params);
        mHsv.setSomeParam(rl_nav, this,
                dm.widthPixels);
        // 获取布局填充器
        mInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        initNavigationHSV();
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
                    ft = otherFragment;
                    break;
                case 1:
                    ft = new HelpMeFragment();
                    break;

            }
            return ft;
        }

        @Override
        public int getCount() {
            return tabTitle.length;
        }
    }


    private void setListener() {
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
                                    ( rg_nav_content
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


    /**
     * 获取服务器信息
     */
    private void getWebInfo2Adapter() {
        HashMap<String, String> hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this));
        hms.put("pageIndex", "1");
        hms.put("pageSize", "9");
        WEBUrl = JointGetUrl.getUrl(WEBUrl, hms);
        request = new Request.Builder().url(WEBUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                request = null;
                try {
                    String str = response.body().string();
                    str = URLDecoder.decode(str, "utf-8");
                    lifeHelpOtherBean = new Gson().fromJson(str, LifeHelpOtherBean.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bean", lifeHelpOtherBean);
                    otherFragment = new HelpOtherFragment();
                    otherFragment.setArguments(bundle);
                    handler.sendEmptyMessage(1);//成功
                } catch (Exception e) {
                    handler.sendEmptyMessage(2);//成功
                    e.printStackTrace();
                }
            }
        });
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
                    mViewPager.setAdapter(mAdapter);
                    mViewPager.setOffscreenPageLimit(2);//设置缓存

                    mDialog.dismiss();
                    break;
                case 2:
                    Toast.makeText(LifeHelpHomeActivity.this, "获取数据异常", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:
                startActivity(new Intent(LifeHelpHomeActivity.this, LifeHelpPublishActivity.class));
                break;
        }
    }

    private void initNavigationHSV() {

        rg_nav_content.removeAllViews();

        for (int i = 0; i < tabTitle.length; i++) {

            RadioButton rb = (RadioButton) getLayoutInflater().inflate(
                    R.layout.nav_radiogroup_item, null);
            rb.setId(i);
            rb.setText(tabTitle[i]);
            rb.setLayoutParams(new ViewGroup.LayoutParams(indicatorWidth,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            rg_nav_content.addView(rb);
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
