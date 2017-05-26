package com.hyzs.onekeyhelp.home.forum.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.home.forum.bean.ForumDetailCommentBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class ForumDetailCommentAdapter extends BaseAdapter {
    private List<ForumDetailCommentBean.ForumCommentListBean> list;
    private Context context;

    public ForumDetailCommentAdapter(List<ForumDetailCommentBean.ForumCommentListBean> list, Context context) {
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_circle_detail_comment, null);
            holder = new ViewHolder();
            holder.icon = (CircleImageView) convertView.findViewById(R.id.item_circle_detail_comment_icon);
            holder.name = (TextView) convertView.findViewById(R.id.item_circle_detail_comment_name);
            holder.gender = (TextView) convertView.findViewById(R.id.item_circle_detail_comment_gender);
            holder.content = (TextView) convertView.findViewById(R.id.item_circle_detail_comment_content);
            holder.praise = (TextView) convertView.findViewById(R.id.item_circle_detail_comment_praise);
            holder.time = (TextView) convertView.findViewById(R.id.item_circle_detail_comment_time);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        final ForumDetailCommentBean.ForumCommentListBean bean = list.get(position);
        NetRequest.loadImg(context, bean.getAvatar(), holder.icon);
        holder.name.setText(bean.getNickName());
        holder.praise.setText(bean.getFc_Count() + "");
        holder.time.setText(bean.getDiffTime());
        holder.content.setText(bean.getFc_Content());
        holder.gender.setText(bean.getSex());
        switch (bean.getSex()) {
            case "男":
                holder.gender.setBackgroundResource(R.drawable.shape_1ccd9b_6_soild);
                holder.gender.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.mipmap.man), null, null, null);
                break;
            case "女":
                holder.gender.setBackgroundResource(R.drawable.shape_fc999b_6soild);
                holder.gender.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.mipmap.woman), null, null, null);
                break;
        }
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MineHomeActivity.class);
                intent.putExtra("targetUserId", list.get(position).getFc_UserID()+"");
                context.startActivity(intent);
            }
        });
        holder.praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> param = new HashMap<String, String>();
                param.put("currId", MySharedPreferences.getUserId(context));
                param.put("commentId", bean.getFc_ForumID() + "");
                NetRequest.ParamPostRequest(PortUtil.ForumDetailCommentPraise, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        DataStatusBean bean1 = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                        if ("10000".equals(bean1.getCode())) {
                            holder.praise.setText((Integer.parseInt(holder.praise.getText().toString()) + 1) + "");
                        }
                    }
                });
            }
        });
        return convertView;
    }

    class ViewHolder {
        CircleImageView icon;
        TextView name, gender, time, content, praise;
    }
}
