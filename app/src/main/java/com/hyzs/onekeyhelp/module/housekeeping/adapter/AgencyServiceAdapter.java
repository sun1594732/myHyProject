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
import android.widget.TextView;
import android.widget.Toast;

import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.module.housekeeping.bean.AgencyServiceBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.List;


/**
 * Created by Administrator on 2017/5/3.
 */

public class AgencyServiceAdapter extends BaseAdapter {
    private Context mContext;
    private List<AgencyServiceBean.LifeServiceOrganizationBean> list;

    public AgencyServiceAdapter(Context mContext, List<AgencyServiceBean.LifeServiceOrganizationBean> list) {
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
            view = View.inflate(mContext, R.layout.item_agency_service_layout, null);
            holder = new MyHolder();
            holder.icon = (RoundImageView) view.findViewById(R.id.item_agency_service_layout_icon);
            holder.name = (TextView) view.findViewById(R.id.item_agency_service_layout_name);
            holder.distance = (TextView) view.findViewById(R.id.item_agency_service_layout_distance);
            holder.content = (TextView) view.findViewById(R.id.item_agency_service_layout_content);
            holder.phone = (TextView) view.findViewById(R.id.item_agency_service_layout_phone);
            holder.address = (TextView) view.findViewById(R.id.item_agency_service_layout_address);
            holder.callPhone = (ImageView) view.findViewById(R.id.item_agency_service_layout_call_phone);
            holder.chat = (ImageView) view.findViewById(R.id.item_agency_service_layout_chat);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        final AgencyServiceBean.LifeServiceOrganizationBean bean = list.get(position);
        if (TextUtils.isEmpty(bean.getLogo())) {
            holder.icon.setImageResource(R.drawable.icon_replace);
        } else NetRequest.loadImg(mContext, bean.getLogo(), holder.icon);
        holder.name.setText(bean.getName());
        holder.distance.setText(bean.getDistance());
        holder.content.setText(bean.getIntro());
        holder.phone.setText(bean.getTel());
        holder.address.setText(bean.getAddress());

        holder.callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mContext.getApplicationContext().checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mContext, "您还没有授予打电话权限", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!TextUtils.isEmpty(bean.getTel())) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + bean.getTel());
                            intent.setData(data);
                            mContext.startActivity(intent);
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(bean.getTel())) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + bean.getTel());
                        intent.setData(data);
                        mContext.startActivity(intent);
                    }
                }
            }
        });

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreferences.getInstance(mContext).setString("Img", bean.getLogo());
                NotifyMsgCountUtil.notifyMsg(bean.getUserid() + "");
                Intent intent = new Intent();
                intent.putExtra("one", bean.getUserid() + "");
                intent.putExtra("userName", bean.getName());
                intent.setClass(mContext, IntentChatActivity.class);
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    private class MyHolder {
        private RoundImageView icon;
        private TextView name, distance, content, phone, address;
        private ImageView callPhone, chat;
    }
}
