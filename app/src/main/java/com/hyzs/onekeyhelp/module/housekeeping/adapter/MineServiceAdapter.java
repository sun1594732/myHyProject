package com.hyzs.onekeyhelp.module.housekeeping.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.module.housekeeping.bean.MineServiceBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.List;


public class MineServiceAdapter extends BaseAdapter {
    private Context mContext;
    private List<MineServiceBean.LifeServicePersonalBean> list;

    public MineServiceAdapter(Context mContext, List<MineServiceBean.LifeServicePersonalBean> list) {
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
            view = View.inflate(mContext, R.layout.item_mine_service_layout, null);
            holder = new MyHolder();
            holder.icon = (RoundImageView) view.findViewById(R.id.item_mine_service_icon);
            holder.name = (TextView) view.findViewById(R.id.item_mine_service_name);
            holder.native_place = (TextView) view.findViewById(R.id.item_mine_service_native_place);
            holder.charges = (TextView) view.findViewById(R.id.item_mine_service_charges);
            holder.range = (TextView) view.findViewById(R.id.item_mine_service_range);
            holder.tag1 = (TextView) view.findViewById(R.id.item_mine_service_tag1);
            holder.tag2 = (TextView) view.findViewById(R.id.item_mine_service_tag2);
            holder.tag3 = (TextView) view.findViewById(R.id.item_mine_service_tag3);
            holder.callPhone = (ImageView) view.findViewById(R.id.item_mine_service_layout_call_phone);
            holder.chat = (ImageView) view.findViewById(R.id.item_mine_service_layout_chat);
            holder.ratingBar = (RatingBar) view.findViewById(R.id.item_mine_service_ratingbar);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        final MineServiceBean.LifeServicePersonalBean bean = list.get(position);
        holder.tag1.setVisibility(View.GONE);
        holder.tag2.setVisibility(View.GONE);
        holder.tag3.setVisibility(View.GONE);
        if (TextUtils.isEmpty(bean.getFace())) {
            holder.icon.setImageResource(R.drawable.icon_replace);
        } else NetRequest.loadImg(mContext, bean.getFace(), holder.icon);
        holder.name.setText(bean.getUesrname());
        holder.native_place.setText(bean.getNativeplace());
        holder.charges.setText(bean.getMsalary() + "元/月");
        holder.range.setText(bean.getDistance());
        if (!TextUtils.isEmpty(bean.getTag())) {
            String[] tags = bean.getTag().split(",");
            switch (tags.length) {
                case 3:
                    holder.tag3.setText(tags[2]);
                    holder.tag3.setVisibility(View.VISIBLE);
                case 2:
                    holder.tag2.setText(tags[1]);
                    holder.tag2.setVisibility(View.VISIBLE);
                case 1:
                    holder.tag1.setText(tags[0]);
                    holder.tag1.setVisibility(View.VISIBLE);
            }
        }

        holder.ratingBar.setIsIndicator(true);
        holder.ratingBar.setNumStars(bean.getRecommendIndex());

        holder.callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mContext.getApplicationContext().checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mContext, "您还没有授予打电话权限", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!TextUtils.isEmpty(bean.getPhone())) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + bean.getPhone());
                            intent.setData(data);
                            mContext.startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "对方没有设置手机号", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(bean.getPhone())) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + bean.getPhone());
                        intent.setData(data);
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "对方没有设置手机号", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreferences.getInstance(mContext).setString("Img", bean.getFace());
                NotifyMsgCountUtil.notifyMsg(bean.getUserid() + "");
                Intent intent = new Intent();
                intent.putExtra("one", bean.getUserid() + "");
                intent.putExtra("userName", bean.getUesrname());
                intent.setClass(mContext, IntentChatActivity.class);
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    private class MyHolder {
        private RoundImageView icon;
        private TextView name, native_place, charges, range, tag1, tag2, tag3;
        private ImageView callPhone, chat;
        private RatingBar ratingBar;
    }
}
