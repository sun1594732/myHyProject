package com.hyzs.onekeyhelp.carresuce.view;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.ShowImageActivity;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.carresuce.adapter.CarDetailsAdapter;
import com.hyzs.onekeyhelp.carresuce.adapter.CarDetailsEndAdapter;
import com.hyzs.onekeyhelp.carresuce.bean.MyResuceDetailsBean;
import com.hyzs.onekeyhelp.carresuce.bean.MyResuceOkBean;
import com.hyzs.onekeyhelp.lifehelp.bean.HelpMeDetailsCommonBean;
import com.hyzs.onekeyhelp.lifehelp.bean.HelpOtherDetailsHelMeBean;
import com.hyzs.onekeyhelp.widget.ListViewForScrollView;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class MyResuceDetailsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack, iv_identity;
    private RoundImageView iv_face, pirc1, pirc2, pirc3, pirc4, pirc5, pirc6, pirc7, pirc8;
    private TextView toolbar_title, tv_name, tv_zheng, tv_state, tv_context, tv_address, tv_time, tv_car_type, tv_car_number, tv_thank_money, tv_confirm_help, tv_maymoney;
    private LinearLayout ll_one, ll_two, ll_play_voice, activity_my_resuce_details,activity_my_resuce_details_help_sum;
    private SpannableString spannable;
    private String VR_ID;
    private StringBuffer MoneyReword;
    private RelativeLayout mEmptyView;
    private String ResuceDetailsUrl = PortUtil.CarMResuceDetailsHelpUrl;
    private String selectIsHelpUrl = PortUtil.CarSelectIsHelpUrlUrl;
    private String WEBUrl_SeekHelp_me = PortUtil.CarWEBUrlSeekHelpMe;
    private String WEBUrl_payMoney = PortUtil.CarWEBUrlPayMoney;
    private OkHttpClient okHttpClient;
    private Request request;
    private HashMap hms;
    private MyResuceDetailsBean detailsBean;
    private MusicPlayerUtils playerUtils;
    private MyResuceOkBean okBean;
    private HelpOtherDetailsHelMeBean helMeBean;
    private ListViewForScrollView car_help_other_details_lv_get_help;
    private Double Mmoney;
    private double[] ints;
    private int state = 1;
    private Dialog mDialog;
    private boolean isHelpOk;
    private boolean isFreeIssue;
    private HelpMeDetailsCommonBean commonBean;

    @Override
    protected void assignView() {
        VR_ID = getIntent().getStringExtra("VR_ID");
        okHttpClient = new OkHttpClient();
        playerUtils = new MusicPlayerUtils(this);
        MoneyReword = new StringBuffer();
        mDialog = com.hyzs.onekeyhelp.util.ProgressDialog.createProgressLoading(this);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        iv_face = (RoundImageView) findViewById(R.id.my_resuce_details_iv_face);
        iv_identity = (ImageView) findViewById(R.id.my_resuce_details_iv_identity);
        pirc1 = (RoundImageView) findViewById(R.id.my_resuce_details_pirc1);
        pirc2 = (RoundImageView) findViewById(R.id.my_resuce_details_pirc2);
        pirc3 = (RoundImageView) findViewById(R.id.my_resuce_details_pirc3);
        pirc4 = (RoundImageView) findViewById(R.id.my_resuce_details_pirc4);
        pirc5 = (RoundImageView) findViewById(R.id.my_resuce_details_pirc5);
        pirc6 = (RoundImageView) findViewById(R.id.my_resuce_details_pirc6);
        pirc7 = (RoundImageView) findViewById(R.id.my_resuce_details_pirc7);
        pirc8 = (RoundImageView) findViewById(R.id.my_resuce_details_pirc8);
        mEmptyView = (RelativeLayout) findViewById(R.id.layout_empty);
        car_help_other_details_lv_get_help = (ListViewForScrollView) findViewById(R.id.car_help_other_details_lv_get_help);
        tv_name = (TextView) findViewById(R.id.my_resuce_details_tv_name);
        tv_maymoney = (TextView) findViewById(R.id.tv_maymoney);
        tv_zheng = (TextView) findViewById(R.id.my_resuce_details_tv_zheng);
        tv_state = (TextView) findViewById(R.id.my_resuce_details_tv_state);
        tv_context = (TextView) findViewById(R.id.my_resuce_details_tv_context);
        tv_address = (TextView) findViewById(R.id.my_resuce_details_tv_address);
        tv_time = (TextView) findViewById(R.id.my_resuce_details_tv_time);
        tv_car_type = (TextView) findViewById(R.id.my_resuce_details_tv_car_type);
        tv_car_number = (TextView) findViewById(R.id.my_resuce_details_tv_car_number);
        tv_thank_money = (TextView) findViewById(R.id.my_resuce_details_tv_thank_money);
        tv_confirm_help = (TextView) findViewById(R.id.my_resuce_details_tv_confirm_help);
        pirc1.setOnClickListener(this);
        pirc2.setOnClickListener(this);
        pirc3.setOnClickListener(this);
        pirc4.setOnClickListener(this);
        pirc5.setOnClickListener(this);
        pirc6.setOnClickListener(this);
        pirc7.setOnClickListener(this);
        pirc8.setOnClickListener(this);
        ll_one = (LinearLayout) findViewById(R.id.my_resuce_details_ll_one);
        ll_two = (LinearLayout) findViewById(R.id.my_resuce_details_ll_two);
        ll_play_voice = (LinearLayout) findViewById(R.id.my_resuce_details_ll_play_voice);
        activity_my_resuce_details = (LinearLayout) findViewById(R.id.activity_my_resuce_details);
        activity_my_resuce_details_help_sum = (LinearLayout) findViewById(R.id.activity_my_resuce_details_help_sum);
    }


    @Override
    protected void initView() {
        toolbar_title.setText("我的救援-详情");
        playerUtils = new MusicPlayerUtils(this);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        ll_play_voice.setOnClickListener(this);
        tv_confirm_help.setOnClickListener(this);
        iv_face.setOnClickListener(this);
        activity_my_resuce_details_help_sum.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_resuce_details;
    }

    @Override
    protected void initData() {
        mDialog.show();
        hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this));
        hms.put("VR_ID", VR_ID + "");
        requestMyData(hms, ResuceDetailsUrl, 1);

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
                String data = UrlDecodeUtil.urlCode(response.body().string());
                try {
                    switch (type) {
                        case 1:
                            detailsBean = new Gson().fromJson(data, MyResuceDetailsBean.class);
                            Log.e("dawa", data);
                            break;
                        case 2:
                            okBean = new Gson().fromJson(data, MyResuceOkBean.class);
                            break;
                        case 3:
                            helMeBean = new Gson().fromJson(data, HelpOtherDetailsHelMeBean.class);
                            break;
                        case 5:
                            okBean = new Gson().fromJson(data, MyResuceOkBean.class);
                            break;
                        case 9:
                            commonBean = new Gson().fromJson(data, HelpMeDetailsCommonBean.class);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(4);
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
                    setParame();
                    setImage();
                    mDialog.dismiss();
                    mEmptyView.setVisibility(View.GONE);
                    break;
                case 2:
                    if (Double.valueOf(tv_maymoney.getText().toString()) != 0) {
                        Toast.makeText(MyResuceDetailsActivity.this, "请将可分配金额分配至0！", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                        return;
                    }
                    if (okBean.getCode().equals("10000")) {

                        for (int i = 0; i < helMeBean.getTotal(); i++) {
                            MoneyReword.append(helMeBean.getMySeekHelpMoneyAllotList().get(i).getUserid() + ":" + ints[i] + "|");
                        }

                        String encodedData = null;

                        if (MoneyReword.length() == 0) {

                        } else {
                            try {
                                encodedData = URLEncoder.encode(MoneyReword.substring(0, MoneyReword.length() - 1), "UTF-8");
                            } catch (Exception e) {
                                Toast.makeText(MyResuceDetailsActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }

                        hms.clear();
                        hms.put("currId", MySharedPreferences.getUserId(MyResuceDetailsActivity.this));
                        hms.put("seekHelpID", detailsBean.getMyHelpVehicleRescueDetails().getID() + "");
                        hms.put("helpUserIdAndPayMoney", encodedData + "");

                        requestMyData(hms, WEBUrl_payMoney, 5);

                    } else {
                        Toast.makeText(MyResuceDetailsActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();

                    }
                    mDialog.dismiss();


                    break;

                case 3:

                    if (helMeBean == null) {
                        Toast.makeText(MyResuceDetailsActivity.this, "获取信息为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ints = new double[helMeBean.getTotal()];

                    car_help_other_details_lv_get_help.setAdapter(new CarDetailsAdapter(MyResuceDetailsActivity.this, helMeBean.getMySeekHelpMoneyAllotList(), tv_maymoney, Mmoney, ints, isHelpOk));
                    break;

                case 4:
                    Toast.makeText(MyResuceDetailsActivity.this, "数据获取异常", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    if (tv_confirm_help.getText().toString().equals("取消救援")) {
                        Toast.makeText(MyResuceDetailsActivity.this, "取消救援！", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(MyResuceDetailsActivity.this, "援助成功！", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                    finish();
                    break;

                case 9:
                    if (commonBean == null) {
                        Toast.makeText(MyResuceDetailsActivity.this, "获取帮助人为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    car_help_other_details_lv_get_help.setAdapter(new CarDetailsEndAdapter(MyResuceDetailsActivity.this, commonBean.getComplateSeekHelpUserListCommon(), Mmoney, isHelpOk));
                    break;
            }


        }

    };


    /**
     * 设置参数
     */
    private void setParame() {
        Mmoney = Double.valueOf(detailsBean.getMyHelpVehicleRescueDetails().getReward());
        if (detailsBean.getMyHelpVehicleRescueDetails().getSeek_State() <= 1) {
            /** 此处放置请求，防止Mmoney为空*/
            hms.clear();
            hms.put("currId", MySharedPreferences.getUserId(this));
            hms.put("SeekHelpID", VR_ID + "");
            requestMyData(hms, WEBUrl_SeekHelp_me, 3);
        } else {
            hms.clear();
            hms.put("currId", MySharedPreferences.getUserId(this));
            hms.put("seekHelpID", detailsBean.getMyHelpVehicleRescueDetails().getID() + "");
            hms.put("seekType", "1");
            requestMyData(hms, PortUtil.ComplateSeekHelpUserListCommon, 9);
        }
        tv_name.setText(detailsBean.getMyHelpVehicleRescueDetails().getNickName() + "");
        tv_context.setText(detailsBean.getMyHelpVehicleRescueDetails().getContent());
        tv_address.setText(detailsBean.getMyHelpVehicleRescueDetails().getGPSDesc());

        tv_time.setText(DateFormatUtils.Time2DateForAll((detailsBean.getMyHelpVehicleRescueDetails().getSeek_Time() * 1000) + ""));
//        tv_car_type.setText(detailsBean.getMyHelpVehicleRescueDetails().getVehicleName()+"");

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
                isHelpOk = false;
                if (detailsBean.getMyHelpVehicleRescueDetails().getHelpCount() == 0) {
                    state = 0;
                    tv_confirm_help.setVisibility(View.VISIBLE);
                    tv_confirm_help.setText("取消救援");
                } else {
                    //确认援助
                    state = 1;
                    tv_confirm_help.setVisibility(View.VISIBLE);
                }


                break;
            case 2:
                tv_state.setText("救援已完成");
                tv_state.setTextColor(Color.parseColor("#15a61f"));
                tv_confirm_help.setVisibility(View.GONE);
                isHelpOk = true;
                break;
            case 3:
                tv_state.setText("救援被终止");
                tv_state.setTextColor(Color.parseColor("#999999"));
                tv_confirm_help.setVisibility(View.GONE);
                break;
            case 4:
                tv_state.setText("救援被终止");
                tv_state.setTextColor(Color.parseColor("#999999"));
                tv_confirm_help.setVisibility(View.GONE);
                break;
            default:
                tv_state.setText("请稍后 ....");
                tv_confirm_help.setVisibility(View.GONE);
                break;
        }

        //Speen
        spannable = new SpannableString("酬金" + detailsBean.getMyHelpVehicleRescueDetails().getReward() + "元");
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#ec2b2b")), 2, spannable.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_thank_money.setText(spannable);

        tv_maymoney.setText(0 + "");


    }

    ArrayList<String> arrayList;

    /**
     * 设置图片
     */
    private void setImage() {
        arrayList = new ArrayList();

        for (int i = 0; i < detailsBean.getImageGroup().size(); i++) {
            arrayList.add(detailsBean.getImageGroup().get(i).getImage_url());
        }


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

        activity_my_resuce_details.setVisibility(View.VISIBLE);

    }


    Intent intent;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_my_resuce_details_help_sum:
                Intent intent1 = new Intent(this, HelpPeopleSum.class);
                intent1.putExtra("VR_ID", VR_ID);
                startActivity(intent1);
                break;
            case R.id.my_resuce_details_ll_play_voice:  // 播放语音
                if (playerUtils.mediaPlayer.isPlaying()) {
                    return;
                }
                if (!TextUtils.isEmpty(detailsBean.getMyHelpVehicleRescueDetails().getSeek_Voice())) {
                    playerUtils.playUrl(detailsBean.getMyHelpVehicleRescueDetails().getSeek_Voice());
                    ll_play_voice.setBackground(ContextCompat.getDrawable(MyResuceDetailsActivity.this, R.drawable.shape_red_solid));
                    playerUtils.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            ll_play_voice.setBackground(ContextCompat.getDrawable(MyResuceDetailsActivity.this, R.drawable.shape_1ccd9b_6_soild));
                        }
                    });
                } else {
                    Toast.makeText(this, "播放语音失败！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.my_resuce_details_tv_confirm_help:  //确认援助
                mDialog.show();
                hms.clear();
                hms.put("currId", MySharedPreferences.getUserId(this));
                hms.put("State", state + "");
                hms.put("VR_ID", detailsBean.getMyHelpVehicleRescueDetails().getID() + "");
                requestMyData(hms, selectIsHelpUrl, 2);
                break;
            case R.id.toolbar_back:  //返回
                finish();
                break;
            case R.id.my_resuce_details_pirc1:
                intent = new Intent(MyResuceDetailsActivity.this, ShowImageActivity.class);
                intent.putStringArrayListExtra("url", arrayList);
                intent.putExtra("position", 0);
                startActivity(intent);
                break;
            case R.id.my_resuce_details_pirc2:
                intent = new Intent(this, ShowImageActivity.class);
                intent.putStringArrayListExtra("url", arrayList);
                intent.putExtra("position", 1);
                startActivity(intent);
                break;
            case R.id.my_resuce_details_pirc3:
                intent = new Intent(this, ShowImageActivity.class);
                intent.putStringArrayListExtra("url", arrayList);
                intent.putExtra("position", 2);
                startActivity(intent);
                break;
            case R.id.my_resuce_details_pirc4:
                intent = new Intent(this, ShowImageActivity.class);
                intent.putStringArrayListExtra("url", arrayList);
                intent.putExtra("position", 3);
                startActivity(intent);
                break;
            case R.id.my_resuce_details_pirc5:

                intent = new Intent(this, ShowImageActivity.class);
                intent.putStringArrayListExtra("url", arrayList);
                intent.putExtra("position", 4);
                startActivity(intent);
                break;
            case R.id.my_resuce_details_pirc6:

                intent = new Intent(this, ShowImageActivity.class);
                intent.putStringArrayListExtra("url", arrayList);
                intent.putExtra("position", 5);
                startActivity(intent);
                break;
            case R.id.my_resuce_details_pirc7:
                intent = new Intent(this, ShowImageActivity.class);
                intent.putStringArrayListExtra("url", arrayList);
                intent.putExtra("position", 6);
                startActivity(intent);
                break;
            case R.id.my_resuce_details_pirc8:
                intent = new Intent(this, ShowImageActivity.class);
                intent.putStringArrayListExtra("url", arrayList);
                intent.putExtra("position", 7);
                startActivity(intent);
                break;
            case R.id.my_resuce_details_iv_face:
                if (!MySharedPreferences.isLogin(this)) {
                    ToastUtils.showShort(this, "请先登录.");
                    return;
                }
                Intent intent = new Intent(this, MineHomeActivity.class);
                intent.putExtra("targetUserId", detailsBean.getMyHelpVehicleRescueDetails().getSeekHelpUserId()+"");
                startActivity(intent);
                break;
        }
    }

    public void onResume() {
        super.onResume();
        toolbar_title.requestFocus();
        toolbar_title.isFocusableInTouchMode();
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
