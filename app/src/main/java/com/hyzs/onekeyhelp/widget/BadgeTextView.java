package com.hyzs.onekeyhelp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;

public class BadgeTextView extends FrameLayout {
    private ImageView mPoint;
    private TextView mTv;

    public BadgeTextView(Context context) {
        super(context);
    }

    public BadgeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.my_badge_tv, this);
        mPoint = (ImageView) findViewById(R.id.point);
        mTv = (TextView) findViewById(R.id.text);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BadgeTextView);
        int textColor = a.getColor(R.styleable.BadgeTextView_text_color, 0xFFFFFFFF);
        float textSize = a.getDimension(R.styleable.BadgeTextView_text_size, 35);
        String text = a.getString(R.styleable.BadgeTextView_text_content);
        Drawable drawable = a.getDrawable(R.styleable.BadgeTextView_text_drawable);
        mTv.setText(text);
        mTv.setTextColor(textColor);
        mTv.setTextSize(textSize);
        mTv.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    public BadgeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showPoint(boolean isShow) {
        if (isShow) {
            mPoint.setVisibility(VISIBLE);
        } else
            mPoint.setVisibility(INVISIBLE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        //设置宽高
        setMeasuredDimension(width, height);
    }

    //根据xml的设定获取宽度
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST){

        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY){

        }
        Log.i("这个控件的宽度----------","specMode="+specMode+" specSize="+specSize);

        return specSize;
    }

    //根据xml的设定获取高度
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST){

        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY){

        }
        Log.i("这个控件的高度----------","specMode:"+specMode+" specSize:"+specSize);

        return specSize;
    }
}
