package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.hyzs.onekeyhelp.home.adapter.HomeChooseCommunityAdapter;
import com.hyzs.onekeyhelp.mine.opinion.adapter.OpinionImageAdapter;
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

public class ServiceProjectAddActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ServiceProjectAddActivi";
    private ImageView back;
    private TextView title, charges, service_object, submit;
    private EditText name, price, original_cost, desc, detail_info, charges_number;
    private AlertDialog unitAlertDialog;
    private List<String> unitList;
    private List<String> objectList;
    private OpinionImageAdapter mGridAdapter;
    private RecyclerView recyclerView;
    private ImageView add_icon;
    private String sName;
    private String sPrice;
    private String s_original_cost;
    private String sDesc;
    private String s_detail_info;
    private Dialog dialog;
    private StringBuilder sb;
    private PopupWindow mSubmitPop;
    private LinearLayout ll;
    private AlertDialog objectDialog;
    private int objectPosition = -1, unitPosition = -1;
    private String s_charges_number;

    @Override
    protected void assignView() {
        charges_number = (EditText) findViewById(R.id.activity_service_project_add_charges_number);
        ll = (LinearLayout) findViewById(R.id.activity_service_project_add);
        sb = new StringBuilder();
        mSubmitPop = HelpDialog.createDialogNoAlert(this, this, "添加成功", 0);
        dialog = ProgressDialog.createProgressLoading(this);
        detail_info = (EditText) findViewById(R.id.activity_service_project_add_detail_info);
        desc = (EditText) findViewById(R.id.activity_service_project_add_desc);
        original_cost = (EditText) findViewById(R.id.activity_service_project_add_original_cost);
        price = (EditText) findViewById(R.id.activity_service_project_add_price);
        name = (EditText) findViewById(R.id.activity_service_project_add_name);
        submit = (TextView) findViewById(R.id.activity_service_project_add_submit);
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        charges = (TextView) findViewById(R.id.activity_service_project_add_charges);
        service_object = (TextView) findViewById(R.id.activity_service_project_add_service_object);
        recyclerView = (RecyclerView) findViewById(R.id.activity_opinion_recycler);
        add_icon = (ImageView) findViewById(R.id.activity_opinion_add_img);
    }

    @Override
    protected void initView() {
        title.setText("服务项目增加");
        initUnitDialog();
        initObjectDialog();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mGridAdapter = new OpinionImageAdapter(ServiceProjectAddActivity.this, 1);
        recyclerView.setAdapter(mGridAdapter);
    }

    private void initObjectDialog() {
        objectList = new ArrayList<>();
        objectList.add("家庭");
        View DialogView = LayoutInflater.from(this).inflate(R.layout.dialog_only_lv, null);
        assignDialogView(DialogView, objectList);
        objectDialog = new AlertDialog.Builder(this).create();
        objectDialog.show();
        objectDialog.getWindow().setContentView(DialogView);
        objectDialog.dismiss();
        ListView lv = (ListView) DialogView.findViewById(R.id.dialog_only_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                service_object.setText(objectList.get(position));
                objectPosition = position;
                objectDialog.dismiss();
            }
        });
    }

    @Override
    protected void initListener() {
        charges.setOnClickListener(this);
        service_object.setOnClickListener(this);
        back.setOnClickListener(this);
        add_icon.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_project_add;
    }

    @Override
    protected void initData() {

    }

    private void initUnitDialog() {
        unitList = new ArrayList<>();
        unitList.add("次");
        unitList.add("小时");
        View DialogView = LayoutInflater.from(this).inflate(R.layout.dialog_only_lv, null);
        assignDialogView(DialogView, unitList);
        unitAlertDialog = new AlertDialog.Builder(this).create();
        unitAlertDialog.show();
        unitAlertDialog.getWindow().setContentView(DialogView);
        unitAlertDialog.dismiss();
        ListView lv = (ListView) DialogView.findViewById(R.id.dialog_only_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                charges.setText(unitList.get(position));
                unitPosition = position;
                unitAlertDialog.dismiss();
            }
        });
    }

    private void assignDialogView(View dialogView, List<String> list) {

        ListView lv = (ListView) dialogView.findViewById(R.id.dialog_only_list);
        lv.setAdapter(new HomeChooseCommunityAdapter(list, ServiceProjectAddActivity.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_service_project_add_submit:
                submit();
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_service_project_add_charges:
                unitAlertDialog.show();
                break;
            case R.id.activity_service_project_add_service_object:
                objectDialog.show();
                break;
            case R.id.activity_opinion_add_img:
//                Album.startAlbum(CreateGroupActivity.this, 100, 1, ContextCompat.getColor(this, R.color.color_1ccd9b), ContextCompat.getColor(this, R.color.color_1ccd9b));
                Intent intent = new Intent(this, AlbumActivity.class);
                intent.putExtra("limitCount", 1);
                startActivityForResult(intent, 100);
                break;
            case R.id.help_pop_confirm:
                mSubmitPop.dismiss();
                finish();
                break;
        }
    }

    private void submit() {
        sName = name.getText().toString();
        sPrice = price.getText().toString();
        s_original_cost = original_cost.getText().toString();
        sDesc = desc.getText().toString();
        s_detail_info = detail_info.getText().toString();
        s_charges_number = charges_number.getText().toString();
        if (TextUtils.isEmpty(sName)) {
            Toast.makeText(this, "请输入项目名称", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(sPrice)) {
            Toast.makeText(this, "请输入服务价格", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(s_original_cost)) {
            Toast.makeText(this, "请输入市场价格", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(sDesc)) {
            Toast.makeText(this, "请输入收费方式", Toast.LENGTH_SHORT).show();
            return;
        }
        if (unitPosition == -1) {
            Toast.makeText(this, "请选择收费单位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (objectPosition == -1) {
            Toast.makeText(this, "请选择服务对象", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(sDesc)) {
            Toast.makeText(this, "请输入简短介绍", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(s_detail_info)) {
            Toast.makeText(this, "请输入详细介绍", Toast.LENGTH_SHORT).show();
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
        final String url = PortUtil.LifeServiceSPAdd;
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("SPP_LS_ID", getIntent().getStringExtra("id"));
        map.put("SPP_Name", sName);
        map.put("SPP_Desc", sDesc);
        map.put("SPP_DetailIntro", s_detail_info);
        map.put("SPP_Img", sb == null ? "" : sb.toString());
        map.put("SPP_Price", sPrice);
        map.put("SPP_OriginalCost", s_original_cost);
        map.put("SPP_Unit", unitPosition+"");
        map.put("SPP_ServiceObj", objectPosition+"");
        map.put("SPP_Number", s_charges_number);

        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    Log.e(TAG, "getData: " + UrlDecodeUtil.urlCode(data));
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if (jsonObject.getString("code").equals("10000")) {
//                        setResult(1);
                        dialog.dismiss();
                        mSubmitPop.showAtLocation(ll, Gravity.CENTER, 0, 0);
                    } else {
                        if (jsonObject.getString("code").equals("10001"))
                            Toast.makeText(ServiceProjectAddActivity.this, "添加失败请重试", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ServiceProjectAddActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                        ToastUtils.showShort(ServiceProjectAddActivity.this, "选择上传的图片不能大于1张，请删除后再选择");
                        return;
                    }
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(ServiceProjectAddActivity.this, "取消上传");
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
