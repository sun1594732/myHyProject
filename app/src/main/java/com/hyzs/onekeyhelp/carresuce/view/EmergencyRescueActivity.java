package com.hyzs.onekeyhelp.carresuce.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.carresuce.bean.CarInfoBean;
import com.hyzs.onekeyhelp.carresuce.bean.EmergencyRescueBean;
import com.hyzs.onekeyhelp.carresuce.bean.MyResuceMusicBean;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.home.adapter.GridAdapter;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.BitmapUtil;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MusicPlayerUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.album.Album;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmergencyRescueActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "EmergencyRescueActivity";
    private ImageView mBack, iv_subtract, iv_add, mNoneView;
    private RecyclerView mRecyclerView;
    private GridAdapter mGridAdapter;
    private TextView toolbar_title, confirm_help, car_type, my_resuce_details_tv_address;
    private List<String> pathList;
    private EditText et_number, et_context, et_address;
    private int number = 0;
    private StringBuilder sb;
    private String mp3Code;
    private String mp3CodeUrl;
    private double latitude;
    private double longitude;
    private MusicPlayerUtils musicPlayerUtils;
    ByteArrayInputStream bais;
    private PopupWindow mPopupWindow;
    private Dialog dialog;
    private Dialog PragDialog;
    private CarInfoBean infoBean;
    private LinearLayout ll_select_car;
    private String CarId;
    private EmergencyRescueBean bean;
    private LinearLayout ll;
    private PopupWindow pop;
    //重新启动发布按钮
    Handler handler;

    @Override
    protected void assignView() {
        sb = new StringBuilder();
        musicPlayerUtils = new MusicPlayerUtils(this);
        PragDialog = ProgressDialog.createProgressLoading(this);
        latitude = getIntent().getDoubleExtra("latitude", 0.00);
        longitude = getIntent().getDoubleExtra("longitude", 0.00);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        iv_subtract = (ImageView) findViewById(R.id.emergency_iv_subtract);
        mNoneView = (ImageView) findViewById(R.id.activity_emergency_rescue_nonePic);
        iv_add = (ImageView) findViewById(R.id.emergency_iv_add);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        confirm_help = (TextView) findViewById(R.id.emergency_tv_confirm_help);
        my_resuce_details_tv_address = (TextView) findViewById(R.id.my_resuce_details_tv_address);
        car_type = (TextView) findViewById(R.id.my_resuce_details_tv_car_type);
        et_number = (EditText) findViewById(R.id.emergency_et_money);
        et_context = (EditText) findViewById(R.id.my_resuce_details_tv_context);
        ll_select_car = (LinearLayout) findViewById(R.id.ll_select_car);
        et_address = (EditText) findViewById(R.id.my_resuce_details_tv_editText);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_emergency_rescue_recycler);
        ll = (LinearLayout) findViewById(R.id.activity_emergency_rescue);
        my_resuce_details_tv_address.setText(getIntent().getStringExtra("address"));
        pop = HelpDialog.createDialog(EmergencyRescueActivity.this, EmergencyRescueActivity.this, "发布成功", R.mipmap.bingo);

    }

    private void initPop() {
        handler = new MyHandler(this);
        View popupView = getLayoutInflater().inflate(R.layout.help_pop_layout, null);
        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAtLocation(mRecyclerView, Gravity.CENTER, 0, 0);
        popupView.findViewById(R.id.help_pop_confirm).setOnClickListener(this);


    }


    @Override
    protected void initView() {
        mBack.setOnClickListener(this);
        iv_subtract.setOnClickListener(this);
        confirm_help.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        mNoneView.setOnClickListener(this);
        ll_select_car.setOnClickListener(this);
        toolbar_title.setText("紧急救援");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGridAdapter = new GridAdapter(EmergencyRescueActivity.this);
        mRecyclerView.setAdapter(mGridAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_emergency_rescue;
    }

    @Override
    protected void initData() {
        getmp3();
        getCarInfo();
    }

    /**
     * 初始化Dialog
     */
    private void initDialog() {
        dialog = new AlertDialog.Builder(this).create();
        View DialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog_car_select, null);
        ListView list = (ListView) DialogView.findViewById(R.id.list_car);
        list.setAdapter(new MAdapter());
        dialog.show();
        dialog.getWindow().setContentView(DialogView);
        dialog.dismiss();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CarId = infoBean.getMyVehicleList().get(i).getVehicleID() + "";
                dialog.dismiss();
                car_type.setText(infoBean.getMyVehicleList().get(i).getUesrname());
            }
        });

    }

    private class MAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return infoBean.getMyVehicleList().size();
        }

        @Override
        public Object getItem(int i) {
            return infoBean.getMyVehicleList().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(EmergencyRescueActivity.this, R.layout.car_select_item, null);

            TextView tv_name = (TextView) view.findViewById(R.id.tv_select_name);

            tv_name.setText(infoBean.getMyVehicleList().get(i).getUesrname());

            return view;
        }
    }

    /**
     * 获取车辆信息接口
     */
    private void getCarInfo() {
        Map<String, String> paramCar = new HashMap<>();
        paramCar.put("currId", MySharedPreferences.getUserId(this));
        paramCar.put("pageSize", "10");
        paramCar.put("pageIndex", "1");
        NetRequest.CommonUseListRequestMy(PortUtil.CarListDetails, paramCar, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                infoBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CarInfoBean.class);
                if (infoBean.getMyVehicleList().size() == 0) {
                    Toast.makeText(EmergencyRescueActivity.this, "请先设置车辆数据！", Toast.LENGTH_SHORT).show();
                } else {
                    initDialog();
                }

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.emergency_iv_subtract:
                if (number == 0) {
                    return;
                } else {
                    --number;
                }
                et_number.setText(number + "");
                break;
            case R.id.emergency_iv_add:
                ++number;
                et_number.setText(number + "");
                break;
            case R.id.activity_emergency_rescue_nonePic:
                Album.startAlbum(EmergencyRescueActivity.this, 100, 8, ContextCompat.getColor(this, R.color.color_1ccd9b), ContextCompat.getColor(this, R.color.color_1ccd9b));
                break;
            case R.id.emergency_tv_confirm_help:
                confirm_help.setClickable(false);
                handler.sendEmptyMessageDelayed(0, 3000);
                Toast.makeText(this, "正在上传，请稍后..", Toast.LENGTH_SHORT).show();
                PragDialog.show();

                if (pathList != null) {
                    for (int i = 0; i < pathList.size(); i++) {
                        getPicPath(pathList.get(i), i);
                    }
                } else publishRequest();
                break;

            case R.id.ll_select_car:
                if (infoBean.getMyVehicleList().size() == 0) {
                    Toast.makeText(EmergencyRescueActivity.this, "请先增加车辆！", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                }
                break;

            case R.id.help_pop_confirm:
                if (flag) {
                    finish();
                }
                mPopupWindow.dismiss();
                break;
        }
    }

    private void getPicPath(String path, final int position) {
        byte[] datas = BitmapUtil.getImage(path);
        String encodeString = android.util.Base64.encodeToString(datas, Base64.DEFAULT);
        Map<String, String> param = new HashMap<>();
        param.put("currId", MySharedPreferences.getInstance(this).getString("uid"));
        param.put("FileBase64", encodeString);
        param.put("FileType", "0");
        NetRequest.PictureUpRequest(param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                PublishPicBean bean;
                try {
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PublishPicBean.class);
                } catch (Exception e) {
                    Toast.makeText(EmergencyRescueActivity.this, "获取数据异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (position == pathList.size() - 1) {
                    sb.append("{" + bean.getUrl() + "}");
                    publishRequest();
                } else {
                    sb.append("{" + bean.getUrl() + "},");
                }
            }
        });
    }

    private boolean flag = false;

    private void publishRequest() {
        flag = false;
        Map<String, String> param1 = new HashMap<>();
        param1.put("currId", MySharedPreferences.getUserId(EmergencyRescueActivity.this));
        if (TextUtils.isEmpty(CarId)) {
            CarId = "0";
        }
        param1.put("VR_VehicleId", CarId + "");
        param1.put("VR_LocationDesc", my_resuce_details_tv_address.getText().toString() + et_address.getText().toString());//位置描述
        param1.put("VR_Location", longitude + "," + latitude);//坐标经纬度

        param1.put("VR_Content", et_context.getText().toString());//内容

        param1.put("VR_Voice", musicBean.getUrl());//语音

        if ("".equals(et_number.getText().toString())) {
            param1.put("VR_PayMoney", "0");//打赏金额
        } else {
            param1.put("VR_PayMoney", et_number.getText().toString());//打赏金额
        }
        param1.put("VR_ID", TextUtils.isEmpty(bean.getIdentity() + "") ? "0" : bean.getIdentity() + "");//使用ID 追加救援信息 ,没有可填写0作为值
        param1.put("VR_ImageGroup", sb.toString());  //图片组
        param1.put("VR_Type", "0");  //类型
        NetRequest.rescuePublishRequest(param1, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                EmergencyRescueBean bean;
                try {
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), EmergencyRescueBean.class);
                } catch (Exception e) {
                    Toast.makeText(EmergencyRescueActivity.this, "获取数据异常.", Toast.LENGTH_SHORT).show();
                    PragDialog.dismiss();
                    return;
                }
                if ("10000".equals(bean.getCode())) {
                    PragDialog.dismiss();
                    flag = true;
                    pop.showAtLocation(ll, Gravity.CENTER, 0, 0);
                } else {
                    ToastUtils.showShort(EmergencyRescueActivity.this, bean.getMessage() + "");
                    PragDialog.dismiss();
                }
            }
        }, new NetRequest.getMessageFailCallback1() {
            @Override
            public void getData(String data) {
                ToastUtils.showShort(EmergencyRescueActivity.this, UrlDecodeUtil.urlCode(data));
            }
        });
    }

    private void updateRescueInfo() {
        Map<String, String> param1 = new HashMap<>();
        param1.put("currId", MySharedPreferences.getUserId(EmergencyRescueActivity.this));
        param1.put("VR_VehicleId", 0 + "");
        param1.put("VR_LocationDesc", getIntent().getStringExtra("address"));//位置描述
        param1.put("VR_Location", longitude + "," + latitude);//坐标经纬度
        param1.put("VR_Content", "");//内容
        param1.put("VR_Voice", musicBean.getUrl());//语音
        param1.put("VR_PayMoney", "0");//打赏金额
        param1.put("VR_ID", "0");//使用ID 追加救援信息 ,没有可填写0作为值
        param1.put("VR_ImageGroup", "");  //图片组
        param1.put("VR_Type", "0");  //类型
        NetRequest.rescuePublishRequest(param1, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), EmergencyRescueBean.class);
                } catch (Exception e) {
                    Toast.makeText(EmergencyRescueActivity.this, "发布数据异常.", Toast.LENGTH_SHORT).show();
                    PragDialog.dismiss();
                    return;
                }
                if ("10000".equals(bean.getCode())) {
                    initPop();
                } else {
                    Toast.makeText(EmergencyRescueActivity.this, bean.getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            }
        }, new NetRequest.getMessageFailCallback1() {
            @Override
            public void getData(String data) {
                Toast.makeText(EmergencyRescueActivity.this, UrlDecodeUtil.urlCode(data), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) { // 成功选择了照片。
                // 选择好了照片后，调用这个方法解析照片路径的List。
                if (pathList == null) {
                    pathList = Album.parseResult(data);
                } else {
                    if (pathList.size() + Album.parseResult(data).size() > 8) {
                        ToastUtils.showShort(EmergencyRescueActivity.this, "选择上传的图片不能大于8张，请删除后再选择");
                        return;
                    }
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(EmergencyRescueActivity.this, "取消上传");
            }
        }
    }

    private void handleSelectImage(List<String> pathList) {
        mGridAdapter.notifyDataSetChanged(pathList);
        if (pathList == null || pathList.size() == 0) {
            mNoneView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mNoneView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    MyResuceMusicBean musicBean;

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
                    mp3CodeUrl = musicBean.getUrl();
                    updateRescueInfo();

                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    private boolean bls = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !bls) {
            bls = true;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    //重新启动发布按钮
    private static class MyHandler extends Handler {
        private final WeakReference<EmergencyRescueActivity> mActivity;

        public MyHandler(EmergencyRescueActivity activity) {
            mActivity = new WeakReference<EmergencyRescueActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
            mActivity.get().confirm_help.setClickable(true);
        }
    }
}



