package com.hyzs.onekeyhelp.family.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.bean.FamilyAddMemberBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.List;

import static com.hyzs.onekeyhelp.R.mipmap.chat;
import static com.hyzs.onekeyhelp.R.mipmap.identity;


public class FamilyAddMemberAdapter extends BaseAdapter {
    static int array[];
    private static StringBuilder sb;
    private Context mContext;
    private static List<FamilyAddMemberBean.FamilySearchAddListBean> list;

    public FamilyAddMemberAdapter(Context mContext, List<FamilyAddMemberBean.FamilySearchAddListBean> list) {
        this.mContext = mContext;
        this.list = list;
        sb = new StringBuilder();
        array = new int[list.size()];
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
    public View getView(final int position, View view, ViewGroup parent) {
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
            holder.check = (CheckBox) view.findViewById(R.id.item_family_member_check);
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
        final FamilyAddMemberBean.FamilySearchAddListBean bean = list.get(position);
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
        if (array[position] == 0) {
            holder.check.setChecked(false);
        }else holder.check.setChecked(true);
        if (!TextUtils.isEmpty(bean.getIdentityMark())) {
            String[] s = bean.getIdentityMark().split(",");
            for (int i = 0; i < s.length; i++) {
                switch (s[i].trim()) {
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
        holder.check.setVisibility(View.VISIBLE);
        holder.chat.setVisibility(View.GONE);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.check.setChecked(!holder.check.isChecked());

                if (holder.check.isChecked()) {
                    array[position] = 1;
                } else array[position] = 0;
            }
        });
        holder.remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }


    public class MyHolder {
        public ImageView chat, identity;
        CheckBox check;
        private RoundImageView icon;
        private TextView name, remark, zheng, shang, fu, wu, jiu, ke, jie, lin, jin, wei, jia;
    }

    public static String getProject() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                if (sb.toString().contains(list.get(i).getUserid() + "")) {
                    continue;
                }
                sb.append(list.get(i).getUserid() + ",");
            }
        }
        String s = sb.toString();
        if (s.length()!=0) {
            return s.substring(0, s.length() - 1);
        }else return s;

    }
}
