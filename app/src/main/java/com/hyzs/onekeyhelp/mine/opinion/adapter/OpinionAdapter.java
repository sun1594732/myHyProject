package com.hyzs.onekeyhelp.mine.opinion.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.opinion.bean.OpinionBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/21.
 */

public class OpinionAdapter extends BaseAdapter {

    private Context mContext;
    private List<OpinionBean.SfcBean> list;

    public OpinionAdapter(Context mContext, List<OpinionBean.SfcBean> list) {
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
            view = View.inflate(mContext, R.layout.pop_textview_layout, null);
            holder = new MyHolder();
            holder.textView = (TextView) view.findViewById(R.id.pop_textview_layout_textview);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        holder.textView.setText(list.get(position).getSFC_Name());
        return view;
    }
    private class MyHolder{
        TextView textView;
    }
}
