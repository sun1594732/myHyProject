package com.hyzs.onekeyhelp.util;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by ZHANGZHEN on 2017-3-18.
 */

public class LoginHttpUtils {
    private static OkHttpClient client = null;
    private LoginHttpUtils() {
    }

    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (LoginHttpUtils.class) {
                if (client == null)
                    client = new OkHttpClient();
            }
        }
        return client;
    }

    /**
     * Get请求
     *
     * @param url
     * @param callback
     */
    public static void doGet(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * Post请求发送键值对数据
     *
     * @param url
     * @param mapParams
     * @param callback
     */
    public static void doPost(String url, Map<String, String> mapParams, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }
}

