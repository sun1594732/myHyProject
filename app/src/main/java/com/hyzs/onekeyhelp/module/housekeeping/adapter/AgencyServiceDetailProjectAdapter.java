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
import com.hyzs.onekeyhelp.module.housekeeping.bean.AgencyServiceDetailBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.List;

public class AgencyServiceDetailProjectAdapter extends RecyclerView.Adapter<AgencyServiceDetailProjectAdapter.MyHolder> {
    private Context mContext;
    private List<AgencyServiceDetailBean.ServiceProjectBean> list;
    private ServiceProjectClickListener listener;

    public AgencyServiceDetailProjectAdapter(Context mContext, List<AgencyServiceDetailBean.ServiceProjectBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_mine_service_project_layout
                        , parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(v,position);
            }
        });
        holder.original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        AgencyServiceDetailBean.ServiceProjectBean bean = list.get(position);
        if (TextUtils.isEmpty(bean.getSPP_Img())) {
            holder.icon.setImageResource(R.drawable.icon_replace);
        } else NetRequest.loadImg(mContext, bean.getSPP_Img(), holder.icon);
        holder.name.setText(bean.getSPP_Name());
        holder.content.setText(bean.getSPP_Desc());
        holder.curr_price.setText(bean.getSPP_Price() + "/次");
        holder.original_price.setText(bean.getSPP_OriginalCost() + "");
    }

    @Override
    public int getItemCount() {
        return list.size() >= 2 ? 2 : list.size();
    }

    public void setListener(ServiceProjectClickListener listener) {
        this.listener = listener;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
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

    public interface ServiceProjectClickListener {
        public void click(View view,int position);
    }
}
