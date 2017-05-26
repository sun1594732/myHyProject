package com.hyzs.onekeyhelp.help.adapter;

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
import android.widget.TextView;
import android.widget.Toast;

import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.help.bean.BegDetailListBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class HelpDetailListAdapter extends BaseAdapter {
    private Context mContext;
    private List<BegDetailListBean.ComplateSeekHelpUserListCommonBean> list;

    public HelpDetailListAdapter(Context mContext, List<BegDetailListBean.ComplateSeekHelpUserListCommonBean> list) {
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
            view = View.inflate(mContext, R.layout.item_help_detail, null);
            holder = new MyHolder();
            holder.icon = (RoundImageView) view.findViewById(R.id.item_help_detail_icon);
            holder.name = (TextView) view.findViewById(R.id.item_help_detail_name);
            holder.zheng = (TextView) view.findViewById(R.id.item_help_detail_zheng);
            holder.time = (TextView) view.findViewById(R.id.item_help_detail_time);
            holder.status = (TextView) view.findViewById(R.id.item_help_detail_status);
            holder.identity = (ImageView) view.findViewById(R.id.item_help_detail_idendity);
            holder.chat = (ImageView) view.findViewById(R.id.item_help_detail_chat);
            holder.phone = (ImageView) view.findViewById(R.id.item_help_detail_phone);
            view.setTag(holder);
        } else {
            holder = (MyHolder) view.getTag();
        }
        final BegDetailListBean.ComplateSeekHelpUserListCommonBean bean = list.get(position);
        if (!TextUtils.isEmpty(bean.getFace())) {
            NetRequest.loadImg(mContext, bean.getFace(), holder.icon);
        } else holder.icon.setImageResource(R.mipmap.icon_replace);
        holder.name.setText(bean.getUesrname());
        if (0 == bean.getNCCS()) {
            holder.zheng.setVisibility(View.GONE);
        } else holder.zheng.setVisibility(View.VISIBLE);
        if (0 == bean.getRNA()) {
            holder.identity.setVisibility(View.GONE);
        } else holder.identity.setVisibility(View.VISIBLE);
        holder.time.setText(bean.getHelp_WCRQ());
        if ("已完成".equals(bean.getHelpState().trim())) {
            holder.status.setText("等待确认");
        } else holder.status.setText("已确认");
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("one", bean.getUserid() + "");
                intent.putExtra("userName", bean.getUesrname());
                intent.setClass(mContext, IntentChatActivity.class);
                mContext.startActivity(intent);
            }
        });
        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mContext.getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                        Toast.makeText(mContext, "您还没有授予打电话权限", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!TextUtils.isEmpty(bean.getPhone() + "")) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + bean.getPhone());
                            intent.setData(data);
                            mContext.startActivity(intent);
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(bean.getPhone() + "")) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + bean.getPhone());
                        intent.setData(data);
                        mContext.startActivity(intent);
                    }
                }
            }
        });
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySharedPreferences.isLogin(mContext)) {
                    ToastUtils.showShort(mContext, "请先登录.");
                    return;
                }
                Intent intent = new Intent(mContext, MineHomeActivity.class);
                intent.putExtra("targetUserId", bean.getUserid()+"");
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    private String getTime(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(l * 1000);
    }

    private class MyHolder {
        private RoundImageView icon;
        private TextView name, zheng, time, status;
        private ImageView identity, chat, phone;
    }
}
