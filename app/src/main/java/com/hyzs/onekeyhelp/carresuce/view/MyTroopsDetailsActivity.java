package com.hyzs.onekeyhelp.carresuce.view;


import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.carresuce.bean.TroopsDetailsBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

public class MyTroopsDetailsActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MyTroopsDetailsActivity";
    private RoundImageView icon;
    private ImageView mBack;
    private TextView name, desc, phone, address, scope, region, callPhone, mTitle;
    private TroopsDetailsBean bean;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_mine_album_call_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + bean.getVehicleRescueTeamDetails().getVRT_Phone());
                intent.setData(data);
                startActivity(intent);
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    @Override
    protected void assignView() {
        icon = (RoundImageView) findViewById(R.id.activity_mine_album_icon);
        name = (TextView) findViewById(R.id.activity_mine_album_name);
        desc = (TextView) findViewById(R.id.activity_mine_home_tv_desc);
        phone = (TextView) findViewById(R.id.activity_mine_home_tv_phone_number);
        address = (TextView) findViewById(R.id.activity_mine_home_tv_address);
        scope = (TextView) findViewById(R.id.activity_mine_home_ll_scope);
        region = (TextView) findViewById(R.id.activity_mine_home_tv_region);
        callPhone = (TextView) findViewById(R.id.activity_mine_album_call_phone);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
    }

    @Override
    protected void initView() {
        mTitle.setText("详情");
    }

    @Override
    protected void initListener() {
        callPhone.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_resuce_troops_details;
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("VRT_ID", getIntent().getStringExtra("troopId"));
        NetRequest.ParamPostRequest(PortUtil.VehicleRescueTeamDetails, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), TroopsDetailsBean.class);
                setParames();
            }
        });
    }

    private void setParames() {
        TroopsDetailsBean.VehicleRescueTeamDetailsBean b = bean.getVehicleRescueTeamDetails();
        if (TextUtils.isEmpty(b.getVRT_Image())) {
            icon.setImageResource(R.drawable.icon_replace);
        } else {
            NetRequest.loadImg(this, b.getVRT_Image(), icon);
        }
        name.setText(b.getVRT_Name());
        desc.setText(b.getVRT_Description());
        phone.setText(b.getVRT_Phone());
        address.setText(b.getVRT_Address());
        scope.setText(b.getVRT_Project());
        region.setText(b.getVRT_Range());
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
