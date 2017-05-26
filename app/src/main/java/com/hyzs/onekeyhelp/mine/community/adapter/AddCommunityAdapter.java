package com.hyzs.onekeyhelp.mine.community.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.community.bean.AddCommunityBean;

import java.util.List;

public class AddCommunityAdapter extends BaseAdapter {
    private static final String TAG = "AddCommunityAdapter";
    private Context mContext;
    private List<AddCommunityBean.SearchCommunityBean> list;
    public AddCommunityAdapter(Context mContext, List<AddCommunityBean.SearchCommunityBean>list) {
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
            view = View.inflate(mContext, R.layout.item_add_community, null);
            holder = new MyHolder();
            holder.check = (ImageView) view.findViewById(R.id.item_add_community_checkbox);
            holder.name = (TextView) view.findViewById(R.id.item_add_community_name);
            holder.address = (TextView) view.findViewById(R.id.item_add_community_address);
            holder.distance = (TextView) view.findViewById(R.id.item_add_community_distance);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        AddCommunityBean.SearchCommunityBean bean = list.get(position);
        holder.name.setText(bean.getCommunityName());
        holder.address.setText(bean.getGPSDesc());
        holder.distance.setText(bean.getDistance());
        if (0 == bean.getIsCheck()) {
            holder.check.setImageResource(R.mipmap.add_community_unchecked);
        }else holder.check.setImageResource(R.mipmap.add_community_checked);
        return view;
    }

    public class MyHolder{
        public  ImageView check;
        public  TextView name,address,distance;
    }
}
