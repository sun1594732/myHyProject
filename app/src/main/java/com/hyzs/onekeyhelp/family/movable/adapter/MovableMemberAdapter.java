package com.hyzs.onekeyhelp.family.movable.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.movable.bean.MovableMemberBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/3/20.
 */

public class MovableMemberAdapter extends BaseAdapter {

    private Context context;
    private MovableMemberBean movableMemberBean;
    private List<MovableMemberBean.EventMemberListBean> list;

    public MovableMemberAdapter(Context context,MovableMemberBean movableMemberBean) {
        this.context = context;
        this.movableMemberBean=movableMemberBean;
        this.list=movableMemberBean.getEventMemberList();
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public MovableMemberBean.EventMemberListBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        MyHolder holder = null;
        if (view == null) {
            view = View.inflate(context,R.layout.item_movable_member, null);
            holder=new MyHolder();
            holder.icon = (CircleImageView) view.findViewById(R.id.item_mavable_member_icon);
            holder.rnc = (ImageView) view.findViewById(R.id.item_mavable_member_rnc);
            holder.chat = (ImageView) view.findViewById(R.id.item_mavable_member_chat);
            holder.juweihui = (ImageView) view.findViewById(R.id.item_mavable_member_juweihui);
            holder.name = (TextView) view.findViewById(R.id.item_mavable_member_name);
            holder.sponsor = (TextView) view.findViewById(R.id.item_mavable_member_sponsor);
            holder.num = (TextView) view.findViewById(R.id.item_mavable_member_num);
            holder.zheng = (TextView) view.findViewById(R.id.item_mavable_member_zheng);
            holder.district = (TextView) view.findViewById(R.id.item_mavable_member_district);
            view.setTag(holder);
        }else{
            holder = (MyHolder) view.getTag();
        }
        final MovableMemberBean.EventMemberListBean bean = list.get(position);
        if (!TextUtils.isEmpty(bean.getAvatar())) {
            NetRequest.loadImg(context,bean.getAvatar(),holder.icon);
        }
        if ("0".equals(bean.getEM_Sponsor().trim())) {
            holder.sponsor.setVisibility(View.INVISIBLE);
            holder.num.setText(longToTime(Long.parseLong(bean.getEM_Datetime())));
        }else{
            holder.sponsor.setVisibility(View.VISIBLE);
            holder.num.setText("全部成员"+movableMemberBean.getApplyCount()+"人");
        }
        if ("0".equals(bean.getEM_SponsorType().trim())) {
            holder.juweihui.setVisibility(View.GONE);
        }else holder.juweihui.setVisibility(View.VISIBLE);
        holder.name.setText(bean.getNickName());
        if (0 == bean.getRNC()) {
            holder.rnc.setVisibility(View.GONE);
        }else holder.rnc.setVisibility(View.VISIBLE);
        if (0 == bean.getNCC()) {
            holder.zheng.setVisibility(View.GONE);
        }else holder.zheng.setVisibility(View.VISIBLE);
        holder.district.setText(bean.getFloorNumber());
        if (Integer.parseInt(MySharedPreferences.getUserId(context)) == bean.getEM_UserID()) {
            holder.chat.setVisibility(View.INVISIBLE);
        }
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("one", bean.getEM_UserID() + "");
                intent.putExtra("userName", bean.getNickName());
                intent.setClass(context, IntentChatActivity.class);
                context.startActivity(intent);
            }
        });
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MineHomeActivity.class);
                intent.putExtra("targetUserId",bean.getEM_UserID() );
                context.startActivity(intent);
            }
        });
        return view;
    }

    private String longToTime(long time){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        return format.format(time*1000);
    }

    private class MyHolder{
        private CircleImageView icon;
        private TextView sponsor,name,num,zheng,district;
        private ImageView rnc,chat,juweihui;
    }
}
