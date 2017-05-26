package com.hyzs.onekeyhelp.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.home.bean.NewsListBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.List;

public class HomeNewsAdapter extends BaseAdapter {
    private List<NewsListBean.NewsListByClassifyBean> list;
    private Context context;

    public HomeNewsAdapter(List<NewsListBean.NewsListByClassifyBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_news,null);
            holder = new ViewHolder();
            holder.icon = (RoundImageView) convertView.findViewById(R.id.item_home_news_pic);
            holder.time = (TextView) convertView.findViewById(R.id.item_home_news_time);
            holder.title = (TextView) convertView.findViewById(R.id.item_home_news_title);
            holder.author = (TextView) convertView.findViewById(R.id.item_home_news_author);
            convertView.setTag(holder);
        }else holder = (ViewHolder) convertView.getTag();
        NewsListBean.NewsListByClassifyBean bean = list.get(position);
        holder.title.setText(bean.getTitle());
        holder.time.setText(bean.getDiffTime());
        holder.author.setText("作者 / "+bean.getResource());
        if (TextUtils.isEmpty(bean.getImgURL())) {
            holder.icon.setVisibility(View.GONE);
        }else NetRequest.loadImg(context,bean.getImgURL(),holder.icon);
        return convertView;
    }

    class ViewHolder {
        RoundImageView icon;
        TextView title, time, author;
    }
}
