package com.hyzs.onekeyhelp.news;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyzs.onekeyhelp.MyApplication;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;

public class NewsWebActivity extends BaseActivity implements View.OnClickListener {

    private WebView mWebView;
    private WebSettings webSettings;
    private ImageView mBack;
    private TextView toolbar_title;
    private Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void assignView() {
        mWebView = (WebView) findViewById(R.id.new_webview);
        webSettings = mWebView.getSettings();
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
    }

    @Override
    protected void initView() {
        mProgressDialog = ProgressDialog.createProgressLoading(this);
        mWebView.requestFocusFromTouch();
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        mBack.setOnClickListener(this);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressDialog.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 加载网页失败时处理 如：提示失败，或显示新的界面
                mProgressDialog.dismiss();
                ToastUtils.showShort(MyApplication.getAppContext(),"网页加载失败");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_web;
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("communityNoticeID"))) {
            toolbar_title.setText("社区公告详情");
            try {
                mWebView.loadUrl(PortUtil.CommunityNoticeH5 + getIntent().getStringExtra("communityNoticeID"));
            } catch (Exception e) {
                Toast.makeText(this, "数据获取异常，请稍后重试", Toast.LENGTH_SHORT).show();
            }
        } else {
            toolbar_title.setText("新闻详情");
            try {
                if (!TextUtils.isEmpty(getIntent().getStringExtra("url"))) {
                    mWebView.loadUrl(getIntent().getStringExtra("url"));
                } else
                    mWebView.loadUrl(PortUtil.NewsDetails + getIntent().getStringExtra("newID"));
            } catch (Exception e) {
                Toast.makeText(this, "数据获取异常，请稍后重试", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;

        }

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
