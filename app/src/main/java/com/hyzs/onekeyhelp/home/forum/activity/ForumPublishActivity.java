package com.hyzs.onekeyhelp.home.forum.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
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

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.circle.activity.CirclePublishActivity;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.home.adapter.GridAdapter;
import com.hyzs.onekeyhelp.home.adapter.HomeChooseCommunityAdapter;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.BitmapUtil;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.video.VideoChooseActivity;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.album.Album;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hyzs.onekeyhelp.contact.activity.ContactSearchActivity.handler;


public class ForumPublishActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack, mNoneView;
    private TextView mTitle, mToolBarRight;
    private RecyclerView mRecyclerView;
    private GridAdapter mGridAdapter;
    private List<String> pathList;
    private EditText mEdittext;
    private StringBuilder sb;
    private LinearLayout mLL;
    private PopupWindow pop;
    private MyHandler handler;
    private boolean isChoosePic;
    private Dialog mProgressDialog, mChooseCommunity;
    private Bitmap videoBitmap;
    private String videoUrl, videoBitmapUrl;

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolBarRight = (TextView) findViewById(R.id.toolbar_right);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_circle_publish_recycler);
        mNoneView = (ImageView) findViewById(R.id.activity_circle_publish_nonePic);
        mEdittext = (EditText) findViewById(R.id.activity_circle_publish_edit);
        mLL = (LinearLayout) findViewById(R.id.activity_circle_publish);
    }

    @Override
    protected void initView() {
        handler = new MyHandler(this);
        initDialog();
        mTitle.setText("发论坛");
        mToolBarRight.setText("发布");
        mToolBarRight.setVisibility(View.VISIBLE);
        mEdittext.setHint("请输入论坛信息( 限制在 3000 字内 )");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGridAdapter = new GridAdapter(ForumPublishActivity.this);
        mRecyclerView.setAdapter(mGridAdapter);
        pop = HelpDialog.createDialogNoAlert(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                finish();
            }
        }, "发布成功", R.mipmap.bingo);
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
                mChooseCommunity.show();
                break;
            case R.id.toolbar_right:
                mToolBarRight.setClickable(false);
                handler.sendEmptyMessageDelayed(0, 3000);
                if (TextUtils.isEmpty(mEdittext.getText())) {
                    ToastUtils.showShort(ForumPublishActivity.this, "请输入发布信息");
                    break;
                }
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
        param1.put("currId", MySharedPreferences.getUserId(ForumPublishActivity.this));
        param1.put("forum_type", getIntent().getStringExtra("type"));
        param1.put("forum_Content", mEdittext.getText().toString());
        param1.put("forum_Voice", "");
        param1.put("Community_id", MySharedPreferences.getCommunityId(this));
        param1.put("forum_AffixImgList", sb.toString());
        NetRequest.ParamPostRequest(PortUtil.ForumPublish, param1, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                if ("10000".equals(bean.getCode())) {
                    pop.showAtLocation(mLL, Gravity.CENTER, 0, 0);
                } else ToastUtils.showShort(ForumPublishActivity.this, "发布失败");
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
                    sb.append("{" + bean.getUrl() + "}");
                    publishRequest();
                } else {
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
                        ToastUtils.showShort(ForumPublishActivity.this, "选择上传的图片不能大于8张，请删除后再选择");
                        return;
                    }
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(ForumPublishActivity.this, "取消上传");
            }
        }
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                videoBitmap = data.getParcelableExtra("pic");
                videoUrl = data.getStringExtra("url");
                mNoneView.setImageBitmap(videoBitmap);

                String encodeString = android.util.Base64.encodeToString(BitmapUtil.compressImage(videoBitmap), Base64.DEFAULT);
                Map<String, String> param = new HashMap<>();
                param.put("currId", MySharedPreferences.getUserId(this));
                param.put("FileBase64", encodeString);
                param.put("FileType", "0");
                NetRequest.ParamFailMsgPostRequest(PortUtil.DoUpLoad, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        PublishPicBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PublishPicBean.class);
                        videoBitmapUrl = bean.getUrl();
                    }
                }, new NetRequest.getMessageFailCallback1() {
                    @Override
                    public void getData(String data) {
                    }
                });
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
        handler.removeCallbacksAndMessages(null);
    }

    //重新启动发布按钮
    private static class MyHandler extends Handler {
        private final WeakReference<ForumPublishActivity> mActivity;

        public MyHandler(ForumPublishActivity activity) {
            mActivity = new WeakReference<ForumPublishActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
            mActivity.get().mToolBarRight.setClickable(true);
        }
    }

    private void initDialog() {
        View DialogView = LayoutInflater.from(this).inflate(R.layout.dialog_only_lv, null);
        assignDialogView(DialogView);
        mChooseCommunity = new AlertDialog.Builder(this).create();
        mChooseCommunity.show();
        mChooseCommunity.getWindow().setContentView(DialogView);
        mChooseCommunity.dismiss();
    }

    private void assignDialogView(View dialogView) {
        final ListView lv = (ListView) dialogView.findViewById(R.id.dialog_only_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isChoosePic = (position == 0);
                if (isChoosePic) {
                    Album.startAlbum(ForumPublishActivity.this, 100, 8, ContextCompat.getColor(ForumPublishActivity.this, R.color.color_1ccd9b), ContextCompat.getColor(ForumPublishActivity.this, R.color.color_1ccd9b));
                } else {
                    mProgressDialog.show();
                    startActivityForResult(new Intent(ForumPublishActivity.this, VideoChooseActivity.class), 200);
                }
                mChooseCommunity.dismiss();
            }
        });
        List<String> resource = new ArrayList<>();
        resource.add("上传图片");
        resource.add("上传视频");
        lv.setAdapter(new HomeChooseCommunityAdapter(resource, ForumPublishActivity.this));
    }
}
