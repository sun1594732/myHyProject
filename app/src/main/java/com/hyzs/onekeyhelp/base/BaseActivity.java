package com.hyzs.onekeyhelp.base;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hyzs.onekeyhelp.netRequest.nohttp.CallServer;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
//		SDKInitializer.initialize(getApplicationContext());
        setContentView(getLayoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//			StatusBarUtil.statusBarLightMode(this);
//			StatusBarUtil.setStatusBarColor(this, android.R.color.white);
//		}

// getSupportActionBar().hide();
        assignView();
        initData();
        initView();
        initListener();
    }

    protected abstract void assignView();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract int getLayoutId();

    protected abstract void initData();

    @Override
    protected void onStop() {
        super.onStop();
        CallServer.getInstance().cancelAll();
    }
}
