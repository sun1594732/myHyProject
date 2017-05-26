package com.hyzs.onekeyhelp.family.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */

public class FamilyListBean {

    /**
     * total : 1
     * users : [{"FG_ID":2,"FG_UserID":19,"FG_GroupID":"14480272588801","FG_GroupName":"测试群","FG_Desc":"为了测试","FG_PeopleCount":null,"FG_Face":"/Files/19/img/19_d743f234-bf1e-4c9c-9053-47b3e588292f.jpg}","FG_AddTime":"1493202180","FG_RecentContactContent":null,"FG_RecentContactDate":null}]
     * message : 正常
     * code : 10001
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
         * FG_ID : 2
         * FG_UserID : 19
         * FG_GroupID : 14480272588801
         * FG_GroupName : 测试群
         * FG_Desc : 为了测试
         * FG_PeopleCount : null
         * FG_Face : /Files/19/img/19_d743f234-bf1e-4c9c-9053-47b3e588292f.jpg}
         * FG_AddTime : 1493202180
         * FG_RecentContactContent : null
         * FG_RecentContactDate : null
         */

        private int FG_ID;
        private int FG_UserID;
        private String FG_GroupID;
        private String FG_GroupName;
        private String FG_Desc;
        private String FG_PeopleCount;
        private String FG_Face;
        private String FG_AddTime;
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

        public String getFG_PeopleCount() {
            return FG_PeopleCount;
        }

        public void setFG_PeopleCount(String FG_PeopleCount) {
            this.FG_PeopleCount = FG_PeopleCount;
        }

        public String getFG_Face() {
            return FG_Face;
        }

        public void setFG_Face(String FG_Face) {
            this.FG_Face = FG_Face;
        }

        public String getFG_AddTime() {
            return FG_AddTime;
        }

        public void setFG_AddTime(String FG_AddTime) {
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
