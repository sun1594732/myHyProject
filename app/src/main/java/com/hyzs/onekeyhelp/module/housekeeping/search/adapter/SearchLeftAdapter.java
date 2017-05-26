package com.hyzs.onekeyhelp.module.housekeeping.search.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.module.housekeeping.search.bean.TabBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */

public class SearchLeftAdapter extends BaseAdapter {
    private Context mContext;
    private List<TabBean> list;

    public SearchLeftAdapter(Context mContext, List<TabBean> list) {
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
            view = View.inflate(mContext, R.layout.item_search_left, null);
            holder = new MyHolder();
            holder.line = view.findViewById(R.id.item_search_left_line);
            holder.textView = (TextView) view.findViewById(R.id.item_search_left_text);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        TabBean bean = list.get(position);
        holder.textView.setText(bean.getTab());
        if (bean.getIsCheck() == 0) {
            holder.line.setVisibility(View.INVISIBLE);
            Drawable drawable = ContextCompat.getDrawable(mContext, bean.getImg());
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.textView.setCompoundDrawables(drawable, null, null, null);
            holder.textView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.color_f4));
        } else {
            holder.line.setVisibility(View.VISIBLE);
            Drawable drawable = ContextCompat.getDrawable(mContext, bean.getImg());
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.textView.setCompoundDrawables(drawable, null, null, null);
            holder.textView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.color_f));
        }

        return view;
    }

    public class MyHolder {
        public View line;
        public TextView textView;
    }
}
