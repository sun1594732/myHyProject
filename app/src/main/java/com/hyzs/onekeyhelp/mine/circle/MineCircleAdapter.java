package com.hyzs.onekeyhelp.mine.circle;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.mine.circle.MineCricleBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MineCircleAdapter extends BaseAdapter {
    private Context context;
    private List<MineCricleBean.PersonalCenterMyCircleBean> list;

    public MineCircleAdapter(Context context, List<MineCricleBean.PersonalCenterMyCircleBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_mine_cricle,null);
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
            holder.CircleName = (TextView) convertView.findViewById(R.id.item_fragment_interest_replace_CircleName);
            holder.del = (TextView) convertView.findViewById(R.id.item_mine_circle_del);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
       final MineCricleBean.PersonalCenterMyCircleBean bean = list.get(position);
        holder.name.setText(bean.getUesrname());
        holder.CircleName.setText("["+bean.getCircleName()+"]");
        holder.CircleName.setVisibility(View.VISIBLE);
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
        NetRequest.loadImg(context, bean.getFace(), holder.icon);
        holder.community.setText(bean.getCommunityName());
        holder.content.setText(bean.getCircle_Content());
        holder.time.setText(handleTime(bean.getCircle_DateTime()));
        holder.up.setText(bean.getPointPraiseCount() + "");
        holder.commentCount.setText(bean.getCommentCount() + "");
        handlePicture(bean.getCircle_AffixImgList(), holder.pic1, holder.pic2, holder.pic3, holder.pic4);
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> param = new ArrayMap<String, String>();
                param.put("currId", MySharedPreferences.getUserId(context));
                param.put("type", "1");
                param.put("T_ID", bean.getID()+"");
                NetRequest.ParamPostRequest(PortUtil.MyDoDel, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        DataStatusBean bean1 = new Gson().fromJson(UrlDecodeUtil.urlCode(data),DataStatusBean.class);
                        if ("10000".equals(bean1.getCode())) {
                            list.remove(position);
                            notifyDataSetChanged();
                        }else ToastSingleUtil.showToast(context,bean1.getMessage());
                    }
                });
            }
        });
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
        TextView name, gender, community, content, time, up, commentCount,CircleName,del;
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
