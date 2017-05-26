package com.hyzs.onekeyhelp.mine.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.mine.bean.WeiXinDataBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.HttpUtils;
import com.hyzs.onekeyhelp.util.JsonResponseHandler;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.widget.RecModeWindow;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class MineChargeActivity extends BaseActivity implements View.OnClickListener {
    private static final int ZHIFUBAO = 0;
    private static final int WEIXIN = 1;
    private int REC_MODE = 0;
    private RelativeLayout mChargeMode;
    private RecModeWindow menuWindow;
    private ImageView mIcon, mBack;
    private TextView mName, mInfo;
    private EditText mEdit;
    private Button mSubmit;
    private Dialog loading;

    @Override
    protected void assignView() {
        mChargeMode = (RelativeLayout) findViewById(R.id.rl_rec_mode);
        mIcon = (ImageView) findViewById(R.id.tv_rec_mode_icon);
        mBack = (ImageView) findViewById(R.id.iv_rec_back);
        mName = (TextView) findViewById(R.id.tv_rec_mode_name);
        mInfo = (TextView) findViewById(R.id.tv_rec_mode_info);
        mSubmit = (Button) findViewById(R.id.btn_rec_submit);
        mEdit = (EditText) findViewById(R.id.et_rec_num);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        mChargeMode.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_charge;
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_rec_back:
                finish();
                break;
            case R.id.rl_rec_mode:
                // TODO: 2017/4/24  微信商户号不匹配，暂时屏蔽
//                // 实例化SelectPicPopupWindow
//                menuWindow = new RecModeWindow(MineChargeActivity.this, itemsOnClick);
//                // 显示窗口
//                menuWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.btn_rec_submit:
                final String num = mEdit.getText().toString();
                if (TextUtils.isEmpty(num)) {
                    Toast.makeText(MineChargeActivity.this, "请输入充值金额！", Toast.LENGTH_SHORT).show();
                }
                if (Double.parseDouble(num) <= 0) {
                    Toast.makeText(MineChargeActivity.this, "充值金额不能小于0！", Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (REC_MODE) {
                    case 0:
                        Toast.makeText(MineChargeActivity.this, "即将跳转到支付宝...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MineChargeActivity.this, MineAlipyH5Activity.class);
                        intent.putExtra("money", num+"");
                        startActivity(intent);
                        break;
                    case 1:
                        loading = ProgressDialog.createProgressLoading(MineChargeActivity.this);
                        loading.show();
                        HashMap<String, String> params = new HashMap<>();
                        params.put("aid", MySharedPreferences.getUserId(this));
                        params.put("money", num);
                        params.put("buydesc", "账户充值");
                        HttpUtils.post(MineChargeActivity.this, PortUtil.WXPayUrl, params, new JsonResponseHandler() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                LogUtils.e(e.getMessage());
                                loading.dismiss();
                            }

                            @Override
                            public void onError(com.squareup.okhttp.Call call, Exception e, int id) {
                                LogUtils.e(e.getMessage());
                                loading.dismiss();
                            }

                            @Override
                            public void onSuccess(String response, int id) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONObject object = jsonObject.getJSONObject("data");
                                    int sid = object.getInt("id");
                                    startWeiXin(sid);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                loading.dismiss();
                            }
                        });
                        break;
                }
                break;
        }
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.rl_mode_zhifubao:
                    REC_MODE = ZHIFUBAO;
                    mIcon.setImageResource(R.drawable.zhifubao_logo);
                    mName.setText("支付宝");
                    mInfo.setText("数亿用户都在用，安全可托付");
                    break;
                case R.id.rl_mode_weixin:
                    REC_MODE = WEIXIN;
                    mIcon.setImageResource(R.drawable.weixin_logo);
                    mName.setText("微信支付");
                    mInfo.setText("亿万用户的选择，更快更安全");
                    break;
            }
        }
    };

    protected void startWeiXin(int sid) {

        Map<String, String> params = new HashMap<>();
        params.put("sid", sid + "");
        HttpUtils.get(MineChargeActivity.this, PortUtil.START_WEIXIN, params, new JsonResponseHandler() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(e.getMessage());
            }

            @Override
            public void onError(com.squareup.okhttp.Call call, Exception e, int id) {

            }

            @Override
            public void onSuccess(String response, int id) {
                WeiXinDataBean json = new Gson().fromJson(response, WeiXinDataBean.class);
                IWXAPI api = WXAPIFactory.createWXAPI(MineChargeActivity.this, "wx4aa889feeb9d8dcb");
                api.registerApp(json.getAppid());
                PayReq request = new PayReq();
                request.appId = json.getAppid();
                request.partnerId = json.getPartnerid();
                request.prepayId = json.getPrepayid();
                request.packageValue = json.getPackageX();
                request.nonceStr = json.getNoncestr();
                request.timeStamp = json.getTimestamp();
                request.sign = json.getSign();
                api.sendReq(request);
                boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                if (isPaySupported) {
                    Toast.makeText(MineChargeActivity.this, "跳转到微信支付...", Toast.LENGTH_SHORT).show();
                    MineChargeActivity.this.finish();
                } else {
                    Toast.makeText(MineChargeActivity.this, "抱歉，你的手机未安装微信或不支持微信支付！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MineChargeActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MineChargeActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

}
