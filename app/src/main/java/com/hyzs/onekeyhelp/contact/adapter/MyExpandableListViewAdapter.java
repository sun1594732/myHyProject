package com.hyzs.onekeyhelp.contact.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.ContactListEntity;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "MyExpandableListViewAda";
    private Context context;
    private List<String> group_list;
    List<List<ContactListEntity.ContactListBean>> item_list;

    public MyExpandableListViewAdapter(Context context, List<String> group_list, List<List<ContactListEntity.ContactListBean>> item_list) {
        this.context = context;
        this.group_list = group_list;
        this.item_list = item_list;
    }

    @Override
    public int getGroupCount() {
        return group_list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return item_list.get(groupPosition) == null ? 0 : item_list.get(groupPosition).size();

    }

    @Override
    public Object getGroup(int groupPosition) {

        return group_list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (item_list.get(groupPosition).size() == 0) {
            return null;
        }
        return item_list.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_group, null);
            groupHolder = new GroupHolder();
            groupHolder.groupName = (TextView) convertView.findViewById(R.id.expand_group_tv);
            groupHolder.groupArrow = (ImageView) convertView.findViewById(R.id.expand_group_arrow);
            convertView.setTag(groupHolder);
        } else groupHolder = (GroupHolder) convertView.getTag();
        groupHolder.groupName.setText(group_list.get(groupPosition));
        if (isExpanded) {
            groupHolder.groupName.setTextColor(ContextCompat.getColor(context, R.color.color_f));
            groupHolder.groupArrow.setImageResource(R.drawable.arrow_up);
            convertView.setBackgroundResource(R.color.color_1ccd9b);
        } else {
            groupHolder.groupName.setTextColor(ContextCompat.getColor(context, R.color.color_0));
            groupHolder.groupArrow.setImageResource(R.drawable.arrow_down);
            convertView.setBackgroundResource(R.color.color_f4);
        }
        convertView.setTag(R.id.contact_list, groupPosition);
        convertView.setTag(R.id.contact_search_add_hand, -1);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact_list, null);
            itemHolder = new ItemHolder();
            itemHolder.userName = (TextView) convertView.findViewById(R.id.item_contact_list_name);
            itemHolder.userIcon = (ImageView) convertView.findViewById(R.id.item_contact_list_icon);
            itemHolder.liao = (ImageView) convertView.findViewById(R.id.imageView);
            itemHolder.identity = (ImageView) convertView.findViewById(R.id.item_contact_list_identity);
            itemHolder.zheng = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_zheng);
            itemHolder.community = (TextView) convertView.findViewById(R.id.item_contact_list_community_name);
            itemHolder.shang = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_shang);
            itemHolder.fu = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_fu);
            itemHolder.wu = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_wu);
            itemHolder.jiu = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_jiu);
            itemHolder.ke = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_ke);
            itemHolder.jie = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_jie);
            itemHolder.lin = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_lin);
            itemHolder.jin = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_jin);
            itemHolder.wei = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_wei);
            itemHolder.jia = (TextView) convertView.findViewById(R.id.item_contact_list_markContainer_jia);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        itemHolder.shang.setVisibility(View.GONE);
        itemHolder.fu.setVisibility(View.GONE);
        itemHolder.wu.setVisibility(View.GONE);
        itemHolder.jiu.setVisibility(View.GONE);
        itemHolder.wu.setVisibility(View.GONE);
        itemHolder.jiu.setVisibility(View.GONE);
        itemHolder.ke.setVisibility(View.GONE);
        itemHolder.jie.setVisibility(View.GONE);
        itemHolder.lin.setVisibility(View.GONE);
        itemHolder.jin.setVisibility(View.GONE);
        itemHolder.wei.setVisibility(View.GONE);
        itemHolder.jia.setVisibility(View.GONE);
        if (item_list.get(groupPosition).get(childPosition).getRNA() == 1) {
            itemHolder.identity.setVisibility(View.VISIBLE);
        } else itemHolder.identity.setVisibility(View.GONE);
        if (item_list.get(groupPosition).get(childPosition).getNCCS() == 1) {
            itemHolder.zheng.setVisibility(View.VISIBLE);
        } else itemHolder.zheng.setVisibility(View.GONE);
        itemHolder.userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MineHomeActivity.class);
                intent.putExtra("targetUserId", item_list.get(groupPosition).get(childPosition).getTargetUserId() + "");
                context.startActivity(intent);
            }
        });
        if (!TextUtils.isEmpty(item_list.get(groupPosition).get(childPosition).getIdentityMark())) {
            String[] s = item_list.get(groupPosition).get(childPosition).getIdentityMark().split(",");
            for (int i = 0; i < s.length; i++) {
                switch (s[i].trim()) {
                    case "1":
                        itemHolder.shang.setVisibility(View.VISIBLE);
                        break;
                    case "2":
                        itemHolder.fu.setVisibility(View.VISIBLE);
                        break;
                    case "3":
                        itemHolder.wu.setVisibility(View.VISIBLE);
                        break;
                    case "4":
                        itemHolder.jiu.setVisibility(View.VISIBLE);
                        break;
                    case "5":
                        itemHolder.ke.setVisibility(View.VISIBLE);
                        break;
                    case "6":
                        itemHolder.jie.setVisibility(View.VISIBLE);
                        break;
                    case "7":
                        itemHolder.lin.setVisibility(View.VISIBLE);
                        break;
                    case "8":
                        itemHolder.jin.setVisibility(View.VISIBLE);
                        break;
                    case "9":
                        itemHolder.wei.setVisibility(View.VISIBLE);
                        break;
                    case "10":
                        itemHolder.jia.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
        itemHolder.userName.setText(item_list.get(groupPosition).get(childPosition).getUserNick());
        if (TextUtils.isEmpty(item_list.get(groupPosition).get(childPosition).getAvatar())) {
            itemHolder.userIcon.setImageResource(R.mipmap.icon_replace);
        } else
            NetRequest.loadImg(context, item_list.get(groupPosition).get(childPosition).getAvatar(), itemHolder.userIcon);
        itemHolder.liao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreferences.getInstance(context).setString("FriendsName", item_list.get(groupPosition).get(childPosition).getUserNick());
                String i;
                int s1 = item_list.get(groupPosition).get(childPosition).getTargetUserId();
                i = Integer.toString(s1);
                NotifyMsgCountUtil.notifyMsg(i);
                Intent intent = new Intent();
                intent.putExtra("one", i);
                intent.putExtra("userName", item_list.get(groupPosition).get(childPosition).getUserNick());
                intent.setClass(context, IntentChatActivity.class);
                context.startActivity(intent);
            }
        });


        convertView.setTag(R.id.contact_list, groupPosition);
        convertView.setTag(R.id.contact_search_add_hand, childPosition);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder {
        TextView groupName;
        ImageView groupArrow;
    }

    class ItemHolder {
        ImageView liao;
        TextView userName;
        ImageView userIcon;
        public ImageView identity;
        public TextView zheng;
        public TextView community;
        public TextView shang;
        public TextView fu;
        public TextView wu, jiu, ke, jie, lin, jin, wei, jia;
    }
}
