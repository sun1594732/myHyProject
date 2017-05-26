package com.hyzs.onekeyhelp.module.market.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.home.adapter.GridAdapter;
import com.hyzs.onekeyhelp.home.adapter.HomeChooseCommunityAdapter;
import com.hyzs.onekeyhelp.mine.opinion.adapter.OpinionImageAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.activity.PersonAddActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.BitmapUtil;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发布宝贝儿
 */
public class ReleaseCommodityActivity extends BaseActivity implements View.OnClickListener {
    private EditText mName, mDes, mPrice, mPhone;
    private TextView mChooseNew, mCommit, mSend ,mTitle;
    private OpinionImageAdapter mGridAdapter;
    private ImageView mImage ,mBack;
    private Dialog dialog;
    private StringBuilder sb;
    private PopupWindow mSubmitPop;
    private RecyclerView mRecyclerView;
    private Dialog mChooseCommunity;
    private String NewNumber, SendNumber;

    @Override
    protected void assignView() {
        mName = (EditText) findViewById(R.id.activity_release_commodity_name);
        mChooseNew = (TextView) findViewById(R.id.activity_release_commodity_new);
        mDes = (EditText) findViewById(R.id.activity_release_commodity_des);
        mPrice = (EditText) findViewById(R.id.activity_release_commodity_price);
        mPhone = (EditText) findViewById(R.id.activity_release_commodity_phone);
        mCommit = (TextView) findViewById(R.id.activity_release_commodity_commit);
        mSend = (TextView) findViewById(R.id.activity_release_commodity_send);
        mImage = (ImageView) findViewById(R.id.activity_opinion_add_img);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_opinion_recycler);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
    }

    @Override
    protected void initView() {
        sb = new StringBuilder();
        mTitle.setText("发布物品");
        dialog = ProgressDialog.createProgressLoading(this);
        mSubmitPop = HelpDialog.createDialogNoAlert(this, this, "添加成功", 0);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGridAdapter = new OpinionImageAdapter(ReleaseCommodityActivity.this, 8);
        mRecyclerView.setAdapter(mGridAdapter);
    }

    @Override
    protected void initListener() {
        mImage.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mChooseNew.setOnClickListener(this);
        mCommit.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_commodity;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_opinion_add_img:
                Intent intent = new Intent(this, AlbumActivity.class);
                intent.putExtra("limitCount", 8);
                startActivityForResult(intent, 100);
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_release_commodity_new:
                initNewDialog();
                mChooseCommunity.show();
                break;
            case R.id.activity_release_commodity_commit:
                dialog.show();
                if (pathList != null) {
                    for (int i = 0; i < pathList.size(); i++) {
                        getPicPath(pathList.get(i), i);
                    }
                } else publishRequest();
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
                    sb.append("{").append(bean.getUrl()).append("}");
                    publishRequest();
                } else {
                    sb.append("{").append(bean.getUrl()).append("},");
                }
            }
        });
    }

    private void publishRequest() {
        final String url = PortUtil.ReleaseSecondGoods;
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("SHG_GoodsName", mName.getText().toString());
        map.put("SHG_GoodsDetail", mDes.getText().toString());
        map.put("SHG_Price", mPrice.getText().toString());
        map.put("SHG_Condition", NewNumber);
        map.put("SHG_IsDeliver", SendNumber);
        map.put("SHG_Contact", mPhone.getText().toString());
        map.put("SHG_ImageGroup", sb == null ? "" : sb.toString());
        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if (jsonObject.getString("code").equals("10000")) {
                        dialog.dismiss();
                        mSubmitPop.showAtLocation(mTitle, Gravity.CENTER, 0, 0);
                    } else {
                        if (jsonObject.getString("code").equals("10001"))
                            Toast.makeText(ReleaseCommodityActivity.this, "添加失败请重试", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ReleaseCommodityActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                    if (pathList.size() + Album.parseResult(data).size() > 1) {
                        ToastUtils.showShort(ReleaseCommodityActivity.this, "选择上传的图片不能大于1张，请删除后再选择");
                        return;
                    }
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(ReleaseCommodityActivity.this, "取消上传");
            }
        }
    }

    private void handleSelectImage(List<String> pathList) {
        mGridAdapter.notifyDataSetChanged(pathList);
        if (pathList == null || pathList.size() == 0) {
            mImage.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mImage.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void initNewDialog() {
        View DialogView = LayoutInflater.from(this).inflate(R.layout.dialog_only_lv, null);
        assignNewDialogView(DialogView);
        mChooseCommunity = new AlertDialog.Builder(this).create();
        mChooseCommunity.show();
        mChooseCommunity.getWindow().setContentView(DialogView);
        mChooseCommunity.dismiss();
    }

    private void assignNewDialogView(View dialogView) {
        final ListView lv = (ListView) dialogView.findViewById(R.id.dialog_only_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 7) {
                    NewNumber = position + "";
                    mChooseNew.setText("全新");
                } else {
                    NewNumber = position + "";
                    mChooseNew.setText(NewNumber + "成新");
                }
                mChooseCommunity.dismiss();
            }
        });
        List<String> resource = new ArrayList<String>();
        resource.add("1成新");
        resource.add("3成新");
        resource.add("5成新");
        resource.add("6成新");
        resource.add("7成新");
        resource.add("8成新");
        resource.add("9成新");
        resource.add("全新");
        lv.setAdapter(new HomeChooseCommunityAdapter(resource, ReleaseCommodityActivity.this));
    }

    private void initSendDialog() {
        View DialogView = LayoutInflater.from(this).inflate(R.layout.dialog_only_lv, null);
        assignSendDialogView(DialogView);
        mChooseCommunity = new AlertDialog.Builder(this).create();
        mChooseCommunity.show();
        mChooseCommunity.getWindow().setContentView(DialogView);
        mChooseCommunity.dismiss();
    }

    private void assignSendDialogView(View dialogView) {
        final ListView lv = (ListView) dialogView.findViewById(R.id.dialog_only_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    SendNumber = "1";
                    mChooseNew.setText("是");
                } else {
                    SendNumber = "0";
                    mChooseNew.setText("否");
                }
                mChooseCommunity.dismiss();
            }
        });
        List<String> resource = new ArrayList<String>();
        resource.add("是");
        resource.add("否");
        lv.setAdapter(new HomeChooseCommunityAdapter(resource, ReleaseCommodityActivity.this));
    }
}
