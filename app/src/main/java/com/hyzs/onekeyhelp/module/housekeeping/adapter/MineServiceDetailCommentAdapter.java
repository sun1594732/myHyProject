package com.hyzs.onekeyhelp.module.housekeeping.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.module.housekeeping.bean.MineServiceDetailBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */
public class MineServiceDetailCommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<MineServiceDetailBean.UserCommentBean> list;

    public MineServiceDetailCommentAdapter(Context mContext, List<MineServiceDetailBean.UserCommentBean> list) {
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
            view = View.inflate(mContext, R.layout.item_mine_service_comment_layout, null);
            holder = new MyHolder();
            holder.icon = (RoundImageView) view.findViewById(R.id.item_mine_service_comment_icon);
            holder.name = (TextView) view.findViewById(R.id.item_mine_service_comment_name);
            holder.time = (TextView) view.findViewById(R.id.item_mine_service_comment_time);
            holder.content = (TextView) view.findViewById(R.id.item_mine_service_comment_content);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        MineServiceDetailBean.UserCommentBean bean = list.get(position);
        if (TextUtils.isEmpty(bean.getFace())) {
            holder.icon.setImageResource(R.drawable.icon_replace);
        } else NetRequest.loadImg(mContext, bean.getFace(), holder.icon);
        holder.name.setText(bean.getUesrname());
        holder.time.setText(timeChange(Long.parseLong(bean.getCommentTime())));
        holder.content.setText(bean.getCommentContent());
        return view;
    }

    private String timeChange(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(l * 1000);
    }

    private class MyHolder {
        private RoundImageView icon;
        private TextView name, time, content;
    }
}
