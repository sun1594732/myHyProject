package com.hyzs.onekeyhelp.widget.view.uilts.myviewpager;


import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.hyzs.onekeyhelp.ShowImageActivity;
import com.hyzs.onekeyhelp.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class HeadViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<MyImageView> mList;
    private List<String> imgList;

    public HeadViewPagerAdapter(Context context, List<MyImageView> list, List<String> imgList) {
        this.mContext = context;
        this.mList = list;
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    //���View
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        container.addView(mList.get(position));
        mList.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowImageActivity.class);
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("url", (ArrayList<String>) imgList);
                mContext.startActivity(intent);
            }
        });
        return mList.get(position);
    }

}
