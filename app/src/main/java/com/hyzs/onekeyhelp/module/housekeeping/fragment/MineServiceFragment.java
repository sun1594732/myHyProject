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
import com.hyzs.onekeyhelp.module.housekeeping.activity.MineServiceDetailActivity;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.MineServiceAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.bean.MineServiceBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineServiceFragment extends Fragment implements AdapterView.OnItemClickListener, VRefresh.OnLoadListener, VRefresh.OnRefreshListener {
    private static final String TAG = "MineServiceFragment";
    private Context mContext;
    //    private LinearLayout distance, wage, experience, age, service;
    private VRefresh refresh;
    private ListView listView;
    private MineServiceAdapter adapter;
    private MySharedPreferences mySharedPreferences;
    private int index = 1;
    private boolean isRefresh = true;
    private MineServiceBean bean;
    private List<MineServiceBean.LifeServicePersonalBean> list = new ArrayList<>();
    private int d = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_service, null);
        mContext = getActivity();
        index = 1;
        isRefresh = true;
        assignView(view);
        initView();
        initListener();
        initData();
        return view;
    }

    public void setListData(List<MineServiceBean.LifeServicePersonalBean> list) {
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void assignView(View view) {
        mySharedPreferences = MySharedPreferences.getInstance(mContext);
//        distance = (LinearLayout) view.findViewById(R.id.fragment_mine_service_distance);
//        wage = (LinearLayout) view.findViewById(R.id.fragment_mine_service_wage);
//        experience = (LinearLayout) view.findViewById(R.id.fragment_mine_service_experience);
//        age = (LinearLayout) view.findViewById(R.id.fragment_mine_service_age);
//        service = (LinearLayout) view.findViewById(R.id.fragment_mine_service_service);
        refresh = (VRefresh) view.findViewById(R.id.vrefresh);
        listView = (ListView) view.findViewById(R.id.fragment_mine_service_listview);
        refresh.setView(mContext, listView);
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
        map.put("b_j", d+"");
        map.put("e_j", d+"");
        map.put("b_s", d+"");
        map.put("e_s", d+"");
        map.put("b_e", d+"");
        map.put("e_e", d+"");
        map.put("b_a", d+"");
        map.put("e_a", d+"");
        map.put("s", d+"");
        Log.e(TAG, "requestData: " + MySharedPreferences.getUserId(mContext));
        Log.e(TAG, "requestData: " + mySharedPreferences.getString("X") + "," + mySharedPreferences.getString("Y"));
        Log.e(TAG, "requestData: " + System.currentTimeMillis() / 1000);
        NetRequest.ParamPostRequest(PortUtil.LifeServicePersonal, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    Log.e(TAG, "getData: " + UrlDecodeUtil.urlCode(data));
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineServiceBean.class);
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
            list.addAll(bean.getLifeServicePersonal());
        } else {
            list.addAll(bean.getLifeServicePersonal());
        }
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        adapter = new MineServiceAdapter(mContext, list);
        listView.setAdapter(adapter);
    }

    private void initListener() {
        refresh.setOnLoadListener(this);
        refresh.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), MineServiceDetailActivity.class);
        intent.putExtra("LS_ID", list.get(position).getId() + "");
        startActivity(intent);
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
}
