package com.hyzs.onekeyhelp.mine.company;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.module.housekeeping.activity.OrderH5Activity;

public class MineCompanyActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTitle, mHouseKeep;
    private ImageView mBack;

    @Override
    protected void assignView() {
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mHouseKeep = (TextView) findViewById(R.id.activity_mine_company_houseKeep);
    }

    @Override
    protected void initView() {
        mTitle.setText("商家入口");
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mHouseKeep.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_company;
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
            case R.id.activity_mine_company_houseKeep:
                Intent intent1 = new Intent(this, OrderH5Activity.class);
                intent1.putExtra("mine",true);
                intent1.putExtra("sppId","0");
                intent1.putExtra("lsId","0");
                intent1.putExtra("type","1");
                startActivity(intent1);
                break;
        }
    }
}
