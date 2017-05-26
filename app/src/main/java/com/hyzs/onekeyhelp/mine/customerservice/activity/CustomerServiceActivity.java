package com.hyzs.onekeyhelp.mine.customerservice.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.mine.customerservice.adapter.CustomerServiceAdapter;
import com.hyzs.onekeyhelp.mine.customerservice.bean.CustomerServiceBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.HashMap;
import java.util.Map;

public class CustomerServiceActivity extends BaseActivity implements View.OnClickListener{
    private ImageView back;
    private TextView title;
    private ListView listView;
    private CustomerServiceBean bean;
    private CustomerServiceAdapter adapter;

    @Override
    protected void assignView() {
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        listView = (ListView) findViewById(R.id.activity_customerservice_list);
    }

    @Override
    protected void initView() {
        title.setText("在线客服");
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customerservice;
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        String url = PortUtil.Service_Handler+"?action=Get_ServiceList";
        Map<String, String> map = new HashMap<>();
        map.put("action", "Get_ServiceList");
        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CustomerServiceBean.class);
                initListView();
            }
        });
    }

    private void initListView() {
        adapter = new CustomerServiceAdapter(this, bean.getService_class());
        listView.setAdapter(adapter);
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
