package com.hyzs.onekeyhelp.lifehelp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.lifehelp.adapter.LiveHelpMeAdapter;
import com.hyzs.onekeyhelp.lifehelp.bean.LifeHelpMeBean;
import com.hyzs.onekeyhelp.lifehelp.view.HelpMeDetailsActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.JointGetUrl;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.OkHttpUtil;

import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * Created by chason on 2017/3/19.
 *
 * @action 我的求助Fragment界面
 */

public class HelpMeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View view;
    private ListView live_fragment_help_me_list;
    private OkHttpClient okHttpClient;
    private Request request;
    public VRefresh superRefreshLayout;

    // 标志位，标志已经初始化完成。

    private String WEBUrl = PortUtil.LifeHelpMe;

    private LifeHelpMeBean lifeHelpBean;
    private int pindex = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_live_help_me, container, false);
        live_fragment_help_me_list = (ListView) view.findViewById(R.id.live_fragment_help_me_list);
        superRefreshLayout = (VRefresh) view.findViewById(R.id.live_fragment_help_me_list_fresh);
        live_fragment_help_me_list.setOnItemClickListener(this);
        okHttpClient = new OkHttpClient();
        onActivityCreateds();
        return view;
    }


    LiveHelpMeAdapter meAdapter;

    /**
     * 获取服务器信息
     */
    private void getWebInfo2Adapter(final int type) {
        HashMap<String, String> hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this.getContext()));
        hms.put("Community_id", MySharedPreferences.getCommunityId(getActivity()));
        hms.put("pageIndex", pindex + "");
        hms.put("pageSize", "20");
        WEBUrl = JointGetUrl.getUrl(WEBUrl, hms);
        request = new Request.Builder().url(WEBUrl).build();
        WEBUrl = PortUtil.LifeHelpMe;
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                request = null;
            }

            @Override
            public void onResponse(Response response) throws IOException {
                request = null;
                try {
                    String str = response.body().string();
                    str = URLDecoder.decode(str, "utf-8");
                    Log.e("datasa", str);

                    if (type == 1) {
                        lifeHelpBean = new Gson().fromJson(str, LifeHelpMeBean.class);
                        meAdapter = new LiveHelpMeAdapter(view.getContext(), lifeHelpBean);
                    } else {
                        if (new Gson().fromJson(str, LifeHelpMeBean.class).getMySeekHelpList().size() == 0) {
                            handler.sendEmptyMessage(4);
                            return;

                        } else
                            lifeHelpBean.getMySeekHelpList().addAll(new Gson().fromJson(str, LifeHelpMeBean.class).getMySeekHelpList());
                    }
                    handler.sendEmptyMessage(type);
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(3);
                }
            }
        });
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    live_fragment_help_me_list.setAdapter(meAdapter);
                    superRefreshLayout.setRefreshing(false);
                    break;
                case 2:
                    meAdapter.notifyDataSetChanged();
                    superRefreshLayout.setLoading(false);
                    break;
                case 3:
                    Toast.makeText(HelpMeFragment.this.getActivity(), "数据获取异常", Toast.LENGTH_SHORT).show();
                    superRefreshLayout.setRefreshing(false);
                    superRefreshLayout.setLoading(false);
                    break;
                case 4:
                    Toast.makeText(HelpMeFragment.this.getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                    superRefreshLayout.setRefreshing(false);
                    superRefreshLayout.setLoading(false);
                    break;
            }

        }
    };


    public void onActivityCreateds() {


        getWebInfo2Adapter(1);

        superRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light);
        superRefreshLayout.setProgressViewOffset(false, 0, 48);
        superRefreshLayout.setSize(0);//0和1  圆形进度条两种不同效果 0刚开始有渲染效果
        superRefreshLayout.setView(this.getActivity(), live_fragment_help_me_list);//设置嵌套的子view -listview

        superRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pindex = 1;
                getWebInfo2Adapter(1);
            }
        });
        superRefreshLayout.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                ++pindex;
                getWebInfo2Adapter(2);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getContext(), HelpMeDetailsActivity.class);

        intent.putExtra("Seek_ID", lifeHelpBean.getMySeekHelpList().get(position).getSeek_ID() + "");
        intent.putExtra("ID", lifeHelpBean.getMySeekHelpList().get(position).getSeek_ID() + "");
        intent.putExtra("Seek_UserID", lifeHelpBean.getMySeekHelpList().get(position).getSeek_UserID() + "");
        startActivity(intent);
    }

    public void onResume() {
        super.onResume();
        pindex = 1;
        getWebInfo2Adapter(1);
        MobclickAgent.onPageStart("HelpMeFragment");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HelpMeFragment");
    }
}
