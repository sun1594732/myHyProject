package com.hyzs.onekeyhelp.contact.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyzs.onekeyhelp.MyApplication;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.LoginStatusBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.InPutUtils;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MyAsycnTask;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, MyAsycnTask.MyCallBack {
    MySharedPreferences mySharedPreferences;
    EditText mAccountEdit, mPassWordEdit;
    Button mLoginBtn, mChangeBtn;
    ImageView mPhoneClear, mPassWordClear, mBack;
    TextView mGetCode, mTitle, mRightBtn, mForget;
    CheckBox mRemember, mAutoLogin;
    Context mContext;
    Dialog loading;
    boolean LoginByCode = true;
    boolean pass = false;//用于记录是否记住密码
    /**
     * 短信验证码
     */
    private String co;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        mySharedPreferences = MySharedPreferences.getInstance(this);
        mContext = this;
        init();
    }

    private void init() {
        mBack = (ImageView) findViewById(R.id.activity_login_back);
        mySharedPreferences = MySharedPreferences.getInstance(this);
        mChangeBtn = (Button) findViewById(R.id.activity_login_changeBtn);
        mAccountEdit = (EditText) findViewById(R.id.activity_login_phone_edit);
        mPassWordEdit = (EditText) findViewById(R.id.activity_login_passwrod_edit);
        mLoginBtn = (Button) findViewById(R.id.activity_login_loginBtn);
        mGetCode = (TextView) findViewById(R.id.activity_login_getCode);
        mTitle = (TextView) findViewById(R.id.activity_login_title);
        mRightBtn = (TextView) findViewById(R.id.activity_login_register);
        mRemember = (CheckBox) findViewById(R.id.activity_login_remember);
        mAutoLogin = (CheckBox) findViewById(R.id.activity_login_auto);
        mForget = (TextView) findViewById(R.id.activity_login_forget);
        mPhoneClear = (ImageView) findViewById(R.id.phone_clear);
        mPassWordClear = (ImageView) findViewById(R.id.password_clear);
        mPhoneClear.setOnClickListener(this);
        mPassWordClear.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mGetCode.setOnClickListener(this);
        mChangeBtn.setOnClickListener(this);// 切换登录方法按钮
        mRightBtn.setOnClickListener(this);// 跳转注册
        mRemember.setOnClickListener(this);//记住密码
        mForget.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mAccountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mPhoneClear.setVisibility(View.VISIBLE);
                } else mPhoneClear.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (!TextUtils.isEmpty(mySharedPreferences.getString("userAccount"))) {
            mAccountEdit.setText(mySharedPreferences.getString("userAccount"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_back:
                finish();
                break;
            case R.id.activity_login_register:
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
                break;
            //点击切换登录方法
            case R.id.activity_login_changeBtn:
                if (LoginByCode) {
                    mGetCode.setVisibility(View.GONE);
                    mPassWordEdit.setHint("请输入密码");
                    mTitle.setText("账号登录");
                    mChangeBtn.setText("使用手机验证码登录");
                    LoginByCode = false;
                } else {
                    mGetCode.setVisibility(View.VISIBLE);
                    mPassWordEdit.setHint("请输入验证码");
                    mTitle.setText("手机号登录");
                    mChangeBtn.setText("使用账号密码登录");
                    LoginByCode = true;
                }
                break;
            case R.id.activity_login_loginBtn:
                String eidt_phone = mAccountEdit.getText().toString().trim();
                String edit_code = mPassWordEdit.getText().toString().trim();
                if (TextUtils.isEmpty(eidt_phone)) {
                    Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edit_code)) {
                    Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
                } else if (!InPutUtils.isMobilePhone(eidt_phone)) {
                    Toast.makeText(mContext, "手机号不正确", Toast.LENGTH_SHORT).show();
                } else if (LoginByCode && edit_code.length() != 4) {
                    Toast.makeText(mContext, "验证码错误", Toast.LENGTH_SHORT).show();
                } else {
                    login(eidt_phone, edit_code);
                }
                break;
            case R.id.activity_login_getCode:
                String phone1 = mAccountEdit.getText().toString();
                if (!InPutUtils.isMobilePhone(phone1)) {
                    ToastUtils.showShort(mContext, "请输入正确的手机号!");
                    return;
                }
                if (InPutUtils.replaceBlank(mGetCode.getText().toString()).equals("重发验证")
                        || InPutUtils.replaceBlank(mGetCode.getText().toString()).equals("获取验证码")) {
                    Map<String, String> param = new ArrayMap<>();
                    param.put("phone", phone1);
                    param.put("type", "1");
                    NetRequest.ParamDialogPostRequest(PortUtil.SendMessage, param, new NetRequest.getDataCallback() {
                        @Override
                        public void getData(String data) {
                        }
                    }, new NetRequest.getDataFailCallback1() {
                        @Override
                        public void getData(Exception error) {
                            ToastUtils.showShort(LoginActivity.this, "验证码发送失败");
                        }
                    });
                    setExpireTime();
                }
                break;
            case R.id.activity_login_forget:
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                break;
            case R.id.phone_clear:
                mAccountEdit.getText().clear();
                break;
            case R.id.password_clear:
                mPassWordEdit.getText().clear();
                break;
        }
    }

    @Override
    public void SendResult(String result) {
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

    private Handler mJPushHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            // 调用 JPush 接口来设置别名。
            if (JPushInterface.isPushStopped(MyApplication.getAppContext())) {
                JPushInterface.resumePush(MyApplication.getAppContext());
            }
            JPushInterface.setAliasAndTags(MyApplication.getAppContext(),
                    MySharedPreferences.getUserId(LoginActivity.this),
                    null,
                    mAliasCallback);
        }
    };
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ToastUtils.showShort(mContext, "登陆成功");
                    finish();
                    break;
                case 1:
                    setExpireTime();

                    break;
                case RE_ISSUED_WAIT:
                    mGetCode.setText(time + "秒");
                    break;
                case RE_ISSUED:
                    mGetCode.setText(" 重发验证  ");
                    break;
                case 5:
                    break;
                default:
                    break;
            }
        }
    };

    public void login(final String phone, final String code) {
        loading = ProgressDialog.createProgressLoading(LoginActivity.this);
        loading.show();
        Map<String, String> map = new ArrayMap<String, String>();
        map.put("username", phone);
        if (LoginByCode) {
            map.put("code", code);
        } else
            map.put("password", code);
        NetRequest.ParamDialogPostRequest(PortUtil.denglu, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    LoginStatusBean loginBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), LoginStatusBean.class);
                    //存id存到本地
                    if ("10000".equals(loginBean.getCode())) {
                        Toast.makeText(mContext, "登陆成功", Toast.LENGTH_SHORT).show();
                        if (mRemember.isChecked()) {
                            mySharedPreferences.setString("userAccount", mAccountEdit.getText().toString());
                        } else mySharedPreferences.setString("userAccount", "");
                        if (mAutoLogin.isChecked()) {
                            mySharedPreferences.setBoolean("autoLogin", true);
                        } else mySharedPreferences.setBoolean("autoLogin", false);
                        mySharedPreferences.setString("uid", loginBean.getUsers().get(0).getId() + "");
                        mySharedPreferences.setString("myImg", loginBean.getUsers().get(0).getImg());
                        mySharedPreferences.setString("mySign", loginBean.getUsers().get(0).getNote());
                        mySharedPreferences.setString("myName", loginBean.getUsers().get(0).getUsername());
                        mySharedPreferences.setString("communityName", loginBean.getUsers().get(0).getCommunityName());
                        mySharedPreferences.setString("communityId", loginBean.getUsers().get(0).getCommunityId() + "");
                        loginByHX();
                        mJPushHandler.sendEmptyMessage(0);
                        finish();
                    } else if ("10001".equals(loginBean.getCode())) {
                        Toast.makeText(mContext, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    ToastUtils.showShort(mContext, "网络错误");
                } finally {
                    loading.dismiss();
                }
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {
                ToastUtils.showShort(mContext, "连接服务器失败");
                loading.dismiss();
            }
        });
    }

    //    设置极光别名
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    break;
                case 6002:
                    mJPushHandler.sendEmptyMessageDelayed(0, 1000 * 60);
                    break;
                default:
                    break;
            }
        }
    };


    protected void loginByHX() {
//        EMClient.getInstance().logout(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("环信登陆 ：" + mySharedPreferences.getString("uid"));
                EMClient.getInstance().login(mySharedPreferences.getString("uid"), "hyzh_2017", new EMCallBack() {
                    //成功
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                EMClient.getInstance().chatManager().loadAllConversations();
                            }
                        });
                    }

                    //失败
                    public void onError(int i, String s) {
                    }

                    public void onProgress(int i, String s) {

                    }
                });
            }
        }).start();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("LoginActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LoginActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}