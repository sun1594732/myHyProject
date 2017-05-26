package com.hyzs.onekeyhelp.mine.opinion.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/21.
 */

public class OpinionBean {

    /**
     * total : 8
     * sfc : [{"SFC_ID":1,"SFC_Name":"服务态度","SFC_State":0,"SFC_Sort":0},{"SFC_ID":2,"SFC_Name":"一键帮助","SFC_State":0,"SFC_Sort":0},{"SFC_ID":3,"SFC_Name":"车辆救援","SFC_State":0,"SFC_Sort":0},{"SFC_ID":4,"SFC_Name":"生活求助","SFC_State":0,"SFC_Sort":0},{"SFC_ID":5,"SFC_Name":"社区活动","SFC_State":0,"SFC_Sort":0},{"SFC_ID":6,"SFC_Name":"生活圈子","SFC_State":0,"SFC_Sort":0},{"SFC_ID":7,"SFC_Name":"社区论坛","SFC_State":0,"SFC_Sort":0},{"SFC_ID":8,"SFC_Name":"其他问题","SFC_State":0,"SFC_Sort":0}]
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<SfcBean> sfc;

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

    public List<SfcBean> getSfc() {
        return sfc;
    }

    public void setSfc(List<SfcBean> sfc) {
        this.sfc = sfc;
    }

    public static class SfcBean {
        /**
         * SFC_ID : 1
         * SFC_Name : 服务态度
         * SFC_State : 0
         * SFC_Sort : 0
         */

        private int SFC_ID;
        private String SFC_Name;
        private int SFC_State;
        private int SFC_Sort;

        public int getSFC_ID() {
            return SFC_ID;
        }

        public void setSFC_ID(int SFC_ID) {
            this.SFC_ID = SFC_ID;
        }

        public String getSFC_Name() {
            return SFC_Name;
        }

        public void setSFC_Name(String SFC_Name) {
            this.SFC_Name = SFC_Name;
        }

        public int getSFC_State() {
            return SFC_State;
        }

        public void setSFC_State(int SFC_State) {
            this.SFC_State = SFC_State;
        }

        public int getSFC_Sort() {
            return SFC_Sort;
        }

        public void setSFC_Sort(int SFC_Sort) {
            this.SFC_Sort = SFC_Sort;
        }
    }
}
