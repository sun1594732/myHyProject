package com.hyzs.onekeyhelp.mine.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.home.forum.bean.ForumFragmentBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;

import java.util.ArrayList;
import java.util.List;

public class CircleAdapter extends BaseAdapter {
    private Context context;
    private List<ForumFragmentBean.ForumListsBean> list;

    public CircleAdapter(Context context, List<ForumFragmentBean.ForumListsBean> list) {
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_fragment_cricle, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.item_fragment_interest_replace_icon);
            holder.name = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_name);
            holder.gender = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_gender);
            holder.community = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_community);
            holder.content = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_content);
            holder.time = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_time);
            holder.up = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_up);
            holder.commentCount = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_count);
            holder.pic1 = (ImageView) convertView.findViewById(R.id.item_fragment_interest_replace_pic1);
            holder.pic2 = (ImageView) convertView.findViewById(R.id.item_fragment_interest_replace_pic2);
            holder.pic3 = (ImageView) convertView.findViewById(R.id.item_fragment_interest_replace_pic3);
            holder.pic4 = (ImageView) convertView.findViewById(R.id.item_fragment_interest_replace_pic4);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        ForumFragmentBean.ForumListsBean bean  = list.get(position);
        holder.name.setText(bean.getTrueName());
        switch (bean.getSex()) {
            case "男":
                holder.gender.setText("男");
                holder.gender.setBackgroundResource(R.drawable.shape_66baff_6_solid);
                holder.gender.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.mipmap.man),null,null,null);
                break;
            case "女":
                holder.gender.setText("女");
                holder.gender.setBackgroundResource(R.drawable.shape_fc999b_6soild);
                holder.gender.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.mipmap.woman),null,null,null);
                break;
        }
        NetRequest.loadImg(context, bean.getAvatar(), holder.icon);
        holder.community.setText(bean.getCommunity());
        holder.content.setText(bean.getForum_Content());
        holder.time.setText(bean.getDiffTime());
//        holder.up.setText(bean.get() + "");
        holder.commentCount.setText(bean.getCommentCount() + "");
        handlePicture(bean.getForum_AffixImgList(), holder.pic1, holder.pic2, holder.pic3, holder.pic4);
        return convertView;
    }

    private void handlePicture(String circle_affixImgList, ImageView pic1, ImageView pic2, ImageView pic3, ImageView pic4) {
        List<String> pathList = getUrl(circle_affixImgList);
        pic1.setVisibility(View.GONE);
        pic2.setVisibility(View.GONE);
        pic3.setVisibility(View.GONE);
        pic4.setVisibility(View.GONE);
        switch (pathList.size()) {
            case 1:
                if (TextUtils.isEmpty(pathList.get(0))) {
                    return;
                }
                pic1.setVisibility(View.VISIBLE);
                NetRequest.loadImg(context,pathList.get(0),pic1);
                break;
            case 2:
                pic1.setVisibility(View.VISIBLE);
                pic2.setVisibility(View.VISIBLE);
                NetRequest.loadImg(context,pathList.get(0),pic1);
                NetRequest.loadImg(context,pathList.get(1),pic2);
                break;
            case 3:
                pic1.setVisibility(View.VISIBLE);
                pic2.setVisibility(View.VISIBLE);
                pic3.setVisibility(View.VISIBLE);
                NetRequest.loadImg(context,pathList.get(0),pic1);
                NetRequest.loadImg(context,pathList.get(1),pic2);
                NetRequest.loadImg(context,pathList.get(2),pic3);
                break;
            case 4:
                pic1.setVisibility(View.VISIBLE);
                pic2.setVisibility(View.VISIBLE);
                pic3.setVisibility(View.VISIBLE);
                pic4.setVisibility(View.VISIBLE);
                NetRequest.loadImg(context,pathList.get(0),pic1);
                NetRequest.loadImg(context,pathList.get(1),pic2);
                NetRequest.loadImg(context,pathList.get(2),pic3);
                NetRequest.loadImg(context,pathList.get(3),pic4);
                break;
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

    class ViewHolder {
        ImageView icon,pic1, pic2, pic3, pic4;
        TextView name, gender, community, content, time, up, commentCount;
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

}
