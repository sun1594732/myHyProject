package com.hyzs.onekeyhelp.family.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/28.
 */

public class FamilyAddMemberBean {

    /**
     * familySearchAddList : [{"userid":16,"firstname":"肖扬","uesrname":"xy","face":"http://app.hyzsnt.com/Files/16/Avatar/2017427/16_ff528fab-8f36-418e-89c6-9965ce22d7a3.jpg","phone":"18101309361","RNA":0,"NCCS":0,"identityMark":""}]
     * total : 3
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<FamilySearchAddListBean> familySearchAddList;

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

    public List<FamilySearchAddListBean> getFamilySearchAddList() {
        return familySearchAddList;
    }

    public void setFamilySearchAddList(List<FamilySearchAddListBean> familySearchAddList) {
        this.familySearchAddList = familySearchAddList;
    }

    public static class FamilySearchAddListBean {
        /**
         * userid : 16
         * firstname : 肖扬
         * uesrname : xy
         * face : http://app.hyzsnt.com/Files/16/Avatar/2017427/16_ff528fab-8f36-418e-89c6-9965ce22d7a3.jpg
         * phone : 18101309361
         * RNA : 0
         * NCCS : 0
         * identityMark :
         */

        private int userid;
        private String firstname;
        private String uesrname;
        private String face;
        private String phone;
        private int RNA;
        private int NCCS;
        private String identityMark;
        private int isCheck;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getUesrname() {
            return uesrname;
        }

        public void setUesrname(String uesrname) {
            this.uesrname = uesrname;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
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

        public String getIdentityMark() {
            return identityMark;
        }

        public void setIdentityMark(String identityMark) {
            this.identityMark = identityMark;
        }

        public int isCheck() {
            return isCheck;
        }

        public void setCheck(int check) {
            isCheck = check;
        }
    }
}
