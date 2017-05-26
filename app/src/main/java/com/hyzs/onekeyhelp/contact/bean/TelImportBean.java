package com.hyzs.onekeyhelp.contact.bean;

import java.util.List;

public class TelImportBean {

    /**
     * contactPhoneImportCheck : [{"phone":"15022341205","isExists":2,"userId":749}]
     * message : 正常
     * code : 10000
     */

    private String message;
    private String code;
    private List<ContactPhoneImportCheckBean> contactPhoneImportCheck;

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

    public List<ContactPhoneImportCheckBean> getContactPhoneImportCheck() {
        return contactPhoneImportCheck;
    }

    public void setContactPhoneImportCheck(List<ContactPhoneImportCheckBean> contactPhoneImportCheck) {
        this.contactPhoneImportCheck = contactPhoneImportCheck;
    }

    public static class ContactPhoneImportCheckBean {
        /**
         * phone : 15022341205
         * isExists : 2
         * userId : 749
         */

        private String phone;
        private int isExists;
        private int userId;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getIsExists() {
            return isExists;
        }

        public void setIsExists(int isExists) {
            this.isExists = isExists;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
