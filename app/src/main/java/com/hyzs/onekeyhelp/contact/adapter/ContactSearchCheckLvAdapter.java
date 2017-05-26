package com.hyzs.onekeyhelp.contact.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.ContactSearchBean;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactSearchCheckLvAdapter extends BaseAdapter {
    private List<ContactSearchBean.ContactSearchResultListBean> list;
    private Context context;

    public ContactSearchCheckLvAdapter(List<ContactSearchBean.ContactSearchResultListBean> list, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_activity_contact_search_result_lv, null);
            holder = new ViewHolder();
            holder.icon = (CircleImageView) convertView.findViewById(R.id.item_contact_search_icon);
            holder.name = (TextView) convertView.findViewById(R.id.item_contact_search_name);
            holder.status = (TextView) convertView.findViewById(R.id.item_contact_search_status);
            holder.identity = (ImageView) convertView.findViewById(R.id.item_contact_search_identity);
            holder.zheng = (TextView) convertView.findViewById(R.id.item_contact_search_zheng);
            holder.shang = (TextView) convertView.findViewById(R.id.item_contact_search_shang);
            holder.fu = (TextView) convertView.findViewById(R.id.item_contact_search_fu);
            holder.wu = (TextView) convertView.findViewById(R.id.item_contact_search_wu);
            holder.jiu = (TextView) convertView.findViewById(R.id.item_contact_search_jiu);
            holder.ke = (TextView) convertView.findViewById(R.id.item_contact_search_ke);
            holder.jie = (TextView) convertView.findViewById(R.id.item_contact_search_jie);
            holder.lin = (TextView) convertView.findViewById(R.id.item_contact_search_lin);
            holder.jin = (TextView) convertView.findViewById(R.id.item_contact_search_jin);
            holder.wei = (TextView) convertView.findViewById(R.id.item_contact_search_wei);
            holder.jia = (TextView) convertView.findViewById(R.id.item_contact_search_jia);
            holder.community = (TextView) convertView.findViewById(R.id.item_contact_search_community_name);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
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
        final ContactSearchBean.ContactSearchResultListBean bean = list.get(position);
        if (bean.getRNA() == 1) {
            holder.identity.setVisibility(View.VISIBLE);
        } else holder.identity.setVisibility(View.GONE);
        if (bean.getNCCS() == 1) {
            holder.zheng.setVisibility(View.VISIBLE);
        } else holder.zheng.setVisibility(View.GONE);
        if (TextUtils.isEmpty(bean.getAvatar())) {
            holder.icon.setImageResource(R.mipmap.icon_replace);
        } else NetRequest.loadImg(context, bean.getAvatar(), holder.icon);
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
        holder.name.setText(bean.getNickName());
        holder.community.setText(bean.getCommunity());
        holder.status.setText(bean.getIsExists());
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MineHomeActivity.class);
                intent.putExtra("targetUserId", bean.getUid() + "");
                context.startActivity(intent);
            }
        });
        handleStatus(holder.status, bean.getIsExists(), bean);
        return convertView;
    }

    class ViewHolder {
        CircleImageView icon;
        TextView name, status, zheng, community, shang, fu, wu, jiu, ke, jie, lin, jin, wei, jia;
        ImageView identity;
    }

    private void handleStatus(final TextView tv, String status, final ContactSearchBean.ContactSearchResultListBean bean) {
        switch (status) {
            case "接受":
                tv.setText("通过验证");
                tv.setTextColor(ContextCompat.getColor(context, R.color.color_f));
                tv.setBackgroundResource(R.drawable.shape_1ccd9b_6_soild);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, String> param = new HashMap<>();
                        param.put("currId", MySharedPreferences.getUserId(context));
                        param.put("targetUserId", bean.getUid() + "");
                        param.put("groupName", "1");
                        param.put("reqType", "1");
                        param.put("addType", "0");
                        param.put("phone", "");
                        param.put("trueName", "");
                        NetRequest.ParamPostRequest(PortUtil.SearchCheckContact, param, new NetRequest.getDataCallback() {
                            @Override
                            public void getData(String data) {
                                DataStatusBean bean1 = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                                if ("10000".equals(bean1.getCode())) {
                                    tv.setText("已添加");
                                    tv.setTextColor(ContextCompat.getColor(context, R.color.color_9));
                                }
                            }
                        });
                    }
                });
                break;
            case "已添加":
                tv.setText("已添加");
                tv.setBackgroundResource(R.color.color_f);
                tv.setTextColor(ContextCompat.getColor(context, R.color.color_9));
                break;
            case "等待验证":
                tv.setBackgroundResource(R.color.color_f);
                tv.setText("等待验证");
                tv.setTextColor(ContextCompat.getColor(context, R.color.color_9));
                break;
            case "未添加":
                tv.setBackgroundResource(R.color.color_f);
                tv.setText("未添加");
                tv.setTextColor(ContextCompat.getColor(context, R.color.color_9));
                break;
        }
    }
}
