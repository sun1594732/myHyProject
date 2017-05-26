package com.hyzs.onekeyhelp.carresuce.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.carresuce.adapter.HelpPeopleSumAdapter;
import com.hyzs.onekeyhelp.lifehelp.bean.HelpMeDetailsCommonBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/19.
 */
public class HelpPeopleSum extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private TextView title;
    private ListView listView;
    private HelpMeDetailsCommonBean bean;
    private HelpPeopleSumAdapter adapter;

    @Override
    protected void assignView() {
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        listView = (ListView) findViewById(R.id.help_people_sum_list);
    }

    @Override
    protected void initView() {
        title.setText("帮助我的人");
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.help_people_sum;
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("seekHelpID", getIntent().getStringExtra("VR_ID"));
        map.put("seekType", "1");
        NetRequest.ParamPostRequest(PortUtil.ComplateSeekHelpUserListCommon, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), HelpMeDetailsCommonBean.class);
                initListView();
            }
        });
    }

    private void initListView() {
        adapter = new HelpPeopleSumAdapter(this, bean.getComplateSeekHelpUserListCommon());
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
