package com.hyzs.onekeyhelp.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.home.bean.HomeCommunityDynamicListBean;
import com.hyzs.onekeyhelp.home.bean.NewsListBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.List;

public class HomeDynamAdapter extends BaseAdapter {
    private List<HomeCommunityDynamicListBean.FamilyDynamicListBean> list;
    private Context context;

    public HomeDynamAdapter(List<HomeCommunityDynamicListBean.FamilyDynamicListBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_news, null);
            holder = new ViewHolder();
            holder.icon = (RoundImageView) convertView.findViewById(R.id.item_home_news_pic);
            holder.time = (TextView) convertView.findViewById(R.id.item_home_news_time);
            holder.title = (TextView) convertView.findViewById(R.id.item_home_news_title);
            holder.picCount = (TextView) convertView.findViewById(R.id.item_home_news_picCount);
            holder.author = (TextView) convertView.findViewById(R.id.item_home_news_author);
            holder.authorIcon = (RoundImageView) convertView.findViewById(R.id.item_home_news_author_icon);
            holder.upCount = (TextView) convertView.findViewById(R.id.item_home_news_up);
            holder.commentCount = (TextView) convertView.findViewById(R.id.item_home_news_comment);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        HomeCommunityDynamicListBean.FamilyDynamicListBean bean = list.get(position);
        holder.title.setText(bean.getContent());
        holder.time.setText(bean.getDiffTime());
        holder.author.setText(bean.getUesrname() + "  发布于  " + bean.getR_c_typeName());
        holder.upCount.setText(bean.getPraise() + "");
        holder.commentCount.setText(bean.getCommentCount() + "");
        if (bean.getImages().size() > 0) {
            holder.picCount.setText(bean.getImages().size()+"");
            if (TextUtils.isEmpty(bean.getImages().get(0).getUrl())) {
                holder.icon.setImageResource(R.mipmap.replace_hybb);
            } else NetRequest.loadImg(context, bean.getImages().get(0).getUrl(), holder.icon);
        } else
            holder.icon.setImageResource(R.mipmap.replace_hybb);
        if (TextUtils.isEmpty(bean.getFace())) {
            holder.authorIcon.setImageResource(R.mipmap.replace_hybb);
        } else NetRequest.loadImg(context, bean.getFace(), holder.authorIcon);
        return convertView;
    }

    class ViewHolder {
        RoundImageView icon, authorIcon;
        TextView title, time, author, picCount, upCount, commentCount;
    }
}
