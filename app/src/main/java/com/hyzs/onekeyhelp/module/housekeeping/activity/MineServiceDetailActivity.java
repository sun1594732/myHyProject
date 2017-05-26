package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.AgencyServiceDetailProjectAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.MineServiceDetailCommentAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.MineServiceDetailExperienceAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.MineServiceDetailProjectAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.bean.MineServiceDetailBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.ListViewForScrollView;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineServiceDetailActivity extends BaseActivity implements View.OnClickListener, AgencyServiceDetailProjectAdapter.ServiceProjectClickListener {

    private static final String TAG = "MineServiceDetailActivi";
    private TextView ageTitle, title, right;
    private ImageView back, img1, img2;
    private View header;
    private ListViewForScrollView experienceListView, commentListView;
    private RecyclerView projectListView;
    private MineServiceDetailBean bean;
    private RoundImageView icon;
    private RatingBar ratingBar;
    private TextView name, native_place, charges, range, tag1, tag2, tag3, IDcard, phone, goodAtType, serviceType, age, serviceCity, maritalStatus, education, yearsOfWork, workStatus, commentDetail, projectDetail;
    private List<MineServiceDetailBean.WorkExperienceBean> experienceList = new ArrayList<>();
    private List<MineServiceDetailBean.UserCommentBean> commentList = new ArrayList<>();
    private List<MineServiceDetailBean.ServiceProjectBean> projectList = new ArrayList<>();
    private MineServiceDetailExperienceAdapter experienceAdapter;
    private MineServiceDetailProjectAdapter projectAdapter;
    private MineServiceDetailCommentAdapter commentAdapter;
    private MySharedPreferences mySharedPreferences;
    private LinearLayout chat, reservation, call_phone;

    @Override
    protected void assignView() {
        right = (TextView) findViewById(R.id.toolbar_right);
        projectDetail = (TextView) findViewById(R.id.activity_mine_service_detail_project_detail);
        ratingBar = (RatingBar) findViewById(R.id.item_mine_service_ratingbar);
        commentDetail = (TextView) findViewById(R.id.activity_mine_service_detail_comment_detail);
        header = findViewById(R.id.activity_mine_service_detail_header);
        header.setFocusable(true);
        header.setFocusableInTouchMode(true);
        mySharedPreferences = MySharedPreferences.getInstance(this);
        chat = (LinearLayout) findViewById(R.id.activity_mine_service_detail_chat);
        reservation = (LinearLayout) findViewById(R.id.activity_mine_service_detail_reservation);
        call_phone = (LinearLayout) findViewById(R.id.activity_mine_service_detail_call_phone);
        commentListView = (ListViewForScrollView) findViewById(R.id.activity_mine_service_detail_comment_listview);
        projectListView = (RecyclerView) findViewById(R.id.activity_mine_service_detail_project_listview);
        ageTitle = (TextView) findViewById(R.id.activity_mine_service_detail_age_title);
        title = (TextView) findViewById(R.id.toolbar_title);
        back = (ImageView) findViewById(R.id.toolbar_back);
        img1 = (ImageView) findViewById(R.id.item_mine_service_layout_call_phone);
        img2 = (ImageView) findViewById(R.id.item_mine_service_layout_chat);
        img1.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        experienceListView = (ListViewForScrollView) findViewById(R.id.activity_mine_service_detail_experience_listview);
        icon = (RoundImageView) findViewById(R.id.item_mine_service_icon);
        name = (TextView) findViewById(R.id.item_mine_service_name);
        native_place = (TextView) findViewById(R.id.item_mine_service_native_place);
        charges = (TextView) findViewById(R.id.item_mine_service_charges);
        range = (TextView) findViewById(R.id.item_mine_service_range);
        tag1 = (TextView) findViewById(R.id.item_mine_service_tag1);
        tag2 = (TextView) findViewById(R.id.item_mine_service_tag2);
        tag3 = (TextView) findViewById(R.id.item_mine_service_tag3);

        IDcard = (TextView) findViewById(R.id.activity_mine_service_detail_ID_card);
        phone = (TextView) findViewById(R.id.activity_mine_service_detail_phone);
        goodAtType = (TextView) findViewById(R.id.activity_mine_service_detail_good_at_type);
        serviceType = (TextView) findViewById(R.id.activity_mine_service_detail_service_type);
        age = (TextView) findViewById(R.id._activity_mine_service_detail_age);
        serviceCity = (TextView) findViewById(R.id.activity_mine_service_detail_service_city);
        maritalStatus = (TextView) findViewById(R.id.activity_mine_service_detail_marital_status);
        education = (TextView) findViewById(R.id.activity_mine_service_detail_education);
        yearsOfWork = (TextView) findViewById(R.id.activity_mine_service_detail_years_of_work);
        workStatus = (TextView) findViewById(R.id.activity_mine_service_detail_work_status);
    }

    @Override
    protected void initView() {
        right.setText("投诉");
        title.setText("个人服务");
        right.setVisibility(View.VISIBLE);
        SpannableStringBuilder builder = new SpannableStringBuilder(ageTitle.getText().toString());
        ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);
        builder.setSpan(whiteSpan, 1, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        ageTitle.setText(builder);
        experienceAdapter = new MineServiceDetailExperienceAdapter(this, experienceList);
        experienceListView.setAdapter(experienceAdapter);
        experienceListView.setSelection(2);
        projectListView.setVisibility(View.VISIBLE);
        projectListView.setLayoutManager(new GridLayoutManager(this, 2));
        projectAdapter = new MineServiceDetailProjectAdapter(this, projectList);
        projectListView.setAdapter(projectAdapter);
        commentAdapter = new MineServiceDetailCommentAdapter(this, commentList);
        commentListView.setAdapter(commentAdapter);
        commentListView.setSelection(2);
    }

    @Override
    protected void initListener() {
        right.setOnClickListener(this);
        back.setOnClickListener(this);
        chat.setOnClickListener(this);
        reservation.setOnClickListener(this);
        call_phone.setOnClickListener(this);
        commentDetail.setOnClickListener(this);
        projectDetail.setOnClickListener(this);
        projectAdapter.setListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_service_detail;
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
//        map.put("currId", "1");
        map.put("LS_ID", getIntent().getStringExtra("LS_ID"));
        map.put("location", mySharedPreferences.getString("X") + "," + mySharedPreferences.getString("Y"));
        NetRequest.ParamPostRequest(PortUtil.LifeServicePersonalDetails, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                Log.e(TAG, "getData: " + UrlDecodeUtil.urlCode(data));
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineServiceDetailBean.class);
                inflaterData();
            }
        });
    }

    private void inflaterData() {
        if (TextUtils.isEmpty(bean.getPersonalInfo().getFace())) {
            icon.setImageResource(R.drawable.icon_replace);
        } else NetRequest.loadImg(this, bean.getPersonalInfo().getFace(), icon);
        name.setText(bean.getPersonalInfo().getName());
        charges.setText(bean.getPersonalInfo().getMsalary() + "元/月");
        native_place.setText(bean.getPersonalInfo().getPSIA_NativePlace());
        range.setText(bean.getPersonalInfo().getPSIA_ServiceRangeDesc());
        ratingBar.setIsIndicator(true);
        ratingBar.setNumStars(bean.getPersonalInfo().getRecommendIndex());
        if (!TextUtils.isEmpty(bean.getPersonalInfo().getTag())) {
            String[] tags = bean.getPersonalInfo().getTag().split(",");
            tag1.setText(tags[0]);
            tag2.setText(tags[1]);
            tag3.setText(tags[2]);
        }

        IDcard.setText(bean.getPersonalInfo().getPSIA_ICN());
        phone.setText(bean.getPersonalInfo().getPSIA_Phone());
        goodAtType.setText(bean.getPersonalInfo().getPSIA_SP());
        if (bean.getPersonalInfo().getPSIA_ServiceType() == 0) {
            serviceType.setText("兼职");
        } else serviceType.setText("全职");

        age.setText(bean.getPersonalInfo().getAge() + "");
        serviceCity.setText(bean.getPersonalInfo().getPSIA_ServiceRangeDesc());
        maritalStatus.setText(bean.getPersonalInfo().getPSIA_MaritalState());
        education.setText(bean.getPersonalInfo().getPSIA_CultureLevel());
        yearsOfWork.setText(bean.getPersonalInfo().getWorkAge());
        workStatus.setText(bean.getPersonalInfo().getPSIA_WorkState());

        experienceList.clear();
        experienceList.addAll(bean.getWorkExperience());
        experienceAdapter.notifyDataSetChanged();

        projectList.clear();
        projectList.addAll(bean.getServiceProject());
        projectAdapter.notifyDataSetChanged();

        commentList.clear();
        commentList.addAll(bean.getUserComment());
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.toolbar_right:
                intent = new Intent(this, ServiceComplaintsActivity.class);
                intent.putExtra("icon", bean.getPersonalInfo().getFace());
                intent.putExtra("name", bean.getPersonalInfo().getName());
                intent.putExtra("LS_ID", getIntent().getStringExtra("LS_ID"));
                startActivity(intent);
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_mine_service_detail_chat:
                MySharedPreferences.getInstance(this).setString("Img", bean.getPersonalInfo().getFace());
                NotifyMsgCountUtil.notifyMsg(bean.getPersonalInfo().getLS_UserId() + "");
                intent = new Intent();
                intent.putExtra("one", bean.getPersonalInfo().getLS_UserId() + "");
                intent.putExtra("userName", bean.getPersonalInfo().getName());
                intent.setClass(this, IntentChatActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_mine_service_detail_reservation:
                intent = new Intent(this, ServiceProjectListActivity.class);
                intent.putExtra("LS_ID", getIntent().getStringExtra("LS_ID"));
                startActivity(intent);
                break;
            case R.id.activity_mine_service_detail_call_phone:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "您还没有授予打电话权限", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!TextUtils.isEmpty(bean.getPersonalInfo().getTag())) {
                            intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + bean.getPersonalInfo().getTag());
                            intent.setData(data);
                            startActivity(intent);
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(bean.getPersonalInfo().getTag())) {
                        intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + bean.getPersonalInfo().getTag());
                        intent.setData(data);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.activity_mine_service_detail_comment_detail:
                intent = new Intent(this, ServiceCommentListActivity.class);
                intent.putExtra("LS_ID", getIntent().getStringExtra("LS_ID"));
                intent.putExtra("personalInfo", bean.getPersonalInfo());
                startActivity(intent);
                break;
            case R.id.activity_mine_service_detail_project_detail:
                intent = new Intent(this, ServiceProjectListActivity.class);
                intent.putExtra("LS_ID", getIntent().getStringExtra("LS_ID"));
                startActivity(intent);
                break;
        }
    }

    @Override
    public void click(View view, int position) {
        Intent intent = new Intent(MineServiceDetailActivity.this, ServiceProjectDetailActivity.class);
        intent.putExtra("id", bean.getServiceProject().get(position).getID() + "");
        startActivity(intent);
    }
}
