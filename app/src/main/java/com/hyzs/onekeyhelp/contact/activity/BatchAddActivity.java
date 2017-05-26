package com.hyzs.onekeyhelp.contact.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;

public class BatchAddActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTitle;
    private ImageView mBack;
    private ListView mLv;

    @Override
    protected void assignView() {
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mLv = (ListView) findViewById(R.id.activity_batch_add_lv);
    }

    @Override
    protected void initView() {
       mTitle.setText("批量操作");
    }

    @Override
    protected void initListener() {
       mBack.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_batch_add;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
            finish();
            break;
        }
    }
}
