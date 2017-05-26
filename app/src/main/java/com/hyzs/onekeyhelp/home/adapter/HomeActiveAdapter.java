package com.hyzs.onekeyhelp.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.movable.bean.MovableListBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by wubin on 2017/3/28.
 */

public class HomeActiveAdapter extends BaseAdapter {
    private List<MovableListBean.EventRegistrationListBean> list;
    private Context context;

    public HomeActiveAdapter(List<MovableListBean.EventRegistrationListBean> list, Context context) {
        this.list = list;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_active_nopic, null);
            holder = new ViewHolder();
            holder.icon = (RoundImageView) convertView.findViewById(R.id.item_home_active_nopic_pic);
            holder.time = (TextView) convertView.findViewById(R.id.item_home_active_nopic_time);
            holder.title = (TextView) convertView.findViewById(R.id.item_home_active_nopic_title);
            holder.count = (TextView) convertView.findViewById(R.id.item_home_active_nopic_count);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        MovableListBean.EventRegistrationListBean bean = list.get(position);
        holder.title.setText(bean.getER_Title());
        holder.time.setText("活动开始 : " + transTime(Long.parseLong(bean.getER_Datetime())));
        holder.count.setText("人数 : " + bean.getRegistrationCount());
        if (TextUtils.isEmpty(bean.getER_ImageUrl())) {
            holder.icon.setVisibility(View.GONE);
        } else NetRequest.loadImg(context, bean.getER_ImageUrl(), holder.icon);
        return convertView;
    }

    private String transTime(long times) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = new Long(times * 1000);
        String d = format.format(time);
        return d.substring(0,10);
    }

    class ViewHolder {
        RoundImageView icon;
        TextView title, time, count;
    }
}
