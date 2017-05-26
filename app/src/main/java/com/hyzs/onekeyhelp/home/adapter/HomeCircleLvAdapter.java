package com.hyzs.onekeyhelp.home.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.home.bean.HomePageBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeCircleLvAdapter extends BaseAdapter {
    private List<HomePageBean.CircleHotBean> list;
    private Context context;

    public HomeCircleLvAdapter(List<HomePageBean.CircleHotBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_circle, null);
            holder = new ViewHolder();
            holder.icon = (RoundImageView) convertView.findViewById(R.id.item_home_circle_icon);
            holder.name = (TextView) convertView.findViewById(R.id.item_home_circle_name);
            holder.title = (TextView) convertView.findViewById(R.id.item_home_circle_title);
            holder.hotCount = (TextView) convertView.findViewById(R.id.item_home_circle_hotCount);
            holder.type = (TextView) convertView.findViewById(R.id.item_home_circle_type);
            holder.pariseCount = (TextView) convertView.findViewById(R.id.item_home_circle_praiseCount);
            holder.pic1 = (RoundImageView) convertView.findViewById(R.id.item_home_circle_pic1);
            holder.pic2 = (RoundImageView) convertView.findViewById(R.id.item_home_circle_pic2);
            holder.pic3 = (RoundImageView) convertView.findViewById(R.id.item_home_circle_pic3);
            holder.pic4 = (RoundImageView) convertView.findViewById(R.id.item_home_circle_pic4);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        HomePageBean.CircleHotBean bean = list.get(position);
        holder.title.setText(bean.getCircle_Content());
        holder.name.setText(bean.getUesrname());
        holder.hotCount.setText("人气  "+bean.getHot());
        holder.type.setText(bean.getCircleName());
        holder.pariseCount.setText(bean.getCircle_Count() + "");
        if (!TextUtils.isEmpty(bean.getFace())) {
            NetRequest.loadImg(context, bean.getFace(), holder.icon);
        }
        handlePicture(bean.getCircle_AffixImgList(),holder.pic1,holder.pic2,holder.pic3,holder.pic4);
        return convertView;
    }

    class ViewHolder {
        RoundImageView icon, pic1, pic2, pic3, pic4;
        TextView name, title, hotCount, type, pariseCount;
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
}
