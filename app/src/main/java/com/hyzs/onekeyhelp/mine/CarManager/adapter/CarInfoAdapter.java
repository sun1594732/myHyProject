package com.hyzs.onekeyhelp.mine.CarManager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.CarManager.bean.CarInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class CarInfoAdapter extends BaseAdapter {
    private Context mContext;
    private List<CarInfoBean.MyVehicleListBean> list;

    public CarInfoAdapter(Context mContext, List<CarInfoBean.MyVehicleListBean>  list) {
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
        if (null == view) {
            view = View.inflate(mContext, R.layout.item_insur_info, null);
            holder = new MyHolder();
            holder.left = (TextView) view.findViewById(R.id.item_insur_info_left);
            holder.center = (TextView) view.findViewById(R.id.item_insur_info_center);
            holder.right = (ImageView) view.findViewById(R.id.item_insur_info_right);
            view.setTag(holder);
        }else{
            holder = (MyHolder) view.getTag();
        }
        CarInfoBean.MyVehicleListBean bean = list.get(position);
        holder.left.setText(bean.getUesrname());
        holder.center.setText(bean.getV_VehicleCardNum());
        return view;
    }

    private class MyHolder{
        private TextView left,center;
        private ImageView right;
    }
}
