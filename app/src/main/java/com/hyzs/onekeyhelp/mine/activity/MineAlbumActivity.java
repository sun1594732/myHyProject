package com.hyzs.onekeyhelp.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.util.ArrayMap;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.mine.adapter.MineAlbumAdapter;
import com.hyzs.onekeyhelp.mine.bean.MineAlbumBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.Map;

public class MineAlbumActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack, mBg;
    private TextView mTitle, mToolbarRight, mName, mSign;
    private RoundImageView mIcon;
    private List<MineAlbumBean.PersonalAlbumDynamicListBean> list;
    private ListView mLv;
    private View mView;
    private boolean refresh ,isMineAlbum;
    private VRefresh vRefresh;
    int page = 2;
    private MineAlbumAdapter adapter;

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarRight = (TextView) findViewById(R.id.toolbar_right);
        mName = (TextView) findViewById(R.id.activity_mine_album_name);
        mSign = (TextView) findViewById(R.id.activity_mine_album_sign);
        mIcon = (RoundImageView) findViewById(R.id.activity_mine_album_icon);
        mBg = (ImageView) findViewById(R.id.activity_mine_album_bg);
        mLv = (ListView) findViewById(R.id.activity_mine_album_lv);
        vRefresh = (VRefresh) findViewById(R.id.activity_mine_album_vRefresh);
    }

    @Override
    protected void initView() {
        mTitle.setText("个人相册");
        mToolbarRight.setText("发布");
        vRefresh.setView(MineAlbumActivity.this, mLv);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mToolbarRight.setOnClickListener(this);
        vRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Map<String, String> param = new ArrayMap<>();
                param.put("currId", MySharedPreferences.getUserId(MineAlbumActivity.this));
                if (TextUtils.isEmpty(getIntent().getStringExtra("targetUserID")) || getIntent().getStringExtra("targetUserID").equals(MySharedPreferences.getUserId(MineAlbumActivity.this))) {
                    param.put("targetUserID", MySharedPreferences.getUserId(MineAlbumActivity.this));
                    mToolbarRight.setVisibility(View.VISIBLE);
                    isMineAlbum = true;
                } else {
                    param.put("targetUserID", getIntent().getStringExtra("targetUserID"));
                    isMineAlbum = false;
                }
                param.put("pageSize", "10");
                param.put("pageIndex", "1");
                NetRequest.ParamPostRequest(PortUtil.UserAlbumPage, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        try {
                            MineAlbumBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineAlbumBean.class);
                            list.clear();
                            list.addAll(bean.getPersonalAlbumDynamicList());
                            adapter.notifyDataSetChanged();
                            page = 2;
                        } catch (Exception e) {
                            ToastUtils.showShort(MineAlbumActivity.this, "网络错误");
                        } finally {
                            vRefresh.setRefreshing(false);
                        }
                    }
                });
            }
        });
        vRefresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                Map<String, String> param = new ArrayMap<>();
                param.put("currId", MySharedPreferences.getUserId(MineAlbumActivity.this));
                if (TextUtils.isEmpty(getIntent().getStringExtra("targetUserID"))) {
                    param.put("targetUserID", MySharedPreferences.getUserId(MineAlbumActivity.this));
                } else
                    param.put("targetUserID", getIntent().getStringExtra("targetUserID"));
                param.put("pageSize", "10");
                param.put("pageIndex", page + "");
                NetRequest.ParamPostRequest(PortUtil.UserAlbumPage, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        try {
                            MineAlbumBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineAlbumBean.class);
                            list.addAll(bean.getPersonalAlbumDynamicList());
                            adapter.notifyDataSetChanged();
                            page++;
                        } catch (Exception e) {
                            ToastUtils.showShort(MineAlbumActivity.this, "网络错误");
                        } finally {
                            vRefresh.setLoading(false);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_album;
    }

    @Override
    protected void initData() {
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(MineAlbumActivity.this));
        if (TextUtils.isEmpty(getIntent().getStringExtra("targetUserID")) || MySharedPreferences.getUserId(MineAlbumActivity.this).equals(getIntent().getStringExtra("targetUserID"))) {
            param.put("targetUserID", MySharedPreferences.getUserId(MineAlbumActivity.this));
            mToolbarRight.setVisibility(View.VISIBLE);
        } else {
            param.put("targetUserID", getIntent().getStringExtra("targetUserID"));
        }
        param.put("pageSize", "10");
        param.put("pageIndex", "1");
        NetRequest.ParamPostRequest(PortUtil.UserAlbumPage, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    MineAlbumBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineAlbumBean.class);
                    list = bean.getPersonalAlbumDynamicList();
                    mName.setText(bean.getUserInfo().getUesrname());
                    if (!TextUtils.isEmpty(bean.getUserInfo().getFace())) {
                        NetRequest.loadImg(MineAlbumActivity.this, bean.getUserInfo().getFace(), mIcon);
                    }
                    mSign.setText(bean.getUserInfo().getPersonalizedSignature());
                    adapter = new MineAlbumAdapter(list, MineAlbumActivity.this,isMineAlbum);
                    mLv.setAdapter(adapter);
                    page = 2;
                } catch (Exception e) {
                    ToastUtils.showShort(MineAlbumActivity.this, "网络错误");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:
                SystemAlbum();
                break;
        }
    }

    public void SystemAlbum() {
        refresh = true;
        Intent picture = new Intent(MineAlbumActivity.this, MineAlbumSendActivity.class);
        startActivity(picture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
//            Uri.
            //获取图片并显示
        }
    }

    public void onResume() {
        super.onResume();
        if (refresh) {
            initData();
            refresh = false;
        }
        MobclickAgent.onPageStart("MineAlbumActivity");
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MineAlbumActivity");
        MobclickAgent.onPause(this);
    }
}
