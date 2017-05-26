package com.hyzs.onekeyhelp.home.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.mine.activity.MineEditActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.widget.view.VRefresh;

public class HomeServiceH5Activity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle, mRightBtn;
    private WebView mWebView;
    private WebSettings webSettings;
    private VRefresh vRefresh;
    private LinearLayout mLL;
    PopupWindow pop;
    final String WebUrl = PortUtil.ServiceH5;

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
        mTitle.setText("生活服务");
//        mRightBtn.setText("完成");
//        mRightBtn.setVisibility(View.VISIBLE);
        initWebView();
    }

    private void initWebView() {
        webSettings = mWebView.getSettings();
        mWebView.requestFocusFromTouch();
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        mWebView.loadUrl(WebUrl);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                pop = HelpDialog.createDialogNoAlert(HomeServiceH5Activity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop.dismiss();
                        result.confirm();
                    }
                }, message, R.mipmap.bingo);
                pop.showAtLocation(mLL, Gravity.CENTER, 0, 0);
                return true;
            }

            //设置响应js 的Confirm()函数
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(HomeServiceH5Activity.this);
                b.setTitle("Confirm");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
                b.create().show();
                return true;
            }
            //设置响应js 的Prompt()函数
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 开始加载网页时处理 如：显示"加载提示" 的加载对话框
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 网页加载完成时处理  如：让 加载对话框 消失
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 加载网页失败时处理 如：提示失败，或显示新的界面
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
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
                mWebView.loadUrl(WebUrl);
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
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }else
                finish();
                break;
            case R.id.toolbar_right:
                break;
        }
    }
}
