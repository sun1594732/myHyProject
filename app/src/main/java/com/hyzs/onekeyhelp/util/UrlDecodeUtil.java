package com.hyzs.onekeyhelp.util;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class UrlDecodeUtil {
    private final static String ENCODE = "UTF-8";
    public static String urlCode(String data) {
        String urlStr = null;
        try {
            urlStr = URLDecoder.decode(data, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return urlStr;
    }

    public static String urlEnCode(String data) {
        String urlStr = null;
        try {
            urlStr = URLEncoder.encode(data,ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return urlStr;
    }
}
