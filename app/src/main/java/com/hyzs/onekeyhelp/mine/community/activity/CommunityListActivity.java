package com.hyzs.onekeyhelp.mine.community.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.mine.community.adapter.ComminityListAdapter;
import com.hyzs.onekeyhelp.mine.community.bean.MyCommunityEntity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.HashMap;
import java.util.Map;


public class CommunityListActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "CommunityListActivity";
    private ImageView back;
    private TextView title,right;
    private Button btnAdd;
    private ListView listView;
    private MyCommunityEntity bean;
    private ComminityListAdapter adapter;

    @Override
    protected void assignView() {
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        btnAdd = (Button) findViewById(R.id.activity_community_list_add);
        listView = (ListView) findViewById(R.id.activity_community_list_list);
        right = (TextView) findViewById(R.id.toolbar_right);
    }

    @Override
    protected void initView() {
        title.setText("我的社区");
        right.setText("添加");
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        right.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_list;
    }

    @Override
    protected void initData() {
//        requestData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData() {
        Map<String, String> map = new HashMap<>();
        map.put("curr_uid", MySharedPreferences.getUserId(this));
        NetRequest.ParamDialogPostRequest(PortUtil.MyCommunity, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MyCommunityEntity.class);
                initListView();
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {

            }
        });
    }

    private void initListView() {
        adapter = new ComminityListAdapter(this, bean.getMycommunity());
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:

                break;
            case R.id.activity_community_list_add:
                startActivity(new Intent(this,AddCommunityActivity.class));
                break;
        }
    }
}
