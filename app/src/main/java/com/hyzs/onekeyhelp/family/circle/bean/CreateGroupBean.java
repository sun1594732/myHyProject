package com.hyzs.onekeyhelp.family.circle.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class CreateGroupBean {

    /**
     * total : 0
     * users : [{"FG_ID":0,"FG_UserID":11,"FG_GroupID":"14465754005505","FG_GroupName":"群组名称","FG_Desc":"群组描述","FG_PeopleCount":0,"FG_Face":"Files/abc.jpg","FG_AddTime":null,"FG_RecentContactContent":null,"FG_RecentContactDate":null}]
     * message : 正常
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
         * FG_ID : 0
         * FG_UserID : 11
         * FG_GroupID : 14465754005505
         * FG_GroupName : 群组名称
         * FG_Desc : 群组描述
         * FG_PeopleCount : 0
         * FG_Face : Files/abc.jpg
         * FG_AddTime : null
         * FG_RecentContactContent : null
         * FG_RecentContactDate : null
         */

        private int FG_ID;
        private int FG_UserID;
        private String FG_GroupID;
        private String FG_GroupName;
        private String FG_Desc;
        private int FG_PeopleCount;
        private String FG_Face;
        private Object FG_AddTime;
        private Object FG_RecentContactContent;
        private Object FG_RecentContactDate;

        public int getFG_ID() {
            return FG_ID;
        }

        public void setFG_ID(int FG_ID) {
            this.FG_ID = FG_ID;
        }

        public int getFG_UserID() {
            return FG_UserID;
        }

        public void setFG_UserID(int FG_UserID) {
            this.FG_UserID = FG_UserID;
        }

        public String getFG_GroupID() {
            return FG_GroupID;
        }

        public void setFG_GroupID(String FG_GroupID) {
            this.FG_GroupID = FG_GroupID;
        }

        public String getFG_GroupName() {
            return FG_GroupName;
        }

        public void setFG_GroupName(String FG_GroupName) {
            this.FG_GroupName = FG_GroupName;
        }

        public String getFG_Desc() {
            return FG_Desc;
        }

        public void setFG_Desc(String FG_Desc) {
            this.FG_Desc = FG_Desc;
        }

        public int getFG_PeopleCount() {
            return FG_PeopleCount;
        }

        public void setFG_PeopleCount(int FG_PeopleCount) {
            this.FG_PeopleCount = FG_PeopleCount;
        }

        public String getFG_Face() {
            return FG_Face;
        }

        public void setFG_Face(String FG_Face) {
            this.FG_Face = FG_Face;
        }

        public Object getFG_AddTime() {
            return FG_AddTime;
        }

        public void setFG_AddTime(Object FG_AddTime) {
            this.FG_AddTime = FG_AddTime;
        }

        public Object getFG_RecentContactContent() {
            return FG_RecentContactContent;
        }

        public void setFG_RecentContactContent(Object FG_RecentContactContent) {
            this.FG_RecentContactContent = FG_RecentContactContent;
        }

        public Object getFG_RecentContactDate() {
            return FG_RecentContactDate;
        }

        public void setFG_RecentContactDate(Object FG_RecentContactDate) {
            this.FG_RecentContactDate = FG_RecentContactDate;
        }
    }
}
