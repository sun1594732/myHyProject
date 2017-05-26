package com.hyzs.onekeyhelp.family.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.circle.bean.CreateGroupBean;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.mine.opinion.adapter.OpinionImageAdapter;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.BitmapUtil;
import com.hyzs.onekeyhelp.util.EditNotInputSpace;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateGroupActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title;
    private EditText name, desc;
    private Button submit;
    private ImageView add_icon;
    private RecyclerView recyclerView;
    private OpinionImageAdapter mGridAdapter;
    private Dialog dialog;
    private StringBuilder sb;
    private String sName;
    private String sDesc;
    private LinearLayout ll;
    private PopupWindow mSubmitPop;

    @Override
    protected void assignView() {
        ll = (LinearLayout) findViewById(R.id.linearLayout);
        mSubmitPop = HelpDialog.createDialogNoAlert(this, this, "创建成功", 0);
        sb = new StringBuilder();
        dialog = ProgressDialog.createProgressLoading(this);
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        name = (EditText) findViewById(R.id.activity_create_group_name);
        desc = (EditText) findViewById(R.id.activity_create_group_desc);
        submit = (Button) findViewById(R.id.activity_create_group_submit);
        add_icon = (ImageView) findViewById(R.id.activity_opinion_add_img);
        recyclerView = (RecyclerView) findViewById(R.id.activity_opinion_recycler);
    }

    @Override
    protected void initView() {
        title.setText("创建家庭");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mGridAdapter = new OpinionImageAdapter(CreateGroupActivity.this, 1);
        recyclerView.setAdapter(mGridAdapter);
    }

    @Override
    protected void initListener() {
        EditNotInputSpace.setEditTextInhibitInputSpace(name);
        EditNotInputSpace.setEditTextInhibitInputSpace(desc);
        add_icon.setOnClickListener(this);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_group;
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
            case R.id.activity_opinion_add_img:
//                Album.startAlbum(CreateGroupActivity.this, 100, 1, ContextCompat.getColor(this, R.color.color_1ccd9b), ContextCompat.getColor(this, R.color.color_1ccd9b));
                Intent intent = new Intent(this, AlbumActivity.class);
                intent.putExtra("limitCount", 1);
                startActivityForResult(intent, 100);
                break;
            case R.id.activity_create_group_submit:
                createGroup();
                break;
            case R.id.help_pop_confirm:
                mSubmitPop.dismiss();
                finish();
                break;
        }
    }

    private void createGroup() {
        sName = name.getText().toString();
        sDesc = desc.getText().toString();
        if (TextUtils.isEmpty(sName)) {
            Toast.makeText(this, "请输入群组名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(sDesc)) {
            Toast.makeText(this, "请输入描述信息", Toast.LENGTH_SHORT).show();
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
        final String url = PortUtil.HXGroupsCreate;
        Map<String, String> map = new HashMap<>();
        map.put("groupname", sName);
        map.put("desc", sDesc);
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("facepath", sb == null ? "" : sb.toString());

        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    CreateGroupBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CreateGroupBean.class);
                    if (bean.getCode().equals("10000")) {
                        setResult(1);
                        dialog.dismiss();
                        mSubmitPop.showAtLocation(ll, Gravity.CENTER, 0, 0);
                    } else {
                        if (bean.getCode().equals("10001"))
                            Toast.makeText(CreateGroupActivity.this, "创建失败请重试", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CreateGroupActivity.this, bean.getMessage(), Toast.LENGTH_SHORT).show();
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
                        ToastUtils.showShort(CreateGroupActivity.this, "选择上传的图片不能大于1张，请删除后再选择");
                        return;
                    }
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(CreateGroupActivity.this, "取消上传");
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
