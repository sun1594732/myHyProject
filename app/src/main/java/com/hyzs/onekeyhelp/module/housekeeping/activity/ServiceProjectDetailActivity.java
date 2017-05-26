package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.module.housekeeping.bean.ServiceProjectDetailBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.Map;

public class ServiceProjectDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImage, mBack;
    private TextView mTitle, mPrice, mOldPrice, mServiceObject, mContent, mToolBarTitle;
    private LinearLayout mChat, mReservation, mPhone;
    private ServiceProjectDetailBean bean;

    @Override
    protected void assignView() {
        mToolBarTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mImage = (ImageView) findViewById(R.id.activity_service_project_detail2_img);
        mTitle = (TextView) findViewById(R.id.activity_service_project_detail2_title);
        mPrice = (TextView) findViewById(R.id.activity_service_project_detail2_price);
        mOldPrice = (TextView) findViewById(R.id.activity_service_project_detail2_oldPrice);
        mServiceObject = (TextView) findViewById(R.id.activity_service_project_detail2_object);
        mContent = (TextView) findViewById(R.id.activity_service_project_detail2_detail);
        mChat = (LinearLayout) findViewById(R.id.activity_service_project_detail2_chat);
        mReservation = (LinearLayout) findViewById(R.id.activity_service_project_detail2_reservation);
        mPhone = (LinearLayout) findViewById(R.id.activity_service_project_detail2_phone);
    }

    @Override
    protected void initView() {
        mToolBarTitle.setText("服务项目");
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mChat.setOnClickListener(this);
        mReservation.setOnClickListener(this);
        mPhone.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_project_detail2;
    }

    @Override
    protected void initData() {
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("SPP_ID", getIntent().getStringExtra("id"));
        NetRequest.ParamPostRequest(PortUtil.LifeServiceSPDetails, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ServiceProjectDetailBean.class);
                if (TextUtils.isEmpty(bean.getLifeServiceSPDetails().getSPP_Img())) {
                    mImage.setImageResource(R.mipmap.replace_hybb);
                } else
                    NetRequest.loadImg(ServiceProjectDetailActivity.this, bean.getLifeServiceSPDetails().getSPP_Img(), mImage);
                mContent.setText(bean.getLifeServiceSPDetails().getSPP_DetailIntro());
                mTitle.setText(bean.getLifeServiceSPDetails().getSPP_Name());
                switch (bean.getLifeServiceSPDetails().getSPP_Unit()) {
                    case 0:
                        mPrice.setText(bean.getLifeServiceSPDetails().getSPP_Price() + "/ 次");
                        break;
                    case 1:
                        mPrice.setText(bean.getLifeServiceSPDetails().getSPP_Price() + "/ 小时");
                        break;
                }
                mOldPrice.setText(bean.getLifeServiceSPDetails().getSPP_OriginalCost() + "");
                mOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                switch (bean.getLifeServiceSPDetails().getSPP_ServiceObj()) {
                    case 0:
                        break;
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
            case R.id.activity_service_project_detail2_chat:
                MySharedPreferences.getInstance(this).setString("Img", bean.getLifeServiceSPDetails().getSPP_Img());
                NotifyMsgCountUtil.notifyMsg(bean.getLifeServiceSPDetails().getUserid() + "");
                Intent intent1 = new Intent();
                intent1.putExtra("one", bean.getLifeServiceSPDetails().getUserid() + "");
                intent1.putExtra("userName", bean.getLifeServiceSPDetails().getSPP_Name());
                intent1.setClass(this, IntentChatActivity.class);
                startActivity(intent1);
                break;
            case R.id.activity_service_project_detail2_reservation:
                Intent intent = new Intent(this,OrderApplyH5Activity.class);
                intent.putExtra("sppId",bean.getLifeServiceSPDetails().getID());
                intent.putExtra("lsId",bean.getLifeServiceSPDetails().getSPP_LS_ID());
                startActivity(intent);
                break;
            case R.id.activity_service_project_detail2_phone:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "您还没有授予打电话权限", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!TextUtils.isEmpty(bean.getLifeServiceSPDetails().getPhone())) {
                            intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + bean.getLifeServiceSPDetails().getPhone());
                            intent.setData(data);
                            startActivity(intent);
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(bean.getLifeServiceSPDetails().getPhone())) {
                        intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + bean.getLifeServiceSPDetails().getPhone());
                        intent.setData(data);
                        startActivity(intent);
                    }
                }
                break;
        }
    }
}
