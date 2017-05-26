package com.hyzs.onekeyhelp.family.movable.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class MovableListBean {

    /**
     * eventRegistrationList : [{"ID":2,"ER_SponsorType":1,"ER_Top":1,"ER_Sort":0,"ER_Datetime":"1489925212","ER_Event_Start":"1489982400","ER_Address":"财满街","ER_Title":"测试标题","ER_EventState":"0","ER_RegistrationState":0,"nickName":"律政先锋","trueName":"律政先锋","avatar":"http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg","RegistrationCount":2,"StateCode":"1","ER_ImageUrl":""}]
     * total : 2
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<EventRegistrationListBean> eventRegistrationList;

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

    public List<EventRegistrationListBean> getEventRegistrationList() {
        return eventRegistrationList;
    }

    public void setEventRegistrationList(List<EventRegistrationListBean> eventRegistrationList) {
        this.eventRegistrationList = eventRegistrationList;
    }

    public static class EventRegistrationListBean {
        /**
         * ID : 2
         * ER_SponsorType : 1
         * ER_Top : 1
         * ER_Sort : 0
         * ER_Datetime : 1489925212
         * ER_Event_Start : 1489982400
         * ER_Address : 财满街
         * ER_Title : 测试标题
         * ER_EventState : 0
         * ER_RegistrationState : 0
         * nickName : 律政先锋
         * trueName : 律政先锋
         * avatar : http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg
         * RegistrationCount : 2
         * StateCode : 1
         * ER_ImageUrl :
         */

        private int ID;
        private int ER_SponsorType;
        private int ER_Top;
        private int ER_Sort;
        private String ER_Datetime;
        private String ER_Event_Start;
        private String ER_Address;
        private String ER_Title;
        private String ER_EventState;
        private int ER_RegistrationState;
        private String nickName;
        private String trueName;
        private String avatar;
        private int RegistrationCount;
        private String StateCode;
        private String ER_ImageUrl;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getER_SponsorType() {
            return ER_SponsorType;
        }

        public void setER_SponsorType(int ER_SponsorType) {
            this.ER_SponsorType = ER_SponsorType;
        }

        public int getER_Top() {
            return ER_Top;
        }

        public void setER_Top(int ER_Top) {
            this.ER_Top = ER_Top;
        }

        public int getER_Sort() {
            return ER_Sort;
        }

        public void setER_Sort(int ER_Sort) {
            this.ER_Sort = ER_Sort;
        }

        public String getER_Datetime() {
            return ER_Datetime;
        }

        public void setER_Datetime(String ER_Datetime) {
            this.ER_Datetime = ER_Datetime;
        }

        public String getER_Event_Start() {
            return ER_Event_Start;
        }

        public void setER_Event_Start(String ER_Event_Start) {
            this.ER_Event_Start = ER_Event_Start;
        }

        public String getER_Address() {
            return ER_Address;
        }

        public void setER_Address(String ER_Address) {
            this.ER_Address = ER_Address;
        }

        public String getER_Title() {
            return ER_Title;
        }

        public void setER_Title(String ER_Title) {
            this.ER_Title = ER_Title;
        }

        public String getER_EventState() {
            return ER_EventState;
        }

        public void setER_EventState(String ER_EventState) {
            this.ER_EventState = ER_EventState;
        }

        public int getER_RegistrationState() {
            return ER_RegistrationState;
        }

        public void setER_RegistrationState(int ER_RegistrationState) {
            this.ER_RegistrationState = ER_RegistrationState;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getRegistrationCount() {
            return RegistrationCount;
        }

        public void setRegistrationCount(int RegistrationCount) {
            this.RegistrationCount = RegistrationCount;
        }

        public String getStateCode() {
            return StateCode;
        }

        public void setStateCode(String StateCode) {
            this.StateCode = StateCode;
        }

        public String getER_ImageUrl() {
            return ER_ImageUrl;
        }

        public void setER_ImageUrl(String ER_ImageUrl) {
            this.ER_ImageUrl = ER_ImageUrl;
        }
    }
}
