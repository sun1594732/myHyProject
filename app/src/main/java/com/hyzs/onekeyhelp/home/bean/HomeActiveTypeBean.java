package com.hyzs.onekeyhelp.home.bean;

import java.util.List;

/**
 * Created by wubin on 2017/3/28.
 */

public class HomeActiveTypeBean {

    /**
     * activityType : [{"ID":3,"ET_Name":"周末生活"},{"ID":4,"ET_Name":"周边组团游"},{"ID":5,"ET_Name":"社区活动"}]
     * total : 3
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<ActivityTypeBean> activityType;

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

    public List<ActivityTypeBean> getActivityType() {
        return activityType;
    }

    public void setActivityType(List<ActivityTypeBean> activityType) {
        this.activityType = activityType;
    }

    public static class ActivityTypeBean {
        /**
         * ID : 3
         * ET_Name : 周末生活
         */

        private int ID;
        private String ET_Name;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getET_Name() {
            return ET_Name;
        }

        public void setET_Name(String ET_Name) {
            this.ET_Name = ET_Name;
        }
    }
}
