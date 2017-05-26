package com.hyzs.onekeyhelp.module.housekeeping.search.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.module.housekeeping.search.bean.RightTabBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */

public class SearchRightAdapter extends BaseAdapter {
    private Context mContext;
    private List<RightTabBean.LifeServiceSPBean> list;

    public SearchRightAdapter(Context mContext, List<RightTabBean.LifeServiceSPBean> list) {
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
            view = View.inflate(mContext, R.layout.item_search_right, null);
            holder = new MyHolder();
            holder.textView = (TextView) view.findViewById(R.id.item_search_right_text);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        holder.textView.setText(list.get(position).getSP_Name());
        return view;
    }

    private class MyHolder {
        private TextView textView;
    }
}
