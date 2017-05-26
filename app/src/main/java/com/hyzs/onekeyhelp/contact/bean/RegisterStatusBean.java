package com.hyzs.onekeyhelp.contact.bean;

/**
 * Created by wubin on 2017/3/19.
 */

public class RegisterStatusBean {

    /**
     * register : {"id":"用户编号","firstname":"真实姓名","note":"说明","sex":"性别","type":"类型","img":"头像","username":"昵称","tel":"手机号码","communityName":"社区名称","communityId":"社区id"}
     * code : 10000
     * message : 注册成功
     */

    private RegisterBean register;
    private String code;
    private String message;

    public RegisterBean getRegister() {
        return register;
    }

    public void setRegister(RegisterBean register) {
        this.register = register;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class RegisterBean {
        /**
         * id : 用户编号
         * firstname : 真实姓名
         * note : 说明
         * sex : 性别
         * type : 类型
         * img : 头像
         * username : 昵称
         * tel : 手机号码
         * communityName : 社区名称
         * communityId : 社区id
         */

        private String id;
        private String firstname;
        private String note;
        private String sex;
        private String type;
        private String img;
        private String username;
        private String tel;
        private String communityName;
        private String communityId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
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

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }
    }
}
