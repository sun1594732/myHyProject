package com.hyzs.onekeyhelp.mine.CarManager.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class InsurInfoBean {

    /**
     * myVehicleInsuranceList : [{"userid":179,"InsuranceID":17,"uesrname":"娃哈哈","IC_Name":"中国平安保险"}]
     * total : 3
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<MyVehicleInsuranceListBean> myVehicleInsuranceList;

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

    public List<MyVehicleInsuranceListBean> getMyVehicleInsuranceList() {
        return myVehicleInsuranceList;
    }

    public void setMyVehicleInsuranceList(List<MyVehicleInsuranceListBean> myVehicleInsuranceList) {
        this.myVehicleInsuranceList = myVehicleInsuranceList;
    }

    public static class MyVehicleInsuranceListBean {
        /**
         * userid : 179
         * InsuranceID : 17
         * uesrname : 娃哈哈
         * IC_Name : 中国平安保险
         */

        private int userid;
        private int InsuranceID;
        private String uesrname;
        private String IC_Name;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getInsuranceID() {
            return InsuranceID;
        }

        public void setInsuranceID(int InsuranceID) {
            this.InsuranceID = InsuranceID;
        }

        public String getUesrname() {
            return uesrname;
        }

        public void setUesrname(String uesrname) {
            this.uesrname = uesrname;
        }

        public String getIC_Name() {
            return IC_Name;
        }

        public void setIC_Name(String IC_Name) {
            this.IC_Name = IC_Name;
        }
    }
}
