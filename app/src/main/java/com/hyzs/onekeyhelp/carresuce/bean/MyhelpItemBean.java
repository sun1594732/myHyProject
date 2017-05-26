package com.hyzs.onekeyhelp.carresuce.bean;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/24.
 */

public class MyhelpItemBean {

    /**
     * vehicleRescueMyHelpList : [{"imageGroup":[{"ed_id":9,"image_url":"http://localhost:49902/Files/179/img/179_1b8dd1ec-e6b4-4b36-94d5-3907076d1bd8.jpg"},{"ed_id":9,"image_url":"http://localhost:49902/Files/179/img/179_6e2dfec8-7560-4509-ab42-5df2b53ff167.jpg"}],"ID":9,"phone":"1555555","VR_Type":0,"reward":0,"GPSXY":"116.433589,39.910508","GPSDesc":"天安门广场附近","VehicleCardNum":"京A·1***8","content":"京A·1***8","seek_id":5,"seekHelpUserId":179,"helpPeopleUserId":202,"seek_State":1,"seek_Time":"1490339796","seek_Reminder":"向您发送了一个求助信息","Seek_Voice":"","HR_Type":0,"avatar":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","nickName":"娃哈哈","trueName":"宁夏","RNA":1,"NCCS":1,"identityMark":"10,2,3,1","community":"康惠园小区"}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<VehicleRescueMyHelpListBean> vehicleRescueMyHelpList;

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

    public List<VehicleRescueMyHelpListBean> getVehicleRescueMyHelpList() {
        return vehicleRescueMyHelpList;
    }

    public void setVehicleRescueMyHelpList(List<VehicleRescueMyHelpListBean> vehicleRescueMyHelpList) {
        this.vehicleRescueMyHelpList = vehicleRescueMyHelpList;
    }

    public static class VehicleRescueMyHelpListBean {
        /**
         * imageGroup : [{"ed_id":9,"image_url":"http://localhost:49902/Files/179/img/179_1b8dd1ec-e6b4-4b36-94d5-3907076d1bd8.jpg"},{"ed_id":9,"image_url":"http://localhost:49902/Files/179/img/179_6e2dfec8-7560-4509-ab42-5df2b53ff167.jpg"}]
         * ID : 9
         * phone : 1555555
         * VR_Type : 0
         * reward : 0
         * GPSXY : 116.433589,39.910508
         * GPSDesc : 天安门广场附近
         * VehicleCardNum : 京A·1***8
         * content : 京A·1***8
         * seek_id : 5
         * seekHelpUserId : 179
         * helpPeopleUserId : 202
         * seek_State : 1
         * seek_Time : 1490339796
         * seek_Reminder : 向您发送了一个求助信息
         * Seek_Voice :
         * HR_Type : 0
         * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * nickName : 娃哈哈
         * trueName : 宁夏
         * RNA : 1
         * NCCS : 1
         * identityMark : 10,2,3,1
         * community : 康惠园小区
         */
        private long HR_See_DateTime;
        private int HR_See;
        private int ID;
        private String phone;
        private int VR_Type;
        private int reward;
        private String GPSXY;
        private String GPSDesc;
        private String VehicleCardNum;
        private String content;
        private int seek_id;
        private int seekHelpUserId;
        private int helpPeopleUserId;
        private int seek_State;
        private long seek_Time;
        private String seek_Reminder;
        private String Seek_Voice;
        private int HR_Type;
        private String avatar;
        private String nickName;
        private String trueName;
        private int RNA;
        private int NCCS;
        private String identityMark;
        private String community;
        private List<ImageGroupBean> imageGroup;

        public long getHR_See_DateTime() {
            return HR_See_DateTime;
        }

        public void setHR_See_DateTime(long HR_See_DateTime) {
            this.HR_See_DateTime = HR_See_DateTime;
        }

        public int getHR_See() {
            return HR_See;
        }

        public void setHR_See(int HR_See) {
            this.HR_See = HR_See;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getVR_Type() {
            return VR_Type;
        }

        public void setVR_Type(int VR_Type) {
            this.VR_Type = VR_Type;
        }

        public int getReward() {
            return reward;
        }

        public void setReward(int reward) {
            this.reward = reward;
        }

        public String getGPSXY() {
            return GPSXY;
        }

        public void setGPSXY(String GPSXY) {
            this.GPSXY = GPSXY;
        }

        public String getGPSDesc() {
            return GPSDesc;
        }

        public void setGPSDesc(String GPSDesc) {
            this.GPSDesc = GPSDesc;
        }

        public String getVehicleCardNum() {
            return VehicleCardNum;
        }

        public void setVehicleCardNum(String VehicleCardNum) {
            this.VehicleCardNum = VehicleCardNum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSeek_id() {
            return seek_id;
        }

        public void setSeek_id(int seek_id) {
            this.seek_id = seek_id;
        }

        public int getSeekHelpUserId() {
            return seekHelpUserId;
        }

        public void setSeekHelpUserId(int seekHelpUserId) {
            this.seekHelpUserId = seekHelpUserId;
        }

        public int getHelpPeopleUserId() {
            return helpPeopleUserId;
        }

        public void setHelpPeopleUserId(int helpPeopleUserId) {
            this.helpPeopleUserId = helpPeopleUserId;
        }

        public int getSeek_State() {
            return seek_State;
        }

        public void setSeek_State(int seek_State) {
            this.seek_State = seek_State;
        }

        public long getSeek_Time() {
            return seek_Time;
        }

        public void setSeek_Time(long seek_Time) {
            this.seek_Time = seek_Time;
        }

        public String getSeek_Reminder() {
            return seek_Reminder;
        }

        public void setSeek_Reminder(String seek_Reminder) {
            this.seek_Reminder = seek_Reminder;
        }

        public String getSeek_Voice() {
            return Seek_Voice;
        }

        public void setSeek_Voice(String Seek_Voice) {
            this.Seek_Voice = Seek_Voice;
        }

        public int getHR_Type() {
            return HR_Type;
        }

        public void setHR_Type(int HR_Type) {
            this.HR_Type = HR_Type;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public int getRNA() {
            return RNA;
        }

        public void setRNA(int RNA) {
            this.RNA = RNA;
        }

        public int getNCCS() {
            return NCCS;
        }

        public void setNCCS(int NCCS) {
            this.NCCS = NCCS;
        }

        public String getIdentityMark() {
            return identityMark;
        }

        public void setIdentityMark(String identityMark) {
            this.identityMark = identityMark;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public List<ImageGroupBean> getImageGroup() {
            return imageGroup;
        }

        public void setImageGroup(List<ImageGroupBean> imageGroup) {
            this.imageGroup = imageGroup;
        }

        public static class ImageGroupBean {
            /**
             * ed_id : 9
             * image_url : http://localhost:49902/Files/179/img/179_1b8dd1ec-e6b4-4b36-94d5-3907076d1bd8.jpg
             */

            private int ed_id;
            private String image_url;

            public int getEd_id() {
                return ed_id;
            }

            public void setEd_id(int ed_id) {
                this.ed_id = ed_id;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }
        }
    }
}
