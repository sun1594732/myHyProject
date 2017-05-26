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
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.carresuce.view.CarResuceActivity;
import com.hyzs.onekeyhelp.family.circle.activity.CircleActivity;
import com.hyzs.onekeyhelp.family.movable.activity.MovableListActivity;
import com.hyzs.onekeyhelp.home.activity.CommunityNoticeBaseActivity;
import com.hyzs.onekeyhelp.lifehelp.view.activity.LifeHelpHomeActivity;
import com.hyzs.onekeyhelp.home.forum.activity.ForumActivity;
import com.hyzs.onekeyhelp.news.BaseNewsActivity;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;

public class HomeRightSlideAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mLayoutInflater;
    String[] type;
    int[] drawableResource;
    PopupWindow pop;

    public HomeRightSlideAdapter(Context context,int []drawableResource) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        Resources res = context.getResources();
        type = res.getStringArray(R.array.right_type);
        this.drawableResource = drawableResource;
    }

    @Override
    public int getCount() {
        return type.length;
    }

    @Override
    public Object getItem(int position) {
        return type[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_right_slide, parent, false);
            holder.mIvIcon = (TextView) convertView.findViewById(R.id.item_right_slide_tv);
            holder.line = convertView.findViewById(R.id.item_right_slide_line);
            holder.mImage = (ImageView) convertView.findViewById(R.id.item_right_slide_img);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.mIvIcon.setText(type[position]);
        holder.mImage.setImageResource(drawableResource[position]);
        int backType ;
        if (position<4) {
            backType = 0;
        }else if (position>3&&position<8){
            backType = 1;
        }else if (position>7&&position<12){
            backType = 2;
        }else if (position>11&&position<16){
            backType = 3;
        }else backType = 4;
        switch (backType) {
            case 0:
                convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.color_e5f8fc));
                holder.mIvIcon.setTextColor(ContextCompat.getColor(context,R.color.color_bb8));
                break;
            case 1:
                convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.color_fbf1f2));
                holder.mIvIcon.setTextColor(ContextCompat.getColor(context,R.color.color_c94048));
                break;
            case 2:
                convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.color_effcf2));
                holder.mIvIcon.setTextColor(ContextCompat.getColor(context,R.color.color_05a028));
                break;
            case 3:
                convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.color_fcfcf0));
                holder.mIvIcon.setTextColor(ContextCompat.getColor(context,R.color.color_b88d0d));
                break;

            case 4:
                convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.color_fdeffe));
                holder.mIvIcon.setTextColor(ContextCompat.getColor(context,R.color.color_9830a3));
                break;

        }
        final View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySharedPreferences.isLogin(context)) {
                    pop = HelpDialog.createDialogNoAlert(context, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pop.dismiss();
                        }
                    }, "您还没有登录", 0);
                    pop.showAtLocation(finalConvertView, Gravity.CENTER,0,0);
                    return;
                }
                switch (position) {
                    case 0:
                        intentStart(LifeHelpHomeActivity.class);
                        break;
                    case 1:
                        intentStart(CarResuceActivity.class);
                        break;
                    case 2:
                        intentStart(CarResuceActivity.class);
                        break;
                    case 3:
                        intentStartActive(MovableListActivity.class, 3, type[position]);
                        break;
                    case 4:
                        intentStartActive(MovableListActivity.class, 4, type[position]);
                        break;
                    case 5:
                        intentStartActive(MovableListActivity.class, 5, type[position]);
                        break;
                    case 6:
                        intentStartCircle(CircleActivity.class, 1);
                        break;
                    case 7:
                        intentStartCircle(CircleActivity.class, 2);
                        break;
                    case 8:
                        intentStartCircle(CircleActivity.class, 3);
                        break;
                    case 9:
                        intentStartCircle(CircleActivity.class, 4);
                        break;
                    case 10:
                        intentStartCircle(CircleActivity.class, 5);
                        break;
                    case 11:
                        intentStartCircle(CircleActivity.class, 6);
                        break;
                    case 12:
                        intentStartCircle(CircleActivity.class, 7);
                        break;
                    case 13:
                        intentStart(CommunityNoticeBaseActivity.class);
                        break;
                    case 14:
                        intentStartCircle(BaseNewsActivity.class, 1);
                        break;
                    case 15:
                        intentStartCircle(BaseNewsActivity.class, 2);
                        break;
                    case 16:
                        intentStartCircle(BaseNewsActivity.class, 3);
                        break;
                    case 17:
                        intentStartCircle(ForumActivity.class, 1);
                        break;
                    case 18:
                        intentStartCircle(ForumActivity.class, 2);
                        break;
                    case 19:
                        intentStartCircle(ForumActivity.class, 3);
                        break;
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView mIvIcon;
        View line;
        ImageView mImage;
    }


    private void intentStart(Class TargetClass) {
        context.startActivity(new Intent(context, TargetClass));
    }

    private void intentStartCircle(Class TargetClass, int type) {
        Intent intent = new Intent(context, TargetClass);
        intent.putExtra("type", type - 1);
        context.startActivity(intent);
    }

    private void intentStartActive(Class TargetClass, int type, String title) {
        Intent intent = new Intent(context, TargetClass);
        intent.putExtra("type", type);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }
}
