package com.hyzs.onekeyhelp.family;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.activity.LoginActivity;
import com.hyzs.onekeyhelp.family.activity.CreateGroupActivity;
import com.hyzs.onekeyhelp.family.activity.FamilyListActivity;
import com.hyzs.onekeyhelp.family.adapter.FamilyLvAdapter;
import com.hyzs.onekeyhelp.family.bean.FamilyListBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

public class FamilyFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = "FamilyFragment";
    private ImageView mBack;
    private TextView mTitle, mRightBtn;
    private ListView mLv;
    private FamilyLvAdapter adapter;
    private RelativeLayout mEmptyView;
    private ImageView mEmptyBtn;
    private FamilyListBean bean;
    private VRefresh refresh;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, null);
        assignView(view);
        initView();
        initListener();
        return view;
    }

    private void initData() {
        if (!MySharedPreferences.isLogin(getActivity())) {
            refresh.setRefreshing(false);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(getActivity()));
        NetRequest.ParamPostRequest(PortUtil.HXGroupsList, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), FamilyListBean.class);
                    initListView();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    refresh.setRefreshing(false);
                }
            }
        });
    }

    private void initListView() {
        adapter = new FamilyLvAdapter(getActivity(),bean.getUsers());
        mLv.setAdapter(adapter);
    }

    private void initView() {
        mTitle.setText("家庭");
        mRightBtn.setText("创建家庭");
        mBack.setVisibility(View.INVISIBLE);
        mRightBtn.setVisibility(View.VISIBLE);
    }

    private void initListener() {
        mLv.setOnItemClickListener(this);
        mEmptyBtn.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    private void assignView(View view) {
        mTitle = (TextView) view.findViewById(R.id.toolbar_title);
        mBack = (ImageView) view.findViewById(R.id.toolbar_back);
        mLv = (ListView) view.findViewById(R.id.fragment_family_lv);
        mRightBtn = (TextView) view.findViewById(R.id.toolbar_right);
        mEmptyView = (RelativeLayout) view.findViewById(R.id.layout_empty);
        mEmptyBtn = (ImageView) view.findViewById(R.id.layout_empty_login);
        refresh = (VRefresh) view.findViewById(R.id.vrefresh);
        refresh.setView(getActivity(),mLv);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_empty_login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.toolbar_right:
                startActivityForResult(new Intent(getActivity(), CreateGroupActivity.class),100);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == 1) {
                initData();
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (MySharedPreferences.isLogin(getActivity()))
            mEmptyView.setVisibility(View.GONE);
        else mEmptyView.setVisibility(View.VISIBLE);
        initData();
        MobclickAgent.onPageStart("Cmmunity" + "Fragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Cmmunity" + "Fragment");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), FamilyListActivity.class);
        intent.putExtra("familyId", bean.getUsers().get(position).getFG_ID()+"");
        intent.putExtra("userId", bean.getUsers().get(position).getFG_UserID()+"");
        intent.putExtra("groupId", bean.getUsers().get(position).getFG_GroupID()+"");
        startActivity(intent);
    }
}

