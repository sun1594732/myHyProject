package com.hyzs.onekeyhelp.family.movable.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.movable.bean.MovabledynamicStateBean;
import com.hyzs.onekeyhelp.util.DateFormatUtils;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;


/**
 * Created by Administrator on 2017/3/20.
 */

public class MovableDynamicAdapter extends BaseAdapter {
    private Context mContext;
    private MovabledynamicStateBean StateBean;
    private MovabledynamicStateBean.ActivityDynamicListBean ListBean;

    public MovableDynamicAdapter(Context mContext,MovabledynamicStateBean StateBean) {

        this.mContext = mContext;
        this.StateBean = StateBean;
    }

    @Override
    public int getCount() {
        return StateBean.getActivityDynamicList().size();
    }

    @Override
    public Object getItem(int position) {
        return StateBean.getActivityDynamicList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListBean= StateBean.getActivityDynamicList().get(position);
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_movable_dynamic, null);
            vh=new ViewHolder();
            vh.iv_icon= (ImageView) convertView.findViewById(R.id.item_tel_import_icon);
            vh.iv_idcard= (ImageView) convertView.findViewById(R.id.item_movable_dynamic_iv_idcard);
            vh.tv_name= (TextView) convertView.findViewById(R.id.item_movable_dynamic_tv_name);
            vh.tv_info= (TextView) convertView.findViewById(R.id.item_movable_dynamic_tv_info);
            vh.tv_zheng= (TextView) convertView.findViewById(R.id.item_movable_dynamic_tv_zheng);
            vh.tv_time= (TextView) convertView.findViewById(R.id.item_movable_dynamic_tv_time);
            vh.linear= (LinearLayout) convertView.findViewById(R.id.item_movable_dynamic_linear);
            vh.ll_one= (LinearLayout) convertView.findViewById(R.id.item_movable_ll_one);
            vh.ll_two= (LinearLayout) convertView.findViewById(R.id.item_movable_ll_two);
            vh.prc1= (ImageView) convertView.findViewById(R.id.item_movable_pirc1);
            vh.prc2= (ImageView) convertView.findViewById(R.id.item_movable_pirc2);
            vh.prc3= (ImageView) convertView.findViewById(R.id.item_movable_pirc3);
            vh.prc4= (ImageView) convertView.findViewById(R.id.item_movable_pirc4);
            vh.prc5= (ImageView) convertView.findViewById(R.id.item_movable_pirc5);
            vh.prc6= (ImageView) convertView.findViewById(R.id.item_movable_pirc6);
            vh.prc7= (ImageView) convertView.findViewById(R.id.item_movable_pirc7);
            vh.prc8= (ImageView) convertView.findViewById(R.id.item_movable_pirc8);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        ImageLoadUtils.setImageForUrl(ListBean.getAvatar(),vh.iv_icon);
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
        vh.tv_name.setText(ListBean.getNickName());
        vh.tv_time.setText(DateFormatUtils.Time2DateForAll(ListBean.getED_Datetime()*1000+""));
        vh.tv_info.setText(ListBean.getED_Content());
        if(ListBean.getImageGroup().size()==0){
            vh.linear.setVisibility(View.GONE);
        }
        if(ListBean.getImageGroup().size()<=4){
            vh.linear.setVisibility(View.VISIBLE);
            vh.ll_one.setVisibility(View.VISIBLE);
            vh.ll_two.setVisibility(View.GONE);
        }
        if(ListBean.getImageGroup().size()>4){
            vh.linear.setVisibility(View.VISIBLE);
            vh.ll_two.setVisibility(View.VISIBLE);
            vh.ll_one.setVisibility(View.VISIBLE);
        }

