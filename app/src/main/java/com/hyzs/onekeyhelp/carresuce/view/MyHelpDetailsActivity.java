package com.hyzs.onekeyhelp.carresuce.view;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.carresuce.adapter.HelpPeopleSumAdapter;
import com.hyzs.onekeyhelp.carresuce.bean.MyHelpDetailsBean;
import com.hyzs.onekeyhelp.carresuce.bean.MyResuceOkBean;
import com.hyzs.onekeyhelp.lifehelp.bean.HelpMeDetailsCommonBean;
import com.hyzs.onekeyhelp.widget.ListViewForScrollView;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.DateFormatUtils;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;
import com.hyzs.onekeyhelp.util.JointGetUrl;
import com.hyzs.onekeyhelp.util.MusicPlayerUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyHelpDetailsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack, iv_identity, chat, phone;
    private RoundImageView iv_face, pirc1, pirc2, pirc3, pirc4, pirc5, pirc6, pirc7, pirc8;
    private TextView toolbar_title, tv_name, tv_zheng, tv_state, tv_context, tv_address, tv_time, tv_car_type, tv_car_number, tv_thank_money, tv_confirm_help;
    private LinearLayout ll_one, ll_two, ll_play_voice;
    private SpannableString spannable;
    private RelativeLayout mEmptyView;
    private OkHttpClient okHttpClient;
    private Request request;
    private HashMap hms;
    private String VR_ID;
    private MyHelpDetailsBean detailsBean;
    private MusicPlayerUtils playerUtils;
    private MyResuceOkBean okBean;
    private String selectIsHelpUrl = PortUtil.SetHelpRescueStatus;
    private String HelpDetailsUrl = PortUtil.CarHelpDetailsUrl;
    private Dialog mDialog;
    private ListViewForScrollView listViewForScrollView;
    private HelpMeDetailsCommonBean bean;
    private HelpPeopleSumAdapter adapter;
    private ScrollView scrollView;

    @Override
    protected void assignView() {
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        listViewForScrollView = (ListViewForScrollView) findViewById(R.id.listViewForScrollView);
        chat = (ImageView) findViewById(R.id.help_detail_chat);
        phone = (ImageView) findViewById(R.id.help_detail_phone);
        okHttpClient = new OkHttpClient();
        VR_ID = getIntent().getStringExtra("VR_ID");
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        playerUtils = new MusicPlayerUtils(this);
        mDialog = com.hyzs.onekeyhelp.util.ProgressDialog.createProgressLoading(this);
        iv_face = (RoundImageView) findViewById(R.id.my_help_details_iv_face);
        iv_identity = (ImageView) findViewById(R.id.my_help_details_iv_identity);
        pirc1 = (RoundImageView) findViewById(R.id.my_help_details_pirc1);
        pirc2 = (RoundImageView) findViewById(R.id.my_help_details_pirc2);
        pirc3 = (RoundImageView) findViewById(R.id.my_help_details_pirc3);
        pirc4 = (RoundImageView) findViewById(R.id.my_help_details_pirc4);
        pirc5 = (RoundImageView) findViewById(R.id.my_help_details_pirc5);
        pirc6 = (RoundImageView) findViewById(R.id.my_help_details_pirc6);
        pirc7 = (RoundImageView) findViewById(R.id.my_help_details_pirc7);
        pirc8 = (RoundImageView) findViewById(R.id.my_help_details_pirc8);
        tv_name = (TextView) findViewById(R.id.my_help_details_tv_name);
        tv_zheng = (TextView) findViewById(R.id.my_help_details_tv_zheng);
        tv_state = (TextView) findViewById(R.id.my_help_details_tv_state);
        tv_context = (TextView) findViewById(R.id.my_help_details_tv_context);
        tv_address = (TextView) findViewById(R.id.my_help_details_tv_address);
        tv_time = (TextView) findViewById(R.id.my_help_details_tv_time);
        tv_car_type = (TextView) findViewById(R.id.my_help_details_tv_car_type);
        tv_car_number = (TextView) findViewById(R.id.my_help_details_tv_car_number);
        tv_thank_money = (TextView) findViewById(R.id.my_help_details_tv_thank_money);
        tv_confirm_help = (TextView) findViewById(R.id.my_help_details_tv_confirm_help);
        mEmptyView = (RelativeLayout) findViewById(R.id.layout_empty);
        ll_one = (LinearLayout) findViewById(R.id.my_help_details_ll_one);
        ll_two = (LinearLayout) findViewById(R.id.my_help_details_ll_two);
        ll_play_voice = (LinearLayout) findViewById(R.id.my_help_details_ll_play_voice);
        listViewForScrollView.setFocusable(false);
    }

    @Override
    protected void initView() {
        toolbar_title.setText("我的帮助-详情");
    }

    @Override
    protected void initListener() {
        chat.setOnClickListener(this);
        phone.setOnClickListener(this);
        mBack.setOnClickListener(this);
        ll_play_voice.setOnClickListener(this);
        tv_confirm_help.setOnClickListener(this);
        iv_face.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_help_details;
    }

    @Override
    protected void initData() {
        mDialog.show();
        hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this));
        hms.put("VR_ID", VR_ID);
        requestMyData(hms, HelpDetailsUrl, 1);
        requestListData();
    }

    private void requestListData() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("seekHelpID", getIntent().getStringExtra("VR_ID"));
        map.put("seekType", "1");
        NetRequest.ParamPostRequest(PortUtil.ComplateSeekHelpUserListCommon, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), HelpMeDetailsCommonBean.class);
                initListView();
            }
        });
    }

    private void initListView() {
        adapter = new HelpPeopleSumAdapter(this, bean.getComplateSeekHelpUserListCommon());
        listViewForScrollView.setAdapter(adapter);
        scrollView.scrollTo(0, 0);
    }

    private int state = 4;
    private static final int BAIDU_READ_PHONE_STATE = 100;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.help_detail_chat:
                if (!MySharedPreferences.isLogin(this)) {
                    Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                MySharedPreferences.getInstance(this).setString("Img", detailsBean.getMyHelpVehicleRescueDetails().getAvatar());
                Intent intent = new Intent();
                intent.putExtra("one", detailsBean.getMyHelpVehicleRescueDetails().getSeekHelpUserId() + "");
                intent.putExtra("userName", detailsBean.getMyHelpVehicleRescueDetails().getNickName());
                intent.setClass(this, IntentChatActivity.class);
                startActivity(intent);
                break;
            case R.id.help_detail_phone:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}
                                , BAIDU_READ_PHONE_STATE);
                    } else {
                        if (!TextUtils.isEmpty(detailsBean.getMyHelpVehicleRescueDetails().getPhone())) {
                            intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + detailsBean.getMyHelpVehicleRescueDetails().getPhone());
                            intent.setData(data);
                            startActivity(intent);
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(detailsBean.getMyHelpVehicleRescueDetails().getPhone())) {
                        intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + detailsBean.getMyHelpVehicleRescueDetails().getPhone());
                        intent.setData(data);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.my_help_details_ll_play_voice:
                playerUtils = new MusicPlayerUtils(this);
                if (playerUtils.mediaPlayer.isPlaying()) {
                    return;
                }
                if (!TextUtils.isEmpty(detailsBean.getMyHelpVehicleRescueDetails().getSeek_Voice())) {
                    playerUtils.playUrl(detailsBean.getMyHelpVehicleRescueDetails().getSeek_Voice());
                    ll_play_voice.setBackground(ContextCompat.getDrawable(MyHelpDetailsActivity.this, R.drawable.shape_red_solid));
                    playerUtils.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            ll_play_voice.setBackground(ContextCompat.getDrawable(MyHelpDetailsActivity.this, R.drawable.shape_1ccd9b_6_soild));
                        }
                    });
                } else {
                    Toast.makeText(this, "播放语音失败！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.my_help_details_tv_confirm_help:  //确认援助
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                mDialog.show();
                hms.clear();

                if (tv_confirm_help.getText().toString().equals("确认救援")) {
                    state = 2;
                } else if (tv_confirm_help.getText().toString().equals("已完成")) {
                    Toast.makeText(this, "已经帮助过啦", Toast.LENGTH_SHORT).show();
                    return;
                }

                hms.put("currId", MySharedPreferences.getUserId(this));
                hms.put("status", state + "");
                Log.e("state", detailsBean.getMyHelpVehicleRescueDetails().getID() + "");
                hms.put("HR_ID", detailsBean.getMyHelpVehicleRescueDetails().getID() + "");

                requestMyData(hms, selectIsHelpUrl, 2);

                break;
            case R.id.toolbar_back:  //返回
                finish();
                break;
            case R.id.my_help_details_iv_face:
                if (!MySharedPreferences.isLogin(this)) {
                    ToastUtils.showShort(this, "请先登录.");
                    return;
                }
                Intent intent1 = new Intent(this, MineHomeActivity.class);
                intent1.putExtra("targetUserId", detailsBean.getMyHelpVehicleRescueDetails().getSeekHelpUserId() + "");
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                if (!TextUtils.isEmpty(detailsBean.getMyHelpVehicleRescueDetails().getPhone())) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + detailsBean.getMyHelpVehicleRescueDetails().getPhone());
                    intent.setData(data);
                    startActivity(intent);
                }
                break;
            default:
                break;

        }
    }

    /**
     * 联网请求数据
     */
    private void requestMyData(HashMap hms, String MWEBUrl, final int type) {

        MWEBUrl = JointGetUrl.getUrl(MWEBUrl, hms);

        request = new Request.Builder().url(MWEBUrl).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String res = response.body().string();
                try {
                    if (type == 1) {
                        detailsBean = new Gson().fromJson(UrlDecodeUtil.urlCode(res), MyHelpDetailsBean.class);
                    } else {
                        okBean = new Gson().fromJson(UrlDecodeUtil.urlCode(res), MyResuceOkBean.class);
                    }
                } catch (Exception e) {
                    handler.sendEmptyMessage(3);
                    return;
                }
                handler.sendEmptyMessage(type);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    setImage();
                    setParame();
                    mDialog.dismiss();
                    mEmptyView.setVisibility(View.GONE);
                    break;
                case 2:
                    if (okBean.getCode().equals("10000")) {
                        Toast.makeText(MyHelpDetailsActivity.this, "帮助成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(MyHelpDetailsActivity.this, okBean.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
                    break;
                case 3:
                    Toast.makeText(MyHelpDetailsActivity.this, "数据获取异常", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                    break;
            }
        }

    };

    /**
     * 设置参数
     */
    private void setParame() {
        tv_name.setText(detailsBean.getMyHelpVehicleRescueDetails().getNickName());
        tv_context.setText(detailsBean.getMyHelpVehicleRescueDetails().getSeek_Reminder());
        tv_address.setText(detailsBean.getMyHelpVehicleRescueDetails().getGPSDesc());
        tv_time.setText(DateFormatUtils.Time2DateForAll((detailsBean.getMyHelpVehicleRescueDetails().getSeek_Time() * 1000) + ""));
        tv_car_type.setText("");
        tv_car_number.setText(detailsBean.getMyHelpVehicleRescueDetails().getVehicleCardNum());
        ImageLoadUtils.setImageForUrl(detailsBean.getMyHelpVehicleRescueDetails().getAvatar(), iv_face);
        if (detailsBean.getMyHelpVehicleRescueDetails().getRNA() == 1) {//判断是否通过实名认证
            iv_identity.setVisibility(View.VISIBLE);
        } else {
            iv_identity.setVisibility(View.GONE);
        }
        if (detailsBean.getMyHelpVehicleRescueDetails().getNCCS() == 1) {//判断是否通过居委会认证
            tv_zheng.setVisibility(View.VISIBLE);

        } else {
            tv_zheng.setVisibility(View.GONE);
        }


        tv_car_type.setText(detailsBean.getMyHelpVehicleRescueDetails().getVehicleName() + "");
        switch (detailsBean.getMyHelpVehicleRescueDetails().getSeek_State()) {
            case 1:
                tv_state.setText("等待救援");
                tv_state.setTextColor(Color.parseColor("#f7221d"));

                break;
            case 2:
                tv_state.setText("救援已完成");
                tv_state.setTextColor(Color.parseColor("#15a61f"));
                break;
            case 3:
                tv_state.setText("救援被终止");
                tv_state.setTextColor(Color.parseColor("#999999"));

                break;
            case 4:
                tv_state.setText("救援被终止");
                tv_state.setTextColor(Color.parseColor("#999999"));
                break;
            default:
                tv_state.setText("请稍后 ....");
                break;
        }

        if (detailsBean.getMyHelpVehicleRescueDetails().getHR_RescueState() <= 1) {
            //帮助中
            tv_confirm_help.setVisibility(View.VISIBLE);
        } else {
            tv_confirm_help.setVisibility(View.GONE);
        }

        //Speen
        spannable = new SpannableString("酬金" + detailsBean.getMyHelpVehicleRescueDetails().getReward() + "元");
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#ec2b2b")), 2, spannable.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_thank_money.setText(spannable);
    }


    /**
     * 设置图片
     */
    private void setImage() {

        Log.e("ImageGroup", detailsBean.getImageGroup().size() + "");
        switch (detailsBean.getImageGroup().size()) {
            case 0:
                ll_one.setVisibility(View.GONE);
                ll_two.setVisibility(View.GONE);
                break;
            case 1:

                ll_one.setVisibility(View.VISIBLE);
                ll_two.setVisibility(View.GONE);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(0).getImage_url(), pirc1);
                pirc2.setVisibility(View.GONE);
                pirc3.setVisibility(View.GONE);
                pirc4.setVisibility(View.GONE);
                break;
            case 2:

                ll_one.setVisibility(View.VISIBLE);
                ll_two.setVisibility(View.GONE);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(0).getImage_url(), pirc1);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(1).getImage_url(), pirc2);

                pirc3.setVisibility(View.GONE);
                pirc4.setVisibility(View.GONE);
                break;
            case 3:

                ll_one.setVisibility(View.VISIBLE);
                ll_two.setVisibility(View.GONE);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(0).getImage_url(), pirc1);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(1).getImage_url(), pirc2);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(2).getImage_url(), pirc3);

                pirc4.setVisibility(View.GONE);
                break;
            case 4:

                ll_one.setVisibility(View.VISIBLE);
                ll_two.setVisibility(View.GONE);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(0).getImage_url(), pirc1);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(1).getImage_url(), pirc2);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(2).getImage_url(), pirc3);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(3).getImage_url(), pirc4);
                break;
            case 5:

                ll_one.setVisibility(View.VISIBLE);
                ll_two.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(0).getImage_url(), pirc1);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(1).getImage_url(), pirc2);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(2).getImage_url(), pirc3);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(3).getImage_url(), pirc4);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(4).getImage_url(), pirc5);
                pirc6.setVisibility(View.GONE);
                pirc7.setVisibility(View.GONE);
                pirc8.setVisibility(View.GONE);
                break;
            case 6:

                ll_one.setVisibility(View.VISIBLE);
                ll_two.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(0).getImage_url(), pirc1);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(1).getImage_url(), pirc2);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(2).getImage_url(), pirc3);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(3).getImage_url(), pirc4);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(4).getImage_url(), pirc5);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(5).getImage_url(), pirc6);

                pirc7.setVisibility(View.GONE);
                pirc8.setVisibility(View.GONE);
                break;
            case 7:

                ll_one.setVisibility(View.VISIBLE);
                ll_two.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(0).getImage_url(), pirc1);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(1).getImage_url(), pirc2);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(2).getImage_url(), pirc3);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(3).getImage_url(), pirc4);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(4).getImage_url(), pirc5);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(5).getImage_url(), pirc6);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(6).getImage_url(), pirc7);

                pirc8.setVisibility(View.GONE);
                break;
            case 8:

                ll_one.setVisibility(View.VISIBLE);
                ll_two.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(0).getImage_url(), pirc1);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(1).getImage_url(), pirc2);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(2).getImage_url(), pirc3);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(3).getImage_url(), pirc4);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(4).getImage_url(), pirc5);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(5).getImage_url(), pirc6);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(6).getImage_url(), pirc7);
                ImageLoadUtils.setImageForUrl(detailsBean.getImageGroup().get(7).getImage_url(), pirc8);
                break;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerUtils.stop();
    }
}
