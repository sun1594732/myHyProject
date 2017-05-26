package com.hyzs.onekeyhelp.family.circle.bean;

import java.util.List;

/**
 * Created by wubin on 2017/3/18.
 */

public class CircleFragmentBean {

    /**
     * circleLists : [{"rownumber":1,"CommentCount":1,"PointPraiseCount":0,"CommunityName":"4","ID":4,"Circle_UserID":179,"Circle_Content":"中华田园犬的内容","Circle_Voice":"","Circle_AffixImgList":"{http://localhost:49902/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a655.jpg},{http://localhost:49902/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a656.jpg}","Circle_DateTime":"/Date(1489814883053)/","Circle_Class":"0","Circle_Path":"活动入口地址","uesrname":"娃哈哈","firstname":"宁夏","face":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","Sex":"女"}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<CircleListsBean> circleLists;

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

    public List<CircleListsBean> getCircleLists() {
        return circleLists;
    }

    public void setCircleLists(List<CircleListsBean> circleLists) {
        this.circleLists = circleLists;
    }

    public static class CircleListsBean {
        /**
         * rownumber : 1
         * CommentCount : 1
         * PointPraiseCount : 0
         * CommunityName : 4
         * ID : 4
         * Circle_UserID : 179
         * Circle_Content : 中华田园犬的内容
         * Circle_Voice :
         * Circle_AffixImgList : {http://localhost:49902/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a655.jpg},{http://localhost:49902/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a656.jpg}
         * Circle_DateTime : /Date(1489814883053)/
         * Circle_Class : 0
         * Circle_Path : 活动入口地址
         * uesrname : 娃哈哈
         * firstname : 宁夏
         * face : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * Sex : 女
         */

        private int rownumber;
        private int CommentCount;
        private int PointPraiseCount;
        private String CommunityName;
        private int ID;
        private int Circle_UserID;
        private String Circle_Content;
        private String Circle_Voice;
        private String Circle_AffixImgList;
        private String Circle_DateTime;
        private String Circle_Class;
        private String Circle_Path;
        private String uesrname;
        private String firstname;
        private String face;
        private String Sex;
        private String Circle_SPURL;
        private String Circle_SPDYZT;

        public String getCircle_SPURL() {
            return Circle_SPURL;
        }

        public void setCircle_SPURL(String circle_SPURL) {
            Circle_SPURL = circle_SPURL;
        }

        public String getCircle_SPDYZT() {
            return Circle_SPDYZT;
        }

        public void setCircle_SPDYZT(String circle_SPDYZT) {
            Circle_SPDYZT = circle_SPDYZT;
        }

        public int getRownumber() {
            return rownumber;
        }

        public void setRownumber(int rownumber) {
            this.rownumber = rownumber;
        }

        public int getCommentCount() {
            return CommentCount;
        }

        public void setCommentCount(int CommentCount) {
            this.CommentCount = CommentCount;
        }

        public int getPointPraiseCount() {
            return PointPraiseCount;
        }

        public void setPointPraiseCount(int PointPraiseCount) {
            this.PointPraiseCount = PointPraiseCount;
        }

        public String getCommunityName() {
            return CommunityName;
        }

        public void setCommunityName(String CommunityName) {
            this.CommunityName = CommunityName;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getCircle_UserID() {
            return Circle_UserID;
        }

        public void setCircle_UserID(int Circle_UserID) {
            this.Circle_UserID = Circle_UserID;
        }

        public String getCircle_Content() {
            return Circle_Content;
        }

        public void setCircle_Content(String Circle_Content) {
            this.Circle_Content = Circle_Content;
        }

        public String getCircle_Voice() {
            return Circle_Voice;
        }

        public void setCircle_Voice(String Circle_Voice) {
            this.Circle_Voice = Circle_Voice;
        }

        public String getCircle_AffixImgList() {
            return Circle_AffixImgList;
        }

        public void setCircle_AffixImgList(String Circle_AffixImgList) {
            this.Circle_AffixImgList = Circle_AffixImgList;
        }

        public String getCircle_DateTime() {
            return Circle_DateTime;
        }

        public void setCircle_DateTime(String Circle_DateTime) {
            this.Circle_DateTime = Circle_DateTime;
        }

        public String getCircle_Class() {
            return Circle_Class;
        }

        public void setCircle_Class(String Circle_Class) {
            this.Circle_Class = Circle_Class;
        }

        public String getCircle_Path() {
            return Circle_Path;
        }

        public void setCircle_Path(String Circle_Path) {
            this.Circle_Path = Circle_Path;
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
