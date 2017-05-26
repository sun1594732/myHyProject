package com.hyzs.onekeyhelp.widget.view.uilts.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hyzs.onekeyhelp.R;


public class MyImageView extends RelativeLayout {

	public ImageView getmImageView() {
		return mImageView;
	}

	public void setmImageView(ImageView mImageView) {
		this.mImageView = mImageView;
	}

	private ImageView mImageView;
	
	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyImageView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.my_image_view, this);
		mImageView = (ImageView) findViewById(R.id.myImage);
	}
	
	public void setImage(int id){
		mImageView.setImageResource(id);
	}

}
