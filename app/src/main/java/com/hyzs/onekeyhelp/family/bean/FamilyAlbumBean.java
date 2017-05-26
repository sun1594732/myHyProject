package com.hyzs.onekeyhelp.family.bean;

import java.util.List;

/**
 * Created by wubin on 2017/4/27.
 */

public class FamilyAlbumBean {

    /**
     * groupAlbumDynamicList : [{"albums":[{"id":0,"GAI_ID":0,"GAI_URL":null}],"ID":1,"GA_UserID":1,"GA_GroupID":2,"GA_Content":"测试内容","GA_PublishDateTime":"1493281740","diffTime":null}]
     * total : 0
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<GroupAlbumDynamicListBean> groupAlbumDynamicList;

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

    public List<GroupAlbumDynamicListBean> getGroupAlbumDynamicList() {
        return groupAlbumDynamicList;
    }

    public void setGroupAlbumDynamicList(List<GroupAlbumDynamicListBean> groupAlbumDynamicList) {
        this.groupAlbumDynamicList = groupAlbumDynamicList;
    }

    public static class GroupAlbumDynamicListBean {
        /**
         * albums : [{"id":0,"GAI_ID":0,"GAI_URL":null}]
         * ID : 1
         * GA_UserID : 1
         * GA_GroupID : 2
         * GA_Content : 测试内容
         * GA_PublishDateTime : 1493281740
         * diffTime : null
         */

        private int ID;
        private int GA_UserID;
        private int GA_GroupID;
        private String GA_Content;
        private String GA_PublishDateTime;
        private Object diffTime;
        private List<AlbumsBean> albums;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getGA_UserID() {
            return GA_UserID;
        }

        public void setGA_UserID(int GA_UserID) {
            this.GA_UserID = GA_UserID;
        }

        public int getGA_GroupID() {
            return GA_GroupID;
        }

        public void setGA_GroupID(int GA_GroupID) {
            this.GA_GroupID = GA_GroupID;
        }

        public String getGA_Content() {
            return GA_Content;
        }

        public void setGA_Content(String GA_Content) {
            this.GA_Content = GA_Content;
        }

        public String getGA_PublishDateTime() {
            return GA_PublishDateTime;
        }

        public void setGA_PublishDateTime(String GA_PublishDateTime) {
            this.GA_PublishDateTime = GA_PublishDateTime;
        }

        public Object getDiffTime() {
            return diffTime;
        }

        public void setDiffTime(Object diffTime) {
            this.diffTime = diffTime;
        }

        public List<AlbumsBean> getAlbums() {
            return albums;
        }

        public void setAlbums(List<AlbumsBean> albums) {
            this.albums = albums;
        }

        public static class AlbumsBean {
            /**
             * id : 0
             * GAI_ID : 0
             * GAI_URL : null
             */

            private int id;
            private int GAI_ID;
            private Object GAI_URL;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getGAI_ID() {
                return GAI_ID;
            }

            public void setGAI_ID(int GAI_ID) {
                this.GAI_ID = GAI_ID;
            }

            public Object getGAI_URL() {
                return GAI_URL;
            }

            public void setGAI_URL(Object GAI_URL) {
                this.GAI_URL = GAI_URL;
            }
        }
    }
}
