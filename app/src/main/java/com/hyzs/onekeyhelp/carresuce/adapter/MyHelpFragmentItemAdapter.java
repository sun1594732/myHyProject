package com.hyzs.onekeyhelp.carresuce.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.carresuce.bean.MyhelpItemBean;
import com.hyzs.onekeyhelp.util.DateFormatUtils;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hyzs123 on 2017/3/24.
 */

public class MyHelpFragmentItemAdapter extends BaseAdapter {

    private Context mContext;
    private SpannableString spannable;
    private MyhelpItemBean itemBean;
    private MyhelpItemBean.VehicleRescueMyHelpListBean listBean;


    public MyHelpFragmentItemAdapter(Context mContext, MyhelpItemBean itemBean) {
        this.mContext = mContext;
        this.itemBean = itemBean;

    }

    @Override
    public int getCount() {
        return itemBean.getVehicleRescueMyHelpList().size();
    }

    @Override
    public Object getItem(int position) {
        return itemBean.getVehicleRescueMyHelpList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        listBean = itemBean.getVehicleRescueMyHelpList().get(position);
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_car_resuce_myhelp, null);
            vh = new ViewHolder();
            vh.iv_face = (RoundImageView) convertView.findViewById(R.id.item_car_resuce_myhelp_iv_face);
            vh.iv_identity = (ImageView) convertView.findViewById(R.id.item_car_resuce_myhelp_iv_identity);
            vh.tv_name = (TextView) convertView.findViewById(R.id.item_car_resuce_myhelp_tv_name);
            vh.tv_state = (TextView) convertView.findViewById(R.id.item_car_resuce_myhelp_tv_state);
            vh.tv_info = (TextView) convertView.findViewById(R.id.item_car_resuce_myhelp_tv_info);
            vh.tv_time = (TextView) convertView.findViewById(R.id.item_car_resuce_myhelp_tv_time);
            vh.tv_money = (TextView) convertView.findViewById(R.id.item_car_resuce_myhelp_tv_money);
            vh.tv_car_number = (TextView) convertView.findViewById(R.id.item_car_resuce_myhelp_tv_car_number);
            vh.tv_zheng = (TextView) convertView.findViewById(R.id.item_car_resuce_myhelp_tv_zheng);
            vh.read = (CircleImageView) convertView.findViewById(R.id.item_car_resuce_myhelp_read);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(listBean.getAvatar())) {
            ImageLoadUtils.setImageForUrl(listBean.getAvatar(), vh.iv_face);
        }
        if (listBean.getRNA() == 1) {//判断是否通过实名认证
            vh.iv_identity.setVisibility(View.VISIBLE);
        } else {
            vh.iv_identity.setVisibility(View.GONE);
        }
        if (listBean.getNCCS() == 1) {//判断是否通过居委会认证
            vh.tv_zheng.setVisibility(View.VISIBLE);
        } else {
            vh.tv_zheng.setVisibility(View.GONE);
        }
        if (listBean.getHR_See() == 0) {
            vh.read.setVisibility(View.VISIBLE);
        }else vh.read.setVisibility(View.INVISIBLE);
        vh.tv_name.setText(listBean.getNickName());

        vh.tv_time.setText(DateFormatUtils.Time2DateForAll((listBean.getSeek_Time() * 1000) + ""));

        vh.tv_car_number.setText(listBean.getVehicleCardNum());
        vh.tv_info.setText(listBean.getGPSDesc());

        switch (listBean.getSeek_State()) {
            case 1:
                vh.tv_state.setText("等待救援");
                vh.tv_state.setTextColor(Color.parseColor("#f7221d"));
                break;
            case 2:
                vh.tv_state.setText("救援已完成");
                vh.tv_state.setTextColor(Color.parseColor("#15a61f"));
                break;
            case 3:
                vh.tv_state.setText("救援被终止");
                vh.tv_state.setTextColor(Color.parseColor("#999999"));

                break;
            case 4:
                vh.tv_state.setText("救援被终止");
                vh.tv_state.setTextColor(Color.parseColor("#999999"));
                break;
            default:
                vh.tv_state.setText("请稍后 ....");
                break;
        }

        spannable = new SpannableString("酬谢" + listBean.getReward() + "元");


        //取值类似于数组开始， 管前不管后
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#ec2b2b")), 2, spannable.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        vh.tv_money.setText(spannable);

        return convertView;
    }

    private class ViewHolder {
        private RoundImageView iv_face;
        private ImageView iv_identity;
        private TextView tv_name, tv_state, tv_info, tv_time, tv_money, tv_car_number, tv_zheng;
        private CircleImageView read;
    }


}
