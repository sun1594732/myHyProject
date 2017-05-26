package com.hyzs.onekeyhelp.family.circle.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.ShowImageActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;

import java.util.ArrayList;

public class CircleItemRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<String> mImagePathList;
    private LayoutInflater mLayoutInflater;

    public CircleItemRecyclerAdapter(Context context, ArrayList<String> mImagePathList) {
        this.context = context;
        this.mImagePathList = mImagePathList;
    }

    public void createLayoutInflater(Context context) {
        if (mLayoutInflater == null)
            mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        createLayoutInflater(parent.getContext());
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.item_circle_recycler_image, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageView img  = (ImageView) holder.itemView.findViewById(R.id.item_circle_recycler_image1);
        if (TextUtils.isEmpty( mImagePathList.get(position))) {
            img.setImageResource(R.mipmap.replace_hybb);
        }else
        NetRequest.loadImg(context,mImagePathList.get(position), img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowImageActivity.class);
                intent.putExtra("position",position);
                intent.putStringArrayListExtra("url",mImagePathList);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImagePathList == null ? 0 : mImagePathList.size();
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvIcon;

        public ImageViewHolder(View itemView) {
            super(itemView);
            itemView.requestLayout();
            mIvIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }

    }

}
