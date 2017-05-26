package com.hyzs.onekeyhelp.carresuce.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.carresuce.bean.ResuceTroopListBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/30.
 */

public class MyResuceTroopsFragmentItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<ResuceTroopListBean.VehicleRescueTeamListBean> list;

    public MyResuceTroopsFragmentItemAdapter(Context mContext, List<ResuceTroopListBean.VehicleRescueTeamListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final viewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_resuce__troops_fragment, null);
            holder = new viewHolder();
            holder.troopName = (TextView) view.findViewById(R.id.item_resuce_troops_name);
            holder.troopLocation = (TextView) view.findViewById(R.id.item_resuce_troops_location);
            holder.troopPosition = (TextView) view.findViewById(R.id.item_resuce_troops_position);
            holder.troopPhone = (TextView) view.findViewById(R.id.item_resuce_troops_phone);
            holder.troopJob = (TextView) view.findViewById(R.id.item_resuce_troops_job);
            holder.troopCall = (ImageView) view.findViewById(R.id.item_resuce_troops_call);
            holder.troopIcon = (ImageView) view.findViewById(R.id.item_resuce_troops_icon);
            view.setTag(holder);
        } else holder = (viewHolder) view.getTag();
        ResuceTroopListBean.VehicleRescueTeamListBean bean = list.get(i);
        holder.troopName.setText(bean.getVRT_Name());
        holder.troopJob.setText(bean.getVRT_Project());
        holder.troopPhone.setText(bean.getVRT_Phone());
        holder.troopLocation.setText(bean.getVRT_Range());
        holder.troopPosition.setText(bean.getVRT_Address());
        if (!TextUtils.isEmpty(bean.getVRT_Image())) {
            NetRequest.loadImg(mContext, bean.getVRT_Image(), holder.troopIcon);
        } else holder.troopIcon.setImageResource(R.mipmap.replace_hybb);
        holder.troopCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(holder.troopPhone.getText().toString())) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + holder.troopPhone.getText().toString());
                    intent.setData(data);
                    mContext.startActivity(intent);
                }
            }
        });
        return view;
    }

    class viewHolder {
        TextView troopName, troopLocation, troopPosition, troopPhone, troopJob;
        ImageView troopCall, troopIcon;
    }
}
