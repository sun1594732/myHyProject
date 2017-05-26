package com.hyzs.onekeyhelp.lifehelp.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.ShowImageActivity;
import com.hyzs.onekeyhelp.lifehelp.bean.LifeHelpMeBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.util.DateFormatUtils;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.ArrayList;


/**
 * Created by hyzs123 on 2017/3/19.
 */

public class LiveHelpMeAdapter extends BaseAdapter {

    private Context mContext;
    private LifeHelpMeBean lifeHelpMeBean;
    private LifeHelpMeBean.MySeekHelpListBean listBean;
    public LiveHelpMeAdapter(Context mContext, LifeHelpMeBean lifeHelpMeBean) {
        this.mContext = mContext;
        this.lifeHelpMeBean = lifeHelpMeBean;
    }

    @Override
    public int getCount() {
        return lifeHelpMeBean.getMySeekHelpList().size();
    }

    @Override
    public Object getItem(int position) {
        return lifeHelpMeBean.getMySeekHelpList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        listBean = lifeHelpMeBean.getMySeekHelpList().get(position);


        MViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_fragment_live_help_me, null);
            vh = new MViewHolder();
            vh.iv_face = (RoundImageView) convertView.findViewById(R.id.fragment_live_help_me_have_iv_face);
            vh.iv_face1 = (RoundImageView) convertView.findViewById(R.id.fragment_live_help_me_have_imageView1);
            vh.iv_face2 = (RoundImageView) convertView.findViewById(R.id.fragment_live_help_me_have_imageView2);
            vh.iv_face3 = (RoundImageView) convertView.findViewById(R.id.fragment_live_help_me_have_imageView3);
            vh.iv_face4 = (RoundImageView) convertView.findViewById(R.id.fragment_live_help_me_have_imageView4);
            vh.iv_idcard = (ImageView) convertView.findViewById(R.id.fragment_live_help_me_have_iv_idcard);
            vh.ll_images2 = (LinearLayout) convertView.findViewById(R.id.fragment_live_help_me_have_ll_images2); //草根英雄
            vh.tv_label = (TextView) convertView.findViewById(R.id.fragment_live_help_me_have_tv_chenghao); //草根英雄

            vh.tv_name = (TextView) convertView.findViewById(R.id.fragment_live_help_me_have_tv_name);
            vh.tv_time = (TextView) convertView.findViewById(R.id.fragment_live_help_me_have_time);
            vh.tv_zheng = (TextView) convertView.findViewById(R.id.live_fragment_my_help_list_tv_zheng);
            vh.tv_info = (TextView) convertView.findViewById(R.id.fragment_live_help_me_have_info);//具体信息
            vh.tv_state = (TextView) convertView.findViewById(R.id.fragment_live_help_me_have_tv_stat);
            vh.tv_address = (TextView) convertView.findViewById(R.id.fragment_live_help_me_have_tv_adress);
            vh.tv_number = (TextView) convertView.findViewById(R.id.fragment_live_help_me_have_number);
            vh.ll_toXQ = (LinearLayout) convertView.findViewById(R.id.fragment_live_help_me_have_toXQ);
            if (TextUtils.isEmpty(listBean.getFace())) {
                vh.iv_face.setImageResource(R.mipmap.icon_replace);
            }else
            ImageLoadUtils.setImageForUrl(listBean.getFace(), vh.iv_face);
            convertView.setTag(vh);
        } else {
            vh = (MViewHolder) convertView.getTag();
        }
        //处理数据

        if (listBean.getRNA() == 1) {//判断是否通过实名认证
            vh.iv_idcard.setVisibility(View.VISIBLE);

        } else {
            vh.iv_idcard.setVisibility(View.GONE);
        }

        if (listBean.getNCCS() == 1) {//判断是否通过居委会认证
            vh.tv_zheng.setVisibility(View.VISIBLE);

        } else {
            vh.tv_zheng.setVisibility(View.GONE);
        }

        vh.tv_info.setText(listBean.getSeek_Text());//设置具体信息



//        “Seek_State” = “求助状态，1求助中，2成功，3失败，4取消”,
        switch (listBean.getSeek_State()) {
            case 1:
                vh.tv_state.setText("求助中 ....");
                break;
            case 2:
                vh.tv_state.setText("成功 ....");
                break;
            case 3:
                vh.tv_state.setText("失败 ....");
                break;
            case 4:
                vh.tv_state.setText("取消 ....");
                break;
            default:
                vh.tv_state.setText("请稍后 ....");
                break;
        }

        vh.tv_number.setText(listBean.getHelpCount() + "人接受求助");

        vh.tv_address.setText(listBean.getCommunityName());//社区名称
        vh.tv_label.setText(listBean.getGrassrootsHero()); //标签

