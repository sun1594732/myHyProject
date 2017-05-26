package com.hyzs.onekeyhelp.mine.adapter;

/**
 * Created by wubin on 2017/3/27.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.ShowImageActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;

import java.util.ArrayList;


public class MineAlbumRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<String> mImagePathList;
    private LayoutInflater mLayoutInflater;

    public MineAlbumRecyclerAdapter(Context context, ArrayList<String> mImagePathList) {
        this.context = context;
        this.mImagePathList = mImagePathList;
    }

    public void createLayoutInflater(Context context) {
        if (mLayoutInflater == null)
            mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MineAlbumRecyclerAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        createLayoutInflater(parent.getContext());
        return new MineAlbumRecyclerAdapter.ImageViewHolder(mLayoutInflater.inflate(R.layout.item_mine_album_recycler_image, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageView img  = (ImageView) holder.itemView.findViewById(R.id.item_mine_album_recycler_image);
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

