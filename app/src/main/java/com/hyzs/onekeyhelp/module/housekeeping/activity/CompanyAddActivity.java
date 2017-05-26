package com.hyzs.onekeyhelp.module.housekeeping.activity;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.contact.bean.ResultBean;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.family.movable.bean.MovabledynamicStateBean;
import com.hyzs.onekeyhelp.home.adapter.GridAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.ProjectListGridAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.bean.ProjectListBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.BitmapUtil;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.GridViewForScrollView;
import com.hyzs.onekeyhelp.widget.wheel.BirthDialog;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumActivity;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CompanyAddActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTitle, mTime, mMapLocation, mSubmit;
    private ImageView mBack, mNoPic, mLogoNopic;
    private EditText mName, mNumber, mPosition, mTel, mLocation, mInfo;
    private GridViewForScrollView mGridView;
    private BirthDialog mBirthDialog;
    private List<String> pathList;
    private RecyclerView mRecyclerView;
    private GridAdapter mGridAdapter;
    private StringBuilder sb;
    private String pointX, pointY, LOGOURL ,pointInfo;
    private ProjectListGridAdapter adapter;
    private PopupWindow pop;
    private Dialog mProgressDialog;

    @Override
    protected void assignView() {
        mTime = (TextView) findViewById(R.id.activity_company_add_time);
        mNoPic = (ImageView) findViewById(R.id.activity_company_add_nonePic);
        mLogoNopic = (ImageView) findViewById(R.id.activity_company_add_nonePic1);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mName = (EditText) findViewById(R.id.activity_company_add_name);
        mNumber = (EditText) findViewById(R.id.activity_company_add_number);
        mPosition = (EditText) findViewById(R.id.activity_company_add_position);
        mLocation = (EditText) findViewById(R.id.activity_company_add_location);
        mTel = (EditText) findViewById(R.id.activity_company_add_tel);
        mInfo = (EditText) findViewById(R.id.activity_company_add_info);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_company_add_recycler);
        mGridView = (GridViewForScrollView) findViewById(R.id.activity_company_add_grid);
        mMapLocation = (TextView) findViewById(R.id.activity_company_add_mapLocation);
        mSubmit = (TextView) findViewById(R.id.activity_company_add_submit);
        mProgressDialog = ProgressDialog.createProgressLoading(this);
    }

    @Override
    protected void initView() {
        sb = new StringBuilder();
        mTitle.setText("申请加入");
        mBirthDialog = new BirthDialog(this);
        String systemTime = getSystemTime();
        String[] split = systemTime.split("-");
        mBirthDialog.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGridAdapter = new GridAdapter(CompanyAddActivity.this);
        mRecyclerView.setAdapter(mGridAdapter);
        pop = HelpDialog.createDialogNoAlert(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                finish();
            }
        }, "申请成功，请耐心等待", R.mipmap.bingo);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mNoPic.setOnClickListener(this);
        mTime.setOnClickListener(this);
        mLogoNopic.setOnClickListener(this);
        mMapLocation.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mBirthDialog.setBirthdayListener(new BirthDialog.OnBirthListener() {
            @Override
            public void onClick(String year, String month, String day) {
                mTime.setText(year + "-" + month + "-" + day);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_add;
    }

    @Override
    protected void initData() {
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", "0");
        param.put("pageSize", "99");
        param.put("pageIndex", "1");
        NetRequest.ParamPostRequest(PortUtil.LifeServiceSP, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                ProjectListBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ProjectListBean.class);
                if (bean.getLifeServiceSP().size() != 0) {
                    adapter = new ProjectListGridAdapter(bean.getLifeServiceSP(), CompanyAddActivity.this);
                    mGridView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_company_add_nonePic:
                Intent intent = new Intent(this, AlbumActivity.class);
                intent.putExtra("limitCount", 8);
                startActivityForResult(intent, 100);
                break;
            case R.id.activity_company_add_time:
                mBirthDialog.show();
                break;
            case R.id.activity_company_add_mapLocation:
                startActivityForResult(new Intent(this, MapViewActivity.class), 200);
                break;
            case R.id.activity_company_add_nonePic1:
                Intent intent1 = new Intent(this, AlbumActivity.class);
                intent1.putExtra("limitCount", 1);
                startActivityForResult(intent1, 300);
                break;
            case R.id.activity_company_add_submit:
                mProgressDialog.show();
                if (pathList != null) {
                    for (int i = 0; i < pathList.size(); i++) {
                        getPicPath(pathList.get(i), i);
                    }
                } else publishRequest();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.requestFocus();
        mTitle.isFocusableInTouchMode();
    }

    private String getSystemTime() {
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(l);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) { // 成功选择了照片。
                if (pathList == null) {
                    pathList = Album.parseResult(data);
                } else {
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(CompanyAddActivity.this, "取消上传");
            }
        } else if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                pointX = data.getStringExtra("pointX");
                pointY = data.getStringExtra("pointY");
                pointInfo = data.getStringExtra("pointInfo");
                mLocation.setText(pointInfo);
                mMapLocation.setText(pointInfo);
            }
        } else if (requestCode == 300) {
            if (resultCode == RESULT_OK) {
                mLogoNopic.setImageBitmap(BitmapFactory.decodeFile(Album.parseResult(data).get(0)));
                getLogoPicPath(Album.parseResult(data).get(0));
            }
        }
    }

    private void handleSelectImage(List<String> pathList) {
        mGridAdapter.notifyDataSetChanged(pathList);
        if (pathList == null || pathList.size() == 0) {
            mNoPic.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mNoPic.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void getPicPath(String path, final int position) {
        byte[] datas = BitmapUtil.getImage(path);
        String encodeString = android.util.Base64.encodeToString(datas, Base64.DEFAULT);
        Map<String, String> param = new HashMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("FileBase64", encodeString);
        param.put("FileType", "0");
        NetRequest.PictureUpRequest(param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                PublishPicBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PublishPicBean.class);
                if (position == pathList.size() - 1) {
                    sb.append("{" + bean.getUrl() + "}");
                    publishRequest();
                } else {
                    sb.append("{" + bean.getUrl() + "},");
                }
            }
        });
    }

    private void getLogoPicPath(String path) {
        byte[] datas = BitmapUtil.getImage(path);
        String encodeString = android.util.Base64.encodeToString(datas, Base64.DEFAULT);
        Map<String, String> param = new HashMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("FileBase64", encodeString);
        param.put("FileType", "0");
        NetRequest.PictureUpRequest(param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                PublishPicBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PublishPicBean.class);
                LOGOURL = bean.getUrl();
            }
        });
    }

    private void publishRequest() {
        if (TextUtils.isEmpty(mName.getText().toString()) | TextUtils.isEmpty(mNumber.getText().toString()) | TextUtils.isEmpty(mTime.getText().toString())
                | TextUtils.isEmpty(mPosition.getText().toString()) | TextUtils.isEmpty(mTel.getText().toString())
                | TextUtils.isEmpty(mLocation.getText().toString()) | TextUtils.isEmpty(mInfo.getText().toString())) {
            ToastSingleUtil.showToast(this, "请完善信息");
            return;
        }
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("OSIA_Name", mName.getText().toString());
        param.put("OSIA_BusinessLicense", mNumber.getText().toString());
        param.put("OSIA_SetUpDate", mTime.getText().toString());
        param.put("OSIA_Desc", mPosition.getText().toString());
        param.put("OSIA_Lng", pointY);
        param.put("OSIA_Lat", pointX);
        param.put("OSIA_AffiList", sb.toString());
        param.put("OSIA_Tel", mTel.getText().toString());
        param.put("OSIA_Address", mLocation.getText().toString());
        param.put("OSIA_Intro", mInfo.getText().toString());
        param.put("OSIA_SP", ProjectListGridAdapter.getProject());
        param.put("img", LOGOURL);
        NetRequest.ParamDialogPostRequest(PortUtil.LifeServiceOrganizationApplyJoin, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                if ("10000".equals(bean.getCode())) {
                    pop.showAtLocation(mTitle, Gravity.CENTER, 0, 0);
                } else ToastUtils.showShort(CompanyAddActivity.this, bean.getMessage());
                mProgressDialog.dismiss();
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {
                mProgressDialog.dismiss();
            }
        });
    }
}
