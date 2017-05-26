package com.hyzs.onekeyhelp.family.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */

public class FamilyCircleBean {

    /**
     * familyDynamicList : [{"images":[{"Url":"http://app.hyzsnt.com/Files/1/img/1_d1f69e9a-3112-404b-bca7-37980e9a5a0a.jpg"}],"userid":1,"face":"http://app.hyzsnt.com/Files/1/Avatar/2017423/1_6f0c8a49-578e-4947-8ad6-e9d1b8e1a0e1.jpg","uesrname":"嘻嘻哈哈","firstname":"哈哈嘻嘻","RNA":0,"NCCS":0,"identityMark":"","record_id":1,"r_type":2,"content":"观看升国旗-天安门-故宫博物院-王府井小吃街","publishTime":"1493006972","praise":0,"commentCount":0,"diffTime":"3天前"},{"images":[{"Url":"http://app.hyzsnt.com/Files/1/img/1_49b2201b-f2e4-4024-b3d0-831bb9934fd5.jpg"},{"Url":"http://app.hyzsnt.com/Files/1/img/1_41a090e3-0b20-4d88-820f-950ab57331aa.jpg"},{"Url":"http://app.hyzsnt.com/Files/1/img/1_b8cd7be9-fbed-4a20-9498-e7093d7ec811.jpg"},{"Url":"http://app.hyzsnt.com/Files/1/img/1_47c19c68-0dc0-4d55-9a1c-f67c5e29c742.jpg"},{"Url":"http://app.hyzsnt.com/Files/1/img/1_79bf1bc1-ca29-4b48-945f-260035748fc6.jpg"}],"userid":1,"face":"http://app.hyzsnt.com/Files/1/Avatar/2017423/1_6f0c8a49-578e-4947-8ad6-e9d1b8e1a0e1.jpg","uesrname":"嘻嘻哈哈","firstname":"哈哈嘻嘻","RNA":0,"NCCS":0,"identityMark":"","record_id":8,"r_type":0,"content":"一组图看清金蝉的生长状态\n知了是这样的，第一个图片详细的一塌糊涂","publishTime":"1493005952","praise":0,"commentCount":0,"diffTime":"3天前"}]
     * total : 2
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<FamilyDynamicListBean> familyDynamicList;

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

    public List<FamilyDynamicListBean> getFamilyDynamicList() {
        return familyDynamicList;
    }

    public void setFamilyDynamicList(List<FamilyDynamicListBean> familyDynamicList) {
        this.familyDynamicList = familyDynamicList;
    }

    public static class FamilyDynamicListBean {
        /**
         * images : [{"Url":"http://app.hyzsnt.com/Files/1/img/1_d1f69e9a-3112-404b-bca7-37980e9a5a0a.jpg"}]
         * userid : 1
         * face : http://app.hyzsnt.com/Files/1/Avatar/2017423/1_6f0c8a49-578e-4947-8ad6-e9d1b8e1a0e1.jpg
         * uesrname : 嘻嘻哈哈
         * firstname : 哈哈嘻嘻
         * RNA : 0
         * NCCS : 0
         * identityMark :
         * record_id : 1
         * r_type : 2
         * content : 观看升国旗-天安门-故宫博物院-王府井小吃街
         * publishTime : 1493006972
         * praise : 0
         * commentCount : 0
         * diffTime : 3天前
         */

        private int userid;
        private String face;
        private String uesrname;
        private String firstname;
        private int RNA;
        private int NCCS;
        private String identityMark;
        private int record_id;
        private int r_type;
        private String content;
        private String publishTime;
        private int praise;
        private int commentCount;
        private String diffTime;
        private List<ImagesBean> images;
        private String r_c_typeName;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
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

        public int getRecord_id() {
            return record_id;
        }

        public void setRecord_id(int record_id) {
            this.record_id = record_id;
        }

        public int getR_type() {
            return r_type;
        }

        public void setR_type(int r_type) {
            this.r_type = r_type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getDiffTime() {
            return diffTime;
        }

        public void setDiffTime(String diffTime) {
            this.diffTime = diffTime;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public String getR_c_typeName() {
            return r_c_typeName;
        }

        public void setR_c_typeName(String r_c_typeName) {
            this.r_c_typeName = r_c_typeName;
        }

        public static class ImagesBean {
            /**
             * Url : http://app.hyzsnt.com/Files/1/img/1_d1f69e9a-3112-404b-bca7-37980e9a5a0a.jpg
             */

            private String Url;

            public String getUrl() {
                return Url;
            }

            public void setUrl(String Url) {
                this.Url = Url;
            }
        }
    }
}
