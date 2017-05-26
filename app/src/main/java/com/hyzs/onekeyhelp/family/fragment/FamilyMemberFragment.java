package com.hyzs.onekeyhelp.family.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.adapter.FamilyAddMemberAdapter;
import com.hyzs.onekeyhelp.family.adapter.FamilyMemberAdapter;
import com.hyzs.onekeyhelp.family.bean.FamilyAddMemberBean;
import com.hyzs.onekeyhelp.family.bean.FamilyMemberBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 家庭成员
 */
public class FamilyMemberFragment extends Fragment implements VRefresh.OnLoadListener, VRefresh.OnRefreshListener {
    private static final String TAG = "FamilyMemberFragment";
    private Context mContext;
    private ListView listView, addListView;
    private FamilyMemberAdapter adapter;
    private Dialog dialog;
    private int index = 1;
    private int add_index = 1;
    private boolean isRefresh = true;
    private boolean add_isRefresh = true;
    private VRefresh refresh, add_refresh;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://添加成员
                    add_index = 1;
                    add_isRefresh = true;
                    initAddListData();
                    break;
                case 2://删除成员
                    adapter.showCheck(true);
                    delPosition = -1;
                    break;
                case 3://左边的按钮

                    if (msg.obj.toString().equals("拒绝")) {
                        dialog.show();
                        isRefresh = true;
                        index = 1;
                        initData();
                    } else {
                        delMember();
                    }
                    break;
                case 4://右边的按钮
                    if (msg.obj.toString().equals("拒绝")) {
                        for (FamilyMemberBean.FamilyMemberListModelBean b : list) {
                            b.setCheck(0);
                        }
                        adapter.showCheck(false);
                    } else {
                        addMember();
                    }
                    break;
            }
        }
    };
    public int delPosition = -1;

    private void delMember() {
        if (delPosition == -1) {
            Toast.makeText(mContext, "请选择要删除的成员", Toast.LENGTH_SHORT).show();
            return;
        }
        adapter.showCheck(false);
        dialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(mContext));
        map.put("targetFamilyID", getActivity().getIntent().getStringExtra("familyId"));
        map.put("hxGroupId", getActivity().getIntent().getStringExtra("groupId"));
        map.put("userid", list.get(delPosition).getUserid() + "");
        NetRequest.ParamPostRequest(PortUtil.FamilyMemberDel, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if (jsonObject.getString("code").equals("10000")) {
                        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                        list.remove(delPosition);
                        adapter.showCheck(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                }
            }
        });
    }

    private FamilyAddMemberAdapter addAdapter;

    private void addMember() {
//        StringBuilder sb = new StringBuilder();
//        for (FamilyAddMemberBean.FamilySearchAddListBean b : addBean.getFamilySearchAddList()) {
//            if (b.isCheck() == 1)
//                sb.append(b.getUserid()).append(",");
//        }
//        if (sb.length() == 0) {
//            Toast.makeText(mContext, "请选择要添加的成员", Toast.LENGTH_SHORT).show();
//            return;
//        }
        adapter.showCheck(false);
        dialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(mContext));
        map.put("targetFamilyID", getActivity().getIntent().getStringExtra("familyId"));
        map.put("hxGroupId", getActivity().getIntent().getStringExtra("groupId"));
        map.put("userids", FamilyAddMemberAdapter.getProject());
        NetRequest.ParamPostRequest(PortUtil.FamilyMemberAdd, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if (jsonObject.getString("code").equals("10000")) {
                        Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                        isRefresh = true;
                        index = 1;
                        initData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FamilyAddMemberBean addBean;

    private void initAddListData() {
        dialog.show();
        Map<String, String> map = new ArrayMap<>();
        map.put("currId", MySharedPreferences.getUserId(getActivity()));
        map.put("targetFamilyID", getActivity().getIntent().getStringExtra("familyId"));
        map.put("pageSize", "99");
        map.put("pageIndex", add_index + "");
        NetRequest.ParamPostRequest(PortUtil.FamilySearchAddList, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    initAddListView(data);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                    add_refresh.setLoading(false);
                    add_refresh.setRefreshing(false);
                }
            }
        });
    }

    private List<FamilyAddMemberBean.FamilySearchAddListBean> add_list;

    private void initAddListView(String data) {
        addBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), FamilyAddMemberBean.class);
        add_list.clear();
        add_list.addAll(addBean.getFamilySearchAddList());
        addAdapter = new FamilyAddMemberAdapter(mContext, add_list);
        addListView.setAdapter(addAdapter);
        add_refresh.setVisibility(View.VISIBLE);
    }

    private FamilyMemberBean bean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_member, null);
        mContext = getActivity();
        assignView(view);
        initView();
        initListener();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            index = 1;
            isRefresh = true;
            initData();
        }
    }

    private void initData() {
        if (adapter != null)
            adapter.showCheck(false);
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(getActivity()));
        map.put("targetFamilyID", getActivity().getIntent().getStringExtra("familyId"));
        map.put("pageSize", "99");
        map.put("pageIndex", index + "");
        NetRequest.ParamPostRequest(PortUtil.FamilyMemberList, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), FamilyMemberBean.class);
                    initListView();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    refresh.setLoading(false);
                    refresh.setRefreshing(false);
                    dialog.dismiss();
                }
            }
        });
    }

    private List<FamilyMemberBean.FamilyMemberListModelBean> list = new ArrayList<>();

    private void initListView() {
        add_refresh.setVisibility(View.GONE);
        if (isRefresh) {
            list.clear();
            list.addAll(bean.getFamilyMemberListModel());
        } else {
            list.addAll(bean.getFamilyMemberListModel());
        }
        if (listView == null || adapter == null) {
            adapter = new FamilyMemberAdapter(mContext, list);
            listView.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FamilyMemberAdapter.MyHolder holder = (FamilyMemberAdapter.MyHolder) view.getTag();
                if (list.get(position).isCheck() == 1) {
                    holder.check.setImageResource(R.mipmap.add_community_unchecked);
                    list.get(position).setCheck(0);
                    delPosition = -1;
                } else {
                    for (FamilyMemberBean.FamilyMemberListModelBean b : list) {
                        b.setCheck(0);
                    }
                    holder.check.setImageResource(R.mipmap.add_community_checked);
                    list.get(position).setCheck(1);
                    delPosition = position;
                }
                adapter.notifyDataSetChanged();
            }
        });
        addListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FamilyAddMemberAdapter.MyHolder holder = (FamilyAddMemberAdapter.MyHolder) view.getTag();
