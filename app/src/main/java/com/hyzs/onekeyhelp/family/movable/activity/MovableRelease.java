package com.hyzs.onekeyhelp.family.movable.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.home.adapter.GridAdapter;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.BitmapUtil;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.album.Album;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.bitmap;

/**
 * Created by Administrator on 2017/4/6.
 */

public class MovableRelease extends BaseActivity implements View.OnClickListener {
    private ImageView mBack, mNoneView;
    private TextView mTitle, mToolBarRight;
    private RecyclerView mRecyclerView;
    private GridAdapter mGridAdapter;
    private List<String> pathList;
    private EditText mEdittext;
    private StringBuilder sb;
    private Dialog dialog;

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolBarRight = (TextView) findViewById(R.id.toolbar_right);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_circle_publish_recycler);
        mNoneView = (ImageView) findViewById(R.id.activity_circle_publish_nonePic);
        mEdittext = (EditText) findViewById(R.id.activity_circle_publish_edit);
        dialog = ProgressDialog.createProgressLoading(this);
    }

    @Override
    protected void initView() {
        mEdittext.setHint("请输入活动动态信息，(限制字数在1000字之内)");
        mTitle.setText("发动态");
        mToolBarRight.setText("发布");
        mToolBarRight.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGridAdapter = new GridAdapter(MovableRelease.this);
        mRecyclerView.setAdapter(mGridAdapter);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mNoneView.setOnClickListener(this);
        mToolBarRight.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_publish;
    }

    @Override
    protected void initData() {
        sb = new StringBuilder();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_circle_publish_nonePic:
                Album.startAlbum(MovableRelease.this, 100, 8, ContextCompat.getColor(this, R.color.color_1ccd9b), ContextCompat.getColor(this, R.color.color_1ccd9b));
                break;
            case R.id.toolbar_right:
                if (TextUtils.isEmpty(mEdittext.getText())) {
                    ToastUtils.showShort(MovableRelease.this, "请输入发布信息");
                    break;
                }
                dialog.show();
                if (pathList != null) {
                    for (int i = 0; i < pathList.size(); i++) {
                        getPicPath(pathList.get(i), i);
                    }
                } else publishRequest();
                break;
        }
    }

    private void publishRequest() {
        Map<String, String> param1 = new HashMap<>();
        param1.put("currId", MySharedPreferences.getUserId(this)); //  15-3
        param1.put("activity_id", getIntent().getIntExtra("movableId", 0) + "");
        param1.put("activity_type", getIntent().getIntExtra("type",3)+"");  //周末生活
        param1.put("ed_type", "0");  //0动态，1评论
        param1.put("ed_Content", mEdittext.getText().toString());
        param1.put("ed_AffixImgList", sb.toString());
        NetRequest.ParamDialogPostRequest(PortUtil.PublishActivityDynamic, param1, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                if ("10000".equals(bean.getCode())) {
                    ToastUtils.showShort(MovableRelease.this, "发布成功");
                    finish();
                } else ToastUtils.showShort(MovableRelease.this, "发布失败");
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {
                ToastUtils.showShort(MovableRelease.this, "发布失败");
                dialog.dismiss();
                error.printStackTrace();
            }
        });
    }

    private void getPicPath(String path, final int position) {
        byte[] datas = BitmapUtil.getImage(path);
        String encodeString = Base64.encodeToString(datas, Base64.DEFAULT);
        Map<String, String> param = new HashMap<>();
        param.put("currId", MySharedPreferences.getInstance(this).getString("uid"));
        param.put("FileBase64", encodeString);
        param.put("FileType", "0");
        NetRequest.PictureUpRequest(param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                PublishPicBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PublishPicBean.class);
                if (position == pathList.size() - 1) {
                    LogUtils.e(UrlDecodeUtil.urlCode(data) + "           -------" + position + "         " + pathList.size());
                    sb.append("{" + bean.getUrl() + "}");
                    publishRequest();
                } else {
                    LogUtils.e(UrlDecodeUtil.urlCode(data) + "           -------" + position + "         " + pathList.size());
                    sb.append("{" + bean.getUrl() + "},");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) { // 成功选择了照片。
                // 选择好了照片后，调用这个方法解析照片路径的List。
                if (pathList == null) {
                    pathList = Album.parseResult(data);
                } else {
                    if (pathList.size() + Album.parseResult(data).size() > 8) {
                        ToastUtils.showShort(MovableRelease.this, "选择上传的图片不能大于8张，请删除后再选择");
                        return;
                    }
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(MovableRelease.this, "取消上传");
            }
        }
    }

    private void handleSelectImage(List<String> pathList) {
        mGridAdapter.notifyDataSetChanged(pathList);
        if (pathList == null || pathList.size() == 0) {
            mNoneView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mNoneView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    //图片转换成二进制
    public byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        return baos.toByteArray();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != dialog&&dialog.isShowing())
            dialog.dismiss();
    }
}
