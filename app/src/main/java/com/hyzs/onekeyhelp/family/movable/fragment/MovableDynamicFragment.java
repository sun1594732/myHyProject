package com.hyzs.onekeyhelp.family.movable.fragment;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.movable.activity.MovableRelease;
import com.hyzs.onekeyhelp.family.movable.adapter.MovableDynamicAdapter;
import com.hyzs.onekeyhelp.family.movable.bean.MovableDynamicCommentBean;
import com.hyzs.onekeyhelp.family.movable.bean.MovabledynamicStateBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.JointGetUrl;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.hyzs.onekeyhelp.family.movable.activity.EventRegistActivity.releaseFlag;

/**
 * Created by Administrator on 2017/3/20.
 */

public class MovableDynamicFragment extends Fragment implements View.OnClickListener, VRefresh.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {
    private Context mContext;
    private ListView listView;
    private MovableDynamicAdapter adapter;
    private MovabledynamicStateBean stateBean;
    List<MovabledynamicStateBean.ActivityDynamicListBean> list;
    private MovableDynamicCommentBean CommentBean;
    private EditText et_context;
    private ImageView iv_send;
    private HashMap hms;
    private String url = PortUtil.ActivityDynamicList;
    private String Publishurl = PortUtil.PublishActivityDynamic;
    private VRefresh vrefresh;
    private OkHttpClient okHttpClient;
    private int index = 1;
    private Dialog PDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movable_dynamic, null);
        vrefresh = (VRefresh) view.findViewById(R.id.vrefresh);
        mContext = getActivity();
        assignView(view);
        PDialog = com.hyzs.onekeyhelp.util.ProgressDialog.createProgressLoading(getActivity());
        initData();
        initView();
        return view;
    }

    private void initView() {
        iv_send.setOnClickListener(this);

    }


    private void initData() {
        //请求数据完成再去做操作。
        list = new ArrayList<>();
        PDialog.show();
        resrshData(1);
    }


    private void resrshData(int type) {

        hms = new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(mContext)); //  15-3
        hms.put("activity_id", MovableDynamicFragment.this.getActivity().getIntent().getIntExtra("movableId", 0) + "");
        hms.put("activity_type", getActivity().getIntent().getIntExtra("type",3)+"");  //周末生活
        hms.put("pageSize", 10 + "");
        hms.put("pageIndex", index + "");
        requestMyData(hms, url, type);
    }


    /**
     * 联网请求数据
     */
    private void requestMyData(HashMap hms, String MWEBUrl, final int type) {

        MWEBUrl = JointGetUrl.getUrl(MWEBUrl, hms);
        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(MWEBUrl).build();

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String resule = URLDecoder.decode(response.body().string(), "utf-8");
                switch (type) {
                    case 1:
                    case 3:
                        stateBean = null;
                        stateBean = new Gson().fromJson(resule, MovabledynamicStateBean.class);
                        break;
                    case 2:
                        CommentBean = new Gson().fromJson(resule, MovableDynamicCommentBean.class);
                        break;
                    case 4:
                        stateBean.getActivityDynamicList().addAll(new Gson().fromJson(resule, MovabledynamicStateBean.class).getActivityDynamicList());
                        break;
                }
                handler.sendEmptyMessage(type);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vrefresh.setLoading(false);
            vrefresh.setRefreshing(false);
            switch (msg.what) {
                case 1:
                    adapter = null;
                    adapter = new MovableDynamicAdapter(mContext, stateBean);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    PDialog.dismiss();
                    break;
                case 2:
                    et_context.setText("");
                    Toast.makeText(mContext, "评论成功!", Toast.LENGTH_SHORT).show();

                    resrshData(3);
                    break;
                case 3:
                    adapter = null;
                    adapter = new MovableDynamicAdapter(mContext, stateBean);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    listView.setSelection(stateBean.getActivityDynamicList().size());
                    PDialog.dismiss();


                    break;
                case 4:
                    adapter.notifyDataSetChanged();

                    break;
            }
        }
    };

    private void assignView(View view) {
        listView = (ListView) view.findViewById(R.id.movable_dynamic_list);
        iv_send = (ImageView) view.findViewById(R.id.fragment_movable_dynamic_send);
        et_context = (EditText) view.findViewById(R.id.fragment_movable_dynamic_et_context);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.fragment_movable_dynamic_ll);

        vrefresh.setView(this.getActivity(), listView);

        vrefresh.setOnLoadListener(this);
        vrefresh.setOnRefreshListener(this);
        ll.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_movable_dynamic_send:
//                hms.clear();
//                hms = null;
//                hms = new HashMap();
//                hms.put("currId", MySharedPreferences.getUserId(mContext)); //  15-3
//                hms.put("currId", "179"); //  15-3
////        hms.put("activity_id",getActivity().getIntent().getIntExtra("movableId",0)+"");
//                hms.put("activity_id", "15");
//                hms.put("activity_type", "3");  //周末生活
//                hms.put("ed_type", "1");  //0动态，1评论
//                hms.put("ed_Content", et_context.getText().toString());
//                hms.put("ed_AffixImgList", "");
//                requestMyData(hms, Publishurl, 2);
                break;
            case R.id.fragment_movable_dynamic_ll:
                if (-1 == getActivity().getIntent().getIntExtra("movableId", -1)) {
                    return;
                }
                Intent intent = new Intent(mContext, MovableRelease.class);
                intent.putExtra("movableId",getActivity().getIntent().getIntExtra("movableId", -1));
                intent.putExtra("type", getActivity().getIntent().getIntExtra("type", 3));
                startActivity(intent);
                releaseFlag = true;
                break;
        }

    }

    /**
     * 圆形加载对话框
     */
    public void onResume() {
        super.onResume();
        if (releaseFlag) {
            releaseFlag=false;
            resrshData(3);
        }
        MobclickAgent.onPageStart("MovableDynamicFragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MovableDynamicFragment");
    }

    @Override
    public void onLoadMore() {
        ++index;
        resrshData(4);
    }

    @Override
    public void onRefresh() {
        index = 1;
        resrshData(1);
    }
}
