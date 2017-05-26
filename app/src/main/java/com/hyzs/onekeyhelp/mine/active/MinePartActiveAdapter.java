package com.hyzs.onekeyhelp.mine.active;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


public class MinePartActiveAdapter extends BaseAdapter {
    private Context mContext;
    private List<IPartBean.PersonalCenterIParticipationBean> list;

    public MinePartActiveAdapter(Context mContext, List<IPartBean.PersonalCenterIParticipationBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public IPartBean.PersonalCenterIParticipationBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        MyHolder holder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_movable_list, null);
            holder = new MyHolder();
            holder.avatar = (RoundImageView) view.findViewById(R.id.item_movable_avatar);
            holder.icon = (RoundImageView) view.findViewById(R.id.item_movable_icon);
            holder.publisher_img = (ImageView) view.findViewById(R.id.item_movable_publisher_img);
            holder.publisher_text = (TextView) view.findViewById(R.id.item_movable_publisher_text);
            holder.name = (TextView) view.findViewById(R.id.item_movable_name);
            holder.status = (TextView) view.findViewById(R.id.item_movable_status);
            holder.number = (TextView) view.findViewById(R.id.item_movable_num);
            holder.title = (TextView) view.findViewById(R.id.item_movable_title);
            holder.time = (TextView) view.findViewById(R.id.item_movable_time);
            holder.address = (TextView) view.findViewById(R.id.item_movable_address);
            holder.ding = (ImageView) view.findViewById(R.id.item_movable_ding);
            holder.del = (TextView) view.findViewById(R.id.item_movable_del);
            view.setTag(holder);
        } else {
            holder = (MyHolder) view.getTag();
        }
        final IPartBean.PersonalCenterIParticipationBean bean = list.get(position);
        if (!TextUtils.isEmpty(bean.getFace())) {
            NetRequest.loadImg(mContext, bean.getFace(), holder.avatar);
        } else
            holder.avatar.setImageResource(R.mipmap.replace_hybb);
        if (bean.getImages().size() != 0) {
            if (!TextUtils.isEmpty(bean.getImages().get(0).getUrl()))
                NetRequest.loadImg(mContext, bean.getImages().get(0).getUrl(), holder.icon);
            else
                holder.icon.setImageResource(R.mipmap.icon_replace);
        } else
            holder.icon.setImageResource(R.mipmap.icon_replace);
        holder.name.setText(bean.getUesrname());
        if (bean.getR_type() == 0) {
            holder.publisher_text.setVisibility(View.VISIBLE);
            holder.publisher_img.setVisibility(View.GONE);
        } else {
            holder.publisher_text.setVisibility(View.GONE);
            holder.publisher_img.setVisibility(View.VISIBLE);
        }
        holder.ding.setVisibility(View.INVISIBLE);
//        if (bean.get() == 0) {
//            holder.ding.setVisibility(View.INVISIBLE);
//        } else holder.ding.setVisibility(View.VISIBLE);
        switch (bean.getR_c_type()) {
            case 1:
                holder.status.setVisibility(View.INVISIBLE);
                holder.number.setText("审核中");
                holder.number.setTextColor(ContextCompat.getColor(mContext, R.color.color_6));
                break;
            case 2:
                holder.status.setVisibility(View.INVISIBLE);
                holder.number.setText("筹备中");
                holder.number.setTextColor(ContextCompat.getColor(mContext, R.color.color_6));
                break;
            case 3:
//                holder.status.setVisibility(View.VISIBLE);
                holder.number.setText("报名人数(" + bean.getR_type() + ")");
                holder.number.setTextColor(ContextCompat.getColor(mContext, R.color.color_6));
                break;
            case 4:
                holder.status.setVisibility(View.INVISIBLE);
                holder.number.setText("活动进行中");
                holder.number.setTextColor(ContextCompat.getColor(mContext, R.color.color_0349a9));
                break;
            case 5:
                holder.status.setVisibility(View.INVISIBLE);
                holder.number.setText("活动未成功");
                holder.number.setTextColor(ContextCompat.getColor(mContext, R.color.color_6));
                break;
            case 6:
                holder.status.setVisibility(View.INVISIBLE);
                holder.number.setText("报名被终止");
                holder.number.setTextColor(ContextCompat.getColor(mContext, R.color.color_6));
                break;
            case 7:
                holder.status.setVisibility(View.INVISIBLE);
                holder.number.setText("圆满成功");
                holder.number.setTextColor(ContextCompat.getColor(mContext, R.color.color_f7221b));
                break;
        }
        holder.title.setText(bean.getContent());
        holder.time.setText("时间：" + bean.getDiffTime());
        holder.address.setVisibility(View.INVISIBLE);
//        holder.address.setText("地点：" + bean.getER_Address());
        holder.del.setVisibility(View.INVISIBLE);
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> param = new ArrayMap<String, String>();
                param.put("currId", MySharedPreferences.getUserId(mContext));
                param.put("type", "4");
                param.put("T_ID", bean.getR_id() + "");
                NetRequest.ParamPostRequest(PortUtil.MyDoDel, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        DataStatusBean bean1 = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                        if ("10000".equals(bean1.getCode())) {
                            list.remove(position);
                            notifyDataSetChanged();
                        } else ToastSingleUtil.showToast(mContext, bean1.getMessage());
                    }
                });

            }
        });
        return view;
    }

    private String longToTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(time * 1000);
    }

    private class MyHolder {
        private RoundImageView avatar, icon;
        private TextView publisher_text, name, status, number, title, time, address, del;
        private ImageView publisher_img, ding;
    }
}
