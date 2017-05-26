package com.hyzs.onekeyhelp.family.circle.activity;

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
import com.hyzs.onekeyhelp.family.circle.bean.CircleTypeBean;
import com.hyzs.onekeyhelp.family.circle.fragment.CircleInterestFragment;
import com.hyzs.onekeyhelp.contact.activity.LoginActivity;
import com.hyzs.onekeyhelp.home.adapter.FmPagerAdapter;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class CircleActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle;
    private TextView mToolBarRight;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> list;
    private FmPagerAdapter mAdapter;
    private Dialog mCheckedDialog, mProgressDialog;
    private List<String> ids;

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
//        mTitle.setText("圈子");
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
        return R.layout.activity_circle;
    }

    @Override
    protected void initData() {
        ids = new ArrayList<>();
        mProgressDialog = ProgressDialog.createProgressLoading(this);
        mProgressDialog.show();
        fillFragment();
        initDialog();
    }

    private void fillFragment() {
        list = new ArrayList<>();
        if (!MySharedPreferences.isLogin(CircleActivity.this)) {
            ToastUtils.showShort(CircleActivity.this, "您还未登录");
            return;
        }
        NetRequest.ParamPostRequest(PortUtil.CircleTypeList, null, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    CircleTypeBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CircleTypeBean.class);
                    String title[] = new String[bean.getCircleTypes().size()];
                    for (int i = 0; i < bean.getCircleTypes().size(); i++) {
                        ids.add(bean.getCircleTypes().get(i).getID() + "");
                        CircleInterestFragment fragment = new CircleInterestFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", bean.getCircleTypes().get(i).getID());
                        title[i] = bean.getCircleTypes().get(i).getCircleName();
                        fragment.setArguments(bundle);
                        list.add(fragment);
                    }
                    mAdapter = new FmPagerAdapter(getSupportFragmentManager(), list, title);
                    mViewPager.setAdapter(mAdapter);
                    mTabLayout.setViewPager(mViewPager);
                    mViewPager.setCurrentItem(getIntent().getIntExtra("type", 0));
                    mTabLayout.setCurrentTab(getIntent().getIntExtra("type", 0));
                    mTitle.setText(title[getIntent().getIntExtra("type", 0)]);
                } catch (Exception e) {
                    ToastUtils.showShort(CircleActivity.this, "网络异常");
                } finally {
                    mProgressDialog.dismiss();
                }
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
                if (!MySharedPreferences.isLogin(CircleActivity.this)) {
                    mCheckedDialog.show();
                    return;
                }
                Intent intent = new Intent(CircleActivity.this, CirclePublishActivity.class);
                intent.putExtra("type", ids.get(mTabLayout.getCurrentTab()));
                startActivity(intent);
                break;
        }
    }

    private void initDialog() {
        View DialogView = LayoutInflater.from(CircleActivity.this).inflate(R.layout.layout_dialog_help_publish_photo, null);
        assignDialogView(DialogView);
        mCheckedDialog = new AlertDialog.Builder(CircleActivity.this).create();
        mCheckedDialog.setCanceledOnTouchOutside(false);
        mCheckedDialog.show();
        mCheckedDialog.getWindow().setContentView(DialogView);
        mCheckedDialog.dismiss();
    }

    private void assignDialogView(View dialogView) {
        TextView sure = (TextView) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_sure);
        TextView cancel = (TextView) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_cancel);
        TextView text = (TextView) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_text);
        text.setText("想了解更多圈子内容，请先登录！");
        sure.setText("登录");
        cancel.setText("取消");
        cancel.setTextColor(ContextCompat.getColor(CircleActivity.this, R.color.color_9));
        sure.setTextColor(ContextCompat.getColor(CircleActivity.this, R.color.color_1ccd9b));
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
                startActivity(new Intent(CircleActivity.this, LoginActivity.class));
            }
        });
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
