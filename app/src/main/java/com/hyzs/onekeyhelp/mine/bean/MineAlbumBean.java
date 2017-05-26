package com.hyzs.onekeyhelp.mine.bean;

import java.util.List;

/**
 * Created by wubin on 2017/3/27.
 */

public class MineAlbumBean {


    private UserInfoBean userInfo;
    private int total;
    private String message;
    private String code;
    private List<PersonalAlbumDynamicListBean> personalAlbumDynamicList;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

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

    public List<PersonalAlbumDynamicListBean> getPersonalAlbumDynamicList() {
        return personalAlbumDynamicList;
    }

    public void setPersonalAlbumDynamicList(List<PersonalAlbumDynamicListBean> personalAlbumDynamicList) {
        this.personalAlbumDynamicList = personalAlbumDynamicList;
    }

    public static class UserInfoBean {

        private int userid;
        private String uesrname;
        private String face;
        private String personalizedSignature;
        private int RNA;
        private int NCCS;
        private String identityMark;

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

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getPersonalizedSignature() {
            return personalizedSignature;
        }

        public void setPersonalizedSignature(String personalizedSignature) {
            this.personalizedSignature = personalizedSignature;
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
    }

    public static class PersonalAlbumDynamicListBean {

        private int ID;
        private int PA_UserID;
        private String PA_Content;
        private int PA_LookRole;
        private String PA_PublishDateTime;
        private List<ImagesBean> images;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getPA_UserID() {
            return PA_UserID;
        }

        public void setPA_UserID(int PA_UserID) {
            this.PA_UserID = PA_UserID;
        }

        public String getPA_Content() {
            return PA_Content;
        }

        public void setPA_Content(String PA_Content) {
            this.PA_Content = PA_Content;
        }

        public int getPA_LookRole() {
            return PA_LookRole;
        }

        public void setPA_LookRole(int PA_LookRole) {
            this.PA_LookRole = PA_LookRole;
        }

        public String getPA_PublishDateTime() {
            return PA_PublishDateTime;
        }

        public void setPA_PublishDateTime(String PA_PublishDateTime) {
            this.PA_PublishDateTime = PA_PublishDateTime;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {

            private int ID;
            private String AI_URL;
            private int PA_ID;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getAI_URL() {
                return AI_URL;
            }

            public void setAI_URL(String AI_URL) {
                this.AI_URL = AI_URL;
            }

            public int getPA_ID() {
                return PA_ID;
            }

            public void setPA_ID(int PA_ID) {
                this.PA_ID = PA_ID;
            }
        }
    }
}
