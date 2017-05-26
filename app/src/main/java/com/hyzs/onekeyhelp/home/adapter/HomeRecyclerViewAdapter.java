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

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.home.bean.HomePageBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ImageViewHolder> {

    private Context context;
    private List<HomePageBean.CommunityHeroBean.HelpPeopleBean> mImagePathList;
    private LayoutInflater mLayoutInflater;

    public HomeRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public HomeRecyclerViewAdapter(Context context, List<HomePageBean.CommunityHeroBean.HelpPeopleBean> mImagePathList) {
        this.context = context;
        this.mImagePathList = mImagePathList;
    }

    public void notifyDataSetChanged(List<HomePageBean.CommunityHeroBean.HelpPeopleBean> imagePathList) {
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
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        if (!TextUtils.isEmpty(mImagePathList.get(position).getFace())) {
            NetRequest.loadImg(context, mImagePathList.get(position).getFace(), holder.mIvIcon);
        }else holder.mIvIcon.setImageResource(R.mipmap.icon_replace);
        holder.mIvIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySharedPreferences.isLogin(context)) {
                    return;
                }
                Intent intent = new Intent(context, MineHomeActivity.class);
                intent.putExtra("targetUserId", mImagePathList.get(position).getSeek_UserID() + "");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mImagePathList.size() > 6 ? 6 : mImagePathList.size();
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
