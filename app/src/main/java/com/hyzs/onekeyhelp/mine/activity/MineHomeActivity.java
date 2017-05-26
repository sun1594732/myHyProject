package com.hyzs.onekeyhelp.mine.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.ShowImageActivity;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.circle.activity.CircleActivity;
import com.hyzs.onekeyhelp.family.movable.activity.MovableListActivity;
import com.hyzs.onekeyhelp.widget.ListViewForScrollView;
import com.hyzs.onekeyhelp.mine.MineHomeAdapterAction;
import com.hyzs.onekeyhelp.mine.MineHomeAdapterCircle;
import com.hyzs.onekeyhelp.mine.bean.HomeBean;
import com.hyzs.onekeyhelp.mine.bean.MineHomeAddContactBean;
import com.hyzs.onekeyhelp.mine.bean.OkBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.ConfirmDialog;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineHomeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack, iv_idcard, imageView2;
    private TextView mTitle, mHomeName, mSign, mAttention, mHelpNumber, heroRanking, tv_zheng, tv_address, mHomeNice, addContact, mAlbumNoPic, mHelpNoPeople, mCircleNoPic, mActionNoPic;
    private ImageView mToolbarRight, photos_right, iv_talk_mine_home;
    private RoundImageView mHomeIcon, photos1, photos2, photos3, photos4;
    private LinearLayout mAlbum, mActive, mCircle;

    private CircleImageView circleImageView1, circleImageView2, circleImageView3, circleImageView4, circleImageView5;
    private ListViewForScrollView lv_circle, lv_action;
    private Dialog pDialog;
    private HomeBean bean;
    private RelativeLayout mEmptyView;
    private Dialog dialog;

    @Override
    protected void assignView() {
        pDialog = ProgressDialog.createProgressLoading(this);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarRight = (ImageView) findViewById(R.id.toolbar_right_img);
        iv_idcard = (ImageView) findViewById(R.id.activity_mine_home_idcard);
        iv_talk_mine_home = (ImageView) findViewById(R.id.iv_talk_mine_home);
        mActionNoPic = (TextView) findViewById(R.id.activity_mine_home_action_nopic);
        mCircleNoPic = (TextView) findViewById(R.id.activity_mine_home_circle_nopic);
        mHelpNoPeople = (TextView) findViewById(R.id.activity_mine_home_help_nopic);
        mAlbumNoPic = (TextView) findViewById(R.id.activity_mine_home_album_nopic);
        mHomeName = (TextView) findViewById(R.id.activity_mine_home_name);
        addContact = (TextView) findViewById(R.id.activity_mine_home_addContact);
        mHomeNice = (TextView) findViewById(R.id.activity_mine_home_nick);
        tv_zheng = (TextView) findViewById(R.id.activity_mine_home_zheng);
        mHomeIcon = (RoundImageView) findViewById(R.id.activity_mine_home_icon);
        mSign = (TextView) findViewById(R.id.activity_mine_home_sign);
        mAttention = (TextView) findViewById(R.id.activity_mine_home_attention);
        mHelpNumber = (TextView) findViewById(R.id.activity_mine_home_helpNumber);
        heroRanking = (TextView) findViewById(R.id.activity_mine_home_heroRanking);
        tv_address = (TextView) findViewById(R.id.activity_mine_home_tv_address);
        lv_circle = (ListViewForScrollView) findViewById(R.id.lv_circle);
        lv_action = (ListViewForScrollView) findViewById(R.id.lv_action);
        circleImageView1 = (CircleImageView) findViewById(R.id.circleImageView1);
        circleImageView2 = (CircleImageView) findViewById(R.id.circleImageView2);
        circleImageView3 = (CircleImageView) findViewById(R.id.circleImageView3);
        circleImageView4 = (CircleImageView) findViewById(R.id.circleImageView4);
        circleImageView5 = (CircleImageView) findViewById(R.id.circleImageView5);
        photos1 = (RoundImageView) findViewById(R.id.photos1);
        photos2 = (RoundImageView) findViewById(R.id.photos2);
        photos3 = (RoundImageView) findViewById(R.id.photos3);
        photos4 = (RoundImageView) findViewById(R.id.photos4);
        photos_right = (ImageView) findViewById(R.id.photos_right);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        mAlbum = (LinearLayout) findViewById(R.id.activity_mine_home_album);
        mCircle = (LinearLayout) findViewById(R.id.activity_mine_home_circle);
        mActive = (LinearLayout) findViewById(R.id.activity_mine_home_active);
        mEmptyView = (RelativeLayout) findViewById(R.id.layout_empty);
    }

    @Override
    protected void initView() {
        mTitle.setText("个人主页");
        mToolbarRight.setVisibility(View.GONE);
        dialog = ConfirmDialog.createConfirmLoading(this, "同时设置为紧急联系人", "取消", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPerpeo(0);
                dialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPerpeo(1);
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void initListener() {
        mAlbum.setOnClickListener(this);
        mActive.setOnClickListener(this);
        mCircle.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mAttention.setOnClickListener(this);
        addContact.setOnClickListener(this);
        mHomeIcon.setOnClickListener(this);
        iv_talk_mine_home.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_home;
    }


    @Override
    protected void initData() {
        pDialog.show();
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("targetUserId", getIntent().getStringExtra("targetUserId"));
        NetRequest.ParamPostRequest(PortUtil.UserHomePage, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    mEmptyView.setVisibility(View.GONE);
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), HomeBean.class);
                    mHomeName.setText(bean.getNickName());
                    NetRequest.loadImg(MineHomeActivity.this, bean.getAvatar(), mHomeIcon);
                    mSign.setText("个性签名： " + bean.getPersonalizedSignature());
                    mHelpNumber.setText(bean.getWeekRespondHelpTotal() + "");
                    heroRanking.setText(bean.getHeroListPosition() + "");
                } catch (Exception e) {
                    Toast.makeText(MineHomeActivity.this, "数据获取异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bean.getRNA() == 1) {//判断是否通过实名认证
                    iv_idcard.setVisibility(View.VISIBLE);
                } else {
                    iv_idcard.setVisibility(View.GONE);
                }
                if (bean.getNCCS() == 1) {//判断是否通过居委会认证
                    tv_zheng.setVisibility(View.VISIBLE);
                } else {
                    tv_zheng.setVisibility(View.GONE);
                }
                tv_address.setText(bean.getCommunity() + "");//社区名称

                mHomeNice.setText(bean.getGrassHero());//草根英雄


                if (TextUtils.isEmpty(bean.getIsExists())) {


                    mAttention.setVisibility(View.VISIBLE);
                    iv_talk_mine_home.setVisibility(View.GONE);
                } else {

                    if ((bean.getIsExists().equals("未添加"))) {
                        //好友
                        mAttention.setVisibility(View.VISIBLE);
                        iv_talk_mine_home.setVisibility(View.GONE);
                    } else {
                        iv_talk_mine_home.setVisibility(View.VISIBLE);
                        mAttention.setVisibility(View.GONE);
                    }
                }
                if (bean.getIsConcern() == 0) {  //0是未关注
                    addContact.setText("添加关注");
                } else {
                    addContact.setText("取消关注");
                }
                if ((bean.getUid() + "").equals(MySharedPreferences.getUserId(MineHomeActivity.this))) {
                    addContact.setVisibility(View.GONE);
                    mAttention.setVisibility(View.GONE);
                }
                if (bean.getCircle().size() == 0) {
                    mCircleNoPic.setVisibility(View.VISIBLE);
                }
                if (bean.getEventDynamic().size() == 0) {
                    mActionNoPic.setVisibility(View.VISIBLE);
                }
                lv_circle.setAdapter(new MineHomeAdapterCircle(MineHomeActivity.this, bean.getCircle()));
                lv_action.setAdapter(new MineHomeAdapterAction(MineHomeActivity.this, bean.getEventDynamic()));

                lv_circle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(MineHomeActivity.this, CircleActivity.class));
                    }
                });

                lv_action.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(MineHomeActivity.this, MovableListActivity.class));
                    }
                });
                //帮助过的人
                setHelpPeople(bean);
                int albumSize;
                if (bean.getAlbums().size() <= 4) {
                    albumSize = bean.getAlbums().size();
                } else albumSize = 4;
                switch (albumSize) {
                    case 0:
                        photos1.setVisibility(View.GONE);
                        photos2.setVisibility(View.GONE);
                        photos3.setVisibility(View.GONE);
                        photos4.setVisibility(View.GONE);
                        photos_right.setVisibility(View.GONE);
                        mAlbumNoPic.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        ImageLoadUtils.setImageForUrl(bean.getAlbums().get(0).getPhotoUrl(), photos1);
                        photos2.setVisibility(View.GONE);
                        photos3.setVisibility(View.GONE);
                        photos4.setVisibility(View.GONE);
                        break;
                    case 2:
                        ImageLoadUtils.setImageForUrl(bean.getAlbums().get(0).getPhotoUrl(), photos1);
                        ImageLoadUtils.setImageForUrl(bean.getAlbums().get(1).getPhotoUrl(), photos2);
                        photos3.setVisibility(View.GONE);
                        photos4.setVisibility(View.GONE);
                        break;
                    case 3:
                        ImageLoadUtils.setImageForUrl(bean.getAlbums().get(0).getPhotoUrl(), photos1);
                        ImageLoadUtils.setImageForUrl(bean.getAlbums().get(1).getPhotoUrl(), photos2);
                        ImageLoadUtils.setImageForUrl(bean.getAlbums().get(2).getPhotoUrl(), photos3);
                        photos4.setVisibility(View.GONE);
                        break;
                    case 4:
                        ImageLoadUtils.setImageForUrl(bean.getAlbums().get(0).getPhotoUrl(), photos1);
                        ImageLoadUtils.setImageForUrl(bean.getAlbums().get(1).getPhotoUrl(), photos2);
                        ImageLoadUtils.setImageForUrl(bean.getAlbums().get(2).getPhotoUrl(), photos3);
                        ImageLoadUtils.setImageForUrl(bean.getAlbums().get(3).getPhotoUrl(), photos4);
                        break;
                }
                pDialog.dismiss();
            }
        });
    }

    //设置帮助人头像
    private void setHelpPeople(HomeBean bean) {
        switch (bean.getHelpPeploes().size()) {
            case 0:
                circleImageView1.setVisibility(View.GONE);
                circleImageView2.setVisibility(View.GONE);
                circleImageView3.setVisibility(View.GONE);
                circleImageView4.setVisibility(View.GONE);
                circleImageView5.setVisibility(View.GONE);
                imageView2.setVisibility(View.GONE);
                mHelpNoPeople.setVisibility(View.VISIBLE);
                break;
            case 1:
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(0).getAvatar(), circleImageView1);
                circleImageView2.setVisibility(View.GONE);
                circleImageView3.setVisibility(View.GONE);
                circleImageView4.setVisibility(View.GONE);
                circleImageView5.setVisibility(View.GONE);
                break;

            case 2:
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(0).getAvatar(), circleImageView1);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(1).getAvatar(), circleImageView2);
                circleImageView3.setVisibility(View.GONE);
                circleImageView4.setVisibility(View.GONE);
                circleImageView3.setVisibility(View.GONE);
                circleImageView5.setVisibility(View.GONE);
                break;
            case 3:
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(0).getAvatar(), circleImageView1);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(1).getAvatar(), circleImageView2);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(2).getAvatar(), circleImageView3);
                circleImageView4.setVisibility(View.GONE);
                circleImageView5.setVisibility(View.GONE);
                break;
            case 4:
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(0).getAvatar(), circleImageView1);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(1).getAvatar(), circleImageView2);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(2).getAvatar(), circleImageView3);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(3).getAvatar(), circleImageView4);
                circleImageView5.setVisibility(View.GONE);
                break;
            case 5:
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(0).getAvatar(), circleImageView1);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(1).getAvatar(), circleImageView2);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(2).getAvatar(), circleImageView3);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(3).getAvatar(), circleImageView4);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(4).getAvatar(), circleImageView5);
                break;
            default:
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(0).getAvatar(), circleImageView1);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(1).getAvatar(), circleImageView2);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(2).getAvatar(), circleImageView3);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(3).getAvatar(), circleImageView4);
                ImageLoadUtils.setImageForUrl(bean.getHelpPeploes().get(4).getAvatar(), circleImageView5);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_mine_home_attention:
                dialog.show();

                break;
            case R.id.activity_mine_home_album:
                Intent intent = new Intent(MineHomeActivity.this, MineAlbumActivity.class);
                intent.putExtra("targetUserID", bean.getUid() + "");
                startActivity(intent);
                break;
            case R.id.activity_mine_home_circle:
                Intent intent1 = new Intent(MineHomeActivity.this, CircleActivity.class);
                intent1.putExtra("targetUserID", bean.getUid() + "");
                intent1.putExtra("type", bean.getCircle().get(0).getCircle_Type() + "");
                startActivity(intent1);
                break;
            case R.id.activity_mine_home_active:
                Intent intent2 = new Intent(MineHomeActivity.this, MovableListActivity.class);
                intent2.putExtra("title", "周末生活");
                intent2.putExtra("targetUserID", bean.getUid() + "");
                startActivity(intent2);
                break;
            case R.id.activity_mine_home_addContact:
                if (addContact.getText().toString().equals("添加关注")) {
                    addContact();
                } else {
                    cancelContact();
                }
                break;
            case R.id.activity_mine_home_icon:
                Intent ShowImage = new Intent(this, ShowImageActivity.class);
                ShowImage.putExtra("position", 0);
                ArrayList<String> array = new ArrayList<>();
                array.add(bean.getAvatar());
                ShowImage.putStringArrayListExtra("url", array);
                startActivity(ShowImage);
                break;
            case R.id.iv_talk_mine_home:
                //跳转到聊天界面
                MySharedPreferences.getInstance(this).setString("Img", bean.getAvatar());
                Intent intent4 = new Intent(this, IntentChatActivity.class);
                intent4.putExtra("one", bean.getUid() + "");
                intent4.putExtra("userName", bean.getNickName());
                startActivity(intent4);
                break;
        }
    }

    public void addPerpeo(int type) {
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("targetUserId", getIntent().getStringExtra("targetUserId"));
        param.put("groupName", "1");
        param.put("reqType", "0");
        param.put("addType", "0");
        param.put("phone", "");
        param.put("trueName", "");
        param.put("isSetUrgencyContact", type+"");
        NetRequest.CommonUseListRequestMy(PortUtil.FriendAddCheck, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                OkBean okBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), OkBean.class);
                if (okBean.getCode().equals("10000")) {
                    Toast.makeText(MineHomeActivity.this, "已申请", Toast.LENGTH_SHORT).show();
                    mAttention.setVisibility(View.GONE);
                } else
                    Toast.makeText(MineHomeActivity.this, okBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancelContact() {
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("MW_ToID", bean.getWatchId() + "");

        NetRequest.CommonUseListRequestMy(PortUtil.MyWatchlistCancel, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                OkBean okBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), OkBean.class);
                if (okBean.getCode().equals("10000")) {
                    Toast.makeText(MineHomeActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                    addContact.setText("添加关注");
                } else {
                    Toast.makeText(MineHomeActivity.this, okBean.getMessage(), Toast.LENGTH_SHORT).show(); //TODO
                }
            }
        });
    }


    public void addContact() {
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("MW_ToID", getIntent().getStringExtra("targetUserId"));
        param.put("MW_Type", "0");
        param.put("MW_TargetType", "1");
        NetRequest.CommonUseListRequestMy(PortUtil.MyWatchlistAdd, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                MineHomeAddContactBean okBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineHomeAddContactBean.class);
                if (okBean.getCode().equals("10000")) {
                    Toast.makeText(MineHomeActivity.this, "已关注", Toast.LENGTH_SHORT).show();
                    addContact.setText("取消关注");
                    bean.setWatchId(okBean.getIdentity() + "");
                } else
                    Toast.makeText(MineHomeActivity.this, okBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onResume() {
        super.onResume();
        mTitle.setFocusableInTouchMode(true);
        mTitle.requestFocus();
        mTitle.setFocusable(true);
        MobclickAgent.onPageStart("MineHomeActivity");
        MobclickAgent.onResume(this);
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MineHomeActivity");
        MobclickAgent.onPause(this);
    }
}
