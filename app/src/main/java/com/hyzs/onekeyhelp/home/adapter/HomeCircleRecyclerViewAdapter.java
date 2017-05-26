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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.home.bean.HomePageBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumActivity;
import com.yanzhenjie.album.task.ImageLocalLoader;

import java.util.List;

public class HomeCircleRecyclerViewAdapter extends RecyclerView.Adapter<HomeCircleRecyclerViewAdapter.ImageViewHolder> {

    private Context context;
    private List<String> mImagePathList;
    private LayoutInflater mLayoutInflater;

    public HomeCircleRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public HomeCircleRecyclerViewAdapter(Context context, List<String> mImagePathList) {
        this.context = context;
        this.mImagePathList = mImagePathList;
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
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.item_home_recycler_imag, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        NetRequest.loadImg(context, mImagePathList.get(position), holder.mIvIcon);
    }

    @Override
    public int getItemCount() {

        return mImagePathList.size() > 4 ? 4 : mImagePathList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvIcon;

        public ImageViewHolder(View itemView) {
            super(itemView);
            itemView.requestLayout();
            mIvIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }

    }
}
