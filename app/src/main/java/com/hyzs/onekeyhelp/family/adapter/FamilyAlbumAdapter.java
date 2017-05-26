package com.hyzs.onekeyhelp.family.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.bean.FamilyAlbumBean;
import com.hyzs.onekeyhelp.mine.adapter.MineAlbumRecyclerAdapter;
import com.hyzs.onekeyhelp.mine.adapter.MineAlbumSmallRecyclerAdapter;
import com.hyzs.onekeyhelp.mine.bean.MineAlbumBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FamilyAlbumAdapter extends BaseAdapter {
    private List<FamilyAlbumBean.GroupAlbumDynamicListBean> list;
    private Context context;

    public FamilyAlbumAdapter(List<FamilyAlbumBean.GroupAlbumDynamicListBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mine_album_lv,null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.item_mine_album_lv_date);
            holder.title = (TextView) convertView.findViewById(R.id.item_mine_album_lv_title);
            holder.month = (TextView) convertView.findViewById(R.id.item_mine_album_lv_month);
            holder.del = (TextView) convertView.findViewById(R.id.item_mine_album_del);
            holder.totalCount = (TextView) convertView.findViewById(R.id.item_mine_album_lv_totalCount);
            holder.recyclerView = (RecyclerView) convertView.findViewById(R.id.item_mine_album_lv_recycler);
            holder.recyclerViewSmall = (RecyclerView) convertView.findViewById(R.id.item_mine_album_lv_recycler_onePic);
            convertView.setTag(holder);
        }else holder = (ViewHolder) convertView.getTag();
        FamilyAlbumBean.GroupAlbumDynamicListBean bean = list.get(position);
        String date1 = new SimpleDateFormat("dd/MM").format(new Date(Long.parseLong(bean.getGA_PublishDateTime())* 1000));
        String date2 = new SimpleDateFormat("dd/MM").format(new Date(System.currentTimeMillis()));
        if (date1.split("/")[0].equals(date2.split("/")[0])&&date1.split("/")[1].equals(date2.split("/")[1])){
            holder.date.setText("今天");
            holder.month.setVisibility(View.INVISIBLE);
        }else {
            holder.date.setText(date1.split("/")[0]);
            if ("0".equals(date1.split("/")[1].substring(0,1))) {
                holder.month.setText(date1.split("/")[1].substring(1,2)+"月");
            }else
                holder.month.setText(date1.split("/")[1]+"月");
            holder.month.setVisibility(View.VISIBLE);
        }
        holder.del.setVisibility(View.GONE);
        holder.title.setText(bean.getGA_Content());
        ArrayList<String>ImagePath = new ArrayList<>();
        for ( FamilyAlbumBean.GroupAlbumDynamicListBean.AlbumsBean bean1: bean.getAlbums()) {
            ImagePath.add(bean1.getGAI_URL()+"");
        }
        holder.recyclerView.setVisibility(View.GONE);
        holder.totalCount.setVisibility(View.GONE);
        holder.recyclerViewSmall.setVisibility(View.GONE);
        if (ImagePath.size()>4) {
            holder.totalCount.setVisibility(View.VISIBLE);
            holder.recyclerViewSmall.setVisibility(View.VISIBLE);
            holder.recyclerViewSmall.setHasFixedSize(true);
            holder.recyclerViewSmall.setLayoutManager(new GridLayoutManager(context, 2));
            holder.recyclerViewSmall.setAdapter(new MineAlbumSmallRecyclerAdapter(context, ImagePath));
            holder.totalCount.setText("共 "+ImagePath.size()+ " 张");
        }else {
            holder.recyclerView.setVisibility(View.VISIBLE);
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
            holder.recyclerView.setAdapter(new MineAlbumRecyclerAdapter(context, ImagePath));
        }
        return convertView;
    }

    class ViewHolder {
        TextView date, title ,month ,totalCount ,del;
        RecyclerView recyclerView, recyclerViewSmall;
    }
}
