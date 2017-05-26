package com.hyzs.onekeyhelp.mine.activity;

import android.app.Dialog;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.Map;

public class ChangePWDActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack, mOldClear, mNewClear, mNewAgainClear;
    private TextView mTitle, mSure, mCancel, mError;
    private EditText mOldEdit, mNewEdit, mNewAgainEdit;

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mSure = (TextView) findViewById(R.id.activity_change_pwd_sure);
        mCancel = (TextView) findViewById(R.id.activity_change_pwd_cancel);
        mOldClear = (ImageView) findViewById(R.id.activity_change_pwd_old_clear);
        mNewClear = (ImageView) findViewById(R.id.activity_change_pwd_new_clear);
        mNewAgainClear = (ImageView) findViewById(R.id.activity_change_pwd_newAgain_clear);
        mOldEdit = (EditText) findViewById(R.id.activity_change_pwd_old);
        mNewEdit = (EditText) findViewById(R.id.activity_change_pwd_new);
        mNewAgainEdit = (EditText) findViewById(R.id.activity_change_pwd_newAgain);
        mError = (TextView) findViewById(R.id.activity_change_pwd_error);
    }

    @Override
    protected void initView() {
        mTitle.setText("修改密码");
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mOldClear.setOnClickListener(this);
        mNewClear.setOnClickListener(this);
        mNewAgainClear.setOnClickListener(this);
        mSure.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mOldEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mOldClear.setVisibility(View.VISIBLE);
                } else mOldClear.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNewEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mNewClear.setVisibility(View.VISIBLE);
                } else mNewClear.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNewAgainEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mError.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(s)) {
                    mNewAgainClear.setVisibility(View.VISIBLE);
                } else mNewAgainClear.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pwd;
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
            case R.id.activity_change_pwd_old_clear:
                mOldEdit.getText().clear();
                break;
            case R.id.activity_change_pwd_new_clear:
                mNewEdit.getText().clear();
                break;
            case R.id.activity_change_pwd_newAgain_clear:
                mNewAgainEdit.getText().clear();
                break;
            case R.id.activity_change_pwd_sure:
                if (!mNewAgainEdit.getText().toString().equals(mNewEdit.getText().toString())) {
                    mError.setVisibility(View.VISIBLE);
                    return;
                }
                Map<String, String> param = new ArrayMap<>();
                param.put("currId", MySharedPreferences.getUserId(this));
                param.put("oldpwd", mOldEdit.getText().toString());
                param.put("newpwd", mNewEdit.getText().toString());
                final Dialog dialog = ProgressDialog.createProgressLoading(ChangePWDActivity.this);
                dialog.show();
                NetRequest.ParamDialogPostRequest(PortUtil.ChangePWD, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                        switch (bean.getCode()) {
                            case "10000":
                                Toast.makeText(ChangePWDActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            break;
                            case "10001":
                                Toast.makeText(ChangePWDActivity.this, bean.getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                            case "10002":
                                Toast.makeText(ChangePWDActivity.this, bean.getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                        dialog.dismiss();
                    }
                }, new NetRequest.getDataFailCallback1() {
                    @Override
                    public void getData(Exception error) {

                    }
                });
                break;
            case R.id.activity_change_pwd_cancel:
                finish();
                break;
        }
    }
}
