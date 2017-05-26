package com.hyzs.onekeyhelp.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.circle.bean.CircleTypeBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;

import java.util.List;

public class CircleSelectAdapter extends BaseAdapter {
    private Context mContext;
    private List<CircleTypeBean.CircleTypesBean> list;

    public CircleSelectAdapter(Context mContext, List<CircleTypeBean.CircleTypesBean>list) {
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
        view = View.inflate(mContext, R.layout.item_circle_select, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_circle_select_icon);
        NetRequest.loadImg(mContext, list.get(position).getLogo(), imageView);
        return view;
    }
}
