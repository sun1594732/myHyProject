package com.hyzs.onekeyhelp.family.movable.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class MovableMemberBean {


    /**
     * eventMemberList : [{"ID":3,"EM_SponsorType":"0","EM_Sponsor":"0","EM_UserID":13,"EM_Datetime":"1490163464","nickName":"律政先锋","trueName":"律政先锋","RNC":1,"NCC":1,"avatar":"http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg","floorNumber":"康惠园小区"}]
     * total : 1
     * applyCount : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private int applyCount;
    private String message;
    private String code;
    private List<EventMemberListBean> eventMemberList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(int applyCount) {
        this.applyCount = applyCount;
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

    public List<EventMemberListBean> getEventMemberList() {
        return eventMemberList;
    }

    public void setEventMemberList(List<EventMemberListBean> eventMemberList) {
        this.eventMemberList = eventMemberList;
    }

    public static class EventMemberListBean {
        /**
         * ID : 3
         * EM_SponsorType : 0
         * EM_Sponsor : 0
         * EM_UserID : 13
         * EM_Datetime : 1490163464
         * nickName : 律政先锋
         * trueName : 律政先锋
         * RNC : 1
         * NCC : 1
         * avatar : http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg
         * floorNumber : 康惠园小区
         */

        private int ID;
        private String EM_SponsorType;
        private String EM_Sponsor;
        private int EM_UserID;
        private String EM_Datetime;
        private String nickName;
        private String trueName;
        private int RNC;
        private int NCC;
        private String avatar;
        private String floorNumber;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getEM_SponsorType() {
            return EM_SponsorType;
        }

        public void setEM_SponsorType(String EM_SponsorType) {
            this.EM_SponsorType = EM_SponsorType;
        }

        public String getEM_Sponsor() {
            return EM_Sponsor;
        }

        public void setEM_Sponsor(String EM_Sponsor) {
            this.EM_Sponsor = EM_Sponsor;
        }

        public int getEM_UserID() {
            return EM_UserID;
        }

        public void setEM_UserID(int EM_UserID) {
            this.EM_UserID = EM_UserID;
        }

        public String getEM_Datetime() {
            return EM_Datetime;
        }

        public void setEM_Datetime(String EM_Datetime) {
            this.EM_Datetime = EM_Datetime;
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

        public int getRNC() {
            return RNC;
        }

        public void setRNC(int RNC) {
            this.RNC = RNC;
        }

        public int getNCC() {
            return NCC;
        }

        public void setNCC(int NCC) {
            this.NCC = NCC;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getFloorNumber() {
            return floorNumber;
        }

        public void setFloorNumber(String floorNumber) {
            this.floorNumber = floorNumber;
        }
    }
}
