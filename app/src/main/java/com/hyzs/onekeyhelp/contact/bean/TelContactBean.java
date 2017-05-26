package com.hyzs.onekeyhelp.contact.bean;

/**
 * Created by wubin on 2017/4/12.
 */

public class TelContactBean {
    private String contactName;
    private String phoneNumber;
    private String isExist;
    private String id;

    public String getIsExist() {
        return isExist;
    }

    public void setIsExist(String isExist) {
        this.isExist = isExist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
