package com.hyzs.onekeyhelp.contact.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.ContactListEntity;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MyAsycnTask;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;
import com.hyzs.onekeyhelp.widget.SectionedBaseAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wubin on 2017/3/9.
 */

public class PinnedLvAdapter extends SectionedBaseAdapter implements MyAsycnTask.MyCallBack {
    private MySharedPreferences mySharePreferences;
    private static final String TAG = "PinnedLvAdapter";
    private Context context;

    public void setContactBeanList(List<List<ContactListEntity.ContactListBean>> contactBeanList) {
        this.contactBeanList = contactBeanList;
    }

    public List<List<ContactListEntity.ContactListBean>> getContactBeanList() {
        return contactBeanList;
    }

    private List<List<ContactListEntity.ContactListBean>> contactBeanList = null;
    private char number[] = {'#', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public PinnedLvAdapter(Context context, List<List<ContactListEntity.ContactListBean>> contactBeanList) {
        this.context = context;
        this.contactBeanList = contactBeanList;
        mySharePreferences = MySharedPreferences.getInstance(context);
    }

    @Override
    public ContactListEntity.ContactListBean getItem(int section, int position) {
        return contactBeanList.get(section).get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return number.length;
    }

    @Override
    public int getCountForSection(int section) {
        return contactBeanList.get(section).size();
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        MyHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_contact_list, null);
            holder = new MyHolder();
            holder.name = (TextView) convertView.findViewById(R.id.item_contact_list_name);
            holder.icon = (CircleImageView) convertView.findViewById(R.id.item_contact_list_icon);
            holder.liaotian_image = (ImageView) convertView.findViewById(R.id.imageView);
            holder.identity = (ImageView) convertView.findViewById(R.id.item_contact_list_identity);
            holder.zheng = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_zheng);
            holder.community = (TextView) convertView.findViewById(R.id.item_contact_list_community_name);
            holder.shang = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_shang);
            holder.fu = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_fu);
            holder.wu = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_wu);
            holder.jiu = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_jiu);
            holder.ke = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_ke);
            holder.jie = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_jie);
            holder.lin = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_lin);
            holder.jin = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_jin);
            holder.wei = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_wei);
            holder.jia = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_jia);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
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
        final ContactListEntity.ContactListBean bean = contactBeanList.get(section).get(position);
        holder.name.setText(bean.getUserNick());
        if (bean.getRNA() == 1) {
            holder.identity.setVisibility(View.VISIBLE);
        } else holder.identity.setVisibility(View.GONE);
        if (bean.getNCCS() == 1) {
            holder.zheng.setVisibility(View.VISIBLE);
        } else holder.zheng.setVisibility(View.GONE);
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
        if (!TextUtils.isEmpty(bean.getAvatar().trim())) {
            NetRequest.loadImg(context, bean.getAvatar(), holder.icon);
        } else holder.icon.setImageResource(R.mipmap.icon_replace);
        holder.community.setText(bean.getCommunity());
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MineHomeActivity.class);
                intent.putExtra("targetUserId", bean.getTargetUserId() + "");
                context.startActivity(intent);
            }
        });
        holder.liaotian_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreferences.getInstance(context).setString("FriendsName", bean.getUserNick());
                MySharedPreferences.getInstance(context).setString("Img", bean.getAvatar());
                NotifyMsgCountUtil.notifyMsg(bean.getTargetUserId() + "");
                Intent intent = new Intent();
                intent.putExtra("one", bean.getTargetUserId()+"");
                intent.putExtra("userName", bean.getUserNick());
                intent.setClass(context, IntentChatActivity.class);
                context.startActivity(intent);

            }
        });
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.section_head, null);
        TextView textView = (TextView) view.findViewById(R.id.section_number);
        textView.setText(number[section] + "");
        if (!(contactBeanList.get(section).size() > 0)) {
            textView.setVisibility(View.GONE);
        }
        view.setTag("1");
        return view;
    }

    @Override
    public void SendResult(String result) {

    }

    class MyHolder {
        ImageView identity;
        ImageView liaotian_image;
        TextView name, zheng, community, shang, fu, wu, jiu, ke, jie, lin, jin, wei, jia;
        CircleImageView icon;
    }

}
