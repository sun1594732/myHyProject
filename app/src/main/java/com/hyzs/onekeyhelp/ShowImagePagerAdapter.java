package com.hyzs.onekeyhelp;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.hyzs.onekeyhelp.widget.pinnedimage.GestureImageView;

import java.util.List;

/**
 * Created by wubin on 2017/3/23.
 */

public class ShowImagePagerAdapter extends PagerAdapter{
    private List<GestureImageView> imageViews;

    public ShowImagePagerAdapter(List<GestureImageView> imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews.get(position), 0);
        return imageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews.get(position));
    }
}
