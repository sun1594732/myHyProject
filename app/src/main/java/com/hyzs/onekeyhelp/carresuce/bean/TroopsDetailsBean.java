package com.hyzs.onekeyhelp.carresuce.bean;

/**
 * Created by Administrator on 2017/4/28.
 */

public class TroopsDetailsBean {

    /**
     * vehicleRescueTeamDetails : {"id":1,"VRT_Name":"中国(大陆)汽车救援服务平台","VRT_Image":"","VRT_Description":"中国(大陆)汽车救援服务平台|全国道路救援公司 一个电话,24小时救援！我们专注于汽车救援、道路救援，不管你在中国大陆哪个城市/地区，一个电话，为你提供24小时汽车救援服务。","VRT_CreateTime":"1493372445","VRT_Remark":"","VRT_Address":"","VRT_Project":"拖车救援、汽车搭电、紧急送油送水、流动换胎补胎、交通拯救、汽车困境救援服务等","VRT_Range":"中国大陆","VRT_Phone":"4006185995","VRT_fee":""}
     * message : 正常
     * code : 10000
     */

    private VehicleRescueTeamDetailsBean vehicleRescueTeamDetails;
    private String message;
    private String code;

    public VehicleRescueTeamDetailsBean getVehicleRescueTeamDetails() {
        return vehicleRescueTeamDetails;
    }

    public void setVehicleRescueTeamDetails(VehicleRescueTeamDetailsBean vehicleRescueTeamDetails) {
        this.vehicleRescueTeamDetails = vehicleRescueTeamDetails;
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

    public static class VehicleRescueTeamDetailsBean {
        /**
         * id : 1
         * VRT_Name : 中国(大陆)汽车救援服务平台
         * VRT_Image :
         * VRT_Description : 中国(大陆)汽车救援服务平台|全国道路救援公司 一个电话,24小时救援！我们专注于汽车救援、道路救援，不管你在中国大陆哪个城市/地区，一个电话，为你提供24小时汽车救援服务。
         * VRT_CreateTime : 1493372445
         * VRT_Remark :
         * VRT_Address :
         * VRT_Project : 拖车救援、汽车搭电、紧急送油送水、流动换胎补胎、交通拯救、汽车困境救援服务等
         * VRT_Range : 中国大陆
         * VRT_Phone : 4006185995
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
