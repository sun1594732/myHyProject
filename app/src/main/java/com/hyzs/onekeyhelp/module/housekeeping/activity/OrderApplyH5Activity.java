package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.mine.activity.MineEditActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.widget.view.VRefresh;

public class OrderApplyH5Activity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle, mRightBtn;
    private com.tencent.smtt.sdk.WebView mWebView;
    private VRefresh vRefresh;
    private LinearLayout mLL;
    PopupWindow pop;
    String WebUrl = null;

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mWebView = (com.tencent.smtt.sdk.WebView) findViewById(R.id.activity_mine_edit_web);
        mRightBtn = (TextView) findViewById(R.id.toolbar_right);
        vRefresh = (VRefresh) findViewById(R.id.vRefresh);
        mLL = (LinearLayout) findViewById(R.id.activity_mine_edit);
    }

    @Override
    protected void initView() {
        WebUrl = PortUtil.LifeServiceOrderApply + "?currId=" + MySharedPreferences.getUserId(this) + "&LSO_SPP_ID=" + getIntent().getIntExtra("sppId", 1) + "&LSO_LS_ID=" + getIntent().getIntExtra("lsId", 1)+"&phonetype=0";
        mTitle.setText("预约下单");
        mRightBtn.setText("订单");
        mRightBtn.setVisibility(View.VISIBLE);
        mRightBtn.setOnClickListener(this);
        mWebView.loadUrl(WebUrl);
        com.tencent.smtt.sdk.WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
                return false;
            }
        });
        mWebView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            @Override
            public boolean onJsAlert(final com.tencent.smtt.sdk.WebView webView, String s, final String s1, final com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
                pop = HelpDialog.createDialogNoAlert(OrderApplyH5Activity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop.dismiss();
                        jsResult.confirm();
                        if ("0".equals(s1.substring(0,1))) {
                            webView.loadUrl(s1.substring(2,s1.length()-1));
//                            startActivity(new Intent(OrderApplyH5Activity.this,OrderH5Activity.class));
                        }
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
                Intent intent = new Intent(this,OrderH5Activity.class);
                intent.putExtra("sppId",getIntent().getIntExtra("sppId", 1));
                intent.putExtra("lsId",getIntent().getIntExtra("lsId", 1));
                startActivity(intent);
                break;
        }
    }
}
