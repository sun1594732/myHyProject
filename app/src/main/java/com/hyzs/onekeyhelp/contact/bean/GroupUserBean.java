package com.hyzs.onekeyhelp.contact.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/9.
 */

public class GroupUserBean {

    /**
     * identityMemberListByClassify : [{"userid":179,"uesrname":"娃哈哈","firstname":"宁夏","NCCS":1,"RNA":1,"identityMark":"10,2,3,1","position":"","identityName":"服务商"}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<IdentityMemberListByClassifyBean> identityMemberListByClassify;

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

    public List<IdentityMemberListByClassifyBean> getIdentityMemberListByClassify() {
        return identityMemberListByClassify;
    }

    public void setIdentityMemberListByClassify(List<IdentityMemberListByClassifyBean> identityMemberListByClassify) {
        this.identityMemberListByClassify = identityMemberListByClassify;
    }

    public static class IdentityMemberListByClassifyBean {
        /**
         * userid : 179
         * uesrname : 娃哈哈
         * firstname : 宁夏
         * NCCS : 1
         * RNA : 1
         * identityMark : 10,2,3,1
         * position :
         * identityName : 服务商
         */

        private int userid;
        private String uesrname;
        private String firstname;
        private int NCCS;
        private int RNA;
        private String identityMark;
        private String position;
        private String identityName;
        private String face;

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
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

        public String getIdentityMark() {
            return identityMark;
        }

        public void setIdentityMark(String identityMark) {
            this.identityMark = identityMark;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getIdentityName() {
            return identityName;
        }

        public void setIdentityName(String identityName) {
            this.identityName = identityName;
        }
    }
}
