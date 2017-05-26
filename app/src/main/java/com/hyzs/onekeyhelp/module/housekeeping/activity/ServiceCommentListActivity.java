package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.ServiceCommentListAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.bean.AgencyServiceDetailBean;
import com.hyzs.onekeyhelp.module.housekeeping.bean.MineServiceDetailBean;
import com.hyzs.onekeyhelp.module.housekeeping.bean.ServiceCommentListBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.hyzs.onekeyhelp.widget.view.VRefresh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceCommentListActivity extends BaseActivity implements View.OnClickListener, VRefresh.OnLoadListener, VRefresh.OnRefreshListener {
    private static final String TAG = "ServiceCommentDetailAct";
    private ImageView back;
    private View agency, mine;
    private RoundImageView agencyIcon, mineIcon;
    private TextView title, agencyName, agencyDistance, agencyContent, agencyPhone, agencyAddress, mineName, mine_native_place, mineCharges, mineRange, mineTag1, mineTag2, mineTag3;
    private ListView listView;
    private int index = 1;
    private boolean isRefresh = true;
    private ServiceCommentListBean bean;
    private List<ServiceCommentListBean.LifeServiceCommentListBean> list = new ArrayList<>();
    private ServiceCommentListAdapter adapter;
    private VRefresh refresh;
    private AgencyServiceDetailBean.BasicInfoBean basicInfo;
    private MineServiceDetailBean.PersonalInfoBean personalInfo;
    private RatingBar ratingBar;

    @Override
    protected void assignView() {
        agency = findViewById(R.id.activity_service_comment_detail_agency);
        mine = findViewById(R.id.activity_service_comment_detail_mine);
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        listView = (ListView) findViewById(R.id.activity_service_comment_detail_list);
        refresh = (VRefresh) findViewById(R.id.activity_service_comment_detail_refresh);
        refresh.setView(this, listView);
        findViewById(R.id.item_agency_service_layout_call_phone).setVisibility(View.GONE);
        findViewById(R.id.item_agency_service_layout_chat).setVisibility(View.GONE);
        agencyIcon = (RoundImageView) findViewById(R.id.item_agency_service_layout_icon);
        agencyName = (TextView) findViewById(R.id.item_agency_service_layout_name);
        agencyDistance = (TextView) findViewById(R.id.item_agency_service_layout_distance);
        agencyContent = (TextView) findViewById(R.id.item_agency_service_layout_content);
        agencyPhone = (TextView) findViewById(R.id.item_agency_service_layout_phone);
        agencyAddress = (TextView) findViewById(R.id.item_agency_service_layout_address);
        ratingBar = (RatingBar) findViewById(R.id.item_mine_service_ratingbar);
        findViewById(R.id.item_mine_service_layout_call_phone).setVisibility(View.GONE);
        findViewById(R.id.item_mine_service_layout_chat).setVisibility(View.GONE);
        mineIcon = (RoundImageView) findViewById(R.id.item_mine_service_icon);
        mineName = (TextView) findViewById(R.id.item_mine_service_name);
        mine_native_place = (TextView) findViewById(R.id.item_mine_service_native_place);
        mineCharges = (TextView) findViewById(R.id.item_mine_service_charges);
        mineRange = (TextView) findViewById(R.id.item_mine_service_range);
        mineTag1 = (TextView) findViewById(R.id.item_mine_service_tag1);
        mineTag2 = (TextView) findViewById(R.id.item_mine_service_tag2);
        mineTag3 = (TextView) findViewById(R.id.item_mine_service_tag3);
    }

    @Override
    protected void initView() {
        title.setText("用户评价");
        if (getIntent().getSerializableExtra("basicInfo") != null) {
            basicInfo = (AgencyServiceDetailBean.BasicInfoBean) getIntent().getSerializableExtra("basicInfo");
            initAgencyInfo();
        } else {
            personalInfo = (MineServiceDetailBean.PersonalInfoBean) getIntent().getSerializableExtra("personalInfo");
            initMineInfo();
        }
        adapter = new ServiceCommentListAdapter(this, list);
        listView.setAdapter(adapter);
    }

    private void initMineInfo() {
        if (TextUtils.isEmpty(personalInfo.getFace())) {
            mineIcon.setImageResource(R.drawable.icon_replace);
        } else NetRequest.loadImg(this, personalInfo.getFace(), mineIcon);
        mineName.setText(personalInfo.getName());
        mineCharges.setText(personalInfo.getMsalary() + "元/月");
        mine_native_place.setText(personalInfo.getPSIA_NativePlace());
        mineRange.setText(personalInfo.getPSIA_ServiceRangeDesc());
        ratingBar.setIsIndicator(true);
        ratingBar.setNumStars(personalInfo.getRecommendIndex());
        if (!TextUtils.isEmpty(personalInfo.getTag())) {
            String[] tags = personalInfo.getTag().split(",");
            mineTag1.setText(tags[0]);
            mineTag2.setText(tags[1]);
            mineTag3.setText(tags[2]);
        }
        mine.setVisibility(View.VISIBLE);
    }

    private void initAgencyInfo() {
        if (TextUtils.isEmpty(basicInfo.getLogo())) {
            agencyIcon.setImageResource(R.drawable.icon_replace);
        } else NetRequest.loadImg(this, basicInfo.getLogo(), agencyIcon);
        agencyName.setText(basicInfo.getName());
        agencyDistance.setText(basicInfo.getDistance());
        agencyAddress.setText(basicInfo.getAddress());
        agencyPhone.setText(basicInfo.getTel());
        agencyContent.setText(basicInfo.getIntro());
        agency.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        refresh.setOnLoadListener(this);
        refresh.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_comment_detail;
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("LS_ID", getIntent().getStringExtra("LS_ID"));
        map.put("pageSize", "10");
        map.put("pageIndex", index + "");
        NetRequest.ParamPostRequest(PortUtil.LifeServiceCommentList, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    Log.e(TAG, "getData: " + UrlDecodeUtil.urlCode(data));
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ServiceCommentListBean.class);
                    initListView();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    refresh.setLoading(false);
                    refresh.setRefreshing(false);
                }
            }
        });
    }

    private void initListView() {
        if (isRefresh) {
            list.clear();
            list.addAll(bean.getLifeServiceCommentList());
        } else list.addAll(bean.getLifeServiceCommentList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;

        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        index = 1;
        initData();
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        index++;
        initData();
    }
}
