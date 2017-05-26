package com.hyzs.onekeyhelp.help.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.carresuce.bean.MyResuceItemBean;
import com.hyzs.onekeyhelp.help.activity.BegDetailActivity;
import com.hyzs.onekeyhelp.help.adapter.QiuZhuAdapter;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.JointGetUrl;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class QiuZhuFragment extends Fragment {
    private static final String TAG = "QiuZhuFragment";
    private Context mContext;
    private String url = PortUtil.qiuzhufragment;
    private OkHttpClient okHttpClient;
    private Request request;
    private HashMap hms;

    MyResuceItemBean bean;
    private QiuZhuAdapter adapter;
    private ListView listView;

    private VRefresh refresh;
    private Dialog dialog;
    private boolean loadFlag = false;
    private int pageIndex = 1;
    private List<MyResuceItemBean.MyVehicleRescueListBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qiu_zhu, null);
        mContext = getActivity();
        assignView(view);
        initListener();
        initView();
        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, BegDetailActivity.class);
                intent.putExtra("VR_ID",list.get(position).getID()+"");
                mContext.startActivity(intent);
            }
        });
        refresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                loadFlag = true;
                initData();
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                initData();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        pageIndex = 1;
        dialog.show();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        requesData(1);
    }

    private void assignView(View view) {
        listView = (ListView) view.findViewById(R.id.qiu_zhu_list);
        okHttpClient = new OkHttpClient();
        dialog = ProgressDialog.createProgressLoading(mContext);
        refresh = (VRefresh) view.findViewById(R.id.fragment_qiu_zhu_refresh);
        refresh.setView(mContext, listView);
    }

    /**
     * 请求数据
     */
    private void requesData(int type) {
        if (!MySharedPreferences.isLogin(mContext)) {
            Toast.makeText(mContext, "您还没登录", Toast.LENGTH_SHORT).show();
            return;
        }
        hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(mContext));
        hms.put("VR_Type", "1");
        hms.put("pageIndex", pageIndex + "");
        hms.put("pageSize", "10");
        requestMyData(hms, url, type);
    }

    /**
     * 联网请求数据
     */
    private void requestMyData(HashMap hms, String MWEBUrl, final int type) {

        MWEBUrl = JointGetUrl.getUrl(MWEBUrl, hms);

        request = new Request.Builder().url(MWEBUrl).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) {
                try {
                    String str = URLDecoder.decode(response.body().string(), "utf-8");
                    Log.e(TAG, "onResponse: "+str );
                    if (type == 1) {
                        bean = new Gson().fromJson(str, MyResuceItemBean.class);
                        handler.sendEmptyMessage(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        });
    }

    private void initListView() {
        refresh.setLoading(false);
        refresh.setRefreshing(false);
        if (loadFlag) {
            loadFlag = false;
            if (bean.getMyVehicleRescueList().size() == 0) {
                return;
            }
            list.addAll(bean.getMyVehicleRescueList());
            adapter.notifyDataSetChanged();
        } else {
            dialog.dismiss();
            list.clear();
            list.addAll(bean.getMyVehicleRescueList());
            if (null == adapter) {
                adapter = new QiuZhuAdapter(mContext, list);
                listView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initListView();
                    break;
            }
        }
    };
}
