package com.hyzs.onekeyhelp.family.movable.bean;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/23.
 */

public class MovabledynamicStateBean {


    /**
     * activityDynamicList : [{"imageGroup":[{"ed_id":1,"image_url":"http://192.168.1.186:803/Files/179/img/179_dd9956d4-9676-40b1-b52b-2c98b674ff80.jpg"},{"ed_id":1,"image_url":"http://192.168.1.186:803/Files/179/img/179_8840196c-e3d6-4934-8e1e-ec8ed8a1fff0.jpg"}],"ID":1,"ED_Content":"太好玩了，下回还要去玩耍玩耍~","ED_Datetime":"1490165919","diffTime":"24小时前","ED_Type":3,"userID":13,"nickName":"律政先锋","trueName":"律政先锋","avatar":"http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg","RNA":1,"NCCS":1,"identityMark":"","community":"康惠园小区"},{"imageGroup":[{"ed_id":2,"image_url":"http://192.168.1.186:803/Files/179/img/179_dd9956d4-9676-40b1-b52b-2c98b674ff80.jpg"},{"ed_id":2,"image_url":"http://192.168.1.186:803/Files/179/img/179_8840196c-e3d6-4934-8e1e-ec8ed8a1fff0.jpg"}],"ID":2,"ED_Content":"太好玩了2016，下回还要去玩耍玩耍~","ED_Datetime":"1490166074","diffTime":"23小时前","ED_Type":3,"userID":179,"nickName":"娃哈哈","trueName":"宁夏","avatar":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","RNA":1,"NCCS":1,"identityMark":"10,2,3,1","community":"康惠园小区"}]
     * total : 2
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<ActivityDynamicListBean> activityDynamicList;

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

    public List<ActivityDynamicListBean> getActivityDynamicList() {
        return activityDynamicList;
    }

    public void setActivityDynamicList(List<ActivityDynamicListBean> activityDynamicList) {
        this.activityDynamicList = activityDynamicList;
    }

    public static class ActivityDynamicListBean {
        /**
         * imageGroup : [{"ed_id":1,"image_url":"http://192.168.1.186:803/Files/179/img/179_dd9956d4-9676-40b1-b52b-2c98b674ff80.jpg"},{"ed_id":1,"image_url":"http://192.168.1.186:803/Files/179/img/179_8840196c-e3d6-4934-8e1e-ec8ed8a1fff0.jpg"}]
         * ID : 1
         * ED_Content : 太好玩了，下回还要去玩耍玩耍~
         * ED_Datetime : 1490165919
         * diffTime : 24小时前
         * ED_Type : 3
         * userID : 13
         * nickName : 律政先锋
         * trueName : 律政先锋
         * avatar : http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg
         * RNA : 1
         * NCCS : 1
         * identityMark :
         * community : 康惠园小区
         */

        private int ID;
        private String ED_Content;
        private long ED_Datetime;
        private String diffTime;
        private int ED_Type;
        private int userID;
        private String nickName;
        private String trueName;
        private String avatar;
        private int RNA;
        private int NCCS;
        private String identityMark;
        private String community;
        private List<ImageGroupBean> imageGroup;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getED_Content() {
            return ED_Content;
        }

        public void setED_Content(String ED_Content) {
            this.ED_Content = ED_Content;
        }

        public long getED_Datetime() {
            return ED_Datetime;
        }

        public void setED_Datetime(long ED_Datetime) {
            this.ED_Datetime = ED_Datetime;
        }

        public String getDiffTime() {
            return diffTime;
        }

        public void setDiffTime(String diffTime) {
            this.diffTime = diffTime;
        }

        public int getED_Type() {
            return ED_Type;
        }

        public void setED_Type(int ED_Type) {
            this.ED_Type = ED_Type;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
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

        public List<ImageGroupBean> getImageGroup() {
            return imageGroup;
        }

        public void setImageGroup(List<ImageGroupBean> imageGroup) {
            this.imageGroup = imageGroup;
        }

        public static class ImageGroupBean {
            /**
             * ed_id : 1
             * image_url : http://192.168.1.186:803/Files/179/img/179_dd9956d4-9676-40b1-b52b-2c98b674ff80.jpg
             */

            private int ed_id;
            private String image_url;

            public int getEd_id() {
                return ed_id;
            }

            public void setEd_id(int ed_id) {
                this.ed_id = ed_id;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }
        }
    }
}
