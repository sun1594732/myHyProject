package com.hyzs.onekeyhelp.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZHANGZHEN on 2017-3-17.
 */

public class JsonUtils {

    /**
     * @param result
     * @return 服务器返回是否成功
     */
    public static boolean isSuccess(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int status = jsonObject.optInt("code", 1);
            if (status == 0) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param result
     * @return 获取成功结果之后的data节点数据
     */
    public static String getSuccessData(String result, String feild) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.optString(feild, "data");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param result
     * @return 获取调用失败的信息
     */
    public static String getErrorMessage(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.optString("error", "");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
