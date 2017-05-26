package com.hyzs.onekeyhelp.module.housekeeping.bean;

/**
 * Created by wubin on 2017/5/10.
 */

public class ApplyStatusBean {

    /**
     * lifeServiceApplyStatusQuery : {"status":1,"LS_ID":1,"statusDesc":"已认证通过"}
     * message : 正常
     * code : 10000
     */

    private LifeServiceApplyStatusQueryBean lifeServiceApplyStatusQuery;
    private String message;
    private String code;

    public LifeServiceApplyStatusQueryBean getLifeServiceApplyStatusQuery() {
        return lifeServiceApplyStatusQuery;
    }

    public void setLifeServiceApplyStatusQuery(LifeServiceApplyStatusQueryBean lifeServiceApplyStatusQuery) {
        this.lifeServiceApplyStatusQuery = lifeServiceApplyStatusQuery;
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

    public static class LifeServiceApplyStatusQueryBean {
        /**
         * status : 1
         * LS_ID : 1
         * statusDesc : 已认证通过
         */

        private int status;
        private int LS_ID;
        private String statusDesc;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getLS_ID() {
            return LS_ID;
        }

        public void setLS_ID(int LS_ID) {
            this.LS_ID = LS_ID;
        }

        public String getStatusDesc() {
            return statusDesc;
        }

        public void setStatusDesc(String statusDesc) {
            this.statusDesc = statusDesc;
        }
    }
}
