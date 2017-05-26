package com.hyzs.onekeyhelp.mine.opinion.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.mine.opinion.adapter.OpinionAdapter;
import com.hyzs.onekeyhelp.mine.opinion.adapter.OpinionImageAdapter;
import com.hyzs.onekeyhelp.mine.opinion.bean.OpinionBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.BitmapUtil;
import com.hyzs.onekeyhelp.util.EditNotInputSpace;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.MyTextWatcher;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OpinionActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "OpinionActivity";
    private ImageView back;
    private TextView title, type;
    private ImageView add;
    private LinearLayout down;
    private EditText suggest,mTitle;
    private Button submit;
    private PopupWindow mPopupWindow;
    private RecyclerView recyclerView;
    private PopupWindow mSubmitPop;
    private LinearLayout ll;
    private OpinionBean bean;
    private OpinionAdapter adapter;
    private ListView popListView;
    private int currPosition = -1;
    private OpinionImageAdapter mGridAdapter;
    private Dialog dialog;
    private StringBuilder sb;
    @Override
    protected void assignView() {
        sb = new StringBuilder();
        mTitle = (EditText) findViewById(R.id.activity_opinion_title);
        dialog = ProgressDialog.createProgressLoading(this);
        type = (TextView) findViewById(R.id.activity_opinion_type);
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        add = (ImageView) findViewById(R.id.activity_opinion_add_img);
        down = (LinearLayout) findViewById(R.id.activity_opinion_down);
        suggest = (EditText) findViewById(R.id.activity_opinion_suggest);
        submit = (Button) findViewById(R.id.activity_opinion_submit);
        recyclerView = (RecyclerView) findViewById(R.id.activity_opinion_recycler);
        ll = (LinearLayout) findViewById(R.id.linearLayout);
        EditNotInputSpace.setEditTextInhibitInputSpace(suggest);
        EditNotInputSpace.setEditTextInhibitInputSpace(mTitle);
        initSubmitPop();
    }

    @Override
    protected void initView() {
        title.setText("建议反馈");
        suggest.addTextChangedListener(new MyTextWatcher(suggest));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mGridAdapter = new OpinionImageAdapter(OpinionActivity.this,3);
        recyclerView.setAdapter(mGridAdapter);
    }

    @Override
    protected void initListener() {
        down.setOnClickListener(this);
        submit.setOnClickListener(this);
        add.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_opinion;
    }

    @Override
    protected void initData() {

    }

    private void initPop(View v) {
        View popupView = getLayoutInflater().inflate(R.layout.pop_sort, null);
        Log.e(TAG, "initPop: " + down.getWidth());
        Log.e(TAG, "initPop: " + down.getHeight());
        mPopupWindow = new PopupWindow(popupView, down.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popListView = (ListView) popupView.findViewById(R.id.pop_sort_list);
        initPopListView(v, popListView);
    }

    private void initPopListView(final View v, final ListView popListView) {
        if (bean != null) {
            initListView(v, popListView);
            return;
        }
        Request build = new Request.Builder().url(PortUtil.SuggestionFeedback_Handler+"?action=Get_SF_Type").build();
        new OkHttpClient().newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = UrlDecodeUtil.urlCode(response.body().string());
                Log.e(TAG, "onResponse: " + json);
                bean = new Gson().fromJson(json, OpinionBean.class);
                OpinionActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initListView(v, popListView);
                    }
                });
            }
        });
    }

    private void initListView(View v, ListView listView) {
        adapter = new OpinionAdapter(this, bean.getSfc());
        listView.setAdapter(adapter);
        popListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currPosition = position;
                type.setText(bean.getSfc().get(position).getSFC_Name());
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.showAsDropDown(v);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_opinion_add_img:
//                Album.startAlbum(OpinionActivity.this, 100, 3, ContextCompat.getColor(this, R.color.color_1ccd9b), ContextCompat.getColor(this, R.color.color_1ccd9b));
                Intent intent = new Intent(this, AlbumActivity.class);
                intent.putExtra("limitCount", 3);
                startActivityForResult(intent,100);
                break;
            case R.id.activity_opinion_submit:
                if (TextUtils.isEmpty(mTitle.getText().toString())) {
                    Toast.makeText(this, "请输入标题", Toast.LENGTH_LONG).show();
                    return;
                }
                if (currPosition == -1) {
                    Toast.makeText(this, "请选择反馈模块", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(suggest.getText().toString())) {
                    Toast.makeText(this, "请输入建议反馈", Toast.LENGTH_LONG).show();
                    return;
                }
                dialog.show();
                if (pathList != null) {
                    for (int i = 0; i < pathList.size(); i++) {
                        getPicPath(pathList.get(i), i);
                    }
                } else publishRequest();
                break;
            case R.id.activity_opinion_down:
                initPop(v);
                break;
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

    private void publishRequest() {
        String url = PortUtil.SuggestionFeedback_Handler;
        Map<String, String> map = new HashMap<>();
        map.put("action", "SF_Save");
        map.put("SF_UserID", MySharedPreferences.getUserId(this));
        map.put("SF_Title",mTitle.getText().toString() );
        map.put("SF_Content", suggest.getText().toString());
        map.put("SF_Type", bean.getSfc().get(currPosition).getSFC_ID()+"");
        map.put("SF_Image1",sb == null ? "" : sb.toString());
        map.put("SF_Image2","");
        map.put("SF_Image3","");
        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if ("10000".equals(jsonObject.getString("code"))){
                        dialog.dismiss();
                        ll.setBackgroundColor(ContextCompat.getColor(OpinionActivity.this, R.color.color_f4));
                        mSubmitPop.showAtLocation(ll, Gravity.CENTER, 0, 0);
                    }else
                        Toast.makeText(OpinionActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initSubmitPop() {
        View popupView = getLayoutInflater().inflate(R.layout.submit_pop, null);
        mSubmitPop = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mSubmitPop.setTouchable(true);
        mSubmitPop.setOutsideTouchable(true);
        mSubmitPop.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mSubmitPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finish();
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
                    if (pathList.size() + Album.parseResult(data).size() > 3) {
                        ToastUtils.showShort(OpinionActivity.this, "选择上传的图片不能大于3张，请删除后再选择");
                        return;
                    }
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(OpinionActivity.this, "取消上传");
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
