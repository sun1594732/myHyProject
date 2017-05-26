package com.hyzs.onekeyhelp.home.forum.bean;

/**
 * Created by wubin on 2017/3/18.
 */

public class ForumDetailBean {

    /**
     * forumDetails : {"forum_ID":1,"forum_UserID":179,"forum_AffixImgList":"","forum_Voice":"","forum_DateTime":"1489985640","commentCount":2,"nickName":null,"RNA":1,"NCCS":1,"trueName":"宁夏","sex":"女","identityMark":"10,2,3,1","community":"康惠园小区","avatar":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","diffTime":"1天前"}
     * message : 正常
     * code : 10000
     */

    private ForumDetailsBean forumDetails;
    private String message;
    private String code;

    public ForumDetailsBean getForumDetails() {
        return forumDetails;
    }

    public void setForumDetails(ForumDetailsBean forumDetails) {
        this.forumDetails = forumDetails;
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

    public static class ForumDetailsBean {
        /**
         * forum_ID : 1
         * forum_UserID : 179
         * forum_AffixImgList :
         * forum_Voice :
         * forum_DateTime : 1489985640
         * commentCount : 2
         * nickName : null
         * RNA : 1
         * NCCS : 1
         * trueName : 宁夏
         * sex : 女
         * identityMark : 10,2,3,1
         * community : 康惠园小区
         * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * diffTime : 1天前
         */

        private int forum_ID;
        private int forum_UserID;
        private String forum_AffixImgList;
        private String forum_Voice;
        private String forum_DateTime;
        private int commentCount;
        private Object nickName;
        private int RNA;
        private int NCCS;
        private int watchId;
        private String Circle_Content;
        private String forum_Content;
        private String trueName;
        private String sex;
        private String identityMark;
        private String community;
        private String avatar;
        private String diffTime;

        public int getWatchId() {
            return watchId;
        }

        public void setWatchId(int watchId) {
            this.watchId = watchId;
        }

        public String getForum_Content() {
            return forum_Content;
        }

        public void setForum_Content(String forum_Content) {
            this.forum_Content = forum_Content;
        }

        public int getForum_ID() {
            return forum_ID;
        }

        public void setForum_ID(int forum_ID) {
            this.forum_ID = forum_ID;
        }

        public int getForum_UserID() {
            return forum_UserID;
        }

        public void setForum_UserID(int forum_UserID) {
            this.forum_UserID = forum_UserID;
        }

        public String getForum_AffixImgList() {
            return forum_AffixImgList;
        }

        public void setForum_AffixImgList(String forum_AffixImgList) {
            this.forum_AffixImgList = forum_AffixImgList;
        }

        public String getForum_Voice() {
            return forum_Voice;
        }

        public void setForum_Voice(String forum_Voice) {
            this.forum_Voice = forum_Voice;
        }

        public String getForum_DateTime() {
            return forum_DateTime;
        }

        public void setForum_DateTime(String forum_DateTime) {
            this.forum_DateTime = forum_DateTime;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public Object getNickName() {
            return nickName;
        }

        public void setNickName(Object nickName) {
            this.nickName = nickName;
        }

        public int getRNA() {
            return RNA;
        }

        public void setRNA(int RNA) {
            this.RNA = RNA;
        }

        public int getNCCS() {
            return NCCS;
        }

        public void setNCCS(int NCCS) {
            this.NCCS = NCCS;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIdentityMark() {
            return identityMark;
        }

        public void setIdentityMark(String identityMark) {
            this.identityMark = identityMark;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDiffTime() {
            return diffTime;
        }

        public void setDiffTime(String diffTime) {
            this.diffTime = diffTime;
        }
    }
}
