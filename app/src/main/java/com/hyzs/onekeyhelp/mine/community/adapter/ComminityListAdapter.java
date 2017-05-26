package com.hyzs.onekeyhelp.mine.community.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.community.bean.MyCommunityEntity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ComminityListAdapter extends BaseAdapter {
    private static final String TAG = "ComminityListAdapter";
    private Context mContext;
    private List<MyCommunityEntity.MycommunityBean> list;
    MySharedPreferences mySharedPreferences;

    public ComminityListAdapter(Context mContext, List<MyCommunityEntity.MycommunityBean> list) {
        this.mContext = mContext;
        this.list = list;
        mySharedPreferences = MySharedPreferences.getInstance(mContext);
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
        MyHolder holder;
        if (null == view) {
            view = View.inflate(mContext, R.layout.item_comminuty_list, null);
            holder = new MyHolder();
            holder.img = (ImageView) view.findViewById(R.id.item_comminuty_list_set);
            holder.name = (TextView) view.findViewById(R.id.item_comminuty_list_name);
            holder.address = (TextView) view.findViewById(R.id.item_comminuty_list_address);
            holder.set = (TextView) view.findViewById(R.id.item_comminuty_list_set_text);
            holder.ll = (LinearLayout) view.findViewById(R.id.item_community_set_ll);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        final MyCommunityEntity.MycommunityBean bean = list.get(position);
        holder.name.setText(bean.getCommunityName());
        holder.address.setText("位置：" + bean.getGPSDesc());
        if (bean.getM_default() == 0) {
            holder.set.setText("设为默认");
            holder.img.setVisibility(View.VISIBLE);
        }else{
            holder.set.setText("已默认");
            holder.img.setVisibility(View.GONE);
        }

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getM_default() == 1) {
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("curr_uid", MySharedPreferences.getUserId(mContext));
                map.put("id", bean.getId() + "");
                NetRequest.ParamPostRequest(PortUtil.SetDefaultCommunity, map, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                            if ("10000".equals(jsonObject.getString("code"))) {
                                for (int i = 0; i < list.size(); i++) {
                                    if (i == position) {
                                        list.get(i).setM_default(1);
                                        continue;
                                    }
                                    list.get(i).setM_default(0);
                                }
                                mySharedPreferences.setString("communityName", bean.getCommunityName());
                                mySharedPreferences.setString("communityId", bean.getId() + "");
                                notifyDataSetChanged();
                            } else
                                Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        return view;
    }

    private class MyHolder {
        private LinearLayout ll;
        private ImageView img;
        private TextView name, address, set;
    }
}
