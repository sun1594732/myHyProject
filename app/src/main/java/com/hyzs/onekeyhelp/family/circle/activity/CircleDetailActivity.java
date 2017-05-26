package com.hyzs.onekeyhelp.family.circle.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.family.circle.adapter.CircleDetailCommentAdapter;
import com.hyzs.onekeyhelp.family.circle.adapter.CircleItemRecyclerAdapter;
import com.hyzs.onekeyhelp.family.circle.bean.CircleDetailBean;
import com.hyzs.onekeyhelp.family.circle.bean.CircleDetailCommentBean;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.widget.ListViewForScrollView;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class CircleDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "CircleDetailActivity";
    private ListViewForScrollView mLv;
    private TextView mPraiseBtn, mCommentCount, mUserName, mUserGender, mUserCommunity, mContent,
            mTime, mPraise, CardCommentCount, mTitle, mToolBarRight, mCommit, mAttention;
    private ImageView mUserIcon, mBack;
    private CircleDetailBean.CircleCommentUsersBean bean;
    private CircleDetailCommentAdapter adapter;
    private List<CircleDetailCommentBean.CircleCommentListsBean> list;
    private RecyclerView mRecyclerView;
    private EditText mEdit;
    private ArrayList<String> pathList;
    private Dialog dialog;
    private RelativeLayout emptyView;
    private String AttentionId;
    private RoundImageView mVideoPic;
    private RelativeLayout mVideoRl;

    @Override
    protected void assignView() {
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolBarRight = (TextView) findViewById(R.id.toolbar_right);
        mLv = (ListViewForScrollView) findViewById(R.id.activity_circle_detail_commentLv);
        mCommentCount = (TextView) findViewById(R.id.activity_circle_detail_commentCount);
        mUserIcon = (ImageView) findViewById(R.id.item_fragment_interest_icon);
        mRecyclerView = (RecyclerView) findViewById(R.id.item_fragment_interest_recycler);
        mUserName = (TextView) findViewById(R.id.item_fragment_interest_name);
        mUserGender = (TextView) findViewById(R.id.item_fragment_interest_gender);
        mUserCommunity = (TextView) findViewById(R.id.item_fragment_interest_community);
        mContent = (TextView) findViewById(R.id.item_fragment_interest_content);
        mTime = (TextView) findViewById(R.id.item_fragment_interest_time);
        mPraise = (TextView) findViewById(R.id.item_fragment_interest_up);
        CardCommentCount = (TextView) findViewById(R.id.item_fragment_interest_count);
        mCommit = (TextView) findViewById(R.id.activity_circle_detail_commit);
        mEdit = (EditText) findViewById(R.id.activity_circle_detail_edit);
        emptyView = (RelativeLayout) findViewById(R.id.layout_empty);
        mVideoPic = (RoundImageView) findViewById(R.id.item_fragment_interest_videoPic);
        mAttention = (TextView) findViewById(R.id.item_fragment_interest_attention);
        mVideoRl = (RelativeLayout) findViewById(R.id.item_fragment_interest_ll);
        dialog = ProgressDialog.createProgressLoading(CircleDetailActivity.this);
    }

    @Override
    protected void initView() {
        mTitle.setText("详情");
        mToolBarRight.setText("加入");
        mAttention.setVisibility(View.VISIBLE);
        mToolBarRight.setVisibility(View.GONE);
        if (getIntent().getIntExtra("type", 0) == 1) {
            mVideoRl.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mToolBarRight.setOnClickListener(this);
        mCommit.setOnClickListener(this);
        mPraise.setOnClickListener(this);
        mUserIcon.setOnClickListener(this);
        mAttention.setOnClickListener(this);
        mVideoRl.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_detail;
    }

    @Override
    protected void initData() {
        dialog.show();
        list = new ArrayList<>();
        NetRequest.CircleDetailRequest(MySharedPreferences.getInstance(CircleDetailActivity.this).getString("uid"), getIntent().getStringExtra("id"), new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    emptyView.setVisibility(View.GONE);
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CircleDetailBean.class).getCircleCommentUsers().get(0);
                    if (bean.getWatchId() == 0) {
                        mAttention.setText("关注");
                    } else mAttention.setText("已关注");
                    initViewData();
                } catch (Exception e) {
                    ToastUtils.showShort(CircleDetailActivity.this, "网络异常");
                } finally {
                    dialog.dismiss();
                }
            }
        });

        NetRequest.CircleDetailCommentRequest(MySharedPreferences.getInstance(CircleDetailActivity.this).getString("uid"), getIntent().getStringExtra("id"), "1", new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                list = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CircleDetailCommentBean.class).getCircleCommentLists();
                adapter = new CircleDetailCommentAdapter(list, CircleDetailActivity.this);
                mLv.setAdapter(adapter);
            }
        });
    }

    private void initViewData() {
        mUserName.setText(bean.getUesrname());
        if (TextUtils.isEmpty(bean.getFace())) {
            mUserIcon.setImageResource(R.mipmap.icon_replace);
        } else
            NetRequest.loadImg(this, bean.getFace(), mUserIcon);
        if (!TextUtils.isEmpty(bean.getCircle_SPDYZT())) {
            NetRequest.loadImg(this, bean.getCircle_SPDYZT(), mVideoPic);
        } else mVideoPic.setImageResource(R.mipmap.replace_hybb);
        mUserCommunity.setText(bean.getCommunityName());
        mContent.setText(bean.getCircle_Content());
        mTime.setText(handleTime(bean.getCircle_DateTime()));
        mPraise.setText(bean.getPointPraiseCount() + "");
        mCommentCount.setText("评论 （" + bean.getCommentCount() + "）");
        CardCommentCount.setText(bean.getCommentCount() + "");
        switch (bean.getSex()) {
            case "男":
                mUserGender.setText(bean.getSex());
                mUserGender.setBackgroundResource(R.drawable.shape_6699ff_4_solid);
                mUserGender.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.mipmap.woman), null, null, null);
                break;
            case "女":
                mUserGender.setText(bean.getSex());
                mUserGender.setBackgroundResource(R.drawable.shape_fc999b_6soild);
                mUserGender.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.mipmap.man), null, null, null);
                break;
        }
        pathList = getUrl(bean.getCircle_AffixImgList());
        if (TextUtils.isEmpty(pathList.get(0))) {
            return;
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(new CircleItemRecyclerAdapter(this, pathList));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                Intent intent1 = getIntent();
                intent1.putExtra("praise", mPraise.getText().toString());
                intent1.putExtra("comment", CardCommentCount.getText().toString());
                setResult(RESULT_OK, intent1);
                finish();
                break;
            case R.id.item_fragment_interest_icon:
                Intent intent = new Intent(CircleDetailActivity.this, MineHomeActivity.class);
                intent.putExtra("targetUserId", bean.getCircle_UserID() + "");
                startActivity(intent);
                break;
            case R.id.item_fragment_interest_up:
                Map<String, String> param = new ArrayMap<>();
                param.put("currId", MySharedPreferences.getUserId(CircleDetailActivity.this));
                param.put("circleId", bean.getID() + "");
                NetRequest.ParamPostRequest(PortUtil.CirclePraise, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        mPraise.setText((Integer.parseInt(mPraise.getText().toString()) + 1) + "");
                    }
                });
                break;
            case R.id.item_fragment_interest_attention:
                Map<String, String> param1 = new ArrayMap<>();
                if (bean.getWatchId() == 0) {
                    param1.put("currId", MySharedPreferences.getUserId(this));
                    param1.put("MW_ToID", bean.getID() + "");
                    param1.put("MW_Type", "0");
                    param1.put("MW_TargetType", "2");
                    NetRequest.ParamPostRequest(PortUtil.MyWatchlistAdd, param1, new NetRequest.getDataCallback() {
                        @Override
                        public void getData(String data) {
                            DataStatusBean bean1 = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                            if ("10000".equals(bean1.getCode())) {
                                mAttention.setText("已关注");
                                bean.setWatchId(bean1.getIdentity());
                            } else
                                ToastSingleUtil.showToast(CircleDetailActivity.this, bean1.getMessage());
                        }
                    });
                } else {
                    param1.put("currId", MySharedPreferences.getUserId(this));
                    param1.put("MW_ToID", bean.getWatchId() + "");
                    NetRequest.ParamPostRequest(PortUtil.MyWatchlistCancel, param1, new NetRequest.getDataCallback() {
                        @Override
                        public void getData(String data) {
                            DataStatusBean bean1 = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                            if ("10000".equals(bean1.getCode())) {
                                mAttention.setText("关注");
                                bean.setWatchId(0);
                            } else
                                ToastSingleUtil.showToast(CircleDetailActivity.this, bean1.getMessage());
                        }
                    });
                }
                break;
            case R.id.toolbar_right:
                NetRequest.CircleJoinRequest(CircleDetailActivity.this, bean.getID() + "", new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                        if ("10000".equals(bean.getCode())) {
                            ToastUtils.showShort(CircleDetailActivity.this, "加入成功");
                        } else ToastUtils.showShort(CircleDetailActivity.this, "加入失败");
                    }
                });
                break;
            case R.id.item_fragment_interest_ll:
                Intent intent2 = new Intent(this,CircleMovieDetailActivity.class);
                intent2.putExtra("url",bean.getCircle_SPURL());
                intent2.putExtra("pic",bean.getCircle_SPDYZT());
                startActivity(intent2);
                break;
            case R.id.activity_circle_detail_commit:
                if (TextUtils.isEmpty(mEdit.getText())) {
                    ToastUtils.showShort(CircleDetailActivity.this, "请输入评论内容");
                } else {
                    NetRequest.CircleCommentRequest(MySharedPreferences.getInstance(CircleDetailActivity.this).getString("uid"), getIntent().getStringExtra("id"), UrlDecodeUtil.urlEnCode(mEdit.getText().toString()), "", "", new NetRequest.getDataCallback() {
                        @Override
                        public void getData(String data) {
                            try {
                                DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                                if ("10000".equals(bean.getCode())) {
                                    ToastUtils.showShort(CircleDetailActivity.this, "评论成功");
                                    mEdit.getText().clear();
                                    NetRequest.CircleDetailCommentRequest(MySharedPreferences.getInstance(CircleDetailActivity.this).getString("uid"), getIntent().getStringExtra("id"), "1", new NetRequest.getDataCallback() {
                                        @Override
                                        public void getData(String data) {
                                            CircleDetailCommentBean bean1 = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CircleDetailCommentBean.class);
                                            list = bean1.getCircleCommentLists();
                                            int count = parseInt(mCommentCount.getText().toString().split("（")[1].split("）")[0]);
                                            mCommentCount.setText("评论 （" + (count + 1) + "）");
                                            count = Integer.parseInt(CardCommentCount.getText().toString());
                                            CardCommentCount.setText((count + 1) + "");
                                            CardCommentCount.postInvalidate();
                                            adapter = new CircleDetailCommentAdapter(list, CircleDetailActivity.this);
                                            mLv.setAdapter(adapter);
                                        }
                                    });
                                } else ToastUtils.showShort(CircleDetailActivity.this, "评论失败");
                            } catch (Exception e) {
                                ToastUtils.showShort(CircleDetailActivity.this, "网络错误");
                            }
                        }
                    });
                    break;
                }
        }
    }

    private String handleTime(String circle_dateTime) {
        long minute = (System.currentTimeMillis() / 1000 - Long.parseLong(circle_dateTime)) / 60;
        if (minute > 60 && minute < 1440) {
            return minute / 60 + "小时前";
        } else if (minute > 1440) {
            return minute / 1440 + "天前";
        } else
            return minute + "分钟前";
    }

    public ArrayList<String> getUrl(String pathList) {
        ArrayList<String> list = new ArrayList<>();
        String a[] = pathList.split(",");
        for (int i = 0; i < a.length; i++) {
            String temp = a[i].replace("{", "").replace("}", "");
            list.add(temp);
        }
        return list;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent1 = getIntent();
            intent1.putExtra("praise", mPraise.getText().toString());
            intent1.putExtra("comment", CardCommentCount.getText().toString());
            setResult(RESULT_OK, intent1);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
