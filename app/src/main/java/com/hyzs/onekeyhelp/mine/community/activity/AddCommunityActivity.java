package com.hyzs.onekeyhelp.mine.community.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.mine.community.adapter.AddCommunityAdapter;
import com.hyzs.onekeyhelp.mine.community.bean.AddCommunityBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.EditNotInputSpace;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/15.
 */

public class AddCommunityActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "AddCommunityActivity";
    private ImageView back;
    private TextView title, search;
    private EditText condition;
    private Button add;
    private ListView listView;
    private MySharedPreferences mySharedPreferences;
    private AddCommunityBean bean;
    private AddCommunityAdapter adapter;
    private int id = -1;
    private String name;
    private int position;
    private PopupWindow pop;
    private LinearLayout ll;
    private List<AddCommunityBean.SearchCommunityBean> list = new ArrayList<>();
    private String s = "";
    private LinearLayout noLinearLayout;

    @Override
    protected void assignView() {
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        search = (TextView) findViewById(R.id.add_community_search);
        condition = (EditText) findViewById(R.id.add_community_condition);
        add = (Button) findViewById(R.id.activity_add_community_add);
        listView = (ListView) findViewById(R.id.add_community_list);
        mySharedPreferences = MySharedPreferences.getInstance(this);
        ll = (LinearLayout) findViewById(R.id.linearLayout);
        noLinearLayout = (LinearLayout) findViewById(R.id.noLinearLayout);
    }

    @Override
    protected void initView() {
        title.setText("添加社区");
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        add.setOnClickListener(this);
        EditNotInputSpace.setEditTextInhibitInputSpace(condition);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddCommunityAdapter.MyHolder holder = (AddCommunityAdapter.MyHolder) view.getTag();
                if (list.get(position).getIsCheck() == 0) {
                    for (AddCommunityBean.SearchCommunityBean searchCommunityBean : list) {
                        searchCommunityBean.setIsCheck(0);
                    }
                    list.get(position).setIsCheck(1);
                    AddCommunityActivity.this.id = list.get(position).getId();
                    name = list.get(position).getCommunityName();
                    AddCommunityActivity.this.position = position;
                    holder.check.setImageResource(R.mipmap.add_community_checked);
                    adapter.notifyDataSetChanged();
                } else {
                    AddCommunityActivity.this.id = -1;
                    name = "";
                    list.get(position).setIsCheck(0);
                    holder.check.setImageResource(R.mipmap.add_community_unchecked);
                }
            }
        });

        condition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    AddCommunityActivity.this.s = "";
                    requestListView("", "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_community;
    }

    @Override
    protected void initData() {
        requestListView("", "");
    }

    private void requestListView(String communityName, String firstLetter) {
        Map<String, String> map = new HashMap<>();
        map.put("curr_uid", MySharedPreferences.getUserId(this));
        map.put("communityName", communityName);
        map.put("firstLetter", firstLetter);
        map.put("lat", mySharedPreferences.getString("Y"));
        map.put("lng", mySharedPreferences.getString("X"));
        map.put("pageSize", "999");
        map.put("pageIndex", "1");
        NetRequest.ParamPostRequest(PortUtil.SearchCommunity, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), AddCommunityBean.class);
                initListView();
            }
        });
    }

    private void initListView() {
        if (bean.getSearchCommunity().size() == 0) {
            noLinearLayout.setVisibility(View.VISIBLE);
        } else {
            Log.e(TAG, "initListView: " );
            noLinearLayout.setVisibility(View.GONE);
            list.clear();
            list.addAll(bean.getSearchCommunity());
            adapter = new AddCommunityAdapter(this, list);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_community_search:
                for (AddCommunityBean.SearchCommunityBean searchCommunityBean : list) {
                    searchCommunityBean.setIsCheck(0);
                }
                id=-1;
                name = "";
                s = condition.getText().toString().trim();
                if (TextUtils.isEmpty(s))
                    return;
                requestListView(s, "");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_add_community_add:
                if (id != -1 & !TextUtils.isEmpty(name)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("curr_uid", MySharedPreferences.getUserId(this));
                    map.put("communityName", name);
                    map.put("communityId", id + "");
                    NetRequest.ParamPostRequest(PortUtil.ApplyJoinCommunity, map, new NetRequest.getDataCallback() {
                        @Override
                        public void getData(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                                if ("10000".equals(jsonObject.getString("code"))) {
                                    id = -1;
                                    name = "";
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    pop = HelpDialog.createDialogNoAlert(AddCommunityActivity.this, AddCommunityActivity.this, "添加社区成功", R.mipmap.bingo);
                                    pop.showAtLocation(ll, Gravity.CENTER, 0, 0);
                                } else
                                    Toast.makeText(AddCommunityActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case R.id.help_pop_confirm:
                pop.dismiss();
                if (!TextUtils.isEmpty(s)) {
                    s = "";
                }
                condition.setText(s);
                requestListView("","");
                break;
        }
    }
}
