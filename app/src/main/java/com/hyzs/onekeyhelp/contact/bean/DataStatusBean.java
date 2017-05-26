package com.hyzs.onekeyhelp.contact.bean;


/**
 * Created by wubin on 2017/3/18.
 */
public class DataStatusBean {

    /**
     * message : 手机号已经存在，请尝试搜索添加
     * code : 10001
     */

    private String message;
    private String code;
    private int Identity;

    public int getIdentity() {
        return Identity;
    }

    public void setIdentity(int identity) {
        Identity = identity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
