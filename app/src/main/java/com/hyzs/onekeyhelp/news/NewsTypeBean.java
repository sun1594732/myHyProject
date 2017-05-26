package com.hyzs.onekeyhelp.news;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/31.
 */

public class NewsTypeBean {

    /**
     * data : [{"id":98,"name":"热点新闻","pid":0},{"id":99,"name":"保健资讯","pid":0},{"id":100,"name":"失物招领","pid":0}]
     * code : 0
     * error :
     */

    private int code;
    private String error;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 98
         * name : 热点新闻
         * pid : 0
         */

        private int id;
        private String name;
        private int pid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }
}
