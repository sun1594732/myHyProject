package com.hyzs.onekeyhelp.contact.bean;

import java.util.List;

/**
 * Created by wubin on 2017/3/12.
 */

public class ContactSearchBean {

    /**
     * contactSearchResultList : [{"uid":62,"trueName":"陈鑫宇","personalizedSignature":"","qrCodeUrl":"","province":"湖北省","city":"鄂州市","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"11111111111","sex":"男","isExists":"未添加","RNA":0,"NCCS":0,"identityMark":""},{"uid":69,"trueName":"jp","personalizedSignature":"","qrCodeUrl":"","province":"暂无","city":"暂无","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"11111111111","sex":"暂无","isExists":"未添加","RNA":0,"NCCS":0,"identityMark":""},{"uid":77,"trueName":"test23","personalizedSignature":"","qrCodeUrl":"","province":"暂无","city":"暂无","avatar":"","phone":"11111111111","sex":"暂无","isExists":"未添加","RNA":0,"NCCS":0,"identityMark":""},{"uid":78,"trueName":"test56","personalizedSignature":"","qrCodeUrl":"","province":"暂无","city":"暂无","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"11111111111","sex":"暂无","isExists":"未添加","RNA":0,"NCCS":0,"identityMark":""},{"uid":79,"trueName":"test13","personalizedSignature":"","qrCodeUrl":"","province":"暂无","city":"暂无","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"11111111111","sex":"暂无","isExists":"未添加","RNA":0,"NCCS":0,"identityMark":""},{"uid":85,"trueName":"","personalizedSignature":"","qrCodeUrl":"","province":"暂无","city":"暂无","avatar":"","phone":"11111111111","sex":"暂无","isExists":"未添加","RNA":0,"NCCS":0,"identityMark":""},{"uid":86,"trueName":"吕百万","personalizedSignature":"","qrCodeUrl":"","province":"暂无","city":"暂无","avatar":"http://img.zglzxf.com/default/2016/0816/5288527738576801852.jpg","phone":"18600781793","sex":"暂无","isExists":"未添加","RNA":0,"NCCS":0,"identityMark":""},{"uid":94,"trueName":"au","personalizedSignature":"","qrCodeUrl":"","province":"暂无","city":"暂无","avatar":"http://img.zglzxf.com/default/2016/0418/3.jpg","phone":"11111111111","sex":"男","isExists":"未添加","RNA":0,"NCCS":0,"identityMark":""},{"uid":95,"trueName":"","personalizedSignature":"","qrCodeUrl":"","province":"暂无","city":"暂无","avatar":"http://img.zglzxf.com/default/2016/0418/4.jpg","phone":"11111111111","sex":"暂无","isExists":"未添加","RNA":0,"NCCS":0,"identityMark":""},{"uid":124,"trueName":"短短","personalizedSignature":"","qrCodeUrl":"","province":"暂无","city":"暂无","avatar":"http://img.zglzxf.com/default/2016/0826/4807325294785326574.png","phone":"15801250595","sex":"暂无","isExists":"未添加","RNA":0,"NCCS":0,"identityMark":""}]
     * total : 463
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<ContactSearchResultListBean> contactSearchResultList;

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

    public List<ContactSearchResultListBean> getContactSearchResultList() {
        return contactSearchResultList;
    }

    public void setContactSearchResultList(List<ContactSearchResultListBean> contactSearchResultList) {
        this.contactSearchResultList = contactSearchResultList;
    }

    public static class ContactSearchResultListBean {
        /**
         * uid : 62
         * trueName : 陈鑫宇
         * personalizedSignature :
         * qrCodeUrl :
         * province : 湖北省
         * city : 鄂州市
         * avatar : http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg
         * phone : 11111111111
         * sex : 男
         * isExists : 未添加
         * RNA : 0
         * NCCS : 0
         * identityMark :
         */

        private int uid;
        private String trueName;
        private String personalizedSignature;
        private String qrCodeUrl;
        private String province;
        private String nickName;
        private String city;
        private String avatar;
        private String phone;
        private String sex;
        private String isExists;
        private String community;
        private int RNA;
        private int NCCS;
        private String identityMark;

        @Override
        public String toString() {
            return "ContactSearchResultListBean{" +
                    "uid=" + uid +
                    ", trueName='" + trueName + '\'' +
                    ", personalizedSignature='" + personalizedSignature + '\'' +
                    ", qrCodeUrl='" + qrCodeUrl + '\'' +
                    ", province='" + province + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", city='" + city + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", phone='" + phone + '\'' +
                    ", sex='" + sex + '\'' +
                    ", isExists='" + isExists + '\'' +
                    ", community='" + community + '\'' +
                    ", RNA=" + RNA +
                    ", NCCS=" + NCCS +
                    ", identityMark='" + identityMark + '\'' +
                    '}';
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public String getPersonalizedSignature() {
            return personalizedSignature;
        }

        public void setPersonalizedSignature(String personalizedSignature) {
            this.personalizedSignature = personalizedSignature;
        }

        public String getQrCodeUrl() {
            return qrCodeUrl;
        }

        public void setQrCodeUrl(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIsExists() {
            return isExists;
        }

        public void setIsExists(String isExists) {
            this.isExists = isExists;
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
}
