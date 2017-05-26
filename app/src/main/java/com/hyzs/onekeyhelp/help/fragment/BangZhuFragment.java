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
import com.hyzs.onekeyhelp.carresuce.bean.MyhelpItemBean;
import com.hyzs.onekeyhelp.help.activity.HelpDetailActivity;
import com.hyzs.onekeyhelp.help.adapter.BangZhuAdapter;
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

public class BangZhuFragment extends Fragment {
    private String url= PortUtil.bangzhufragment;
    private Context mContext;
    private ListView listView;
    private BangZhuAdapter adapter;

    private OkHttpClient okHttpClient;
    private Request request ;
    private HashMap hms;
    private MyhelpItemBean itemBean;
    private VRefresh refresh;
    private Dialog dialog;
    private boolean loadFlag = false;
    private List<MyhelpItemBean.VehicleRescueMyHelpListBean> list = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1:
                    initListView();
                    break;
            }
        }

    };

    private void initListView() {
        refresh.setLoading(false);
        refresh.setRefreshing(false);
        if (loadFlag) {
            loadFlag = false;
            if (itemBean.getVehicleRescueMyHelpList().size() == 0) {
                return;
            }
            list.addAll(itemBean.getVehicleRescueMyHelpList());
            adapter.notifyDataSetChanged();
        }else {
            dialog.dismiss();
            list.clear();
            list.addAll(itemBean.getVehicleRescueMyHelpList())  ;
            if (null==adapter) {
                adapter = new BangZhuAdapter(mContext, list);
                listView.setAdapter(adapter);
            }else{
                adapter.notifyDataSetChanged();
            }
        }
    }

    private int pageIndex=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bang_zhu, null);
        mContext = getActivity();
        assignView(view);
        initView();
        initListener();
        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, HelpDetailActivity.class);
                intent.putExtra("VR_ID",list.get(position).getSeek_id()+"");
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
                pageIndex=1;
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
        okHttpClient=new OkHttpClient();
        listView = (ListView) view.findViewById(R.id.bang_zhu_list);
        dialog = ProgressDialog.createProgressLoading(mContext);
        refresh = (VRefresh) view.findViewById(R.id.fragment_bang_zhu_refresh);
        refresh.setView(mContext,listView);
    }

    /**
     *  请求数据
     */
    private void requesData(int type) {
        if (!MySharedPreferences.isLogin(mContext)) {
            Toast.makeText(mContext, "您还没登录", Toast.LENGTH_SHORT).show();
            return;
        }
        hms=new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(mContext));
        hms.put("VR_Type", "1");
        hms.put("pageIndex",pageIndex+"");
        hms.put("pageSize",10+"");

        requestMyData(hms,url,type);
    }

    /**
     *   联网请求数据
     *
     */
    private void requestMyData(HashMap hms, String MWEBUrl, final int type) {


        MWEBUrl = JointGetUrl.getUrl(MWEBUrl, hms);

        request = new Request.Builder().url(MWEBUrl).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String str= URLDecoder.decode(response.body().string(),"utf-8");
                Log.e("infoHelp",str);
                if(type==1){
                    itemBean=new Gson().fromJson(str,MyhelpItemBean.class);
                    handler.sendEmptyMessage(1);
                }
            }
        });
    }
}
