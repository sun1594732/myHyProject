package com.hyzs.onekeyhelp.mine.attention;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;

import java.util.HashMap;

public class MineAttentionForMeFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener, VRefresh.OnLoadListener {

    private View view;
    private ListView lv_attention_for_me;
    private int index = 1;
    private MineAttentionBean attentionBean;
    private MineAttentionForMeAdapter forMeAdapter;
    private VRefresh vrefresh;
    private Dialog mDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_attention_for_me, null);
        mDialog = ProgressDialog.createProgressLoading(this.getContext());
        mDialog.show();
        lv_attention_for_me = (ListView) view.findViewById(R.id.lv_attention_for_me);
        vrefresh = (VRefresh) view.findViewById(R.id.vrefresh);
        vrefresh.setView(this.getContext(), lv_attention_for_me);
        vrefresh.setOnRefreshListener(this);
        vrefresh.setOnLoadListener(this);

        setData();

        return view;
    }

    public void setData() {

        RequestData(1);

    }

    /**
     * 请求数据
     */
    public void RequestData(final int type) {
        HashMap<String, String> hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this.getActivity()));
        hms.put("pageSize", 10 + "");
        hms.put("pageIndex", index + "");
        hms.put("firstPageReqDateTime", System.currentTimeMillis()/1000 + "");
        NetRequest.CommonUseListRequestMy(PortUtil.MyWatchlist, hms, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {

                if (type == 1) {
                    attentionBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineAttentionBean.class);
                    forMeAdapter = new MineAttentionForMeAdapter(MineAttentionForMeFragment.this.getContext(), attentionBean.getUserInfo());
                    lv_attention_for_me.setAdapter(forMeAdapter);
                } else {
                    attentionBean.getUserInfo().addAll(new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineAttentionBean.class).getUserInfo());
                    forMeAdapter.notifyDataSetChanged();
                }
                vrefresh.setLoading(false);
                vrefresh.setRefreshing(false);
                mDialog.dismiss();
            }
        });
    }

    @Override
    public void onRefresh() {
        index = 1;
        RequestData(1);
    }

    @Override
    public void onLoadMore() {
        ++index;
        RequestData(2);
    }

}
