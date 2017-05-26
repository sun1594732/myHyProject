package com.hyzs.onekeyhelp.mine.activity;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hyzs.onekeyhelp.MainActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.home.activity.HomeHospitalActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class MineAuthenticationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle, mRightBtn;
    private WebView mWebView;
    private WebSettings webSettings;
    private VRefresh vRefresh;
    private LinearLayout mLL;
    private PopupWindow pop;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 2;
    final String WebUrl = PortUtil.EZZX+"?userid=";

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
        mTitle.setText("身份认证");
        mRightBtn.setText("完成");
        mRightBtn.setVisibility(View.VISIBLE);
        initWebView();
    }

    private void initWebView() {
        webSettings = mWebView.getSettings();
        mWebView.requestFocusFromTouch();
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        mWebView.loadUrl(WebUrl + MySharedPreferences.getUserId(this)+"&hid_communityid="+MySharedPreferences.getCommunityId(this));
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void openFileChooser(com.tencent.smtt.sdk.ValueCallback<Uri> valueCallback, String s, String s1) {
                mUploadMessage = valueCallback;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }


            // For Lollipop 5.0+ Devices
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                try {
                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                } catch (ActivityNotFoundException e) {
                    uploadMessage = null;
                    Toast.makeText(getBaseContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

            //For Android 4.1 only
            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }

            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, final com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
                pop = HelpDialog.createDialogNoAlert(MineAuthenticationActivity.this, new View.OnClickListener() {
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
                mWebView.loadUrl(WebUrl + MySharedPreferences.getUserId(MineAuthenticationActivity.this));
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
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != MainActivity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else
            Toast.makeText(getBaseContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:
                mWebView.loadUrl("javascript:Save_Authentication()");
                break;
        }
    }
}
