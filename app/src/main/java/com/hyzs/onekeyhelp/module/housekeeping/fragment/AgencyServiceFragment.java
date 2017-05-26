package com.hyzs.onekeyhelp.module.housekeeping.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.module.housekeeping.activity.AgencyServiceDetailActivity;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.AgencyServiceAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.bean.AgencyServiceBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AgencyServiceFragment extends Fragment implements View.OnClickListener, VRefresh.OnLoadListener, VRefresh.OnRefreshListener, AdapterView.OnItemClickListener {
    private static final String TAG = "AgencyServiceFragment";
    private Context mContext;
    //    private LinearLayout distance, assess, service;
    private VRefresh refresh;
    private ListView listView;
    private MySharedPreferences mySharedPreferences;
    private int index = 1;
    private List<AgencyServiceBean.LifeServiceOrganizationBean> list = new ArrayList<>();
    private AgencyServiceAdapter adapter;
    private boolean isRefresh = true;
    private AgencyServiceBean bean;
    private int d = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agency_layout, null);
        mContext = getActivity();
        assignView(view);
        initView();
        initListener();
        isRefresh = true;
        index = 1;
        initData();
        return view;
    }

    public void setListData(List<AgencyServiceBean.LifeServiceOrganizationBean> list) {
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initListener() {
        refresh.setOnLoadListener(this);
        refresh.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
    }

    private void initView() {
        adapter = new AgencyServiceAdapter(mContext, list);
        listView.setAdapter(adapter);
    }

    private void initData() {
        requestData();
    }

    private void requestData() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(mContext));
        map.put("location", mySharedPreferences.getString("X") + "," + mySharedPreferences.getString("Y"));
        map.put("firstPageReqDateTime", System.currentTimeMillis() / 1000 + "");
        map.put("serachKeyWords", "");
        map.put("pageSize", "10");
        map.put("pageIndex", index + "");
        map.put("b_j", d + "");
        map.put("e_j", d + "");
        map.put("c_n", d + "");
        map.put("s", d + "");

        NetRequest.ParamPostRequest(PortUtil.LifeServiceOrganization, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    Log.e(TAG, "getData: " + UrlDecodeUtil.urlCode(data));
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), AgencyServiceBean.class);
                    initListView();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    refresh.setLoading(false);
                    refresh.setRefreshing(false);
                }
            }
        });
    }

    private void initListView() {
        if (isRefresh) {
            list.clear();
            list.addAll(bean.getLifeServiceOrganization());
        } else {
            list.addAll(bean.getLifeServiceOrganization());
        }
        adapter.notifyDataSetChanged();
    }

    private void assignView(View view) {
        mySharedPreferences = MySharedPreferences.getInstance(mContext);
//        distance = (LinearLayout) view.findViewById(R.id.fragment_agency_layout_distance);
//        assess = (LinearLayout) view.findViewById(R.id.fragment_agency_layout_assess);
//        service = (LinearLayout) view.findViewById(R.id.fragment_agency_layout_service);
        refresh = (VRefresh) view.findViewById(R.id.vrefresh);
        listView = (ListView) view.findViewById(R.id.fragment_agency_layout_listview);
        refresh.setView(mContext, listView);
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        index = 1;
        initData();
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        index++;
        initData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), AgencyServiceDetailActivity.class);
        intent.putExtra("LS_ID", list.get(position).getId() + "");
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
