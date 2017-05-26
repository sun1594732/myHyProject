package com.hyzs.onekeyhelp.contact.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.activity.LoginActivity;
import com.hyzs.onekeyhelp.contact.bean.ContactListEntity;
import com.hyzs.onekeyhelp.mine.activity.MineUrgentActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.ConfirmDialog;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;


public class ContactFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    ContactGroupFragment contactGroupFragment;
    ContactListFragment contactListFragment;
    ContactRecentFragment contactRecentFragment;
    RadioGroup rg;
    Dialog mCheckedDialog;
    private RelativeLayout mEmptyView;
    private ImageView mEmptyBg, mEmptyBtn;
    private Dialog confirmDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, null);
        initDialog();
        assignView(view);
        initFragments();
        initListener();
        return view;
    }

    private void initListener() {
        rg.setOnCheckedChangeListener(this);
        mEmptyBtn.setOnClickListener(this);
    }

    private void assignView(View view) {
        rg = (RadioGroup) view.findViewById(R.id.fragment_contact_rg);
        mEmptyView = (RelativeLayout) view.findViewById(R.id.layout_empty);
        mEmptyBg = (ImageView) view.findViewById(R.id.layout_empty_bg);
        mEmptyBtn = (ImageView) view.findViewById(R.id.layout_empty_login);
        mEmptyBtn.setVisibility(View.VISIBLE);
        mEmptyBg.setVisibility(View.VISIBLE);
    }

    private void initFragments() {
        contactGroupFragment = new ContactGroupFragment();
        contactListFragment = new ContactListFragment();
        contactRecentFragment = new ContactRecentFragment();
        FragmentTransaction transaction = this.getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_contact_container, contactRecentFragment);
        transaction.add(R.id.fragment_contact_container, contactGroupFragment);
        transaction.add(R.id.fragment_contact_container, contactListFragment);
        transaction.hide(contactRecentFragment);
        transaction.hide(contactGroupFragment);
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments() {
        FragmentTransaction transaction = this.getChildFragmentManager().beginTransaction();
        if (contactGroupFragment != null) {
            transaction.hide(contactGroupFragment);
        }
        if (contactListFragment != null) {
            transaction.hide(contactListFragment);
        }
        if (contactRecentFragment != null) {
            transaction.hide(contactRecentFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        hideFragments();
        FragmentTransaction transaction = this.getChildFragmentManager().beginTransaction();
        switch (checkedId) {
            case R.id.fragment_contact_recent:
                transaction.show(contactRecentFragment);
                break;
            case R.id.fragment_contact_people_list:
                transaction.show(contactListFragment);
                break;
            case R.id.fragment_contact_group:
                transaction.show(contactGroupFragment);
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    private void initDialog() {
        View DialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog_help_publish_photo, null);
        assignDialogView(DialogView);
        mCheckedDialog = new AlertDialog.Builder(getActivity()).create();
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
        cancel.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_9));
        sure.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_1ccd9b));
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
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            int totalCount = MySharedPreferences.getInstance(getActivity()).getInt("notificationCount", 0);
            if (totalCount > 0) {
                rg.clearCheck();
                rg.check(R.id.fragment_contact_recent);
            }
            Map<String, String> m = new ArrayMap<>();
            m.put("currId", MySharedPreferences.getUserId(getActivity()));
            m.put("searchKeyWords", "");
            m.put("pageIndex", "1");
            m.put("pageSize", "999");
            NetRequest.ParamDialogPostRequest(PortUtil.UserContactList, m, new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    try {
                        boolean hasUrgent = false;
                        ContactListEntity bean = new Gson().fromJson(data, ContactListEntity.class);
                        for (int i = 0; i < bean.getContactList().size(); i++) {
                            if ("是".equals(bean.getContactList().get(i).getUrgentFlag().trim())) {
                                hasUrgent = true;
                            }
                        }
                        if (!hasUrgent) {
                            confirmDialog = ConfirmDialog.createConfirmLoading(getActivity(), "立即设置紧急联系人?", "取消", "确认", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    confirmDialog.dismiss();
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getActivity(), MineUrgentActivity.class));
                                    confirmDialog.dismiss();
                                }
                            });
                            confirmDialog.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                    }
                }
            }, new NetRequest.getDataFailCallback1() {
                @Override
                public void getData(Exception error) {

                }
            });
        }

    }

    public void onResume() {
        super.onResume();
        if (MySharedPreferences.isLogin(getActivity())) {
            mEmptyView.setVisibility(View.GONE);
        } else mEmptyView.setVisibility(View.VISIBLE);
        MobclickAgent.onPageStart("Contact" + "Fragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Contact" + "Fragment");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_empty_login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }
}
