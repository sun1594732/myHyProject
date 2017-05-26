package com.hyzs.onekeyhelp.contact.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.RegisterStatusBean;
import com.hyzs.onekeyhelp.home.adapter.HomeChooseCommunityAdapter;
import com.hyzs.onekeyhelp.home.bean.HomeCommunityListBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.InPutUtils;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MD5Util;
import com.hyzs.onekeyhelp.util.MyAsycnTask;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, MyAsycnTask.MyCallBack {
    Context mContext;
    private TextView mGetCode, mLoginTypeChange, mTitle, mChooseCoummunity;// 获取验证码
    private Button zhuce_but, mChangeMode; //注册按钮
    private EditText phone_edit, code_edit, mNickName, mTrueName; //手机号和验证码
    private ImageView mBack;
    private Dialog mLodingDialog, mChooseCommunity;
    private HomeCommunityListBean CommunityListBean;
    private String community, communityId;
    private boolean codeLogin = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        mLodingDialog = ProgressDialog.createProgressLoading(RegisterActivity.this);
        initDialog();
        initListener();
    }

    private void initListener() {
        mTrueName = (EditText) findViewById(R.id.zhuce_trueName_edittext);
        mNickName = (EditText) findViewById(R.id.zhuce_nickName_edittext);
        mChooseCoummunity = (TextView) findViewById(R.id.zhuce_community_edittext);
        mGetCode = (TextView) findViewById(R.id.getcode_zhece);//获取验证码按钮
        zhuce_but = (Button) findViewById(R.id.zhuce_button_re);//注册按钮
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        phone_edit = (EditText) findViewById(R.id.zhuce_phone_edittext);//手机号输入框
        code_edit = (EditText) findViewById(R.id.zhuce_code_edittext);//验证码输入框
        mLoginTypeChange = (TextView) findViewById(R.id.toolbar_right);//登录跳转
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mChangeMode = (Button) findViewById(R.id.zhuce_button_changeMode);
        mTitle.setText("手机号注册");
        mLoginTypeChange.setText("登录");
        mLoginTypeChange.setVisibility(View.VISIBLE);
        mGetCode.setOnClickListener(this);
        zhuce_but.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mLoginTypeChange.setOnClickListener(this);
        mChooseCoummunity.setOnClickListener(this);
        mChangeMode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 界面点击事件
        switch (v.getId()) {
            case R.id.zhuce_community_edittext:
                mChooseCommunity.show();
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.zhuce_button_changeMode:
                if (codeLogin) {
                    codeLogin = false;
                    mChangeMode.setText("使用手机验证码注册");
                    mGetCode.setVisibility(View.INVISIBLE);
                    code_edit.setHint("请输入密码");
                } else {
                    codeLogin = true;
                    mChangeMode.setText("使用账户密码注册");
                    mGetCode.setVisibility(View.VISIBLE);
                    code_edit.setHint("请输入验证码");
                }
                break;
            case R.id.zhuce_button_re:// 注册
                String edit_phone = phone_edit.getText().toString().trim();//手机号
                String edit_code = code_edit.getText().toString().trim();//验证码
                if (TextUtils.isEmpty(edit_phone)) {
                    Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edit_code)) {
                    Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
                } else if (!InPutUtils.isMobilePhone(edit_phone)) {
                    Toast.makeText(mContext, "手机号不正确", Toast.LENGTH_SHORT).show();
                } else if (codeLogin && edit_code.length() != 4) {
                    Toast.makeText(mContext, "验证码错误", Toast.LENGTH_SHORT).show();
                } else {
                    login(edit_phone, edit_code);
                }
                break;
            case R.id.getcode_zhece:
                String phone = phone_edit.getText().toString();
                if (!InPutUtils.isMobilePhone(phone)) {
                    ToastUtils.showShort(mContext, "请输入正确的手机号!");
                    return;
                }
                if (InPutUtils.replaceBlank(mGetCode.getText().toString()).equals("重发验证")
                        || InPutUtils.replaceBlank(mGetCode.getText().toString()).equals("获取验证码")) {
                    Map<String, String> param = new ArrayMap<>();
                    param.put("phone", phone);
                    param.put("type", "2");
                    NetRequest.ParamDialogPostRequest(PortUtil.SendMessage, param, new NetRequest.getDataCallback() {
                        @Override
                        public void getData(String data) {
                        }
                    }, new NetRequest.getDataFailCallback1() {
                        @Override
                        public void getData(Exception error) {
                            ToastUtils.showShort(RegisterActivity.this, "验证码发送失败");
                        }
                    });
                    setExpireTime();
                }
                break;
            case R.id.toolbar_right:
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private Timer timer;
    private int time;

    /**
     * 剩余时间定时器
     */
    public void setExpireTime() {
        if (timer != null) {
            timer.cancel();
        }
        time = 120;
        TimerTask task = new TimerTask() {
            public void run() {
                time--;
                if (time > 0) {
                    mHandler.sendEmptyMessage(RE_ISSUED_WAIT);
                } else {
                    if (timer != null) {
                        timer.cancel();
                    }
                    mHandler.sendEmptyMessage(RE_ISSUED);
                }
            }
        };
        timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

    /* 重发验证码等待 */
    private final int RE_ISSUED_WAIT = 3;
    /* 重发验证码 */
    private final int RE_ISSUED = 4;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 0:
                    ToastUtils.showShort(mContext, "注册成功");
                    finish();
                    break;
                case 1:
                    ToastUtils.showShort(mContext, msg.obj.toString());
                    setExpireTime();
                    break;
                case RE_ISSUED_WAIT:
                    mGetCode.setText(time + "秒");
                    break;
                case RE_ISSUED:
                    mGetCode.setText(" 重发验证  ");
                    break;
                case 5:
                    ToastUtils.showShort(mContext, "注册失败");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void SendResult(String result) {
    }

    public void login(String phone, final String code) {
        if (TextUtils.isEmpty(mNickName.getText().toString())) {
            ToastUtils.showShort(RegisterActivity.this, "请填写昵称后提交");
            return;
        }
        mLodingDialog.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", phone);
        if (codeLogin) {
            map.put("code", code);
            map.put("password", "");
        } else {
            map.put("password", MD5Util.getMd5Value(code));
            map.put("code", "");
        }
        map.put("uesrname", mNickName.getText().toString());
        map.put("firstname", TextUtils.isEmpty(mTrueName.getText()) ? "" : mTrueName.getText().toString());
        map.put("homeaddress", community);
        map.put("sqxbaseid", communityId);
        NetRequest.ParamDialogPostRequest(PortUtil.Register, map, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        try {
                            RegisterStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), RegisterStatusBean.class);
                            if ("10000".equals(bean.getCode())) {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            } else ToastUtils.showShort(RegisterActivity.this, bean.getMessage());
                        } catch (Exception e) {
                            Toast.makeText(RegisterActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        } finally {
                            mLodingDialog.dismiss();
                        }
                    }
                }
                , new NetRequest.getDataFailCallback1() {
                    @Override
                    public void getData(Exception error) {
                        Toast.makeText(RegisterActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
                        mLodingDialog.dismiss();
                    }
                });
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("RegisterActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("RegisterActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    private void initDialog() {
        View DialogView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_community, null);
        assignDialogView(DialogView);
        mChooseCommunity = new AlertDialog.Builder(this).create();
        mChooseCommunity.show();
        mChooseCommunity.getWindow().setContentView(DialogView);
        mChooseCommunity.dismiss();
    }

    private void assignDialogView(View dialogView) {
        TextView sure = (TextView) dialogView.findViewById(R.id.dialog_choose_community_sure);
        TextView cancel = (TextView) dialogView.findViewById(R.id.dialog_choose_community_cancel);
        final ListView lv = (ListView) dialogView.findViewById(R.id.dialog_choose_community_lv);
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.isLogin(this) ? MySharedPreferences.getUserId(this) : "0");
        param.put("dataCount", "10");
        param.put("locationX", MySharedPreferences.getInstance(this).getString("X"));
        param.put("locationY", MySharedPreferences.getInstance(this).getString("Y"));
        NetRequest.ParamPostRequest(PortUtil.GetCommunityList, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                CommunityListBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), HomeCommunityListBean.class);
                List<String> resource = new ArrayList<String>();
                for (int i = 0; i < CommunityListBean.getHomePageGetChangeCommunityList().size(); i++) {
                    resource.add(CommunityListBean.getHomePageGetChangeCommunityList().get(i).getC_Name());
                }
                lv.setAdapter(new HomeChooseCommunityAdapter(resource, RegisterActivity.this));
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < lv.getAdapter().getCount(); i++) {
                    lv.getChildAt(i).setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.color_f));
                }
                community = CommunityListBean.getHomePageGetChangeCommunityList().get(position).getC_Name();
                communityId = CommunityListBean.getHomePageGetChangeCommunityList().get(position).getId() + "";
                view.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.color_f4));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChooseCommunity.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChooseCoummunity.setText(community);
                mChooseCoummunity.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.color_3));
                mChooseCommunity.dismiss();
            }
        });
    }
}