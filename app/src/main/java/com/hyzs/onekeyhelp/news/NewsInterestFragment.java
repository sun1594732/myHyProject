package com.hyzs.onekeyhelp.news;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;


import com.hyzs.onekeyhelp.home.bean.NewsListBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;

import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;


import java.util.HashMap;



public class NewsInterestFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, VRefresh.OnLoadListener {
    private ListView mLv;
    private HashMap hms;
    private Dialog loadDialog;
    private VRefresh news_vr;
    private NewsListBean newsBean;
    private NewAdapter newAdapter;
    private int index = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle_interest, null);
        loadDialog = ProgressDialog.createProgressLoading(getActivity());
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
        loadDialog.show();
        hms = new HashMap<>();
        hms.put("pageSize", "8");
        hms.put("pageIndex", index + "");
        hms.put("Classify", getArguments().getInt("type") + "");
        NetRequest.ParamDialogPostRequest(PortUtil.HomeNewList, hms, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    if (type == 1) {
                        newsBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), NewsListBean.class);
                        newAdapter = new NewAdapter(NewsInterestFragment.this.getContext(), newsBean);
                        index++;
                        mLv.setAdapter(newAdapter);
                    } else {
                        if (new Gson().fromJson(UrlDecodeUtil.urlCode(data), NewsListBean.class).getNewsListByClassify().size() == 0) {
                            Toast.makeText(NewsInterestFragment.this.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                            news_vr.setLoading(false);
                            return;
                        }
                        newsBean.getNewsListByClassify().addAll(new Gson().fromJson(UrlDecodeUtil.urlCode(data), NewsListBean.class).getNewsListByClassify());
                        index++;
                        newAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(NewsInterestFragment.this.getActivity(), "数据获取异常，请稍后重试", Toast.LENGTH_SHORT).show();
                } finally {
                    loadDialog.dismiss();
                }
                news_vr.setLoading(false);
                news_vr.setRefreshing(false);
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                loadDialog.dismiss();
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
        Intent intent = new Intent(NewsInterestFragment.this.getContext(), NewsWebActivity.class);
        intent.putExtra("newID", newsBean.getNewsListByClassify().get(position).getId() + "");
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
        initData(2);
    }
}