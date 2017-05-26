package com.hyzs.onekeyhelp.module.housekeeping.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */

public class MineServiceBean implements Serializable {
    /**
     * lifeServicePersonal : [{"id":1,"userid":1,"face":"http://app.hyzsnt.com/Files/1/Avatar/2017429/1_1d30862c-cb92-4bc8-bc34-e041f5b81232.jpg","uesrname":"嘻嘻哈哈","firstname":"嘻嘻哈哈","nativeplace":"嘻嘻哈哈","phone":"","msalary":0,"recommendIndex":0,"distance":"13","tag":"月嫂,四年经验,全职","publishTime":1494113290}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<LifeServicePersonalBean> lifeServicePersonal;

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

    public List<LifeServicePersonalBean> getLifeServicePersonal() {
        return lifeServicePersonal;
    }

    public void setLifeServicePersonal(List<LifeServicePersonalBean> lifeServicePersonal) {
        this.lifeServicePersonal = lifeServicePersonal;
    }

    public static class LifeServicePersonalBean implements Serializable{
        /**
         * id : 1
         * userid : 1
         * face : http://app.hyzsnt.com/Files/1/Avatar/2017429/1_1d30862c-cb92-4bc8-bc34-e041f5b81232.jpg
         * uesrname : 嘻嘻哈哈
         * firstname : 嘻嘻哈哈
         * nativeplace : 嘻嘻哈哈
         * phone :
         * msalary : 0
         * recommendIndex : 0
         * distance : 13
         * tag : 月嫂,四年经验,全职
         * publishTime : 1494113290
         */

        private int id;
        private int userid;
        private String face;
        private String uesrname;
        private String firstname;
        private String nativeplace;
        private String phone;
        private int msalary;
        private int recommendIndex;
        private String distance;
        private String tag;
        private int publishTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
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

        public String getNativeplace() {
            return nativeplace;
        }

        public void setNativeplace(String nativeplace) {
            this.nativeplace = nativeplace;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getMsalary() {
            return msalary;
        }

        public void setMsalary(int msalary) {
            this.msalary = msalary;
        }

        public int getRecommendIndex() {
            return recommendIndex;
        }

        public void setRecommendIndex(int recommendIndex) {
            this.recommendIndex = recommendIndex;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(int publishTime) {
            this.publishTime = publishTime;
        }
    }
}
