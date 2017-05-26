package com.hyzs.onekeyhelp.mine.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.carresuce.bean.MyResuceOkBean;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineAlbumSendActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack, mNoneView;
    private TextView mTitle, mRight, album_group;
    private AlertDialog mChooseGroup;
    private String mGroupId = "0";
    private GridAdapter mGridAdapter;
    private List<String> pathList;
    private StringBuilder sb;
    private RecyclerView mRecyclerView;
    private EditText mEdit;
    private LinearLayout select_group;
    private Dialog dialog;

    @Override
    protected void assignView() {
        sb = new StringBuilder();
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mRight = (TextView) findViewById(R.id.toolbar_right);
        album_group = (TextView) findViewById(R.id.album_group);
        mNoneView = (ImageView) findViewById(R.id.activity_life_help_publish_nonePic);
        mEdit = (EditText) findViewById(R.id.activity_life_help_publish_edit);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_life_help_publish_recycler);
        select_group = (LinearLayout) findViewById(R.id.select_group);
        dialog = ProgressDialog.createProgressLoading(this);
    }

    @Override
    protected void initView() {
        mBack.setOnClickListener(this);
        mRight.setOnClickListener(this);
        mNoneView.setOnClickListener(this);
        select_group.setOnClickListener(this);
        mRight.setVisibility(View.VISIBLE);
        mTitle.setText("上传图片");
        mRight.setText("保存");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGridAdapter = new GridAdapter(MineAlbumSendActivity.this);
        mRecyclerView.setAdapter(mGridAdapter);
    }


    private void initDialog() {
        View DialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog_mine_album_group, null);
        assignDialogView(DialogView);
        mChooseGroup = new AlertDialog.Builder(MineAlbumSendActivity.this).create();
        mChooseGroup.show();
        mChooseGroup.getWindow().setContentView(DialogView);
        mChooseGroup.dismiss();
    }


    private void assignDialogView(View dialogView) {
        TextView sure = (TextView) dialogView.findViewById(R.id.layout_dialog_life_help_sure);
        TextView cancel = (TextView) dialogView.findViewById(R.id.layout_dialog_life_help_cancel);
        final RadioGroup rg = (RadioGroup) dialogView.findViewById(R.id.layout_dialog_album);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChooseGroup.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rg.getCheckedRadioButtonId()) {
                    case R.id.layout_dialog_life_help_all:
                        mGroupId = "0";
                        ToastUtils.showShort(MineAlbumSendActivity.this, "选择所有人分组");
                        album_group.setText("所有人");
                        break;
                    case R.id.layout_dialog_life_help_family:
                        mGroupId = "1";
                        ToastUtils.showShort(MineAlbumSendActivity.this, "选择家庭分组");
                        album_group.setText("家庭");
                        break;
                    case R.id.layout_dialog_life_help_friend:
                        mGroupId = "2";
                        ToastUtils.showShort(MineAlbumSendActivity.this, "选择朋友分组");
                        album_group.setText("朋友");
                        break;
                    case R.id.layout_dialog_life_help_neighbor:
                        mGroupId = "3";
                        ToastUtils.showShort(MineAlbumSendActivity.this, "选择邻居分组");
                        album_group.setText("邻居");
                        break;
                }
                mChooseGroup.dismiss();
            }
        });
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_album_send;
    }

    @Override
    protected void initData() {
        initDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right://保存图片发布
                dialog.show();
                if (pathList != null) {
                    for (int i = 0; i < pathList.size(); i++) {
                        getPicPath(pathList.get(i), i);
                    }
                } else publishRequest();
                break;
            case R.id.activity_life_help_publish_nonePic:
                Album.startAlbum(MineAlbumSendActivity.this, 100, 8, ContextCompat.getColor(this, R.color.color_1ccd9b), ContextCompat.getColor(this, R.color.color_1ccd9b));
                break;

            case R.id.select_group:
                mChooseGroup.show();
                break;
        }
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
                        ToastUtils.showShort(MineAlbumSendActivity.this, "选择上传的图片不能大于8张，请删除后再选择");
                        return;
                    }
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(MineAlbumSendActivity.this, "取消上传");
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

    private void getPicPath(String path, final int position) {
        byte[] datas = BitmapUtil.getImage(path);
        String encodeString = android.util.Base64.encodeToString(datas, Base64.DEFAULT);
        Map<String, String> param = new HashMap<>();
        param.put("currId", MySharedPreferences.getInstance(this).getString("uid"));
        param.put("FileBase64", encodeString);
        param.put("FileType", "0");
        NetRequest.PictureUpRequest(param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                PublishPicBean bean;
                try {
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PublishPicBean.class);
                } catch (Exception e) {
                    Toast.makeText(MineAlbumSendActivity.this, "获取数据异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (position == pathList.size() - 1) {
                    sb.append("{" + bean.getUrl() + "}");
                    publishRequest();
                } else {
                    sb.append("{" + bean.getUrl() + "},");
                }
            }
        });
    }

    private void publishRequest() {

        Map<String, String> param1 = new HashMap<>();
        param1.put("currId", MySharedPreferences.getUserId(MineAlbumSendActivity.this));
        param1.put("PA_Content", mEdit.getText().toString());
        LogUtils.e("content ::" + mEdit.getText().toString());
        param1.put("PA_LookRole", mGroupId);
        param1.put("AffixImgList", sb.toString());
        NetRequest.ParamPostRequest(PortUtil.PublishMineAlbum, param1, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {

                MyResuceOkBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MyResuceOkBean.class);
                if ("10000".equals(bean.getCode())) {
                    ToastUtils.showShort(MineAlbumSendActivity.this, "发布成功");
                    finish();
                } else ToastUtils.showShort(MineAlbumSendActivity.this, "发布失败");
                dialog.dismiss();
            }
        });
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MineAlbumSendActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MineAlbumSendActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
}
