package com.hyzs.onekeyhelp.lifehelp.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.home.adapter.GridAdapter;
import com.hyzs.onekeyhelp.home.adapter.HomeChooseCommunityAdapter;
import com.hyzs.onekeyhelp.mine.community.bean.MyCommunityEntity;
import com.hyzs.onekeyhelp.module.housekeeping.activity.PersonAddActivity;
import com.hyzs.onekeyhelp.module.housekeeping.bean.PCCABean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.BitmapUtil;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.album.Album;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LifeHelpPublishActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private ImageView mBack, mNoneView;
    private TextView mTitle, mToolBarRight;
    private RadioGroup mChooseHelper, mHelpStatus;
    private LinearLayout mMoneyEditLayout;
    private LinearLayout ll;
    private AlertDialog mChooseGroup;
    private RecyclerView mRecyclerView;
    private GridAdapter mGridAdapter;
    private List<String> pathList;
    private EditText mEdittext, mMoneyEdit;
    private StringBuilder sb;
    private String mGroupId = "0", mSeekType = "1",mCommunityId = "0";
    private Dialog pragDialog, mChooseCommunity;
    private PopupWindow pop;
    private boolean isGroup = true;
    //重新启动发布按钮
    Handler handler;
    private MyCommunityEntity bean;

    @Override
    protected void assignView() {
        pragDialog = ProgressDialog.createProgressLoading(this);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolBarRight = (TextView) findViewById(R.id.toolbar_right);
        mHelpStatus = (RadioGroup) findViewById(R.id.activity_life_help_publish_status);
        mChooseHelper = (RadioGroup) findViewById(R.id.activity_life_help_publish__chooseHelper);
        mMoneyEditLayout = (LinearLayout) findViewById(R.id.activity_life_help_publish_money);
        mNoneView = (ImageView) findViewById(R.id.activity_life_help_publish_nonePic);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_life_help_publish_recycler);
        mEdittext = (EditText) findViewById(R.id.activity_life_help_publish_edit);
        mMoneyEdit = (EditText) findViewById(R.id.activity_life_help_publish_money_edit);
        ll = (LinearLayout) findViewById(R.id.activity_life_help);
        pop = HelpDialog.createDialog(LifeHelpPublishActivity.this, LifeHelpPublishActivity.this, "发布成功", R.mipmap.bingo);
    }

    @Override
    protected void initView() {
        handler = new MyHandler(this);
        mTitle.setText("生活求助");
        mToolBarRight.setText("发布");
        mToolBarRight.setVisibility(View.VISIBLE);
        initDialog();
        initCommunityDialog();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGridAdapter = new GridAdapter(LifeHelpPublishActivity.this);
        mRecyclerView.setAdapter(mGridAdapter);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mHelpStatus.setOnCheckedChangeListener(this);
        mToolBarRight.setOnClickListener(this);
        mChooseHelper.setOnCheckedChangeListener(this);
        mNoneView.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_life_help_publish;
    }

    @Override
    protected void initData() {
        sb = new StringBuilder();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.activity_life_help_publish_free:
                mSeekType = "1";
                mMoneyEditLayout.setVisibility(View.GONE);
                break;
            case R.id.activity_life_help_publish_offer:
                mSeekType = "2";
                mMoneyEditLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.activity_life_help_chooseHelper_group:
                isGroup = true;
                mChooseGroup.show();
                break;
            case R.id.activity_life_help_chooseHelper_community:
                isGroup = false;
                mChooseCommunity.show();
                break;
            case R.id.activity_life_help_chooseHelper_all:
                break;
        }
    }

    private void initDialog() {
        View DialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog_life_help_group, null);
        assignDialogView(DialogView);
        mChooseGroup = new AlertDialog.Builder(LifeHelpPublishActivity.this).create();
        mChooseGroup.show();
        mChooseGroup.getWindow().setContentView(DialogView);
        mChooseGroup.dismiss();
    }

    private void assignDialogView(View dialogView) {
        TextView sure = (TextView) dialogView.findViewById(R.id.layout_dialog_life_help_sure);
        TextView cancel = (TextView) dialogView.findViewById(R.id.layout_dialog_life_help_cancel);
        final RadioGroup rg = (RadioGroup) dialogView.findViewById(R.id.layout_dialog_life_help_rg);
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
                    case R.id.layout_dialog_life_help_family:
                        mGroupId = "0";
                        ToastUtils.showShort(LifeHelpPublishActivity.this, "选择家庭分组");
                        break;
                    case R.id.layout_dialog_life_help_friend:
                        mGroupId = "1";
                        ToastUtils.showShort(LifeHelpPublishActivity.this, "选择朋友分组");
                        break;
                    case R.id.layout_dialog_life_help_neighbor:
                        mGroupId = "2";
                        ToastUtils.showShort(LifeHelpPublishActivity.this, "选择邻居分组");
                        break;
                }
                mChooseGroup.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right: //发布请求
                mToolBarRight.setClickable(false);
                handler.sendEmptyMessageDelayed(0, 3000);
                if (TextUtils.isEmpty(mEdittext.getText())) {
                    ToastUtils.showShort(LifeHelpPublishActivity.this, "请输入发布信息");
                    break;
                }
                if ("2".equals(mSeekType) && TextUtils.isEmpty(mMoneyEdit.getText())) {
                    ToastUtils.showShort(LifeHelpPublishActivity.this, "请输入悬赏金额");
                    break;
                }
                pragDialog.show();
                if (pathList != null) {
                    for (int i = 0; i < pathList.size(); i++) {
                        getPicPath(pathList.get(i), i);
                    }
                } else {
                    publishRequest();
                }
                break;
            case R.id.activity_life_help_publish_nonePic:
                Album.startAlbum(LifeHelpPublishActivity.this, 100, 8, ContextCompat.getColor(this, R.color.color_1ccd9b), ContextCompat.getColor(this, R.color.color_1ccd9b));
                break;
            case R.id.help_pop_confirm:
                finish();
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
                        ToastUtils.showShort(LifeHelpPublishActivity.this, "选择上传的图片不能大于8张，请删除后再选择");
                        return;
                    }
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(LifeHelpPublishActivity.this, "取消上传");
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
        param.put("currId", MySharedPreferences.getUserId(this));
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
        Map<String, String> param1 = new HashMap<>();
        param1.put("currId", MySharedPreferences.getUserId(this));
        param1.put("seek_Title", "");
        param1.put("seek_Type", mSeekType);
        param1.put("seek_Type_b", isGroup ? "2" : "3");
        param1.put("seek_GroupId", mGroupId);
        param1.put("communityID", mCommunityId);
        param1.put("seek_UserID_b", "0");
        param1.put("seek_Money", "1".equals(mSeekType) ? "0" : mMoneyEdit.getText().toString());
        param1.put("seek_Text", mEdittext.getText().toString());
        param1.put("seek_Voice", "");
        param1.put("seek_Location", "");
        param1.put("seek_AffixImgList", sb.toString());
        NetRequest.ParamPostRequest(PortUtil.PublishSeekHelp, param1, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                    if ("10000".equals(bean.getCode())) {
                        pop.showAtLocation(ll, Gravity.CENTER, 0, 0);
                    } else {
                        ToastUtils.showShort(LifeHelpPublishActivity.this, bean.getMessage() + "");
                    }
                } catch (Exception e) {
                    ToastUtils.showShort(LifeHelpPublishActivity.this, "连接服务器失败");
                } finally {
                    pragDialog.dismiss();
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("LifeHelpPublishActivity");
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LifeHelpPublishActivity");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        handler = null;
    }

    //重新启动发布按钮
    private static class MyHandler extends Handler {
        private final WeakReference<LifeHelpPublishActivity> mActivity;

        public MyHandler(LifeHelpPublishActivity activity) {
            mActivity = new WeakReference<LifeHelpPublishActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
            mActivity.get().mToolBarRight.setClickable(true);
        }
    }

    private void initCommunityDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_only_lv, null);
        assignCommunityDialogView(dialogView);
        mChooseCommunity = new AlertDialog.Builder(this).create();
        mChooseCommunity.show();
        mChooseCommunity.getWindow().setContentView(dialogView);
        mChooseCommunity.dismiss();
    }

    private void assignCommunityDialogView(View dialogView) {
        final ListView lv = (ListView) dialogView.findViewById(R.id.dialog_only_list);
        Map<String, String> param = new ArrayMap<>();
        param.put("curr_uid", MySharedPreferences.getUserId(this));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCommunityId = bean.getMycommunity().get(position).getId() + "";
                mChooseCommunity.dismiss();
            }
        });
        NetRequest.ParamPostRequest(PortUtil.MyCommunity, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MyCommunityEntity.class);
                    List<String> resource = new ArrayList<String>();
                    for (int i = 0; i < bean.getMycommunity().size(); i++) {
                        resource.add(bean.getMycommunity().get(i).getCommunityName());
                    }
                    lv.setAdapter(new HomeChooseCommunityAdapter(resource, LifeHelpPublishActivity.this));
                } catch (Exception e) {
                }
            }
        });
    }
}
