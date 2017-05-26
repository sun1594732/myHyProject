package com.hyzs.onekeyhelp.home.forum.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.contact.activity.LoginActivity;
import com.hyzs.onekeyhelp.home.adapter.FmPagerAdapter;
import com.hyzs.onekeyhelp.home.forum.bean.ForumTypeBean;
import com.hyzs.onekeyhelp.home.forum.fragment.ForumFragment;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle;
    private TextView mToolBarRight;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> list;
    private List<ForumTypeBean.ForumTypesBean> types;
    private FmPagerAdapter mAdapter;
    private Dialog mCheckedDialog;
    private List<String> ids;

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolBarRight = (TextView) findViewById(R.id.toolbar_right);
        mTabLayout = (SlidingTabLayout) findViewById(R.id.activity_forum_tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.activity_forum_viewPager);
    }

    @Override
    protected void initView() {
        mTitle.setText("论坛");
        mToolBarRight.setText("发布");
        mToolBarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mToolBarRight.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forum;
    }

    @Override
    protected void initData() {
        ids = new ArrayList<>();
        fillFragment();
        initDialog();
    }

    private void fillFragment() {
        list = new ArrayList<>();
        types = new ArrayList<>();
        NetRequest.ParamDialogPostRequest(PortUtil.ForumType, null, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    ForumTypeBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ForumTypeBean.class);
                    types = bean.getForumTypes();
                    for (int i = 0; i < types.size(); i++) {
                        ids.add(types.get(i).getID() + "");
                        ForumTypeBean.ForumTypesBean bean1 = types.get(i);
                        ForumFragment fragment = new ForumFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", bean1.getID());
                        fragment.setArguments(bundle);
                        list.add(fragment);
                    }
                    mAdapter = new FmPagerAdapter(getSupportFragmentManager(), list, getmTitles());
                    mViewPager.setAdapter(mAdapter);
                    mTabLayout.setViewPager(mViewPager);
                    mViewPager.setCurrentItem(getIntent().getIntExtra("type", 0));
                    mTabLayout.setCurrentTab(getIntent().getIntExtra("type", 0));
                } catch (Exception e) {
                    ToastUtils.showShort(ForumActivity.this, "网络异常");
                }
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:
                if (!MySharedPreferences.isLogin(ForumActivity.this)) {
                    mCheckedDialog.show();
                    return;
                }
                Intent intent = new Intent(ForumActivity.this, ForumPublishActivity.class);
                intent.putExtra("type", ids.get(mTabLayout.getCurrentTab()));
                startActivity(intent);
                break;
        }
    }

    private void initDialog() {
        View DialogView = LayoutInflater.from(ForumActivity.this).inflate(R.layout.layout_dialog_help_publish_photo, null);
        assignDialogView(DialogView);
        mCheckedDialog = new AlertDialog.Builder(ForumActivity.this).create();
        mCheckedDialog.show();
        mCheckedDialog.getWindow().setContentView(DialogView);
        mCheckedDialog.dismiss();
    }

    private void assignDialogView(View dialogView) {
        TextView sure = (TextView) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_sure);
        TextView cancel = (TextView) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_cancel);
        TextView text = (TextView) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_text);
        text.setText("想了解更多论坛内容，请先登录！");
        sure.setText("登录");
        cancel.setText("取消");
        cancel.setTextColor(ContextCompat.getColor(ForumActivity.this, R.color.color_9));
        sure.setTextColor(ContextCompat.getColor(ForumActivity.this, R.color.color_1ccd9b));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckedDialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckedDialog.dismiss();
                startActivity(new Intent(ForumActivity.this, LoginActivity.class));
            }
        });
    }

    private String[] getmTitles() {
        String[] title = new String[types.size()];
        for (int i = 0; i < types.size(); i++) {
            title[i] = types.get(i).getForumName();
        }
        return title;
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ForumActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ForumActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
}
