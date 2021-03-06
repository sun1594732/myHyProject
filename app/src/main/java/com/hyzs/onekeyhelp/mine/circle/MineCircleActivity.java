package com.hyzs.onekeyhelp.mine.circle;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.family.circle.activity.CirclePublishActivity;
import com.hyzs.onekeyhelp.family.circle.bean.CircleTypeBean;
import com.hyzs.onekeyhelp.family.movable.activity.MovableReleaseActivity;
import com.hyzs.onekeyhelp.home.adapter.HomeChooseCommunityAdapter;
import com.hyzs.onekeyhelp.home.bean.HomeActiveTypeBean;
import com.hyzs.onekeyhelp.lifehelp.fragment.HelpOtherFragment;
import com.hyzs.onekeyhelp.lifehelp.view.SyncHorizontalScrollView;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.mine.active.MineActiveActivity;
import com.hyzs.onekeyhelp.mine.active.MineActiveFragment;
import com.hyzs.onekeyhelp.mine.active.MinePartActiveFragment;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.JointGetUrl;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MineCircleActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_nav;
    private SyncHorizontalScrollView mHsv;
    private RadioGroup rg_nav_content;
    private ImageView iv_nav_indicator;

    private ViewPager mViewPager;
    private int indicatorWidth; // 指示器宽度

    private ImageView mBack;
    private TextView mTitle, mToolbarRight;
    private LayoutInflater mInflater;
    private TabFragmentPagerAdapter mAdapter;
    private int currentIndicatorLeft = 0;
    public static String[] tabTitle = {"我发起的", "我参与的"}; // 标题

    private String WEBUrl = PortUtil.LifeHelpList;
    private AlertDialog mChooseCommunity;
    private int mChooseCommunityPosition = 0;
    private CircleTypeBean circleTypeBean;

    @Override
    protected void assignView() {

        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarRight = (TextView) findViewById(R.id.toolbar_right);

        rl_nav = (RelativeLayout) findViewById(R.id.rl_nav);
        mHsv = (SyncHorizontalScrollView) findViewById(R.id.mHsv);
        rg_nav_content = (RadioGroup) findViewById(R.id.rg_nav_content);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        iv_nav_indicator = (ImageView) findViewById(R.id.iv_nav_indicator);

    }

    /**
     * 初始化InmageLoader
     */


    public void initView() {
        mToolbarRight.setText("发布");
        mToolbarRight.setVisibility(View.VISIBLE);
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
        mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);//设置缓存
        initDialog();
        setListener();
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mToolbarRight.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_attention;
    }

    @Override
    protected void initData() {
        mTitle.setText("我的圈子");
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
                    ft = new MineCircleFragment();
                    break;
                case 1:
                    ft = new MinePartCircleFragment();
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
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (rg_nav_content != null
                        && rg_nav_content.getChildCount() > position) {
                    (rg_nav_content.getChildAt(position))
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


    /**
     * 获取服务器信息
     */
    private void getWebInfo2Adapter() {
        HashMap<String, String> hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this));
        hms.put("pageIndex", "1");
        hms.put("pageSize", "9");
        WEBUrl = JointGetUrl.getUrl(WEBUrl, hms);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:
                mChooseCommunity.show();
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

    private void initDialog() {
        View DialogView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_community, null);
        assignDialogView(DialogView);
        mChooseCommunity = new AlertDialog.Builder(this).create();
        mChooseCommunity.show();
        mChooseCommunity.getWindow().setContentView(DialogView);
        mChooseCommunity.dismiss();
    }

    private void assignDialogView(View dialogView) {
        TextView sure = (TextView) dialogView.findViewById(R.id.dialog_choose_community_sure);
        TextView cancel = (TextView) dialogView.findViewById(R.id.dialog_choose_community_cancel);
        final ListView lv = (ListView) dialogView.findViewById(R.id.dialog_choose_community_lv);
        NetRequest.ParamPostRequest(PortUtil.CircleTypeList, null, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    circleTypeBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CircleTypeBean.class);
                    List<String> resource = new ArrayList<String>();
                    for (int i = 0; i < circleTypeBean.getCircleTypes().size(); i++) {
                        resource.add(circleTypeBean.getCircleTypes().get(i).getCircleName());
                    }
                    lv.setAdapter(new HomeChooseCommunityAdapter(resource, MineCircleActivity.this));
                } catch (Exception e) {
                }
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < lv.getAdapter().getCount(); i++) {
                    lv.getChildAt(i).setBackgroundColor(ContextCompat.getColor(MineCircleActivity.this, R.color.color_f));
                }
                view.setBackgroundColor(ContextCompat.getColor(MineCircleActivity.this, R.color.color_f4));
                mChooseCommunityPosition = position;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChooseCommunity.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineCircleActivity.this, CirclePublishActivity.class);
                intent.putExtra("type", circleTypeBean.getCircleTypes().get(mChooseCommunityPosition).getID()+"");
                startActivity(intent);
                mChooseCommunity.dismiss();
            }
        });
    }
}
