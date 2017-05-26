package com.hyzs.onekeyhelp.mine;

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

public class MineHomeAdapterCircle extends BaseAdapter {

    public Context mContext;
    public List<HomeBean.CircleBean> lits;

    public MineHomeAdapterCircle(Context mContext,List<HomeBean.CircleBean> lits){
        this.mContext=mContext;
        this.lits=lits;

    }

    @Override
    public int getCount() {
        return lits.size();
    }

    @Override
    public Object getItem(int i) {
        return lits.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convetview, ViewGroup viewGroup) {

        ViewHolder vh;
        if(convetview==null){
            convetview=View.inflate(mContext, R.layout.item_mine_home_circle,null);
            vh=new ViewHolder();

            vh.circle1_time= (TextView) convetview.findViewById(R.id.activity_mine_home_circle1_time);
            vh.home_title= (TextView) convetview.findViewById(R.id.activity_mine_home_circle1);
            vh.item_tv_content= (TextView) convetview.findViewById(R.id.item_tv_content);

            convetview.setTag(vh);
        }else{

            vh= (ViewHolder) convetview.getTag();
        }

        vh.circle1_time.setText(DateFormatUtils.Time2Date(lits.get(i).getCircle_DateTime()));

        vh.home_title.setText(lits.get(i).getCircleName()+"");
        vh.item_tv_content.setText(lits.get(i).getCircle_Content()+"");




        return convetview;
    }

    public class ViewHolder {
        TextView circle1_time;
        TextView home_title;
        TextView item_tv_content;

    }


}
