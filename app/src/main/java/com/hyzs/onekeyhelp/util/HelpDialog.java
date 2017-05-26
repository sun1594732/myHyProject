package com.hyzs.onekeyhelp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;

/**
 * Created by Administrator on 2017/4/12.
 */

public class HelpDialog {
    public static PopupWindow createDialog(Context context , View.OnClickListener click,String info,int icon){
        LayoutInflater inflater = LayoutInflater.from(context);
        View popupView = inflater.inflate(R.layout.help_pop_layout, null);
        TextView tvInfo = (TextView) popupView.findViewById(R.id.help_pop_info);
        TextView tv1 = (TextView) popupView.findViewById(R.id.help_pop_text1);
        ImageView img = (ImageView) popupView.findViewById(R.id.help_pop_icon);
        if (!TextUtils.isEmpty(info)) {
            tvInfo.setText(info);
        }
        tv1.setVisibility(View.GONE);
        if (0 != icon) {
            img.setImageResource(icon);
        }
        PopupWindow mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        popupView.findViewById(R.id.help_pop_confirm).setOnClickListener(click);
        return mPopupWindow;
    }
    public static PopupWindow createDialogNoAlert(Context context , View.OnClickListener click,String info,int icon){
        LayoutInflater inflater = LayoutInflater.from(context);
        View popupView = inflater.inflate(R.layout.help_pop_layout, null);
        TextView tvInfo = (TextView) popupView.findViewById(R.id.help_pop_info);
        TextView tv1 = (TextView) popupView.findViewById(R.id.help_pop_text1);
        tv1.setVisibility(View.GONE);
        ImageView img = (ImageView) popupView.findViewById(R.id.help_pop_icon);
        if (!TextUtils.isEmpty(info)) {
            tvInfo.setText(info);
        }
        tv1.setVisibility(View.GONE);
        if (0 != icon) {
            img.setImageResource(icon);
        }
        PopupWindow mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        popupView.findViewById(R.id.help_pop_confirm).setOnClickListener(click);
        return mPopupWindow;
    }
}
