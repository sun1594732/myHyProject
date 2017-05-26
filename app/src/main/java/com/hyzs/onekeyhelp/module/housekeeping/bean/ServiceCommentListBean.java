package com.hyzs.onekeyhelp.module.housekeeping.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ServiceCommentListBean {

    /**
     * lifeServiceCommentList : [{"userid":1,"uesrname":"张三","firstname":"张三","commentId":1,"commentTime":147859122,"commentContent":"张三","face":"http://app.hyzsnt.com/files/hjj.jpg"},{"userid":1,"uesrname":"张三","firstname":"张三","commentId":1,"commentTime":147859122,"commentContent":"张三","face":"http://app.hyzsnt.com/files/hjj.jpg"}]
     * total : 10
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<LifeServiceCommentListBean> lifeServiceCommentList;

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

    public List<LifeServiceCommentListBean> getLifeServiceCommentList() {
        return lifeServiceCommentList;
    }

    public void setLifeServiceCommentList(List<LifeServiceCommentListBean> lifeServiceCommentList) {
        this.lifeServiceCommentList = lifeServiceCommentList;
    }

    public static class LifeServiceCommentListBean {
        /**
         * userid : 1
         * uesrname : 张三
         * firstname : 张三
         * commentId : 1
         * commentTime : 147859122
         * commentContent : 张三
         * face : http://app.hyzsnt.com/files/hjj.jpg
         */

        private int userid;
        private String uesrname;
        private String firstname;
        private int commentId;
        private int commentTime;
        private String commentContent;
        private String face;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
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

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public int getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(int commentTime) {
            this.commentTime = commentTime;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
