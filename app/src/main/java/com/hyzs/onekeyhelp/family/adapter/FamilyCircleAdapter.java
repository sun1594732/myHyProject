package com.hyzs.onekeyhelp.family.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.ShowImageActivity;
import com.hyzs.onekeyhelp.family.bean.FamilyCircleBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class

FamilyCircleAdapter extends BaseAdapter {

    private Context mContext;
    private List<FamilyCircleBean.FamilyDynamicListBean> list;

    public FamilyCircleAdapter(Context mContext, List<FamilyCircleBean.FamilyDynamicListBean> list) {
        this.mContext = mContext;
        this.list = list;
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
            view = View.inflate(mContext, R.layout.item_family_circle, null);
            holder = new MyHolder();
            holder.ll = (LinearLayout) view.findViewById(R.id.item_family_circle_img_ll);
            holder.icon = (RoundImageView) view.findViewById(R.id.item_family_circle_icon);
            holder.name = (TextView) view.findViewById(R.id.item_family_circle_name);
            holder.zheng = (TextView) view.findViewById(R.id.item_family_circle_zheng);
            holder.identity = (ImageView) view.findViewById(R.id.item_family_circle_identity);
            holder.title = (TextView) view.findViewById(R.id.item_family_circle_title);
            holder.time = (TextView) view.findViewById(R.id.item_family_circle_time);
            holder.imgSum = (TextView) view.findViewById(R.id.item_family_circle_imgSum);
            holder.like = (TextView) view.findViewById(R.id.item_family_circle_like);
            holder.comment = (TextView) view.findViewById(R.id.item_family_circle_comment);
            holder.commentListView = (ListView) view.findViewById(R.id.item_family_circle_comment_list);
            holder.img1 = (RoundImageView) view.findViewById(R.id.item_family_circle_img1);
            holder.img2 = (RoundImageView) view.findViewById(R.id.item_family_circle_img2);
            holder.img3 = (RoundImageView) view.findViewById(R.id.item_family_circle_img3);
            holder.img4 = (RoundImageView) view.findViewById(R.id.item_family_circle_img4);
            holder.type = (TextView) view.findViewById(R.id.item_family_circle_type);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();
        final FamilyCircleBean.FamilyDynamicListBean bean = list.get(position);
        holder.img1.setVisibility(View.GONE);
        holder.img2.setVisibility(View.GONE);
        holder.img3.setVisibility(View.GONE);
        holder.img4.setVisibility(View.GONE);
        if (TextUtils.isEmpty(bean.getFace())) {
            holder.icon.setImageResource(R.mipmap.icon_replace);
        }else NetRequest.loadImg(mContext,bean.getFace(), holder.icon);
        holder.name.setText(bean.getUesrname());
        holder.title.setText(bean.getContent());
        if (bean.getNCCS() == 0) {
            holder.zheng.setVisibility(View.GONE);
        }else holder.zheng.setVisibility(View.VISIBLE);
        if (bean.getRNA() == 0) {
            holder.identity.setVisibility(View.GONE);
        }else holder.identity.setVisibility(View.VISIBLE);
        switch (bean.getR_type()){
            case 0:
                holder.type.setText("圈子");
                holder.like.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.type.setText("论坛");
                holder.like.setVisibility(View.INVISIBLE);
                break;
            case 2:
                holder.type.setText("活动");
                holder.like.setVisibility(View.INVISIBLE);
                break;
        }
        holder.time.setText(bean.getDiffTime());
        List<FamilyCircleBean.FamilyDynamicListBean.ImagesBean> images = bean.getImages();
        switch ((images.size()>=4?4:images.size())) {
            case 4:
                holder.img4.setVisibility(View.VISIBLE);
            case 3:
                holder.img3.setVisibility(View.VISIBLE);
            case 2:
                holder.img2.setVisibility(View.VISIBLE);
            case 1:
                holder.img1.setVisibility(View.VISIBLE);
                break;
            case 0:
                holder.img1.setVisibility(View.GONE);
                holder.img2.setVisibility(View.GONE);
                holder.img3.setVisibility(View.GONE);
                holder.img4.setVisibility(View.GONE);
                break;
        }
        if (images.size() >0) {
            holder.imgSum.setText("共"+images.size()+"张");
            holder.ll.setVisibility(View.VISIBLE);
            List<RoundImageView> imageViews = new ArrayList<>();
            imageViews.add(holder.img1);
            imageViews.add(holder.img2);
            imageViews.add(holder.img3);
            imageViews.add(holder.img4);
            for (int i = 0; i < (images.size()>=4?4:images.size()); i++) {
                NetRequest.loadImg(mContext,images.get(i).getUrl(),imageViews.get(i));
            }
        }else {
            holder.ll.setVisibility(View.GONE);
            holder.imgSum.setText("");
        }
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MineHomeActivity.class);
                intent.putExtra("targetUserId", bean.getUserid()+"");
                mContext.startActivity(intent);
            }
        });
        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inquireImages(position,0);
            }
        });
        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inquireImages(position,1);
            }
        });
        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inquireImages(position,2);
            }
        });
        holder.img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inquireImages(position,3);
            }
        });
        holder.like.setText(bean.getPraise()+"");
        holder.comment.setText(bean.getCommentCount()+"");
        return view;
    }

    private void inquireImages(int position,int index) {
        ArrayList<String> stringList = new ArrayList<>();
        for (FamilyCircleBean.FamilyDynamicListBean.ImagesBean img : list.get(position).getImages()) {
            stringList.add(img.getUrl());
        }
        Intent intent = new Intent(mContext, ShowImageActivity.class);
        intent.putExtra("position",index);
        intent.putStringArrayListExtra("url",stringList);
        mContext.startActivity(intent);
    }

    private class MyHolder {
        private LinearLayout ll;
        private ListView commentListView;
        private ImageView identity;
        private RoundImageView icon, img1, img2, img3, img4;
        private TextView name, title, time, imgSum, like, comment,zheng,type;
    }
}
