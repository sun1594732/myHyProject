package com.hyzs.onekeyhelp.mine.attention;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.home.adapter.HomeDynamAdapter;
import com.hyzs.onekeyhelp.home.bean.HomeCommunityDynamicListBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MineAttentionDynamicAdapter extends BaseAdapter {
    private List<MineAttentionDynamicBean.MyFavoritesListBean> list;
    private Context mContext;

    public MineAttentionDynamicAdapter(List<MineAttentionDynamicBean.MyFavoritesListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
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
        viewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_news, null);
            holder = new viewHolder();
            holder.icon = (RoundImageView) convertView.findViewById(R.id.item_home_news_pic);
            holder.time = (TextView) convertView.findViewById(R.id.item_home_news_time);
            holder.title = (TextView) convertView.findViewById(R.id.item_home_news_title);
            holder.picCount = (TextView) convertView.findViewById(R.id.item_home_news_picCount);
            holder.author = (TextView) convertView.findViewById(R.id.item_home_news_author);
            holder.authorIcon = (RoundImageView) convertView.findViewById(R.id.item_home_news_author_icon);
            holder.upCount = (TextView) convertView.findViewById(R.id.item_home_news_up);
            holder.commentCount = (TextView) convertView.findViewById(R.id.item_home_news_comment);
            holder.notification = (CircleImageView) convertView.findViewById(R.id.item_home_news_notification);
            convertView.setTag(holder);
        } else holder = (viewHolder) convertView.getTag();
        MineAttentionDynamicBean.MyFavoritesListBean bean = list.get(position);
        holder.title.setText(bean.getT_Content());
        holder.time.setText(handleTime(bean.getT_DateTime()));
        holder.author.setText(bean.getUesrname() + "  发布于  " + bean.getT_TypeName());
        holder.upCount.setVisibility(View.GONE);
        holder.commentCount.setVisibility(View.GONE);
        if (bean.getNewInfoCount() != 0) {
            holder.notification.setVisibility(View.VISIBLE);
        } else holder.notification.setVisibility(View.GONE);
        List<String> mImages = getUrl(bean.getT_AffixImgList());
        if (mImages.size() > 0) {
            holder.picCount.setText(mImages.size() + "");
            if (TextUtils.isEmpty(mImages.get(0))) {
                holder.icon.setImageResource(R.mipmap.replace_hybb);
            } else NetRequest.loadImg(mContext, mImages.get(0), holder.icon);
        } else
            holder.icon.setImageResource(R.mipmap.replace_hybb);
        if (TextUtils.isEmpty(bean.getFace())) {
            holder.authorIcon.setImageResource(R.mipmap.replace_hybb);
        } else NetRequest.loadImg(mContext, bean.getFace(), holder.authorIcon);
        return convertView;
    }

    class viewHolder {
        RoundImageView icon, authorIcon;
        TextView title, time, author, picCount, upCount, commentCount;
        CircleImageView notification;
    }


    public List<String> getUrl(String pathList) {
        List<String> list = new ArrayList<>();
        String a[] = pathList.split(",");
        if (a.length > 4) {
            for (int i = 0; i < 4; i++) {
                String temp = a[i].replace("{", "").replace("}", "");
                list.add(temp);
            }
            return list.subList(0, 4);
        } else {
            for (int i = 0; i < a.length; i++) {
                String temp = a[i].replace("{", "").replace("}", "");
                list.add(temp);
            }
            return list;
        }
    }

    private String handleTime(String circle_dateTime) {
        long minute = (System.currentTimeMillis() / 1000 - Long.parseLong(circle_dateTime)) / 60 + 8 * 60;
        if (minute > 60 && minute < 1440) {
            return minute / 60 + "小时前";
        } else if (minute > 1440) {
            return minute / 1440 + "天前";
        } else
            return minute + "分钟前";
    }
}
