package com.hyzs.onekeyhelp.home.forum.bean;

import java.util.List;

/**
 * Created by wubin on 2017/3/18.
 */

public class ForumDetailCommentBean {

    /**
     * forumCommentList : [{"fc_ForumID":1,"fc_UserID":179,"fc_Content":"sdfsdfsfsdf211123","fc_AffixImgList":"","fc_Voice":"","fc_Count":3,"fc_DateTime":"1489985692","diffTime":"28分钟前","nickName":"娃哈哈","trueName":"宁夏","avatar":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","sex":"女","RNA":1,"NCCS":1,"identityMark":"10,2,3,1","community":"康惠园小区"}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<ForumCommentListBean> forumCommentList;

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

    public List<ForumCommentListBean> getForumCommentList() {
        return forumCommentList;
    }

    public void setForumCommentList(List<ForumCommentListBean> forumCommentList) {
        this.forumCommentList = forumCommentList;
    }

    public static class ForumCommentListBean {
        /**
         * fc_ForumID : 1
         * fc_UserID : 179
         * fc_Content : sdfsdfsfsdf211123
         * fc_AffixImgList :
         * fc_Voice :
         * fc_Count : 3
         * fc_DateTime : 1489985692
         * diffTime : 28分钟前
         * nickName : 娃哈哈
         * trueName : 宁夏
         * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * sex : 女
         * RNA : 1
         * NCCS : 1
         * identityMark : 10,2,3,1
         * community : 康惠园小区
         */

        private int fc_ForumID;
        private int fc_UserID;
        private String fc_Content;
        private String fc_AffixImgList;
        private String fc_Voice;
        private int fc_Count;
        private int watchId;
        private String fc_DateTime;
        private String diffTime;
        private String nickName;
        private String trueName;
        private String avatar;
        private String sex;
        private int RNA;
        private int NCCS;
        private String identityMark;
        private String community;

        public int getWatchId() {
            return watchId;
        }

        public void setWatchId(int watchId) {
            this.watchId = watchId;
        }

        public int getFc_ForumID() {
            return fc_ForumID;
        }

        public void setFc_ForumID(int fc_ForumID) {
            this.fc_ForumID = fc_ForumID;
        }

        public int getFc_UserID() {
            return fc_UserID;
        }

        public void setFc_UserID(int fc_UserID) {
            this.fc_UserID = fc_UserID;
        }

        public String getFc_Content() {
            return fc_Content;
        }

        public void setFc_Content(String fc_Content) {
            this.fc_Content = fc_Content;
        }

        public String getFc_AffixImgList() {
            return fc_AffixImgList;
        }

        public void setFc_AffixImgList(String fc_AffixImgList) {
            this.fc_AffixImgList = fc_AffixImgList;
        }

        public String getFc_Voice() {
            return fc_Voice;
        }

        public void setFc_Voice(String fc_Voice) {
            this.fc_Voice = fc_Voice;
        }

        public int getFc_Count() {
            return fc_Count;
        }

        public void setFc_Count(int fc_Count) {
            this.fc_Count = fc_Count;
        }

        public String getFc_DateTime() {
            return fc_DateTime;
        }

        public void setFc_DateTime(String fc_DateTime) {
            this.fc_DateTime = fc_DateTime;
        }

        public String getDiffTime() {
            return diffTime;
        }

        public void setDiffTime(String diffTime) {
            this.diffTime = diffTime;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
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
    }
}
