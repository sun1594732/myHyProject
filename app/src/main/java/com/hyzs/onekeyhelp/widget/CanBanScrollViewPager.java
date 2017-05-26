package com.hyzs.onekeyhelp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hyzs.onekeyhelp.R;

public class CanBanScrollViewPager extends ViewPager {
    private boolean isCanScroll;/*是否可以滑动*/
    public CanBanScrollViewPager(Context context) {
        super(context);
    }
    public CanBanScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CanBanScrollViewPager);
        /*获取布局中设置的属性*/
        isCanScroll = a.getBoolean(R.styleable.CanBanScrollViewPager_isScroll, false);
        a.recycle();
    }
    public void setCanScroll(boolean isCanScroll) {
    /*对外公开的方法，设置是否可以滑动*/
        this.isCanScroll = isCanScroll;
    }
    public boolean isCanScroll() {
    /*对外公开的方法，获取当前是否可以滑动*/
        return isCanScroll;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
    /*重写此方法，判断当前是否可以滑动，如果可以，正常调用父类的方法，该干嘛干嘛！如果不可以滑动，直接返回false，不做任何触摸事件的处理*/
        if (isCanScroll()) {
            return super.onTouchEvent(ev);
        }
        return false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    /*这里意思和上面的重写方法差不多，不多解释*/
        if (isCanScroll()) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }
}
