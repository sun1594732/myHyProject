package com.hyzs.onekeyhelp.home.bean;

import java.util.List;


public class NewsListBean {

    /**
     * newsListByClassify : [{"id":5346,"title":"山楂配一物，可降脂、降血压、缓解腰酸疼痛！","imgURL":"","resource":"今日头条","sj":"1490683749","viewnum":0}]
     * total : 286
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<NewsListByClassifyBean> newsListByClassify;

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

    public List<NewsListByClassifyBean> getNewsListByClassify() {
        return newsListByClassify;
    }

    public void setNewsListByClassify(List<NewsListByClassifyBean> newsListByClassify) {
        this.newsListByClassify = newsListByClassify;
    }

    public static class NewsListByClassifyBean {
        /**
         * id : 5346
         * title : 山楂配一物，可降脂、降血压、缓解腰酸疼痛！
         * imgURL :
         * resource : 今日头条
         * sj : 1490683749
         * viewnum : 0
         */

        private String diffTime;
        private int id;
        private String title;
        private String imgURL;
        private String resource;
        private String sj;
        private int viewnum;

        public String getDiffTime() {
            return diffTime;
        }

        public void setDiffTime(String diffTime) {
            this.diffTime = diffTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgURL() {
            return imgURL;
        }

        public void setImgURL(String imgURL) {
            this.imgURL = imgURL;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public String getSj() {
            return sj;
        }

        public void setSj(String sj) {
            this.sj = sj;
        }

        public int getViewnum() {
            return viewnum;
        }

        public void setViewnum(int viewnum) {
            this.viewnum = viewnum;
        }
    }
}
