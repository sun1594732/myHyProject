package com.hyzs.onekeyhelp.carresuce.bean;

import java.util.List;


public class MyHelpDetailsBean {

    /**
     * myHelpVehicleRescueDetails : {"VR_Type":"\u201d0\u201d","phone":"\u201d15555555\u201d","HelpCount":0,"reward":0,"GPSXY":"116.433589,39.910508","GPSDesc":"天安门广场附近","VehicleCardNum":"京J·N***B","content":"京A·1***8","VehicleName":null,"ID":5,"seekHelpUserId":179,"helpPeopleUserId":0,"seek_State":1,"seek_Time":"1490339796","seek_Reminder":"您的救援信息已成功发送给紧急联系人、附近的人、救援机构、和环宇客服。客服会尽快和您联系","Seek_Voice":"","HR_Type":0,"avatar":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","nickName":"娃哈哈","trueName":"宁夏","RNA":1,"NCCS":1,"identityMark":"10,2,3,1","community":"康惠园小区"}
     * imageGroup : [{"id":452,"image_url":"http://localhost:49902/Files/179/img/179_1b8dd1ec-e6b4-4b36-94d5-3907076d1bd8.jpg"},{"id":453,"image_url":"http://localhost:49902/Files/179/img/179_6e2dfec8-7560-4509-ab42-5df2b53ff167.jpg"}]
     * message : 正常
     * code : 10000
     */

    private MyHelpVehicleRescueDetailsBean myHelpVehicleRescueDetails;
    private String message;
    private String code;
    private List<ImageGroupBean> imageGroup;

    public MyHelpVehicleRescueDetailsBean getMyHelpVehicleRescueDetails() {
        return myHelpVehicleRescueDetails;
    }

    public void setMyHelpVehicleRescueDetails(MyHelpVehicleRescueDetailsBean myHelpVehicleRescueDetails) {
        this.myHelpVehicleRescueDetails = myHelpVehicleRescueDetails;
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

    public List<ImageGroupBean> getImageGroup() {
        return imageGroup;
    }

    public void setImageGroup(List<ImageGroupBean> imageGroup) {
        this.imageGroup = imageGroup;
    }

    public static class MyHelpVehicleRescueDetailsBean {
        /**
         * VR_Type : ”0”
         * phone : ”15555555”
         * HelpCount : 0
         * reward : 0
         * GPSXY : 116.433589,39.910508
         * GPSDesc : 天安门广场附近
         * VehicleCardNum : 京J·N***B
         * content : 京A·1***8
         * VehicleName : null
         * ID : 5
         * seekHelpUserId : 179
         * helpPeopleUserId : 0
         * seek_State : 1
         * seek_Time : 1490339796
         * seek_Reminder : 您的救援信息已成功发送给紧急联系人、附近的人、救援机构、和环宇客服。客服会尽快和您联系
         * Seek_Voice :
         * HR_Type : 0
         * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * nickName : 娃哈哈
         * trueName : 宁夏
         * RNA : 1
         * NCCS : 1
         * identityMark : 10,2,3,1
         * community : 康惠园小区]
         * "Distance":"0","HR_BonusPoint":0,"HR_PayMoney":0,"HR_RescueState":0
         */

        private String VR_Type;
        private String phone;
        private String Distance;
        private int HR_BonusPoint;
        private int HR_PayMoney;
        private int HR_RescueState;
        private int HelpCount;
        private int reward;
        private String GPSXY;
        private String GPSDesc;
        private String VehicleCardNum;
        private String content;
        private Object VehicleName;
        private int ID;
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

        public String getDistance() {
            return Distance;
        }

        public void setDistance(String distance) {
            Distance = distance;
        }

        public int getHR_BonusPoint() {
            return HR_BonusPoint;
        }

        public void setHR_BonusPoint(int HR_BonusPoint) {
            this.HR_BonusPoint = HR_BonusPoint;
        }

        public int getHR_PayMoney() {
            return HR_PayMoney;
        }

        public void setHR_PayMoney(int HR_PayMoney) {
            this.HR_PayMoney = HR_PayMoney;
        }

        public int getHR_RescueState() {
            return HR_RescueState;
        }

        public void setHR_RescueState(int HR_RescueState) {
            this.HR_RescueState = HR_RescueState;
        }

        public String getVR_Type() {
            return VR_Type;
        }

        public void setVR_Type(String VR_Type) {
            this.VR_Type = VR_Type;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getHelpCount() {
            return HelpCount;
        }

        public void setHelpCount(int HelpCount) {
            this.HelpCount = HelpCount;
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

        public Object getVehicleName() {
            return VehicleName;
        }

        public void setVehicleName(Object VehicleName) {
            this.VehicleName = VehicleName;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
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
    }

    public static class ImageGroupBean {
        /**
         * id : 452
         * image_url : http://localhost:49902/Files/179/img/179_1b8dd1ec-e6b4-4b36-94d5-3907076d1bd8.jpg
         */

        private int id;
        private String image_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
