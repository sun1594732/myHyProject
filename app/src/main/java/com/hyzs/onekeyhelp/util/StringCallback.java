package com.hyzs.onekeyhelp.util;

import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by ZHANGZHEN on 2017-3-17.
 */

public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        return response.body().string();
    }
}
