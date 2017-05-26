package com.hyzs.onekeyhelp.family.albumimport;

import java.util.List;

public class ImportBean {

    /**
     * personalAlbumDynamicList : [{"images":[{"ID":64,"AI_URL":"http://app.hyzsnt.com/Files/1/img/1_0a527f65-34c4-41b2-a866-9017640acab7.jpg","PA_ID":23}],"ID":23,"PA_UserID":1,"PA_Content":"666\n","PA_LookRole":0,"PA_PublishDateTime":"1493279785"},{"images":[{"ID":1,"AI_URL":"http://app.hyzsnt.com/Files/1/img/1_4eb37c8d-0e57-41cc-8c36-833cbabe6450.jpg","PA_ID":1}],"ID":1,"PA_UserID":1,"PA_Content":"a","PA_LookRole":0,"PA_PublishDateTime":"1492951317"}]
     * total : 2
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<PersonalAlbumDynamicListBean> personalAlbumDynamicList;

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

    public static class PersonalAlbumDynamicListBean {
        /**
         * images : [{"ID":64,"AI_URL":"http://app.hyzsnt.com/Files/1/img/1_0a527f65-34c4-41b2-a866-9017640acab7.jpg","PA_ID":23}]
         * ID : 23
         * PA_UserID : 1
         * PA_Content : 666

         * PA_LookRole : 0
         * PA_PublishDateTime : 1493279785
         */

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
            /**
             * ID : 64
             * AI_URL : http://app.hyzsnt.com/Files/1/img/1_0a527f65-34c4-41b2-a866-9017640acab7.jpg
             * PA_ID : 23
             */

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
