package com.hyzs.onekeyhelp.util;

import com.squareup.okhttp.Call;

/**
 * Created by ZHANGZHEN on 2017-3-17.
 */

public interface ResponseHandler {

    void onError(Call call, Exception e, int id);

    void onSuccess(String response, int id);

    void inProgress(float progress, long total, int id);
}
