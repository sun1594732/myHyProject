package com.hyzs.onekeyhelp.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/4/18.
 */

public class MyTextWatcher implements TextWatcher {
    String currentText = "";
    private EditText editText;

    public MyTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int lines = editText.getLineCount();
        // 限制最大输入行数
        if (lines > editText.getMaxLines()) {
            editText.setText(currentText);
            editText.setSelection(currentText.length());
        } else if (lines <= editText.getMaxLines()) {
            currentText = s != null ? s.toString() : "";
        }
    }
}
