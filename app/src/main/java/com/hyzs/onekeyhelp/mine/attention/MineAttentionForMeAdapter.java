package com.hyzs.onekeyhelp.mine.attention;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MineAttentionForMeAdapter extends BaseAdapter {

    private Context context;
    private List<MineAttentionBean.UserInfoBean> attentionBean;


    public MineAttentionForMeAdapter(Context context, List<MineAttentionBean.UserInfoBean> attentionBean) {
        this.context = context;
        this.attentionBean = attentionBean;
    }


    @Override
    public int getCount() {
        return attentionBean.size();
    }

    @Override
    public Object getItem(int position) {
        return attentionBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MineAttentionBean.UserInfoBean bean = attentionBean.get(position);
        MyHolder holder;
        if (convertView == null) {
            holder = new MyHolder();
            convertView = View.inflate(context, R.layout.item_mine_attention_for_me, null);
            holder.name = (TextView) convertView.findViewById(R.id.item_contact_list_name);
            holder.icon = (CircleImageView) convertView.findViewById(R.id.item_contact_list_icon);
            holder.liaotian_image = (ImageView) convertView.findViewById(R.id.imageView);
            holder.identity = (ImageView) convertView.findViewById(R.id.item_contact_list_identity);
            holder.zheng = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_zheng);
            holder.community = (TextView) convertView.findViewById(R.id.item_contact_list_community_name);
            holder.shang = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_shang);
            holder.fu = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_fu);
            holder.wu = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_wu);
            holder.notification = (CircleImageView) convertView.findViewById(R.id.item_contact_list_notification);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        holder.name.setText(bean.getUesrname());
        if (bean.getNewInfoCount()!=0) {
            holder.notification.setVisibility(View.VISIBLE);
        }else holder.notification.setVisibility(View.GONE);
        ImageLoadUtils.setImageForUrl(bean.getFace(), holder.icon);

        /**点击聊天*/
        holder.liaotian_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IntentChatActivity.class);
                intent.putExtra("one", bean.getUserid() + "");
                intent.putExtra("userName", bean.getUesrname() + "");
                context.startActivity(intent);
            }
        });

        /**点击查看个人主页*/
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MineHomeActivity.class);
                intent.putExtra("targetUserId", bean.getUserid() + "");
                context.startActivity(intent);

            }
        });
        return convertView;
    }

    class MyHolder {
        ImageView identity;
        ImageView liaotian_image;
        TextView name, zheng, community, shang, fu, wu;
        CircleImageView icon ,notification;
    }
}
