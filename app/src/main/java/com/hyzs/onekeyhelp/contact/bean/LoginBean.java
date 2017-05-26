package com.hyzs.onekeyhelp.contact.bean;

/**
 * Created by ZHANGZHEN on 2017-3-18.
 */

public class LoginBean {

    /**
     * data : {"id":4222,"firstname":"","note":"","sex":"","type":1,"img":"","username":"","tel":"18410107932"}
     * code : 0
     * error :
     */

    private DataBean data;
    private int code;
    private String error;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class DataBean {
        /**
         * id : 4222
         * firstname :
         * note :
         * sex :
         * type : 1
         * img :
         * username :
         * tel : 18410107932
         */

        private int id;
        private String firstname;
        private String note;
        private String sex;
        private int type;
        private String img;
        private String username;
        private String tel;

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
