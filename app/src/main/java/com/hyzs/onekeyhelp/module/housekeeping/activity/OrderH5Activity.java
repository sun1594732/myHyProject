package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.mine.bean.MineInfoBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.Map;

import static com.hyzs.onekeyhelp.netRequest.PortUtil.LifeServiceOrderList;


public class OrderH5Activity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle, mRightBtn;
    private com.tencent.smtt.sdk.WebView mWebView;
    private LinearLayout mLL;
    PopupWindow pop;
    String WebUrl = null;

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mWebView = (com.tencent.smtt.sdk.WebView) findViewById(R.id.activity_mine_edit_web);
        mRightBtn = (TextView) findViewById(R.id.toolbar_right);
        mLL = (LinearLayout) findViewById(R.id.activity_mine_edit);
    }

    @Override
    protected void initView() {
        if (getIntent().getBooleanExtra("mine", false)) {
            WebUrl = PortUtil.LifeServiceOrderList + "?currId=" + MySharedPreferences.getUserId(this) + "&LSO_SPP_ID=0&LSO_LS_ID=0&phonetype=0&type="+getIntent().getIntExtra("type",0);
        } else
            WebUrl = LifeServiceOrderList + "?currId=" + MySharedPreferences.getUserId(this) + "&LSO_SPP_ID=" + getIntent().getIntExtra("sppId", 1) + "&LSO_LS_ID=" + getIntent().getIntExtra("lsId", 1) + "&phonetype=0";
        mTitle.setText("订单列表");
        mRightBtn.setText("订单");
        mRightBtn.setVisibility(View.GONE);
        mWebView.loadUrl(WebUrl);
        com.tencent.smtt.sdk.WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JsInterface(), "jsObj");
        mWebView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
                return false;
            }
        });
        mWebView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            @Override
            public boolean onJsAlert(com.tencent.smtt.sdk.WebView webView, String s, String s1, final com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
                pop = HelpDialog.createDialogNoAlert(OrderH5Activity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop.dismiss();
                        jsResult.confirm();

                    }
                }, s1, R.mipmap.bingo);
                pop.showAtLocation(mLL, Gravity.CENTER, 0, 0);
                return true;
            }
        });
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_edit;
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:
                break;
        }
    }

    private class JsInterface {
        /**
         * js中通过window.jsObj.HtmlcallJava2("参数") 可以调用此方法并且把js中input中的值作为参数传入，
         */
        @JavascriptInterface
        public void JsChat(final String userId) {
            final Map<String, String> param = new ArrayMap<>();
            param.put("currId", userId);
            NetRequest.ParamPostRequest(PortUtil.MineInfo, param, new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    MineInfoBean.PersonalUserInfoBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineInfoBean.class).getPersonalUserInfo();
                    MySharedPreferences.getInstance(OrderH5Activity.this).setString("Img", bean.getFace());
                    NotifyMsgCountUtil.notifyMsg(userId);
                    Intent intent1 = new Intent();
                    intent1.putExtra("one", userId);
                    intent1.putExtra("userName", bean.getUesrname());
                    intent1.setClass(OrderH5Activity.this, IntentChatActivity.class);
                    startActivity(intent1);
                }
            });

        }

        @JavascriptInterface
        public void JsRecommend(String serviceId, String orderId) {
            Intent intent1 = new Intent();
            intent1.putExtra("serviceId", serviceId);
            intent1.putExtra("orderId", orderId);
            intent1.setClass(OrderH5Activity.this, IntentChatActivity.class);
            startActivity(intent1);
        }
    }

}
