package com.hyzs.onekeyhelp.home.help.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.carresuce.view.EmergencyRescueActivity;
import com.hyzs.onekeyhelp.contact.bean.SearchBean;
import com.hyzs.onekeyhelp.contact.overlayutils.PoiOverlay;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.AudioRecorder2Mp3Util;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.OkHttpUtil;
import com.hyzs.onekeyhelp.widget.GIFview;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

import static com.hyzs.onekeyhelp.R.drawable.location;


/**
 * Created by Administrator on 2017/3/24.
 */

public class VehicleRescueActivity extends BaseActivity implements View.OnClickListener, BaiduMap.OnMapRenderCallback {
    private static final String TAG = "VehicleRescueActivity";
    private ImageView back;
    private TextView title;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private float distance = 300;//搜索范围
    private static final int BAIDU_READ_PHONE_STATE = 100;
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new VehicleRescueActivity.MyLocationListener();
    private boolean mapLoadFlag = true;
    private long time;
    private LatLng center = null;
    private boolean searchFlag = true;
    private List<SearchBean.SearchAroundResultBean> list;
    BitmapDescriptor mCurrMarker;
    final List<OverlayOptions> overlayOptions = new ArrayList<>();
    private int mark = 0;
    private TextView num;
    private int number = 10;
    private GIFview gifView;
    private PopupWindow mPopupWindow;
    private LinearLayout ll;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mark++;
                    Bitmap bitmap1;
                    if (null != msg.getData().getByteArray("urlByte")) {
                        byte[] bytes = msg.getData().getByteArray("urlByte");
                        bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    } else {
                        bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_replace);
                    }
                    if (bitmap1==null) {
                        bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_replace);
                    }
                    Bitmap bitmap = zoomImg(bitmap1, 100, 100);
                    LatLng mPoint = (LatLng) msg.obj;
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(toRoundBitmap(bitmap));
                    MarkerOptions oA = new MarkerOptions().position(mPoint).icon(bitmapDescriptor);
                    overlayOptions.add(oA);
                    bitmapDescriptor = null;
                    if (mark == list.size()) {
                        overlayManager.addToMap();
                        overlayManager.zoomToSpan(); //仅对mark起作用
                        recordListener();

                    }
                    break;
                case 3:
                    Intent intent = new Intent(VehicleRescueActivity.this, EmergencyRescueActivity.class);
                    if (center != null) {
                        intent.putExtra("latitude", center.latitude);
                        intent.putExtra("longitude", center.longitude);
                        intent.putExtra("address", address);
                    }
