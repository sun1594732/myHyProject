package com.hyzs.onekeyhelp.family.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */

public class FamilyMemberBean {

    /**
     * familyMemberListModel : [{"member_id":1,"userid":1,"face":"http://app.hyzsnt.com/Files/1/Avatar/2017423/1_6f0c8a49-578e-4947-8ad6-e9d1b8e1a0e1.jpg","uesrname":"嘻嘻哈哈","firstname":"哈哈嘻嘻","RNA":0,"NCCS":0,"identityMark":""}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<FamilyMemberListModelBean> familyMemberListModel;

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

    public List<FamilyMemberListModelBean> getFamilyMemberListModel() {
        return familyMemberListModel;
    }

    public void setFamilyMemberListModel(List<FamilyMemberListModelBean> familyMemberListModel) {
        this.familyMemberListModel = familyMemberListModel;
    }

    public static class FamilyMemberListModelBean {
        /**
         * member_id : 1
         * userid : 1
         * face : http://app.hyzsnt.com/Files/1/Avatar/2017423/1_6f0c8a49-578e-4947-8ad6-e9d1b8e1a0e1.jpg
         * uesrname : 嘻嘻哈哈
         * firstname : 哈哈嘻嘻
         * RNA : 0
         * NCCS : 0
         * identityMark :
         */

        private int member_id;
        private int userid;
        private String face;
        private String uesrname;
        private String firstname;
        private int RNA;
        private int NCCS;
        private String identityMark;
        private String phone;
        private int isCheck;

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int isCheck() {
            return isCheck;
        }

        public void setCheck(int check) {
            isCheck = check;
        }
    }
}
