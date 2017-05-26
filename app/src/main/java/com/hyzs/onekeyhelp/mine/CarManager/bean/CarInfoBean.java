package com.hyzs.onekeyhelp.mine.CarManager.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class CarInfoBean {

    /**
     * myVehicleList : [{"userid":179,"vehicleID":6,"uesrname":"娃哈哈","V_VehicleCardNum":"京·JNB123"}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<MyVehicleListBean> myVehicleList;

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

    public List<MyVehicleListBean> getMyVehicleList() {
        return myVehicleList;
    }

    public void setMyVehicleList(List<MyVehicleListBean> myVehicleList) {
        this.myVehicleList = myVehicleList;
    }

    public static class MyVehicleListBean {
        /**
         * userid : 179
         * vehicleID : 6
         * uesrname : 娃哈哈
         * V_VehicleCardNum : 京·JNB123
         */

        private int userid;
        private int vehicleID;
        private String uesrname;
        private String V_VehicleCardNum;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getVehicleID() {
            return vehicleID;
        }

        public void setVehicleID(int vehicleID) {
            this.vehicleID = vehicleID;
        }

        public String getUesrname() {
            return uesrname;
        }

        public void setUesrname(String uesrname) {
            this.uesrname = uesrname;
        }

        public String getV_VehicleCardNum() {
            return V_VehicleCardNum;
        }

        public void setV_VehicleCardNum(String V_VehicleCardNum) {
            this.V_VehicleCardNum = V_VehicleCardNum;
        }
    }
}
