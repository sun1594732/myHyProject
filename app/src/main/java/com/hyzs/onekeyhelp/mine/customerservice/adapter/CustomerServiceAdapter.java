package com.hyzs.onekeyhelp.mine.customerservice.adapter;

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
import com.hyzs.onekeyhelp.mine.customerservice.bean.CustomerServiceBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class CustomerServiceAdapter extends BaseAdapter {

    private Context mContext;
    private List<CustomerServiceBean.ServiceClassBean> list;
    private static final int BAIDU_READ_PHONE_STATE = 100;
    public CustomerServiceAdapter(Context mContext, List<CustomerServiceBean.ServiceClassBean> list) {
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
            view = View.inflate(mContext, R.layout.item_customerservice, null);
            holder = new MyHolder();
            holder.icon = (RoundImageView) view.findViewById(R.id.item_customerservice_icon);
            holder.name = (TextView) view.findViewById(R.id.item_customerservice_name);
            holder.phone = (TextView) view.findViewById(R.id.item_customerservice_phone);
            holder.time = (TextView) view.findViewById(R.id.item_customerservice_time);
            holder.chat = (ImageView) view.findViewById(R.id.item_customerservice_chat);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        final CustomerServiceBean.ServiceClassBean bean = list.get(position);
        if (TextUtils.isEmpty(bean.getS_Face())) {
            holder.icon.setImageResource(R.mipmap.service);
        }else NetRequest.loadImg(mContext,bean.getS_Face(),holder.icon);
        holder.name.setText(bean.getS_Name());
        holder.phone.setText(bean.getS_Tel());
        holder.time.setText(bean.getS_WorkTime());
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreferences.getInstance(mContext).setString("Img", bean.getS_Face());
                NotifyMsgCountUtil.notifyMsg(bean.getS_UserID() + "");
                Intent intent = new Intent();
                intent.putExtra("one", bean.getS_UserID()+"");
                intent.putExtra("userName", bean.getS_Name());
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
                        Toast.makeText(mContext, "请设置打电话权限", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!TextUtils.isEmpty(bean.getS_Tel())) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + bean.getS_Tel());
                            intent.setData(data);
                            mContext.startActivity(intent);
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(bean.getS_Tel())) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + bean.getS_Tel());
                        intent.setData(data);
                        mContext.startActivity(intent);
                    }
                }
            }
        });

        return view;
    }

    private class MyHolder {
        private RoundImageView icon;
        private TextView name, phone, time;
        private ImageView chat;
    }
}
