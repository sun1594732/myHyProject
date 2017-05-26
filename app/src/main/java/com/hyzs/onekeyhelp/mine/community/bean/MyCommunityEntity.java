package com.hyzs.onekeyhelp.mine.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class MyCommunityEntity {

    /**
     * mycommunity : [{"id":1,"communityName":"白领家园","lat":"39.916996","lng":"116.52731","m_default":0,"GPSDesc":"北京朝阳区白领家园"}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<MycommunityBean> mycommunity;

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

    public List<MycommunityBean> getMycommunity() {
        return mycommunity;
    }

    public void setMycommunity(List<MycommunityBean> mycommunity) {
        this.mycommunity = mycommunity;
    }

    public static class MycommunityBean {
        /**
         * id : 1
         * communityName : 白领家园
         * lat : 39.916996
         * lng : 116.52731
         * m_default : 0
         * GPSDesc : 北京朝阳区白领家园
         */

        private int id;
        private String communityName;
        private String lat;
        private String lng;
        private int m_default;
        private String GPSDesc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public int getM_default() {
            return m_default;
        }

        public void setM_default(int m_default) {
            this.m_default = m_default;
        }

        public String getGPSDesc() {
            return GPSDesc;
        }

        public void setGPSDesc(String GPSDesc) {
            this.GPSDesc = GPSDesc;
        }
    }
}
