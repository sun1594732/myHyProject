package com.hyzs.onekeyhelp.family.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.MyApplication;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.carresuce.bean.MyResuceMusicBean;
import com.hyzs.onekeyhelp.family.adapter.FamilyRecordAdapter;
import com.hyzs.onekeyhelp.module.record.activity.HomeRecordActivity;
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
import java.util.List;
import java.util.Map;

/**
 * 家庭记事
 */
public class FamilyRecordFragment extends Fragment {
    private GIFview mGif;
    private TextView mPressBtn;
    private ImageView mImage;
    private ListView mLv;
    private static final int AUDIO_PHONE_STATE = 100;
    float y;    //手指上滑取消时的基准点击位置
    boolean isSend, isTimeOut;   //是否发送消息的标志位及发送时间是否超过规定时间
    private boolean canClean = false;
    AudioRecorder2Mp3Util util;
    private MyResuceMusicBean musicBean;
    private FamilyRecordAdapter adapter;
    private List<RecordLvBean.ServiceClassBean> list;
    private List<RecordLvBean.UserinfosBean> infoList;
    private Map<String, String> param;
    long startTime, endTime;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isTimeOut = true;
            mGif.setVisibility(View.GONE);
            mImage.setVisibility(View.GONE);
            mPressBtn.setText("按住说话");
            mPressBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_6_boder9_solidf4));
            stopRecord();
            getmp3();
            ToastUtils.showShort(getActivity(), "发送语音长度不能超过20秒，语音已发出");
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_record, null);
        assignView(view);
        initData();
        initView();
        initListener();
        return view;
    }

    private void initListener() {
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
                        mPressBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_6_boder9_solidd));
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
                            mPressBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_6_boder9_solidf4));
                            stopRecord();
                            handler.removeMessages(1);
                            if (isSend) {
                                if ((endTime - startTime) / 1000 < 1) {
                                    ToastSingleUtil.showToast(getActivity(),"说话时间太短");
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

    private void initView() {
        mGif.setMovieResource(R.mipmap.huatong1);
        list = new ArrayList<>();
        infoList = new ArrayList<>();
        adapter = new FamilyRecordAdapter(getActivity(), list, infoList);
        mLv.setAdapter(adapter);
    }

    private void initData() {
        param = new ArrayMap<>();
        getAudioRecordList();
        checkPermission();
    }

    private void assignView(View view) {
        mPressBtn = (TextView) view.findViewById(R.id.activity_home_record_btn);
        mGif = (GIFview) view.findViewById(R.id.activity_home_record_gif);
        mImage = (ImageView) view.findViewById(R.id.activity_home_record_img);
        mLv = (ListView) view.findViewById(R.id.activity_home_record_lv);
    }

    private void getAudioRecordList() {
        param.put("action", "Get_FamilyVoiceAccountingList");
        param.put("currId", MySharedPreferences.getUserId(getActivity()));
        param.put("familyId", getActivity().getIntent().getStringExtra("familyId"));
        NetRequest.ParamPostRequest(PortUtil.AudioRecordList, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                RecordLvBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), RecordLvBean.class);
                list.clear();
                infoList.clear();
                list.addAll(bean.getService_class());
                infoList.addAll(bean.getUserinfos());
                adapter.notifyDataSetChanged();
                mLv.setSelection(mLv.getBottom());
            }
        });
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().getApplicationContext().checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
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
            NetRequest.mp3Utils(MySharedPreferences.getUserId(getActivity()), uploadFile, new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    musicBean = new Gson().fromJson(data, MyResuceMusicBean.class);
                    if ("10000".equals(musicBean.getCode())) {
                        param.put("action", "Get_VoiceAccountingSave");
                        param.put("currId", MySharedPreferences.getUserId(getActivity()));
                        param.put("ls_type", "1");  //0  记账  1记事
                        param.put("ls_time", (endTime - startTime) / 1000 + "");
                        param.put("ls_path", musicBean.getUrl());
                        param.put("familyId", getActivity().getIntent().getStringExtra("familyId"));
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    stopRecord();
                }
            }).start();
        }
    }

}
