package com.hyzs.onekeyhelp.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by hyzs123 on 2017/4/6.
 */

public class InputMethodManagerUtils {


    public static void setInputMethod(Context context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()){
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }
}
