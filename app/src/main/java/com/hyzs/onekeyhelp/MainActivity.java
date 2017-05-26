package com.hyzs.onekeyhelp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyzs.onekeyhelp.alert.AlertBean;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.carresuce.view.CarResuceActivity;
import com.hyzs.onekeyhelp.contact.fragment.ContactFragment;
import com.hyzs.onekeyhelp.family.FamilyFragment;
import com.hyzs.onekeyhelp.help.activity.HelpActivity;
import com.hyzs.onekeyhelp.help.activity.HelpListActivity;
import com.hyzs.onekeyhelp.home.HomeFragment;
import com.hyzs.onekeyhelp.lifehelp.view.activity.LifeHelpHomeActivity;
import com.hyzs.onekeyhelp.mine.MineFragment;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.hyzs.onekeyhelp.InfoService.isAppRunningForeground;
import static com.hyzs.onekeyhelp.R.id.rb_main_contact;


public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    public static final String ACTION_UPDATEUI = "action.updateUI";
    UpdateUIBroadcastReceiver broadcastReceiver;
    private LinearLayout ll;
    public MainActivity mActivity;
    HomeFragment homeFragment;
    FamilyFragment familyFragment;
    ContactFragment contactFragment;
    MineFragment mineFragment;
    RadioGroup rgMainBottom;
    RadioButton mContactBtn;
    Button helpBtn;
    Notification notification = null;
    MySharedPreferences mySharedPreferences;
    private TextView contactPop;
    private double X, Y;

    private Handler mJPushHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (JPushInterface.isPushStopped(MyApplication.getAppContext())) {
                JPushInterface.resumePush(MyApplication.getAppContext());
            }
            // 调用 JPush 接口来设置别名。
            JPushInterface.setAliasAndTags(MyApplication.getAppContext(),
                    MySharedPreferences.getUserId(MainActivity.this),
                    null,
                    mAliasCallback);
        }
    };


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Map<String, String> param = new ArrayMap<>();
            if (!MySharedPreferences.isLogin(MainActivity.this)) {
                handler.sendEmptyMessageDelayed(1, 10000);
                return;
            }
            param.put("userid", MySharedPreferences.getUserId(MainActivity.this));
            NetRequest.ParamPostRequest(PortUtil.Alert, param, new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    try {
                        AlertBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), AlertBean.class);
                        if (bean.getDistressAlarms().size() == 0) {
                            handler.sendEmptyMessageDelayed(1, 10000);
                            return;
                        }
                        AlertBean.DistressAlarmsBean newbean = bean.getDistressAlarms().get(0);
                        String ns = Context.NOTIFICATION_SERVICE;
                        mNotificationManager = (NotificationManager) getSystemService(ns);
                        //定义通知栏展现的内容信息
                        int icon = R.mipmap.ic_launcher;
                        Context context = getApplicationContext();
                        CharSequence tickerText = null;
                        Intent notificationIntent = null;
                        long when = System.currentTimeMillis();
                        //定义下拉通知栏时要展现的内容信息
                        CharSequence contentTitle = "你有一条新的求助信息通知";
                        CharSequence contentText = "你有一条新的求助信息通知";
                        Context appContext = MyApplication.getAppContext();
                        String packageName = appContext.getApplicationInfo().packageName;
                        if (!isAppRunningForeground(appContext)) {
                            notificationIntent = appContext.getPackageManager().getLaunchIntentForPackage(packageName);
                            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        } else {
                            switch (newbean.getType()) {
                                case 0:
                                    tickerText = "有新的车辆救援信息";
                                    notificationIntent = new Intent(context, CarResuceActivity.class);
                                    break;
                                case 1:
                                    tickerText = "有新的一键求助信息";
                                    notificationIntent = new Intent(context, HelpListActivity.class);
                                    break;
                                case 2:
                                    tickerText = "有新的生活求助信息";
                                    notificationIntent = new Intent(context, LifeHelpHomeActivity.class);
                                    break;
                            }
//                            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                        Notification.Builder builder = new Notification.Builder(getApplicationContext()).setTicker(tickerText)
                                .setSmallIcon(icon).setSound(Uri.parse("android.resource://"
                                        + getPackageName() + "/" + R.raw.alert_sound)).setAutoCancel(true).setOnlyAlertOnce(true).setWhen(when);
                        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0,
                                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        notification = builder.setContentIntent(contentIntent).setContentTitle(contentTitle).setContentText(contentText).build();
                        mNotificationManager.notify(0, notification);
                        handler.sendEmptyMessageDelayed(1, 10000);
                    } catch (Exception e) {
                        ToastUtils.showShort(MyApplication.getAppContext(), "请求错误，求助推送暂无法使用");
                    }
                }
            });
        }
    };
    private NotificationManager mNotificationManager;
    private FrameLayout mContactFrame;
    private PopupWindow pop;

    @Override
    protected void assignView() {
        handler.sendEmptyMessageDelayed(1, 10000);
        ll = (LinearLayout) findViewById(R.id.activity_main);
        rgMainBottom = (RadioGroup) findViewById(R.id.rg_main_bottom);
        mContactBtn = (RadioButton) findViewById(R.id.rb_main_contact);
        helpBtn = (Button) findViewById(R.id.rb_main_help);
        contactPop = (TextView) findViewById(R.id.rb_main_contact_pop);
        mContactFrame = (FrameLayout) findViewById(R.id.rb_main_contact_frame);
    }

    private void InitImageLoader() {
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this.getApplicationContext());
        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void initView() {
        Intent intent = new Intent(this, InfoService.class);
        startService(intent);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATEUI);
        broadcastReceiver = new UpdateUIBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);
        mJPushHandler.sendEmptyMessage(0);
        pop = HelpDialog.createDialogNoAlert(this, this, "您还没有登录", 0);
        InitImageLoader();
    }

    @Override
    protected void initListener() {
        mContactFrame.setOnClickListener(this);
        helpBtn.setOnClickListener(this);
        rgMainBottom.setOnCheckedChangeListener(this);
        mContactBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mActivity = this;
        fillFragment();
        measureRb();
        loginByHX();
        homeFragment.setCallBack(new HomeFragment.homeCallBack() {
            @Override
            public void getCallBack() {
                rgMainBottom.clearCheck();
                rgMainBottom.check(R.id.rb_main_mine);
            }
        });
    }

    private void fillFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        homeFragment = new HomeFragment();
        familyFragment = new FamilyFragment();
        contactFragment = new ContactFragment();
        mineFragment = new MineFragment();
        if ("notification".equals(getIntent().getStringExtra("from"))) {
            transaction.add(R.id.container, homeFragment);
            transaction.add(R.id.container, familyFragment);
            transaction.add(R.id.container, contactFragment);
            transaction.add(R.id.container, mineFragment);
            transaction.hide(familyFragment);
            transaction.hide(homeFragment);
            transaction.hide(mineFragment);
        } else {
            transaction.add(R.id.container, homeFragment);
            transaction.add(R.id.container, familyFragment);
            transaction.add(R.id.container, contactFragment);
            transaction.add(R.id.container, mineFragment);
            transaction.hide(familyFragment);
            transaction.hide(contactFragment);
            transaction.hide(mineFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        hideFragments();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (checkedId) {
            case R.id.rb_main_home:
                transaction.show(homeFragment);
                break;
            case R.id.rb_main_community:
                transaction.show(familyFragment);
                break;
            case rb_main_contact:
                transaction.show(contactFragment);
                break;
            case R.id.rb_main_mine:
                transaction.show(mineFragment);
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (familyFragment != null) {
            transaction.hide(familyFragment);
        }
        if (contactFragment != null) {
            transaction.hide(contactFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    //测量底部导航栏高度
    public void measureRb() {
        mySharedPreferences = MySharedPreferences.getInstance(this);
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        rgMainBottom.measure(w, h);
        int height = rgMainBottom.getMeasuredHeight();
        mySharedPreferences.setInt("height", height);
    }

    protected void loginByHX() {
        if (TextUtils.isEmpty(mySharedPreferences.getString("uid"))) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("lgoin::"+mySharedPreferences.getString("uid"));
                EMClient.getInstance().login(mySharedPreferences.getString("uid"), "hyzh_2017", new EMCallBack() {
                    //成功
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    }

                    //失败
                    public void onError(int i, String s) {

                    }

                    public void onProgress(int i, String s) {

                    }
                });
            }
        }).start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (!TextUtils.isEmpty(intent.getStringExtra("from"))) {
            rgMainBottom.clearCheck();
            rgMainBottom.check(R.id.rb_main_contact);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            hideFragments();
            transaction.show(contactFragment);
            transaction.commitAllowingStateLoss();
        }
    }

    public void onResume() {
        super.onResume();
        uploadFlag = true;
        uploadLocation();
        int msgCount = mySharedPreferences.getInt("notificationCount", 0);
        if (msgCount == 0 || msgCount < 0) {
            contactPop.setVisibility(View.INVISIBLE);
        } else {
            contactPop.setText(msgCount + "");
            contactPop.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("notification"))) {
            rgMainBottom.clearCheck();
            rgMainBottom.check(R.id.rb_main_contact);
        }
        MobclickAgent.onPageStart("MainActivity");
        MobclickAgent.onResume(this);
    }

    private static final int BAIDU_READ_PHONE_STATE = 100;
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    private boolean uploadFlag = false;

    private void uploadLocation() {
        if (MySharedPreferences.isLogin(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                        || getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || getApplicationContext().checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                        || getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                                    , Manifest.permission.READ_PHONE_STATE
                                    , Manifest.permission.READ_EXTERNAL_STORAGE
                                    , Manifest.permission.RECORD_AUDIO
                                    , Manifest.permission.CAMERA}
                            , BAIDU_READ_PHONE_STATE);
                } else {
                    //声明LocationClient类
                    mLocationClient = new LocationClient(getApplicationContext());
                    //注册监听函数
                    mLocationClient.registerLocationListener(myListener);
                    initLocation();
                    //开始定位
                    mLocationClient.start();
                    SDKInitializer.initialize(this);
                    //获取地图控件引用
                }
            } else {
                //声明LocationClient类
                mLocationClient = new LocationClient(getApplicationContext());
                //注册监听函数
                mLocationClient.registerLocationListener(myListener);
                initLocation();
                //开始定位
                mLocationClient.start();
                SDKInitializer.initialize(this);
                //获取地图控件引用
            }
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
                        return;
                    }
                }
                // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                //声明LocationClient类
                mLocationClient = new LocationClient(getApplicationContext());
                //注册监听函数
                mLocationClient.registerLocationListener(myListener);
                initLocation();
                //开始定位
                mLocationClient.start();
                SDKInitializer.initialize(this);
                //获取地图控件引用
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


    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (uploadFlag) {
                Y = location.getLatitude();
                X = location.getLongitude();
                if (mySharedPreferences.getString("X").equals(X) & mySharedPreferences.getString("Y").equals(Y)) {
                    mLocationClient.unRegisterLocationListener(myListener);
                    return;
                }
                upLoadLatLng(new LatLng(location.getLatitude(),
                        location.getLongitude()));
                mySharedPreferences.setString("X", X + "");
                mySharedPreferences.setString("Y", Y + "");
                mLocationClient.unRegisterLocationListener(myListener);
            }
        }
    }

    private void upLoadLatLng(LatLng latLng) {
        if (!MySharedPreferences.isLogin(this)) {
            return;
        }
        String url = PortUtil.UserGPSInfoAdd;
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.isLogin(this) ? MySharedPreferences.getUserId(this) : "0");
        map.put("location", latLng.longitude + "," + latLng.latitude);
        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                uploadFlag = false;
            }
        });
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainActivity");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                if (notification != null) {
                    mNotificationManager.cancel(0);
                }
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_main_help:
                if (!MySharedPreferences.isLogin(this)) {
                    pop.showAtLocation(ll, Gravity.CENTER, 0, 0);
                    return;
                }
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
                break;
            case R.id.rb_main_contact:
                rgMainBottom.clearCheck();
                rgMainBottom.check(R.id.rb_main_contact);
                break;
            case R.id.help_pop_confirm:
                pop.dismiss();
                break;
        }
    }

    //    及时更新radiobutton的广播接收者
    private class UpdateUIBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int msgCount = intent.getIntExtra("count", 0);
            if (msgCount == 0 || msgCount < 0) {
                contactPop.setVisibility(View.INVISIBLE);
            } else {
                contactPop.setText(msgCount + "");
                contactPop.setVisibility(View.VISIBLE);
            }
        }
    }

    //    设置极光别名
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    break;
                case 6002:
                    mJPushHandler.sendEmptyMessageDelayed(0, 1000 * 60);
                    break;
                default:
                    break;
            }
        }
    };


}
