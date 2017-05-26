package com.hyzs.onekeyhelp.home.forum.bean;

import java.util.List;

/**
 * Created by wubin on 2017/3/22.
 */

public class ForumTypeBean {

    /**
     * forumTypes : [{"ID":1,"forumName":"养生专题"}]
     * total : 4
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<ForumTypesBean> forumTypes;

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

    public List<ForumTypesBean> getForumTypes() {
        return forumTypes;
    }

    public void setForumTypes(List<ForumTypesBean> forumTypes) {
        this.forumTypes = forumTypes;
    }

    public static class ForumTypesBean {
        /**
         * ID : 1
         * forumName : 养生专题
         */

        private int ID;
        private String forumName;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getForumName() {
            return forumName;
        }

        public void setForumName(String forumName) {
            this.forumName = forumName;
        }
    }
}
