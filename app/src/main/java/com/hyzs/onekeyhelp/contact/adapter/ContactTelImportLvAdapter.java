package com.hyzs.onekeyhelp.contact.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.TelContactBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.ConfirmDialog;
import com.hyzs.onekeyhelp.util.ContactTelImportUtil;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.id;
import static com.hyzs.onekeyhelp.R.id.view;

public class ContactTelImportLvAdapter extends BaseAdapter {
    private Context context;
    private List<TelContactBean> list;
    private MySharedPreferences mySharedPreferences;
    private Dialog dialog;
    private boolean isUrgentStyle;

    public ContactTelImportLvAdapter(Context context, List<TelContactBean> list, boolean isUrgentStyle) {
        this.context = context;
        this.list = list;
        mySharedPreferences = MySharedPreferences.getInstance(context);
        this.isUrgentStyle = isUrgentStyle;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public TelContactBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tel_import_lv, null);
            holder = new ViewHolder();
            holder.icon = (CircleImageView) convertView.findViewById(R.id.item_tel_import_icon);
            holder.name = (TextView) convertView.findViewById(R.id.item_tel_import_name);
            holder.tel = (TextView) convertView.findViewById(R.id.item_tel_import_tel);
            holder.register = (TextView) convertView.findViewById(R.id.item_tel_import_register);
            holder.status = (TextView) convertView.findViewById(R.id.item_tel_import_status);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        final TelContactBean map = list.get(position);
        // TODO: 2017/4/12 设置联系人头像部分
        holder.name.setText(map.getContactName());
        if (!"0".equals(map.getId())) {
            holder.register.setText("已注册");
        }else holder.register.setText("未注册");
        holder.tel.setText("电话号码 ： " + map.getPhoneNumber());
        switch (map.getIsExist()) {
            case "0":
                holder.status.setText("已导入");
                holder.status.setClickable(false);
                holder.status.setTextColor(ContextCompat.getColor(context, R.color.color_d3));
                holder.status.setBackgroundColor(ContextCompat.getColor(context, R.color.color_f));
                break;
            case "1":
                holder.status.setText("等待验证");
                holder.status.setClickable(false);
                holder.status.setTextColor(ContextCompat.getColor(context, R.color.color_d3));
                holder.status.setBackgroundColor(ContextCompat.getColor(context, R.color.color_f));
                break;
            case "2":
                holder.status.setText("未添加");
                holder.status.setClickable(false);
                holder.status.setTextColor(ContextCompat.getColor(context, R.color.color_f));
                holder.status.setBackgroundColor(ContextCompat.getColor(context, R.color.color_1ccd9b));
                holder.status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isUrgentStyle) {
                            addFriend(map, holder, "1");
                            return;
                        }
                        dialog = ConfirmDialog.createConfirmLoading(context, "同时设置为紧急联系人", "取消", "确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addFriend(map, holder, "0");
                                dialog.dismiss();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addFriend(map, holder, "1");
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });
                break;
        }
        return convertView;
    }

    class ViewHolder {
        CircleImageView icon;
        TextView name, tel, status, register;
    }

    private void addFriend(final TelContactBean bean, final ViewHolder holder, String type) {
        String url = PortUtil.FriendAddCheck;
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(context));
        map.put("targetUserId", "0");
        map.put("groupName", "1");
        map.put("reqType", "0");
        map.put("addType", "1");
        map.put("phone", bean.getPhoneNumber());
        map.put("trueName", bean.getContactName());
        map.put("isSetUrgencyContact", type);
        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if ("10000".equals(jsonObject.getString("code"))) {
                        holder.status.setText("已导入");
                        holder.status.setClickable(false);
                        holder.status.setTextColor(ContextCompat.getColor(context, R.color.color_d3));
                        holder.status.setBackgroundColor(ContextCompat.getColor(context, R.color.color_f));
                    }else ToastSingleUtil.showToast(context, jsonObject.getString("message"));
                } catch (Exception e) {
                    ToastSingleUtil.showToast(context, "添加失败");
                    e.printStackTrace();
                }
            }
        });
    }
}
