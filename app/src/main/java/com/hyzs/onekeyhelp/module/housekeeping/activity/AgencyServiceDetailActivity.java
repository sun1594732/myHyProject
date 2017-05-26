package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.ShowImageActivity;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.AgencyServiceDetailCommentAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.AgencyServiceDetailProjectAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.bean.AgencyServiceDetailBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.ListViewForScrollView;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.hyzs.onekeyhelp.widget.view.uilts.myviewpager.HeadViewPager;
import com.hyzs.onekeyhelp.widget.view.uilts.myviewpager.HeadViewPagerAdapter;
import com.hyzs.onekeyhelp.widget.view.uilts.myviewpager.HeadViewPagerTransformer;
import com.hyzs.onekeyhelp.widget.view.uilts.myviewpager.MyImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgencyServiceDetailActivity extends BaseActivity implements View.OnClickListener,AgencyServiceDetailProjectAdapter.ServiceProjectClickListener {
    private static final String TAG = "AgencyServiceDetailActi";
    private View header;
    private ImageView img1, img2 ,left,right;
    private TextView title,toolBarRight;
    private ImageView back;
    private RecyclerView projectListView;
    private ListViewForScrollView commentListView;
    private AgencyServiceDetailBean bean;
    private RoundImageView icon;
    private TextView name, distance, content, phone, address, business_license, time, range, service_address, commentDetail, projectDetail;
    private List<AgencyServiceDetailBean.ServiceProjectBean> projectList = new ArrayList<>();
    private List<AgencyServiceDetailBean.UserCommentBean> commentList = new ArrayList<>();
    private AgencyServiceDetailCommentAdapter commentAdapter;
    private AgencyServiceDetailProjectAdapter projectAdapter;
    private MySharedPreferences mySharedPreferences;
    private LinearLayout chat, reservation, call_phone;
    private ViewPager headViewPager;
    private List<MyImageView> imgList;
    private ArrayList<String> stringList;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void assignView() {
        toolBarRight = (TextView) findViewById(R.id.toolbar_right);
        mySharedPreferences = MySharedPreferences.getInstance(this);
        projectDetail = (TextView) findViewById(R.id.activity_agency_service_detail_project_detail);
        commentDetail = (TextView) findViewById(R.id.activity_agency_service_detail_comment_detail);
        headViewPager = (ViewPager) findViewById(R.id.activity_agency_service_horizontal);
        chat = (LinearLayout) findViewById(R.id.activity_agency_service_detail_chat);
        reservation = (LinearLayout) findViewById(R.id.activity_agency_service_detail_reservation);
        call_phone = (LinearLayout) findViewById(R.id.activity_agency_service_detail_call_phone);
        img1 = (ImageView) findViewById(R.id.item_agency_service_layout_call_phone);
        img2 = (ImageView) findViewById(R.id.item_agency_service_layout_chat);
        left = (ImageView) findViewById(R.id.activity_agency_service_horizontal_left);
        right = (ImageView) findViewById(R.id.activity_agency_service_horizontal_right);
        img1.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        header = findViewById(R.id.activity_agency_service_detail_header);
        header.setFocusable(true);
        header.setFocusableInTouchMode(true);
        title = (TextView) findViewById(R.id.toolbar_title);
        back = (ImageView) findViewById(R.id.toolbar_back);
        projectListView = (RecyclerView) findViewById(R.id.activity_agency_service_detail_project_listview);
        commentListView = (ListViewForScrollView) findViewById(R.id.activity_agency_service_detail_comment_listview);
        icon = (RoundImageView) findViewById(R.id.item_agency_service_layout_icon);
        name = (TextView) findViewById(R.id.item_agency_service_layout_name);
        distance = (TextView) findViewById(R.id.item_agency_service_layout_distance);
        content = (TextView) findViewById(R.id.item_agency_service_layout_content);
        phone = (TextView) findViewById(R.id.item_agency_service_layout_phone);
        address = (TextView) findViewById(R.id.item_agency_service_layout_address);
        business_license = (TextView) findViewById(R.id.activity_agency_service_detail_business_license);
        time = (TextView) findViewById(R.id.activity_agency_service_detail_time);
        range = (TextView) findViewById(R.id.activity_agency_service_detail_range);
        service_address = (TextView) findViewById(R.id.activity_agency_service_detail_address);
    }

    @Override
    protected void initView() {
        toolBarRight.setText("投诉");
        title.setText("机构服务");
        toolBarRight.setVisibility(View.VISIBLE);
        projectListView.setVisibility(View.VISIBLE);
        projectListView.setLayoutManager(new GridLayoutManager(this, 2));
        projectAdapter = new AgencyServiceDetailProjectAdapter(this, projectList);
        projectListView.setAdapter(projectAdapter);
        commentAdapter = new AgencyServiceDetailCommentAdapter(this, commentList);
        commentListView.setAdapter(commentAdapter);
        commentListView.setSelection(2);
    }

    @Override
    protected void initListener() {
        toolBarRight.setOnClickListener(this);
        back.setOnClickListener(this);
        chat.setOnClickListener(this);
        reservation.setOnClickListener(this);
        call_phone.setOnClickListener(this);
        commentDetail.setOnClickListener(this);
        projectDetail.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        projectAdapter.setListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_agency_service_layout;
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("LS_ID", getIntent().getStringExtra("LS_ID"));
        map.put("location", mySharedPreferences.getString("X") + "," + mySharedPreferences.getString("Y"));
        NetRequest.ParamPostRequest(PortUtil.LifeServiceOrganizationDetails, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), AgencyServiceDetailBean.class);
                inflaterData();
            }
        });
    }

    private void inflaterData() {
        if (TextUtils.isEmpty(bean.getBasicInfo().getLogo())) {
            icon.setImageResource(R.drawable.icon_replace);
        } else NetRequest.loadImg(this, bean.getBasicInfo().getLogo(), icon);
        name.setText(bean.getBasicInfo().getName());
        distance.setText(bean.getBasicInfo().getDistance());
        address.setText(bean.getBasicInfo().getAddress());
        phone.setText(bean.getBasicInfo().getTel());
        content.setText(bean.getBasicInfo().getIntro());

        business_license.setText(bean.getOrgInfo().getOSIA_BusinessLicense());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        time.setText(format.format(Long.parseLong(bean.getOrgInfo().getOSIA_SetUpDate()) * 1000));
        range.setText(bean.getOrgInfo().getOSIA_SP());
        service_address.setText(bean.getOrgInfo().getOSIA_Desc());

        projectList.clear();
        projectList.addAll(bean.getServiceProject());
        projectAdapter.notifyDataSetChanged();

        commentList.clear();
        commentList.addAll(bean.getUserComment());
        commentAdapter.notifyDataSetChanged();

        String[] imgs = bean.getOrgInfo().getOSIA_AffiList().split(",");
        imgList = new ArrayList<>();
        stringList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            String url = imgs[i].substring(1, imgs[i].length() - 1);
            MyImageView v = new MyImageView(this);
            NetRequest.loadImg(this, url, v.getmImageView());
            imgList.add(v);
            stringList.add(url);
        }

        headViewPager.setAdapter(new HeadViewPagerAdapter(this, imgList, stringList));
        headViewPager.setOffscreenPageLimit(4);
        headViewPager.setPageTransformer(true,new HeadViewPagerTransformer());
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.toolbar_right:
                intent = new Intent(this, ServiceComplaintsActivity.class);
                intent.putExtra("icon", bean.getBasicInfo().getLogo());
                intent.putExtra("name", bean.getBasicInfo().getName());
                intent.putExtra("LS_ID", getIntent().getStringExtra("LS_ID"));
                startActivity(intent);
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_agency_service_detail_chat:
                MySharedPreferences.getInstance(this).setString("Img", bean.getBasicInfo().getLogo());
                NotifyMsgCountUtil.notifyMsg(bean.getBasicInfo().getUserid() + "");
                intent = new Intent();
                intent.putExtra("one", bean.getBasicInfo().getUserid() + "");
                intent.putExtra("userName", bean.getBasicInfo().getName());
                intent.setClass(this, IntentChatActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_agency_service_detail_reservation:
                intent = new Intent(this, ServiceProjectListActivity.class);
                intent.putExtra("LS_ID", getIntent().getStringExtra("LS_ID"));
                startActivity(intent);
                break;
            case R.id.activity_agency_service_detail_call_phone:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "您还没有授予打电话权限", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!TextUtils.isEmpty(bean.getBasicInfo().getTel())) {
                            intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + bean.getBasicInfo().getTel());
                            intent.setData(data);
                            startActivity(intent);
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(bean.getBasicInfo().getTel())) {
                        intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + bean.getBasicInfo().getTel());
                        intent.setData(data);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.activity_agency_service_detail_comment_detail:
                intent = new Intent(this, ServiceCommentListActivity.class);
                intent.putExtra("LS_ID", getIntent().getStringExtra("LS_ID"));
                intent.putExtra("basicInfo", bean.getBasicInfo());
                startActivity(intent);
                break;
            case R.id.activity_agency_service_detail_project_detail:
                intent = new Intent(this, ServiceProjectListActivity.class);
                intent.putExtra("LS_ID", getIntent().getStringExtra("LS_ID"));
                startActivity(intent);
                break;
            case R.id.activity_agency_service_horizontal_left:
                if (headViewPager.getCurrentItem()>0) {
                    headViewPager.setCurrentItem(headViewPager.getCurrentItem()-1);
                }
                break;
            case R.id.activity_agency_service_horizontal_right:
                if (headViewPager.getCurrentItem()<imgList.size()-1) {
                    headViewPager.setCurrentItem(headViewPager.getCurrentItem()+1);
                }
                break;
        }
    }

    @Override
    public void click(View view,int position) {
        Intent intent = new Intent(AgencyServiceDetailActivity.this, ServiceProjectDetailActivity.class);
        intent.putExtra("id", bean.getServiceProject().get(position).getID() + "");
        startActivity(intent);
    }
}
