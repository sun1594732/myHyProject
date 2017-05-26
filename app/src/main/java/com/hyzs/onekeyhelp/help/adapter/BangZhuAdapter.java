package com.hyzs.onekeyhelp.help.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.carresuce.bean.MyhelpItemBean;
import com.hyzs.onekeyhelp.help.activity.HelpDetailActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class BangZhuAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyhelpItemBean.VehicleRescueMyHelpListBean> list;

    public BangZhuAdapter(Context mContext, List<MyhelpItemBean.VehicleRescueMyHelpListBean> list) {
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
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_bang_zhu, null);
            holder = new ViewHolder();
            holder.detail = (ImageView) view.findViewById(R.id.item_bang_zhu_detail);
            holder.identity = (ImageView) view.findViewById(R.id.item_bang_zhu_identity);
            holder.icon = (RoundImageView) view.findViewById(R.id.item_bang_zhu_icon);
            holder.name = (TextView) view.findViewById(R.id.item_bang_zhu_name);
            holder.zheng = (TextView) view.findViewById(R.id.item_bang_zhu_zheng);
            holder.time = (TextView) view.findViewById(R.id.item_bang_zhu_time);
            holder.status = (TextView) view.findViewById(R.id.item_bang_zhu_status);
            holder.read = (CircleImageView) view.findViewById(R.id.item_bang_zhu_read);
            holder.shang = (TextView) view.findViewById(R.id.item_contact_search_shang);
            holder.fu = (TextView) view.findViewById(R.id.item_contact_search_fu);
            holder.wu = (TextView) view.findViewById(R.id.item_contact_search_wu);
            holder.jiu = (TextView) view.findViewById(R.id.item_contact_search_jiu);
            holder.ke = (TextView) view.findViewById(R.id.item_contact_search_ke);
            holder.jie = (TextView) view.findViewById(R.id.item_contact_search_jie);
            holder.lin = (TextView) view.findViewById(R.id.item_contact_search_lin);
            holder.jin = (TextView) view.findViewById(R.id.item_contact_search_jin);
            holder.wei = (TextView) view.findViewById(R.id.item_contact_search_wei);
            holder.jia = (TextView) view.findViewById(R.id.item_contact_search_jia);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.shang.setVisibility(View.GONE);
        holder.fu.setVisibility(View.GONE);
        holder.wu.setVisibility(View.GONE);
        holder.jiu.setVisibility(View.GONE);
        holder.ke.setVisibility(View.GONE);
        holder.jie.setVisibility(View.GONE);
        holder.lin.setVisibility(View.GONE);
        holder.jin.setVisibility(View.GONE);
        holder.wei.setVisibility(View.GONE);
        holder.jia.setVisibility(View.GONE);
        final MyhelpItemBean.VehicleRescueMyHelpListBean bean = list.get(position);
        if (!TextUtils.isEmpty(bean.getIdentityMark())) {
            String[] s = bean.getIdentityMark().split(",");
            for (int i = 0; i < s.length; i++) {
                switch (s[i].trim()) {
                    case "1":
                        holder.shang.setVisibility(View.VISIBLE);
                        break;
                    case "2":
                        holder.fu.setVisibility(View.VISIBLE);
                        break;
                    case "3":
                        holder.wu.setVisibility(View.VISIBLE);
                        break;
                    case "4":
                        holder.jiu.setVisibility(View.VISIBLE);
                        break;
                    case "5":
                        holder.ke.setVisibility(View.VISIBLE);
                        break;
                    case "6":
                        holder.jie.setVisibility(View.VISIBLE);
                        break;
                    case "7":
                        holder.lin.setVisibility(View.VISIBLE);
                        break;
                    case "8":
                        holder.jin.setVisibility(View.VISIBLE);
                        break;
                    case "9":
                        holder.wei.setVisibility(View.VISIBLE);
                        break;
                    case "10":
                        holder.jia.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
        if (TextUtils.isEmpty(bean.getAvatar())) {
            holder.icon.setImageResource(R.mipmap.icon_replace);
        } else {
            NetRequest.loadImg(mContext, bean.getAvatar(), holder.icon);
        }
        if (0 == bean.getRNA()) {
            holder.identity.setVisibility(View.GONE);
        } else holder.identity.setVisibility(View.VISIBLE);
        holder.name.setText(bean.getNickName());
        if (0 == bean.getNCCS()) {
            holder.zheng.setVisibility(View.GONE);
        } else holder.zheng.setVisibility(View.VISIBLE);
        if (bean.getHR_See() == 0) {
            holder.read.setVisibility(View.VISIBLE);
        } else {
            holder.read.setVisibility(View.INVISIBLE);
        }
        holder.time.setText(getTime(Long.parseLong(bean.getSeek_Time() + "")));
        switch (bean.getSeek_State()) {
            case 1:
                holder.status.setText("等待救援");
                holder.status.setTextColor(mContext.getResources().getColor(R.color.color_f7221b));
                break;
            case 2:
                holder.status.setText("救援已完成");
                holder.status.setTextColor(mContext.getResources().getColor(R.color.color_15b521));
                break;
            case 3:
            case 4:
                holder.status.setText("救援被终止");
                holder.status.setTextColor(mContext.getResources().getColor(R.color.color_9));
                break;
        }
        holder.detail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HelpDetailActivity.class);
                intent.putExtra("VR_ID", bean.getSeek_id() + "");
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    private String getTime(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(l * 1000);
    }

    private class ViewHolder {
        private ImageView detail, identity;
        private RoundImageView icon;
        private TextView name, zheng, time, status, shang, fu, wu, jiu, ke, jie, lin, jin, wei, jia;
        CircleImageView read;
    }
}
