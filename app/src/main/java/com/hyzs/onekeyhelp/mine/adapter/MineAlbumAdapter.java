package com.hyzs.onekeyhelp.mine.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.mine.bean.MineAlbumBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wubin on 2017/3/27.
 */

public class MineAlbumAdapter extends BaseAdapter {
    private List<MineAlbumBean.PersonalAlbumDynamicListBean> list;
    private Context context;
    private boolean isMineAlbum;

    public MineAlbumAdapter(List<MineAlbumBean.PersonalAlbumDynamicListBean> list, Context context, boolean isMineAlbum) {
        this.list = list;
        this.context = context;
        this.isMineAlbum = isMineAlbum;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mine_album_lv, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.item_mine_album_lv_date);
            holder.title = (TextView) convertView.findViewById(R.id.item_mine_album_lv_title);
            holder.month = (TextView) convertView.findViewById(R.id.item_mine_album_lv_month);
            holder.totalCount = (TextView) convertView.findViewById(R.id.item_mine_album_lv_totalCount);
            holder.recyclerView = (RecyclerView) convertView.findViewById(R.id.item_mine_album_lv_recycler);
            holder.recyclerViewSmall = (RecyclerView) convertView.findViewById(R.id.item_mine_album_lv_recycler_onePic);
            holder.del = (TextView) convertView.findViewById(R.id.item_mine_album_del);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        if (isMineAlbum) {
            holder.del.setVisibility(View.VISIBLE);
        } else
            holder.del.setVisibility(View.GONE);
        final MineAlbumBean.PersonalAlbumDynamicListBean bean = list.get(position);
        String date1 = new SimpleDateFormat("dd/MM").format(new Date(Long.parseLong(bean.getPA_PublishDateTime()) * 1000));
        String date2 = new SimpleDateFormat("dd/MM").format(new Date(System.currentTimeMillis()));
        if (date1.split("/")[0].equals(date2.split("/")[0]) && date1.split("/")[1].equals(date2.split("/")[1])) {
            holder.date.setText("今天");
            holder.month.setVisibility(View.INVISIBLE);
        } else {
            holder.date.setText(date1.split("/")[0]);
            if ("0".equals(date1.split("/")[1].substring(0, 1))) {
                holder.month.setText(date1.split("/")[1].substring(1, 2) + "月");
            } else
                holder.month.setText(date1.split("/")[1] + "月");
            holder.month.setVisibility(View.VISIBLE);
        }
        holder.title.setText(bean.getPA_Content());
        ArrayList<String> ImagePath = new ArrayList<>();
        for (MineAlbumBean.PersonalAlbumDynamicListBean.ImagesBean bean1 : bean.getImages()) {
            ImagePath.add(bean1.getAI_URL());
        }
        holder.recyclerView.setVisibility(View.GONE);
        holder.totalCount.setVisibility(View.GONE);
        holder.recyclerViewSmall.setVisibility(View.GONE);
        if (ImagePath.size() > 4) {
            holder.totalCount.setVisibility(View.VISIBLE);
            holder.recyclerViewSmall.setVisibility(View.VISIBLE);
            holder.recyclerViewSmall.setHasFixedSize(true);
            holder.recyclerViewSmall.setLayoutManager(new GridLayoutManager(context, 2));
            holder.recyclerViewSmall.setAdapter(new MineAlbumSmallRecyclerAdapter(context, ImagePath));
            holder.totalCount.setText("共 " + ImagePath.size() + " 张");
        } else {
            holder.recyclerView.setVisibility(View.VISIBLE);
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
            holder.recyclerView.setAdapter(new MineAlbumRecyclerAdapter(context, ImagePath));
        }
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> param = new ArrayMap<String, String>();
                param.put("currId", MySharedPreferences.getUserId(context));
                param.put("type", "0");
                param.put("T_ID", bean.getID() + "");
                NetRequest.ParamPostRequest(PortUtil.MyDoDel, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {
                        DataStatusBean bean1 = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                        if ("10000".equals(bean1.getCode())) {
                            list.remove(position);
                            notifyDataSetChanged();
                        } else ToastSingleUtil.showToast(context, bean1.getMessage());
                    }
                });
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView date, title, month, totalCount, del;
        RecyclerView recyclerView, recyclerViewSmall;
    }
}
