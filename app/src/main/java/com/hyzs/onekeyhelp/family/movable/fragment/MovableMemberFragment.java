package com.hyzs.onekeyhelp.family.movable.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.movable.adapter.MovableMemberAdapter;
import com.hyzs.onekeyhelp.family.movable.bean.MovableMemberBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.OkHttpUtil;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/20.
 */

public class MovableMemberFragment extends Fragment {
    private static final String TAG = "MovableMemberFragment";
    private ListView listView;
    private MovableMemberAdapter adapter;
    private Context mContext;
    private MovableMemberBean bean;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    fillData();
                    break;
                case 2:
                    Toast.makeText(mContext, "还没有报名成员", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private Dialog dialog;

    private void fillData() {
        if (null == bean) {
            return;
        }
        if (bean.getEventMemberList().size() == 0) {
            handler.sendEmptyMessage(2);
            return;
        }
        adapter = new MovableMemberAdapter(mContext, bean);
        listView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movable_member, null);
        mContext = getActivity();
        assignView(view);
        initListener();
        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MySharedPreferences.getInstance(mContext).setString("Img", bean.getEventMemberList().get(position).getAvatar());
                Intent intent = new Intent();
                intent.putExtra("one",bean.getEventMemberList().get(position).getEM_UserID()+"");
                intent.putExtra("userName",bean.getEventMemberList().get(position).getNickName());
                intent.setClass(mContext, IntentChatActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    private void initData() {
        dialog.show();
        requestMemberData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initData();
        }
    }

    private void requestMemberData() {
        if (MySharedPreferences.isLogin(mContext)) {
            String url = PortUtil.EventMemberList+"?currId=" + MySharedPreferences.getUserId(mContext) + "&event_id=" + getActivity().getIntent().getIntExtra("movableId", 0) + "&pageSize=999&pageIndex=1";
            OkHttpUtil.newInstamce().getAsynHttp(url, new OkHttpUtil.HttpCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) {
                    String s = "";
                    try {
                        s = UrlDecodeUtil.urlCode(response.body().string());
                        String json = UrlDecodeUtil.urlCode(s);
                        bean = new Gson().fromJson(json, MovableMemberBean.class);
                        if (bean.getCode().trim().equals("10000") & null != bean.getEventMemberList()) {
                            handler.sendEmptyMessage(1);
                        } else handler.sendEmptyMessage(2);
                    } catch (Exception e) {
                        handler.sendEmptyMessage(2);
                        e.printStackTrace();
                    }finally {
                        dialog.dismiss();
                    }

                }
            });
        }
    }

    private void assignView(View view) {
        dialog = ProgressDialog.createProgressLoading(mContext);
        listView = (ListView) view.findViewById(R.id.movable_member_list);
    }

    private void initView() {
        listView.setAdapter(adapter);
    }

    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            initData();
            initView();
        }
        MobclickAgent.onPageStart("MovableMemberFragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MovableMemberFragment");
    }
}
