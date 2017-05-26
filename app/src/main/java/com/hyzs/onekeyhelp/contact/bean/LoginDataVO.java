package com.hyzs.onekeyhelp.contact.bean;


public class LoginDataVO {

    private int id;
    private String firstname;
    private String note;
    private String sex;
    private String type;
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
    @Override
    public String toString() {
        return "LoginVO [id=" + id + ", firstname=" + firstname + ", note=" + note + ", sex=" + sex + ", type=" + type
                + ", img=" + img + ", username=" + username + ", tel=" + tel + "]";
    }
}
