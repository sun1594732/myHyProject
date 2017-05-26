package com.hyzs.onekeyhelp.mine;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyzs.onekeyhelp.MyApplication;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.activity.ForgetActivity;
import com.hyzs.onekeyhelp.contact.activity.LoginActivity;
import com.hyzs.onekeyhelp.help.activity.HelpListActivity;
import com.hyzs.onekeyhelp.mine.CarManager.activity.CarManagerActivity;
import com.hyzs.onekeyhelp.mine.activity.ChangeIconActivity;
import com.hyzs.onekeyhelp.mine.active.MineActiveActivity;
import com.hyzs.onekeyhelp.mine.activity.MineAlbumActivity;
import com.hyzs.onekeyhelp.mine.activity.MineSignActivity;
import com.hyzs.onekeyhelp.mine.attention.MineAttentionActivity;
import com.hyzs.onekeyhelp.mine.activity.MineAuthenticationActivity;
import com.hyzs.onekeyhelp.mine.activity.MineEditActivity;
import com.hyzs.onekeyhelp.mine.activity.MineFamilyActivity;
import com.hyzs.onekeyhelp.mine.company.MineCompanyActivity;
import com.hyzs.onekeyhelp.mine.forum.MineForumActivity;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.mine.activity.MinePurseActivity;
import com.hyzs.onekeyhelp.mine.activity.MineUrgentActivity;
import com.hyzs.onekeyhelp.mine.bean.MineInfoBean;
import com.hyzs.onekeyhelp.mine.bean.MineSignInBean;
import com.hyzs.onekeyhelp.mine.circle.MineCircleActivity;
import com.hyzs.onekeyhelp.mine.community.activity.CommunityListActivity;
import com.hyzs.onekeyhelp.mine.customerservice.activity.CustomerServiceActivity;
import com.hyzs.onekeyhelp.mine.opinion.activity.OpinionActivity;
import com.hyzs.onekeyhelp.module.housekeeping.activity.OrderH5Activity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;


