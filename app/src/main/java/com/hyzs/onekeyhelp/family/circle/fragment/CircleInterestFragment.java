package com.hyzs.onekeyhelp.family.circle.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.circle.activity.CircleDetailActivity;
import com.hyzs.onekeyhelp.family.circle.adapter.CircleInterestAdapter;
import com.hyzs.onekeyhelp.family.circle.bean.CircleFragmentBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class CircleInterestFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mLv;
    private CircleInterestAdapter adapter;
    private List<CircleFragmentBean.CircleListsBean> list;
    private VRefresh vRefresh;
    private Dialog mCheckedDialog, mProgressDialog;
    String circleType = null;
    int page = 2, position = 0;
    String uid = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle_interest, null);
        assignView(view);
        initView();
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        mLv.setOnItemClickListener(this);
        vRefresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                NetRequest.CircleListRequest(uid, circleType, MySharedPreferences.getCommunityId(getActivity()), page + "", new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        try {
                            if (new Gson().fromJson(UrlDecodeUtil.urlCode(data), CircleFragmentBean.class).getCircleLists().size() == 0) {
                                ToastUtils.showShort(getActivity(), "无更多内容");
                                vRefresh.setLoading(false);
                                return;
                            }
                            list.addAll(new Gson().fromJson(UrlDecodeUtil.urlCode(data), CircleFragmentBean.class).getCircleLists());
                            adapter.notifyDataSetChanged();
                            page++;
                            vRefresh.setLoading(false);
                        } catch (Exception e) {
                        }
                    }
                });
            }
        });
        vRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NetRequest.CircleListRequest(uid, circleType, MySharedPreferences.getCommunityId(getActivity()), "1", new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        try {
                            list.clear();
                            list.addAll(new Gson().fromJson(UrlDecodeUtil.urlCode(data), CircleFragmentBean.class).getCircleLists());
                            page = 2;
                            adapter.notifyDataSetChanged();
                            vRefresh.setRefreshing(false);
                        } catch (Exception e) {
//                            ToastUtils.showShort(getActivity(), "网络异常");
                        }
                    }
                });
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        circleType = getArguments().getInt("type") + "";
        if (!MySharedPreferences.isLogin(getActivity())) {
            uid = "0";
        } else uid = MySharedPreferences.getInstance(getActivity()).getString("uid");
        NetRequest.CircleListRequest(uid, circleType, MySharedPreferences.getCommunityId(getActivity()), "1", new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    CircleFragmentBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CircleFragmentBean.class);
                    list.addAll(bean.getCircleLists());
                    adapter = new CircleInterestAdapter(getActivity(), list);
                    mLv.setAdapter(adapter);
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Circle" + "Fragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    private void initView() {
    }

    private void assignView(View view) {
        mLv = (ListView) view.findViewById(R.id.fragment_circle_interest_lv);
        vRefresh = (VRefresh) view.findViewById(R.id.fragment_circle_interest_refresh);
        vRefresh.setView(getActivity(), mLv);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
        Intent intent = new Intent(getActivity(), CircleDetailActivity.class);
        intent.putExtra("id", list.get(position).getID() + "");
        intent.putExtra("type", TextUtils.isEmpty(list.get(position).getCircle_SPURL()) ? 0 : 1);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            list.get(position).setPointPraiseCount(Integer.parseInt(data.getStringExtra("praise")));
            list.get(position).setCommentCount(Integer.parseInt(data.getStringExtra("comment")));
            adapter.notifyDataSetChanged();
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Circle" + "Fragment");
    }

}
