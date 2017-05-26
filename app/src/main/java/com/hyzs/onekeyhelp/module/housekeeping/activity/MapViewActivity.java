package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.contact.overlayutils.PoiOverlay;
import com.hyzs.onekeyhelp.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class MapViewActivity extends BaseActivity {

    private MapView mMapView;
    public LocationClient mLocationClient = null;
    private static final int BAIDU_READ_PHONE_STATE = 100;
    private String address;
    public BDLocationListener myListener = new MyLocationListener();
    private BaiduMap mBaiduMap;
    BitmapDescriptor mCurrMarker;
    boolean isFirstLoc = true;
    private PoiOverlay overlayManager;
    private List<OverlayOptions> overlayOptions;
    private GeoCoder mGeoCoder;
    private LatLng mPoint;
    private TextView mMapDes, mSure;
    private MapStatus.Builder builder;

    @Override
    protected void assignView() {
        mMapView = (MapView) findViewById(R.id.map);
        mMapDes = (TextView) findViewById(R.id.map_des);
        mSure = (TextView) findViewById(R.id.map_sure);
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    || getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || getApplicationContext().checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                                , Manifest.permission.READ_PHONE_STATE
                                , Manifest.permission.READ_EXTERNAL_STORAGE
                                , Manifest.permission.RECORD_AUDIO
                        }
                        , BAIDU_READ_PHONE_STATE);
            } else {
// 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                mLocationClient = new LocationClient(getApplicationContext());
                mLocationClient.registerLocationListener(myListener);
                initLocation();
                //开始定位
                mLocationClient.start();
                SDKInitializer.initialize(this);
            }
        } else {
            // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
            mLocationClient = new LocationClient(getApplicationContext());
            mLocationClient.registerLocationListener(myListener);
            initLocation();
            //开始定位
            mLocationClient.start();
            SDKInitializer.initialize(this);
        }
    }

    @Override
    protected void initListener() {
        mBaiduMap.setOnMapClickListener(listener);
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntent().putExtra("pointX", mPoint.latitude + "");
                getIntent().putExtra("pointY", mPoint.longitude + "");
                getIntent().putExtra("pointInfo", address);
                setResult(RESULT_OK, getIntent());
                if (mGeoCoder!=null) {
                    mGeoCoder.destroy();
                }
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_view;
    }

    @Override
    protected void initData() {
        overlayOptions = new ArrayList<>();
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mGeoCoder = GeoCoder.newInstance();
        mGeoCoder.setOnGetGeoCodeResultListener(GeoCoderListener);
        overlayManager = new PoiOverlay(mBaiduMap) {
            @Override
            public List<OverlayOptions> getOverlayOptions() {
                return overlayOptions;
            }

            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }

            @Override
            public boolean onPolylineClick(Polyline polyline) {
                return true;
            }

            @Override
            public boolean onPoiClick(int i) {
                return true;
            }
        };
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

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
// 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            LatLng ll = new LatLng(locData.latitude,
                    locData.longitude);
            mPoint = ll;
            mCurrMarker = BitmapDescriptorFactory
                    .fromResource(R.drawable.location);
            address = location.getAddress().address;
            mMapDes.setText(location.getAddress().address);
            if (isFirstLoc) {
                isFirstLoc = false;
                builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                MarkerOptions oA = new MarkerOptions().position(ll).icon(mCurrMarker);
                overlayManager.removeFromMap();
                overlayOptions.add(oA);
                overlayManager.addToMap();
                overlayManager.zoomToSpan(); //仅对mark起作用
            }
            mLocationClient.unRegisterLocationListener(myListener);
        }
    }

    BaiduMap.OnMapClickListener listener = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         * @param point 点击的地理坐标
         */
        public void onMapClick(LatLng point) {

            mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(point));
            mPoint = point;
            if (mPoint!=null) {
                builder.target(mPoint).zoom(18.0f);
            }
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            MarkerOptions oA = new MarkerOptions().position(mPoint).icon(mCurrMarker);
            overlayOptions.clear();
            overlayOptions.add(oA);
            overlayManager.addToMap();
            overlayManager.zoomToSpan(); //仅对mark起作用
        }

        @Override
        public boolean onMapPoiClick(MapPoi mapPoi) {
            return false;
        }
        /**
         * 地图内 Poi 单击事件回调函数
         * @param poi 点击的 poi 信息
         */
    };

    OnGetGeoCoderResultListener GeoCoderListener = new OnGetGeoCoderResultListener() {
        public void onGetGeoCodeResult(GeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有检索到结果
            }
            //获取地理编码结果
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
                address = "已选择";
                mMapDes.setText(address);
            } else {
                //获取反向地理编码结果
                address = result.getAddress();
                mMapDes.setText(address);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}
