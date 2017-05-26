package com.hyzs.onekeyhelp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hyphenate.util.DensityUtil;
import com.hyzs.onekeyhelp.R;

/**
 * Created by wubin on 2017/3/25.
 */

public class RoundImageView extends ImageView {
    private int cornerSize;//圆角大小
    private Context context;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyle, 0);
        cornerSize = a.getInt(R.styleable.RoundImageView_corner_size, 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = getWidth();
        int h = getHeight();
        //这里对path添加一个圆角区域，这里一般需要将dp转换为pixel
        path.addRoundRect(new RectF(0, 0, w, h), DensityUtil.dip2px(context,cornerSize), DensityUtil.dip2px(context,cornerSize), Path.Direction.CW);
        canvas.clipPath(path);//将Canvas按照上面的圆角区域截取
        super.onDraw(canvas);
    }

    /**
     * 设置圆角的大小
     *
     * @param size
     */
    public void setCornerSize(int size) {
        cornerSize = size;
    }
}
