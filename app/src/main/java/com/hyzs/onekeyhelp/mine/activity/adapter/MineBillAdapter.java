package com.hyzs.onekeyhelp.mine.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.activity.bean.ExpenseBean;
import com.hyzs.onekeyhelp.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class MineBillAdapter extends BaseAdapter {

    private List<ExpenseBean.AccountConsumptionListBean> list;
    private Context context;

    public MineBillAdapter(List<ExpenseBean.AccountConsumptionListBean> list, Context context) {
        this.list = list;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bill_lv,null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.item_bill_lv_text);
            holder.time = (TextView) convertView.findViewById(R.id.item_bill_lv_time);
            holder.resource = (TextView) convertView.findViewById(R.id.item_bill_lv_resource);
            convertView.setTag(holder);
        }else holder = (ViewHolder) convertView.getTag();
        ExpenseBean.AccountConsumptionListBean bean = list.get(position);
        switch (bean.getWhich()) {
            case 0:
                switch (bean.getType()) {
                    case 0:
                        holder.resource.setText("短信消费");
                        break;
                    case 1:
                        holder.resource.setText("生活求助");
                        break;
                    case 2:
                        holder.resource.setText("车辆救援");
                        break;
                    case 3:
                        holder.resource.setText("一键求助");
                        break;
                }
                break;
            case 1:
                switch (bean.getType()) {
                    case 0:
                        holder.resource.setText("提现");
                        break;
                    case 1:
                        holder.resource.setText("充值");
                        break;
                }
                break;
        }
        holder.tv.setText(bean.getMoney()+"");
        if (bean.getMoney()>0) {
            holder.tv.setTextColor(ContextCompat.getColor(context,R.color.color_1ccd9b));
        }else if (bean.getMoney()<0){
            holder.tv.setTextColor(ContextCompat.getColor(context,R.color.color_ef1111));
        }
        holder.time.setText(getTime(Long.parseLong(bean.getAddtime())));
        return convertView;
    }

    private String getTime(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(l * 1000);
    }

    class ViewHolder {
        TextView tv, time ,resource;
    }
}
