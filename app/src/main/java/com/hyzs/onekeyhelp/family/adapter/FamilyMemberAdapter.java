package com.hyzs.onekeyhelp.family.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.bean.FamilyMemberBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.List;

public class FamilyMemberAdapter extends BaseAdapter {

    private Context mContext;
    private boolean isShow = false;
    private List<FamilyMemberBean.FamilyMemberListModelBean> list;

    public FamilyMemberAdapter(Context mContext, List<FamilyMemberBean.FamilyMemberListModelBean> list) {
        this.mContext = mContext;
        this.list = list;
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
    public View getView(int position, View view, ViewGroup parent) {
        final MyHolder holder;
        if (null == view) {
            view = View.inflate(mContext, R.layout.item_family_member, null);
            holder = new MyHolder();
            holder.identity = (ImageView) view.findViewById(R.id.item_contact_list_identity);
            holder.zheng = (TextView) view.findViewById(R.id.item_contact_list_markContainer_zheng);
            holder.shang = (TextView) view.findViewById(R.id.item_contact_list_markContainer_shang);
            holder.fu = (TextView) view.findViewById(R.id.item_contact_list_markContainer_fu);
            holder.wu = (TextView) view.findViewById(R.id.item_contact_list_markContainer_wu);
            holder.jiu = (TextView) view.findViewById(R.id.item_contact_list_markContainer_jiu);
            holder.ke = (TextView) view.findViewById(R.id.item_contact_list_markContainer_ke);
            holder.jie = (TextView) view.findViewById(R.id.item_contact_list_markContainer_jie);
            holder.lin = (TextView) view.findViewById(R.id.item_contact_list_markContainer_lin);
            holder.jin = (TextView) view.findViewById(R.id.item_contact_list_markContainer_jin);
            holder.wei = (TextView) view.findViewById(R.id.item_contact_list_markContainer_wei);
            holder.jia = (TextView) view.findViewById(R.id.item_contact_list_markContainer_jia);
            holder.check = (ImageView) view.findViewById(R.id.item_family_member_checkImg);
            holder.chat = (ImageView) view.findViewById(R.id.item_family_member_chat);
            holder.icon = (RoundImageView) view.findViewById(R.id.item_family_member_icon);
            holder.name = (TextView) view.findViewById(R.id.item_family_member_name);
            holder.remark = (TextView) view.findViewById(R.id.item_family_member_set_remark);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        holder.shang.setVisibility(View.GONE);
        holder.fu.setVisibility(View.GONE);
        holder.wu.setVisibility(View.GONE);
        holder.jiu.setVisibility(View.GONE);
        holder.ke.setVisibility(View.GONE);
        holder.jie.setVisibility(View.GONE);
        holder.lin.setVisibility(View.GONE);
        holder.jin.setVisibility(View.GONE);
        holder.wei.setVisibility(View.GONE);
        holder.jia.setVisibility(View.GONE);
        final FamilyMemberBean.FamilyMemberListModelBean bean = list.get(position);
        if (TextUtils.isEmpty(bean.getFace())) {
            holder.icon.setImageResource(R.mipmap.icon_replace);
        } else NetRequest.loadImg(mContext, bean.getFace(), holder.icon);
        holder.name.setText(bean.getUesrname());
        if (bean.getNCCS() == 0) {
            holder.zheng.setVisibility(View.GONE);
        } else holder.zheng.setVisibility(View.VISIBLE);
        if (bean.getRNA() == 0) {
            holder.identity.setVisibility(View.GONE);
        } else holder.identity.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(bean.getIdentityMark())) {
            String[] s = bean.getIdentityMark().split(",");
            for (String value : s) {
                switch (value.trim()) {
                    case "1":
                        holder.shang.setVisibility(View.VISIBLE);
                        break;
                    case "2":
                        holder.fu.setVisibility(View.VISIBLE);
                        break;
                    case "3":
                        holder.wu.setVisibility(View.VISIBLE);
                        break;
                    case "4":
                        holder.jiu.setVisibility(View.VISIBLE);
                        break;
                    case "5":
                        holder.ke.setVisibility(View.VISIBLE);
                        break;
                    case "6":
                        holder.jie.setVisibility(View.VISIBLE);
                        break;
                    case "7":
                        holder.lin.setVisibility(View.VISIBLE);
                        break;
                    case "8":
                        holder.jin.setVisibility(View.VISIBLE);
                        break;
                    case "9":
                        holder.wei.setVisibility(View.VISIBLE);
                        break;
                    case "10":
                        holder.jia.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }

        if (list.get(position).isCheck() == 1) {
            holder.check.setImageResource(R.mipmap.add_community_checked);
        } else holder.check.setImageResource(R.mipmap.add_community_unchecked);

        if (isShow) {
            holder.check.setVisibility(View.VISIBLE);
            holder.chat.setVisibility(View.GONE);
        } else {
            holder.check.setVisibility(View.GONE);
            holder.chat.setVisibility(View.VISIBLE);
        }
//        holder.check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (bean.isCheck()) {
//                    holder.check.setImageResource(R.mipmap.add_community_unchecked);
//                    bean.setCheck(false);
//                } else {
//                    holder.check.setImageResource(R.mipmap.add_community_checked);
//                    bean.setCheck(true);
//                }
//            }
//        });
        holder.remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MineHomeActivity.class);
                intent.putExtra("targetUserId", bean.getUserid() + "");
                mContext.startActivity(intent);
            }
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreferences.getInstance(mContext).setString("Img", bean.getFace());
                Intent intent = new Intent();
                intent.putExtra("one", bean.getUserid() + "");
                intent.putExtra("userName", bean.getUesrname());
                intent.setClass(mContext, IntentChatActivity.class);
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    public void showCheck(boolean isShow) {
        this.isShow = isShow;
        notifyDataSetChanged();
    }

    public class MyHolder {
        public ImageView chat, identity, check;
        private RoundImageView icon;
        private TextView name, remark, zheng, shang, fu, wu, jiu, ke, jie, lin, jin, wei, jia;
    }
}
