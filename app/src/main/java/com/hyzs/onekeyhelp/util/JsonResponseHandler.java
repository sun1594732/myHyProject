package com.hyzs.onekeyhelp.util;

import okhttp3.Call;

/**
 * Created by ZHANGZHEN on 2017-3-17.
 */

public abstract class JsonResponseHandler implements ResponseHandler {

    public abstract void onError(Call call, Exception e, int id);

    @Override
    public abstract void onSuccess(String response, int id);

    @Override
    public void inProgress(float progress, long total, int id) {

    }

}
