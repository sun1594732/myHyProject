package com.hyzs.onekeyhelp.contact.bean;

import java.util.List;

/**
 * Created by wubin on 2017/4/10.
 */

public class LoginStatusBean {

    /**
     * total : 0
     * users : [{"id":749,"firstname":"张想","note":"","sex":"","type":0,"img":"","username":"张想","tel":"15022341205"}]
     * message : 登陆成功！
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<UsersBean> users;

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

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        /**
         * id : 749
         * firstname : 张想
         * note :
         * sex :
         * type : 0
         * img :
         * username : 张想
         * tel : 15022341205
         */

        private int id;
        private String firstname;
        private String note;
        private String sex;
        private int type;
        private String img;
        private String username;
        private String tel;
        private String communityName ;
        private int communityId;

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public int getCommunityId() {
            return communityId;
        }

        public void setCommunityId(int communityId) {
            this.communityId = communityId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
}
