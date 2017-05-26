package com.hyzs.onekeyhelp.home.bean;

import java.util.List;

/**
 * Created by wubin on 2017/5/3.
 */

public class HomeCommunityDynamicListBean {

    /**
     * familyDynamicList : [{"images":[{"Url":"http://app.hyzsnt.com/Files/5/img/5_bd1798e8-e9bf-4325-af89-141fa43acf0a.jpg"},{"Url":"http://app.hyzsnt.com/Files/5/img/5_5951280d-25a1-494a-bd44-81ae472b1863.jpg"},{"Url":"http://app.hyzsnt.com/Files/5/img/5_e63e6f97-804e-43a3-984f-0882b3ece3a4.jpg"},{"Url":"http://app.hyzsnt.com/Files/5/img/5_e1313488-8c7b-4a51-a24f-d09112e98127.jpg"}],"r_c_type":5,"r_c_typeName":"宠物圈","userid":5,"face":"http://app.hyzsnt.com/Files/5/Avatar/2017423/5_bed7d99e-a1e5-4fb1-aa1d-9bb10948b885.jpg","uesrname":"叶子","firstname":"王霞","RNA":0,"NCCS":0,"identityMark":"","record_id":158,"r_type":0,"content":"谁知道这都是什么动物？？？","publishTime":"1493733378","praise":0,"commentCount":0,"diffTime":"17小时前"}]
     * total : 149
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
         * images : [{"Url":"http://app.hyzsnt.com/Files/5/img/5_bd1798e8-e9bf-4325-af89-141fa43acf0a.jpg"},{"Url":"http://app.hyzsnt.com/Files/5/img/5_5951280d-25a1-494a-bd44-81ae472b1863.jpg"},{"Url":"http://app.hyzsnt.com/Files/5/img/5_e63e6f97-804e-43a3-984f-0882b3ece3a4.jpg"},{"Url":"http://app.hyzsnt.com/Files/5/img/5_e1313488-8c7b-4a51-a24f-d09112e98127.jpg"}]
         * r_c_type : 5
         * r_c_typeName : 宠物圈
         * userid : 5
         * face : http://app.hyzsnt.com/Files/5/Avatar/2017423/5_bed7d99e-a1e5-4fb1-aa1d-9bb10948b885.jpg
         * uesrname : 叶子
         * firstname : 王霞
         * RNA : 0
         * NCCS : 0
         * identityMark :
         * record_id : 158
         * r_type : 0
         * content : 谁知道这都是什么动物？？？
         * publishTime : 1493733378
         * praise : 0
         * commentCount : 0
         * diffTime : 17小时前
         */

        private int r_c_type;
        private String r_c_typeName;
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

        public int getR_c_type() {
            return r_c_type;
        }

        public void setR_c_type(int r_c_type) {
            this.r_c_type = r_c_type;
        }

        public String getR_c_typeName() {
            return r_c_typeName;
        }

        public void setR_c_typeName(String r_c_typeName) {
            this.r_c_typeName = r_c_typeName;
        }

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

        public static class ImagesBean {
            /**
             * Url : http://app.hyzsnt.com/Files/5/img/5_bd1798e8-e9bf-4325-af89-141fa43acf0a.jpg
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
