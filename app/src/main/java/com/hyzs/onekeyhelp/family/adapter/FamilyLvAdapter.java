package com.hyzs.onekeyhelp.family.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.IntentGroupChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.bean.FamilyListBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by wubin on 2017/4/17.
 */

public class FamilyLvAdapter extends BaseAdapter {

    private Context context;
    private List<FamilyListBean.UsersBean> list;

    public FamilyLvAdapter(Context context, List<FamilyListBean.UsersBean> list) {
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
    public View getView(int position, View view, ViewGroup parent) {
        MyHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_family_lv, null);
            holder = new MyHolder();
            holder.chat = (ImageView) view.findViewById(R.id.item_family_lv_chat);
            holder.icon = (RoundImageView) view.findViewById(R.id.item_family_lv_icon);
            holder.name = (TextView) view.findViewById(R.id.item_family_lv_name);
            holder.desc = (TextView) view.findViewById(R.id.item_family_lv_desc);
            holder.number = (TextView) view.findViewById(R.id.item_family_lv_number);
            holder.time = (TextView) view.findViewById(R.id.item_family_lv_time);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        final FamilyListBean.UsersBean bean = list.get(position);
        if (TextUtils.isEmpty(bean.getFG_Face())) {
            holder.icon.setImageResource(R.mipmap.icon_replace);
        } else NetRequest.loadImg(context, bean.getFG_Face(), holder.icon);
        holder.name.setText(bean.getFG_GroupName());
        holder.desc.setText(bean.getFG_Desc());
        holder.number.setText("家庭人数 : " + bean.getFG_PeopleCount());
        holder.time.setText(timeChange(Long.parseLong(bean.getFG_AddTime())));
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IntentGroupChatActivity.class);
                intent.putExtra("groupId", bean.getFG_GroupID());
                intent.putExtra("groupName", bean.getFG_GroupName());
                context.startActivity(intent);
            }
        });
        return view;
    }

    private String timeChange(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(time*1000);
    }


    private class MyHolder {
        private ImageView chat;
        private RoundImageView icon;
        private TextView name, desc, number, time;
    }
}
