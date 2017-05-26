package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.mine.opinion.adapter.OpinionImageAdapter;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.BitmapUtil;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.InPutUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceComplaintsActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ServiceComplaintsActivi";
    private ImageView back,add;
    private TextView title,name,submit;
    private RoundImageView icon;
    private EditText content, phone;
    private RecyclerView recyclerView;
    private String sContent;
    private String sPhone;
    private Dialog dialog;
    private PopupWindow mSubmitPop;
    private LinearLayout ll;
    private StringBuilder sb;
    private OpinionImageAdapter mGridAdapter;
    @Override
    protected void assignView() {
        sb = new StringBuilder();
        ll = (LinearLayout) findViewById(R.id.activity_service_complaints);
        dialog = ProgressDialog.createProgressLoading(this);
        mSubmitPop = HelpDialog.createDialogNoAlert(this, this, "您的投诉已提交，我们会尽快处理。", 0);
        submit = (TextView) findViewById(R.id.activity_service_complaints_submit);
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        name = (TextView) findViewById(R.id.activity_service_complaints_name);
        icon = (RoundImageView) findViewById(R.id.activity_service_complaints_icon);
        add = (ImageView) findViewById(R.id.activity_opinion_add_img);
        content = (EditText) findViewById(R.id.activity_service_complaints_content);
        phone = (EditText) findViewById(R.id.activity_service_complaints_phone);
        recyclerView = (RecyclerView) findViewById(R.id.activity_opinion_recycler);
    }

    @Override
    protected void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGridAdapter = new OpinionImageAdapter(this, 4);
        recyclerView.setAdapter(mGridAdapter);
        title.setText("客户投入");
        name.setText(getIntent().getStringExtra("name"));
        if (TextUtils.isEmpty(getIntent().getStringExtra("icon"))) {
            icon.setImageResource(R.drawable.icon_replace);
        } else NetRequest.loadImg(this, getIntent().getStringExtra("icon"), icon);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        add.setOnClickListener(this);
        submit.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_complaints;
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
            case R.id.activity_service_complaints_submit:
                submit();
                break;
            case R.id.activity_opinion_add_img:
                Intent intent = new Intent(this, AlbumActivity.class);
                intent.putExtra("limitCount", 4);
                startActivityForResult(intent, 100);
                break;
            case R.id.help_pop_confirm:
                mSubmitPop.dismiss();
                finish();
                break;
        }
    }

    private void submit() {
        sContent = content.getText().toString();
        sPhone = phone.getText().toString();
        if (TextUtils.isEmpty(sContent)) {
            Toast.makeText(this, "请输入投诉内容", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(sPhone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_LONG).show();
            return;
        }
        if (!InPutUtils.isMobilePhone(sPhone)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        }
        dialog.show();
        if (pathList != null) {
            for (int i = 0; i < pathList.size(); i++) {
                getPicPath(pathList.get(i), i);
            }
        } else publishRequest();
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
                PublishPicBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PublishPicBean.class);
                if (position == pathList.size() - 1) {
                    sb.append("{").append(bean.getUrl()).append("}");
                    publishRequest();
                } else {
                    sb.append("{").append(bean.getUrl()).append("},");
                }
            }
        });
    }

    private void publishRequest() {
        final String url = PortUtil.LifeServiceComplainAdd;
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("LSC_LS_ID", getIntent().getStringExtra("LS_ID"));
        map.put("LSC_Content", sContent);
        map.put("LSC_Phone", sPhone);
        map.put("LSC_AfixList", sb == null ? "" : sb.toString());


        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    Log.e(TAG, "getData: "+UrlDecodeUtil.urlCode(data) );
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if (jsonObject.getString("code").equals("10000")) {
//                        setResult(1);
                        dialog.dismiss();
                        mSubmitPop.showAtLocation(ll, Gravity.CENTER, 0, 0);
                    } else {
                        if (jsonObject.getString("code").equals("10001"))
                            Toast.makeText(ServiceComplaintsActivity.this, "提交失败请重试", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ServiceComplaintsActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                }
            }
        });
    }

    private List<String> pathList;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) { // 成功选择了照片。
                // 选择好了照片后，调用这个方法解析照片路径的List。
                if (pathList == null) {
                    pathList = Album.parseResult(data);
                } else {
                    if (pathList.size() + Album.parseResult(data).size() > 4) {
                        ToastUtils.showShort(this, "选择上传的图片不能大于4张，请删除后再选择");
                        return;
                    }
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(this, "取消上传");
            }
        }
    }
    private void handleSelectImage(List<String> pathList) {
        mGridAdapter.notifyDataSetChanged(pathList);
        if (pathList == null || pathList.size() == 0) {
            add.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            add.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
