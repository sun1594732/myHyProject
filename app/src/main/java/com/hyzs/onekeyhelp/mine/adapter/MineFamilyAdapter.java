package com.hyzs.onekeyhelp.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.bean.MineFamilyBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/28.
 */

public class MineFamilyAdapter extends BaseAdapter {
    private Context mContxet;
    private List<MineFamilyBean.PersonalCenterFamilyMemberBean> list;

    public MineFamilyAdapter(Context mContxet, List<MineFamilyBean.PersonalCenterFamilyMemberBean> list) {
        this.mContxet = mContxet;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContxet).inflate(R.layout.item_mine_family, null);
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

            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }

        if (list.get(position).getRNA() == 1) {
            itemHolder.identity.setVisibility(View.VISIBLE);
        } else itemHolder.identity.setVisibility(View.GONE);
        if (list.get(position).getNCCS() == 1) {
            itemHolder.zheng.setVisibility(View.VISIBLE);
        } else itemHolder.zheng.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(list.get(position).getIdentityMark())) {
            String[] s = list.get(position).getIdentityMark().split(",");
            for (int is = 0; is < s.length; is++) {
                switch (s[is].trim()) {
                    case "1":
                        itemHolder.shang.setVisibility(View.VISIBLE);
                        break;
                    case "2":
                        itemHolder.fu.setVisibility(View.VISIBLE);
                        break;
                    case "3":
                        itemHolder.wu.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }

        itemHolder.userName.setText(list.get(position).getNickName());
        NetRequest.loadImg(mContxet, list.get(position).getAvatar(), itemHolder.userIcon);
        itemHolder.liao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreferences.getInstance(mContxet).setString("FriendsName", list.get(position).getNickName());
                MySharedPreferences.getInstance(mContxet).setString("Img", list.get(position).getAvatar());
                String i;
                int s1 = list.get(position).getUid();
                Log.e("ssss", "onClick: " + s1);
                i = Integer.toString(s1);
                Intent intent = new Intent();
                intent.putExtra("one", i);
                intent.putExtra("userName", list.get(position).getNickName());
                intent.setClass(mContxet, IntentChatActivity.class);
                mContxet.startActivity(intent);
            }
        });


        return convertView;


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
        public TextView wu;
    }

}
