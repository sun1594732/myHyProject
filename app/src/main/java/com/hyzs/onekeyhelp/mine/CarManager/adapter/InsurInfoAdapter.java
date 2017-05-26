package com.hyzs.onekeyhelp.mine.CarManager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.CarManager.bean.InsurInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */

public class InsurInfoAdapter extends BaseAdapter {
    private Context mContext;
    private List<InsurInfoBean.MyVehicleInsuranceListBean> list;
    public InsurInfoAdapter(Context mContext,List<InsurInfoBean.MyVehicleInsuranceListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        MyHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_insur_info, null);
            holder = new MyHolder();
            holder.left = (TextView) view.findViewById(R.id.item_insur_info_left);
            holder.center = (TextView) view.findViewById(R.id.item_insur_info_center);
            holder.right = (ImageView) view.findViewById(R.id.item_insur_info_right);
            view.setTag(holder);
        }else{
            holder = (MyHolder) view.getTag();
        }
        InsurInfoBean.MyVehicleInsuranceListBean bean = list.get(position);
        holder.left.setText(bean.getUesrname());
        holder.center.setText(bean.getIC_Name());
        return view;
    }

    private class MyHolder{
        private TextView left,center;
        private ImageView right;
    }
}
