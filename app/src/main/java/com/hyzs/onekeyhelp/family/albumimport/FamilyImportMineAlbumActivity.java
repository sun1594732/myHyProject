package com.hyzs.onekeyhelp.family.albumimport;

import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FamilyImportMineAlbumActivity extends BaseActivity implements View.OnClickListener {

    private ListView mLv;
    private FamilyImportAlbumAdapter adapter;
    private RelativeLayout mRl;
    private ImageView mBack;
    private TextView mTitle, mToolBarRight;

    @Override
    protected void assignView() {
        mRl = (RelativeLayout) findViewById(R.id.toolbar);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolBarRight = (TextView) findViewById(R.id.toolbar_right);
        mLv = (ListView) findViewById(R.id.fragment_only_list);
    }

    @Override
    protected void initView() {
        mRl.setVisibility(View.VISIBLE);
        mTitle.setText("个人相册导入");
        mToolBarRight.setText("完成");
        mToolBarRight.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mToolBarRight.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_only_lv;
    }

    @Override
    protected void initData() {
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("pageSize", "10");
        param.put("pageIndex", "1");
        NetRequest.ParamPostRequest(PortUtil.PersonalAlbumGetNoImportDynamicList, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                ImportBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ImportBean.class);
                adapter = new FamilyImportAlbumAdapter(bean.getPersonalAlbumDynamicList(), FamilyImportMineAlbumActivity.this);
                mLv.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:
                Map<String, String> param = new ArrayMap<>();
                param.put("currId", MySharedPreferences.getUserId(this));
                param.put("groupID", getIntent().getStringExtra("familyId"));
                param.put("pid", FamilyImportAlbumAdapter.getProject());
                NetRequest.ParamPostRequest(PortUtil.FamilyAlbumImportPDynamicList, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                        if ("10000".equals(bean.getCode())) {
                            setResult(RESULT_OK);
                            finish();
                        } else
                            ToastSingleUtil.showToast(FamilyImportMineAlbumActivity.this, bean.getMessage());
                    }
                });
                break;
        }
    }
}
