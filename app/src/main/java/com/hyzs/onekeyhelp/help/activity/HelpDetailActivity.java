package com.hyzs.onekeyhelp.help.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.carresuce.bean.MyHelpDetailsBean;
import com.hyzs.onekeyhelp.help.adapter.HelpDetailListAdapter;
import com.hyzs.onekeyhelp.help.bean.BegDetailListBean;
import com.hyzs.onekeyhelp.help.bean.HelpDetailListBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MusicPlayerUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static com.hyzs.onekeyhelp.R.id.help_detail_list;


public class HelpDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title;
    private RoundImageView icon;
    private TextView name, zheng, status, address, time, confirm, distance, shang, fu, wu, jiu, ke, jie, lin, jin, wei, jia;
    private ImageView identity, chat, phone;
    private TextView voice;
    private ListView listView;

    private OkHttpClient okHttpClient;
    private Request request;
    private HashMap hms;
    private String VR_ID;
    private Dialog dialog;
    private RelativeLayout mEmptyView;
    private MyHelpDetailsBean detailsBean;
    private HelpDetailListBean bean;
    private MusicPlayerUtils musicPlayerUtils;
    private BegDetailListBean listBean;
    private HelpDetailListAdapter adapter;

    @Override
    protected void assignView() {
        dialog = ProgressDialog.createProgressLoading(this);
        dialog.show();
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        okHttpClient = new OkHttpClient();
        VR_ID = getIntent().getStringExtra("VR_ID");
        icon = (RoundImageView) findViewById(R.id.help_detail_icon);
        name = (TextView) findViewById(R.id.help_detail_name);
        zheng = (TextView) findViewById(R.id.help_detail_zheng);
        status = (TextView) findViewById(R.id.help_detail_status);
        address = (TextView) findViewById(R.id.help_detail_address);
        time = (TextView) findViewById(R.id.help_detail_time);
        confirm = (TextView) findViewById(R.id.help_detail_confirm);
        identity = (ImageView) findViewById(R.id.help_detail_identity);
        chat = (ImageView) findViewById(R.id.help_detail_chat);
        phone = (ImageView) findViewById(R.id.help_detail_phone);
        voice = (TextView) findViewById(R.id.help_detail_voice);
        listView = (ListView) findViewById(help_detail_list);
        distance = (TextView) findViewById(R.id.help_detail_distance);

        shang = (TextView) findViewById(R.id.item_contact_search_shang);
        fu = (TextView) findViewById(R.id.item_contact_search_fu);
        wu = (TextView) findViewById(R.id.item_contact_search_wu);
        jiu = (TextView) findViewById(R.id.item_contact_search_jiu);
        ke = (TextView) findViewById(R.id.item_contact_search_ke);
        jie = (TextView) findViewById(R.id.item_contact_search_jie);
        lin = (TextView) findViewById(R.id.item_contact_search_lin);
        jin = (TextView) findViewById(R.id.item_contact_search_jin);
        wei = (TextView) findViewById(R.id.item_contact_search_wei);
        jia = (TextView) findViewById(R.id.item_contact_search_jia);
        mEmptyView = (RelativeLayout) findViewById(R.id.layout_empty);
    }

    private void setParame() {
        MyHelpDetailsBean.MyHelpVehicleRescueDetailsBean bean = detailsBean.getMyHelpVehicleRescueDetails();
        shang.setVisibility(View.GONE);
        fu.setVisibility(View.GONE);
        wu.setVisibility(View.GONE);
        jiu.setVisibility(View.GONE);
        ke.setVisibility(View.GONE);
        jie.setVisibility(View.GONE);
        lin.setVisibility(View.GONE);
        jin.setVisibility(View.GONE);
        wei.setVisibility(View.GONE);
        jia.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(bean.getIdentityMark())) {
            String[] s = bean.getIdentityMark().split(",");
            for (int i = 0; i < s.length; i++) {
                switch (s[i].trim()) {
                    case "1":
                        shang.setVisibility(View.VISIBLE);
                        break;
                    case "2":
                        fu.setVisibility(View.VISIBLE);
                        break;
                    case "3":
                        wu.setVisibility(View.VISIBLE);
                        break;
                    case "4":
                        jiu.setVisibility(View.VISIBLE);
                        break;
                    case "5":
                        ke.setVisibility(View.VISIBLE);
                        break;
                    case "6":
                        jie.setVisibility(View.VISIBLE);
                        break;
                    case "7":
                        lin.setVisibility(View.VISIBLE);
                        break;
                    case "8":
                        jin.setVisibility(View.VISIBLE);
                        break;
                    case "9":
                        wei.setVisibility(View.VISIBLE);
                        break;
                    case "10":
                        jia.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
        if (TextUtils.isEmpty(bean.getAvatar())) {
            icon.setImageResource(R.mipmap.icon_replace);
        } else NetRequest.loadImg(this, bean.getAvatar(), icon);
        name.setText(bean.getNickName());
        if (0 == bean.getRNA()) {
            identity.setVisibility(View.GONE);
        } else identity.setVisibility(View.VISIBLE);
        time.setText(getTime(Long.parseLong(bean.getSeek_Time() + "")));
        if (0 == bean.getNCCS()) {
            zheng.setVisibility(View.GONE);
        } else zheng.setVisibility(View.VISIBLE);
        switch (bean.getSeek_State()) {
            case 1:
                status.setText("等待救援");
                status.setTextColor(getResources().getColor(R.color.color_f7221b));
                if (2 == bean.getHR_RescueState()) {
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setText("等待对方确认");
                } else {
                    confirm.setText("完成帮助");
                    confirm.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                status.setText("救援已完成");
                status.setTextColor(getResources().getColor(R.color.color_15b521));
                confirm.setVisibility(View.GONE);
                break;
            case 3:
            case 4:
                status.setText("救援被终止");
                status.setTextColor(getResources().getColor(R.color.color_9));
                confirm.setVisibility(View.GONE);
                break;
        }
        distance.setText(bean.getDistance());
        address.setText(bean.getGPSDesc());
        dialog.dismiss();
    }

    private String getTime(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(l * 1000);
    }


    @Override
    protected void initView() {
        musicPlayerUtils = new MusicPlayerUtils(this);
        title.setText("我的帮助-详情");
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        voice.setOnClickListener(this);
        confirm.setOnClickListener(this);
        chat.setOnClickListener(this);
        phone.setOnClickListener(this);
        icon.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_detail;
    }

    @Override
    protected void initData() {
        if (!MySharedPreferences.isLogin(this)) {
            Toast.makeText(this, "您还没有登录", Toast.LENGTH_SHORT).show();
            return;
        }
        hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this));
        hms.put("VR_ID", VR_ID);
        NetRequest.ParamPostRequest(PortUtil.HelpDetailsUrl, hms, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                mEmptyView.setVisibility(View.GONE);
                detailsBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MyHelpDetailsBean.class);
                setParame();
            }
        });
        initListData();
    }

    private void initListData() {
        String url = PortUtil.baseUrl + "VehicleRescueService.asmx/ComplateSeekHelpUserListCommon";
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("seekHelpID", VR_ID + "");
        map.put("seekType", "0");
        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    mEmptyView.setVisibility(View.GONE);
                    listBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), BegDetailListBean.class);
                    initList();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        });
    }

    private void initList() {
        adapter = new HelpDetailListAdapter(this, listBean.getComplateSeekHelpUserListCommon());
        listView.setAdapter(adapter);
    }

    private static final int BAIDU_READ_PHONE_STATE = 100;

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.help_detail_voice:
                if (musicPlayerUtils.mediaPlayer.isPlaying()) {
                    return;
                }
                if (!TextUtils.isEmpty(detailsBean.getMyHelpVehicleRescueDetails().getSeek_Voice())) {
                    musicPlayerUtils.playUrl(detailsBean.getMyHelpVehicleRescueDetails().getSeek_Voice());
                    voice.setBackground(ContextCompat.getDrawable(HelpDetailActivity.this,R.drawable.shape_red_solid));
                    musicPlayerUtils.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            voice.setBackground(ContextCompat.getDrawable(HelpDetailActivity.this,R.drawable.shape_1ccd9b_6_soild));
                        }
                    });
                }
                break;
            case R.id.help_detail_chat:
                if (!MySharedPreferences.isLogin(this)) {
                    Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                MySharedPreferences.getInstance(this).setString("Img", detailsBean.getMyHelpVehicleRescueDetails().getAvatar());
                intent = new Intent();
                intent.putExtra("one", detailsBean.getMyHelpVehicleRescueDetails().getSeekHelpUserId() + "");
                intent.putExtra("userName", detailsBean.getMyHelpVehicleRescueDetails().getNickName());
                intent.setClass(this, IntentChatActivity.class);
                startActivity(intent);
                break;
            case R.id.help_detail_phone:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            case R.id.help_detail_confirm:
                if (detailsBean.getMyHelpVehicleRescueDetails().getHR_RescueState() != 2)
                    confirmHelp();
                break;
            case R.id.help_detail_icon:
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

    private void confirmHelp() {
        if (MySharedPreferences.isLogin(this)) {
            String url = PortUtil.SetHelpRescueStatus;
            Map<String, String> map = new HashMap<>();
            map.put("currId", MySharedPreferences.getUserId(this));
            map.put("status", "4");
            map.put("HR_ID", detailsBean.getMyHelpVehicleRescueDetails().getID() + "");
            NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    try {
                        JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                        if ("10000".equals(jsonObject.getString("code"))) {
                            Toast.makeText(HelpDetailActivity.this, "完成帮助", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(HelpDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(HelpDetailActivity.this, "网络状态不好或该条信息不存在", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != musicPlayerUtils)
            musicPlayerUtils.stop();
    }
}