//                if (addBean.getFamilySearchAddList().get(position).isCheck() == 1) {
//                    holder.check.setImageResource(R.mipmap.add_community_unchecked);
//                    holder.isCheck = false;
//                    addBean.getFamilySearchAddList().get(position).setCheck(0);
//                } else {
//                    holder.check.setImageResource(R.mipmap.add_community_checked);
//                    addBean.getFamilySearchAddList().get(position).setCheck(1);
//                    holder.isCheck = true;
//                }
            }
        });
        refresh.setOnLoadListener(this);
        refresh.setOnRefreshListener(this);
        add_refresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
//                add_index++;
//                add_isRefresh = false;
//                initAddListData();
                add_refresh.setLoading(false);
            }
        });
        add_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                add_index = 1;
//                add_isRefresh = true;
                initAddListData();
                add_refresh.setRefreshing(false);
            }
        });
    }

    private void initView() {
        add_list = new ArrayList<>();
    }

    private void assignView(View view) {
        listView = (ListView) view.findViewById(R.id.fragment_family_member_list);
        addListView = (ListView) view.findViewById(R.id.fragment_family_add_member_list);
        dialog = ProgressDialog.createProgressLoading(mContext);
        refresh = (VRefresh) view.findViewById(R.id.vRefresh);
        refresh.setView(mContext, listView);
        add_refresh = (VRefresh) view.findViewById(R.id.vRefresh_add);
        add_refresh.setView(mContext, addListView);
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
        refresh.setLoading(false);
        add_refresh.setLoading(false);
//        initData();
    }
}
