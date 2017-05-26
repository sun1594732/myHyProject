package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.ServiceProjectListAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.bean.ServiceProjectListBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServiceProjectListActivity extends BaseActivity implements View.OnClickListener, VRefresh.OnLoadListener, VRefresh.OnRefreshListener, ServiceProjectListAdapter.ServiceProjectListClickListener {
    private static final String TAG = "ServiceProjectDetailAda";
    private ImageView back;
    private TextView title;
    private RecyclerView recyclerView;
    private VRefresh refresh;
    private int index = 1;
    private boolean isRefresh = true;
    private ServiceProjectListBean bean;
    private List<ServiceProjectListBean.LifeServiceSPListBean> list = new ArrayList<>();
    private ServiceProjectListAdapter adapter;

    @Override
    protected void assignView() {
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        recyclerView = (RecyclerView) findViewById(R.id.activity_service_project_detail_recycler);
        refresh = (VRefresh) findViewById(R.id.activity_service_project_detail_refresh);
        refresh.setView(this, recyclerView);
    }

    @Override
    protected void initView() {
        title.setText("服务项目");
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ServiceProjectListAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        refresh.setOnLoadListener(this);
        refresh.setOnRefreshListener(this);
        adapter.setListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_project_detail;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("LS_ID", getIntent().getStringExtra("LS_ID"));
//        map.put("currId", "1");
//        map.put("LS_ID", "1");
        map.put("pageSize", "10");
        map.put("pageIndex", index + "");
        NetRequest.ParamPostRequest(PortUtil.LifeServiceSPList, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    Log.e(TAG, "getData() returned: " + UrlDecodeUtil.urlCode(data));
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ServiceProjectListBean.class);
                    initRecyclerView();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    refresh.setLoading(false);
                    refresh.setRefreshing(false);
                }
            }
        });
    }

    private void initRecyclerView() {
        if (isRefresh) {
            list.clear();
            list.addAll(bean.getLifeServiceSPList());
        } else list.addAll(bean.getLifeServiceSPList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        index = 1;
        requestData();
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        index++;
        requestData();
    }

    @Override
    public void click(View view, int position) {
        Intent intent = new Intent(ServiceProjectListActivity.this, ServiceProjectDetailActivity.class);
        intent.putExtra("id", list.get(position).getID() + "");
        startActivity(intent);
    }
}
