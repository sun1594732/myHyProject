package com.hyzs.onekeyhelp.home.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;


import com.hyzs.onekeyhelp.home.adapter.CommunityNoticeAdapter;
import com.hyzs.onekeyhelp.home.adapter.HomePostLvAdapter;
import com.hyzs.onekeyhelp.home.bean.CommunityNoticeBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.news.NewsWebActivity;

import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommunityNoticeFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, VRefresh.OnLoadListener {
    private ListView mLv;
    private Dialog loadDialog;
    private VRefresh news_vr;
    private CommunityNoticeBean newsBean;
    private CommunityNoticeAdapter newAdapter;
    private int index = 1;
    private Map<String, String> param;
    private Map<String, String> hms;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle_interest, null);
        loadDialog = ProgressDialog.createProgressLoading(this.getActivity());
        loadDialog.show();
        assignView(view);
        initView();
        initData(1);
        initListener();
        return view;
    }

    private void initListener() {
        mLv.setOnItemClickListener(this);
    }

    private void initData(final int type) {
        hms = new ArrayMap<>();
        param = new ArrayMap<String, String>();
        hms.put("classify_id", getArguments().getInt("type") + "");
        hms.put("communtityId", MySharedPreferences.getCommunityId(getActivity()));
        hms.put("pageIndex", index+"");
        hms.put("pageSize", "10");
        NetRequest.ParamPostRequest(PortUtil.CommunityNoticeList, hms, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    if (type == 1) {
                        newsBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CommunityNoticeBean.class);
                        List<Integer> bgs = new ArrayList<>();
                        if (newsBean.getCommunityNoticeList().size()!=0) {
                            int bgArray[] = {R.mipmap.sy_gonggao1, R.mipmap.sy_gonggao2, R.mipmap.sy_gonggao3, R.mipmap.sy_gonggao4, R.mipmap.sy_gonggao5};
                            for (int i = 0; i < newsBean.getCommunityNoticeList().size(); i++) {
                                bgs.add(bgArray[i]);
                            }
                        }
                        newAdapter = new CommunityNoticeAdapter(newsBean.getCommunityNoticeList(), getActivity(),bgs);
                        mLv.setAdapter(newAdapter);
                    } else {
                        if (new Gson().fromJson(UrlDecodeUtil.urlCode(data), CommunityNoticeBean.class).getCommunityNoticeList().size() == 0) {
                            Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                            news_vr.setLoading(false);
                            return;
                        }
                        newsBean.getCommunityNoticeList().addAll(new Gson().fromJson(data, CommunityNoticeBean.class).getCommunityNoticeList());
                        newAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "数据获取异常，请稍后重试", Toast.LENGTH_SHORT).show();
                } finally {
                    loadDialog.dismiss();
                }
                news_vr.setLoading(false);
                news_vr.setRefreshing(false);
            }
        });
    }

    private void initView() {
    }

    private void assignView(View view) {
        mLv = (ListView) view.findViewById(R.id.fragment_circle_interest_lv);
        news_vr = (VRefresh) view.findViewById(R.id.fragment_circle_interest_refresh);
        mLv.setScrollContainer(false);
        news_vr.setView(this.getContext(), mLv);
        news_vr.setOnRefreshListener(this);
        news_vr.setOnLoadListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        param.put("currId", MySharedPreferences.getUserId(getActivity()));
        param.put("CR_ToID", newsBean.getCommunityNoticeList().get(position).getId() + "");
        param.put("targetType", "0");
        NetRequest.ParamPostRequest(PortUtil.UserStatistics, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {

            }
        });
        Intent intent = new Intent(getActivity(), NewsWebActivity.class);
        intent.putExtra("communityNoticeID", newsBean.getCommunityNoticeList().get(position).getId() + "");
        startActivity(intent);
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("NewsInterestFragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("NewsInterestFragment");
    }


    @Override
    public void onRefresh() {
        index = 1;
        initData(1);
    }

    @Override
    public void onLoadMore() {
        ++index;
        initData(2);
    }
}