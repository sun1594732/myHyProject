package com.hyzs.onekeyhelp.home.bean;

import java.util.List;

/**
 * Created by wubin on 2017/4/6.
 */

public class CommunityNoticeTypeBean {

    /**
     * communityNotice2Classify : [{"id":3,"CNC_Name":"社区公告2","CNC_SuperiorID":1}]
     * total : 2
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<CommunityNotice2ClassifyBean> communityNotice2Classify;

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

    public List<CommunityNotice2ClassifyBean> getCommunityNotice2Classify() {
        return communityNotice2Classify;
    }

    public void setCommunityNotice2Classify(List<CommunityNotice2ClassifyBean> communityNotice2Classify) {
        this.communityNotice2Classify = communityNotice2Classify;
    }

    public static class CommunityNotice2ClassifyBean {
        /**
         * id : 3
         * CNC_Name : 社区公告2
         * CNC_SuperiorID : 1
         */

        private int id;
        private String CNC_Name;
        private int CNC_SuperiorID;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCNC_Name() {
            return CNC_Name;
        }

        public void setCNC_Name(String CNC_Name) {
            this.CNC_Name = CNC_Name;
        }

        public int getCNC_SuperiorID() {
            return CNC_SuperiorID;
        }

        public void setCNC_SuperiorID(int CNC_SuperiorID) {
            this.CNC_SuperiorID = CNC_SuperiorID;
        }
    }
}
