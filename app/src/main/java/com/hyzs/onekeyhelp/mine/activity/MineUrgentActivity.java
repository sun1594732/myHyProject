package com.hyzs.onekeyhelp.mine.activity;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.contact.activity.ContactSearchActivity;
import com.hyzs.onekeyhelp.contact.bean.ContactListEntity;
import com.hyzs.onekeyhelp.mine.activity.adapter.MineUrgentAdapter;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.ConfirmDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.baidu.location.b.k.co;

public class MineUrgentActivity extends BaseActivity implements View.OnClickListener {

    private ListView mLv;
    private MineUrgentAdapter adapter;
    private List<ContactListEntity.ContactListBean> list;
    private RelativeLayout mRl;
    private ImageView mBack ,mImg;
    private TextView mTitle ,mToolBarRight;

    @Override
    protected void assignView() {
        mLv = (ListView) findViewById(R.id.fragment_only_list);
        mRl = (RelativeLayout) findViewById(R.id.toolbar);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolBarRight = (TextView) findViewById(R.id.toolbar_right);
        mImg = (ImageView) findViewById(R.id.fragment_only_img);
    }

    @Override
    protected void initView() {
        mRl.setVisibility(View.VISIBLE);
        mTitle.setText("我的紧急联系人");
        mToolBarRight.setText("添加");
        mToolBarRight.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        adapter = new MineUrgentAdapter(list, this);
        mLv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mToolBarRight.setOnClickListener(this);
        mImg.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_only_lv;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:
                Intent intent = new Intent(this, ContactSearchActivity.class);
                intent.putExtra("isUrgent",true);
                startActivity(intent);
                break;
            case R.id.fragment_only_img:
                mImg.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> m = new ArrayMap<>();
        m.put("currId", MySharedPreferences.getUserId(this));
        m.put("searchKeyWords", "");
        m.put("pageIndex", "1");
        m.put("pageSize", "999");
        NetRequest.ParamDialogPostRequest(PortUtil.UserContactList, m, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    ContactListEntity bean = new Gson().fromJson(data, ContactListEntity.class);
                    for (int i = 0; i < bean.getContactList().size(); i++) {
                        if ("是".equals(bean.getContactList().get(i).getUrgentFlag().trim())) {
                            list.add(bean.getContactList().get(i));
                        }
                    }
                    if (list.size() == 0) {
                        mImg.setBackgroundResource(R.mipmap.yindaoye);
                        mImg.setVisibility(View.VISIBLE);
                        return;
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {

            }
        });
    }
}
