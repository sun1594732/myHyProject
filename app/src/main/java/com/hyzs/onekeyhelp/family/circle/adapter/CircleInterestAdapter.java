package com.hyzs.onekeyhelp.family.circle.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.circle.bean.CircleFragmentBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

public class CircleInterestAdapter extends BaseAdapter {
    private Context context;
    private List<CircleFragmentBean.CircleListsBean> list;

    public CircleInterestAdapter(Context context, List<CircleFragmentBean.CircleListsBean> list) {
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
            convertView = View.inflate(context, R.layout.item_fragment_interest_replace, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.item_fragment_interest_replace_icon);
            holder.name = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_name);
            holder.gender = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_gender);
            holder.community = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_community);
            holder.content = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_content);
            holder.time = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_time);
            holder.up = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_up);
            holder.commentCount = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_count);
            holder.pic1 = (RoundImageView) convertView.findViewById(R.id.item_fragment_interest_replace_pic1);
            holder.pic2 = (RoundImageView) convertView.findViewById(R.id.item_fragment_interest_replace_pic2);
            holder.pic3 = (RoundImageView) convertView.findViewById(R.id.item_fragment_interest_replace_pic3);
            holder.pic4 = (RoundImageView) convertView.findViewById(R.id.item_fragment_interest_replace_pic4);
            holder.play = (ImageView) convertView.findViewById(R.id.item_fragment_interest_replace_play);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        CircleFragmentBean.CircleListsBean bean = list.get(position);
        holder.name.setText(bean.getUesrname());
        switch (bean.getSex()) {
            case "男":
                holder.gender.setText("男");
                holder.gender.setBackgroundResource(R.drawable.shape_6699ff_4_solid);
                holder.gender.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.mipmap.woman), null, null, null);
                break;
            case "女":
                holder.gender.setText("女");
                holder.gender.setBackgroundResource(R.drawable.shape_fc999b_6soild);
                holder.gender.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.mipmap.man), null, null, null);
                break;
        }
        if (TextUtils.isEmpty(bean.getFace())) {
            holder.icon.setImageResource(R.mipmap.icon_replace);
        } else
            NetRequest.loadImg(context, bean.getFace(), holder.icon);
        holder.community.setText(bean.getCommunityName());
        holder.content.setText(bean.getCircle_Content());
        holder.time.setText(handleTime(bean.getCircle_DateTime()));
        holder.up.setText(bean.getPointPraiseCount() + "");
        holder.commentCount.setText(bean.getCommentCount() + "");
        if (!TextUtils.isEmpty(bean.getCircle_SPURL())) {
            holder.pic2.setVisibility(View.GONE);
            holder.pic3.setVisibility(View.GONE);
            holder.pic4.setVisibility(View.GONE);
            holder.play.setVisibility(View.VISIBLE);
            NetRequest.loadImg(context, bean.getCircle_SPDYZT(), holder.pic1);
        } else {
            holder.play.setVisibility(View.GONE);
            handlePicture(bean.getCircle_AffixImgList(), holder.pic1, holder.pic2, holder.pic3, holder.pic4);
        }
        return convertView;
    }

    private void handlePicture(String circle_affixImgList, RoundImageView pic1, RoundImageView pic2, RoundImageView pic3, RoundImageView pic4) {
        List<String> pathList = getUrl(circle_affixImgList);

        pic1.setVisibility(View.GONE);
        pic2.setVisibility(View.GONE);
        pic3.setVisibility(View.GONE);
        pic4.setVisibility(View.GONE);
        switch (pathList.size()) {
            case 1:
                if (TextUtils.isEmpty(pathList.get(0))) {
                    pic1.setImageResource(R.mipmap.replace_hybb);
                } else {
                    NetRequest.loadImg(context, pathList.get(0), pic1);
                    pic1.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                pic1.setVisibility(View.VISIBLE);
                pic2.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(pathList.get(0))) {
                    pic1.setImageResource(R.mipmap.replace_hybb);
                } else
                    NetRequest.loadImg(context, pathList.get(0), pic1);
                if (TextUtils.isEmpty(pathList.get(1))) {
                    pic1.setImageResource(R.mipmap.replace_hybb);
                } else
                    NetRequest.loadImg(context, pathList.get(1), pic2);
                break;
            case 3:
                pic1.setVisibility(View.VISIBLE);
                pic2.setVisibility(View.VISIBLE);
                pic3.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(pathList.get(0))) {
                    pic1.setImageResource(R.mipmap.replace_hybb);
                } else
                    NetRequest.loadImg(context, pathList.get(0), pic1);
                if (TextUtils.isEmpty(pathList.get(1))) {
                    pic1.setImageResource(R.mipmap.replace_hybb);
                } else
                    NetRequest.loadImg(context, pathList.get(1), pic2);
                if (TextUtils.isEmpty(pathList.get(2))) {
                    pic1.setImageResource(R.mipmap.replace_hybb);
                } else
                    NetRequest.loadImg(context, pathList.get(2), pic3);
                break;
            case 4:
                pic1.setVisibility(View.VISIBLE);
                pic2.setVisibility(View.VISIBLE);
                pic3.setVisibility(View.VISIBLE);
                pic4.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(pathList.get(0))) {
                    pic1.setImageResource(R.mipmap.replace_hybb);
                } else
                    NetRequest.loadImg(context, pathList.get(0), pic1);
                if (TextUtils.isEmpty(pathList.get(1))) {
                    pic1.setImageResource(R.mipmap.replace_hybb);
                } else
                    NetRequest.loadImg(context, pathList.get(1), pic2);
                if (TextUtils.isEmpty(pathList.get(2))) {
                    pic1.setImageResource(R.mipmap.replace_hybb);
                } else
                    NetRequest.loadImg(context, pathList.get(2), pic3);
                if (TextUtils.isEmpty(pathList.get(3))) {
                    pic1.setImageResource(R.mipmap.replace_hybb);
                } else
                    NetRequest.loadImg(context, pathList.get(3), pic4);
                break;
        }
    }

    private String handleTime(String circle_dateTime) {
        long minute = (System.currentTimeMillis() / 1000 - Long.parseLong(circle_dateTime)) / 60;
        if (minute > 60 && minute < 1440) {
            return minute / 60 + "小时前";
        } else if (minute > 1440) {
            return minute / 1440 + "天前";
        } else
            return minute + "分钟前";
    }

    class ViewHolder {
        ImageView icon, play;
        RoundImageView pic1, pic2, pic3, pic4;
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
