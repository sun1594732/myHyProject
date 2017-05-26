package com.hyzs.onekeyhelp.family.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.adapter.FamilyCircleAdapter;
import com.hyzs.onekeyhelp.family.bean.FamilyCircleBean;
import com.hyzs.onekeyhelp.family.circle.activity.CircleDetailActivity;
import com.hyzs.onekeyhelp.family.movable.activity.EventRegistActivity;
import com.hyzs.onekeyhelp.home.forum.activity.ForumDetailActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * 家庭动态
 */
public class FamilyCircleFragment extends Fragment implements AdapterView.OnItemClickListener, VRefresh.OnLoadListener, VRefresh.OnRefreshListener {
    private static final String TAG = "FamilyCircleFragment";
    private Context mContext;
    private ListView listView;
    private FamilyCircleBean bean;
    private FamilyCircleAdapter adapter;
    private VRefresh refresh;
    private int index = 0;
    private boolean isLoad = false;
    private int position = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_circle, null);
        mContext = getActivity();
        index = 1;
        isLoad = false;
        assignView(view);
        initData();
        initView();
        initListener();
        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(this);
        refresh.setOnRefreshListener(this);
        refresh.setOnLoadListener(this);
    }

    private void initView() {
        adapter = new FamilyCircleAdapter(mContext, list);
        listView.setAdapter(adapter);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(mContext));
        map.put("targetFamilyID", getActivity().getIntent().getStringExtra("familyId"));
        map.put("pageSize", "10");
        map.put("pageIndex", index + "");
        NetRequest.ParamPostRequest(PortUtil.FamilyDynamicList, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), FamilyCircleBean.class);
                initListView();
            }
        });
    }

    private List<FamilyCircleBean.FamilyDynamicListBean> list = new ArrayList<>();

    private void initListView() {
        refresh.setLoading(false);
        refresh.setRefreshing(false);
        if (isLoad) {
            list.addAll(bean.getFamilyDynamicList());
        } else {
            list.clear();
            list.addAll(bean.getFamilyDynamicList());
        }
        isLoad = false;
        adapter.notifyDataSetChanged();
    }

    private void assignView(View view) {
        listView = (ListView) view.findViewById(R.id.fragment_family_circle_list);
        refresh = (VRefresh) view.findViewById(R.id.vrefresh);
        refresh.setView(mContext, listView);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FamilyCircleBean.FamilyDynamicListBean familyDynamicListBean = list.get(position);
        this.position = position;
        Intent intent = null;
        switch (familyDynamicListBean.getR_type()) {
            case 0:
                intent = new Intent(getActivity(), CircleDetailActivity.class);
                intent.putExtra("id", familyDynamicListBean.getRecord_id() + "");
                break;
            case 1:
                intent = new Intent(getActivity(), ForumDetailActivity.class);
                intent.putExtra("id", familyDynamicListBean.getRecord_id() + "");
                break;
            case 2:
                intent = new Intent(getActivity(), EventRegistActivity.class);
                intent.putExtra("title", familyDynamicListBean.getR_c_typeName());
                intent.putExtra("movableId", familyDynamicListBean.getRecord_id());
                break;
        }
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (!TextUtils.isEmpty(data.getStringExtra("comment"))) {
                list.get(position).setCommentCount(Integer.parseInt(data.getStringExtra("comment")));
            }
            if (!TextUtils.isEmpty(data.getStringExtra("praise"))) {
                list.get(position).setPraise(Integer.parseInt(data.getStringExtra("praise")));
            }
            adapter.notifyDataSetChanged();
        }
        position = -1;
    }

    @Override
    public void onRefresh() {
        index = 1;
        initData();
    }

    @Override
    public void onLoadMore() {
        index++;
        isLoad = true;
        initData();
    }
}
