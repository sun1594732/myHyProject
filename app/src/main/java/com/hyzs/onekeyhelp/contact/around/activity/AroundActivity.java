package com.hyzs.onekeyhelp.contact.around.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.around.adapter.AroundListAdapter;
import com.hyzs.onekeyhelp.contact.bean.SearchBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AroundActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AroundActivity";
    private static final int BAIDU_READ_PHONE_STATE = 100;
    private TextView mEmptyTv;
    private LatLng center = null;
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    private RelativeLayout mEmptyView;
    private float distance = 500;//搜索范围
    private boolean searchFlag = true;
    private List<SearchBean.SearchAroundResultBean> list = new ArrayList<>();
    private SearchBean.SearchAroundResultBean resultBen = null;
    private TextView title;
    private ImageView back;
    private ListView listView;
    private VRefresh refresh;
    private Dialog dialog;
    private SearchBean searchBean;
    private AroundListAdapter adapter;
    private Request request;
    private boolean isUrgentStyle;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_search_around);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                                , Manifest.permission.READ_PHONE_STATE
                                , Manifest.permission.READ_EXTERNAL_STORAGE}
                        , BAIDU_READ_PHONE_STATE);
            } else {
                first();
            }
        } else {
            first();
        }
    }

    private void first() {
        initView();
        initData();
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        //开始定位
        mLocationClient.start();
        SDKInitializer.initialize(this);
        //获取地图控件引用
        initListener();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:

                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                first();
                break;

            default:
                break;

        }
    }


    private void initListener() {
        back.setOnClickListener(this);
        refresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                if (center != null) {
                    if (adapter != null) {
                        adapter.setCountNum(adapter.getCountNum() + 10);
                        adapter.notifyDataSetChanged();
                    }
                    refresh.setLoading(false);
                } else refresh.setLoading(false);
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                aroundSearch(center);
//                if (center != null) {
//                    if (adapter != null) {
//                        adapter.setCountNum(10);
//                        adapter.notifyDataSetChanged();
//                    }
//                    refresh.setRefreshing(false);
//                } else refresh.setRefreshing(false);
            }
        });
    }

    private void initData() {
        title.setText("搜索周边");
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.around_activity_list);
        refresh = (VRefresh) findViewById(R.id.activity_around_refresh);
        refresh.setView(this, listView);
        dialog = ProgressDialog.createProgressLoading(this);
        title = (TextView) findViewById(R.id.toolbar_title);
        back = (ImageView) findViewById(R.id.toolbar_back);
        mEmptyView = (RelativeLayout) findViewById(R.id.layout_empty);
        mEmptyTv = (TextView) findViewById(R.id.layout_empty_tv);
        isUrgentStyle = getIntent().getBooleanExtra("isUrgent", false);
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            if (center == null) {
                center = new LatLng(location.getLatitude(), location.getLongitude());
            }
            if (searchFlag) {
                aroundSearch(center);
                searchFlag = false;
            }
            mLocationClient.unRegisterLocationListener(myListener);
        }
    }

    private void showMarker() {
        if (searchBean.getSearchAroundResult().size() == 0) {
            mEmptyTv.setText("您周围未搜索到其他用户");
        } else mEmptyView.setVisibility(View.GONE);
        refresh.setLoading(false);
        refresh.setRefreshing(false);
        list.clear();
        list.addAll(searchBean.getSearchAroundResult());
        adapter = new AroundListAdapter(AroundActivity.this, list, isUrgentStyle);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void aroundSearch(LatLng center) {
        String logLat = convertDistanceToLogLat(center.longitude, center.latitude, 90);
        String url = PortUtil.SearchAroundList + "?currId=" + MySharedPreferences.getUserId(this) + "&location=" + center.longitude + "," + center.latitude + "&searchRange=" + logLat + "&pageIndex=1&pageSize=999";
        request = new okhttp3.Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                request = null;
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                request = null;
                try {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    searchBean = gson.fromJson(json, SearchBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMarker();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                }

            }
        });
    }


    private String convertDistanceToLogLat(double longitude, double latitude, double angle) {
        String logLat = "";
        double lon = longitude + (distance * Math.sin(angle * Math.PI / 180)) / (111 * Math.cos(longitude * Math.PI / 180));//将距离转换成经度的计算公式
        double lat = latitude + (distance * Math.cos(angle * Math.PI / 180)) / 111;//将距离转换成纬度的计算公式
        logLat = lon + "," + lat;
        return logLat;
    }

    public void onResume() {
        super.onResume();
        dialog.show();
        if (null != center)
            aroundSearch(center);
        MobclickAgent.onPageStart("AroundActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("AroundActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

}
