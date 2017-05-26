package com.hyzs.onekeyhelp.mine.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

public class MineEditActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle, mRightBtn;
    private WebView mWebView;
    private VRefresh vRefresh;
    private LinearLayout mLL;
    PopupWindow pop;
    final String WebUrl = PortUtil.index + "?userid=";

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mWebView = (WebView) findViewById(R.id.activity_mine_edit_web);
        mRightBtn = (TextView) findViewById(R.id.toolbar_right);
        vRefresh = (VRefresh) findViewById(R.id.vRefresh);
        mLL = (LinearLayout) findViewById(R.id.activity_mine_edit);
    }

    @Override
    protected void initView() {
        vRefresh.setView(this, mWebView);
        mTitle.setText("个人信息");
        mRightBtn.setText("完成");
        mRightBtn.setVisibility(View.VISIBLE);
        mWebView.loadUrl(WebUrl + MySharedPreferences.getUserId(MineEditActivity.this));
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
                webView.loadUrl(WebUrl + MySharedPreferences.getUserId(MineEditActivity.this));
                return true;
            }
        });
        mWebView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, final com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
                pop = HelpDialog.createDialogNoAlert(MineEditActivity.this, new View.OnClickListener() {
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
        vRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(WebUrl + MySharedPreferences.getUserId(MineEditActivity.this));
                vRefresh.setRefreshing(false);
            }
        });
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
                mWebView.loadUrl("javascript:Save_bicseInfo()");
                break;
        }
    }
}
