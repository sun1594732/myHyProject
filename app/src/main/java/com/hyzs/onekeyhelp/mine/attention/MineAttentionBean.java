package com.hyzs.onekeyhelp.mine.attention;

import java.util.List;


public class MineAttentionBean {

    private int total;
    private String message;
    private String code;
    private List<UserInfoBean> userInfo;

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

    public List<UserInfoBean> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfoBean> userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {

        private int userid;
        private String uesrname;
        private String face;
        private String firstname;
        private int id;
        private int MW_TargetType;
        private int newInfoCount;
        private String MW_AddTime;

        public int getNewInfoCount() {
            return newInfoCount;
        }

        public void setNewInfoCount(int newInfoCount) {
            this.newInfoCount = newInfoCount;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUesrname() {
            return uesrname;
        }

        public void setUesrname(String uesrname) {
            this.uesrname = uesrname;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMW_TargetType() {
            return MW_TargetType;
        }

        public void setMW_TargetType(int MW_TargetType) {
            this.MW_TargetType = MW_TargetType;
        }

        public String getMW_AddTime() {
            return MW_AddTime;
        }

        public void setMW_AddTime(String MW_AddTime) {
            this.MW_AddTime = MW_AddTime;
        }
    }
}
