package com.hyzs.onekeyhelp.util;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/12.
 */

public class OkHttpUtil {
    private static OkHttpUtil okHttpUtil;
    private OkHttpClient okHttpClient=new OkHttpClient();
    private HttpCallback httpCallback;
    private OkHttpUtil() {

    }

    public synchronized static OkHttpUtil newInstamce() {
        if (okHttpUtil == null) {
            okHttpUtil = new OkHttpUtil();
        }
        return okHttpUtil;
    }

    /**
     * get请求
     */
    public  void getAsynHttp(String url, HttpCallback httpCallback1) {
        this.httpCallback=httpCallback1;
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                httpCallback.onFailure(request,e);

            }

            @Override
            public void onResponse(Response response) throws IOException {
                httpCallback.onResponse(response);
            }


        });
    }


    public void requestFile(){

    }





    public interface HttpCallback{
        public void onFailure(Request request, IOException e);
        public void onResponse(Response response);
    }
}
