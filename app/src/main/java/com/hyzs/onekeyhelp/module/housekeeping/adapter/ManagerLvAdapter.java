package com.hyzs.onekeyhelp.module.housekeeping.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.module.housekeeping.bean.ManagerLvBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.List;
import java.util.Map;


public class ManagerLvAdapter extends BaseAdapter {
    List<ManagerLvBean.LifeServiceSPListBean> list;
    private Context context;
    boolean isTop;

    public ManagerLvAdapter(List<ManagerLvBean.LifeServiceSPListBean> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final viewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_manager_lv, null);
            holder = new viewHolder();
            holder.status = (TextView) convertView.findViewById(R.id.item_manager_status);
            holder.status1 = (TextView) convertView.findViewById(R.id.item_manager_status2);
            holder.name = (TextView) convertView.findViewById(R.id.item_manager_name);
            holder.money = (TextView) convertView.findViewById(R.id.item_manager_money);
            holder.btn = (TextView) convertView.findViewById(R.id.item_manager_btn);
            convertView.setTag(holder);
        } else holder = (viewHolder) convertView.getTag();
        final ManagerLvBean.LifeServiceSPListBean bean = list.get(position);
        holder.status1.setVisibility(View.GONE);
        switch (bean.getSPP_ApprovalState()) {
            case 0:
                holder.status.setText("审核中");
                holder.status.setTextColor(ContextCompat.getColor(context, R.color.color_f96840));
                holder.status.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(context, R.mipmap.blank), null, null);
                holder.btn.setVisibility(View.INVISIBLE);
                break;
            case 1:
                holder.status.setText("审核通过");
                holder.status1.setVisibility(View.VISIBLE);
                holder.status.setTextColor(ContextCompat.getColor(context, R.color.color_1ccd9b));
                holder.status.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(context, R.mipmap.task), null, null);
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, String> param = new ArrayMap<>();
                        param.put("currId", MySharedPreferences.getUserId(context));
                        param.put("SPP_ID", bean.getID() + "");
                        NetRequest.ParamPostRequest(PortUtil.LifeServiceSPUseStatusSet, param, new NetRequest.getDataCallback() {
                            @Override
                            public void getData(String data) {
                                DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                                if ("10000".equals(bean.getCode())) {
                                    if (isTop) {
                                        holder.status1.setText("已下架");
                                        holder.btn.setText("上架");
                                        isTop = false;
                                    } else {
                                        isTop = true;
                                        holder.status1.setText("已上架");
                                        holder.btn.setText("下架");
                                    }
                                }
                            }
                        });
                    }
                });
                switch (bean.getSPP_UseState()) {
                    case 0:
                        isTop = true;
                        holder.status1.setText("已上架");
                        holder.btn.setText("下架");
                        break;
                    case 1:
                        isTop = false;
                        holder.status1.setText("已下架");
                        holder.btn.setText("上架");
                        break;
                }
                break;
            case 2:
                holder.status.setText("审核未通过");
                holder.status.setTextColor(ContextCompat.getColor(context, R.color.color_9));
                holder.status.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(context, R.mipmap.delete), null, null);
                holder.btn.setText("删除");
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, String> param = new ArrayMap<>();
                        param.put("currId", MySharedPreferences.getUserId(context));
                        param.put("SPP_ID", bean.getID() + "");
                        NetRequest.ParamPostRequest(PortUtil.LifeServiceSPDel, param, new NetRequest.getDataCallback() {
                            @Override
                            public void getData(String data) {
                                DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                                if ("10000".equals(bean.getCode())) {
                                    list.remove(position);
                                    ManagerLvAdapter.this.notifyDataSetChanged();
                                } else ToastSingleUtil.showToast(context, bean.getMessage());
                            }
                        });
                    }
                });
                break;
        }
        holder.name.setText(bean.getSPP_Name());
        holder.money.setText("金额  " + bean.getSPP_Price() + " / 次");
        return convertView;
    }

    class viewHolder {
        TextView status, status1, name, money, btn;
    }
}
