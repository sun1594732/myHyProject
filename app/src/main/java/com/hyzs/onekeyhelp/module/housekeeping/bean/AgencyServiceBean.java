package com.hyzs.onekeyhelp.module.housekeeping.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */

public class AgencyServiceBean implements Serializable {

    /**
     * lifeServiceOrganization : [{"id":3,"userid":1,"logo":"http://app.hyzsnt.com/file/img/ss.jpg","name":"嘻嘻哈哈机构","intro":"嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构凑够很多字，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构","tel":"010-055555","address":"北京市朝阳区xxxxxxx","distance":"0","publishTime":1494116652},{"id":2,"userid":1,"logo":"http://app.hyzsnt.com/file/img/ss.jpg","name":"嘻嘻哈哈机构","intro":"嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构凑够很多字，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构","tel":"010-055555","address":"北京市朝阳区xxxxxxx","distance":"0","publishTime":1494116556}]
     * total : 2
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<LifeServiceOrganizationBean> lifeServiceOrganization;

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

    public List<LifeServiceOrganizationBean> getLifeServiceOrganization() {
        return lifeServiceOrganization;
    }

    public void setLifeServiceOrganization(List<LifeServiceOrganizationBean> lifeServiceOrganization) {
        this.lifeServiceOrganization = lifeServiceOrganization;
    }

    public static class LifeServiceOrganizationBean implements Serializable{
        /**
         * id : 3
         * userid : 1
         * logo : http://app.hyzsnt.com/file/img/ss.jpg
         * name : 嘻嘻哈哈机构
         * intro : 嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构凑够很多字，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构
         * tel : 010-055555
         * address : 北京市朝阳区xxxxxxx
         * distance : 0
         * publishTime : 1494116652
         */

        private int id;
        private int userid;
        private String logo;
        private String name;
        private String intro;
        private String tel;
        private String address;
        private String distance;
        private int publishTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(int publishTime) {
            this.publishTime = publishTime;
        }
    }
}
