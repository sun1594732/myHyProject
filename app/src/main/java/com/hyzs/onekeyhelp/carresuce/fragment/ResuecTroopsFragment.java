package com.hyzs.onekeyhelp.carresuce.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;

import com.hyzs.onekeyhelp.carresuce.adapter.MyResuceTroopsFragmentItemAdapter;
import com.hyzs.onekeyhelp.carresuce.bean.ResuceTroopListBean;
import com.hyzs.onekeyhelp.carresuce.view.MyTroopsDetailsActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hyzs.onekeyhelp.util.MySharedPreferences.getUserId;

public class ResuecTroopsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView lv_troops;
    private ArrayMap<String, String> hashMap;
    private MyResuceTroopsFragmentItemAdapter adapter;
    private List<ResuceTroopListBean.VehicleRescueTeamListBean> list;
    private VRefresh mRefresh;
    int index = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(this.getActivity(), R.layout.fragment_only_lv, null);
        assignView(view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        lv_troops.setOnItemClickListener(this);
        mRefresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                initData();
            }
        });
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                index = 1;
                initData();
            }
        });
    }

    private void assignView(View view) {
        lv_troops = (ListView) view.findViewById(R.id.fragment_only_list);
        mRefresh = (VRefresh) view.findViewById(R.id.fragment_only_vRefresh);
        mRefresh.setView(getActivity(), lv_troops);
        hashMap = new ArrayMap<>();
        list = new ArrayList<>();
        adapter = new MyResuceTroopsFragmentItemAdapter(getActivity(), list);
        lv_troops.setAdapter(adapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        hashMap.put("currId", getUserId(getActivity()));
        hashMap.put("lat", MySharedPreferences.getInstance(getActivity()).getString("Y"));
        hashMap.put("lng", MySharedPreferences.getInstance(getActivity()).getString("X"));
        hashMap.put("pageSize", "20");
        hashMap.put("pageIndex", index + "");
        NetRequest.ParamPostRequest(PortUtil.ResuceTroopList, hashMap, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                ResuceTroopListBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ResuceTroopListBean.class);
                list.addAll(bean.getVehicleRescueTeamList());
                if (bean.getVehicleRescueTeamList().size() == 0) {
                    ToastSingleUtil.showToast(getActivity(), "无更多内容");
                    mRefresh.setLoading(false);
                    return;
                }
                adapter.notifyDataSetChanged();
                index++;
                if (mRefresh.isRefreshing()) {
                    mRefresh.setRefreshing(false);
                }
                if (mRefresh.isMoreData()) {
                    mRefresh.setLoading(false);
                }
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this.getActivity(), MyTroopsDetailsActivity.class);
        intent.putExtra("troopId", list.get(i).getId() + "");
        startActivity(intent);
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ResuecTroops" + "Fragment");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ResuecTroops" + "Fragment");
    }

}
