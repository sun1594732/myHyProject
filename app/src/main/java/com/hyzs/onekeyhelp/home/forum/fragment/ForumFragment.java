package com.hyzs.onekeyhelp.home.forum.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.circle.activity.CircleDetailActivity;
import com.hyzs.onekeyhelp.home.forum.activity.ForumDetailActivity;
import com.hyzs.onekeyhelp.home.forum.adapter.ForumFragmentAdapter;
import com.hyzs.onekeyhelp.home.forum.bean.ForumFragmentBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest.ParamPostRequest;


public class ForumFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mLv;
    private ForumFragmentAdapter adapter;
    private List<ForumFragmentBean.ForumListsBean> list;
    private Dialog mCheckedDialog;
    String uid = null;
    private VRefresh vRefresh;
    int page = 2 ,position = 0;

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
                Map<String, String> param = new HashMap<>();
                param.put("currId", uid);
                param.put("forum_type", getArguments().getInt("type") + "");
                param.put("Community_id", MySharedPreferences.getCommunityId(getActivity()));
                param.put("pageSize", "10");
                param.put("pageIndex", page + "");
                NetRequest.ParamPostRequest(PortUtil.ForumList, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        try {
                            if (new Gson().fromJson(UrlDecodeUtil.urlCode(data), ForumFragmentBean.class).getForumLists().size() == 0) {
                                ToastUtils.showShort(getActivity(), "无更多内容");
                                return;
                            }
                            list.addAll(new Gson().fromJson(UrlDecodeUtil.urlCode(data), ForumFragmentBean.class).getForumLists());
                            adapter.notifyDataSetChanged();
                            page++;
                            vRefresh.setLoading(false);
                        } catch (Exception e) {
                            ToastUtils.showShort(getActivity(), "网络异常");
                        }
                    }
                });
            }
        });
        vRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Map<String, String> param = new HashMap<>();
                param.put("currId", uid);
                param.put("forum_type", getArguments().getInt("type") + "");
                param.put("Community_id", MySharedPreferences.getCommunityId(getActivity()));
                param.put("pageSize", "10");
                param.put("pageIndex", "1");
                NetRequest.ParamPostRequest(PortUtil.ForumList, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        try {
                            list.clear();
                            list.addAll(new Gson().fromJson(UrlDecodeUtil.urlCode(data), ForumFragmentBean.class).getForumLists());
                            page = 2;
                            adapter.notifyDataSetChanged();
                            vRefresh.setRefreshing(false);
                        } catch (Exception e) {
                            ToastUtils.showShort(getActivity(), "网络异常");
                        }
                    }
                });
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        if (!MySharedPreferences.isLogin(getActivity())) {
            uid = "0";
        } else uid = MySharedPreferences.getUserId(getActivity());
        Map<String, String> param = new HashMap<>();
        param.put("currId", uid);
        param.put("forum_type", getArguments().getInt("type") + "");
        param.put("Community_id", MySharedPreferences.getCommunityId(getActivity()));
        param.put("pageSize", "10");
        param.put("pageIndex", "1");
        ParamPostRequest(PortUtil.ForumList, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                list.addAll(new Gson().fromJson(UrlDecodeUtil.urlCode(data), ForumFragmentBean.class).getForumLists());
                adapter = new ForumFragmentAdapter(getActivity(), list);
                mLv.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ForumFragment");
        Map<String, String> param = new HashMap<>();
        param.put("currId", uid);
        param.put("forum_type", getArguments().getInt("type") + "");
        param.put("Community_id", MySharedPreferences.getCommunityId(getActivity()));
        param.put("pageSize", "10");
        param.put("pageIndex", "1");
        ParamPostRequest(PortUtil.ForumList, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                if (list != null && list.size() != 0) {
                    list.clear();
                    list.addAll(new Gson().fromJson(UrlDecodeUtil.urlCode(data), ForumFragmentBean.class).getForumLists());
                    adapter.notifyDataSetChanged();
                    page = 2;
                }
            }
        });
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
        if (!MySharedPreferences.isLogin(getActivity())) {
            ToastUtils.showShort(getActivity(),"请先登录");
            return;
        }
        this.position = position;
        Intent intent = new Intent(getActivity(), ForumDetailActivity.class);
        intent.putExtra("id", list.get(position).getForum_ID() + "");
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            list.get(position).setCommentCount(Integer.parseInt(data.getStringExtra("comment")));
            adapter.notifyDataSetChanged();
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ForumFragment");
    }
}
