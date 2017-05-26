package com.hyzs.onekeyhelp.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.home.bean.HomePageBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.List;

public class HomePostLvAdapter extends BaseAdapter {
    private List<HomePageBean.CommunityAnnouncementBean> list;
    private Context context;
    private List<Integer> bgs;

    public HomePostLvAdapter(List<HomePageBean.CommunityAnnouncementBean> list, Context context, List<Integer> bgs) {
        this.list = list;
        this.context = context;
        this.bgs = bgs;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_post, null);
            holder = new ViewHolder();
            holder.icon = (RoundImageView) convertView.findViewById(R.id.item_home_post_pic);
            holder.time = (TextView) convertView.findViewById(R.id.item_home_post_time);
            holder.title = (TextView) convertView.findViewById(R.id.item_home_post_title);
            holder.organization = (TextView) convertView.findViewById(R.id.item_home_post_organization);
            holder.position = (TextView) convertView.findViewById(R.id.item_home_post_position);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        HomePageBean.CommunityAnnouncementBean bean = list.get(position);
        holder.title.setText(bean.title());
        holder.time.setText(bean.getDiffTime());
        holder.position.setText(bean.getJwhOfjq());
        holder.organization.setText(bean.getJwh());
        holder.icon.setImageResource(bgs.get(position));
        return convertView;
    }

    class ViewHolder {
        RoundImageView icon;
        TextView title, time, position, organization;
    }
}