        //判断图片
        switch (ListBean.getImageGroup().size()){
            case 0:
                vh.linear.setVisibility(View.GONE);
                break;
            case 1:
                vh.linear.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(0).getImage_url(),vh.prc1);
                vh.prc1.setVisibility(View.VISIBLE);
                vh.prc2.setVisibility(View.GONE);
                vh.prc3.setVisibility(View.GONE);
                vh.prc4.setVisibility(View.GONE);
                break;
            case 2:
                vh.linear.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(0).getImage_url(),vh.prc1);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(1).getImage_url(),vh.prc2);
                vh.prc1.setVisibility(View.VISIBLE);
                vh.prc2.setVisibility(View.VISIBLE);
                vh.prc3.setVisibility(View.GONE);
                vh.prc4.setVisibility(View.GONE);
                break;
            case 3:
                vh.linear.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(0).getImage_url(),vh.prc1);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(1).getImage_url(),vh.prc2);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(2).getImage_url(),vh.prc3);
                vh.prc1.setVisibility(View.VISIBLE);
                vh.prc2.setVisibility(View.VISIBLE);
                vh.prc3.setVisibility(View.VISIBLE);
                vh.prc4.setVisibility(View.GONE);
                break;

            case 4:vh.linear.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(0).getImage_url(),vh.prc1);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(1).getImage_url(),vh.prc2);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(2).getImage_url(),vh.prc3);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(3).getImage_url(),vh.prc4);
                vh.prc1.setVisibility(View.VISIBLE);
                vh.prc2.setVisibility(View.VISIBLE);
                vh.prc3.setVisibility(View.VISIBLE);
                vh.prc4.setVisibility(View.VISIBLE);
                break;

            case 5:vh.linear.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(0).getImage_url(),vh.prc1);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(1).getImage_url(),vh.prc2);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(2).getImage_url(),vh.prc3);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(3).getImage_url(),vh.prc4);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(4).getImage_url(),vh.prc5);
                vh.prc1.setVisibility(View.VISIBLE);
                vh.prc2.setVisibility(View.VISIBLE);
                vh.prc3.setVisibility(View.VISIBLE);
                vh.prc4.setVisibility(View.VISIBLE);
                vh.prc5.setVisibility(View.VISIBLE);
                vh.prc6.setVisibility(View.GONE);
                vh.prc7.setVisibility(View.GONE);
                vh.prc8.setVisibility(View.GONE);
                break;

            case 6:vh.linear.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(0).getImage_url(),vh.prc1);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(1).getImage_url(),vh.prc2);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(2).getImage_url(),vh.prc3);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(3).getImage_url(),vh.prc4);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(4).getImage_url(),vh.prc5);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(5).getImage_url(),vh.prc6);
                vh.prc1.setVisibility(View.VISIBLE);
                vh.prc2.setVisibility(View.VISIBLE);
                vh.prc3.setVisibility(View.VISIBLE);
                vh.prc4.setVisibility(View.VISIBLE);
                vh.prc5.setVisibility(View.VISIBLE);
                vh.prc6.setVisibility(View.VISIBLE);
                vh.prc7.setVisibility(View.GONE);
                vh.prc8.setVisibility(View.GONE);
                break;

            case 7:vh.linear.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(0).getImage_url(),vh.prc1);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(1).getImage_url(),vh.prc2);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(2).getImage_url(),vh.prc3);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(3).getImage_url(),vh.prc4);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(4).getImage_url(),vh.prc5);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(5).getImage_url(),vh.prc6);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(6).getImage_url(),vh.prc7);
                vh.prc1.setVisibility(View.VISIBLE);
                vh.prc2.setVisibility(View.VISIBLE);
                vh.prc3.setVisibility(View.VISIBLE);
                vh.prc4.setVisibility(View.VISIBLE);
                vh.prc5.setVisibility(View.VISIBLE);
                vh.prc6.setVisibility(View.VISIBLE);
                vh.prc7.setVisibility(View.VISIBLE);
                vh.prc8.setVisibility(View.GONE);
                break;

            case 8:
                vh.linear.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(0).getImage_url(),vh.prc1);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(1).getImage_url(),vh.prc2);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(2).getImage_url(),vh.prc3);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(3).getImage_url(),vh.prc4);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(4).getImage_url(),vh.prc5);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(5).getImage_url(),vh.prc6);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(6).getImage_url(),vh.prc7);
                ImageLoadUtils.setImageForUrl(ListBean.getImageGroup().get(7).getImage_url(),vh.prc8);
                vh.prc1.setVisibility(View.VISIBLE);
                vh.prc2.setVisibility(View.VISIBLE);
                vh.prc3.setVisibility(View.VISIBLE);
                vh.prc4.setVisibility(View.VISIBLE);
                vh.prc5.setVisibility(View.VISIBLE);
                vh.prc6.setVisibility(View.VISIBLE);
                vh.prc7.setVisibility(View.VISIBLE);
                vh.prc8.setVisibility(View.VISIBLE);
                break;
        }






        return convertView;
    }





    public class  ViewHolder{
        ImageView iv_icon,iv_idcard,prc1,prc2,prc3,prc4,prc5,prc6,prc7,prc8;
        TextView tv_name,tv_info,tv_zheng,tv_time;
        LinearLayout linear,ll_one,ll_two;

    }



}
