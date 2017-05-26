package com.hyzs.onekeyhelp.home.bean;

import java.util.List;

/**
 * Created by wubin on 2017/4/6.
 */

public class CommunityNoticeBean {

    /**
     * communityNoticeList : [{"CNC_Name":"社区公告","classifyID":1,"id":1,"NC_Title":"","NC_HeadImg":null,"NC_AddTime":"1491321600","diffTime":"22小时前","NC_Top":1,"NC_Sort":0,"NC_Name":"康馨园居委会","NC_StreetScene":"双桥街"}]
     * total : 6
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<CommunityNoticeListBean> communityNoticeList;

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

    public List<CommunityNoticeListBean> getCommunityNoticeList() {
        return communityNoticeList;
    }

    public void setCommunityNoticeList(List<CommunityNoticeListBean> communityNoticeList) {
        this.communityNoticeList = communityNoticeList;
    }

    public static class CommunityNoticeListBean {
        /**
         * CNC_Name : 社区公告
         * classifyID : 1
         * id : 1
         * NC_Title :
         * NC_HeadImg : null
         * NC_AddTime : 1491321600
         * diffTime : 22小时前
         * NC_Top : 1
         * NC_Sort : 0
         * NC_Name : 康馨园居委会
         * NC_StreetScene : 双桥街
         */

        private String CNC_Name;
        private int classifyID;
        private int id;
        private String NC_Title;
        private Object NC_HeadImg;
        private String NC_AddTime;
        private String diffTime;
        private int NC_Top;
        private int NC_Sort;
        private String NC_Name;
        private String NC_StreetScene;

        public String getCNC_Name() {
            return CNC_Name;
        }

        public void setCNC_Name(String CNC_Name) {
            this.CNC_Name = CNC_Name;
        }

        public int getClassifyID() {
            return classifyID;
        }

        public void setClassifyID(int classifyID) {
            this.classifyID = classifyID;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNC_Title() {
            return NC_Title;
        }

        public void setNC_Title(String NC_Title) {
            this.NC_Title = NC_Title;
        }

        public Object getNC_HeadImg() {
            return NC_HeadImg;
        }

        public void setNC_HeadImg(Object NC_HeadImg) {
            this.NC_HeadImg = NC_HeadImg;
        }

        public String getNC_AddTime() {
            return NC_AddTime;
        }

        public void setNC_AddTime(String NC_AddTime) {
            this.NC_AddTime = NC_AddTime;
        }

        public String getDiffTime() {
            return diffTime;
        }

        public void setDiffTime(String diffTime) {
            this.diffTime = diffTime;
        }

        public int getNC_Top() {
            return NC_Top;
        }

        public void setNC_Top(int NC_Top) {
            this.NC_Top = NC_Top;
        }

        public int getNC_Sort() {
            return NC_Sort;
        }

        public void setNC_Sort(int NC_Sort) {
            this.NC_Sort = NC_Sort;
        }

        public String getNC_Name() {
            return NC_Name;
        }

        public void setNC_Name(String NC_Name) {
            this.NC_Name = NC_Name;
        }

        public String getNC_StreetScene() {
            return NC_StreetScene;
        }

        public void setNC_StreetScene(String NC_StreetScene) {
            this.NC_StreetScene = NC_StreetScene;
        }
    }
}
