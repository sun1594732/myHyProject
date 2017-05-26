package com.hyzs.onekeyhelp.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.widget.ListViewForScrollView;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.movable.activity.EventRegistActivity;
import com.hyzs.onekeyhelp.family.movable.bean.MovableListBean;
import com.hyzs.onekeyhelp.home.adapter.HomeActiveAdapter;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;


public class HomeActiveListViewFragment extends Fragment {
    private ListViewForScrollView mLv;
    MovableListBean bean;
    MySharedPreferences mySharedPreferences;
    PopupWindow pop;

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
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mLv, Gravity.CENTER,0,0);
                    return;
                }
                Intent intent = new Intent(getActivity(), EventRegistActivity.class);
                intent.putExtra("title", bean.getEventRegistrationList().get(position).getER_Title());
                intent.putExtra("movableId", bean.getEventRegistrationList().get(position).getID());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        pop = HelpDialog.createDialogNoAlert(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        },"您还未登录",0);
        mySharedPreferences = MySharedPreferences.getInstance(getActivity());
        Map<String, String> param = new HashMap<>();
        param.put("currId", MySharedPreferences.isLogin(getActivity()) ? MySharedPreferences.getUserId(getActivity()) : "0");
        param.put("event_type", getArguments().getInt("type", 1) + "");
        param.put("Community_id", MySharedPreferences.getCommunityId(getActivity()));
        param.put("pageSize", "5");
        param.put("pageIndex", "1");
        NetRequest.ParamPostRequest(PortUtil.HomeActiveList, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MovableListBean.class);
                mLv.setAdapter(new HomeActiveAdapter(bean.getEventRegistrationList(), getActivity()));
//                获取主页第一个活动Fragment的item总长度并设置给HomeFragment
                switch (getArguments().getInt("vpPosition", 0)) {
                    case 0:
                        saveHeight("HomeActive1",true);
                        break;
                    case 1:
                        saveHeight("HomeActive2",false);
                        break;
                    case 2:
                        saveHeight("HomeActive3",false);
                        break;
                }
            }
        });
    }

    private void saveHeight(String key ,boolean isFirst) {
        if (mLv.getAdapter().getCount() == 0) {
            mySharedPreferences.setInt(key, 10);
        } else {
            View view = mLv.getAdapter().getView(0, null, mLv);
            view.measure(0, 0);
            int height = mLv.getAdapter().getCount() * view.getMeasuredHeight();
//            if (isFirst) {
//                HomeFragment.setFirstActiveViewPager(height);
//            }
            mySharedPreferences.setInt(key, height);
        }
    }

    private void assignView(View view) {
        mLv = (ListViewForScrollView) view.findViewById(R.id.fragmeng_home_lvForScroll);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("HomeActiveListViewFragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HomeActiveListViewFragment");
    }

}
