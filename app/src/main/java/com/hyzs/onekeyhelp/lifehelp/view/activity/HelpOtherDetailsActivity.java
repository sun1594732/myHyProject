package com.hyzs.onekeyhelp.lifehelp.view.activity;


import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.ShowImageActivity;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.lifehelp.adapter.LiveDetailsAdapter;
import com.hyzs.onekeyhelp.lifehelp.adapter.LiveDetailsCommentAdapter;
import com.hyzs.onekeyhelp.lifehelp.bean.HelpOtherDetailsBean;
import com.hyzs.onekeyhelp.lifehelp.bean.HelpOtherDetailsHelMeBean;
import com.hyzs.onekeyhelp.lifehelp.bean.LifeHelpOtherCommentBean;
import com.hyzs.onekeyhelp.lifehelp.bean.LiveDetailsCommentOkBean;
import com.hyzs.onekeyhelp.widget.ListViewForScrollView;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.DateFormatUtils;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;
import com.hyzs.onekeyhelp.util.InputMethodManagerUtils;
import com.hyzs.onekeyhelp.util.JointGetUrl;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;
import com.hyzs.onekeyhelp.util.OkHttpUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class HelpOtherDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack, iv_idcrad,chat,phone;
    private TextView toolbar_title, tv_allMoney, tv_mayMoney;
    private TextView toolbar_right, tv_chenghao, tv_address, tv_name, tv_info, tv_state, tv_helpNumber, tv_time, tv_grade, tv_type, tv_zheng, tv_send, et_context;
    private RoundImageView CircleImageView, imageView1, imageView2, imageView3, imageView4;
    private LinearLayout ll_imags;
    private LinearLayout ll_reward, activity_help_other_details;
    private OkHttpUtil okHttpUtil;
    private HelpOtherDetailsBean lifeHelpBean;
    private LifeHelpOtherCommentBean commentBean;
    private LiveDetailsCommentOkBean CommentOkBean;
    private Dialog PDialog;
    private StringBuffer MoneyReword;
    //----------------------------------------------------------------------------------------------
    private ListViewForScrollView lv_mGetHelp;
    private ListViewForScrollView lv_mComment;
    private HashMap<String, String> hms;
    private HelpOtherDetailsHelMeBean helMeBean;
    private LiveDetailsCommentOkBean okBean;

    private OkHttpClient okHttpClient;
    private Request request;
    private ArrayList<String> ars;

    //----------------------------------------------------------------------------------------------
    private String Seek_ID;
    private String Seek_UserID;
    private String ID;

    private String SeekHelpUserId;
    private String HelpPeopleUserId;
    private Double Mmoney;
    private double[] ints;
    private String WEBUrl;
    private String WEBUrl_help_comment = PortUtil.LifeSeekHelpCommentList;
    private String WEBUrl_Comment_me = PortUtil.LifeSeekHelpComment;
    private String WEBUrl_SeekHelp_me = PortUtil.MySeekHelpMoneyAllotList;
    private String WEBUrl_payMoney_me = PortUtil.MySeekHelpPayReward;
    private boolean isFirstComment = true;
    private Intent startImage;
    private ArrayList<String> arrayBitmaps;
    private boolean isHelpMe;
    private boolean isHelpOk;
    private RelativeLayout mEmptyView;

    @Override
    protected void assignView() {
        chat = (ImageView) findViewById(R.id.activity_help_other_details_chat);
        phone = (ImageView) findViewById(R.id.activity_help_other_details_phone);
        chat.setOnClickListener(this);
        phone.setOnClickListener(this);
        lv_mGetHelp = (ListViewForScrollView) findViewById(R.id.live_help_other_details_lv_get_help);
        lv_mComment = (ListViewForScrollView) findViewById(R.id.live_help_other_details_ll_comment);
        ll_reward = (LinearLayout) findViewById(R.id.live_help_other_details_ll_reward);
        activity_help_other_details = (LinearLayout) findViewById(R.id.activity_help_other_details);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        PDialog = com.hyzs.onekeyhelp.util.ProgressDialog.createProgressLoading(this);
        toolbar_right = (TextView) findViewById(R.id.toolbar_right);
        Intent intent = getIntent();
        ars = new ArrayList<>();
        SeekHelpUserId = intent.getStringExtra("SeekHelpUserId");
        HelpPeopleUserId = intent.getStringExtra("HelpPeopleUserId");
        lv_mGetHelp.setVisibility(View.GONE);
        ll_reward.setVisibility(View.GONE);
        toolbar_title.setText("我的帮助-详情");
        //滑到最顶端
        toolbar_title.setFocusable(true);
        toolbar_title.setFocusableInTouchMode(true);
        toolbar_title.requestFocus();
        toolbar_right.setVisibility(View.VISIBLE);
        toolbar_right.setText("完成");
        isHelpMe = false;
        mEmptyView = (RelativeLayout) findViewById(R.id.layout_empty);
        WEBUrl = PortUtil.LifeSeekMyHelpDetails;
        ID = intent.getStringExtra("ID");
        Log.e("Vr", ID);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        CircleImageView = (RoundImageView) findViewById(R.id.live_help_other_details_iv_face);
        tv_chenghao = (TextView) findViewById(R.id.live_help_other_details_tv_chenghao);
        tv_allMoney = (TextView) findViewById(R.id.live_help_other_details_tv_all_money);
        tv_mayMoney = (TextView) findViewById(R.id.live_help_other_details_tv_may_money);
        tv_address = (TextView) findViewById(R.id.live_help_other_details_tv_adress);
        tv_name = (TextView) findViewById(R.id.live_help_other_details_tv_name);
        tv_info = (TextView) findViewById(R.id.live_help_other_details_info);
        tv_state = (TextView) findViewById(R.id.live_help_other_details_tv_stat);
        iv_idcrad = (ImageView) findViewById(R.id.live_help_other_details_iv_idcard);
        ll_imags = (LinearLayout) findViewById(R.id.live_help_other_details_ll_images2);

        imageView1 = (RoundImageView) findViewById(R.id.live_help_other_details_imageView1);
        imageView2 = (RoundImageView) findViewById(R.id.live_help_other_details_imageView2);
        imageView3 = (RoundImageView) findViewById(R.id.live_help_other_details_imageView3);
        imageView4 = (RoundImageView) findViewById(R.id.live_help_other_details_imageView4);

        tv_helpNumber = (TextView) findViewById(R.id.live_help_other_details_number);
        tv_time = (TextView) findViewById(R.id.live_help_other_details_time);
        tv_grade = (TextView) findViewById(R.id.live_help_other_details_grade);  //积分/悬赏
        tv_type = (TextView) findViewById(R.id.live_help_other_details_type);
        tv_zheng = (TextView) findViewById(R.id.live_help_other_details_tv_zheng);
        tv_send = (TextView) findViewById(R.id.live_help_other_details_tv_send);
        et_context = (TextView) findViewById(R.id.live_help_other_details_et_send_contxt);

        okHttpClient = new OkHttpClient();
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);

    }

    @Override
    protected void initView() {
        mBack.setOnClickListener(this);
        toolbar_right.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        MoneyReword = new StringBuffer();
        toolbar_right.requestFocus();
    }

    @Override
    protected void initListener() {
        mBack.requestFocus();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_other_details;
    }

    @Override
    protected void initData() {
        PDialog.show();

        hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this));
        hms.put("SeekHelpID", ID + "");
        requestMyData(hms, WEBUrl, 1);

        hms.clear();

        hms.put("currId", MySharedPreferences.getUserId(this));
        hms.put("seekID", ID);//
        hms.put("pageIndex", "1");
        hms.put("pageSize", 30 + "");
        requestMyData(hms, WEBUrl_help_comment, 2);

        hms.clear();
        hms.put("currId", MySharedPreferences.getUserId(this));
        hms.put("seekHelpID", ID + "");
        requestMyData(hms, PortUtil.MySeekHelpMoneyAllotList, 5);

    }

    private int helpId;

    /**
     * 联网请求数据
     */
    private void requestMyData(HashMap hms, String MWEBUrl, final int select) {
        //判断是否已经登录
        if (!MySharedPreferences.isLogin(this)) {
            Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
            return;
        }

        MWEBUrl = JointGetUrl.getUrl(MWEBUrl, hms);
        request = new Request.Builder().url(MWEBUrl).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                request = null;
            }

            @Override
            public void onResponse(Response response) throws IOException {
                request = null;
                String str = null;
                try {
                    mEmptyView.setVisibility(View.GONE);
                    str = URLDecoder.decode(response.body().string(), "utf-8");
                    Log.e("responsess", str);
                    switch (select) {
                        case 1:
                            lifeHelpBean = new Gson().fromJson(str, HelpOtherDetailsBean.class);
                            Mmoney = Double.valueOf(lifeHelpBean.getLifeSeekMyHelpDetails().getSeek_Money());
                            helpId = lifeHelpBean.getLifeSeekMyHelpDetails().getHelpID();
                            Log.e("lifeHelpBean", helpId + "---" + lifeHelpBean.toString());
                            break;
                        case 2:
                            commentBean = new Gson().fromJson(str, LifeHelpOtherCommentBean.class);
                            break;
                        case 3:
                            Log.e("info_comment_me", str);
                            CommentOkBean = new Gson().fromJson(str, LiveDetailsCommentOkBean.class);
                            break;
                        case 4:
                            Log.e("info_comment_me", str);
                            commentBean = new Gson().fromJson(str, LifeHelpOtherCommentBean.class);
                            break;

                        case 5:
                            Log.e("wudi", str);
                            helMeBean = new Gson().fromJson(str, HelpOtherDetailsHelMeBean.class);
                            break;
                        case 6:
                            Log.e("moneyStr", str);
                            okBean = new Gson().fromJson(str, LiveDetailsCommentOkBean.class);
                            break;
                    }

                } catch (Exception e) {
                    handler.sendEmptyMessage(7);
                    return;
                }

                handler.sendEmptyMessage(select);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:  //帮助详情
                    setValueByViewLifeHelp(lifeHelpBean);

                    tv_allMoney.setText(Mmoney + "元");
                    tv_mayMoney.setText(Mmoney + "");
                    break;
                case 2: //聊天详情
                    if (commentBean == null) {
                        Toast.makeText(HelpOtherDetailsActivity.this, "获取信息为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    lv_mComment.setAdapter(new LiveDetailsCommentAdapter(HelpOtherDetailsActivity.this, commentBean));
                    break;
                case 3://回复评论
                    if (CommentOkBean.getCode().equals("10000")) {//正常
                        Toast.makeText(HelpOtherDetailsActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                        PDialog.show();
                        hms.put("currId", MySharedPreferences.getUserId(HelpOtherDetailsActivity.this));
                        hms.put("seekID", ID + "");
                        hms.put("pageIndex", "1");
                        hms.put("pageSize", Integer.MAX_VALUE + "");
                        requestMyData(hms, WEBUrl_help_comment, 4);

                        return;
                    } else {
                        Toast.makeText(HelpOtherDetailsActivity.this, "网络繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 4: //聊天详情

                    if (commentBean == null) {
                        Toast.makeText(HelpOtherDetailsActivity.this, "获取信息为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    lv_mComment.setAdapter(new LiveDetailsCommentAdapter(HelpOtherDetailsActivity.this, commentBean));
                    lv_mComment.setSelection(commentBean.getLifeSeekHelpComments().size());
                    break;
                case 5: //帮助人明细

                    if (helMeBean == null) {
                        Toast.makeText(HelpOtherDetailsActivity.this, "获取帮助人为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ints = new double[helMeBean.getTotal()];
                    tv_mayMoney.setText("0");
                    lv_mGetHelp.setAdapter(new LiveDetailsAdapter(HelpOtherDetailsActivity.this, helMeBean.getMySeekHelpMoneyAllotList(), tv_mayMoney, Mmoney, ints, isHelpOk, true));
                    break;

                case 6:

                    if (okBean.getCode().equals("10000")) {
                        Toast.makeText(HelpOtherDetailsActivity.this, "支付成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(HelpOtherDetailsActivity.this, okBean.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    break;

                case 7:
                    Toast.makeText(HelpOtherDetailsActivity.this, "获取数据异常，请稍后重试..", Toast.LENGTH_SHORT).show();
                    break;

            }

            PDialog.dismiss();

        }

    };


    /**
     * 填充Activity LifeHelp 各控件
     *
     * @param lifeHelpBean 数据源
     */
    private void setValueByViewLifeHelp(final HelpOtherDetailsBean lifeHelpBean) {
        tv_name.setText(lifeHelpBean.getLifeSeekMyHelpDetails()
                .getUesrname());
        tv_info.setText(lifeHelpBean.getLifeSeekMyHelpDetails().getSeek_Text());
        tv_helpNumber.setText("收到帮助：" + lifeHelpBean.getLifeSeekMyHelpDetails().getHelpCount() + "人");
        tv_time.setText(DateFormatUtils.Time2Date((lifeHelpBean.getLifeSeekMyHelpDetails().getSeek_Time() * 1000) + ""));
        tv_address.setText(lifeHelpBean.getLifeSeekMyHelpDetails().getCommunityName());
        tv_chenghao.setText(lifeHelpBean.getLifeSeekMyHelpDetails().getGrassrootsHero());

        ImageLoadUtils.setImageForUrl(lifeHelpBean.getLifeSeekMyHelpDetails().getFace(), CircleImageView);

        CircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpOtherDetailsActivity.this, MineHomeActivity.class);
                intent.putExtra("targetUserId", lifeHelpBean.getLifeSeekMyHelpDetails().getSeek_UserID() + "");
                startActivity(intent);
            }
        });

        if (lifeHelpBean.getLifeSeekMyHelpDetails().getRNA() == 1) {//判断是否通过实名认证
            iv_idcrad.setVisibility(View.VISIBLE);
        } else {
            iv_idcrad.setVisibility(View.GONE);
        }
        if (lifeHelpBean.getLifeSeekMyHelpDetails().getNCCS() == 1) {//判断是否通过居委会认证
            tv_zheng.setVisibility(View.VISIBLE);
        } else {
            tv_zheng.setVisibility(View.GONE);
        }
        switch (lifeHelpBean.getLifeSeekMyHelpDetails().getSeek_State()) {// 求助状态,Switch
            case 1:
                tv_state.setText("求助中 ....");
                isHelpOk = false;
                break;
            case 2:
            case 3:
                tv_state.setText("已完成");
                isHelpOk = true;
                break;
            case 4:
                tv_state.setText("已取消");
                break;
            default:
                tv_state.setText("获取失败");

                break;
        }

        if (lifeHelpBean.getLifeSeekMyHelpDetails().getHelp_Type() != 1) {
            toolbar_right.setVisibility(View.GONE);
        } else {
            toolbar_right.setVisibility(View.VISIBLE);
        }


        //图片操作

        if (TextUtils.isEmpty(lifeHelpBean.getLifeSeekMyHelpDetails().getSeek_AffixImgList())) {

            ll_imags.setVisibility(View.GONE);


            return;
        } else {
            ll_imags.setVisibility(View.VISIBLE);
        }
        arrayBitmaps = new ArrayList<>();
        String[] faceurls = lifeHelpBean.getLifeSeekMyHelpDetails().getSeek_AffixImgList().split(",");

        for (int i = 0; i < faceurls.length; i++) {
            faceurls[i] = faceurls[i].substring(1, faceurls[i].length() - 1);
            arrayBitmaps.add(faceurls[i]);

        }
        switch (faceurls.length) {
            case 1:
                imageView2.setVisibility(View.GONE);
                imageView3.setVisibility(View.GONE);
                imageView4.setVisibility(View.GONE);
                imageView1.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(faceurls[0], imageView1);

                break;
            case 2:
                imageView1.setVisibility(View.GONE);
                imageView3.setVisibility(View.GONE);
                imageView4.setVisibility(View.GONE);
                imageView1.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(faceurls[0], imageView1);
                ImageLoadUtils.setImageForUrl(faceurls[1], imageView2);
                break;
            case 3:
                imageView1.setVisibility(View.GONE);
                imageView4.setVisibility(View.GONE);
                imageView1.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.VISIBLE);
                imageView3.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(faceurls[0], imageView1);
                ImageLoadUtils.setImageForUrl(faceurls[1], imageView2);
                ImageLoadUtils.setImageForUrl(faceurls[2], imageView3);
                break;
            case 4:
                imageView1.setVisibility(View.GONE);
                imageView1.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.VISIBLE);
                imageView3.setVisibility(View.VISIBLE);
                imageView4.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(faceurls[0], imageView1);
                ImageLoadUtils.setImageForUrl(faceurls[1], imageView2);
                ImageLoadUtils.setImageForUrl(faceurls[2], imageView3);
                ImageLoadUtils.setImageForUrl(faceurls[3], imageView4);
                break;
        }
    }
    private static final int BAIDU_READ_PHONE_STATE = 100;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_help_other_details_chat:
                MySharedPreferences.getInstance(this).setString("Img", lifeHelpBean.getLifeSeekMyHelpDetails().getFace());
                NotifyMsgCountUtil.notifyMsg(lifeHelpBean.getLifeSeekMyHelpDetails().getSeek_UserID() + "");
                Intent intent = new Intent();
                intent.putExtra("one", lifeHelpBean.getLifeSeekMyHelpDetails().getSeek_UserID()+"");
                intent.putExtra("userName", lifeHelpBean.getLifeSeekMyHelpDetails().getUesrname());
                intent.setClass(this, IntentChatActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_help_other_details_phone:
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
//                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}
//                                , BAIDU_READ_PHONE_STATE);
//                    } else {
//                        if (!TextUtils.isEmpty(lifeHelpBean.getLifeSeekMyHelpDetails().get())) {
//                            intent = new Intent(Intent.ACTION_DIAL);
//                            Uri data = Uri.parse("tel:" + detailsBean.getMyHelpVehicleRescueDetails().getPhone());
//                            intent.setData(data);
//                            startActivity(intent);
//                        }
//                    }
//                } else {
//                    if (!TextUtils.isEmpty(detailsBean.getMyHelpVehicleRescueDetails().getPhone())) {
//                        intent = new Intent(Intent.ACTION_DIAL);
//                        Uri data = Uri.parse("tel:" + detailsBean.getMyHelpVehicleRescueDetails().getPhone());
//                        intent.setData(data);
//                        startActivity(intent);
//                    }
//                }
                break;
            case R.id.toolbar_right: //完成

                if (isHelpMe) {
                    //如果帮助人数为0，则返回界面
                    if (helMeBean.getTotal() == 0) {
                        finish();
                        return;
                    }
                    Toast.makeText(this, "请稍后..", Toast.LENGTH_SHORT).show();
                    try {
                        if (Double.valueOf(tv_mayMoney.getText().toString()) != 0) {
                            Toast.makeText(this, "请将可分配金额分配至0！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "请将可分配金额分配至0！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (int i = 0; i < helMeBean.getTotal(); i++) {
                        MoneyReword.append(helMeBean.getMySeekHelpMoneyAllotList().get(i).getHelpID() + ":" + ints[i] + "|");
                    }
                    //设置完成
                    hms.clear();
                    hms.put("currId", MySharedPreferences.getUserId(this));
                    hms.put("seekHelpID", ID + "");
                    String encodedData = null;
                    if (MoneyReword.length() == 0) {
                    } else {
                        try {
                            encodedData = URLEncoder.encode(MoneyReword.substring(0, MoneyReword.length() - 1), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    hms.put("helpUserIdAndPayMoney", encodedData);
                    requestMyData(hms, WEBUrl_payMoney_me, 6);
                } else {//我的帮助
                    hms.clear();
                    hms.put("currId", MySharedPreferences.getUserId(this));
                    hms.put("helpID", helpId + "");
                    Log.e("helpID", "--" + helpId);
                    hms.put("status", 4 + "");
                    NetRequest.CommonUseListRequestMy(PortUtil.SetSeekHelpStatus, hms, new NetRequest.getDataCallback() {
                        @Override
                        public void getData(String data) {
                            try {
                                okBean = new Gson().fromJson(URLDecoder.decode(data, "utf-8"), LiveDetailsCommentOkBean.class);
                            } catch (Exception e) {
                                Toast.makeText(HelpOtherDetailsActivity.this, "获取数据异常", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (okBean.getCode().equals("10000")) {
                                Toast.makeText(HelpOtherDetailsActivity.this, "完成", Toast.LENGTH_SHORT).show();
                                finish();
                            } else
                                Toast.makeText(HelpOtherDetailsActivity.this, okBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.live_help_other_details_tv_send:
                InputMethodManagerUtils.setInputMethod(this);
                hms.clear();
                hms.put("currId", MySharedPreferences.getUserId(HelpOtherDetailsActivity.this));
                hms.put("seekID", ID + "");
                hms.put("seek_AffixImgList", "");
                hms.put("seek_Voice", "");
                hms.put("sc_Type", "2");
                hms.put("sc_Content", et_context.getText().toString());
                requestMyData(hms, WEBUrl_Comment_me, 3);
                et_context.setText("");
                break;
            case R.id.live_help_other_details_imageView1:
                startImage = new Intent(this, ShowImageActivity.class);
                startImage.putExtra("position", 0);
                startImage.putStringArrayListExtra("url", arrayBitmaps);
                startActivity(startImage);
                break;
            case R.id.live_help_other_details_imageView2:
                startImage = new Intent(this, ShowImageActivity.class);
                startImage.putExtra("position", 1);
                startImage.putStringArrayListExtra("url", arrayBitmaps);
                startActivity(startImage);
                break;
            case R.id.live_help_other_details_imageView3:
                startImage = new Intent(this, ShowImageActivity.class);
                startImage.putExtra("position", 2);
                startImage.putStringArrayListExtra("url", arrayBitmaps);
                startActivity(startImage);
                break;
            case R.id.live_help_other_details_imageView4:
                startImage = new Intent(this, ShowImageActivity.class);
                startImage.putExtra("position", 3);
                startImage.putStringArrayListExtra("url", arrayBitmaps);
                startActivity(startImage);
                break;


        }
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("HelpOtherDetailsActivity");
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HelpOtherDetailsActivity");
        MobclickAgent.onPause(this);
    }

}
