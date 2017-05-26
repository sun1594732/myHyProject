package com.hyzs.onekeyhelp.contact.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */

public class SearchBean  {

    /**
     * searchAroundResult : [{"community":"","uid":458,"avatar":"http://img.zglzxf.com/default/2016/0725/5444541759260865946.jpg","userNick":"赵荔","locationX":"39.9185030000","locationY":"116.5076210000","distance":"2.81359","RNA":1,"NCCS":1,"identityMark":""}]
     * total : 8
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<SearchAroundResultBean> searchAroundResult;

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

    public List<SearchAroundResultBean> getSearchAroundResult() {
        return searchAroundResult;
    }

    public void setSearchAroundResult(List<SearchAroundResultBean> searchAroundResult) {
        this.searchAroundResult = searchAroundResult;
    }

    public static class SearchAroundResultBean implements Serializable{
        /**
         * community :
         * uid : 458
         * avatar : http://img.zglzxf.com/default/2016/0725/5444541759260865946.jpg
         * userNick : 赵荔
         * locationX : 39.9185030000
         * locationY : 116.5076210000
         * distance : 2.81359
         * RNA : 1
         * NCCS : 1
         * identityMark :
         */

        private String community;
        private int uid;
        private String avatar;
        private String userNick;
        private String locationX;
        private String locationY;
        private String distance;
        private int RNA;
        private int NCCS;
        private String identityMark;

        @Override
        public String toString() {
            return "SearchAroundResultBean{" +
                    "community='" + community + '\'' +
                    ", uid=" + uid +
                    ", avatar='" + avatar + '\'' +
                    ", userNick='" + userNick + '\'' +
                    ", locationX='" + locationX + '\'' +
                    ", locationY='" + locationY + '\'' +
                    ", distance='" + distance + '\'' +
                    ", RNA=" + RNA +
                    ", NCCS=" + NCCS +
                    ", identityMark='" + identityMark + '\'' +
                    '}';
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUserNick() {
            return userNick;
        }

        public void setUserNick(String userNick) {
            this.userNick = userNick;
        }

        public String getLocationX() {
            return locationX;
        }

        public void setLocationX(String locationX) {
            this.locationX = locationX;
        }

        public String getLocationY() {
            return locationY;
        }

        public void setLocationY(String locationY) {
            this.locationY = locationY;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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
    }
}
