package com.hyzs.onekeyhelp.lifehelp.view;


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
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.ShowImageActivity;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.lifehelp.adapter.LiveDetailsAdapter;
import com.hyzs.onekeyhelp.lifehelp.adapter.LiveDetailsCommentAdapter;
import com.hyzs.onekeyhelp.lifehelp.adapter.LiveDetailsEndAdapter;
import com.hyzs.onekeyhelp.lifehelp.bean.HelpMeDetailsBean;
import com.hyzs.onekeyhelp.lifehelp.bean.HelpMeDetailsCommonBean;
import com.hyzs.onekeyhelp.lifehelp.bean.HelpOtherDetailsHelMeBean;
import com.hyzs.onekeyhelp.lifehelp.bean.LifeHelpOtherCommentBean;
import com.hyzs.onekeyhelp.lifehelp.bean.LiveDetailsCommentOkBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.DateFormatUtils;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;
import com.hyzs.onekeyhelp.util.InputMethodManagerUtils;
import com.hyzs.onekeyhelp.util.JointGetUrl;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.OkHttpUtil;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.ListViewForScrollView;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class HelpMeDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack, iv_idcrad;
    private TextView toolbar_title, tv_allMoney, tv_mayMoney;
    private TextView toolbar_right, tv_chenghao, tv_address, tv_name, tv_info, tv_state, tv_helpNumber, tv_time, tv_grade, tv_type, tv_zheng, tv_send, et_context;
    private RoundImageView CircleImageView, imageView1, imageView2, imageView3, imageView4;
    private LinearLayout ll_imags;
    private LinearLayout ll_reward, activity_help_other_details, live_help_other_details_ll_money_reward;
    private OkHttpUtil okHttpUtil;
    private HelpMeDetailsBean lifeHelpBean;
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
    private RelativeLayout mEmptyView;
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

    private String WEBUrl = PortUtil.LifeSeekHelpDetails;
    private String WEBUrl_help_comment = PortUtil.LifeSeekHelpCommentList;
    private String WEBUrl_Comment_me = PortUtil.LifeSeekHelpComment;
    private String WEBUrl_SeekHelp_me = PortUtil.MySeekHelpMoneyAllotList;
    private String WEBUrl_payMoney_me = PortUtil.MySeekHelpPayReward;

    private boolean isFirstComment = true;

    private Intent startImage;
    private ArrayList<String> arrayBitmaps;
    private HelpMeDetailsCommonBean commonBean;

    private boolean isHelpOk;
    private boolean isFreeIssue;

    @Override
    protected void assignView() {
        lv_mGetHelp = (ListViewForScrollView) findViewById(R.id.live_help_other_details_lv_get_help);
        lv_mComment = (ListViewForScrollView) findViewById(R.id.live_help_other_details_ll_comment);
        ll_reward = (LinearLayout) findViewById(R.id.live_help_other_details_ll_reward);
        activity_help_other_details = (LinearLayout) findViewById(R.id.activity_help_other_details);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        PDialog = com.hyzs.onekeyhelp.util.ProgressDialog.createProgressLoading(this);
        toolbar_right = (TextView) findViewById(R.id.toolbar_right);
        Intent intent = getIntent();
        ars = new ArrayList<>();
        Seek_ID = intent.getStringExtra("Seek_ID");
        Seek_UserID = intent.getStringExtra("Seek_UserID");
        ID = intent.getStringExtra("ID");
        lv_mGetHelp.setVisibility(View.VISIBLE);
        ll_reward.setVisibility(View.VISIBLE);
        toolbar_title.setText("我的求助-详情");
        //滑到最顶端
        toolbar_title.setFocusable(true);
        toolbar_title.setFocusableInTouchMode(true);
        toolbar_title.requestFocus();
        toolbar_right.setVisibility(View.VISIBLE);
        toolbar_right.setText("完成");
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        CircleImageView = (RoundImageView) findViewById(R.id.live_help_other_details_iv_face);
        mEmptyView = (RelativeLayout) findViewById(R.id.layout_empty);
        tv_chenghao = (TextView) findViewById(R.id.live_help_other_details_tv_chenghao);
        tv_allMoney = (TextView) findViewById(R.id.live_help_other_details_tv_all_money);
        tv_mayMoney = (TextView) findViewById(R.id.live_help_other_details_tv_may_money);
        tv_address = (TextView) findViewById(R.id.live_help_other_details_tv_adress);
        tv_name = (TextView) findViewById(R.id.live_help_other_details_tv_name);
        tv_info = (TextView) findViewById(R.id.live_help_other_details_info);
        tv_state = (TextView) findViewById(R.id.live_help_other_details_tv_stat);
        iv_idcrad = (ImageView) findViewById(R.id.live_help_other_details_iv_idcard);
        ll_imags = (LinearLayout) findViewById(R.id.live_help_other_details_ll_images2);
        live_help_other_details_ll_money_reward = (LinearLayout) findViewById(R.id.live_help_other_details_ll_money_reward);
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
        //开启弹窗

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
        hms.put("seekID", ID + "");
        Log.e("helpBeanmap", hms.toString() + "-" + WEBUrl);
        requestMyData(hms, WEBUrl, 1);


        hms.clear();
        hms.put("currId", MySharedPreferences.getUserId(this));
        hms.put("seekID", ID);//
        hms.put("pageIndex", "1");
        hms.put("pageSize", 30 + "");
        Log.e("helpBeanmap2", hms.toString() + "-" + WEBUrl_help_comment);
        requestMyData(hms, WEBUrl_help_comment, 2);


    }

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
            public void onResponse(Response response)  {
                request = null;
                String str = null;
                try {
                    mEmptyView.setVisibility(View.GONE);
                    String json = response.body().string();
                    Log.e("response", json);
                    str = UrlDecodeUtil.urlCode(json);
                    switch (select) {
                        case 1:
                            Log.e("helpBean", str);
                            lifeHelpBean = new Gson().fromJson(str, HelpMeDetailsBean.class);
                            Mmoney = Double.valueOf(lifeHelpBean.getSeek_Money());
                            break;

                        case 2:
                            Log.e("info_comment1", str);
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
                        case 8:
                            Log.e("setStr", str);
                            okBean = new Gson().fromJson(str, LiveDetailsCommentOkBean.class);
                            break;
                        case 9:
                            Log.e("setStr", str);
                            commonBean = new Gson().fromJson(str, HelpMeDetailsCommonBean.class);
                            break;
                    }
                } catch (Exception e) {
                    handler.sendEmptyMessage(7);
                    e.printStackTrace();
                    return;
                }
                handler.sendEmptyMessage(select);
            }
        });
    }

    private int isOkState;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.e("state", lifeHelpBean.getSeek_State() + "");
                    isOkState = lifeHelpBean.getSeek_State();
                    if (lifeHelpBean.getSeek_State() > 1) {
                        hms.clear();
                        hms.put("currId", MySharedPreferences.getUserId(HelpMeDetailsActivity.this));
                        hms.put("seekHelpID", ID + "");
                        hms.put("seekType", "2");
                        requestMyData(hms, PortUtil.ComplateSeekHelpUserListCommon, 9);
                    } else {
                        hms.clear();
                        hms.put("currId", MySharedPreferences.getUserId(HelpMeDetailsActivity.this));
                        hms.put("seekHelpID", ID + "");
                        requestMyData(hms, PortUtil.MySeekHelpMoneyAllotList, 5);
                    }
                    setValueByViewLifeHelp(lifeHelpBean);

                    tv_allMoney.setText(Mmoney + "元");
                    tv_mayMoney.setText(Mmoney + "");
                    break;
                case 2: //聊天详情
                    if (commentBean == null) {
                        Toast.makeText(HelpMeDetailsActivity.this, "获取信息为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.e("info_comment2", commentBean.getLifeSeekHelpComments().size() + "");
                    lv_mComment.setAdapter(new LiveDetailsCommentAdapter(HelpMeDetailsActivity.this, commentBean));
                    break;
                case 3://回复评论
                    if (CommentOkBean.getCode().equals("10000")) {//正常
                        Toast.makeText(HelpMeDetailsActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                        PDialog.show();
                        hms.put("currId", MySharedPreferences.getUserId(HelpMeDetailsActivity.this));
                        hms.put("seekID", ID + "");
                        hms.put("pageIndex", "1");
                        hms.put("pageSize", Integer.MAX_VALUE + "");
                        requestMyData(hms, WEBUrl_help_comment, 4);

                        return;
                    } else {
                        Toast.makeText(HelpMeDetailsActivity.this, "网络繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 4: //聊天详情

                    if (commentBean == null) {
                        Toast.makeText(HelpMeDetailsActivity.this, "获取信息为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    lv_mComment.setAdapter(new LiveDetailsCommentAdapter(HelpMeDetailsActivity.this, commentBean));


                    lv_mComment.setSelection(commentBean.getLifeSeekHelpComments().size());


                    break;
                case 5: //帮助人明细

                    if (helMeBean == null) {
                        Toast.makeText(HelpMeDetailsActivity.this, "获取帮助人为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ints = new double[helMeBean.getTotal()];
                    tv_mayMoney.setText("0");
                    lv_mGetHelp.setAdapter(new LiveDetailsAdapter(HelpMeDetailsActivity.this, helMeBean.getMySeekHelpMoneyAllotList(), tv_mayMoney, Mmoney, ints, isHelpOk, isFreeIssue));
                    break;

                case 6:
                    Log.e("helpUserIdAndPayMoney", okBean.getCode());
                    if (okBean.getCode().equals("10000")) {
                        setHelpState();

                    } else {
                        Toast.makeText(HelpMeDetailsActivity.this, okBean.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    break;

                case 7:
                    Toast.makeText(HelpMeDetailsActivity.this, "获取数据异常", Toast.LENGTH_SHORT).show();
                    break;

                case 8:
                    if (okBean.getCode().equals("10000")) {
                        Toast.makeText(HelpMeDetailsActivity.this, "成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(HelpMeDetailsActivity.this, okBean.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    break;
                case 9:
                    if (commentBean == null) {
                        Toast.makeText(HelpMeDetailsActivity.this, "获取帮助人为空", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    tv_mayMoney.setText("0");
                    lv_mGetHelp.setAdapter(new LiveDetailsEndAdapter(HelpMeDetailsActivity.this, commonBean.getComplateSeekHelpUserListCommon(), Mmoney, isHelpOk));
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
    private void setValueByViewLifeHelp(final HelpMeDetailsBean lifeHelpBean) {
        tv_name.setText(lifeHelpBean.getUesrname());
        tv_info.setText(lifeHelpBean.getSeek_Text());
        tv_helpNumber.setText("收到帮助：" + lifeHelpBean.getHelpCount() + "人");
        tv_time.setText(DateFormatUtils.Time2Date((lifeHelpBean.getSeek_Time() * 1000) + ""));
        tv_address.setText(lifeHelpBean.getCommunityName());
        tv_chenghao.setText(lifeHelpBean.getGrassrootsHero());

        ImageLoadUtils.setImageForUrl(lifeHelpBean.getFace(), CircleImageView);

        CircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMeDetailsActivity.this, MineHomeActivity.class);
                intent.putExtra("targetUserId", lifeHelpBean.getSeek_UserID() + "");
                startActivity(intent);
            }
        });

        if (lifeHelpBean.getSeek_Type() == 1) {
            live_help_other_details_ll_money_reward.setVisibility(View.GONE);
            isFreeIssue = true;
        } else {
            live_help_other_details_ll_money_reward.setVisibility(View.VISIBLE);
            isFreeIssue = false;
        }


        if (lifeHelpBean.getRNA() == 1) {//判断是否通过实名认证
            iv_idcrad.setVisibility(View.VISIBLE);
        } else {
            iv_idcrad.setVisibility(View.GONE);
        }
        if (lifeHelpBean.getNCCS() == 1) {//判断是否通过居委会认证
            tv_zheng.setVisibility(View.VISIBLE);
        } else {
            tv_zheng.setVisibility(View.GONE);
        }

        switch (lifeHelpBean.getSeek_State()) {// 求助状态,Switch
            case 1:
                tv_state.setText("求助中 ....");
                isHelpOk = false;
                break;
            case 2:
                tv_state.setText("成功 ....");
                isHelpOk = true;
                break;
            case 3:
                tv_state.setText("失败 ....");
                break;
            case 4:
                tv_state.setText("取消 ....");
                break;
            default:
                tv_state.setText("请稍后 ....");
                break;
        }


        //图片操作

        if (TextUtils.isEmpty(lifeHelpBean.getSeek_AffixImgList())) {

            ll_imags.setVisibility(View.GONE);

            return;
        } else {
            ll_imags.setVisibility(View.VISIBLE);
        }
        arrayBitmaps = new ArrayList<>();
        String[] faceurls = lifeHelpBean.getSeek_AffixImgList().split(",");

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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toolbar_right: //完成
                //如果帮助人数为0或者已经是完成状态时
                if (isOkState != 1 || helMeBean.getTotal() == 0) {
                    finish();
                    return;
                }
                Toast.makeText(this, "请稍后..", Toast.LENGTH_SHORT).show();
                if (isFreeIssue) {
                    setHelpState();
                    return;
                }
                try {
                    if (Double.valueOf(tv_mayMoney.getText().toString()) != 0) {
                        Toast.makeText(this, "请将可分配金额分配至0！", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    Toast.makeText(this, "请将可分配金额分配至0！", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < helMeBean.getTotal(); i++) {
                    MoneyReword.append(helMeBean.getMySeekHelpMoneyAllotList().get(i).getUserid() + ":" + ints[i] + "|");
                }
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
                Log.e("helpUserIdAndPayMoney", encodedData + "--" + MoneyReword);
                hms.put("helpUserIdAndPayMoney", encodedData);
                requestMyData(hms, WEBUrl_payMoney_me, 6);

                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.live_help_other_details_tv_send:
                InputMethodManagerUtils.setInputMethod(this);
                hms.clear();

                hms.put("currId", MySharedPreferences.getUserId(HelpMeDetailsActivity.this));
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


    public void setHelpState() {
        hms.clear();
        hms.put("currId", MySharedPreferences.getUserId(HelpMeDetailsActivity.this));
        hms.put("seekID", ID + "");
        hms.put("status", "1");
        requestMyData(hms, PortUtil.SetSeekStatus, 8);
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