public class MineFragment extends Fragment implements View.OnClickListener {
    private TextView mTitle, mToolbarRight, mMoney, mSignIn, mInvitation, mName, mSign, mChangeIcon, mBalance, mPoint;
    private LinearLayout mCharge, mActive, mAlbum, mAlert, mHelp, mMessage, mPost, mCircle, mMember, mCarManager,
            mAttention, mAuthentication, mCommunity, mFeedBack, mkeFu ,mUrgent ,mRecord ,mCompany;
    private ImageView mBack, mEdit, mRightBtn, mEmptyViewBg, mEmptyViewBtn;
    private CircleImageView mIcon;
    private Map<String, String> hashMap;
    private String singIn = PortUtil.PersonalSignIn;
    private MySharedPreferences mySharedPreferences;
    private PopupWindow popupWindow;
    private Dialog signOffDialog;
    private MineInfoBean.PersonalUserInfoBean bean;
    private RelativeLayout mEmptyView;
    private PopupWindow pop;
    private LinearLayout ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        assignView(view);
        initView();
        initPop();
        initDialog();
        initListener();
        return view;
    }

    private void initListener() {
        mIcon.setOnClickListener(this);
        mCharge.setOnClickListener(this);
        mAlbum.setOnClickListener(this);
        mActive.setOnClickListener(this);
        mPost.setOnClickListener(this);
        mCircle.setOnClickListener(this);
        mHelp.setOnClickListener(this);
        mCarManager.setOnClickListener(this);
        mMember.setOnClickListener(this);
        mSignIn.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);
        mChangeIcon.setOnClickListener(this);
        mAttention.setOnClickListener(this);
        mUrgent.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        mCommunity.setOnClickListener(this);
        mFeedBack.setOnClickListener(this);
        mkeFu.setOnClickListener(this);
        mAuthentication.setOnClickListener(this);
        mEmptyViewBtn.setOnClickListener(this);
        mCompany.setOnClickListener(this);
        mRecord.setOnClickListener(this);
    }

    private void initView() {
        mBack.setVisibility(View.GONE);
        mySharedPreferences = MySharedPreferences.getInstance(getActivity());
        mTitle.setText("个人中心");
        SpannableString content = new SpannableString(mChangeIcon.getText());
        content.setSpan(new UnderlineSpan(), 0, mChangeIcon.getText().length(), 0);
        mChangeIcon.setText(content);
        mToolbarRight.setVisibility(View.GONE);
        mRightBtn.setVisibility(View.VISIBLE);
        mEmptyViewBtn.setVisibility(View.VISIBLE);
        mEmptyViewBg.setVisibility(View.VISIBLE);
    }

    private void assignView(View view) {
        ll =(LinearLayout) view.findViewById(R.id.fragment_mine);
        pop = HelpDialog.createDialogNoAlert(getActivity(), this, "您还没有登录", 0);
        mFeedBack = (LinearLayout) view.findViewById(R.id.fragment_mine_feedback);
        mkeFu = (LinearLayout) view.findViewById(R.id.fragment_mine_customerService);
        mCommunity = (LinearLayout) view.findViewById(R.id.fragment_mine_community);
        mRightBtn = (ImageView) view.findViewById(R.id.toolbar_right_img);
        mTitle = (TextView) view.findViewById(R.id.toolbar_title);
        mToolbarRight = (TextView) view.findViewById(R.id.toolbar_right);
        mBack = (ImageView) view.findViewById(R.id.toolbar_back);
        mName = (TextView) view.findViewById(R.id.fragment_mine_name);
        mSign = (TextView) view.findViewById(R.id.fragment_mine_sign);
        mBalance = (TextView) view.findViewById(R.id.fragment_mine_balance);
        mPoint = (TextView) view.findViewById(R.id.fragment_mine_point);
        mIcon = (CircleImageView) view.findViewById(R.id.fragment_mine_icon);
        mChangeIcon = (TextView) view.findViewById(R.id.fragment_mine_changeIcon);
        mSignIn = (TextView) view.findViewById(R.id.fragment_mine_signIn);
        mCharge = (LinearLayout) view.findViewById(R.id.fragment_mine_charge);
        mInvitation = (TextView) view.findViewById(R.id.fragment_mine_invitation);
        mActive = (LinearLayout) view.findViewById(R.id.fragment_mine_active);
        mAlbum = (LinearLayout) view.findViewById(R.id.fragment_mine_album);
        mAlert = (LinearLayout) view.findViewById(R.id.fragment_mine_alert);
        mHelp = (LinearLayout) view.findViewById(R.id.fragment_mine_help);
        mMessage = (LinearLayout) view.findViewById(R.id.fragment_mine_message);
        mPost = (LinearLayout) view.findViewById(R.id.fragment_mine_post);
        mUrgent = (LinearLayout) view.findViewById(R.id.fragment_mine_urgent);
        mCircle = (LinearLayout) view.findViewById(R.id.fragment_mine_circle);
        mMember = (LinearLayout) view.findViewById(R.id.fragment_mine_member);
        mCarManager = (LinearLayout) view.findViewById(R.id.fragment_mine_car);
        mEdit = (ImageView) view.findViewById(R.id.fragment_mine_edit);
        mAttention = (LinearLayout) view.findViewById(R.id.fragment_mine_attention);
        mAuthentication = (LinearLayout) view.findViewById(R.id.fragment_mine_authentication);
        mEmptyView = (RelativeLayout) view.findViewById(R.id.layout_empty);
        mEmptyViewBg = (ImageView) view.findViewById(R.id.layout_empty_bg);
        mEmptyViewBtn = (ImageView) view.findViewById(R.id.layout_empty_login);
        mRecord = (LinearLayout) view.findViewById(R.id.fragment_mine_record);
        mCompany = (LinearLayout) view.findViewById(R.id.fragment_mine_sjrk);
    }

    @Override
    public void onClick(View v) {
        if (!MySharedPreferences.isLogin(getActivity())&&v.getId()!=R.id.layout_empty_login) {
            pop.showAtLocation(ll, Gravity.CENTER,0,0);
            return;
        }
        switch (v.getId()) {
            case R.id.help_pop_confirm:
                pop.dismiss();
                break;
            case R.id.fragment_mine_feedback:
                startActivity(new Intent(getActivity(), OpinionActivity.class));
                break;
            case R.id.fragment_mine_customerService:
                startActivity(new Intent(getActivity(), CustomerServiceActivity.class));
                break;
            case R.id.fragment_mine_community:
                startActivity(new Intent(getActivity(), CommunityListActivity.class));
                break;
            case R.id.fragment_mine_authentication:
                startActivity(new Intent(getActivity(), MineAuthenticationActivity.class));
                break;
            case R.id.fragment_mine_edit:
                startActivity(new Intent(getActivity(), MineEditActivity.class));
                break;
            case R.id.layout_menu_changePWD:
                startActivity(new Intent(getActivity(), ForgetActivity.class));
                popupWindow.dismiss();
                break;
            case R.id.toolbar_right_img:
                popupWindow.showAsDropDown(mRightBtn);
                break;
            case R.id.layout_menu_singOff:
                popupWindow.dismiss();
                signOffDialog.show();
                break;
            case R.id.fragment_mine_icon:
                Intent intent = new Intent(getActivity(), MineHomeActivity.class);
                intent.putExtra("targetUserId", MySharedPreferences.getUserId(getActivity()));
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.fragment_mine_active:
                startActivity(new Intent(getActivity(), MineActiveActivity.class));
                break;
            case R.id.fragment_mine_post:
                startActivity(new Intent(getActivity(), MineForumActivity.class));
                break;
            case R.id.fragment_mine_circle:
                startActivity(new Intent(getActivity(), MineCircleActivity.class));
                break;
            case R.id.fragment_mine_help:
                startActivity(new Intent(getActivity(), HelpListActivity.class));
                break;
            case R.id.fragment_mine_car:
                startActivity(new Intent(getActivity(), CarManagerActivity.class));
                break;
            case R.id.fragment_mine_member:
                startActivity(new Intent(getActivity(), MineFamilyActivity.class));
                break;
            case R.id.fragment_mine_signIn:
                startActivity(new Intent(getActivity(), MineSignActivity.class));
//                signIn();
                break;
            case R.id.fragment_mine_charge:
                startActivity(new Intent(getActivity(), MinePurseActivity.class));
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.fragment_mine_album:
                startActivity(new Intent(getActivity(), MineAlbumActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                break;
            case R.id.fragment_mine_changeIcon:
                startActivity(new Intent(getActivity(), ChangeIconActivity.class));
                break;
            case R.id.fragment_mine_attention:
                startActivity(new Intent(getActivity(), MineAttentionActivity.class));
                break;
            case R.id.layout_empty_login:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;
            case R.id.fragment_mine_urgent:
                startActivity(new Intent(getActivity(),MineUrgentActivity.class));
                break;
            case R.id.fragment_mine_sjrk:
                startActivity(new Intent(getActivity(),MineCompanyActivity.class));
                break;
            case R.id.fragment_mine_record:
                Intent intent1 = new Intent(getActivity(), OrderH5Activity.class);
                intent1.putExtra("mine",true);
                intent1.putExtra("sppId","0");
                intent1.putExtra("lsId","0");
                intent1.putExtra("type","0");
                startActivity(intent1);
                break;
        }
    }

    public void signIn() {
        String uid = null;
        if (!MySharedPreferences.isLogin(this.getContext())) {
            uid = "0";
        } else {
            uid = MySharedPreferences.getInstance(this.getContext()).getString("uid");
        }
        hashMap = new HashMap<>();
        hashMap.put("currId", uid);
        NetRequest.CommonUseListRequestMy(singIn, hashMap, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    MineSignInBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineSignInBean.class);
                    Toast.makeText(MineFragment.this.getContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MineFragment.this.getActivity(), "请刷新重试！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        if (MySharedPreferences.isLogin(getActivity())) {
            Map<String, String> param = new ArrayMap<>();
            param.put("currId", MySharedPreferences.getUserId(getActivity()));
            NetRequest.ParamPostRequest(PortUtil.MineInfo, param, new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    mEmptyView.setVisibility(View.GONE);
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineInfoBean.class).getPersonalUserInfo();
                    mName.setText(bean.getUesrname());
                    mSign.setText("个人签名  :  " + bean.getPersonalizedSignature());
                    mBalance.setText("" + bean.getYue());
                    mPoint.setText(bean.getPoints() + "");
                    if (TextUtils.isEmpty(bean.getFace())) {
                        mIcon.setImageResource(R.mipmap.icon_replace);
                    } else {
                        NetRequest.loadImg(getActivity(), bean.getFace(), mIcon);
                        mySharedPreferences.setString("myImg", bean.getFace());
                    }
                }
            });
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        MobclickAgent.onPageStart("Mine" + "Fragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Mine" + "Fragment");
    }

    private void initPop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_menu, null, false);
        popupWindow = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true); //设置PopupWindow可触摸
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
        TextView signOff = (TextView) view.findViewById(R.id.layout_menu_singOff);
        TextView changePWD = (TextView) view.findViewById(R.id.layout_menu_changePWD);
        signOff.setOnClickListener(this);
        changePWD.setOnClickListener(this);
    }

    private void initDialog() {
        View DialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_signoff, null);
        assignDialogView(DialogView);
        signOffDialog = new AlertDialog.Builder(getActivity()).create();
        signOffDialog.show();
        signOffDialog.getWindow().setContentView(DialogView);
        signOffDialog.dismiss();
    }

    private void assignDialogView(View dialogView) {
        TextView title = (TextView) dialogView.findViewById(R.id.layout_dialog_signOff_title);
        TextView sure = (TextView) dialogView.findViewById(R.id.layout_dialog_signOff_sure);
        TextView cancel = (TextView) dialogView.findViewById(R.id.layout_dialog_signOff_cancel);
        title.setText("确定退出登录?");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOffDialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOffDialog.dismiss();
//                退出登陆后把用户本地信息清空，包括用户ID，头像，小区ID，小区名等,登陆界面的机主手机号留存
                JPushInterface.stopPush(MyApplication.getAppContext());
//                mySharedPreferences.clear();
                mySharedPreferences.setString("uid", "");
                EMClient.getInstance().logout(true);
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}
