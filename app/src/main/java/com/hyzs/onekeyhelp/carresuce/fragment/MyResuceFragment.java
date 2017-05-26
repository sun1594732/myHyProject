package com.hyzs.onekeyhelp.carresuce.fragment;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.carresuce.adapter.MyResuceFragmentItemAdapter;
import com.hyzs.onekeyhelp.carresuce.bean.MyResuceItemBean;
import com.hyzs.onekeyhelp.carresuce.view.MyResuceDetailsActivity;
import com.hyzs.onekeyhelp.home.help.activity.VehicleRescueActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.JointGetUrl;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;


public class MyResuceFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, VRefresh.OnLoadListener {
    private ListView listView;
    private TextView sendHelp;
    private Context mContext;
    private MyResuceFragmentItemAdapter itemAdapter;
    private String url= PortUtil.MyVehicleRescueList;
    private HashMap hms;
    private VRefresh  vrefresh;
    private MyResuceItemBean MRDB;
    private int pageIndex=1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_resuce_myresuce,null);
        mContext= this.getActivity();
        assignView(view);
        initListener();
        initData();
        return view;
    }


    private void initData() {
        if(!MySharedPreferences.isLogin(this.mContext)){
            Toast.makeText(this.mContext, "请先登录！", Toast.LENGTH_SHORT).show();
            return;
        }

        requesData(1);

    }

    private void initListener() {

        listView.setOnItemClickListener(this);
        sendHelp.setOnClickListener(this);
    }

    private void assignView(View view) {

        listView = (ListView) view.findViewById(R.id.gragment_car_resuce_iv_info);
        listView.setScrollContainer(false);
        sendHelp = (TextView) view.findViewById(R.id.gragment_car_resuce_tv_send_help);
        vrefresh = (VRefresh) view.findViewById(R.id.fragment_car_resuce_vrefresh);
        vrefresh.setView(this.mContext,listView);
        vrefresh.setOnRefreshListener(this);
        vrefresh.setOnLoadListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(this.getContext(), MyResuceDetailsActivity.class);
        intent.putExtra("VR_ID",MRDB.getMyVehicleRescueList().get(position).getID()+""); //String
        startActivity(intent);
    }
    /**
     *  请求数据
     */
    private void requesData(int type) {
        hms=new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this.getContext()));
        hms.put("VR_Type","0");
        hms.put("pageIndex",pageIndex+"");
        hms.put("pageSize","10");
        Log.e("datas",hms.toString());
        requestMyData(hms,url,type);
    }



    /**
     *   联网请求数据
     *
     */
    private void requestMyData(HashMap hms, String MWEBUrl, final int type) {
        MWEBUrl = JointGetUrl.getUrl(MWEBUrl, hms);
        NetRequest.CommonUseListRequestMy(MWEBUrl, hms, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                data = UrlDecodeUtil.urlCode(data);
                Log.e("data",data);
                try {
                    if (type == 1) {
                        MRDB = new Gson().fromJson(data, MyResuceItemBean.class);
                        itemAdapter = new MyResuceFragmentItemAdapter(mContext, MRDB);
                        listView.setAdapter(itemAdapter);
                    } else {
                        if(new Gson().fromJson(data, MyResuceItemBean.class).getMyVehicleRescueList().size()==0){
                        }
                        MRDB.getMyVehicleRescueList().addAll( new Gson().fromJson(data, MyResuceItemBean.class).getMyVehicleRescueList());
                        Log.e("size" ,MRDB.getMyVehicleRescueList().size()+"");
                        itemAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "获取数据异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                vrefresh.setLoading(false);
                vrefresh.setRefreshing(false);
            }
        });
    }
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), VehicleRescueActivity.class));
    }
    public void onResume() {
        super.onResume();
        hms=new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this.getContext()));
        hms.put("VR_Type","0");
        hms.put("pageIndex","1");
        hms.put("pageSize","10");

        requestMyData(hms,url,1);

        MobclickAgent.onPageStart("MyResuce"+"Fragment");
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MyResuce"+"Fragment");
    }

    @Override
    public void onRefresh() {
        pageIndex=1;
        requesData(1);
    }

    @Override
    public void onLoadMore() {
        ++pageIndex;
        requesData(2);
    }
}
