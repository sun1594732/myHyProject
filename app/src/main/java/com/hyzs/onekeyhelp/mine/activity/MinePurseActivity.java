package com.hyzs.onekeyhelp.mine.activity;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.umeng.analytics.MobclickAgent;

import java.util.Map;

public class MinePurseActivity extends BaseActivity implements View.OnClickListener {

    private TextView mCharge, mMoney, mBill;
    private ImageView mBack;

    @Override
    protected void assignView() {
        mCharge = (TextView) findViewById(R.id.ll_purse_rec);
        mMoney = (TextView) findViewById(R.id.tv_user_balance);
        mBill = (TextView) findViewById(R.id.ll_purse_bill);
        mBack = (ImageView) findViewById(R.id.purse_back);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        mCharge.setOnClickListener(this);
        mBill.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_purse;
    }

    @Override
    protected void initData() {
        NetRequest.GetCountMoney(MySharedPreferences.getUserId(MinePurseActivity.this), new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                mMoney.setText(data);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_purse_rec:
                startActivity(new Intent(MinePurseActivity.this, MineChargeActivity.class));
                break;
            case R.id.ll_purse_bill:
                startActivity(new Intent(MinePurseActivity.this, MineBillActivity.class));
                break;
            case R.id.purse_back:
                finish();
                break;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MinePurseActivity");
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MinePurseActivity");
        MobclickAgent.onPause(this);
    }


}
