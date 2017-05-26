package com.hyzs.onekeyhelp.carresuce.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.hyzs.onekeyhelp.carresuce.adapter.MyHelpFragmentItemAdapter;
import com.hyzs.onekeyhelp.carresuce.bean.MyhelpItemBean;
import com.hyzs.onekeyhelp.carresuce.view.MyHelpDetailsActivity;
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


public class MyHelpFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, VRefresh.OnLoadListener {
    private ListView listView;
    private TextView sendHelp;
    private Context mContext;
    private MyHelpFragmentItemAdapter itemAdapter;
    private VRefresh refresh;
    private String url = PortUtil.bangzhufragment;
    private HashMap hms;
    private MyhelpItemBean itemBean;
    private int pageIndex = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_resuce_myhelp, null);
        mContext = this.getActivity();
        assignView(view);
        initListener();
        initData();
        return view;
    }


    private void initData() {
        itemBean = new MyhelpItemBean();
        requesData(1);
    }


    /**
     * 请求数据
     */
    private void requesData(int type) {
        hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this.getContext()));
        hms.put("VR_Type", "0");
        hms.put("pageIndex", pageIndex + "");
        hms.put("pageSize", "10");
        requestMyData(hms, url, type);
    }

    /**
     * 联网请求数据
     */
    private void requestMyData(HashMap hms, String MWEBUrl, final int type) {
        MWEBUrl = JointGetUrl.getUrl(MWEBUrl, hms);
        NetRequest.CommonUseListRequestMy(MWEBUrl, hms, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                data = UrlDecodeUtil.urlCode(data);
                try {
                    if (type == 1) {
                        itemBean = new Gson().fromJson(data, MyhelpItemBean.class);
                        itemAdapter = new MyHelpFragmentItemAdapter(mContext, itemBean);
                        listView.setAdapter(itemAdapter);

                    } else {
                        if (new Gson().fromJson(data, MyhelpItemBean.class).getVehicleRescueMyHelpList().size() == 0) {
//                                Toast.makeText(mContext, "没有更多数据了！", Toast.LENGTH_SHORT).show();
                        }
                        itemBean.getVehicleRescueMyHelpList().addAll(new Gson().fromJson(data, MyhelpItemBean.class).getVehicleRescueMyHelpList());
                        itemAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "获取数据异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                refresh.setLoading(false);
                refresh.setRefreshing(false);

            }
        });
    }

    private void initListener() {
        listView.setOnItemClickListener(this);
        sendHelp.setOnClickListener(this);
    }

    private void assignView(View view) {
        listView = (ListView) view.findViewById(R.id.gragment_car_resuce_help_iv_info);
        listView.setScrollContainer(false);
        sendHelp = (TextView) view.findViewById(R.id.gragment_car_resuce_help_tv_send_help);
        refresh = (VRefresh) view.findViewById(R.id.car_fragment_my_help_list_fresh);
        refresh.setView(this.mContext, listView);
        refresh.setOnRefreshListener(this);
        refresh.setOnLoadListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getContext(), MyHelpDetailsActivity.class);
        intent.putExtra("VR_ID", itemBean.getVehicleRescueMyHelpList().get(position).getSeek_id() + ""); //String
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), VehicleRescueActivity.class));
    }

    public void onResume() {
        super.onResume();
        pageIndex = 1;
        hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this.getContext()));
        Log.e("cuuid", MySharedPreferences.getUserId(this.getContext()));
        hms.put("VR_Type", "0");
        hms.put("pageIndex", pageIndex + "");
        hms.put("pageSize", "10");
        requestMyData(hms, url, 1);

        MobclickAgent.onPageStart("MyHelp" + "Fragment");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MyHelp" + "Fragment");
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        requesData(1);
    }

    @Override
    public void onLoadMore() {
        ++pageIndex;
        requesData(2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}