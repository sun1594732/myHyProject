package com.hyzs.onekeyhelp.lifehelp.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class SyncHorizontalScrollView extends HorizontalScrollView {

	public SyncHorizontalScrollView(Context context) {
		super(context);
	}

	public SyncHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SyncHorizontalScrollView(Context context, AttributeSet attrs,
									int defStyle) {
		super(context, attrs, defStyle);
	}

	private View view;

	private int windowWitdh = 0;
	private Activity mContext;

	public void setSomeParam(View view, Activity context, int widthPixels) {
		this.view = view;
		this.mContext = context;

		this.windowWitdh = widthPixels;
	}

	public void showAndHideArrow() {
		if (!mContext.isFinishing() && view != null) {
			this.measure(0, 0);
			if (windowWitdh >= this.getMeasuredWidth()) {

			} else {
				if (this.getLeft() == 0) {
					;
				} else if (this.getRight() == this.getMeasuredWidth()
						- windowWitdh) {

				} else {

				}
			}
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (!mContext.isFinishing() && view != null ) {
			if (view.getWidth() <= windowWitdh) {

			} else {
				if (l == 0) {
				;
				} else if (view.getWidth() - l == windowWitdh) {

				} else {

				}
			}
		}
	}

}
