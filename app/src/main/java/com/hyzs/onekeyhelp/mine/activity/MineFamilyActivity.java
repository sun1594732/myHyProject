package com.hyzs.onekeyhelp.mine.activity;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;

import com.hyzs.onekeyhelp.mine.adapter.MineFamilyAdapter;

import com.hyzs.onekeyhelp.mine.bean.MineFamilyBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineFamilyActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, VRefresh.OnLoadListener {
    private ImageView mBack;
    private TextView mTitle;
    private ListView family_lv;
    private List<MineFamilyBean.PersonalCenterFamilyMemberBean> list;
    private Map<String, String> hashMap;
    private String familyUrl = PortUtil.AllFamilyUrl;
    private MineFamilyAdapter adapter;
    private Context mContext;
    private VRefresh family_vr;
    private int index=1;
    @Override
    protected void assignView() {
        list = new ArrayList<>();

        mContext = this;
        family_lv = (ListView) findViewById(R.id.activity_mine_family_lv);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);

        family_vr = (VRefresh) findViewById(R.id.family_vr);
        family_vr.setView(this,family_vr);
        family_vr.setOnRefreshListener(this);
        family_vr.setOnLoadListener(this);

    }

    @Override
    protected void initView() {
        mTitle.setText("我的家人");
        mBack.setOnClickListener(this);
        family_lv.setOnItemClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_family;
    }

    @Override
    protected void initData() {
        initData(1);
    }



    protected void initData(final int type) {
        if(!MySharedPreferences.isLogin(this)){
            Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
            return;
        }

        hashMap = new HashMap<>();
        hashMap.put("currId", MySharedPreferences.getUserId(this));
        hashMap.put("pageSize", "10");
        hashMap.put("pageIndex", index+"");
        NetRequest.CommonUseListRequestMy(familyUrl, hashMap, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    if(type==1) {
                        list=new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineFamilyBean.class).getPersonalCenterFamilyMember();
                        adapter = new MineFamilyAdapter(mContext, list);
                        family_lv.setAdapter(adapter);

                    }else{
                        if(new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineFamilyBean.class).getPersonalCenterFamilyMember().size()==0){
                            Toast.makeText(mContext, "没有更多数据了！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        list.addAll(new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineFamilyBean.class).getPersonalCenterFamilyMember());
                        adapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    Toast.makeText(mContext, "数据获取异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;
                }
                family_vr.setLoading(false);
                family_vr.setRefreshing(false);

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MineFamilyActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MineFamilyActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    @Override
    public void onRefresh() {
        index=1;
        initData(1);
    }

    @Override
    public void onLoadMore() {
        ++index;
        initData(2);
    }
}
