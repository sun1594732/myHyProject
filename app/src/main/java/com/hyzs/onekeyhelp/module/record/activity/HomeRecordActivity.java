package com.hyzs.onekeyhelp.module.record.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;
import com.hyzs.onekeyhelp.MyApplication;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.carresuce.bean.EmergencyRescueBean;
import com.hyzs.onekeyhelp.carresuce.bean.MyResuceMusicBean;
import com.hyzs.onekeyhelp.help.activity.HelpActivity;
import com.hyzs.onekeyhelp.module.record.adapter.HomeRecordLvAdapter;
import com.hyzs.onekeyhelp.module.record.bean.RecordLvBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.AudioRecorder2Mp3Util;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.GIFview;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeRecordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack, mImage;
    private TextView mTitle, mPressBtn;
    private GIFview mGif;
    private ListView mLv;
    private static final int AUDIO_PHONE_STATE = 100;
    float y;    //手指上滑取消时的基准点击位置
    boolean isSend, isTimeOut;   //是否发送消息的标志位及发送时间是否超过规定时间
    private boolean canClean = false;
    AudioRecorder2Mp3Util util;
    private MyResuceMusicBean musicBean;
    private HomeRecordLvAdapter adapter;
    private List<RecordLvBean.ServiceClassBean> list;
    private Map<String, String> param;
    long startTime, endTime;
    //超时后自动发出消息
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isTimeOut = true;
            mGif.setVisibility(View.GONE);
            mImage.setVisibility(View.GONE);
            mPressBtn.setText("按住说话");
            mPressBtn.setBackground(ContextCompat.getDrawable(HomeRecordActivity.this, R.drawable.shape_6_boder9_solidf4));
            stopRecord();
            getmp3();
            ToastUtils.showShort(HomeRecordActivity.this, "发送语音长度不能超过20秒，语音已发出");
        }
    };

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mPressBtn = (TextView) findViewById(R.id.activity_home_record_btn);
        mGif = (GIFview) findViewById(R.id.activity_home_record_gif);
        mImage = (ImageView) findViewById(R.id.activity_home_record_img);
        mLv = (ListView) findViewById(R.id.activity_home_record_lv);
    }

    @Override
    protected void initView() {
        mTitle.setText("语音记账本");
        mGif.setMovieResource(R.mipmap.huatong1);
        list = new ArrayList<>();
        adapter = new HomeRecordLvAdapter(this, list);
        mLv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mPressBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                startRecord();
                            }
                        }).start();
                        handler.sendEmptyMessageDelayed(1, 20000);
                        isSend = true;
                        isTimeOut = false;
                        y = event.getY();
                        mGif.setVisibility(View.VISIBLE);
                        mPressBtn.setText("松开结束");
                        mPressBtn.setBackground(ContextCompat.getDrawable(HomeRecordActivity.this, R.drawable.shape_6_boder9_solidd));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (y - event.getY() > 100) {
                            isSend = false;
                            mGif.setVisibility(View.GONE);
                            mImage.setVisibility(View.VISIBLE);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isTimeOut) {
                            mGif.setVisibility(View.GONE);
                            mImage.setVisibility(View.GONE);
                            mPressBtn.setText("按住说话");
                            mPressBtn.setBackground(ContextCompat.getDrawable(HomeRecordActivity.this, R.drawable.shape_6_boder9_solidf4));
                            stopRecord();
                            handler.removeMessages(1);
                            if (isSend) {
                                if ((endTime - startTime) / 1000 < 1) {
                                    ToastSingleUtil.showToast(HomeRecordActivity.this,"说话时间太短");
                                    return true;
                                }
                                getmp3();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_record;
    }

    @Override
    protected void initData() {
        param = new ArrayMap<>();
        getAudioRecordList();
        checkPermission();
    }

    private void getAudioRecordList() {
        param.put("action", "Get_VoiceAccountingList");
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("ls_type", "0");  //0  记账  1记事
        NetRequest.ParamPostRequest(PortUtil.AudioRecordList, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                RecordLvBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), RecordLvBean.class);
                list.clear();
                list.addAll(bean.getService_class());
                adapter.notifyDataSetChanged();
                mLv.setSelection(mLv.getBottom());
            }
        });
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}
                        , AUDIO_PHONE_STATE);
            } else {
                // 获取到权限，作相应处理
            }
        } else {
            // 获取到权限，作相应处理
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AUDIO_PHONE_STATE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MyApplication.getAppContext(), "没有麦克风权限", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }
        }
    }

    /**
     * 开始录音
     */
    public void startRecord() {
        if (util == null) {
            util = new AudioRecorder2Mp3Util(null, "/sdcard/test_audio_recorder_for_mp3.raw", "/sdcard/test_audio_recorder_for_mp3.mp3");
        }
        if (canClean) {
            util.cleanFile(AudioRecorder2Mp3Util.MP3 | AudioRecorder2Mp3Util.RAW);
        }
        startTime = System.currentTimeMillis();
        util.startRecording();
        canClean = true;
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        if (util == null) {
            return;
        }
        endTime = System.currentTimeMillis();
        util.stopRecordingAndConvertFile();
        util.cleanFile(AudioRecorder2Mp3Util.RAW);
        // 如果要关闭可以
        util.close();
        util = null;
    }

    /**
     * mp3 转化
     */
    private void getmp3() {
        String uploadFile = "/sdcard/test_audio_recorder_for_mp3.mp3";
        try {
            NetRequest.mp3Utils(MySharedPreferences.getUserId(this), uploadFile, new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    musicBean = new Gson().fromJson(data, MyResuceMusicBean.class);
                    if ("10000".equals(musicBean.getCode())) {
                        param.put("action", "Get_VoiceAccountingSave");
                        param.put("currId", MySharedPreferences.getUserId(HomeRecordActivity.this));
                        param.put("ls_type", "0");  //0  记账  1记事
                        param.put("ls_time", (endTime - startTime) / 1000 + "");
                        param.put("ls_path", musicBean.getUrl());
                        NetRequest.ParamPostRequest(PortUtil.AudioRecordList, param, new NetRequest.getDataCallback() {
                            @Override
                            public void getData(String data) {
                                RecordLvBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), RecordLvBean.class);
                                if ("10000".equals(bean.getCode())) {
                                    getAudioRecordList();
                                }
                            }
                        });
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Thread(new Runnable() {
            @Override
            public void run() {
                stopRecord();
            }
        }).start();
    }
}
