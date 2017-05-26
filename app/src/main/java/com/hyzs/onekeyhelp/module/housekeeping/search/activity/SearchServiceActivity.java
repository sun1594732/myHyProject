package com.hyzs.onekeyhelp.module.housekeeping.search.activity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.module.housekeeping.bean.AgencyServiceBean;
import com.hyzs.onekeyhelp.module.housekeeping.bean.MineServiceBean;
import com.hyzs.onekeyhelp.module.housekeeping.search.adapter.SearchLeftAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.search.adapter.SearchRightAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.search.bean.RightTabBean;
import com.hyzs.onekeyhelp.module.housekeeping.search.bean.TabBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.EditNotInputSpace;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchServiceActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SearchServiceActivity";
    private ImageView back, search_submit;
    private TextView title;
    private EditText condition;
    private ListView lvLeft, lvRight;
    private MySharedPreferences mySharedPreferences;
    private String sCondition = "";
    private Dialog dialog;
    private List<TabBean> leftList = new ArrayList<>();
    private List<RightTabBean.LifeServiceSPBean> rightList = new ArrayList<>();
    private String[] leftTabMine = new String[]{"距离", "工资", "经验", "年龄", "服务"};
    private int[] leftImgMine = new int[]{R.mipmap.jl_hui, R.mipmap.gz_hui, R.mipmap.jy_hui, R.mipmap.nianling_hui, R.mipmap.maozi_hui};
    private int[] leftImgMineCheck = new int[]{R.mipmap.jl_lv, R.mipmap.gz_lv, R.mipmap.jy_lv, R.mipmap.nianling_lv, R.mipmap.maozi_lv};
    private int[] leftImgAgency = new int[]{R.mipmap.jl_hui, R.mipmap.pj_hui, R.mipmap.maozi_hui};
    private int[] leftImgAgencyCheck = new int[]{R.mipmap.jl_lv, R.mipmap.pj_lv, R.mipmap.maozi_lv};
    private String[] leftTabAgency = new String[]{"距离", "评价", "服务"};
    private String[] distances = new String[]{"离我3KM以内", "离我6KM以内", "离我10KM以内", "离我20KM以内", "离我20KM以上",};
    private int[] s_distances = new int[]{0, 0, 0, 0, 20};
    private int[] e_distances = new int[]{3, 6, 10, 20, 2000};
    private String[] salary = new String[]{"2000元以下", "2000~4000元", "4000~6000元", "6000~8000元", "8000元以上"};
    private int[] s_salary = new int[]{0, 2000, 4000, 6000, 8000};
    private int[] e_salary = new int[]{2000, 4000, 6000, 8000, 800000};
    private String[] experiences = new String[]{"不限", "一年以下", "一年~三年以内", "3年~5年以内", "5年以上"};
    private int[] s_experiences = new int[]{-1, 0, 1, 3, 5};
    private int[] e_experiences = new int[]{-1, 1, 3, 5, 500};
    private String[] ages = new String[]{"不限", "20岁以内", "20岁~40岁之间"};
    private int[] s_ages = new int[]{-1, 16, 20};
    private int[] e_ages = new int[]{-1, 20, 40};
    private String[] comments = new String[]{"不限", "评论多", "评论少"};
    private int[] s_comments = new int[]{-1, 0, 1};
    private SearchLeftAdapter leftAdapter;
    private SearchRightAdapter rightAdapter;

    private int leftPosition = 0;
    private String b_j_agency = "-1";
    private String e_j_agency = "-1";
    private String c_n_agency = "-1";
    private String s_agency = "-1";
    private String b_j_mine = "-1";
    private String e_j_mine = "-1";
    private String b_s_mine = "-1";
    private String e_s_mine = "-1";
    private String b_e_mine = "-1";
    private String e_e_mine = "-1";
    private String b_a_mine = "-1";
    private String e_a_mine = "-1";
    private String s_mine = "-1";
    private RightTabBean rightTabBean;

    @Override
    protected void assignView() {
        dialog = ProgressDialog.createProgressLoading(this);
        mySharedPreferences = MySharedPreferences.getInstance(this);
        back = (ImageView) findViewById(R.id.toolbar_back);
        title = (TextView) findViewById(R.id.toolbar_title);
        search_submit = (ImageView) findViewById(R.id.activity_search_service_search_submit);
        lvLeft = (ListView) findViewById(R.id.activity_search_service_list_left);
        lvRight = (ListView) findViewById(R.id.activity_search_service_list_right);
        condition = (EditText) findViewById(R.id.activity_search_service_search_bar);
        EditNotInputSpace.setEditTextInhibitInputSpace(condition);
    }

    @Override
    protected void initView() {
        title.setText("条件筛选");
        leftAdapter = new SearchLeftAdapter(this, leftList);
        lvLeft.setAdapter(leftAdapter);

        rightAdapter = new SearchRightAdapter(this, rightList);
        lvRight.setAdapter(rightAdapter);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        search_submit.setOnClickListener(this);
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchServiceActivity.this.leftPosition = position;
                rightList.clear();
                if (getIntent().getIntExtra("index", 0) == 0) {
                    for (int i = 0; i < leftList.size(); i++) {
                        leftList.get(i).setImg(leftImgAgency[i]);
                        leftList.get(i).setIsCheck(0);
                    }
                    TabBean tabBean = leftList.get(position);
                    tabBean.setImg(leftImgAgencyCheck[position]);
                    tabBean.setIsCheck(1);
                    switch (position) {
                        case 0:
                            for (int i = 0; i < distances.length; i++) {
                                rightList.add(new RightTabBean.LifeServiceSPBean(0, distances[i], s_distances[i], e_distances[i]));
                            }
                            break;
                        case 1:
                            for (int i = 0; i < comments.length; i++) {
                                rightList.add(new RightTabBean.LifeServiceSPBean(s_comments[i], comments[i], 0, 0));
                            }
                            break;
                        case 2:
                            if (rightTabBean == null) {
                                requestServiceList();
                            } else {
                                rightList.addAll(rightTabBean.getLifeServiceSP());
                            }
                            break;
                    }
                } else {
                    for (int i = 0; i < leftList.size(); i++) {
                        leftList.get(i).setImg(leftImgMine[i]);
                        leftList.get(i).setIsCheck(0);
                    }
                    TabBean tabBean = leftList.get(position);
                    tabBean.setImg(leftImgMineCheck[position]);
                    tabBean.setIsCheck(1);
                    switch (position) {
                        case 0:
                            for (int i = 0; i < distances.length; i++) {
                                rightList.add(new RightTabBean.LifeServiceSPBean(0, distances[i], s_distances[i], e_distances[0]));
                            }
                            break;
                        case 1:
                            for (int i = 0; i < salary.length; i++) {
                                rightList.add(new RightTabBean.LifeServiceSPBean(0, salary[i], s_salary[i], e_salary[0]));
                            }
                            break;
                        case 2:
                            for (int i = 0; i < experiences.length; i++) {
                                rightList.add(new RightTabBean.LifeServiceSPBean(0, experiences[i], s_experiences[i], e_experiences[0]));
                            }
                            break;
                        case 3:
                            for (int i = 0; i < ages.length; i++) {
                                rightList.add(new RightTabBean.LifeServiceSPBean(0, ages[i], s_ages[i], e_ages[0]));
                            }
                            break;
                        case 4:
                            if (rightTabBean == null) {
                                requestServiceList();
                            } else {
                                rightList.addAll(rightTabBean.getLifeServiceSP());
                            }
                            break;
                    }
                }
                rightAdapter.notifyDataSetChanged();
                leftAdapter.notifyDataSetChanged();
            }
        });

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getIntent().getIntExtra("index", 0) == 0) {
                    switch (leftPosition) {
                        case 0:
                            b_j_agency = rightList.get(position).getStart() + "";
                            e_j_agency = rightList.get(position).getEnd() + "";
                            break;
                        case 1:
                            c_n_agency = rightList.get(position).getID() + "";
                            break;
                        case 2:
                            s_agency = rightList.get(position).getID() + "";
                            break;
                    }
                } else {
                    switch (leftPosition) {
                        case 0:
                            b_j_mine = rightList.get(position).getStart() + "";
                            e_j_mine = rightList.get(position).getEnd() + "";
                            break;
                        case 1:
                            b_s_mine = rightList.get(position).getStart() + "";
                            e_s_mine = rightList.get(position).getEnd() + "";
                            break;
                        case 2:
                            b_e_mine = rightList.get(position).getStart() + "";
                            e_e_mine = rightList.get(position).getStart() + "";
                            break;
                        case 3:
                            b_a_mine = rightList.get(position).getStart() + "";
                            e_a_mine = rightList.get(position).getStart() + "";
                            break;
                        case 4:
                            s_mine = rightList.get(position).getID() + "";
                            break;
                    }
                }
            }
        });


        condition.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    private void requestServiceList() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("pageSize", "111");
        map.put("pageIndex", "1");
        NetRequest.ParamPostRequest(PortUtil.LifeServiceSP, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                Log.e(TAG, "getData: " + UrlDecodeUtil.urlCode(data));
                rightTabBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), RightTabBean.class);
                rightList.addAll(rightTabBean.getLifeServiceSP());
                rightAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_service;
    }

    @Override
    protected void initData() {
        initLeftList();
        initRightList();
    }

    private void initRightList() {
        rightList.clear();
        for (int i = 0; i < distances.length; i++) {
            rightList.add(new RightTabBean.LifeServiceSPBean(0, distances[i], s_distances[i], e_distances[i]));
        }
    }

    private void initLeftList() {
        if (getIntent().getIntExtra("index", 0) == 0) {
            leftList.clear();
            for (int i = 0; i < leftTabAgency.length; i++) {
                if (i == 0) {
                    leftList.add(new TabBean(leftImgAgency[i], leftTabAgency[i], 1));
                    continue;
                }
                leftList.add(new TabBean(leftImgAgency[i], leftTabAgency[i]));
            }
        } else {
            leftList.clear();
            for (int i = 0; i < leftTabMine.length; i++) {
                if (i == 0) {
                    leftList.add(new TabBean(leftImgMine[i], leftTabMine[i], 1));
                    continue;
                }
                leftList.add(new TabBean(leftImgMine[i], leftTabMine[i]));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_search_service_search_submit:
                search();
                break;
        }
    }

    private void search() {
        sCondition = condition.getText().toString();
        dialog.show();
        if (getIntent().getIntExtra("index", 0) == 0) {
            searchAgency();
        } else {
            searchMine();
        }
    }

    private void searchMine() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("location", mySharedPreferences.getString("X") + "," + mySharedPreferences.getString("Y"));
        map.put("firstPageReqDateTime", System.currentTimeMillis() / 1000 + "");
        map.put("serachKeyWords", sCondition);
        map.put("pageSize", "111");
        map.put("pageIndex", "1");
        map.put("b_j", b_j_mine);
        map.put("e_j", e_j_mine);
        map.put("b_s", b_s_mine);
        map.put("e_s", e_s_mine);
        map.put("b_e", b_e_mine);
        map.put("e_e", e_e_mine);
        map.put("b_a", b_a_mine);
        map.put("e_a", e_a_mine);
        map.put("s", s_mine);
        for (String s : map.keySet()) {
            Log.e(TAG, "searchMine: " + map.get(s));
        }
        NetRequest.ParamPostRequest(PortUtil.LifeServicePersonal, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    Log.e(TAG, "getData: " + UrlDecodeUtil.urlCode(data));
                    MineServiceBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MineServiceBean.class);
                    Intent intent = new Intent();
                    intent.putExtra("bean", bean);
                    setResult(1, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                }
            }
        });
    }

    private void searchAgency() {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(this));
        map.put("location", mySharedPreferences.getString("X") + "," + mySharedPreferences.getString("Y"));
        map.put("firstPageReqDateTime", System.currentTimeMillis() / 1000 + "");
        map.put("serachKeyWords", sCondition);
        map.put("pageSize", "111");
        map.put("pageIndex", "1");
        map.put("b_j", b_j_agency);
        map.put("e_j", e_j_agency);
        map.put("c_n", c_n_agency);
        map.put("s", s_agency);
        NetRequest.ParamPostRequest(PortUtil.LifeServiceOrganization, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    Log.e(TAG, "getData: " + UrlDecodeUtil.urlCode(data));
                    AgencyServiceBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), AgencyServiceBean.class);
                    Intent intent = new Intent();
                    intent.putExtra("bean", bean);
                    setResult(1, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                }
            }
        });
    }
}