//        vh.tv_label.setText("草根英雄"); //标签
//        vh.tv_address.setText("小辛庄村");//社区名称

        vh.tv_name.setText(listBean.getUesrname()); //名称
        vh.tv_time.setText(DateFormatUtils.Time2Date((listBean.getSeek_Time()*1000)+"")); //时间


//        NetRequest.loadImg(mContext,listBean.getFace(),vh.iv_face);


        //判断是否存在图片

        if (TextUtils.isEmpty(listBean.getSeek_AffixImgList())) {

            vh.ll_images2.setVisibility(View.GONE);
            return convertView;
        } else {
            vh.ll_images2.setVisibility(View.VISIBLE);
        }

        String[] faceurls = listBean.getSeek_AffixImgList().split(",");


        for (int i = 0; i < faceurls.length; i++) {
            faceurls[i] = faceurls[i].substring(1, faceurls[i].length() - 1);
        }
        final ArrayList<String> sList = new ArrayList<>();
        for (int i = 0; i < faceurls.length; i++) {
            sList.add(faceurls[i]);
        }

        vh.iv_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,MineHomeActivity.class);
                intent.putExtra("targetUserId", listBean.getSeek_UserID());
                mContext.startActivity(intent);
            }
        });
        vh.iv_face1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentShowActivity(sList);
            }
        });
        vh.iv_face2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentShowActivity(sList);
            }
        });
        vh.iv_face3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentShowActivity(sList);
            }
        });
        vh.iv_face4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentShowActivity(sList);
            }
        });
        vh.iv_face1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        switch (faceurls.length) {
            case 1:
                vh.iv_face2.setVisibility(View.GONE);
                vh.iv_face3.setVisibility(View.GONE);
                vh.iv_face4.setVisibility(View.GONE);
                vh.iv_face1.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(faceurls[0], vh.iv_face1);
                break;
            case 2:
                vh.iv_face1.setVisibility(View.GONE);
                vh.iv_face3.setVisibility(View.GONE);
                vh.iv_face4.setVisibility(View.GONE);
                vh.iv_face1.setVisibility(View.VISIBLE);
                vh.iv_face2.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(faceurls[0], vh.iv_face1);
                ImageLoadUtils.setImageForUrl(faceurls[1], vh.iv_face2);
                break;
            case 3:
                vh.iv_face1.setVisibility(View.GONE);
                vh.iv_face4.setVisibility(View.GONE);
                vh.iv_face1.setVisibility(View.VISIBLE);
                vh.iv_face2.setVisibility(View.VISIBLE);
                vh.iv_face3.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(faceurls[0], vh.iv_face1);
                ImageLoadUtils.setImageForUrl(faceurls[1], vh.iv_face2);
                ImageLoadUtils.setImageForUrl(faceurls[2], vh.iv_face3);
                break;
            case 4:
                vh.iv_face1.setVisibility(View.GONE);
                vh.iv_face1.setVisibility(View.VISIBLE);
                vh.iv_face2.setVisibility(View.VISIBLE);
                vh.iv_face3.setVisibility(View.VISIBLE);
                vh.iv_face4.setVisibility(View.VISIBLE);
                ImageLoadUtils.setImageForUrl(faceurls[0], vh.iv_face1);
                ImageLoadUtils.setImageForUrl(faceurls[1], vh.iv_face2);
                ImageLoadUtils.setImageForUrl(faceurls[2], vh.iv_face3);
                ImageLoadUtils.setImageForUrl(faceurls[3], vh.iv_face4);
                break;
        }
        return convertView;
    }

    private void intentShowActivity(ArrayList<String> sList) {
        Intent intent = new Intent(mContext, ShowImageActivity.class);
        intent.putStringArrayListExtra("url",sList);
        mContext.startActivity(intent);
    }


    private class MViewHolder {
        LinearLayout ll_toXQ, ll_images2;
        RoundImageView iv_face1, iv_face2, iv_face3, iv_face4,iv_face;
        ImageView  iv_idcard;
        TextView tv_name;
        TextView tv_time;
        TextView tv_zheng;
        TextView tv_state;
        TextView tv_address;
        TextView tv_label;//草根英雄
        TextView tv_info;//具体信息
        TextView tv_number;//fragment_live_help_me_have_number   具体人数
    }

}
