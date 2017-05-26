package com.hyzs.onekeyhelp.carresuce.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.carresuce.bean.MyResuceItemBean;
import com.hyzs.onekeyhelp.util.DateFormatUtils;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;
import com.hyzs.onekeyhelp.widget.RoundImageView;

/**
 * Created by hyzs123 on 2017/3/24.
 */

public class MyResuceFragmentItemAdapter extends BaseAdapter {

    private Context mContext;
    SpannableString spannable;
    private MyResuceItemBean detailsBean;
    private MyResuceItemBean.MyVehicleRescueListBean listBean;

    public MyResuceFragmentItemAdapter(Context mContext, MyResuceItemBean detailsBean) {
        this.mContext = mContext;
        this.detailsBean = detailsBean;

    }

    @Override
    public int getCount() {
        return detailsBean.getMyVehicleRescueList().size();
    }

    @Override
    public Object getItem(int position) {
        return detailsBean.getMyVehicleRescueList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        listBean = detailsBean.getMyVehicleRescueList().get(position);
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_car_resuce_myresuce, null);
            vh = new ViewHolder();
            vh.iv_face = (RoundImageView) convertView.findViewById(R.id.item_car_resuce_myresuce_iv_face);
            vh.iv_identity = (ImageView) convertView.findViewById(R.id.item_car_resuce_myresuce_iv_identity);
            vh.tv_name = (TextView) convertView.findViewById(R.id.item_car_resuce_myresuce_tv_name);
            vh.tv_state = (TextView) convertView.findViewById(R.id.item_car_resuce_myresuce_tv_state);
            vh.tv_info = (TextView) convertView.findViewById(R.id.item_car_resuce_myresuce_tv_info);
            vh.tv_time = (TextView) convertView.findViewById(R.id.item_car_resuce_myresuce_tv_time);
            vh.tv_money = (TextView) convertView.findViewById(R.id.item_car_resuce_myresuce_tv_money);
            vh.tv_help = (TextView) convertView.findViewById(R.id.item_car_resuce_myresuce_tv_help);
            vh.tv_zheng = (TextView) convertView.findViewById(R.id.item_car_resuce_myresuce_tv_zheng);

            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //取值类似于数组开始， 管前不管后
        spannable = new SpannableString("酬谢" + listBean.getReward() + "元");
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#ec2b2b")), 2, spannable.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        vh.tv_money.setText(spannable);

        ImageLoadUtils.setImageForUrl(listBean.getAvatar(), vh.iv_face);

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

        vh.tv_name.setText(listBean.getNickName());

        vh.tv_time.setText(DateFormatUtils.Time2DateForAll((listBean.getSeek_Time() * 1000) + ""));


        vh.tv_info.setText(listBean.getContent());

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


        vh.tv_help.setText("收到帮助" + listBean.getHelpCount() + "");


        return convertView;
    }

    private class ViewHolder {
        private ImageView  iv_identity;
        private RoundImageView iv_face;
        private TextView tv_name, tv_state, tv_info, tv_time, tv_money, tv_help, tv_zheng;

    }


}
