package com.hyzs.onekeyhelp.mine.bean;

/**
 * Created by wubin on 2017/4/19.
 */

public class MineInfoBean {

    /**
     * personalUserInfo : {"userid":1,"uesrname":"嘻嘻哈哈","firstname":"哈嘻哈嘻","personalizedSignature":"","face":"http://localhost:49902/Files/1/Avatar/2017417/1_2b640f16-e63b-4567-a714-fd5a582bebf8.jpg","phone":"15022341205","defaultCommunityName":"白领家园","defaultCommunityId":7,"points":0,"yue":0.4,"unpaid":0}
     * message : 正常
     * code : 10000
     */

    private PersonalUserInfoBean personalUserInfo;
    private String message;
    private String code;

    public PersonalUserInfoBean getPersonalUserInfo() {
        return personalUserInfo;
    }

    public void setPersonalUserInfo(PersonalUserInfoBean personalUserInfo) {
        this.personalUserInfo = personalUserInfo;
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

    public static class PersonalUserInfoBean {
        /**
         * userid : 1
         * uesrname : 嘻嘻哈哈
         * firstname : 哈嘻哈嘻
         * personalizedSignature :
         * face : http://localhost:49902/Files/1/Avatar/2017417/1_2b640f16-e63b-4567-a714-fd5a582bebf8.jpg
         * phone : 15022341205
         * defaultCommunityName : 白领家园
         * defaultCommunityId : 7
         * points : 0
         * yue : 0.4
         * unpaid : 0
         */

        private int userid;
        private String uesrname;
        private String firstname;
        private String personalizedSignature;
        private String face;
        private String phone;
        private String defaultCommunityName;
        private int defaultCommunityId;
        private int points;
        private double yue;
        private int unpaid;

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

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getPersonalizedSignature() {
            return personalizedSignature;
        }

        public void setPersonalizedSignature(String personalizedSignature) {
            this.personalizedSignature = personalizedSignature;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDefaultCommunityName() {
            return defaultCommunityName;
        }

        public void setDefaultCommunityName(String defaultCommunityName) {
            this.defaultCommunityName = defaultCommunityName;
        }

        public int getDefaultCommunityId() {
            return defaultCommunityId;
        }

        public void setDefaultCommunityId(int defaultCommunityId) {
            this.defaultCommunityId = defaultCommunityId;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public double getYue() {
            return yue;
        }

        public void setYue(double yue) {
            this.yue = yue;
        }

        public int getUnpaid() {
            return unpaid;
        }

        public void setUnpaid(int unpaid) {
            this.unpaid = unpaid;
        }
    }
}
