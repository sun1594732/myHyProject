package com.hyzs.onekeyhelp.mine.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.ContactListEntity;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineUrgentAdapter extends BaseAdapter {
    private List<ContactListEntity.ContactListBean> list;
    private Context context;

    public MineUrgentAdapter(List<ContactListEntity.ContactListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mine_urgent, null);
            holder.mIcon = (CircleImageView) convertView.findViewById(R.id.item_contact_list_icon);
            holder.mCommunity = (TextView) convertView.findViewById(R.id.item_contact_list_community_name);
            holder.mName = (TextView) convertView.findViewById(R.id.item_contact_list_name);
            holder.mChat = (ImageView) convertView.findViewById(R.id.imageView);
            holder.mRemove = (ImageView) convertView.findViewById(R.id.remove);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        final ContactListEntity.ContactListBean bean = list.get(position);
        holder.mName.setText(bean.getUserNick());
        holder.mCommunity.setText(bean.getCommunity());
        if (TextUtils.isEmpty(bean.getAvatar())) {
            holder.mIcon.setImageResource(R.mipmap.replace_hybb);
        } else NetRequest.loadImg(context, bean.getAvatar(), holder.mIcon);
        holder.mChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreferences.getInstance(context).setString("Img", bean.getAvatar());
                NotifyMsgCountUtil.notifyMsg(bean.getTargetUserId() + "");
                Intent intent = new Intent();
                intent.putExtra("one", bean.getTargetUserId() + "");
                intent.putExtra("userName", bean.getUserNick());
                intent.setClass(context, IntentChatActivity.class);
                context.startActivity(intent);
            }
        });
        holder.mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetRequest.UrgentContacttRequest(MySharedPreferences.getUserId(context), bean.getTargetUserId() + "", "N", new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                        if ("10000".equals(bean.getCode())) {
                            ToastSingleUtil.showToast(context, "取消紧急联系人成功");
                            list.remove(position);
                            notifyDataSetChanged();
                        } else ToastSingleUtil.showToast(context, bean.getMessage());
                    }
                });
            }
        });
        return convertView;
    }

    class ViewHolder {
        CircleImageView mIcon;
        TextView mName, mCommunity;
        ImageView mChat, mRemove;
    }
}
