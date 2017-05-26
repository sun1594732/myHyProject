package com.hyzs.onekeyhelp.module.housekeeping.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.module.housekeeping.bean.MineServiceDetailBean;

import java.text.SimpleDateFormat;
import java.util.List;


public class MineServiceDetailExperienceAdapter extends BaseAdapter {
    private Context mContext;
    private List<MineServiceDetailBean.WorkExperienceBean> list;

    public MineServiceDetailExperienceAdapter(Context mContext, List<MineServiceDetailBean.WorkExperienceBean> list) {
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
            view = View.inflate(mContext, R.layout.item_mine_service_experience_layut, null);
            holder = new MyHolder();
            holder.start_time = (TextView) view.findViewById(R.id.item_mine_service_experience_start_time);
            holder.end_time = (TextView) view.findViewById(R.id.item_mine_service_experience_end_time);
            holder.type = (TextView) view.findViewById(R.id.item_mine_service_experience_type);
            holder.address = (TextView) view.findViewById(R.id.item_mine_service_experience_address);
            holder.status = (TextView) view.findViewById(R.id.item_mine_service_experience_status);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        MineServiceDetailBean.WorkExperienceBean bean = list.get(position);
        holder.start_time.setText(timeChange(Long.parseLong(bean.getWE_BeginTime())));
        holder.end_time.setText(timeChange(Long.parseLong(bean.getWE_EndTime())));
        holder.type.setText(bean.getWE_PositionName());
        holder.address.setText(bean.getWE_WorkOfCommunity());
        if (bean.getWE_CheckState() == 0) {
            holder.status.setText("未验证");
            holder.status.setTextColor(ContextCompat.getColor(mContext, R.color.color_3));
            Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.wei_yz);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.status.setCompoundDrawables(null,drawable , null, null);
        } else {
            holder.status.setText("已验证");
            holder.status.setTextColor(ContextCompat.getColor(mContext, R.color.color_f41d2c));
            Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.yi_yz);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.status.setCompoundDrawables(null,drawable , null, null);
        }
        return view;
    }

    private String timeChange(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.DD");
        return format.format(l * 1000);
    }

    private class MyHolder {
        private TextView start_time, end_time, type, address, status;
    }
}
