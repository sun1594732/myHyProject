package com.hyzs.onekeyhelp.module.housekeeping.reservation.activity;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.RatingBar;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluateOrderActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "EvaluateOrderActivity";
    private ImageView back, call_phone, chat;
    private TextView title, right;
    private RoundImageView icon;
    private TextView name, toll_number, toll_unit, time1, time2;
    private RadioButton btn_left, btn_center, btn_right;
    private RadioGroup radioGroup;
    private EditText content;
    private ImageView add_icon;
    private OpinionImageAdapter mGridAdapter;
    private RecyclerView recyclerView;
    private LinearLayout ll;
    private PopupWindow mSubmitPop;
    private StringBuilder sb;
    private Dialog dialog;
    private String sContent;
    private RatingBar ratingBar;
    private int ratingCount = 10;
    private int radioIndex = 0;

    @Override
    protected void assignView() {
        ll = (LinearLayout) findViewById(R.id.activity_evaluate_order);
        sb = new StringBuilder();
        mSubmitPop = HelpDialog.createDialogNoAlert(this, this, "发布成功", 0);
        dialog = ProgressDialog.createProgressLoading(this);
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        right = (TextView) findViewById(R.id.toolbar_right);
        icon = (RoundImageView) findViewById(R.id.activity_evaluate_order_icon);
        name = (TextView) findViewById(R.id.activity_evaluate_order_name);
        toll_number = (TextView) findViewById(R.id.activity_evaluate_order_toll_number);
        toll_unit = (TextView) findViewById(R.id.activity_evaluate_order_toll_unit);
        time1 = (TextView) findViewById(R.id.activity_evaluate_order_time1);
        time2 = (TextView) findViewById(R.id.activity_evaluate_order_time2);
        btn_left = (RadioButton) findViewById(R.id.activity_evaluate_order_left);
        btn_center = (RadioButton) findViewById(R.id.activity_evaluate_order_center);
        btn_right = (RadioButton) findViewById(R.id.activity_evaluate_order_right);
        radioGroup = (RadioGroup) findViewById(R.id.activity_evaluate_order_radio_group);
        content = (EditText) findViewById(R.id.activity_evaluate_order_content);
        call_phone = (ImageView) findViewById(R.id.activity_evaluate_order_call_phone);
        chat = (ImageView) findViewById(R.id.activity_evaluate_order_chat);
        recyclerView = (RecyclerView) findViewById(R.id.activity_opinion_recycler);
        add_icon = (ImageView) findViewById(R.id.activity_opinion_add_img);
        ratingBar = (RatingBar) findViewById(R.id.activity_evaluate_order_rating_bar);
    }

    @Override
    protected void initView() {
        title.setText("订单评价");
        right.setText("发布");
        right.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGridAdapter = new OpinionImageAdapter(EvaluateOrderActivity.this, 4);
        recyclerView.setAdapter(mGridAdapter);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        right.setOnClickListener(this);
        call_phone.setOnClickListener(this);
        chat.setOnClickListener(this);
        add_icon.setOnClickListener(this);
        ratingBar.setClickable(true);//设置可否点击
        ratingBar.setStar(5.0f);//设置显示的星星个数
        ratingBar.setStepSize(RatingBar.StepSize.Full);//设置每次点击增加一颗星还是半颗星
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {//点击星星变化后选中的个数
                EvaluateOrderActivity.this.ratingCount = (int) ratingCount * 2;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_order;
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
            case R.id.toolbar_right:
                submit();
                break;
            case R.id.activity_opinion_add_img:
                Intent intent = new Intent(this, AlbumActivity.class);
                intent.putExtra("limitCount", 4);
                startActivityForResult(intent, 100);
                break;
            case R.id.activity_evaluate_order_call_phone:
                break;
            case R.id.activity_evaluate_order_chat:
                break;
        }
    }

    private void submit() {
        sContent = content.getText().toString();
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.activity_evaluate_order_left:
                radioIndex = 0;
                break;
            case R.id.activity_evaluate_order_center:
                radioIndex = 1;
                break;
            case R.id.activity_evaluate_order_right:
                radioIndex = 2;
                break;
        }

        if (TextUtils.isEmpty(sContent)) {
            Toast.makeText(this, "请输入评价内容", Toast.LENGTH_SHORT).show();
            return;
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
        final String url = PortUtil.LifeServiceCommentAdd;
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("LSC_LS_ID", getIntent().getStringExtra("serviceId"));
        map.put("LSC_OrderID", getIntent().getStringExtra("orderId"));
        map.put("LSC_Content", sContent);
        map.put("LSC_UserId", MySharedPreferences.getUserId(this));
        map.put("LSC_DOP", radioIndex + "");
        map.put("LSC_Satisficing", ratingCount + "");
        map.put("LSC_AffixList", sb == null ? "" : sb.toString());
        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if (jsonObject.getString("code").equals("10000")) {
//                        setResult(1);
                        dialog.dismiss();
                        mSubmitPop.showAtLocation(ll, Gravity.CENTER, 0, 0);
                    } else {
                        if (jsonObject.getString("code").equals("10001"))
                            Toast.makeText(EvaluateOrderActivity.this, "发布失败请重试", Toast.LENGTH_SHORT).show();
                        Toast.makeText(EvaluateOrderActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                        ToastUtils.showShort(EvaluateOrderActivity.this, "选择上传的图片不能大于4张，请删除后再选择");
                        return;
                    }
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(EvaluateOrderActivity.this, "取消上传");
            }
        }
    }

    private void handleSelectImage(List<String> pathList) {
        mGridAdapter.notifyDataSetChanged(pathList);
        if (pathList == null || pathList.size() == 0) {
            add_icon.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            add_icon.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
