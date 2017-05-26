package com.hyzs.onekeyhelp.mine.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class AddCommunityBean {

    /**
     * searchCommunity : [{"id":8,"lat":"39.905596","lng":"116.546929","communityName":"润福缘","distance":"大约9公里","firstLetter":"R","GPSDesc":"北京朝阳区润福缘"}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<SearchCommunityBean> searchCommunity;

    @Override
    public String toString() {
        return "AddCommunityBean{" +
                "total=" + total +
                ", message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", searchCommunity=" + searchCommunity +
                '}';
    }

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

    public List<SearchCommunityBean> getSearchCommunity() {
        return searchCommunity;
    }

    public void setSearchCommunity(List<SearchCommunityBean> searchCommunity) {
        this.searchCommunity = searchCommunity;
    }

    public static class SearchCommunityBean {
        /**
         * id : 8
         * lat : 39.905596
         * lng : 116.546929
         * communityName : 润福缘
         * distance : 大约9公里
         * firstLetter : R
         * GPSDesc : 北京朝阳区润福缘
         */

        private int id;
        private String lat;
        private String lng;
        private String communityName;
        private String distance;
        private String firstLetter;
        private String GPSDesc;
        private int isCheck;

        @Override
        public String toString() {
            return "SearchCommunityBean{" +
                    "id=" + id +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    ", communityName='" + communityName + '\'' +
                    ", distance='" + distance + '\'' +
                    ", firstLetter='" + firstLetter + '\'' +
                    ", GPSDesc='" + GPSDesc + '\'' +
                    ", isCheck=" + isCheck +
                    '}';
        }

        public int getIsCheck() {
            return isCheck;
        }

        public void setIsCheck(int isCheck) {
            this.isCheck = isCheck;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getFirstLetter() {
            return firstLetter;
        }

        public void setFirstLetter(String firstLetter) {
            this.firstLetter = firstLetter;
        }

        public String getGPSDesc() {
            return GPSDesc;
        }

        public void setGPSDesc(String GPSDesc) {
            this.GPSDesc = GPSDesc;
        }
    }
}
