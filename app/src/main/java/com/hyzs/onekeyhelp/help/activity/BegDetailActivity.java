package com.hyzs.onekeyhelp.help.activity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.carresuce.bean.MyResuceDetailsBean;
import com.hyzs.onekeyhelp.help.adapter.HelpDetailListAdapter;
import com.hyzs.onekeyhelp.help.bean.BegDetailListBean;
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

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


public class BegDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "BegDetailActivity";
    private ImageView back;
    private TextView title;
    private String VR_ID;
    private String ResuceDetailsUrl = PortUtil.ResuceDetail;
    private OkHttpClient okHttpClient;
    private Request request;
    private HashMap hms;
    private LinearLayout ll;

    private RoundImageView icon;
    private TextView name, zheng, status, address, time, confirm, voice, shang, fu, wu, jiu, ke, jie, lin, jin, wei, jia;
    private ImageView identity, chat, phone;
    private ListView listView;
    private Dialog dialog;

    MyResuceDetailsBean myResuceDetailsBean;
    private RelativeLayout mEmptyView;
    private BegDetailListBean bean;
    private HelpDetailListAdapter adapter;
    private MusicPlayerUtils musicPlayerUtils;



    @Override
    protected void assignView() {
        Log.e(TAG, "assignView: " );
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
        listView = (ListView) findViewById(R.id.help_detail_list);
        ll = (LinearLayout) findViewById(R.id.activity_help_detail_ll);

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

    @Override
    protected void initView() {
        title.setText("我的求助-详情");
        confirm.setText("确认求助");
        chat.setVisibility(View.INVISIBLE);
        phone.setVisibility(View.INVISIBLE);
        ll.setVisibility(View.INVISIBLE);
        musicPlayerUtils = new MusicPlayerUtils(this);
    }

    @Override
    protected void initListener() {
        voice.setOnClickListener(this);
        back.setOnClickListener(this);
        confirm.setOnClickListener(this);
        icon.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_detail;
    }

    @Override
    protected void initData() {
        Log.e(TAG, "requestMyData: 23333333" );
        hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this));
        hms.put("VR_ID", VR_ID + "");
        requestMyData(hms, ResuceDetailsUrl);
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
                Log.e(TAG, "requestMyData: 22222222" +data);
                try {
                    mEmptyView.setVisibility(View.GONE);
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), BegDetailListBean.class);
                    initList();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        });
    }

    private void initList() {
        adapter = new HelpDetailListAdapter(this, bean.getComplateSeekHelpUserListCommon());
        listView.setAdapter(adapter);
    }

    /**
     * 联网请求数据
     */
    private void requestMyData(HashMap hms, String MWEBUrl) {

        Log.e(TAG, "requestMyData: 11111111" );
        NetRequest.ParamPostRequest(MWEBUrl, hms, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                Log.e(TAG, "requestMyData: 11111111"+data );
                myResuceDetailsBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MyResuceDetailsBean.class);
                setParame();
            }
        });
    }


    private void setParame() {
        MyResuceDetailsBean.MyHelpVehicleRescueDetailsBean bean = myResuceDetailsBean.getMyHelpVehicleRescueDetails();
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
        if (0 == bean.getNCCS()) {
            zheng.setVisibility(View.GONE);
        } else zheng.setVisibility(View.VISIBLE);
        address.setText(bean.getGPSDesc());
        time.setText(getTime(Long.parseLong(bean.getSeek_Time() + "")));
        if (0 == bean.getRNA()) {
            identity.setVisibility(View.GONE);
        } else identity.setVisibility(View.VISIBLE);
        switch (bean.getSeek_State()) {
            case 1:
                status.setText("等待救援");
                status.setTextColor(getResources().getColor(R.color.color_f7221b));
                confirm.setText("确认求助");
                confirm.setVisibility(View.VISIBLE);
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
        dialog.dismiss();
    }

    private String getTime(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(l * 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.help_detail_voice:
                if (musicPlayerUtils.mediaPlayer.isPlaying()) {
                    return;
                }
                if (!TextUtils.isEmpty(myResuceDetailsBean.getMyHelpVehicleRescueDetails().getSeek_Voice())) {
                    musicPlayerUtils.playUrl(myResuceDetailsBean.getMyHelpVehicleRescueDetails().getSeek_Voice());
                    voice.setBackground(ContextCompat.getDrawable(BegDetailActivity.this,R.drawable.shape_red_solid));
                    musicPlayerUtils.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            voice.setBackground(ContextCompat.getDrawable(BegDetailActivity.this,R.drawable.shape_1ccd9b_6_soild));
                        }
                    });
                }
                break;
            case R.id.help_detail_confirm:
                confirmHelp();
                break;
            case R.id.help_detail_icon:
                if (!MySharedPreferences.isLogin(this)) {
                    ToastUtils.showShort(this, "请先登录.");
                    return;
                }
                Intent intent = new Intent(this, MineHomeActivity.class);
                intent.putExtra("targetUserId", MySharedPreferences.getUserId(this));
                startActivity(intent);
                break;
        }
    }

    private void confirmHelp() {
        if (!MySharedPreferences.isLogin(this)) {
            Toast.makeText(this, "您还没登录", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = PortUtil.ResuceDetailState;
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("VR_ID", myResuceDetailsBean.getMyHelpVehicleRescueDetails().getID() + "");
        if (confirm.getText().toString().trim().equals("确认求助")) {
            map.put("State", "1");
        } else {
            map.put("State", "0");
        }
        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if ("10000".equals(jsonObject.getString("code"))) {
                        Toast.makeText(BegDetailActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(BegDetailActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(BegDetailActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != musicPlayerUtils)
            musicPlayerUtils.stop();
    }
}
