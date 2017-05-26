package com.hyzs.onekeyhelp.mine.forum;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/28.
 */

public class MineForumBean {

    /**
     * personalCenterMyPost : [{"forum_ID":50,"forum_UserID":179,"Forum_TypeID":3,"Forum_TypeName":"风光展示","forum_AffixImgList":"{http://192.168.1.186:803/Files/179/img/179_ffe9dbdb-85da-431a-a3b0-aa62b5758686.jpg},{http://192.168.1.186:803/Files/179/img/179_23f038fd-0273-4ba7-a41f-0062cb36048c.jpg},{http://192.168.1.186:803/Files/179/img/179_e1e5ca4d-85ac-4b2d-b894-46ee11de16b8.jpg},{http://192.168.1.186:803/Files/179/img/179_3bb4dc37-7c5a-4728-83c3-3836cfece8ca.jpg},{http://192.168.1.186:803/Files/179/img/179_c308962d-8cdf-44fc-8a71-7f6ef850a9f5.jpg},{http://192.168.1.186:803/Files/179/img/179_3a626b43-4461-4410-bb0f-7591f0b8d478.jpg},{http://192.168.1.186:803/Files/179/img/179_aa587f01-c64b-4a9f-a839-b28299729215.jpg},{http://192.168.1.186:803/Files/179/img/179_11d42940-c90c-4119-8924-9bd52567f5dc.jpg}","forum_Voice":"","diffTime":"2天前","forum_DateTime":"1490514154","commentCount":3,"forum_Content":"","nickName":"娃哈哈","avatar":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","RNA":1,"NCCS":1,"trueName":"宁夏","sex":null,"identityMark":"10,2,3,1","community":"康惠园小区"},{"forum_ID":49,"forum_UserID":179,"Forum_TypeID":1,"Forum_TypeName":"养生专题","forum_AffixImgList":"{http://192.168.1.186:803/Files/179/img/179_01e3b9b9-7938-4eec-a4a5-0a5e04d6b05a.jpg},{http://192.168.1.186:803/Files/179/img/179_8042c439-4536-417b-9a97-bfffe283cfe9.jpg},{http://192.168.1.186:803/Files/179/img/179_b0017079-a877-4b1b-8846-01e46ceedba1.jpg}","forum_Voice":"","diffTime":"5天前","forum_DateTime":"1490269726","commentCount":4,"forum_Content":"Ygc","nickName":"娃哈哈","avatar":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","RNA":1,"NCCS":1,"trueName":"宁夏","sex":null,"identityMark":"10,2,3,1","community":"康惠园小区"}]
     * total : 35
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<PersonalCenterMyPostBean> personalCenterMyPost;

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

    public List<PersonalCenterMyPostBean> getPersonalCenterMyPost() {
        return personalCenterMyPost;
    }

    public void setPersonalCenterMyPost(List<PersonalCenterMyPostBean> personalCenterMyPost) {
        this.personalCenterMyPost = personalCenterMyPost;
    }

    public static class PersonalCenterMyPostBean {
        /**
         * forum_ID : 50
         * forum_UserID : 179
         * Forum_TypeID : 3
         * Forum_TypeName : 风光展示
         * forum_AffixImgList : {http://192.168.1.186:803/Files/179/img/179_ffe9dbdb-85da-431a-a3b0-aa62b5758686.jpg},{http://192.168.1.186:803/Files/179/img/179_23f038fd-0273-4ba7-a41f-0062cb36048c.jpg},{http://192.168.1.186:803/Files/179/img/179_e1e5ca4d-85ac-4b2d-b894-46ee11de16b8.jpg},{http://192.168.1.186:803/Files/179/img/179_3bb4dc37-7c5a-4728-83c3-3836cfece8ca.jpg},{http://192.168.1.186:803/Files/179/img/179_c308962d-8cdf-44fc-8a71-7f6ef850a9f5.jpg},{http://192.168.1.186:803/Files/179/img/179_3a626b43-4461-4410-bb0f-7591f0b8d478.jpg},{http://192.168.1.186:803/Files/179/img/179_aa587f01-c64b-4a9f-a839-b28299729215.jpg},{http://192.168.1.186:803/Files/179/img/179_11d42940-c90c-4119-8924-9bd52567f5dc.jpg}
         * forum_Voice :
         * diffTime : 2天前
         * forum_DateTime : 1490514154
         * commentCount : 3
         * forum_Content :
         * nickName : 娃哈哈
         * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * RNA : 1
         * NCCS : 1
         * trueName : 宁夏
         * sex : null
         * identityMark : 10,2,3,1
         * community : 康惠园小区
         */

        private int forum_ID;
        private int forum_UserID;
        private int Forum_TypeID;
        private String Forum_TypeName;
        private String forum_AffixImgList;
        private String forum_Voice;
        private String diffTime;
        private String forum_DateTime;
        private int commentCount;
        private String forum_Content;
        private String nickName;
        private String avatar;
        private int RNA;
        private int NCCS;
        private String trueName;
        private String sex;
        private String identityMark;
        private String community;

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

        public int getForum_TypeID() {
            return Forum_TypeID;
        }

        public void setForum_TypeID(int Forum_TypeID) {
            this.Forum_TypeID = Forum_TypeID;
        }

        public String getForum_TypeName() {
            return Forum_TypeName;
        }

        public void setForum_TypeName(String Forum_TypeName) {
            this.Forum_TypeName = Forum_TypeName;
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

        public String getDiffTime() {
            return diffTime;
        }

        public void setDiffTime(String diffTime) {
            this.diffTime = diffTime;
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

        public String getForum_Content() {
            return forum_Content;
        }

        public void setForum_Content(String forum_Content) {
            this.forum_Content = forum_Content;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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
    }
}
