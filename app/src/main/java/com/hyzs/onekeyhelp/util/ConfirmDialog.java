package com.hyzs.onekeyhelp.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;


public class ConfirmDialog {
    public static Dialog createProgressLoading(Context context, String sTitle,String sLeft,String sRight, View.OnClickListener click) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_confirm_layout, null);// 得到加载view
        TextView title = (TextView) view.findViewById(R.id.dialog_confirm_title);
        TextView left = (TextView) view.findViewById(R.id.dialog_confirm_left);
        TextView right = (TextView) view.findViewById(R.id.dialog_confirm_right);
        title.setText(sTitle);
        left.setText(sLeft);
        right.setText(sRight);
        left.setOnClickListener(click);
        right.setOnClickListener(click);
        Dialog loadingDialog = new Dialog(context, R.style.MyDialog);// 创建自定义样式dialog
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(true);// 可以用“返回键”取消
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setContentView(view, layoutParams);// 设置布局
        return loadingDialog;
    }

    public static Dialog createConfirmLoading(Context context, String sTitle,String sLeft,String sRight, View.OnClickListener click,View.OnClickListener click1) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_confirm_layout, null);// 得到加载view
        TextView title = (TextView) view.findViewById(R.id.dialog_confirm_title);
        TextView left = (TextView) view.findViewById(R.id.dialog_confirm_left);
        TextView right = (TextView) view.findViewById(R.id.dialog_confirm_right);
        title.setText(sTitle);
        left.setText(sLeft);
        right.setText(sRight);
        left.setOnClickListener(click);
        right.setOnClickListener(click1);
        Dialog loadingDialog = new Dialog(context, R.style.MyDialog);// 创建自定义样式dialog
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(true);// 可以用“返回键”取消
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setContentView(view, layoutParams);// 设置布局
        return loadingDialog;
    }
}
