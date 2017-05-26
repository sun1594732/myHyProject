package com.hyzs.onekeyhelp.contact.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/9.
 */

public class IdentityTypeBean {

    /**
     * total : 3
     * identityTypeList : [{"id":9,"identityName":"居委会"},{"id":4,"identityName":"志愿者"},{"id":1,"identityName":"服务商"}]
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<IdentityTypeListBean> identityTypeList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public List<IdentityTypeListBean> getIdentityTypeList() {
        return identityTypeList;
    }

    public void setIdentityTypeList(List<IdentityTypeListBean> identityTypeList) {
        this.identityTypeList = identityTypeList;
    }

    public static class IdentityTypeListBean {
        /**
         * id : 9
         * identityName : 居委会
         */

        private int id;
        private String identityName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdentityName() {
            return identityName;
        }

        public void setIdentityName(String identityName) {
            this.identityName = identityName;
        }
    }
}
