package com.hyzs.onekeyhelp.home.forum.activity;

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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.circle.activity.CircleDetailActivity;
import com.hyzs.onekeyhelp.family.circle.adapter.CircleItemRecyclerAdapter;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.home.forum.adapter.ForumDetailCommentAdapter;
import com.hyzs.onekeyhelp.home.forum.bean.ForumDetailBean;
import com.hyzs.onekeyhelp.home.forum.bean.ForumDetailCommentBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class ForumDetailActivity extends BaseActivity implements View.OnClickListener {

    private ListView mLv;
    private TextView mCommentCount, mUserName, mUserGender, mUserCommunity, mContent, mTime,
            mPraise, CardCommentCount, mTitle, mToolBarRight, mCommit, mAttention;
    private ImageView mUserIcon, mBack;
    private ForumDetailBean.ForumDetailsBean bean;
    private ForumDetailCommentAdapter adapter;
    private List<ForumDetailCommentBean.ForumCommentListBean> list;
    private RecyclerView mRecyclerView;
    private EditText mEdit;
    private ArrayList<String> pathList;
    private RelativeLayout mEmptyView;

    @Override
    protected void assignView() {
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolBarRight = (TextView) findViewById(R.id.toolbar_right);
        mLv = (ListView) findViewById(R.id.activity_circle_detail_commentLv);
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
        mEmptyView = (RelativeLayout) findViewById(R.id.layout_empty);
        mAttention = (TextView) findViewById(R.id.item_fragment_interest_attention);
    }

    @Override
    protected void initView() {
        mTitle.setText("详情");
        mPraise.setVisibility(View.GONE);
        mAttention.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mCommit.setOnClickListener(this);
        mUserIcon.setOnClickListener(this);
        mAttention.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_detail;
    }

    @Override
    protected void initData() {
        Map<String,String>param = new HashMap<>();
        param.put("currId",MySharedPreferences.getUserId(this));
        param.put("forum_id",getIntent().getStringExtra("id"));
        NetRequest.ParamPostRequest(PortUtil.ForumDetail, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                mEmptyView.setVisibility(View.GONE);
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ForumDetailBean.class).getForumDetails();
                initViewData();
            }
        });
        param.clear();
        param.put("currId",MySharedPreferences.getUserId(this));
        param.put("forum_id",getIntent().getStringExtra("id"));
        param.put("pageSize","10");
        param.put("pageIndex","1");
        NetRequest.ParamPostRequest(PortUtil.ForumDetailComment, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                list = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ForumDetailCommentBean.class).getForumCommentList();
                if (bean.getWatchId()==0) {
                    mAttention.setText("关注");
                }else mAttention.setText("已关注");
                adapter = new ForumDetailCommentAdapter(list, ForumDetailActivity.this);
                mLv.setAdapter(adapter);
            }
        });
    }

    private void initViewData() {
        mUserName.setText(bean.getTrueName());
        if (TextUtils.isEmpty(bean.getAvatar())) {
            mUserIcon.setImageResource(R.mipmap.icon_replace);
        }else
        NetRequest.loadImg(this, bean.getAvatar(), mUserIcon);
        mUserCommunity.setText(bean.getCommunity());
        mContent.setText(bean.getForum_Content());
        mTime.setText(bean.getDiffTime());
//        mPraise.setText(bean.getpra() + "");
        mCommentCount.setText("评论 （" + bean.getCommentCount() + "）");
        CardCommentCount.setText(bean.getCommentCount() + "");
        switch (bean.getSex()) {
            case "男":
                mUserGender.setText(bean.getSex());
                mUserGender.setBackgroundResource(R.drawable.shape_66baff_6_solid);
                mUserGender.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.mipmap.man), null, null, null);
                break;
            case "女":
                mUserGender.setText(bean.getSex());
                mUserGender.setBackgroundResource(R.drawable.shape_fc999b_6soild);
                mUserGender.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.mipmap.woman), null, null, null);
                break;
        }
        pathList = getUrl(bean.getForum_AffixImgList());
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
                intent1.putExtra("comment", CardCommentCount.getText().toString());
                setResult(RESULT_OK, intent1);
                finish();
                break;
            case R.id.item_fragment_interest_icon:
                Intent intent = new Intent(ForumDetailActivity.this, MineHomeActivity.class);
                intent.putExtra("targetUserId",bean.getForum_UserID()+"");
                startActivity(intent);
                break;
            case R.id.item_fragment_interest_attention:
                Map<String, String> param1 = new ArrayMap<>();
                if (bean.getWatchId() == 0) {
                    param1.put("currId", MySharedPreferences.getUserId(this));
                    param1.put("MW_ToID", bean.getForum_ID() + "");
                    param1.put("MW_Type", "0");
                    param1.put("MW_TargetType", "3");
                    NetRequest.ParamPostRequest(PortUtil.MyWatchlistAdd, param1, new NetRequest.getDataCallback() {
                        @Override
                        public void getData(String data) {
                            DataStatusBean bean1 = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                            if ("10000".equals(bean1.getCode())) {
                                mAttention.setText("已关注");
                                bean.setWatchId(bean1.getIdentity());
                            } else
                                ToastSingleUtil.showToast(ForumDetailActivity.this, bean1.getMessage());
                        }
                    });
                }else {
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
                                ToastSingleUtil.showToast(ForumDetailActivity.this, bean1.getMessage());
                        }
                    });
                }
                break;
            case R.id.activity_circle_detail_commit:
                if (TextUtils.isEmpty(mEdit.getText())) {
                    ToastUtils.showShort(ForumDetailActivity.this, "请输入评论内容");
                } else{
                    Map<String,String>param = new HashMap<>();
                    param.put("currId",MySharedPreferences.getUserId(this));
                    param.put("forum_id",getIntent().getStringExtra("id"));
                    param.put("commentContent",UrlDecodeUtil.urlEnCode(mEdit.getText().toString()));
                    param.put("forum_Voice","");
                    param.put("forum_AffixImgList","");
                    NetRequest.ParamPostQueueRequest(PortUtil.ForumDetailAddComment, param, new NetRequest.getDataCallback() {
                        @Override
                        public void getData(String data) {
                            DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                            if ("10000".equals(bean.getCode())) {
                                ToastUtils.showShort(ForumDetailActivity.this, "评论成功");
                                mEdit.getText().clear();
                                Map<String,String>param1 = new HashMap<String, String>();
                                param1.put("currId",MySharedPreferences.getUserId(ForumDetailActivity.this));
                                param1.put("forum_id",getIntent().getStringExtra("id"));
                                param1.put("pageSize","10");
                                param1.put("pageIndex","1");
                                NetRequest.ParamPostQueueRequest(PortUtil.ForumDetailComment, param1, new NetRequest.getDataCallback() {
                                    @Override
                                    public void getData(String data) {
                                        ForumDetailCommentBean bean1 = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ForumDetailCommentBean.class);
                                        list = bean1.getForumCommentList();
                                        int count  = parseInt(mCommentCount.getText().toString().split("（")[1].split("）")[0]);
                                        mCommentCount.setText("评论 （" + (count+1) + "）");
                                        count = Integer.parseInt(CardCommentCount.getText().toString());
                                        CardCommentCount.setText((count+1)+"");
                                        CardCommentCount.postInvalidate();
                                        adapter = new ForumDetailCommentAdapter(list, ForumDetailActivity.this);
                                        mLv.setAdapter(adapter);
                                    }
                                });
                            } else ToastUtils.showShort(ForumDetailActivity.this, "评论失败");
                        }
                    });
                break;
        }}
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
            intent1.putExtra("comment", CardCommentCount.getText().toString());
            setResult(RESULT_OK, intent1);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);          //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
