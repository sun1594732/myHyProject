package com.hyzs.onekeyhelp.widget;



import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.hyzs.onekeyhelp.R;


/**
 * 作者：zzw on 2016-10-24 14:22
 * QQ：1436942789
 * 邮箱：developer_zzw@163.com
 * 作用：
 */

public class RecModeWindow extends PopupWindow {
	private ImageView iv_mode_back;
	private RelativeLayout rl_mode_zhifubao;
	private RelativeLayout rl_mode_weixin;
	private View mView;

	public RecModeWindow(Activity context, View.OnClickListener itemsOnClick) {
		super(context);
		initView(context, itemsOnClick);
	}

	private void initView(final Activity context, View.OnClickListener itemsOnClick) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.pop_rec_mode, null);
		rl_mode_weixin = (RelativeLayout) mView.findViewById(R.id.rl_mode_weixin);
		rl_mode_zhifubao = (RelativeLayout) mView.findViewById(R.id.rl_mode_zhifubao);
		iv_mode_back = (ImageView) mView.findViewById(R.id.iv_mode_back);
		//取消按钮
		iv_mode_back.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//销毁弹出框
				dismiss();
			}
		});
		//设置按钮监听
		rl_mode_zhifubao.setOnClickListener(itemsOnClick);
		rl_mode_weixin.setOnClickListener(itemsOnClick);

		//设置SelectPicPopupWindow的View
		this.setContentView(mView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置PopupWindow可触摸
		this.setTouchable(true);
		//设置非PopupWindow区域是否可触摸
		//        this.setOutsideTouchable(false);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.select_anim);
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		backgroundAlpha(context, 0.5f);//0.0-1.0
		this.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				backgroundAlpha(context, 1f);
			}
		});
	}

	/**
	 * 设置添加屏幕的背景透明度
	 *
	 * @param bgAlpha
	 */
	public void backgroundAlpha(Activity context, float bgAlpha) {
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = bgAlpha;
		context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		context.getWindow().setAttributes(lp);
	}
}
