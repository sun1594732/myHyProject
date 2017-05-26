package com.hyzs.onekeyhelp.module.housekeeping.bean;

import java.util.List;

/**
 * Created by wubin on 2017/5/9.
 */

public class PCCABean {

    /**
     * pcca : [{"id":34,"code":820000,"name":"澳  门"}]
     * total : 68
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<PccaBean> pcca;

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

    public List<PccaBean> getPcca() {
        return pcca;
    }

    public void setPcca(List<PccaBean> pcca) {
        this.pcca = pcca;
    }

    public static class PccaBean {
        /**
         * id : 34
         * code : 820000
         * name : 澳  门
         */

        private int id;
        private int code;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
