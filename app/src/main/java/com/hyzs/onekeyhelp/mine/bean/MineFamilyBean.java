package com.hyzs.onekeyhelp.mine.bean;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/28.
 */

public class MineFamilyBean {

    /**
     * personalCenterFamilyMember : [{"uid":754,"userId":179,"trueName":"123","nickName":"123","avatar":"","sex":"暂无","province":"暂无","city":"暂无","personalizedSignature":"","phone":"123t5788","community":"康惠园小区","RNA":1,"NCCS":1,"qrCodeUrl":"","ofTableType":"家人  ","identityMark":"","isExists":"已添加"}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<PersonalCenterFamilyMemberBean> personalCenterFamilyMember;

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

    public List<PersonalCenterFamilyMemberBean> getPersonalCenterFamilyMember() {
        return personalCenterFamilyMember;
    }

    public void setPersonalCenterFamilyMember(List<PersonalCenterFamilyMemberBean> personalCenterFamilyMember) {
        this.personalCenterFamilyMember = personalCenterFamilyMember;
    }

    public static class PersonalCenterFamilyMemberBean {
        /**
         * uid : 754
         * userId : 179
         * trueName : 123
         * nickName : 123
         * avatar :
         * sex : 暂无
         * province : 暂无
         * city : 暂无
         * personalizedSignature :
         * phone : 123t5788
         * community : 康惠园小区
         * RNA : 1
         * NCCS : 1
         * qrCodeUrl :
         * ofTableType : 家人
         * identityMark :
         * isExists : 已添加
         */

        private int uid;
        private int userId;
        private String trueName;
        private String nickName;
        private String avatar;
        private String sex;
        private String province;
        private String city;
        private String personalizedSignature;
        private String phone;
        private String community;
        private int RNA;
        private int NCCS;
        private String qrCodeUrl;
        private String ofTableType;
        private String identityMark;
        private String isExists;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
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

        public String getPersonalizedSignature() {
            return personalizedSignature;
        }

        public void setPersonalizedSignature(String personalizedSignature) {
            this.personalizedSignature = personalizedSignature;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
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

        public String getQrCodeUrl() {
            return qrCodeUrl;
        }

        public void setQrCodeUrl(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
        }

        public String getOfTableType() {
            return ofTableType;
        }

        public void setOfTableType(String ofTableType) {
            this.ofTableType = ofTableType;
        }

        public String getIdentityMark() {
            return identityMark;
        }

        public void setIdentityMark(String identityMark) {
            this.identityMark = identityMark;
        }

        public String getIsExists() {
            return isExists;
        }

        public void setIsExists(String isExists) {
            this.isExists = isExists;
        }
    }
}
