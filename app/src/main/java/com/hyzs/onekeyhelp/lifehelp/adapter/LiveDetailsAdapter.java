package com.hyzs.onekeyhelp.lifehelp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyzs.onekeyhelp.lifehelp.bean.HelpOtherDetailsHelMeBean;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/21.
 */

public class LiveDetailsAdapter extends BaseAdapter  {

    private Context mContext;
    private List <HelpOtherDetailsHelMeBean .MySeekHelpMoneyAllotListBean> listbean;

    private TextView tv_mayMoney;
    private Double mayMoney,Money;
    private double [] ints;
    private boolean isHelpOk;
    private boolean isFreeIssue;
    private boolean isok;

    public LiveDetailsAdapter(Context mContext,List <HelpOtherDetailsHelMeBean .MySeekHelpMoneyAllotListBean> listbean,
                              TextView tv_mayMoney, Double mayMoney,double [] ints,boolean isHelpOk,boolean isFreeIssue){  //没接口，没数据源
        this.mContext=mContext;
        this.listbean=listbean;
        this.mayMoney=mayMoney;
        this.Money =0.0;
        this.tv_mayMoney=tv_mayMoney;
        this.ints=ints;
        this.isHelpOk=isHelpOk;
        this.isFreeIssue=isFreeIssue;
        //开循环将数值存入每个ints

        for (int i=0;i<listbean.size();i++){
            if(ints.length==0){
                return;
            }
            ints[i]=listbean.get(i).getAutoAllotMoney();
        }
    }


    @Override
    public int getCount() {

        return listbean.size();
    }

    @Override
    public Object getItem(int position) {
        return listbean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HelpOtherDetailsHelMeBean.MySeekHelpMoneyAllotListBean ListBean= listbean.get(position);
        final ViewHolder vh;
        if(convertView==null) {
            convertView = View.inflate(mContext, R.layout.item_live_help_other_details_lv_get_help, null);
            vh=new ViewHolder();
            vh.iv_face= (RoundImageView) convertView.findViewById(R.id.live_help_other_details_item_iv_face);
            vh.tv_ishelp= (TextView) convertView.findViewById(R.id.live_help_other_details_item_tv_ishelp);
            vh.tv_time= (TextView) convertView.findViewById(R.id.live_help_other_details_item_tv_time);
            vh.tv_name= (TextView) convertView.findViewById(R.id.live_help_other_details_item_tv_name);
            vh.tv_subtract= (ImageView) convertView.findViewById(R.id.live_help_other_details_item_iv_subtract);
            vh.iv_idcard= (ImageView) convertView.findViewById(R.id.live_help_other_details_item_iv_idcard);
            vh.tv_add= (ImageView) convertView.findViewById(R.id.live_help_other_details_item_iv_add);
            vh.et_money= (EditText) convertView.findViewById(R.id.live_help_other_details_item_et_money);
            vh.calculate_money= (LinearLayout) convertView.findViewById(R.id.live_help_other_details_item_ll_calculate_money);
            vh.over_money= (TextView) convertView.findViewById(R.id.live_help_other_details_item_tv_overmoney);
            vh.tv_zheng= (TextView) convertView.findViewById(R.id.live_help_other_details_item_tv_zheng);
            convertView.setTag(vh);
        }else{

            vh= (ViewHolder) convertView.getTag();
        }
        ImageLoadUtils.setImageForUrl(ListBean.getFace(),vh.iv_face);

        vh.iv_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MineHomeActivity.class);
                intent.putExtra("targetUserId",ListBean.getUserid()+"");
                mContext.startActivity(intent);
            }
        });

        vh.tv_name.setText(ListBean.getUesrname());
        vh.tv_time.setText(ListBean.getHelp_WCRQ());
        vh.tv_ishelp.setText(ListBean.getHelpState());
        vh.et_money.setText(ints[position]+"");

        if(isHelpOk){
            vh.over_money.setVisibility(View.VISIBLE);
            vh.over_money.setText("悬赏金额："+ListBean.getAutoAllotMoney()+"元");
            vh.calculate_money.setVisibility(View.GONE);
        }else{
            vh.over_money.setVisibility(View.GONE);
            vh.calculate_money.setVisibility(View.VISIBLE);
        }

        if(isFreeIssue){
            vh.over_money.setVisibility(View.VISIBLE);
            vh.over_money.setText("悬赏金额0元");
            vh.calculate_money.setVisibility(View.GONE);
        }else{
            vh.over_money.setVisibility(View.GONE);
            vh.calculate_money.setVisibility(View.VISIBLE);
        }




        if(ListBean.getRNA()==1){//判断是否通过实名认证
            vh.iv_idcard.setVisibility(View.VISIBLE);
        }else{
            vh.iv_idcard.setVisibility(View.GONE);
        }
        if(ListBean.getNCCS()==1){//判断是否通过居委会认证
            vh.tv_zheng.setVisibility(View.VISIBLE);
        }else{
            vh.tv_zheng.setVisibility(View.GONE);
        }



        vh.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数组中的值循环加起来，
                if(compintNumber()){
                    --Money;
                    tv_mayMoney.setText(Money+"");
                    vh.et_money.setText(++ints[position]+"");
                }
            }
        });
        vh.tv_subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ints[position]>0){
                    ++Money;
                    tv_mayMoney.setText(Money+"");
                    vh.et_money.setText(--ints[position]+"");
                }
            }
        });

        return convertView;
    }

    public boolean compintNumber(){
        double s=0;
        for (int i=0;i<ints.length;i++){
            s=ints[i]+s;
        }
        if(s>=mayMoney){
            return false;
        }else{
            return true;
        }
    }




    private class ViewHolder {
        ImageView iv_idcard;
        RoundImageView iv_face;
        EditText et_money;
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
