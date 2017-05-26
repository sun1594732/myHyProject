package com.hyzs.onekeyhelp.module.housekeeping.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.module.housekeeping.bean.ServiceProjectListBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ServiceProjectListAdapter extends RecyclerView.Adapter<ServiceProjectListAdapter.MyHolder> {

    private Context mContext;
    private List<ServiceProjectListBean.LifeServiceSPListBean> list;
    private ServiceProjectListClickListener listener;

    public ServiceProjectListAdapter(Context mContext, List<ServiceProjectListBean.LifeServiceSPListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.item_mine_service_project_layout
                                , parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(v, position);
            }
        });
        holder.original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ServiceProjectListBean.LifeServiceSPListBean bean = list.get(position);
        if (TextUtils.isEmpty(bean.getSPP_Img())) {
            holder.icon.setImageResource(R.drawable.icon_replace);
        } else NetRequest.loadImg(mContext, bean.getSPP_Img(), holder.icon);
        holder.name.setText(bean.getSPP_Name());
        holder.content.setText(bean.getSPP_Desc());
        holder.curr_price.setText(bean.getSPP_Price() + "");
        holder.original_price.setText(bean.getSPP_OriginalCost() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setListener(ServiceProjectListClickListener listener) {
        this.listener = listener;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private RoundImageView icon;
        private TextView name, content, curr_price, original_price;

        public MyHolder(View view) {
            super(view);
            icon = (RoundImageView) view.findViewById(R.id.item_mine_service_project_icon);
            name = (TextView) view.findViewById(R.id.item_mine_service_project_name);
            content = (TextView) view.findViewById(R.id.item_mine_service_project_content);
            curr_price = (TextView) view.findViewById(R.id.item_mine_service_project_curr_price);
            original_price = (TextView) view.findViewById(R.id.item_mine_service_project_original_price);
        }
    }

    public interface ServiceProjectListClickListener {
        public void click(View view, int position);
    }
}