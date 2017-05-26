package com.hyzs.onekeyhelp.lifehelp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.lifehelp.bean.LifeHelpOtherBean;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.util.DateFormatUtils;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hyzs123 on 2017/3/19.
 */

public class LiveMyHelpAdapter extends BaseAdapter {

    private Context mContext;

    private LifeHelpOtherBean lifeHelpOtherBean;
    private LifeHelpOtherBean.MyHelpListsBean listsBean;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public LiveMyHelpAdapter(Context mContext, LifeHelpOtherBean lifeHelpOtherBean) {
        this.mContext = mContext;
        this.lifeHelpOtherBean = lifeHelpOtherBean;


    }

    @Override
    public int getCount() {
        return lifeHelpOtherBean.getMyHelpLists().size();
    }

    @Override
    public Object getItem(int position) {
        return lifeHelpOtherBean.getMyHelpLists().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        listsBean = lifeHelpOtherBean.getMyHelpLists().get(position);

        MViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_live_fragment__my_help, null);
            vh = new MViewHolder();
            vh.iv_face = (RoundImageView) convertView.findViewById(R.id.live_fragment_my_help_list_iv_face);
            vh.iv_idcard = (ImageView) convertView.findViewById(R.id.live_fragment_my_help_list_iv_idcard);
            vh.tv_name = (TextView) convertView.findViewById(R.id.live_fragment_my_help_list_tv_name);
            vh.tv_time = (TextView) convertView.findViewById(R.id.live_fragment_my_help_list_tv_time);
            vh.tv_zheng = (TextView) convertView.findViewById(R.id.live_fragment_my_help_list_tv_zheng);
            vh.tv_state = (TextView) convertView.findViewById(R.id.live_fragment_my_help_list_tv_state);
            vh.tv_address = (TextView) convertView.findViewById(R.id.live_fragment_my_help_list_tv_address);
            vh.tv_info = (TextView) convertView.findViewById(R.id.live_fragment_my_help_list_tv_info);
            vh.read = (CircleImageView) convertView.findViewById(R.id.live_fragment_my_help_list_read);
            convertView.setTag(vh);
        } else {
            vh = (MViewHolder) convertView.getTag();
        }
        if (TextUtils.isEmpty(listsBean.getAvatar())) {
            vh.iv_face.setImageResource(R.mipmap.icon_replace);
        } else
            ImageLoadUtils.setImageForUrl(listsBean.getAvatar(), vh.iv_face);
        //处理数据
        if (listsBean.getHelp_See() == 0) {
            vh.read.setVisibility(View.VISIBLE);
        } else vh.read.setVisibility(View.INVISIBLE);
        if (listsBean.getRNA() == 1) {//判断是否通过实名认证
            vh.iv_idcard.setVisibility(View.VISIBLE);
        } else {
            vh.iv_idcard.setVisibility(View.GONE);
        }
        if (listsBean.getNCCS() == 1) {//判断是否通过居委会认证
            vh.tv_zheng.setVisibility(View.VISIBLE);
        } else {
            vh.tv_zheng.setVisibility(View.GONE);
        }
        vh.tv_address.setText(listsBean.getCommunity());//社区名称

        switch (listsBean.getSeek_State()) {
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

        vh.tv_time.setText(DateFormatUtils.Time2Date((listsBean.getSeek_Time() * 1000) + ""));
        vh.tv_name.setText(listsBean.getNickName());
        vh.tv_info.setText(listsBean.getSeek_Reminder());


        return convertView;
    }

    private class MViewHolder {
        CircleImageView  read;
        RoundImageView iv_face;
        ImageView iv_idcard;
        TextView tv_name;
        TextView tv_time;
        TextView tv_zheng;
        TextView tv_state;
        TextView tv_address;
        TextView tv_info;


    }


}
