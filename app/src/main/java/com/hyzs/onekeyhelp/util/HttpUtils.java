package com.hyzs.onekeyhelp.util;

import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ZHANGZHEN on 2017-3-17.
 */

public class HttpUtils  {

    public static void initClient(okhttp3.OkHttpClient okHttpClient) {
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 异步get请求
     *
     * @param context         发起请求的context
     * @param url             要请求的url
     * @param params          请求参数
     * @param responseHandler 请求回调
     */
    public static void get(Context context, String url, Map<String, String> params, final com.hyzs.onekeyhelp.util.ResponseHandler responseHandler) {

        OkHttpUtils.get().url(url).params(params).tag(context).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                responseHandler.onError((com.squareup.okhttp.Call) call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                responseHandler.onSuccess(response, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                responseHandler.inProgress(progress, total, id);
            }

        });
    }

    /**
     * 异步get请求
     *
     * @param url             要请求的url
     * @param params          请求参数
     * @param responseHandler 请求回调
     */
    public static void get(String url, Map<String, String> params, final com.hyzs.onekeyhelp.util.ResponseHandler responseHandler) {
        HttpUtils.get(null, url, params, responseHandler);
    }

    /**
     * 异步post请求
     *
     * @param context         发起请求的context
     * @param url             要请求的url
     * @param params          请求参数
     * @param responseHandler 请求回调
     */
    public static void post(Context context, String url, Map<String, String> params, final com.hyzs.onekeyhelp.util.ResponseHandler responseHandler) {

        OkHttpUtils.post().url(url).params(params).tag(context).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                responseHandler.onError((com.squareup.okhttp.Call) call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                responseHandler.onSuccess(response, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                responseHandler.inProgress(progress, total, id);
            }
        });
    }

    /**
     * 异步post请求
     *
     * @param url             要请求的url
     * @param params          请求参数
     * @param responseHandler 请求回调
     */
    public static void post(String url, Map<String, String> params, final com.hyzs.onekeyhelp.util.ResponseHandler responseHandler) {
        HttpUtils.post(null, url, params, responseHandler);
    }

    /**
     * 同步post请求
     *
     * @param context 发起请求的context
     * @param url     要请求的url
     * @param params  请求参数
     * @return 返回请求Response对象，需要手动解析
     */
    public static Response synPost(Context context, String url, Map<String, String> params) {
        Response response = null;
        try {
            response = OkHttpUtils.post().url(url).params(params).tag(context).build().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * 同步get请求
     *
     * @param context 发起请求的context
     * @param url     要请求的url
     * @param params  请求参数
     * @return 返回请求Response对象，需要手动解析
     */
    public static Response synGet(Context context, String url, Map<String, String> params) {
        Response response = null;
        try {
            response = OkHttpUtils.get().url(url).params(params).tag(context).build().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 上传多个文件
     *
     * @param context         发起请求的context
     * @param url             url
     * @param params          参数
     * @param files           上传的文件files
     * @param responseHandler 回调
     */
    public static void upload(Context context, String url, Map<String, String> params, Map<String, File> files, final com.hyzs.onekeyhelp.util.ResponseHandler responseHandler) {
        OkHttpUtils.post()//
                .url(url)
                .params(params)
                .files("上传多个文件", files)
                .tag(context)
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        responseHandler.onError((com.squareup.okhttp.Call) call, e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        responseHandler.onSuccess(response, id);
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        responseHandler.inProgress(progress, total, id);
                    }
                });
    }

    /**
     * 上传多个文件
     *
     * @param context         发起请求的context
     * @param url             请求url
     * @param files           上传的文件files
     * @param responseHandler 回调
     */
    public static void upload(Context context, String url, Map<String, File> files, com.hyzs.onekeyhelp.util.ResponseHandler responseHandler) {
        HttpUtils.upload(context, url, null, files, responseHandler);
    }

    /**
     * 上传多个文件
     *
     * @param url             请求url
     * @param params          请求参数
     * @param files           上传的文件files
     * @param responseHandler 回调
     */
    public static void upload(String url, Map<String, String> params, Map<String, File> files, com.hyzs.onekeyhelp.util.ResponseHandler responseHandler) {
        HttpUtils.upload(null, url, params, files, responseHandler);
    }

    /**
     * 上传多个文件
     *
     * @param url             请求url
     * @param files           上传的文件files
     * @param responseHandler 回调
     */
    public static void upload(String url, Map<String, File> files, com.hyzs.onekeyhelp.util.ResponseHandler responseHandler) {
        HttpUtils.upload(null, url, null, files, responseHandler);
    }

    /**
     * 上传单个文件
     *
     * @param context         发起请求的context
     * @param url             请求url
     * @param params          请求参数
     * @param filename        要上传的文件的文件名
     * @param file            要上传的文件file
     * @param responseHandler 回调
     */
    public static void upload(Context context, String url, Map<String, String> params, String filename, File file, final com.hyzs.onekeyhelp.util.ResponseHandler responseHandler) {
        Map<String, File> files = new HashMap<>();
        files.put(filename, file);
        HttpUtils.upload(context, url, params, files, responseHandler);
    }

    /**
     * 上传单个文件
     *
     * @param url             请求url
     * @param params          请求参数
     * @param filename        要上传的文件的文件名
     * @param file            要上传的文件file
     * @param responseHandler 回调
     */
    public static void upload(String url, Map<String, String> params, String filename, File file, final com.hyzs.onekeyhelp.util.ResponseHandler responseHandler) {
        HttpUtils.upload(null, url, params, filename, file, responseHandler);
    }

    /**
     * 上传单个文件
     *
     * @param url             请求url
     * @param filename        要上传的文件的文件名
     * @param file            要上传的文件file
     * @param responseHandler 回调
     */
    public static void upload(String url, String filename, File file, final com.hyzs.onekeyhelp.util.ResponseHandler responseHandler) {
        HttpUtils.upload(null, url, null, filename, file, responseHandler);
    }

    /**
     * 上传单个文件
     *
     * @param context         发起请求的context
     * @param url             url
     * @param filename        要上传的文件的文件名
     * @param file            要上传的文件file
     * @param responseHandler 回调
     */
    public static void upload(Context context, String url, String filename, File file, final com.hyzs.onekeyhelp.util.ResponseHandler responseHandler) {
        HttpUtils.upload(context, url, null, filename, file, responseHandler);
    }

    /**
     * 根据url取消单个请求
     *
     * @param url 要取消请求的url
     */
    public static void cancelByUrl(String url) {
        OkHttpUtils.get().url(url).build().cancel();
    }

    /**
     * 取消所有以context为Tag的请求
     *
     * @param context 作为Tag的context对象
     */
    public static void cancel(Context context) {
        OkHttpUtils.getInstance().cancelTag(context);
        LogUtils.e("取消了这次请求=="+context);
    }

    /**
     * 请求数据成功时候判断返回值
     *
     * @param context 作为Tag的context对象
     */
    public static boolean getSuccessData(String context) {
        boolean falg = false;
        try {
            JSONObject obj = new JSONObject(context);
            String data = obj.getString("data");
            if("1".equals(data)) {
                falg = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return falg;
    }

}
