package com.hyzs.onekeyhelp.family.circle.bean;

import java.util.List;

/**
 * Created by wubin on 2017/3/18.
 */

public class CircleDetailCommentBean {

    /**
     * circleCommentLists : [{"rownumber":1,"ID":1,"CC_Content":"评论内容","CC_AffixImgLis":"","CC_Voice":"","CC_Count":0,"CC_DateTime":"2017-03-16","uesrname":"律政先锋","firstname":"律政先锋","face":"http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg","Sex":"女"}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<CircleCommentListsBean> circleCommentLists;

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

    public List<CircleCommentListsBean> getCircleCommentLists() {
        return circleCommentLists;
    }

    public void setCircleCommentLists(List<CircleCommentListsBean> circleCommentLists) {
        this.circleCommentLists = circleCommentLists;
    }

    public static class CircleCommentListsBean {
        /**
         * rownumber : 1
         * ID : 1
         * CC_Content : 评论内容
         * CC_AffixImgLis :
         * CC_Voice :
         * CC_Count : 0
         * CC_DateTime : 2017-03-16
         * uesrname : 律政先锋
         * firstname : 律政先锋
         * face : http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg
         * Sex : 女
         */

        private String diffTime;
        private int rownumber;
        private int userid;
        private int ID;
        private String CC_Content;
        private String CC_AffixImgLis;
        private String CC_Voice;
        private int CC_Count;
        private String CC_DateTime;
        private String uesrname;
        private String firstname;
        private String face;
        private String Sex;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getDiffTime() {
            return diffTime;
        }

        public void setDiffTime(String diffTime) {
            this.diffTime = diffTime;
        }

        public int getRownumber() {
            return rownumber;
        }

        public void setRownumber(int rownumber) {
            this.rownumber = rownumber;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getCC_Content() {
            return CC_Content;
        }

        public void setCC_Content(String CC_Content) {
            this.CC_Content = CC_Content;
        }

        public String getCC_AffixImgLis() {
            return CC_AffixImgLis;
        }

        public void setCC_AffixImgLis(String CC_AffixImgLis) {
            this.CC_AffixImgLis = CC_AffixImgLis;
        }

        public String getCC_Voice() {
            return CC_Voice;
        }

        public void setCC_Voice(String CC_Voice) {
            this.CC_Voice = CC_Voice;
        }

        public int getCC_Count() {
            return CC_Count;
        }

        public void setCC_Count(int CC_Count) {
            this.CC_Count = CC_Count;
        }

        public String getCC_DateTime() {
            return CC_DateTime;
        }

        public void setCC_DateTime(String CC_DateTime) {
            this.CC_DateTime = CC_DateTime;
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

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }
    }
}
