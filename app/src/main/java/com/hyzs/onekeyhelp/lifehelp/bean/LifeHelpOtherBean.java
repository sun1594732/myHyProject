package com.hyzs.onekeyhelp.lifehelp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/19.
 */

public class LifeHelpOtherBean implements Parcelable {


    /**
     * myHelpLists : [{"seekHelpUserId":179,"helpPeopleUserId":179,"seek_State":1,"seek_Time":"1489636305","seek_Reminder":"向您发送了一个求助信息","avatar":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","nickName":"娃哈哈","trueName":"宁夏","RNA":1,"NCCS":1,"identityMark":"10,2,3,1","community":"康惠园小区","seek_AffixImgList":"{http://192.168.1.186:803/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a655.jpg},{http://192.168.1.186:803/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a656.jpg}","seek_Voice":""}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<MyHelpListsBean> myHelpLists;

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

    public List<MyHelpListsBean> getMyHelpLists() {
        return myHelpLists;
    }

    public void setMyHelpLists(List<MyHelpListsBean> myHelpLists) {
        this.myHelpLists = myHelpLists;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static class MyHelpListsBean {
        /**
         * seekHelpUserId : 179
         * helpPeopleUserId : 179
         * seek_State : 1
         * seek_Time : 1489636305
         * seek_Reminder : 向您发送了一个求助信息
         * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * nickName : 娃哈哈
         * trueName : 宁夏
         * RNA : 1
         * NCCS : 1
         * identityMark : 10,2,3,1
         * community : 康惠园小区
         * seek_AffixImgList : {http://192.168.1.186:803/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a655.jpg},{http://192.168.1.186:803/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a656.jpg}
         * seek_Voice :
         */
        private int Help_See;
        private long Help_See_DateTime;
        private int seekHelpUserId;
        private int seek_id ;
        private int ID;
        private int helpPeopleUserId;
        private int seek_State;
        private long seek_Time;
        private String seek_Reminder;
        private String avatar;
        private String nickName;
        private String trueName;
        private String helpID;
        private int RNA;
        private int NCCS;
        private String identityMark;
        private String community;
        private String seek_AffixImgList;
        private String seek_Voice;

        public int getHelp_See() {
            return Help_See;
        }

        public void setHelp_See(int help_See) {
            Help_See = help_See;
        }

        public long getHelp_See_DateTime() {
            return Help_See_DateTime;
        }

        public void setHelp_See_DateTime(long help_See_DateTime) {
            Help_See_DateTime = help_See_DateTime;
        }

        public String getHelpID() {
            return helpID;
        }

        public void setHelpID(String helpID) {
            this.helpID = helpID;
        }

        public int getSeek_id() {
            return seek_id;
        }

        public void setSeek_id(int seek_id) {
            this.seek_id = seek_id;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getSeekHelpUserId() {
            return seekHelpUserId;
        }

        public void setSeekHelpUserId(int seekHelpUserId) {
            this.seekHelpUserId = seekHelpUserId;
        }

        public int getHelpPeopleUserId() {
            return helpPeopleUserId;
        }

        public void setHelpPeopleUserId(int helpPeopleUserId) {
            this.helpPeopleUserId = helpPeopleUserId;
        }

        public int getSeek_State() {
            return seek_State;
        }

        public void setSeek_State(int seek_State) {
            this.seek_State = seek_State;
        }

        public long getSeek_Time() {
            return seek_Time;
        }

        public void setSeek_Time(long seek_Time) {
            this.seek_Time = seek_Time;
        }

        public String getSeek_Reminder() {
            return seek_Reminder;
        }

        public void setSeek_Reminder(String seek_Reminder) {
            this.seek_Reminder = seek_Reminder;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public int getRNA() {
            return RNA;
        }

        public void setRNA(int RNA) {
            this.RNA = RNA;
        }

        public int getNCCS() {
            return NCCS;
        }

        public void setNCCS(int NCCS) {
            this.NCCS = NCCS;
        }

        public String getIdentityMark() {
            return identityMark;
        }

        public void setIdentityMark(String identityMark) {
            this.identityMark = identityMark;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getSeek_AffixImgList() {
            return seek_AffixImgList;
        }

        public void setSeek_AffixImgList(String seek_AffixImgList) {
            this.seek_AffixImgList = seek_AffixImgList;
        }

        public String getSeek_Voice() {
            return seek_Voice;
        }

        public void setSeek_Voice(String seek_Voice) {
            this.seek_Voice = seek_Voice;
        }
    }
}
