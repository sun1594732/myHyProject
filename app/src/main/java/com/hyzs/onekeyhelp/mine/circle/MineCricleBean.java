package com.hyzs.onekeyhelp.mine.circle;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/28.
 */

public class MineCricleBean {


    /**
     * personalCenterMyCircle : [{"CommentCount":0,"PointPraiseCount":0,"CommunityName":"康惠园小区","ID":112,"Circleid":1,"CircleName":"兴趣","Circle_UserID":0,"Circle_Content":"12345","Circle_Voice":"","Circle_AffixImgList":"{http://192.168.1.186:803/Files/179/img/179_2e1c68c8-8641-4e2e-b163-c3df15fde55d.jpg},{http://192.168.1.186:803/Files/179/img/179_a83c6adc-9c01-4246-9660-82b09252fc46.jpg},{http://192.168.1.186:803/Files/179/img/179_46bb5812-19f8-4246-9b3a-5512852aa348.jpg},{http://192.168.1.186:803/Files/179/img/179_e59364f3-4632-490d-b4a0-36c57289f369.jpg},{http://192.168.1.186:803/Files/179/img/179_228d8d9b-ab6e-4219-a7db-21bb7dd4de2b.jpg},{http://192.168.1.186:803/Files/179/img/179_02be6360-2d3e-4ffe-ad2f-32275d7b80b2.jpg},{http://192.168.1.186:803/Files/179/img/179_8dcfec2f-b569-4b15-a482-b6c561a3b222.jpg},{http://192.168.1.186:803/Files/179/img/179_70f1253a-d1d0-4ae6-95e8-b6fd6edff121.jpg}","Circle_DateTime":"1490513136","Circle_Class":0,"Circle_Path":"活动入口地址","uesrname":"娃哈哈","firstname":"宁夏","face":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","Sex":"女","diffTime":"2天前"},{"CommentCount":0,"PointPraiseCount":0,"CommunityName":"康惠园小区","ID":110,"Circleid":1,"CircleName":"兴趣","Circle_UserID":0,"Circle_Content":"34","Circle_Voice":"","Circle_AffixImgList":"{http://192.168.1.186:803/Files/179/img/179_15248d29-bca0-4f53-a36d-a74d00b90292.jpg},{http://192.168.1.186:803/Files/179/img/179_6acc8df2-4731-41a3-83fd-293f44984afb.jpg}","Circle_DateTime":"1490346939","Circle_Class":0,"Circle_Path":"活动入口地址","uesrname":"娃哈哈","firstname":"宁夏","face":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","Sex":"女","diffTime":"4天前"}]
     * total : 2
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<PersonalCenterMyCircleBean> personalCenterMyCircle;

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

    public List<PersonalCenterMyCircleBean> getPersonalCenterMyCircle() {
        return personalCenterMyCircle;
    }

    public void setPersonalCenterMyCircle(List<PersonalCenterMyCircleBean> personalCenterMyCircle) {
        this.personalCenterMyCircle = personalCenterMyCircle;
    }

    public static class PersonalCenterMyCircleBean {
        /**
         * CommentCount : 0
         * PointPraiseCount : 0
         * CommunityName : 康惠园小区
         * ID : 112
         * Circleid : 1
         * CircleName : 兴趣
         * Circle_UserID : 0
         * Circle_Content : 12345
         * Circle_Voice :
         * Circle_AffixImgList : {http://192.168.1.186:803/Files/179/img/179_2e1c68c8-8641-4e2e-b163-c3df15fde55d.jpg},{http://192.168.1.186:803/Files/179/img/179_a83c6adc-9c01-4246-9660-82b09252fc46.jpg},{http://192.168.1.186:803/Files/179/img/179_46bb5812-19f8-4246-9b3a-5512852aa348.jpg},{http://192.168.1.186:803/Files/179/img/179_e59364f3-4632-490d-b4a0-36c57289f369.jpg},{http://192.168.1.186:803/Files/179/img/179_228d8d9b-ab6e-4219-a7db-21bb7dd4de2b.jpg},{http://192.168.1.186:803/Files/179/img/179_02be6360-2d3e-4ffe-ad2f-32275d7b80b2.jpg},{http://192.168.1.186:803/Files/179/img/179_8dcfec2f-b569-4b15-a482-b6c561a3b222.jpg},{http://192.168.1.186:803/Files/179/img/179_70f1253a-d1d0-4ae6-95e8-b6fd6edff121.jpg}
         * Circle_DateTime : 1490513136
         * Circle_Class : 0
         * Circle_Path : 活动入口地址
         * uesrname : 娃哈哈
         * firstname : 宁夏
         * face : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * Sex : 女
         * diffTime : 2天前
         */

        private int CommentCount;
        private int PointPraiseCount;
        private String CommunityName;
        private int ID;
        private int Circleid;
        private String CircleName;
        private int Circle_UserID;
        private String Circle_Content;
        private String Circle_Voice;
        private String Circle_AffixImgList;
        private String Circle_DateTime;
        private int Circle_Class;
        private String Circle_Path;
        private String uesrname;
        private String firstname;
        private String face;
        private String Sex;
        private String diffTime;

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

        public int getCircleid() {
            return Circleid;
        }

        public void setCircleid(int Circleid) {
            this.Circleid = Circleid;
        }

        public String getCircleName() {
            return CircleName;
        }

        public void setCircleName(String CircleName) {
            this.CircleName = CircleName;
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

        public int getCircle_Class() {
            return Circle_Class;
        }

        public void setCircle_Class(int Circle_Class) {
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

        public String getDiffTime() {
            return diffTime;
        }

        public void setDiffTime(String diffTime) {
            this.diffTime = diffTime;
        }
    }
}
