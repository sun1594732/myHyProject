package com.hyzs.onekeyhelp.lifehelp.fragment;

import android.content.Context;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.lifehelp.adapter.LiveMyHelpAdapter;
import com.hyzs.onekeyhelp.lifehelp.bean.LifeHelpOtherBean;
import com.hyzs.onekeyhelp.lifehelp.view.activity.HelpOtherDetailsActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.JointGetUrl;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

public class HelpOtherFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, VRefresh.OnLoadListener {
    private ListView live_fragment_my_help_list;
    private boolean isPrepared;
    private LifeHelpOtherBean lifeHelpOtherBean;
    private Context mContext;
    public VRefresh superRefreshLayout;
    private String WEBUrl = PortUtil.LifeHelpOther;
    private LiveMyHelpAdapter helpAdapter;
    private int pageIndex = 1;

    public HelpOtherFragment() {
        mContext = this.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_my_help, container, false);
        lifeHelpOtherBean = getArguments().getParcelable("bean");
        helpAdapter = new LiveMyHelpAdapter(this.getActivity(), lifeHelpOtherBean);
        live_fragment_my_help_list = (ListView) view.findViewById(R.id.live_fragment_my_help_list);
        live_fragment_my_help_list.setAdapter(helpAdapter);
        live_fragment_my_help_list.setOnItemClickListener(this);
        superRefreshLayout = (VRefresh) view.findViewById(R.id.live_fragment_my_help_list_fresh);
        initLinearstor();
        return view;

    }

    /**
     * 初始化上拉刷新
     */
    private void initLinearstor() {
        superRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light);
        superRefreshLayout.setProgressViewOffset(false, 0, 48);
        superRefreshLayout.setSize(0);//0和1  圆形进度条两种不同效果 0刚开始有渲染效果
        superRefreshLayout.setView(this.getActivity(), live_fragment_my_help_list);//设置嵌套的子view -listview
        superRefreshLayout.setOnRefreshListener(this);
        superRefreshLayout.setOnLoadListener(this);
    }

    /**
     * 获取服务器信息
     */
    private void getWebInfo2Adapter(final int type) {
        HashMap<String, String> hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this.getContext()));
        hms.put("pageIndex", pageIndex + "");
        hms.put("pageSize", "10");
        WEBUrl = JointGetUrl.getUrl(WEBUrl, hms);
        NetRequest.CommonUseListRequestMy(WEBUrl, hms, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    data = UrlDecodeUtil.urlCode(data);
                    if (type == 1) {
                        lifeHelpOtherBean = new Gson().fromJson(data, LifeHelpOtherBean.class);
                        helpAdapter = new LiveMyHelpAdapter(HelpOtherFragment.this.getContext(), lifeHelpOtherBean);
                        live_fragment_my_help_list.setAdapter(helpAdapter);
                    } else {
                        lifeHelpOtherBean.getMyHelpLists().addAll(new Gson().fromJson(data, LifeHelpOtherBean.class).getMyHelpLists());
                        helpAdapter.notifyDataSetChanged();
                    }
                    superRefreshLayout.setLoading(false);
                    superRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    Toast.makeText(HelpOtherFragment.this.getContext(), "获取数据异常", Toast.LENGTH_SHORT).show();
                    superRefreshLayout.setLoading(false);
                    superRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getContext(), HelpOtherDetailsActivity.class);
        intent.putExtra("isOther", true);
        intent.putExtra("SeekHelpUserId", lifeHelpOtherBean.getMyHelpLists().get(position).getSeekHelpUserId() + "");
        intent.putExtra("HelpPeopleUserId", lifeHelpOtherBean.getMyHelpLists().get(position).getHelpPeopleUserId() + "");
        intent.putExtra("ID", lifeHelpOtherBean.getMyHelpLists().get(position).getSeek_id() + "");
//        intent.putExtra("HelpID",lifeHelpOtherBean.getMyHelpLists().get(position).getID()+"");
        startActivity(intent);
    }

    public void onResume() {
        super.onResume();
        pageIndex = 1;
        getWebInfo2Adapter(1);
        MobclickAgent.onPageStart("HelpOtherFragment");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HelpOtherFragment");
    }


    @Override
    public void onRefresh() {

        pageIndex = 1;
        getWebInfo2Adapter(1);
    }

    @Override
    public void onLoadMore() {
        ++pageIndex;
        getWebInfo2Adapter(2);
    }
}
