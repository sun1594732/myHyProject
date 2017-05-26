package com.hyzs.onekeyhelp.carresuce.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.bean.HomeBean;
import com.hyzs.onekeyhelp.util.DateFormatUtils;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/26.
 */

public class MineResuceTroopsFragmentItemRecentlyAdapter extends BaseAdapter {

    public Context mContext;
    public List<HomeBean.EventDynamicBean> lits;

    public MineResuceTroopsFragmentItemRecentlyAdapter(Context mContext, List<HomeBean.EventDynamicBean> lits){
        this.mContext=mContext;
        this.lits=lits;

    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convetview, ViewGroup viewGroup) {

        ViewHolder vh;
        if(convetview==null){
            convetview=View.inflate(mContext, R.layout.item_mine_resuce_troops_item,null);
            vh=new ViewHolder();
            vh.home_title= (TextView) convetview.findViewById(R.id.activity_mine_home_action_title);
            vh.item_tv_content= (TextView) convetview.findViewById(R.id.activity_mine_home_action_time);
            convetview.setTag(vh);
        }else{
            vh= (ViewHolder) convetview.getTag();
        }
//        vh.home_title.setText(lits.get(i).getER_Address()+"");
//        vh.item_tv_content.setText(lits.get(i).getED_Content()+"");

        return convetview;
    }

    public class ViewHolder {

        TextView home_title;
        TextView item_tv_content;

    }


}
