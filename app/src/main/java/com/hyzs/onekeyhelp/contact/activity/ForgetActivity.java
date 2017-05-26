package com.hyzs.onekeyhelp.contact.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.MyApplication;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.contact.bean.ResultBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.HttpUtils;
import com.hyzs.onekeyhelp.util.InPutUtils;
import com.hyzs.onekeyhelp.util.JsonResponseHandler;
import com.hyzs.onekeyhelp.util.JsonUtils;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;


public class ForgetActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEditTextPhone, mEditTextNew, mEditTextAgain, mEditTextCode;
    private Button mButtonSubmit;
    private TextView mTextViewService, get_code;
    private ImageView mImageViewBack;
    private Dialog progressDialog;

    @Override
    protected void assignView() {
        mEditTextPhone = (EditText) findViewById(R.id.activity_forget_phone_edit);
        mEditTextNew = (EditText) findViewById(R.id.activity_forget_pwd_edit);
        mEditTextAgain = (EditText) findViewById(R.id.activity_forget_pwd_again_edit);
        mEditTextCode = (EditText) findViewById(R.id.activity_forget_code_edit);
        mButtonSubmit = (Button) findViewById(R.id.activity_forget_submit);
        mImageViewBack = (ImageView) findViewById(R.id.iv_reset_back);
        mTextViewService = (TextView) findViewById(R.id.tv_reset_service);
        get_code = (TextView) findViewById(R.id.activity_forget_getCode);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        mButtonSubmit.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
        mTextViewService.setOnClickListener(this);
        get_code.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.iv_reset_back:// 返回
                    finish();
                    break;
                case R.id.tv_reset_service:// 客服
                    String service_phone = "400-628-7198";
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.CALL");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse("tel:" + service_phone));
                    // 开启系统拨号器
                    startActivity(intent);
                    break;
                case R.id.activity_forget_getCode:// 验证码
                    String phone = mEditTextPhone.getText().toString();
                    if (!InPutUtils.isMobilePhone(phone)) {
                        ToastUtils.showShort(MyApplication.getAppContext(), "请输入正确的手机号!");
                        return;
                    }
                    if (InPutUtils.replaceBlank(get_code.getText().toString()).equals("重发验证")
                            || InPutUtils.replaceBlank(get_code.getText().toString()).equals("获取验证码")) {
                        Map<String, String> param = new ArrayMap<>();
                        param.put("phone", phone);
                        param.put("type", "4");
                        NetRequest.ParamDialogPostRequest(PortUtil.SendMessage, param, new NetRequest.getDataCallback() {
                            @Override
                            public void getData(String data) {
                            }
                        }, new NetRequest.getDataFailCallback1() {
                            @Override
                            public void getData(Exception error) {
                                ToastUtils.showShort(ForgetActivity.this,"验证码发送失败");
                            }
                        });
                        setExpireTime();
                    }
                    break;
                case R.id.activity_forget_submit:// 提交
                    String user_phone = mEditTextPhone.getText().toString();
                    String password_new = mEditTextNew.getText().toString();
                    String password_agin = mEditTextAgain.getText().toString();
                    String code = mEditTextCode.getText().toString();
                    if (TextUtils.isEmpty(user_phone)) {
                        ToastUtils.showShort(MyApplication.getAppContext(), "请输入您的电话");
                    } else if (!InPutUtils.isMobilePhone(user_phone)) {
                        Toast.makeText(MyApplication.getAppContext(), "手机号不正确", Toast.LENGTH_SHORT).show();
                        ToastUtils.showShort(MyApplication.getAppContext(), "请输入您的电话");
                    } else if (TextUtils.isEmpty(password_new)) {
                        ToastUtils.showShort(MyApplication.getAppContext(), "请输入您的新密码");
                    } else if (TextUtils.isEmpty(password_agin)) {
                        ToastUtils.showShort(MyApplication.getAppContext(), "请再次输入您的新密码");
                    } else if (!password_new.equals(password_agin)) {
                        ToastUtils.showShort(MyApplication.getAppContext(), "您的新密码输入有误");
                    } else if (TextUtils.isEmpty(code)) {
                        ToastUtils.showShort(MyApplication.getAppContext(), "验证码不能为空");
                    } else {
                        resetPassword(user_phone, password_new, code);
                    }
                    break;

                default:
                    break;
            }
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
                    ToastUtils.showShort(MyApplication.getAppContext(), msg.obj.toString());
                    break;
                case 1:
                    ToastUtils.showShort(MyApplication.getAppContext(), msg.obj.toString());
                    setExpireTime();
                    break;
                case RE_ISSUED_WAIT:
                    get_code.setText(time + "秒");
                    break;
                case RE_ISSUED:
                    get_code.setText(" 重发验证  ");
                    break;
                case 5:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    private void resetPassword(final String user_phone, final String password_new, final String code) {
        progressDialog = ProgressDialog.createProgressLoading(this);
        progressDialog.show();
        final String md5_pass = md5(password_new);
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", user_phone);
        map.put("code", code);
        map.put("newpwd", md5_pass);
        NetRequest.ParamPostRequest(PortUtil.ResetPwd, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                LogUtils.e("forgetactivuty : " + UrlDecodeUtil.urlCode(data));
                DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data),DataStatusBean.class);
                if ("10000".equals(bean.getCode())) {
                    ToastUtils.showShort(ForgetActivity.this,"修改成功");
                    finish();
                }else ToastUtils.showShort(ForgetActivity.this,bean.getMessage());
                progressDialog.dismiss();
            }
        });
    }
//        HttpUtils.post(MyApplication.getAppContext(), Api.INDEX.USER_RESET_PASSWORD, map, new JsonResponseHandler() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                ToastUtils.showShort(MyApplication.getAppContext(), "密码重置失败");
//            }
//
//            @Override
//            public void onSuccess(String result, int id) {
//                if (JsonUtils.isSuccess(result)) {
//                    Gson gson = new Gson();
//                    ResultBean user = gson.fromJson(result, ResultBean.class);
//                    if ("1".equals(user.getData())) {
//                        ToastUtils.showShort(MyApplication.getAppContext(), "密码重置成功");
//                        pop();
//                    } else if ("-1".equals(user.getData())) {
//                        ToastUtils.showShort(MyApplication.getAppContext(), user.getError());
//                    }
//                } else {
//                    ToastUtils.showShort(MyApplication.getAppContext(), "验证码错误");
//                }
//            }
//        });
//    }

    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ForgetActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ForgetActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

}
