package com.hyzs.onekeyhelp.contact.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.activity.ContactSearchActivity;
import com.hyzs.onekeyhelp.contact.bean.ContactSearchBean;
import com.hyzs.onekeyhelp.contact.fragment.ContactGroupFragment;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.ConfirmDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class ContactSearchLvAdapter extends BaseAdapter {
    private List<ContactSearchBean.ContactSearchResultListBean> list;
    private Context context;
    MySharedPreferences mySharedPreferences;
    private Dialog confirmDialog;
    private boolean isUrgentStyle;

    public ContactSearchLvAdapter(List<ContactSearchBean.ContactSearchResultListBean> list, Context context , boolean isUrgentStyle) {
        this.list = list;
        this.context = context;
        mySharedPreferences = MySharedPreferences.getInstance(context);
        this.isUrgentStyle = isUrgentStyle;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ContactSearchBean.ContactSearchResultListBean getItem(int position) {
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
            holder.img = (CircleImageView) convertView.findViewById(R.id.item_contact_search_icon);
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
        if (TextUtils.isEmpty(bean.getAvatar())) {
            holder.img.setImageResource(R.mipmap.icon_replace);
        } else
            NetRequest.loadImg(context, bean.getAvatar(), holder.img);
        holder.name.setText(bean.getNickName());
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
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUrgentStyle) {
                    addContact(bean, "1");
                    return;
                }
                confirmDialog = ConfirmDialog.createConfirmLoading(context, "同时设置为紧急联系人", "取消", "确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addContact(bean, "0");
                        confirmDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addContact(bean, "1");
                        confirmDialog.dismiss();
                    }
                });
                confirmDialog.show();
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MineHomeActivity.class);
                intent.putExtra("targetUserId", bean.getUid() + "");
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        CircleImageView img;
        TextView name, status, zheng, shang, fu, wu, jiu, ke, jie, lin, jin, wei, jia;
        ImageView identity;
    }

    private void addContact(final ContactSearchBean.ContactSearchResultListBean bean, String type) {
        Map<String, String> param = new ArrayMap<String, String>();
        param.put("currId", MySharedPreferences.getUserId(context));
        param.put("targetUserId", bean.getUid() + "");
        param.put("groupName", "1");
        param.put("reqType", "0");
        param.put("addType", "0");
        param.put("phone", "");
        param.put("trueName", "");
        param.put("isSetUrgencyContact", type);
        NetRequest.ParamDialogPostRequest(PortUtil.FriendAddCheck, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                ContactSearchActivity.handler.sendEmptyMessage(1);
                list.remove(bean);
                notifyDataSetChanged();
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {
                ToastUtils.showShort(context, "网络错误，添加失败");
            }
        });
    }
}
