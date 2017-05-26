package com.hyzs.onekeyhelp.mine.CarManager.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.CarManager.activity.CarManagerActivity;
import com.hyzs.onekeyhelp.mine.CarManager.adapter.CarInfoAdapter;
import com.hyzs.onekeyhelp.mine.CarManager.bean.CarInfoBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.hyzs.onekeyhelp.widget.wheel.BirthDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/27.
 */

public class CarInfoFragment extends Fragment implements View.OnClickListener,CarManagerActivity.SaveInterface {
    private static final String TAG = "CarInfoFragment";
    private Context mContext;
    private LinearLayout buyTime;
    private EditText number,name,color,brand,type;
    private TextView time;
    private ListView listView;
    private CarInfoBean bean;
    private CarInfoAdapter adapter;
    private VRefresh refresh;
    private int pageIndex=1;
    private Dialog dialog;
    private List<CarInfoBean.MyVehicleListBean> list=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_info, null);
        mContext = getActivity();
        assignView(view);
        initData();
        initView();
        initListener();
        return view;
    }

    private boolean loadFlag = false;
    private void initListener() {
        buyTime.setOnClickListener(this);
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
                pageIndex=1;
                initListData();
            }
        });
    }

    private void initView() {
        time.setText(getSystemTime());
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
        String url = PortUtil.carInfoFragment;
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(mContext));
        map.put("pageSize", "10");
        map.put("pageIndex", pageIndex+"");
        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), CarInfoBean.class);
                    initListView();
                } catch (Exception e) {

                    e.printStackTrace();
                }finally {
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
            if (bean.getMyVehicleList().size() == 0) {
                Toast.makeText(mContext, "没有更多数据", Toast.LENGTH_SHORT).show();
                return;
            }
            list.addAll(bean.getMyVehicleList());
            adapter.notifyDataSetChanged();
        }else {
            dialog.dismiss();
            list.clear();
            list.addAll(bean.getMyVehicleList())  ;
            if (null==adapter) {
                adapter = new CarInfoAdapter(mContext, list);
                listView.setAdapter(adapter);
            }else{
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void assignView(View view) {
        dialog = ProgressDialog.createProgressLoading(mContext);
        time = (TextView) view.findViewById(R.id.car_info_time);
        buyTime = (LinearLayout) view.findViewById(R.id.car_info_buy_time);
        number = (EditText) view.findViewById(R.id.car_info_number);
        name = (EditText) view.findViewById(R.id.car_info_owner_name);
        color = (EditText) view.findViewById(R.id.car_info_color);
        brand = (EditText) view.findViewById(R.id.car_info_brand);
        type = (EditText) view.findViewById(R.id.car_info_type);
        listView = (ListView) view.findViewById(R.id.insur_info_list);
        refresh = (VRefresh) view.findViewById(R.id.fragment_car_info_refresh);
        refresh.setView(mContext,listView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car_info_buy_time:
                BirthDialog mBirthDialog = new BirthDialog(mContext);
                String systemTime = getSystemTime();
                String[] split = systemTime.split("-");
                mBirthDialog.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                mBirthDialog.show();
                mBirthDialog.setBirthdayListener(new BirthDialog.OnBirthListener() {
                    @Override
                    public void onClick(String year, String month, String day) {
                        time.setText(year + "-" + month + "-" + day);
                    }
                });
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            CarManagerActivity.saveInterface = this;
        }
    }

    private String getSystemTime() {
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(l);
    }

    @Override
    public void save(int currPage) {
        if (0 == currPage) {
            initParame();
        }
    }

    private void initParame(){
        String sNumber,sName,sColor,sBrand,sType,sTime;
        sNumber = number.getText().toString().trim();
        sName = name.getText().toString().trim();
        sColor = color.getText().toString().trim();
        sBrand = brand.getText().toString().trim();
        sType = type.getText().toString().trim();
        sTime = time.getText().toString().trim();
        if (!MySharedPreferences.isLogin(mContext)) {
            Toast.makeText(mContext, "您还没有登录", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(sNumber) || TextUtils.isEmpty(sName)
                || TextUtils.isEmpty(sColor) || TextUtils.isEmpty(sBrand)
                || TextUtils.isEmpty(sType) || TextUtils.isEmpty(sTime)) {
            Toast.makeText(mContext, "信息不可以为空，请正确填写", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Map<String, String> map = new HashMap<>();
            map.put("currId", MySharedPreferences.getUserId(mContext));
            map.put("V_VehicleCardNum", sNumber);
            map.put("V_OwnerName", sName);
            map.put("V_Color", sColor);
            map.put("V_Brand", sBrand);
            map.put("V_Type", sType);
            map.put("V_BuyTime", sTime);
            addCarInfo(map);
        }
    }

    private void addCarInfo(Map<String,String> map) {
        String url = PortUtil.AddcCarInfo;
        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if ("10000".equals(jsonObject.getString("code"))) {
                        pageIndex=1;
                        initListData();
                        Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
