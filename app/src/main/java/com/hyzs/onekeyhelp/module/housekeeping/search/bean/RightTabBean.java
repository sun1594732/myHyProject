package com.hyzs.onekeyhelp.module.housekeeping.search.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */

public class RightTabBean {

    /**
     * lifeServiceSP : [{"ID":1,"SP_Name":"保姆"},{"ID":2,"SP_Name":"扫地、抹桌子"}]
     * total : 2
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<LifeServiceSPBean> lifeServiceSP;

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

    public List<LifeServiceSPBean> getLifeServiceSP() {
        return lifeServiceSP;
    }

    public void setLifeServiceSP(List<LifeServiceSPBean> lifeServiceSP) {
        this.lifeServiceSP = lifeServiceSP;
    }

    public static class LifeServiceSPBean {
        /**
         * ID : 1
         * SP_Name : 保姆
         */

        private int ID;
        private String SP_Name;
        private int start;
        private int end;

        public LifeServiceSPBean(int ID, String SP_Name, int start, int end) {
            this.ID = ID;
            this.SP_Name = SP_Name;
            this.start = start;
            this.end = end;
        }

        public LifeServiceSPBean() {
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getSP_Name() {
            return SP_Name;
        }

        public void setSP_Name(String SP_Name) {
            this.SP_Name = SP_Name;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }
}
