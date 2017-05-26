package com.hyzs.onekeyhelp.carresuce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyzs.onekeyhelp.lifehelp.bean.HelpMeDetailsCommonBean;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/21.
 */

public class CarDetailsEndAdapter extends BaseAdapter  {

    private Context mContext;

    List <HelpMeDetailsCommonBean.ComplateSeekHelpUserListCommonBean> Commonlistbean;

    private Double mayMoney,Money;

    private boolean isHelpOk;



    public CarDetailsEndAdapter(Context mContext, List <HelpMeDetailsCommonBean.ComplateSeekHelpUserListCommonBean> Commonlistbean,
                                 Double mayMoney, boolean isHelpOk){

        this.mContext=mContext;
        this.Commonlistbean=Commonlistbean;
        this.mayMoney=mayMoney;
        this.Money =0.0;


        this.isHelpOk=isHelpOk;



    }

    @Override
    public int getCount() {

        return Commonlistbean.size();
    }

    @Override
    public Object getItem(int position) {
        return Commonlistbean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HelpMeDetailsCommonBean.ComplateSeekHelpUserListCommonBean commonBean= Commonlistbean.get(position);
        ViewHolder vh;
        if(convertView==null) {
            convertView = View.inflate(mContext, R.layout.item_live_help_other_details_lv_get_help, null);
            vh=new ViewHolder();
            vh.iv_face= (ImageView) convertView.findViewById(R.id.live_help_other_details_item_iv_face);
            vh.tv_ishelp= (TextView) convertView.findViewById(R.id.live_help_other_details_item_tv_ishelp);
            vh.tv_time= (TextView) convertView.findViewById(R.id.live_help_other_details_item_tv_time);
            vh.tv_name= (TextView) convertView.findViewById(R.id.live_help_other_details_item_tv_name);
            vh.tv_subtract= (ImageView) convertView.findViewById(R.id.live_help_other_details_item_iv_subtract);
            vh.iv_idcard= (ImageView) convertView.findViewById(R.id.live_help_other_details_item_iv_idcard);
            vh.tv_add= (ImageView) convertView.findViewById(R.id.live_help_other_details_item_iv_add);
            vh.calculate_money= (LinearLayout) convertView.findViewById(R.id.live_help_other_details_item_ll_calculate_money);
            vh.over_money= (TextView) convertView.findViewById(R.id.live_help_other_details_item_tv_overmoney);
            vh.tv_zheng= (TextView) convertView.findViewById(R.id.live_help_other_details_item_tv_zheng);
            convertView.setTag(vh);
        }else{

            vh= (ViewHolder) convertView.getTag();
        }
        ImageLoadUtils.setImageForUrl(commonBean.getFace(),vh.iv_face);
        vh.iv_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySharedPreferences.isLogin(mContext)) {
                    ToastUtils.showShort(mContext, "请先登录.");
                    return;
                }
                Intent intent = new Intent(mContext, MineHomeActivity.class);
                intent.putExtra("targetUserId", commonBean.getUserid()+"");
                mContext.startActivity(intent);
            }
        });
        vh.tv_name.setText(commonBean.getUesrname());
        vh.tv_time.setText(commonBean.getHelp_WCRQ());
        vh.tv_ishelp.setText(commonBean.getHelpState());

        vh.over_money.setVisibility(View.VISIBLE);
        vh.over_money.setText("悬赏金额："+commonBean.getGotMoney()+"元");
        vh.calculate_money.setVisibility(View.GONE);







        if(commonBean.getRNA()==1){//判断是否通过实名认证
            vh.iv_idcard.setVisibility(View.VISIBLE);
        }else{
            vh.iv_idcard.setVisibility(View.GONE);
        }
        if(commonBean.getNCCS()==1){//判断是否通过居委会认证
            vh.tv_zheng.setVisibility(View.VISIBLE);
        }else{
            vh.tv_zheng.setVisibility(View.GONE);
        }


        return convertView;
    }







    private class ViewHolder {
        ImageView iv_face,iv_idcard;
        ImageView tv_add;
        ImageView tv_subtract;
        TextView tv_name;
        TextView tv_time;
        TextView over_money;
        TextView tv_zheng;
        TextView tv_ishelp;
        LinearLayout calculate_money;

    }


}
