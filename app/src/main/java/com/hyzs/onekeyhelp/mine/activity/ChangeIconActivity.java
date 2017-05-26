package com.hyzs.onekeyhelp.mine.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangeIconActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mChangeIcon;
    private TextView mSubmit, mTitle;
    private Bitmap bitmap;
    private StringBuilder sb;
    private Dialog progressLoading;
    private ImageView mBack;
    private CircleImageView mUserIcon;
    private List<String> pathList;
    private EditText mName;

    @Override
    protected void assignView() {
        mUserIcon = (CircleImageView) findViewById(R.id.person_message_image);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mChangeIcon = (LinearLayout) findViewById(R.id.person_message_image_ll);
        mSubmit = (TextView) findViewById(R.id.person_message_submit);
//        mName = (EditText) findViewById(R.id.person_message_name_tv);
    }

    @Override
    protected void initView() {
        mTitle.setText("修改资料");
        if (!TextUtils.isEmpty(MySharedPreferences.getInstance(this).getString("myImg"))) {
            NetRequest.loadImg(this, MySharedPreferences.getInstance(this).getString("myImg"), mUserIcon);
        }
//        if (!TextUtils.isEmpty(MySharedPreferences.getInstance(this).getString("myName"))) {
//            mName.setText(MySharedPreferences.getInstance(this).getString("myName"));
//        }
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mChangeIcon.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_icon;
    }

    @Override
    protected void initData() {
        sb = new StringBuilder();
    }

    private void updateUserImage(String imagePath) {
        progressLoading = ProgressDialog.createProgressLoading(this);
        progressLoading.show();
        String encodeString = android.util.Base64.encodeToString(getImage(imagePath), Base64.DEFAULT);
        Map<String, String> param = new HashMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("FileBase64", encodeString);
        param.put("FileType", "0");
        NetRequest.ParamDialogPostRequest(PortUtil.AvatarUpLoad, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                PublishPicBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PublishPicBean.class);
                if (!TextUtils.isEmpty(bean.getUrl())) {
                    MySharedPreferences.getInstance(ChangeIconActivity.this).setString("myImg", bean.getUrl());
                    NetRequest.loadImg(ChangeIconActivity.this, MySharedPreferences.getInstance(ChangeIconActivity.this).getString("myImg"), mUserIcon);
                    ToastUtils.showShort(ChangeIconActivity.this, "操作成功");
                }
                progressLoading.dismiss();
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_message_image_ll:
                Intent intent = new Intent(this,AlbumActivity.class);
                intent.putExtra("limitCount",1);
                startActivityForResult(intent,100);
                break;
            case R.id.person_message_submit:
                if (bitmap == null) {
                    Toast.makeText(this, "未选择上传头像", Toast.LENGTH_SHORT).show();
                    return;
                }
                updateUserImage(pathList.get(0));
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) { // 成功选择了照片。
                // 选择好了照片后，调用这个方法解析照片路径的List。
                pathList = Album.parseResult(data);
                bitmap = BitmapFactory.decodeFile(pathList.get(0));
                mUserIcon.setImageBitmap(bitmap);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(ChangeIconActivity.this, "取消上传");
            }
        }
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static byte[] compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 85, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 50) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());

        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return baos.toByteArray();
    }

    /**
     * 图片按比例大小压缩方法（根据路径获取图片并压缩）
     *
     * @param srcPath
     * @return
     */
    public static byte[] getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }
}
