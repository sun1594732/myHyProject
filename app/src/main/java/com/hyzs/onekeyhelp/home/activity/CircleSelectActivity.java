package com.hyzs.onekeyhelp.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.circle.activity.CircleActivity;
import com.hyzs.onekeyhelp.family.circle.bean.CircleTypeBean;
import com.hyzs.onekeyhelp.home.adapter.CircleSelectAdapter;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

public class CircleSelectActivity extends BaseActivity {
    private ImageView back;
    private TextView title;
    private ListView listView;
    private CircleTypeBean bean;

    @Override
    protected void assignView() {
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        listView = (ListView) findViewById(R.id.activity_circle_select_list);
    }

    @Override
    protected void initView() {
        title.setText("圈子");
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CircleSelectActivity.this, CircleActivity.class);
                intent.putExtra("type", position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_select_layout;
    }

    @Override
    protected void initData() {
        NetRequest.ParamGetRequest(PortUtil.CircleTypeList, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CircleTypeBean.class);
                initListView();
            }
        });
    }

    private void initListView() {
        listView.setAdapter(new CircleSelectAdapter(this, bean.getCircleTypes()));
    }
}
