package com.hyzs.onekeyhelp.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.home.bean.NewsListBean;
import com.hyzs.onekeyhelp.widget.ListViewForScrollView;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.home.adapter.HomeNewsAdapter;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.news.NewsWebActivity;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.Map;

public class HomeNewsListViewFragment extends Fragment {
    private ListViewForScrollView mLv;
    NewsListBean bean;
    MySharedPreferences mySharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_lv, null);
        assignView(view);
        initView();
        initListener();
        return view;
    }

    private void initListener() {
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsWebActivity.class);
                intent.putExtra("newID", bean.getNewsListByClassify().get(position).getId() + "");
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mySharedPreferences = MySharedPreferences.getInstance(getActivity());
        Map<String, String> param = new ArrayMap<>();
        param.put("Classify", getArguments().getInt("type", 1) + "");
        param.put("pageSize", "5");
        param.put("pageIndex", "1");
        NetRequest.ParamPostRequest(PortUtil.HomeNewList, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), NewsListBean.class);
                mLv.setAdapter(new HomeNewsAdapter(bean.getNewsListByClassify(), getActivity()));
                //                获取主页第一个新闻Fragment的item总长度并设置给HomeFragment
                switch (getArguments().getInt("vpPosition", 0)) {
                    case 0:
                        saveHeight("HomeNew1", true);
                        break;
                    case 1:
                        saveHeight("HomeNew2", true);
                        break;
                    case 2:
                        saveHeight("HomeNew3", true);
                        break;
                }
            }
        });
    }

    private void saveHeight(String key, boolean isFirst) {
        if (mLv.getAdapter().getCount() == 0) {
            mySharedPreferences.setInt(key, 10);
        } else {
            View view = mLv.getAdapter().getView(0, null, mLv);
            view.measure(0, 0);
            int height = mLv.getAdapter().getCount() * view.getMeasuredHeight();
//            if (isFirst) {
//                HomeFragment.setFirstNewViewPager(height);
//            }
            mySharedPreferences.setInt(key, height);
        }
    }

    private void assignView(View view) {
        mLv = (ListViewForScrollView) view.findViewById(R.id.fragmeng_home_lvForScroll);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("HomeNewsListViewFragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HomeNewsListViewFragment");
    }
}
