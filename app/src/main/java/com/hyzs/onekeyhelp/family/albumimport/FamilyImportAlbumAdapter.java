package com.hyzs.onekeyhelp.family.albumimport;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.adapter.MineAlbumRecyclerAdapter;
import com.hyzs.onekeyhelp.mine.adapter.MineAlbumSmallRecyclerAdapter;
import com.hyzs.onekeyhelp.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FamilyImportAlbumAdapter extends BaseAdapter {
    private static List<ImportBean.PersonalAlbumDynamicListBean> list;
    private Context context;
    static int array[];


    public FamilyImportAlbumAdapter(List<ImportBean.PersonalAlbumDynamicListBean> list, Context context) {
        this.list = list;
        this.context = context;
        array = new int[list.size()];
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
        final ViewHolder holder;
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
            holder.cb = (CheckBox) convertView.findViewById(R.id.item_mine_album_cb);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        final ImportBean.PersonalAlbumDynamicListBean bean = list.get(position);
        holder.cb.setVisibility(View.VISIBLE);
        holder.del.setVisibility(View.GONE);
        holder.recyclerView.setVisibility(View.GONE);
        holder.totalCount.setVisibility(View.GONE);
        holder.recyclerViewSmall.setVisibility(View.GONE);
        if (array[position] == 0)
            holder.cb.setChecked(false);
        else holder.cb.setChecked(true);
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
        for (ImportBean.PersonalAlbumDynamicListBean.ImagesBean bean1 : bean.getImages()) {
            ImagePath.add(bean1.getAI_URL());
        }
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
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    array[position] = 1;
                } else array[position] = 0;
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cb.setChecked(!holder.cb.isChecked());
                if (holder.cb.isChecked()) {
                    array[position] = 1;
                } else array[position] = 0;
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView date, title, month, totalCount, del;
        RecyclerView recyclerView, recyclerViewSmall;
        CheckBox cb;
    }

    public static String getProject() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                sb.append(list.get(i).getID() + ",");
            }
        }
        String s = sb.toString();
        if (s.length() != 0) {
            return s.substring(0, s.length() - 1);
        } else return s;

    }
}
