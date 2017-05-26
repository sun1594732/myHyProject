package com.hyzs.onekeyhelp.family.movable.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.movable.adapter.MovableListAdapter;
import com.hyzs.onekeyhelp.family.movable.bean.MovableListBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/19.
 */

public class MovableListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = "MovableListActivity";
    private ImageView mBack;
    private TextView mTitle;
    private TextView mToolBarRight;
    private ListView listView;
    private MovableListAdapter adapter;
    private MovableListBean bean;
    private VRefresh refresh;
    private int pageIndex = 1;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initList();
                    break;
            }
        }
    };
    private Dialog dialog;
    private List<MovableListBean.EventRegistrationListBean> list = new ArrayList<>();
    private boolean loadFlag = false;

    private void initList() {
        refresh.setLoading(false);
        refresh.setRefreshing(false);
        if (loadFlag) {
            loadFlag = false;
            if (bean.getEventRegistrationList().size() == 0) {
                Toast.makeText(this, "没有更多数据", Toast.LENGTH_SHORT).show();
                return;
            }
            list.addAll(bean.getEventRegistrationList());
            adapter.notifyDataSetChanged();
        } else {
            dialog.dismiss();
            list.clear();
            if (null == list)
                list = new ArrayList<>();
            if (null == bean) {
                requestListData();
                return;
            }
            list.addAll(bean.getEventRegistrationList());
            if (null == adapter) {
                if (list.size() == 0) {
                    Toast.makeText(this, "没有活动信息", Toast.LENGTH_SHORT).show();
                }
                adapter = new MovableListAdapter(this, list);
                listView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolBarRight = (TextView) findViewById(R.id.toolbar_right);
        listView = (ListView) findViewById(R.id.activity_movable_list);
        refresh = (VRefresh) findViewById(R.id.activity_movable_list_refresh);
        dialog = ProgressDialog.createProgressLoading(this);
        refresh.setView(this, listView);
    }

    @Override
    protected void initView() {
        mTitle.setText(getIntent().getStringExtra("title"));
        mToolBarRight.setText("发布");
        mToolBarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mToolBarRight.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        refresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                loadFlag = true;
                requestListData();
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                requestListData();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movable_list;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        dialog.show();
        pageIndex = 1;
        requestListData();
        MobclickAgent.onPageStart("MovableListActivity");
        MobclickAgent.onResume(this);
    }

    private void requestListData() {
        if (MySharedPreferences.isLogin(this)) {
            String url = PortUtil.EventRegistrationList;
            Map<String, String> map = new HashMap<>();
            map.put("currId", MySharedPreferences.getUserId(this));
            map.put("event_type", getIntent().getIntExtra("type", 3) + "");
            map.put("Community_id", MySharedPreferences.getCommunityId(MovableListActivity.this));
            map.put("pageSize", "10");
            map.put("pageIndex", pageIndex + "");
            NetRequest.ParamDialogPostRequest(url, map, new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    try {
                        String s = UrlDecodeUtil.urlCode(data);
                        bean = new Gson().fromJson(s, MovableListBean.class);
                        handler.sendEmptyMessage(0);
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MovableListActivity.this, "加载数据失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                        e.printStackTrace();
                    } finally {
                        dialog.dismiss();
                    }
                }
            }, new NetRequest.getDataFailCallback1() {
                @Override
                public void getData(Exception error) {

                }
            });
        } else {
            Toast.makeText(this, "您还没有登录！", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MovableListActivity.this, EventRegistActivity.class);
        intent.putExtra("movableId", list.get(position).getID());
        intent.putExtra("title", getIntent().getStringExtra("title"));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:
                Intent intent = new Intent(MovableListActivity.this, MovableReleaseActivity.class);
                intent.putExtra("type", getIntent().getIntExtra("type", 3));
                intent.putExtra("title", getIntent().getStringExtra("title"));
                startActivity(intent);
                break;
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MovableListActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
}
