package com.hyzs.onekeyhelp.family.movable.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.carresuce.view.EmergencyRescueActivity;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.family.movable.adapter.MovableReleaseRecyclerAdapter;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.BitmapUtil;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.MyTextWatcher;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.wheel.ChangeBirthDialog;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.album.Album;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MovableReleaseActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MovableReleaseActivity";
    private ImageView mBack;
    private ImageView addImg;
    private TextView mTitle;
    private TextView mToolBarRight;
    private LinearLayout start_time_left, start_time_right, end_time_left, end_time_right;
    private TextView sLeft, sRight, eLeft, eRight;
    private RecyclerView mRecyclerView;
    private EditText theme, stroke, location, route, condition, equip, note;
    private Button btn_save;
    private MovableReleaseRecyclerAdapter mGridAdapter;
    private List<String> pathList;
    private StringBuilder sb;
    private String sTheme;
    private String sStroke;
    private String sl;
    private String sr;
    private String el;
    private String er;
    private String sLocation;
    private String sRoute;
    private String sCondition;
    private String sEquip;
    private String sNote;
    private Dialog dialog;

    //重新启动发布按钮
    Handler handler;

    @Override
    protected void assignView() {
        dialog = ProgressDialog.createProgressLoading(this);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        addImg = (ImageView) findViewById(R.id.activity_circle_publish_nonePic);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolBarRight = (TextView) findViewById(R.id.toolbar_right);
        start_time_left = (LinearLayout) findViewById(R.id.activity_movable_release_start_time_left);
        start_time_right = (LinearLayout) findViewById(R.id.activity_movable_release_start_time_right);
        end_time_left = (LinearLayout) findViewById(R.id.activity_movable_release_end_time_left);
        end_time_right = (LinearLayout) findViewById(R.id.activity_movable_release_end_time_right);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_movable_release_recyclerview);
        theme = (EditText) findViewById(R.id.movable_release_theme);
        stroke = (EditText) findViewById(R.id.movable_release_stroke);
        location = (EditText) findViewById(R.id.movable_release_location);
        route = (EditText) findViewById(R.id.movable_release_route);
        condition = (EditText) findViewById(R.id.movable_release_condition);
        equip = (EditText) findViewById(R.id.movable_release_equip);
        note = (EditText) findViewById(R.id.movable_release_note);
        sLeft = (TextView) findViewById(R.id.movable_release_s_left);
        sRight = (TextView) findViewById(R.id.movable_release_s_right);
        eLeft = (TextView) findViewById(R.id.movable_release_e_left);
        eRight = (TextView) findViewById(R.id.movable_release_e_right);
        btn_save = (Button) findViewById(R.id.movable_release_save);
    }

    @Override
    protected void initView() {
        handler = new MyHandler(this);
        sb = new StringBuilder();
        mTitle.setText(getIntent().getStringExtra("title"));
        mToolBarRight.setText("保存");
//        sLeft.setText(getTime() + " 00:01");
//        sRight.setText(getTime() + " 23:59");
//        eLeft.setText(getTime() + " 00:01");
//        eRight.setText(getTime() + " 23:59");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGridAdapter = new MovableReleaseRecyclerAdapter(MovableReleaseActivity.this);
        mRecyclerView.setAdapter(mGridAdapter);
        equip.addTextChangedListener(new MyTextWatcher(equip));
        note.addTextChangedListener(new MyTextWatcher(note));
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mToolBarRight.setOnClickListener(this);
        start_time_left.setOnClickListener(this);
        start_time_right.setOnClickListener(this);
        end_time_left.setOnClickListener(this);
        end_time_right.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        addImg.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movable_release;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:

                break;
            case R.id.activity_circle_publish_nonePic:
                Album.startAlbum(MovableReleaseActivity.this, 100, 4, ContextCompat.getColor(this, R.color.color_1ccd9b), ContextCompat.getColor(this, R.color.color_1ccd9b));
                break;
            case R.id.activity_movable_release_start_time_left:
                setDate(sLeft);
                break;
            case R.id.activity_movable_release_start_time_right:
                setDate(sRight);
                break;
            case R.id.activity_movable_release_end_time_left:
                setDate(eLeft);
                break;
            case R.id.activity_movable_release_end_time_right:
                setDate(eRight);
                break;
            case R.id.movable_release_save:
                btn_save.setClickable(false);
                handler.sendEmptyMessageDelayed(0, 3000);
                String strTheme = theme.getText().toString();
                String strStroke = stroke.getText().toString();
                String strLocation = location.getText().toString();
                String strRoute = route.getText().toString();
                String strCondition = condition.getText().toString();
                String strEquip = equip.getText().toString();
                String strNote = note.getText().toString();
                if (TextUtils.isEmpty(strTheme) || TextUtils.isEmpty(strStroke)
                        || TextUtils.isEmpty(strLocation) || TextUtils.isEmpty(strRoute)
                        || TextUtils.isEmpty(strCondition) || TextUtils.isEmpty(strEquip)
                        || TextUtils.isEmpty(strNote)) {
                    Toast.makeText(this, "所有信息不能为空，请检查填写！", Toast.LENGTH_LONG).show();
                } else {
                    sTheme = theme.getText().toString();
                    sStroke = stroke.getText().toString();
                    sl = sLeft.getText().toString();
                    sr = sRight.getText().toString();
                    el = eLeft.getText().toString();
                    er = eRight.getText().toString();
                    sLocation = location.getText().toString();
                    sRoute = route.getText().toString();
                    sCondition = condition.getText().toString();
                    sEquip = equip.getText().toString();
                    sNote = note.getText().toString();
                    if (TextUtils.isEmpty(sl) || TextUtils.isEmpty(sr) || TextUtils.isEmpty(el) || TextUtils.isEmpty(er) ) {
                        Toast.makeText(this, "请选择活动时间", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (stringToLong(er) <= stringToLong(el)) {
                        Toast.makeText(this, "报名截止的开始时间必须小于终止时间", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (stringToLong(er) >= stringToLong(sl)) {
                        Toast.makeText(this, "活动开始时间必须在报名结束之后", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (stringToLong(sr) <= stringToLong(sl)) {
                        Toast.makeText(this, "活动开始时间必须小于终止时间", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (MySharedPreferences.isLogin(this)) {
                        dialog.show();
                        if (pathList != null) {
                            for (int i = 0; i < pathList.size(); i++) {
                                getPicPath(pathList.get(i), i);
                            }
                        } else publishRequest();
                    }
                }
                break;
        }
    }

    private void setDate(final TextView view) {
        ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(
                MovableReleaseActivity.this);
        String systemTime = getSystemTime();
        String[] split = systemTime.split("-");
        mChangeBirthDialog.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]));
        mChangeBirthDialog.show();
        mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {
            @Override
            public void onClick(String year, String month, String day, String hour, String minute) {
                String sMonth = month, sDay = day, sHour = hour, sMinute = minute;
                if (1 == month.length()) sMonth = "0" + month;
                if (1 == day.length()) sDay = "0" + day;
                if (1 == hour.length()) sHour = "0" + hour;
                if (1 == minute.length()) sMinute = "0" + minute;

                view.setText(year + "-" + sMonth + "-" + sDay + " " + sHour + ":" + sMinute);
            }
        });
    }

    private String getSystemTime() {
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        return format.format(l);
    }

    private String getTime() {
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(l);
    }

    private long stringToLong(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parse = format.parse(s);
            return parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) { // 成功选择了照片。
                // 选择好了照片后，调用这个方法解析照片路径的List。
                if (pathList == null) {
                    pathList = Album.parseResult(data);
                } else {
                    if (pathList.size() + Album.parseResult(data).size() > 4) {
                        ToastUtils.showShort(MovableReleaseActivity.this, "选择上传的图片不能大于4张，请删除后再选择");
                        return;
                    }
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(MovableReleaseActivity.this, "取消上传");
            }
        }
    }

    private void handleSelectImage(List<String> pathList) {
        mGridAdapter.notifyDataSetChanged(pathList);
        if (pathList == null || pathList.size() == 0) {
            addImg.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            addImg.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MovableReleaseActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MovableReleaseActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }


    private void publishRequest() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("ER_CommunityID", MySharedPreferences.getCommunityId(this));
        map.put("ER_Type", getIntent().getIntExtra("type", 3) + "");
        map.put("ER_Title", sTheme);
        map.put("ER_Content", sStroke);
        map.put("ER_Event_Start", sl);
        map.put("ER_Event_End", sr);
        map.put("ER_Registration_Start", el);
        map.put("ER_Registration_End", er);
        map.put("ER_Route", sRoute);
        map.put("ER_Condition", sCondition);
        map.put("ER_Equipment", sEquip);
        map.put("ER_Attention", sNote);
        map.put("ER_PositionX", "1");
        map.put("ER_PositionY", "1");
        map.put("ER_Address", sLocation);
        map.put("ER_Registration_Min", "5");
        map.put("ER_Registration_Max", "10");
        map.put("ER_ImageGroup", sb == null ? "" : sb.toString());
        NetRequest.ParamPostRequest(PortUtil.PubliActivity, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                String json = UrlDecodeUtil.urlCode(data);
                Log.e(TAG, "getData: "+json );
                try {
                    if ("10000".equals(new JSONObject(json).getString("code"))) {
                        Toast.makeText(MovableReleaseActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(MovableReleaseActivity.this, new JSONObject(json).getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
//                    Toast.makeText(MovableReleaseActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                }
            }
        });
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
                PublishPicBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PublishPicBean.class);
                if (position == pathList.size() - 1) {
                    sb.append("{" + bean.getUrl() + "}");
                    publishRequest();
                } else {
                    sb.append("{" + bean.getUrl() + "},");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    //重新启动发布按钮
    private static class MyHandler extends Handler {
        private final WeakReference<MovableReleaseActivity> mActivity;

        public MyHandler(MovableReleaseActivity activity) {
            mActivity = new WeakReference<MovableReleaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
            mActivity.get().btn_save.setClickable(true);
        }
    }
}
