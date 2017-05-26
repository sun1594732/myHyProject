package com.hyzs.onekeyhelp.home.bean;

import java.util.List;

/**
 * Created by wubin on 2017/5/15.
 */

public class HomeFunctionBean {

    /**
     * homePageButtonNewInfoStatistics : [{"moudel":1,"newInfo":0},{"moudel":2,"newInfo":0},{"moudel":3,"newInfo":0},{"moudel":4,"newInfo":0},{"moudel":5,"newInfo":0},{"moudel":6,"newInfo":0},{"moudel":7,"newInfo":10},{"moudel":8,"newInfo":10},{"moudel":9,"newInfo":10},{"moudel":10,"newInfo":0}]
     * message : 正常
     * code : 10000
     */

    private String message;
    private String code;
    private List<HomePageButtonNewInfoStatisticsBean> homePageButtonNewInfoStatistics;

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

    public List<HomePageButtonNewInfoStatisticsBean> getHomePageButtonNewInfoStatistics() {
        return homePageButtonNewInfoStatistics;
    }

    public void setHomePageButtonNewInfoStatistics(List<HomePageButtonNewInfoStatisticsBean> homePageButtonNewInfoStatistics) {
        this.homePageButtonNewInfoStatistics = homePageButtonNewInfoStatistics;
    }

    public static class HomePageButtonNewInfoStatisticsBean {
        /**
         * moudel : 1
         * newInfo : 0
         */

        private int moudel;
        private int newInfo;

        public int getMoudel() {
            return moudel;
        }

        public void setMoudel(int moudel) {
            this.moudel = moudel;
        }

        public int getNewInfo() {
            return newInfo;
        }

        public void setNewInfo(int newInfo) {
            this.newInfo = newInfo;
        }
    }
}
