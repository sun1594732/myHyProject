package com.hyzs.onekeyhelp.alert;

import java.util.List;

/**
 * Created by wubin on 2017/4/11.
 */

public class AlertBean {

    /**
     * total : 29
     * DistressAlarms : [{"id":70,"type":0,"state":"N"},{"id":69,"type":0,"state":"N"}]
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<DistressAlarmsBean> DistressAlarms;

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

    public List<DistressAlarmsBean> getDistressAlarms() {
        return DistressAlarms;
    }

    public void setDistressAlarms(List<DistressAlarmsBean> DistressAlarms) {
        this.DistressAlarms = DistressAlarms;
    }

    public static class DistressAlarmsBean {
        /**
         * id : 70
         * type : 0
         * state : N
         */

        private int id;
        private int type;
        private String state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
