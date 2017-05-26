package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.ManagerLvAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.bean.ApplyStatusBean;
import com.hyzs.onekeyhelp.module.housekeeping.bean.ManagerLvBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagerActivity extends BaseActivity implements View.OnClickListener {

    private TextView mAddApply, mApplyType, mTitle, mApplyStatus, mAddProject;
    private ImageView mBack;
    private ListViewForScrollView mLv;
    private ManagerLvAdapter adapter;
    private List<ManagerLvBean.LifeServiceSPListBean> list;
    private String id;

    @Override
    protected void assignView() {
        mAddApply = (TextView) findViewById(R.id.activity_manager_apply);
        mApplyType = (TextView) findViewById(R.id.activity_manager_applyType);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mApplyStatus = (TextView) findViewById(R.id.activity_manager_applyStatus);
        mLv = (ListViewForScrollView) findViewById(R.id.activity_manager_applySPList);
        mAddProject = (TextView) findViewById(R.id.activity_manager_add);
    }

    @Override
    protected void initView() {
        mTitle.setText("管理");
        adapter = new ManagerLvAdapter(list, this);
        mLv.setAdapter(adapter);
        switch (getIntent().getIntExtra("type", 0)) {
            case 1:
                mApplyType.setText("个人申请加入");
                break;
        }
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mAddApply.setOnClickListener(this);
        mAddProject.setOnClickListener(this);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ManagerActivity.this, ServiceProjectDetailActivity.class);
                intent.putExtra("id", list.get(position).getID() + "");
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manager;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("type", getIntent().getIntExtra("type", 0) == 0 ? "1" : "0");
        NetRequest.ParamPostRequest(PortUtil.LifeServiceApplyStatusQuery, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                ApplyStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ApplyStatusBean.class);
                if ("10000".equals(bean.getCode())) {
                    mApplyStatus.setText(bean.getLifeServiceApplyStatusQuery().getStatusDesc());
                    if (bean.getLifeServiceApplyStatusQuery().getStatus() == 1) {
                    } else {
                        mAddProject.setTextColor(ContextCompat.getColor(ManagerActivity.this, R.color.color_d));
//                        mAddProject.setClickable(false);
                    }
                    id = bean.getLifeServiceApplyStatusQuery().getLS_ID() + "";
                    Map<String, String> param = new ArrayMap<>();
                    param.put("currId", MySharedPreferences.getUserId(ManagerActivity.this));
                    param.put("LS_ID", id);
                    param.put("pageSize", "10");
                    param.put("pageIndex", "1");
                    NetRequest.ParamPostRequest(PortUtil.LifeServiceSPList, param, new NetRequest.getDataCallback() {
                        @Override
                        public void getData(String data) {
                            ManagerLvBean bean1 = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ManagerLvBean.class);
                            if ("10000".equals(bean1.getCode())) {
                                if (bean1.getLifeServiceSPList().size() != 0) {
                                    list.clear();
                                    list.addAll(bean1.getLifeServiceSPList());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_manager_apply:
                Intent intent = new Intent();
                switch (getIntent().getIntExtra("type", 0)) {
                    case 0:
                        intent.setClass(this, CompanyAddActivity.class);
                        break;
                    case 1:
                        intent.setClass(this, PersonAddActivity.class);
                        break;
                }
                startActivity(intent);
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_manager_add:
                Intent intent1 = new Intent(this, ServiceProjectAddActivity.class);
                intent1.putExtra("id", id);
                startActivity(intent1);
                break;
        }
    }
}
