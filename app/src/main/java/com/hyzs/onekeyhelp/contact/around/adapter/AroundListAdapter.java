package com.hyzs.onekeyhelp.contact.around.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.SearchBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.ConfirmDialog;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class AroundListAdapter extends BaseAdapter {
    private static final String TAG = "AroundListAdapter";
    private Context mContext;
    private List<SearchBean.SearchAroundResultBean> list;
    private Dialog dialog;
    private int countNum = 10;
    private boolean isUrgentStyle;

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }

    public AroundListAdapter(Context mContext, List<SearchBean.SearchAroundResultBean> list, boolean isUrgentStyle) {
        this.mContext = mContext;
        this.list = list;
        this.isUrgentStyle = isUrgentStyle;
    }

    @Override
    public int getCount() {
        return list.size() > countNum ? countNum : list.size();
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
        MyHolder holder;
        if (null == view) {
            view = View.inflate(mContext, R.layout.item_around, null);
            holder = new MyHolder();
            holder.icon = (CircleImageView) view.findViewById(R.id.item_around_icon);
            holder.name = (TextView) view.findViewById(R.id.item_around_name);
            holder.community = (TextView) view.findViewById(R.id.item_around_community);
            holder.identity = (ImageView) view.findViewById(R.id.item_around_identity);
            holder.zheng = (TextView) view.findViewById(R.id.item_around_zheng);
            holder.shang = (TextView) view.findViewById(R.id.item_around_shang);
            holder.fu = (TextView) view.findViewById(R.id.item_around_fu);
            holder.wu = (TextView) view.findViewById(R.id.item_around_wu);
            holder.jiu = (TextView) view.findViewById(R.id.item_around_jiu);
            holder.ke = (TextView) view.findViewById(R.id.item_around_ke);
            holder.jie = (TextView) view.findViewById(R.id.item_around_jie);
            holder.lin = (TextView) view.findViewById(R.id.item_around_lin);
            holder.jin = (TextView) view.findViewById(R.id.item_around_jin);
            holder.wei = (TextView) view.findViewById(R.id.item_around_wei);
            holder.jia = (TextView) view.findViewById(R.id.item_around_jia);
            holder.addFriend = (TextView) view.findViewById(R.id.item_around_addFriend);
            holder.distance = (TextView) view.findViewById(R.id.item_around_distance);
            view.setTag(holder);
        } else {
            holder = (MyHolder) view.getTag();
        }
        final SearchBean.SearchAroundResultBean bean = list.get(position);
        holder.zheng.setVisibility(View.GONE);
        holder.shang.setVisibility(View.GONE);
        holder.fu.setVisibility(View.GONE);
        holder.wu.setVisibility(View.GONE);
        holder.identity.setVisibility(View.GONE);
        holder.jiu.setVisibility(View.GONE);
        holder.ke.setVisibility(View.GONE);
        holder.jie.setVisibility(View.GONE);
        holder.lin.setVisibility(View.GONE);
        holder.jin.setVisibility(View.GONE);
        holder.wei.setVisibility(View.GONE);
        holder.jia.setVisibility(View.GONE);
        if (TextUtils.isEmpty(bean.getAvatar())) {
            holder.icon.setImageResource(R.mipmap.icon_replace);
        } else NetRequest.loadImg(mContext, bean.getAvatar(), holder.icon);
        holder.name.setText(bean.getUserNick());
        holder.community.setText(bean.getCommunity());
        holder.distance.setText(bean.getDistance());
        if (!TextUtils.isEmpty(bean.getIdentityMark())) {
            String[] split = bean.getIdentityMark().split(",");
            for (String aSplit : split) {
                switch (aSplit) {
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
        if (bean.getRNA() == 0)
            holder.identity.setVisibility(View.GONE);
        else holder.identity.setVisibility(View.VISIBLE);
        if (bean.getNCCS() == 0)
            holder.zheng.setVisibility(View.GONE);
        else holder.zheng.setVisibility(View.VISIBLE);
        holder.addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUrgentStyle) {
                    addFriend(bean.getUid() + "", position, "1");
                    return;
                }
                dialog = ConfirmDialog.createConfirmLoading(mContext, "同时设置为紧急联系人", "取消", "确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addFriend(bean.getUid() + "", position, "0");
                        dialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addFriend(bean.getUid() + "", position, "1");
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySharedPreferences.isLogin(mContext)) {
                    ToastUtils.showShort(mContext, "请先登录.");
                    return;
                }
                Intent intent = new Intent(mContext, MineHomeActivity.class);
                intent.putExtra("targetUserId", bean.getUid() + "");
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    private void addFriend(String uId, final int position, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(mContext));
        map.put("targetUserId", uId);
        map.put("groupName", "1");
        map.put("reqType", "0");
        map.put("addType", "0");
        map.put("phone", "");
        map.put("trueName", "");
        map.put("isSetUrgencyContact", type);
        NetRequest.ParamPostRequest(PortUtil.FriendAddCheck, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    list.remove(position);
                    notifyDataSetChanged();
                } catch (Exception e) {
                    ToastSingleUtil.showToast(mContext, "添加失败");
                    e.printStackTrace();
                } finally {
                    if (dialog != null)
                        dialog.dismiss();
                }
            }
        });
    }

    private class MyHolder {
        private CircleImageView icon;
        private TextView name, community, zheng, shang, fu, wu, addFriend, distance, jiu, ke, jie, lin, jin, wei, jia;
        private ImageView identity;
    }
}
