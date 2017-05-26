package com.hyzs.onekeyhelp.mine.active;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.movable.activity.EventRegistActivity;
import com.hyzs.onekeyhelp.home.bean.HomeActiveTypeBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MineActiveFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private MineActiveAdapter adapter;
    private MineActiveBean bean;
    private Dialog dialog;
    private List<MineActiveBean.PersonalCenterMyActivityBean> list = new ArrayList<>();
    private boolean loadFlag = false;
    private int pageIndex = 1;
    private VRefresh refresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_only_lv,null);
        assignView(view);
        initListener();
        requestData();
        return view;
    }
    
    public void assignView(View view){
        refresh = (VRefresh) view.findViewById(R.id.fragment_only_vRefresh);
        listView = (ListView) view.findViewById(R.id.fragment_only_list);
        dialog = ProgressDialog.createProgressLoading(getActivity());
        refresh.setView(getActivity(), listView);
    }
    
    public void initListener(){
        listView.setOnItemClickListener(this);
        refresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                loadFlag = true;
                requestData();
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                requestData();
            }
        });
    }

    private void requestData() {
        String url = PortUtil.PersonalCenterMyActivity;
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(getActivity()));
        map.put("pageSize", "10");
        map.put("pageIndex", pageIndex + "");

        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineActiveBean.class);
                    initList();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "加载数据失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                    refresh.setLoading(false);
                    refresh.setRefreshing(false);
                }
            }
        });
    }

    private void initList() {

        if (loadFlag) {
            loadFlag = false;
            if (bean.getPersonalCenterMyActivity().size() == 0) {
                Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                return;
            }
            list.addAll(bean.getPersonalCenterMyActivity());
            adapter.notifyDataSetChanged();
        } else {
            list.clear();
            list.addAll(bean.getPersonalCenterMyActivity());
            if (null == adapter) {
                adapter = new MineActiveAdapter(getActivity(), list);
                listView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void onResume() {
        super.onResume();
        dialog.show();
        pageIndex = 1;
        requestData();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), EventRegistActivity.class);
        intent.putExtra("title", "我的活动");
        intent.putExtra("type", 3);
        intent.putExtra("movableId", bean.getPersonalCenterMyActivity().get(position).getID());
        startActivity(intent);
    }
}
