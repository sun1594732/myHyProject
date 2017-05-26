package com.hyzs.onekeyhelp.mine.CarManager.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.CarManager.activity.CarManagerActivity;
import com.hyzs.onekeyhelp.mine.CarManager.adapter.InsurInfoAdapter;
import com.hyzs.onekeyhelp.mine.CarManager.bean.InsurInfoBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.hyzs.onekeyhelp.widget.wheel.BirthDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/27.
 */

public class InsurInfoFragment extends Fragment implements View.OnClickListener, CarManagerActivity.SaveInterface {
    private static final String TAG = "InsurInfoFragment";
    private Context mContext;
    private ListView listView;
    private TextView leftTime, rightTime, compa;
    private LinearLayout left, right;
    private EditText name, number, type;
    private PopupWindow mPopupWindow;
    private int compaId = 0;
    private InsurInfoBean bean;
    private InsurInfoAdapter adapter;
    private VRefresh refresh;
    private int pageIndex = 1;
    private boolean loadFlag = false;
    private Dialog dialog;
    private List<InsurInfoBean.MyVehicleInsuranceListBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insur_info, null);
        mContext = getActivity();
        assignView(view);
        initData();
        initView();
        initListener();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            CarManagerActivity.saveInterface = this;
        }
    }

    private void initListener() {
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        refresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                loadFlag = true;
                initListData();
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                initListData();
            }
        });
    }

    private void initView() {
        leftTime.setText(getSystemTime());
        rightTime.setText(getSystemTime());
        compa.setOnClickListener(this);
        initPop();
    }

    private void initData() {
        dialog.show();
        initListData();
    }

    private void initListData() {
        if (!MySharedPreferences.isLogin(mContext)) {
            Toast.makeText(mContext, "您还没有登录", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(mContext));
        map.put("pageSize", "10");
        map.put("pageIndex", pageIndex + "");
        NetRequest.ParamPostRequest(PortUtil.InsurInfo, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), InsurInfoBean.class);
                    initListView();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                }

            }
        });
    }

    private void initListView() {
        refresh.setLoading(false);
        refresh.setRefreshing(false);
        if (loadFlag) {
            loadFlag = false;
            if (bean.getMyVehicleInsuranceList().size() == 0) {
                Toast.makeText(mContext, "没有更多数据", Toast.LENGTH_SHORT).show();
                return;
            }
            list.addAll(bean.getMyVehicleInsuranceList());
            adapter.notifyDataSetChanged();
        } else {
            dialog.dismiss();
            list.clear();
            list.addAll(bean.getMyVehicleInsuranceList());
            if (null == adapter) {
                adapter = new InsurInfoAdapter(mContext, list);
                listView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void assignView(View view) {
        dialog = ProgressDialog.createProgressLoading(mContext);
        listView = (ListView) view.findViewById(R.id.insur_info_list);
        left = (LinearLayout) view.findViewById(R.id.insur_info_left);
        right = (LinearLayout) view.findViewById(R.id.insur_info_right);
        leftTime = (TextView) view.findViewById(R.id.insur_info_left_time);
        rightTime = (TextView) view.findViewById(R.id.insur_info_right_time);
        name = (EditText) view.findViewById(R.id.insur_info_name);
        number = (EditText) view.findViewById(R.id.insur_info_number);
        type = (EditText) view.findViewById(R.id.insur_info_type);
        compa = (TextView) view.findViewById(R.id.insur_info_compa);
        refresh = (VRefresh) view.findViewById(R.id.fragment_insur_info_refresh);
        refresh.setView(mContext, listView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insur_info_left:
                BirthDialog mBirthDialog = new BirthDialog(mContext);
                String systemTime = getSystemTime();
                String[] split = systemTime.split("-");
                mBirthDialog.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                mBirthDialog.show();
                mBirthDialog.setBirthdayListener(new BirthDialog.OnBirthListener() {
                    @Override
                    public void onClick(String year, String month, String day) {
                        leftTime.setText(year + "-" + month + "-" + day);
                    }
                });
                break;
            case R.id.insur_info_right:
                BirthDialog mBirthDialog1 = new BirthDialog(mContext);
                String systemTime1 = getSystemTime();
                String[] split1 = systemTime1.split("-");
                mBirthDialog1.setDate(Integer.parseInt(split1[0]), Integer.parseInt(split1[1]), Integer.parseInt(split1[2]));
                mBirthDialog1.show();
                mBirthDialog1.setBirthdayListener(new BirthDialog.OnBirthListener() {
                    @Override
                    public void onClick(String year, String month, String day) {
                        rightTime.setText(year + "-" + month + "-" + day);
                    }
                });
                break;
            case R.id.insur_info_compa:
                mPopupWindow.showAsDropDown(compa);
                break;
            case R.id.insur1:
                compa.setText("中国平安保险");
                compa.setTextColor(ContextCompat.getColor(mContext, R.color.color_3));
                compaId = 1;
                mPopupWindow.dismiss();
                break;
            case R.id.insur2:
                compa.setText("中国人寿保险");
                compa.setTextColor(ContextCompat.getColor(mContext, R.color.color_3));
                compaId = 2;
                mPopupWindow.dismiss();
                break;
            case R.id.insur3:
                compa.setText("中国太平洋保险");
                compa.setTextColor(ContextCompat.getColor(mContext, R.color.color_3));
                compaId = 3;
                mPopupWindow.dismiss();
                break;
            case R.id.insur4:
                compa.setText("中国太平人寿保险");
                compa.setTextColor(ContextCompat.getColor(mContext, R.color.color_3));
                compaId = 4;
                mPopupWindow.dismiss();
                break;
            case R.id.insur5:
                compa.setText("中国人保保险");
                compa.setTextColor(ContextCompat.getColor(mContext, R.color.color_3));
                compaId = 5;
                mPopupWindow.dismiss();
                break;
            case R.id.insur6:
                compa.setText("天安保险");
                compa.setTextColor(ContextCompat.getColor(mContext, R.color.color_3));
                compaId = 6;
                mPopupWindow.dismiss();
                break;
            case R.id.insur7:
                compa.setText("大众保险");
                compa.setTextColor(ContextCompat.getColor(mContext, R.color.color_3));
                compaId = 7;
                mPopupWindow.dismiss();
                break;
        }
    }

    private String getSystemTime() {
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(l);
    }

    @Override
    public void save(int currPage) {
        if (1 == currPage) {
            initParame();
        }
    }

    private void initParame() {
        if (!MySharedPreferences.isLogin(mContext)) {
            Toast.makeText(mContext, "您还没有登录", Toast.LENGTH_SHORT).show();
            return;
        }
        if (compaId == 0) {
            Toast.makeText(mContext, "请选择保险公司", Toast.LENGTH_SHORT).show();
            return;
        }
        String sNumber, sName, sType, sLeftTime, sRightTime;
        sNumber = number.getText().toString().trim();
        sName = name.getText().toString().trim();
        sType = type.getText().toString().trim();
        sLeftTime = leftTime.getText().toString();
        sRightTime = rightTime.getText().toString();
        if (TextUtils.isEmpty(sNumber) || TextUtils.isEmpty(sName)
                || TextUtils.isEmpty(sLeftTime) || TextUtils.isEmpty(sRightTime)
                || TextUtils.isEmpty(sType)) {
            Toast.makeText(mContext, "信息不可以为空，请正确填写", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("currId", MySharedPreferences.getUserId(mContext));
            map.put("IC_ID", compaId + "");
            map.put("VI_OwnerName", sName);
            map.put("VI_Number", sNumber);
            map.put("VI_Type", sType);
            map.put("VI_IndateB", sLeftTime);
            map.put("VI_IndateE", sRightTime);
            addCarInfo(map);
        }
    }

    private void addCarInfo(Map<String, String> map) {
        NetRequest.ParamPostRequest(PortUtil.InsurInfo2, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                Log.e(TAG, "getData: " + UrlDecodeUtil.urlCode(data));
                try {
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if ("10000".equals(jsonObject.getString("code"))) {
                        pageIndex = 1;
                        initListData();
                        initListData();
                        Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void initPop() {
        View popupView = getLayoutInflater(null).inflate(R.layout.pop_insur_info, null);
        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupView.findViewById(R.id.insur1).setOnClickListener(this);
        popupView.findViewById(R.id.insur2).setOnClickListener(this);
        popupView.findViewById(R.id.insur3).setOnClickListener(this);
        popupView.findViewById(R.id.insur4).setOnClickListener(this);
        popupView.findViewById(R.id.insur5).setOnClickListener(this);
        popupView.findViewById(R.id.insur6).setOnClickListener(this);
        popupView.findViewById(R.id.insur7).setOnClickListener(this);
    }
}
