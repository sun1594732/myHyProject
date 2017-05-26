package com.hyzs.onekeyhelp.lifehelp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.lifehelp.bean.LifeHelpOtherCommentBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.DateFormatUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.widget.RoundImageView;

/**
 * Created by hyzs123 on 2017/3/21.
 */


public class LiveDetailsCommentAdapter extends BaseAdapter {

    private Context mContext;
    private LifeHelpOtherCommentBean commentBean;
    public LiveDetailsCommentAdapter(Context mContext, LifeHelpOtherCommentBean commentBean){
        this.mContext=mContext;
        this.commentBean=commentBean;
        Log.e("info_comment3", "LiveDetailsCommentAdapter: "+commentBean.getLifeSeekHelpComments().toString() );
    }




    @Override
    public int getCount() {

        return commentBean.getTotal();
    }

    @Override
    public Object getItem(int position) {
        return commentBean.getLifeSeekHelpComments().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.e("info_comment3",position+"");
        final LifeHelpOtherCommentBean.LifeSeekHelpCommentsBean lifeSeekHelpCommentsBean= commentBean.getLifeSeekHelpComments().get(position);
        ViewHolder vh;
        if(convertView==null){
            convertView=View.inflate(mContext, R.layout.item_help_details_comment,null);
            vh=new ViewHolder();

            vh.iv_face_other= (RoundImageView) convertView.findViewById(R.id.item_help_other_details_iv_face);
            vh.ll_other=(LinearLayout)convertView.findViewById(R.id.item_help_other_details_ll_other);
            vh.tv_name_other=(TextView)convertView.findViewById(R.id.live_help_other_details_tv_name);
            vh.tv_info_other=(TextView)convertView.findViewById(R.id.live_help_other_details_tv_info);
            vh.tv_time_other=(TextView)convertView.findViewById(R.id.live_help_other_details_tv_time);

            vh.iv_face_me= (RoundImageView) convertView.findViewById(R.id.item_help_other_details_iv_face_me);
            vh.ll_me=(LinearLayout)convertView.findViewById(R.id.item_help_other_details_ll_me);
            vh.tv_name_me=(TextView)convertView.findViewById(R.id.live_help_other_details_tv_name_me);
            vh.tv_info_me=(TextView)convertView.findViewById(R.id.live_help_other_details_tv_info_me);
            vh.tv_time_me=(TextView)convertView.findViewById(R.id.live_help_other_details_tv_time_me);

            convertView.setTag(vh);

        }else{
            vh= (ViewHolder) convertView.getTag();
        }

        vh.iv_face_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MineHomeActivity.class);
                intent.putExtra("targetUserId",lifeSeekHelpCommentsBean.getSC_UserID()+"");
                mContext.startActivity(intent);
                Log.e("info_comment3", "onClick: other="+position );
                Log.e("info_comment3", "onClick: other_id="+lifeSeekHelpCommentsBean.getSC_UserID() );
            }
        });

        vh.iv_face_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MineHomeActivity.class);
                intent.putExtra("targetUserId", MySharedPreferences.getUserId(mContext));
                mContext.startActivity(intent);
                Log.e("info_comment3", "onClick: me="+position );
            }
        });


        //首先判断是不是我发出的。
        if(lifeSeekHelpCommentsBean.getPublisher()==0) {
            vh.ll_me.setVisibility(View.GONE);
            vh.ll_other.setVisibility(View.VISIBLE);
            NetRequest.loadImg(mContext,lifeSeekHelpCommentsBean.getFace(),vh.iv_face_other);//设置头像
            vh.tv_name_other.setText(lifeSeekHelpCommentsBean.getUesrname());
            vh.tv_info_other.setText(lifeSeekHelpCommentsBean.getSC_Content());
            vh.tv_time_other.setText(DateFormatUtils.Time2Date((lifeSeekHelpCommentsBean.getSC_DateTime()*1000)+""));

        }else{
            vh.ll_me.setVisibility(View.VISIBLE);
            vh.ll_other.setVisibility(View.GONE);
            NetRequest.loadImg(mContext,lifeSeekHelpCommentsBean.getFace(),vh.iv_face_me);//设置头像
            vh.tv_name_me.setText(lifeSeekHelpCommentsBean.getUesrname());
            vh.tv_info_me.setText(lifeSeekHelpCommentsBean.getSC_Content());
            vh.tv_time_me.setText(DateFormatUtils.Time2Date((lifeSeekHelpCommentsBean.getSC_DateTime()*1000)+""));
        }
        return convertView;
    }

    private class ViewHolder {
        private LinearLayout ll_other; //其他人
        private RoundImageView iv_face_other;
        private TextView tv_name_other;
        private TextView tv_time_other;
        private TextView tv_info_other;
        private LinearLayout ll_me; //我
        private RoundImageView iv_face_me;
        private TextView tv_name_me;
        private TextView tv_time_me;
        private TextView tv_info_me;
    }

}
