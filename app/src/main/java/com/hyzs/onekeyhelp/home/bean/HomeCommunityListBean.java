package com.hyzs.onekeyhelp.home.bean;

import java.util.List;

/**
 * Created by wubin on 2017/4/7.
 */

public class HomeCommunityListBean {

    /**
     * homePageGetChangeCommunityList : [{"id":3,"OfCurrUser":1,"C_Name":"甘露园南里二区"}]
     * total : 5
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<HomePageGetChangeCommunityListBean> homePageGetChangeCommunityList;

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

    public List<HomePageGetChangeCommunityListBean> getHomePageGetChangeCommunityList() {
        return homePageGetChangeCommunityList;
    }

    public void setHomePageGetChangeCommunityList(List<HomePageGetChangeCommunityListBean> homePageGetChangeCommunityList) {
        this.homePageGetChangeCommunityList = homePageGetChangeCommunityList;
    }

    public static class HomePageGetChangeCommunityListBean {
        /**
         * id : 3
         * OfCurrUser : 1
         * C_Name : 甘露园南里二区
         */

        private int id;
        private int OfCurrUser;
        private String C_Name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOfCurrUser() {
            return OfCurrUser;
        }

        public void setOfCurrUser(int OfCurrUser) {
            this.OfCurrUser = OfCurrUser;
        }

        public String getC_Name() {
            return C_Name;
        }

        public void setC_Name(String C_Name) {
            this.C_Name = C_Name;
        }
    }
}
