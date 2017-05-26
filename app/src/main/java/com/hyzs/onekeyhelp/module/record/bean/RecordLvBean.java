package com.hyzs.onekeyhelp.module.record.bean;

import java.util.List;

public class RecordLvBean {

    /**
     * total : 2
     * service_class : [{"VA_ID":3,"VA_Type":0,"VA_UserID":17,"VA_FamilyID":17,"VA_Time":1,"VA_Path":"http://app.hyzsnt.com/Files/17/voice/17_86d7cb58-7c34-4fbd-9270-d3792c469b12.aac","VA_AddDate":"1493088894"},{"VA_ID":9,"VA_Type":0,"VA_UserID":17,"VA_FamilyID":17,"VA_Time":2,"VA_Path":"http://app.hyzsnt.com/Files/17/voice/17_53e44330-9985-4c51-9f11-364c6fe86b65.aac","VA_AddDate":"1493199190"}]
     * userinfos : [{"id":17,"uesrname":"孙伟东","firstname":"","face":"Files/17/Avatar/2017424/17_47bbe0e0-c5a5-4f46-9c93-ef0045df8e7f.jpg"},{"id":17,"uesrname":"孙伟东","firstname":"","face":"Files/17/Avatar/2017424/17_47bbe0e0-c5a5-4f46-9c93-ef0045df8e7f.jpg"}]
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<ServiceClassBean> service_class;
    private List<UserinfosBean> userinfos;

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

    public List<ServiceClassBean> getService_class() {
        return service_class;
    }

    public void setService_class(List<ServiceClassBean> service_class) {
        this.service_class = service_class;
    }

    public List<UserinfosBean> getUserinfos() {
        return userinfos;
    }

    public void setUserinfos(List<UserinfosBean> userinfos) {
        this.userinfos = userinfos;
    }

    public static class ServiceClassBean {
        /**
         * VA_ID : 3
         * VA_Type : 0
         * VA_UserID : 17
         * VA_FamilyID : 17
         * VA_Time : 1
         * VA_Path : http://app.hyzsnt.com/Files/17/voice/17_86d7cb58-7c34-4fbd-9270-d3792c469b12.aac
         * VA_AddDate : 1493088894
         */

        private int VA_ID;
        private int VA_Type;
        private int VA_UserID;
        private int VA_FamilyID;
        private int VA_Time;
        private String VA_Path;
        private String VA_AddDate;

        public int getVA_ID() {
            return VA_ID;
        }

        public void setVA_ID(int VA_ID) {
            this.VA_ID = VA_ID;
        }

        public int getVA_Type() {
            return VA_Type;
        }

        public void setVA_Type(int VA_Type) {
            this.VA_Type = VA_Type;
        }

        public int getVA_UserID() {
            return VA_UserID;
        }

        public void setVA_UserID(int VA_UserID) {
            this.VA_UserID = VA_UserID;
        }

        public int getVA_FamilyID() {
            return VA_FamilyID;
        }

        public void setVA_FamilyID(int VA_FamilyID) {
            this.VA_FamilyID = VA_FamilyID;
        }

        public int getVA_Time() {
            return VA_Time;
        }

        public void setVA_Time(int VA_Time) {
            this.VA_Time = VA_Time;
        }

        public String getVA_Path() {
            return VA_Path;
        }

        public void setVA_Path(String VA_Path) {
            this.VA_Path = VA_Path;
        }

        public String getVA_AddDate() {
            return VA_AddDate;
        }

        public void setVA_AddDate(String VA_AddDate) {
            this.VA_AddDate = VA_AddDate;
        }
    }

    public static class UserinfosBean {
        /**
         * id : 17
         * uesrname : 孙伟东
         * firstname :
         * face : Files/17/Avatar/2017424/17_47bbe0e0-c5a5-4f46-9c93-ef0045df8e7f.jpg
         */

        private int id;
        private String uesrname;
        private String firstname;
        private String face;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
