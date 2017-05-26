package com.hyzs.onekeyhelp.contact.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hyzs.onekeyhelp.contact.bean.ContactListEntity;

import java.util.List;

/**
 * Created by wubin on 2017/4/10.
 */

public class BatchAddAdapter extends BaseAdapter {
    private List<ContactListEntity.ContactListBean> list;
    private Context context;

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
        return null;
    }
}
