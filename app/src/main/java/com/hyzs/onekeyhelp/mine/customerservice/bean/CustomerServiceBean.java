package com.hyzs.onekeyhelp.mine.customerservice.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/21.
 */

public class CustomerServiceBean {


    private int total;
    private String message;
    private String code;
    private List<ServiceClassBean> service_class;

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

    public static class ServiceClassBean {

        private int id;
        private int S_UserID;
        private String S_AddTime;
        private String S_Tel;
        private String S_Name;
        private String S_Face;
        private String S_WorkTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getS_UserID() {
            return S_UserID;
        }

        public void setS_UserID(int S_UserID) {
            this.S_UserID = S_UserID;
        }

        public String getS_AddTime() {
            return S_AddTime;
        }

        public void setS_AddTime(String S_AddTime) {
            this.S_AddTime = S_AddTime;
        }

        public String getS_Tel() {
            return S_Tel;
        }

        public void setS_Tel(String S_Tel) {
            this.S_Tel = S_Tel;
        }

        public String getS_Name() {
            return S_Name;
        }

        public void setS_Name(String S_Name) {
            this.S_Name = S_Name;
        }

        public String getS_Face() {
            return S_Face;
        }

        public void setS_Face(String S_Face) {
            this.S_Face = S_Face;
        }

        public String getS_WorkTime() {
            return S_WorkTime;
        }

        public void setS_WorkTime(String S_WorkTime) {
            this.S_WorkTime = S_WorkTime;
        }
    }
}
