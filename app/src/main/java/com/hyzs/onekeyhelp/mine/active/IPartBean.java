package com.hyzs.onekeyhelp.mine.active;

import java.util.List;

/**
 * Created by wubin on 2017/5/18.
 */

public class IPartBean {

    /**
     * personalCenterIParticipation : [{"images":[{"Url":"http://app.hyzsnt.com/Files/10/img/10_ea01fd63-c716-4333-936e-eb508934c489.jpg"}],"r_c_type":4,"r_c_typeName":"周边组团游","userid":10,"r_id":2,"r_type":0,"content":"集合，坐火车去泰安爬山","commouity":"康家园小区","face":"头像","uesrname":"张三","Sex":"男","publishTime":"1493011086","diffTime":"24天前"}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<PersonalCenterIParticipationBean> personalCenterIParticipation;

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

    public List<PersonalCenterIParticipationBean> getPersonalCenterIParticipation() {
        return personalCenterIParticipation;
    }

    public void setPersonalCenterIParticipation(List<PersonalCenterIParticipationBean> personalCenterIParticipation) {
        this.personalCenterIParticipation = personalCenterIParticipation;
    }

    public static class PersonalCenterIParticipationBean {
        /**
         * images : [{"Url":"http://app.hyzsnt.com/Files/10/img/10_ea01fd63-c716-4333-936e-eb508934c489.jpg"}]
         * r_c_type : 4
         * r_c_typeName : 周边组团游
         * userid : 10
         * r_id : 2
         * r_type : 0
         * content : 集合，坐火车去泰安爬山
         * commouity : 康家园小区
         * face : 头像
         * uesrname : 张三
         * Sex : 男
         * publishTime : 1493011086
         * diffTime : 24天前
         */

        private int r_c_type;
        private String r_c_typeName;
        private int userid;
        private int r_id;
        private int r_type;
        private String content;
        private String commouity;
        private String face;
        private String uesrname;
        private String Sex;
        private String publishTime;
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

        public int getR_id() {
            return r_id;
        }

        public void setR_id(int r_id) {
            this.r_id = r_id;
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

        public String getCommouity() {
            return commouity;
        }

        public void setCommouity(String commouity) {
            this.commouity = commouity;
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

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
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
             * Url : http://app.hyzsnt.com/Files/10/img/10_ea01fd63-c716-4333-936e-eb508934c489.jpg
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
