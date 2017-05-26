package com.hyzs.onekeyhelp.module.housekeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.module.housekeeping.bean.ProjectListBean;

import java.util.List;


public class ProjectListGridAdapter extends BaseAdapter {
    private static List<ProjectListBean.LifeServiceSPBean> list;
    private Context context;
    static int array[];
    private static StringBuilder sb;

    public ProjectListGridAdapter(List<ProjectListBean.LifeServiceSPBean> list, Context context) {
        this.list = list;
        this.context = context;
        sb = new StringBuilder();
        array = new int[list.size()];
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
        final viewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_project_list, null);
            holder = new viewHolder();
            holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
            convertView.setTag(holder);
        } else holder = (viewHolder) convertView.getTag();
        ProjectListBean.LifeServiceSPBean bean = list.get(position);
        holder.cb.setText(bean.getSP_Name());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cb.isChecked()) {
                    array[position] = 1;
                } else array[position] = 0;
            }
        });

        return convertView;
    }

    class viewHolder {
        CheckBox cb;
    }

    public static String getProject() {
        for (int i = 0; i < array.length; i++) {
            if (array[i]==1) {
                sb.append(list.get(i).getID()+",");
            }
        }
        String s = sb.toString();
        return s.substring(0,s.length()-1);
    }
}
