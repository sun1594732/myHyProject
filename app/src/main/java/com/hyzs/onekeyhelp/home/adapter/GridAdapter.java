/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyzs.onekeyhelp.home.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.yanzhenjie.album.AlbumActivity;
import com.yanzhenjie.album.task.ImageLocalLoader;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ImageViewHolder> {

    private Activity context;
    private List<String> mImagePathList;
    private GridAdapter adapter;
    private LayoutInflater mLayoutInflater;
    private Dialog mDeletePhotoDialog;
    private int PHOTO_POSITION = 0;

    public GridAdapter(Activity context) {
        this.context = context;
        adapter = this;
        initDialog();
    }

    public void notifyDataSetChanged(List<String> imagePathList) {
        this.mImagePathList = imagePathList;
        super.notifyDataSetChanged();
    }

    public void createLayoutInflater(Context context) {
        if (mLayoutInflater == null)
            mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        createLayoutInflater(parent.getContext());
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        if (position == mImagePathList.size()) {
            holder.mIvIcon.setImageResource(R.mipmap.default_photo);
            holder.mIvIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AlbumActivity.class);
                    intent.putExtra("limitCount",8);
                    intent.putExtra("picCount",mImagePathList.size());
                    context.startActivityForResult(intent,100);
                }
            });
        } else {
            holder.loadImage(mImagePathList.get(holder.getAdapterPosition()));
            holder.mIvIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PHOTO_POSITION = position;
                    mDeletePhotoDialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (null == mImagePathList) {
            return 0;
        }
        if (mImagePathList.size() == 8) {
            return mImagePathList.size();
        } else
            return mImagePathList == null ? 0 : mImagePathList.size() + 1;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        RoundImageView mIvIcon;

        public ImageViewHolder(View itemView) {
            super(itemView);
            itemView.requestLayout();
            mIvIcon = (RoundImageView) itemView.findViewById(R.id.iv_icon);
        }

        public void loadImage(String imagePath) {
            ImageLocalLoader.getInstance().loadImage(mIvIcon, imagePath);
        }
    }

    private void initDialog() {
        View DialogView = LayoutInflater.from(context).inflate(R.layout.layout_dialog_help_publish_photo, null);
        assignDialogView(DialogView);
        mDeletePhotoDialog = new AlertDialog.Builder(context).create();
        mDeletePhotoDialog.show();
        mDeletePhotoDialog.getWindow().setContentView(DialogView);
        mDeletePhotoDialog.dismiss();
    }

    private void assignDialogView(View dialogView) {
        TextView cancel = (TextView) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_cancel);
        TextView sure = (TextView) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_sure);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeletePhotoDialog.cancel();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImagePathList.remove(PHOTO_POSITION);
                adapter.notifyDataSetChanged();
                mDeletePhotoDialog.dismiss();
            }
        });
    }
}
