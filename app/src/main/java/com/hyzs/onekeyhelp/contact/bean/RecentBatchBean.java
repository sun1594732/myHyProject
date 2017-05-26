package com.hyzs.onekeyhelp.contact.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class RecentBatchBean {
    /**
     * userContactListBatchQueryList : [{"identityMark":"1,2,10,8","community":"康惠园小区","uid":179,"targetUserId":549,"relationGroup":"朋友","trueName":"cb","userNick":"东方少男","remarkName":"","urgentFlag":"是","avatar":"http://img.zglzxf.com/default/2016/1116/5004600618800457910.png","phone":"13161117899","RNA":1,"NCCS":1},{"identityMark":"","community":"中华田园小区","uid":179,"targetUserId":748,"relationGroup":"","trueName":"慕发","userNick":"慕发","remarkName":"","urgentFlag":"否","avatar":"","phone":"18256766725","RNA":1,"NCCS":1},{"identityMark":"","community":"康惠园小区","uid":179,"targetUserId":754,"relationGroup":"家人","trueName":"123","userNick":"123","remarkName":"","urgentFlag":"否","avatar":"","phone":"123t5788","RNA":1,"NCCS":1},{"identityMark":"","community":"","uid":179,"targetUserId":769,"relationGroup":"","trueName":"jj","userNick":"jj","remarkName":"","urgentFlag":"否","avatar":"","phone":"696","RNA":0,"NCCS":0}]
     * total : 2
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<UserContactListBatchQueryListBean> userContactListBatchQueryList;

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

    public List<UserContactListBatchQueryListBean> getUserContactListBatchQueryList() {
        return userContactListBatchQueryList;
    }

    public void setUserContactListBatchQueryList(List<UserContactListBatchQueryListBean> userContactListBatchQueryList) {
        this.userContactListBatchQueryList = userContactListBatchQueryList;
    }

    public static class UserContactListBatchQueryListBean {
        /**
         * identityMark : 1,2,10,8
         * community : 康惠园小区
         * uid : 179
         * targetUserId : 549
         * relationGroup : 朋友
         * trueName : cb
         * userNick : 东方少男
         * remarkName :
         * urgentFlag : 是
         * avatar : http://img.zglzxf.com/default/2016/1116/5004600618800457910.png
         * phone : 13161117899
         * RNA : 1
         * NCCS : 1
         */

        private String identityMark;
        private String community;
        private int uid;
        private int targetUserId;
        private String relationGroup;
        private String trueName;
        private String userNick;
        private String remarkName;
        private String urgentFlag;
        private String avatar;
        private String phone;
        private String date;
        private String lastMessage;
        private int RNA;
        private int NCCS;

        public String getLastMessage() {
            return lastMessage;
        }

        public void setLastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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
    }
}
