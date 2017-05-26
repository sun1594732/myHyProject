package com.hyzs.onekeyhelp.contact.bean;

import java.util.List;

/**
 * Created by wubin on 2017/3/11.
 */

public class ContactListEntity {


    /**
     * contactList : [{"identityMark":"1,2,3,4,5,6,7,8","uid":179,"targetUserId":546,"relationGroup":"家人","trueName":"张娟","userNick":"娟儿","remarkName":"","urgentFlag":"否","avatar":"http://localhost:49902/../Static/images/ico.png","phone":"18307210094","sortFirstChar":"J ","NCCS":0,"RNA":0},{"identityMark":"","uid":179,"targetUserId":747,"relationGroup":"朋友","trueName":"Uyyyu","userNick":"Uyyyu","remarkName":"","urgentFlag":"否","avatar":"","phone":"252828","sortFirstChar":"U ","NCCS":0,"RNA":0}]
     * total : 2
     * identityMark : 10,2,3,1
     * uid : 179
     * RNA : 0
     * NCCS : 0
     * trueName : 宁夏
     * userNick : 娃哈哈
     * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
     * phone : 15910745659
     * message : 正常
     * code : 10000
     */

    private int total;
    private String identityMark;
    private int uid;
    private int RNA;
    private int NCCS;
    private String trueName;
    private String userNick;
    private String avatar;
    private String phone;
    private String message;
    private String code;
    private String community;
    private List<ContactListBean> contactList;

    public int getTotal() {
        return total;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getIdentityMark() {
        return identityMark;
    }

    public void setIdentityMark(String identityMark) {
        this.identityMark = identityMark;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
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

    public List<ContactListBean> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactListBean> contactList) {
        this.contactList = contactList;
    }

    public static class ContactListBean {
        /**
         * identityMark : 1,2,3,4,5,6,7,8
         * uid : 179
         * targetUserId : 546
         * relationGroup : 家人
         * trueName : 张娟
         * userNick : 娟儿
         * remarkName :
         * urgentFlag : 否
         * avatar : http://localhost:49902/../Static/images/ico.png
         * phone : 18307210094
         * sortFirstChar : J
         * NCCS : 0
         * RNA : 0
         */

        private String identityMark;
        private int uid;
        private int targetUserId;
        private String relationGroup;
        private String trueName;
        private String userNick;
        private String remarkName;
        private String urgentFlag;
        private String avatar;
        private String phone;
        private String sortFirstChar;
        private String community;
        private int NCCS;
        private int RNA;

        @Override
        public String toString() {
            return "ContactListBean{" +
                    "identityMark='" + identityMark + '\'' +
                    ", uid=" + uid +
                    ", targetUserId=" + targetUserId +
                    ", relationGroup='" + relationGroup + '\'' +
                    ", trueName='" + trueName + '\'' +
                    ", userNick='" + userNick + '\'' +
                    ", remarkName='" + remarkName + '\'' +
                    ", urgentFlag='" + urgentFlag + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", phone='" + phone + '\'' +
                    ", sortFirstChar='" + sortFirstChar + '\'' +
                    ", community='" + community + '\'' +
                    ", NCCS=" + NCCS +
                    ", RNA=" + RNA +
                    '}';
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }
        public String getIdentityMark() {
            return identityMark;
        }

        public void setIdentityMark(String identityMark) {
            this.identityMark = identityMark;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getTargetUserId() {
            return targetUserId;
        }

        public void setTargetUserId(int targetUserId) {
            this.targetUserId = targetUserId;
        }

        public String getRelationGroup() {
            return relationGroup;
        }

        public void setRelationGroup(String relationGroup) {
            this.relationGroup = relationGroup;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public String getUserNick() {
            return userNick;
        }

        public void setUserNick(String userNick) {
            this.userNick = userNick;
        }

        public String getRemarkName() {
            return remarkName;
        }

        public void setRemarkName(String remarkName) {
            this.remarkName = remarkName;
        }

        public String getUrgentFlag() {
            return urgentFlag;
        }

        public void setUrgentFlag(String urgentFlag) {
            this.urgentFlag = urgentFlag;
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

        public String getSortFirstChar() {
            return sortFirstChar;
        }

        public void setSortFirstChar(String sortFirstChar) {
            this.sortFirstChar = sortFirstChar;
        }

        public int getNCCS() {
            return NCCS;
        }

        public void setNCCS(int NCCS) {
            this.NCCS = NCCS;
        }

        public int getRNA() {
            return RNA;
        }

        public void setRNA(int RNA) {
            this.RNA = RNA;
        }
    }
}
