package com.hyzs.onekeyhelp.contact.bean;

/**
 * Created by ZHANGZHEN on 2017-3-17.
 */

public class ResultBean {
    private String data;
    private int code;
    private String error;
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    @Override
    public String toString() {
        return "ResultBean [data=" + data + ", code=" + code + ", error=" + error
                + "]";
    }
}