//                    intent.putExtra("address",)
                    startActivity(intent);
                    finish();

                    break;
                default:
                    break;
            }
        }
    };



    private PoiOverlay overlayManager;
    private okhttp3.Request request;
    private String address;

    @Override
    protected void assignView() {
        mMapView = (MapView) findViewById(R.id.vehicle_rescue_mapview);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        num = (TextView) findViewById(R.id.vehicle_rescue_num);
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        gifView = (GIFview) findViewById(R.id.gifview);
        mBaiduMap.getUiSettings().setAllGesturesEnabled(false);
        mMapView.showZoomControls(false);
    }

    @Override
    protected void initView() {
        title.setText("紧急救援");
        gifView.setMovieResource(R.mipmap.mac);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vehicle_rescue;
    }

    @Override
    protected void initData() {
        if (!MySharedPreferences.isLogin(this)) {
            Toast.makeText(this, "您还没登录", Toast.LENGTH_SHORT).show();
        }
        mCurrMarker = BitmapDescriptorFactory.fromResource(location);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    ||getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    ||getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    ||getApplicationContext().checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
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
                time = System.currentTimeMillis();
                mapViewDrawListener();
            }
        } else {
            // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
            mLocationClient = new LocationClient(getApplicationContext());
            mLocationClient.registerLocationListener(myListener);
            initLocation();
            //开始定位
            mLocationClient.start();
            SDKInitializer.initialize(this);
            time = System.currentTimeMillis();
            mapViewDrawListener();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:

                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "没有定位或麦克风权限", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                Log.e(TAG, "onRequestPermissionsResult:  权限以获取");
                // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                mLocationClient = new LocationClient(getApplicationContext());
                mLocationClient.registerLocationListener(myListener);
                initLocation();
                //开始定位
                mLocationClient.start();
                SDKInitializer.initialize(this);
                time = System.currentTimeMillis();
                mapViewDrawListener();
                break;

            default:
                break;

        }
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

    private void mapViewDrawListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mapLoadFlag) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (System.currentTimeMillis() - time == 5000) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(VehicleRescueActivity.this, "网络状态不好！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }
        }).start();
    }

    /**
     * 地图加载完成监听
     */
    @Override
    public void onMapRenderFinished() {
        mapLoadFlag = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.help_pop_confirm:

                break;
        }
    }


    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            if (center == null) {
                center = new LatLng(location.getLatitude(), location.getLongitude());
            }
            if (searchFlag) {
                searchFlag = false;
                address = location.getAddrStr();
                aroundSearch(center);
            }
        }
    }

    private void aroundSearch(LatLng center) {
        if (!MySharedPreferences.isLogin(this)) {
            return;
        }
        String logLat = convertDistanceToLogLat(center.longitude, center.latitude, 90);
        String url = PortUtil.SearchAroundList+"?currId="+ MySharedPreferences.getUserId(this)+"&location=" + center.longitude + "," + center.latitude + "&searchRange=" + logLat + "&pageIndex=1&pageSize=10";
        OkHttpUtil.newInstamce().getAsynHttp(url, new OkHttpUtil.HttpCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) {
                try {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    SearchBean searchBean = gson.fromJson(json, SearchBean.class);
                    showMarker(searchBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showMarker(SearchBean searchBean) {
        list = searchBean.getSearchAroundResult();
        locationAll(list, mBaiduMap);
    }

    /**
     * 通过坐标集合，显示并缩放到所有点
     */
    public void locationAll(final List<SearchBean.SearchAroundResultBean> list, final BaiduMap mBaiDuMap) {
//管理多个覆盖物
        overlayManager = new PoiOverlay(mBaiDuMap) {
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

        if (list.size() == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MarkerOptions oA = new MarkerOptions().position(center).icon(mCurrMarker).zIndex(999);
                    overlayOptions.add(oA);
                    overlayManager.addToMap();
                    overlayManager.zoomToSpan();
                    recordListener();
                }
            });
        }

        if (list != null && mBaiDuMap != null) {
            mBaiDuMap.clear();
            for (SearchBean.SearchAroundResultBean point : list) {
                double mLatitude = Double.parseDouble(point.getLocationX());
                double mLongitude = Double.parseDouble(point.getLocationY());
                LatLng mPoint = new LatLng(mLatitude, mLongitude);
                loadImage(point.getAvatar(), mPoint);
            }
            MarkerOptions oA = new MarkerOptions().position(center).icon(mCurrMarker).zIndex(999);
            overlayOptions.add(oA);
        }
    }

    private void loadImage(String url, final LatLng mPoint) {
        if (TextUtils.isEmpty(url)) {
            Message message = new Message();
            message.obj = mPoint;
            message.what = 1;
            handler.sendMessage(message);
//            if (mark == list.size()) {
//                overlayManager.addToMap();
//                overlayManager.zoomToSpan(); //仅对mark起作用
//            }
            return;
        }
        OkHttpClient client = new OkHttpClient();
        request = new okhttp3.Request.Builder().url(url).build();
        Call mcall = client.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                request = null;
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response)  {
                try {
                    request = null;
                    Message message = new Message();
                    byte[] bytes = response.body().bytes();
                    Bundle bundle = new Bundle();
                    bundle.putByteArray("urlByte", bytes);
                    message.setData(bundle);
                    message.obj = mPoint;
                    message.what = 1;
                    if (handler == null) {
                        return;
                    } else {
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

    public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 处理图片
     *
     * @param bm        所要转换的bitmap
     * @param newWidth  新的宽
     * @param newHeight 新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /*
     * 录音十秒
	 */
    private void recordAndSave() {
        // 录音相关 ------------------------------------------------------------
        Toast.makeText(this, "10秒录音开始", Toast.LENGTH_SHORT).show();
        startRecord();
    }

    private boolean canClean = false;
    AudioRecorder2Mp3Util util;

    /**
     * 启动录音
     */
    public void startRecord() {
        if (util == null) {
            util = new AudioRecorder2Mp3Util(null, "/sdcard/test_audio_recorder_for_mp3.raw", "/sdcard/test_audio_recorder_for_mp3.mp3");
        }
        if (canClean) {
            util.cleanFile(AudioRecorder2Mp3Util.MP3 | AudioRecorder2Mp3Util.RAW);
        }
        Toast.makeText(VehicleRescueActivity.this, "请说话", Toast.LENGTH_SHORT).show();
        util.startRecording();
        canClean = true;

    }

    private boolean recordFlag = true;

    private void recordListener() {
        recordAndSave();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    while (number > 0) {
                        Thread.sleep(1000);
                        number--;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                num.setText(number + "");
                            }
                        });
                    }
                    stopRecord();
                    if (null != handler)
                        handler.sendEmptyMessage(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        if (util == null) {
            return;
        }
        util.stopRecordingAndConvertFile();
        util.cleanFile(AudioRecorder2Mp3Util.RAW);
        // 如果要关闭可以
        util.close();
        util = null;
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;
        mMapView.onDestroy();
        new Thread(new Runnable() {
            @Override
            public void run() {
                stopRecord();
            }
        }).start();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("VehicleRescueActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("VehicleRescueActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
}
