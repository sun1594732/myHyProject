package com.hyzs.onekeyhelp.module.housekeeping.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ServiceProjectListBean {
    /**
     * lifeServiceSPList : [{"ID":1,"SPP_LS_ID":1,"SPP_Name":"专业擦玻璃","SPP_Desc":"专业擦玻璃简短描述","SPP_Price":135,"SPP_UseState":0,"SPP_AddTime":"1494295008","SPP_ApprovalState":0,"SPP_Img":"","SPP_IsTop":0,"SPP_Sort":0,"SPP_OriginalCost":170,"SPP_Unit":0,"SPP_ServiceObj":0,"SPP_DetailIntro":"专业擦玻璃详细解释"}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<LifeServiceSPListBean> lifeServiceSPList;

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

    public List<LifeServiceSPListBean> getLifeServiceSPList() {
        return lifeServiceSPList;
    }

    public void setLifeServiceSPList(List<LifeServiceSPListBean> lifeServiceSPList) {
        this.lifeServiceSPList = lifeServiceSPList;
    }

    public static class LifeServiceSPListBean {
        /**
         * ID : 1
         * SPP_LS_ID : 1
         * SPP_Name : 专业擦玻璃
         * SPP_Desc : 专业擦玻璃简短描述
         * SPP_Price : 135
         * SPP_UseState : 0
         * SPP_AddTime : 1494295008
         * SPP_ApprovalState : 0
         * SPP_Img :
         * SPP_IsTop : 0
         * SPP_Sort : 0
         * SPP_OriginalCost : 170
         * SPP_Unit : 0
         * SPP_ServiceObj : 0
         * SPP_DetailIntro : 专业擦玻璃详细解释
         */

        private int ID;
        private int SPP_LS_ID;
        private String SPP_Name;
        private String SPP_Desc;
        private int SPP_Price;
        private int SPP_UseState;
        private String SPP_AddTime;
        private int SPP_ApprovalState;
        private String SPP_Img;
        private int SPP_IsTop;
        private int SPP_Sort;
        private int SPP_OriginalCost;
        private int SPP_Unit;
        private int SPP_ServiceObj;
        private String SPP_DetailIntro;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getSPP_LS_ID() {
            return SPP_LS_ID;
        }

        public void setSPP_LS_ID(int SPP_LS_ID) {
            this.SPP_LS_ID = SPP_LS_ID;
        }

        public String getSPP_Name() {
            return SPP_Name;
        }

        public void setSPP_Name(String SPP_Name) {
            this.SPP_Name = SPP_Name;
        }

        public String getSPP_Desc() {
            return SPP_Desc;
        }

        public void setSPP_Desc(String SPP_Desc) {
            this.SPP_Desc = SPP_Desc;
        }

        public int getSPP_Price() {
            return SPP_Price;
        }

        public void setSPP_Price(int SPP_Price) {
            this.SPP_Price = SPP_Price;
        }

        public int getSPP_UseState() {
            return SPP_UseState;
        }

        public void setSPP_UseState(int SPP_UseState) {
            this.SPP_UseState = SPP_UseState;
        }

        public String getSPP_AddTime() {
            return SPP_AddTime;
        }

        public void setSPP_AddTime(String SPP_AddTime) {
            this.SPP_AddTime = SPP_AddTime;
        }

        public int getSPP_ApprovalState() {
            return SPP_ApprovalState;
        }

        public void setSPP_ApprovalState(int SPP_ApprovalState) {
            this.SPP_ApprovalState = SPP_ApprovalState;
        }

        public String getSPP_Img() {
            return SPP_Img;
        }

        public void setSPP_Img(String SPP_Img) {
            this.SPP_Img = SPP_Img;
        }

        public int getSPP_IsTop() {
            return SPP_IsTop;
        }

        public void setSPP_IsTop(int SPP_IsTop) {
            this.SPP_IsTop = SPP_IsTop;
        }

        public int getSPP_Sort() {
            return SPP_Sort;
        }

        public void setSPP_Sort(int SPP_Sort) {
            this.SPP_Sort = SPP_Sort;
        }

        public int getSPP_OriginalCost() {
            return SPP_OriginalCost;
        }

        public void setSPP_OriginalCost(int SPP_OriginalCost) {
            this.SPP_OriginalCost = SPP_OriginalCost;
        }

        public int getSPP_Unit() {
            return SPP_Unit;
        }

        public void setSPP_Unit(int SPP_Unit) {
            this.SPP_Unit = SPP_Unit;
        }

        public int getSPP_ServiceObj() {
            return SPP_ServiceObj;
        }

        public void setSPP_ServiceObj(int SPP_ServiceObj) {
            this.SPP_ServiceObj = SPP_ServiceObj;
        }

        public String getSPP_DetailIntro() {
            return SPP_DetailIntro;
        }

        public void setSPP_DetailIntro(String SPP_DetailIntro) {
            this.SPP_DetailIntro = SPP_DetailIntro;
        }
    }
}
