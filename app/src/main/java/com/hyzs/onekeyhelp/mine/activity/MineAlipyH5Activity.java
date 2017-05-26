package com.hyzs.onekeyhelp.mine.activity;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.umeng.analytics.MobclickAgent;

import static com.hyzs.onekeyhelp.R.id.zhifu_web;

public class MineAlipyH5Activity extends BaseActivity {
    WebView mWebView;
    String mUrl = null;
    WebViewClient wvc = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,
                                                WebResourceRequest request) {
            view.loadUrl(mUrl);
            return true;
        }

    };

    WebChromeClient wcc = new WebChromeClient() {

        public void onRequestFocus(WebView view) {
            super.onRequestFocus(view);
            view.requestFocus();
        }
    };
    @Override
    protected void assignView() {
        mWebView = (WebView) findViewById(zhifu_web);
    }

    @Override
    protected void initView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mUrl = "http://alpay.hy-bb.cn/?num="+getIntent().getStringExtra("money")+"&uid="+ MySharedPreferences.getUserId(this);
        WebSettings ws = mWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setSaveFormData(false);
        ws.setAppCacheEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 是否允许缩放
        ws.setBuiltInZoomControls(true);
        mWebView.setWebViewClient(wvc);
        mWebView.setWebChromeClient(wcc);
        mWebView.loadUrl(mUrl);
        mWebView.addJavascriptInterface(new ZhiFuInterface(), "ZhiFuInterface");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_alipy_h5;
    }

    @Override
    protected void initData() {

    }
    class ZhiFuInterface {
        @JavascriptInterface
        public void complete() {
            Toast.makeText(MineAlipyH5Activity.this, "支付完成", Toast.LENGTH_SHORT);
            finish();
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MineAlipyH5Activity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MineAlipyH5Activity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
}
