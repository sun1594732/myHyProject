package com.hyzs.onekeyhelp.mine.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.activity.adapter.MineBillAdapter;
import com.hyzs.onekeyhelp.mine.activity.bean.ExpenseBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MineExpenseFragment extends Fragment {
    private ListView mLv;
    private MineBillAdapter adapter;
    private List<ExpenseBean.AccountConsumptionListBean> list;
    private VRefresh mRefresh;
    private int index = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_only_lv, null);
        assignView(view);
        initView();
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        mRefresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                initData();
                mRefresh.setLoading(false);
            }
        });
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                index = 1;
//                list.clear();
//                initData();
                mRefresh.setRefreshing(false);
            }
        });
    }

    private void initView() {
        list = new ArrayList<>();
        mRefresh.setView(getActivity(),mLv);
        adapter = new MineBillAdapter(list,getActivity());
        mLv.setAdapter(adapter);
    }

    private void initData() {
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(getActivity()));
        param.put("pageSize", "20");
        param.put("pageIndex", index+"");
        NetRequest.ParamDialogPostRequest(PortUtil.ExpenseCalendar, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    JSONObject object = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if (object.getInt("total") == 0) {
                        ToastUtils.showShort(getActivity(), "无更多记录");
                        return;
                    }
                    JSONArray array = object.getJSONArray("accountConsumptionList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        ExpenseBean.AccountConsumptionListBean bean = new ExpenseBean.AccountConsumptionListBean();
                        bean.setMoney(object1.getDouble("money"));
                        bean.setAddtime(object1.getString("addtime"));
                        bean.setType(object1.getInt("type"));
//                        which为自定义属性，0为消费，1为充值
                        bean.setWhich(0);
                        list.add(bean);
                    }
                    adapter.notifyDataSetChanged();
                    index++;
                } catch (Exception e) {
                }
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {
            }
        });
    }

    private void assignView(View view) {
        mLv = (ListView) view.findViewById(R.id.fragment_only_list);
        mRefresh = (VRefresh) view.findViewById(R.id.fragment_only_vRefresh);
    }
}
