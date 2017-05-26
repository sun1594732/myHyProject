package com.hyzs.onekeyhelp.contact.bean;


public class LoginVO {
    private LoginDataVO data;
    private int code;
    private String error;
    public LoginDataVO getData() {
        return data;
    }
    public void setData(LoginDataVO data) {
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
        return "LoginVO [data=" + data + ", code=" + code + ", error=" + error + "]";
    }

}
