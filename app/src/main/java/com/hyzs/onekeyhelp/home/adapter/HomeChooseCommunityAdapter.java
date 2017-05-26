package com.hyzs.onekeyhelp.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.home.bean.HomeCommunityListBean;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.List;

/**
 * Created by wubin on 2017/4/7.
 */

public class HomeChooseCommunityAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public HomeChooseCommunityAdapter(List<String> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choose_community, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.item_choose_community_tv);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.textView.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
