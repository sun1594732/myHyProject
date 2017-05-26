package com.hyzs.onekeyhelp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hyzs123 on 2017/3/20.
 */

public class DateFormatUtils {

    public static String Time2Date(String Strnow){
        DateFormat formatter = new SimpleDateFormat("MM-dd hh:mm");
        Long now = Long.valueOf(Strnow);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        String date= formatter.format(calendar.getTime());
        return date;
    }

    public static String Time2DateForAll(String Strnow){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long now = Long.valueOf(Strnow);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        String date= formatter.format(calendar.getTime());
        return date;
    }
}
