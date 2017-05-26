package com.hyzs.onekeyhelp.carresuce.bean;

import java.util.List;

public class ResuceTroopListBean {

    /**
     * vehicleRescueTeamList : [{"id":0,"VRT_Name":"救援大队2017壹组","VRT_Image":"http://app.hyzsnt.com/Files/1/Avatar/2017428/1_e3b15fa1-bf8e-4561-93eb-4ba7bfe9a183.jpg","VRT_Description":"","VRT_CreateTime":"1493368524","VRT_Remark":"","VRT_Address":"","VRT_Project":"","VRT_Range":"","VRT_Phone":"","VRT_fee":""}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<VehicleRescueTeamListBean> vehicleRescueTeamList;

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

    public List<VehicleRescueTeamListBean> getVehicleRescueTeamList() {
        return vehicleRescueTeamList;
    }

    public void setVehicleRescueTeamList(List<VehicleRescueTeamListBean> vehicleRescueTeamList) {
        this.vehicleRescueTeamList = vehicleRescueTeamList;
    }

    public static class VehicleRescueTeamListBean {
        /**
         * id : 0
         * VRT_Name : 救援大队2017壹组
         * VRT_Image : http://app.hyzsnt.com/Files/1/Avatar/2017428/1_e3b15fa1-bf8e-4561-93eb-4ba7bfe9a183.jpg
         * VRT_Description :
         * VRT_CreateTime : 1493368524
         * VRT_Remark :
         * VRT_Address :
         * VRT_Project :
         * VRT_Range :
         * VRT_Phone :
         * VRT_fee :
         */

        private int id;
        private String VRT_Name;
        private String VRT_Image;
        private String VRT_Description;
        private String VRT_CreateTime;
        private String VRT_Remark;
        private String VRT_Address;
        private String VRT_Project;
        private String VRT_Range;
        private String VRT_Phone;
        private String VRT_fee;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVRT_Name() {
            return VRT_Name;
        }

        public void setVRT_Name(String VRT_Name) {
            this.VRT_Name = VRT_Name;
        }

        public String getVRT_Image() {
            return VRT_Image;
        }

        public void setVRT_Image(String VRT_Image) {
            this.VRT_Image = VRT_Image;
        }

        public String getVRT_Description() {
            return VRT_Description;
        }

        public void setVRT_Description(String VRT_Description) {
            this.VRT_Description = VRT_Description;
        }

        public String getVRT_CreateTime() {
            return VRT_CreateTime;
        }

        public void setVRT_CreateTime(String VRT_CreateTime) {
            this.VRT_CreateTime = VRT_CreateTime;
        }

        public String getVRT_Remark() {
            return VRT_Remark;
        }

        public void setVRT_Remark(String VRT_Remark) {
            this.VRT_Remark = VRT_Remark;
        }

        public String getVRT_Address() {
            return VRT_Address;
        }

        public void setVRT_Address(String VRT_Address) {
            this.VRT_Address = VRT_Address;
        }

        public String getVRT_Project() {
            return VRT_Project;
        }

        public void setVRT_Project(String VRT_Project) {
            this.VRT_Project = VRT_Project;
        }

        public String getVRT_Range() {
            return VRT_Range;
        }

        public void setVRT_Range(String VRT_Range) {
            this.VRT_Range = VRT_Range;
        }

        public String getVRT_Phone() {
            return VRT_Phone;
        }

        public void setVRT_Phone(String VRT_Phone) {
            this.VRT_Phone = VRT_Phone;
        }

        public String getVRT_fee() {
            return VRT_fee;
        }

        public void setVRT_fee(String VRT_fee) {
            this.VRT_fee = VRT_fee;
        }
    }
}
