package com.hyzs.onekeyhelp.carresuce.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyzs123 on 2017/3/25.
 */

public class MyResuceDetailsBean {


    /**
     * myHelpVehicleRescueDetails : {"HelpCount":0,"reward":11,"GPSXY":"116.433589,39.910508","GPSDesc":"北京","VehicleCardNum":"京J·N***B","VehicleName":null,"ID":1,"seekHelpUserId":179,"helpPeopleUserId":0,"seek_State":4,"seek_Time":"1490322554","seek_Reminder":"您的救援信息已成功发送给紧急联系人、附近的人、救援机构、和环宇客服。客服会尽快和您联系","Seek_Voice":"http://192.168.1.186:803/Files/13/voice/13_92701445-9791-42be-9278-495b16e2bef4.mp3","HR_Type":0,"avatar":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","nickName":"娃哈哈","trueName":"宁夏","RNA":1,"NCCS":1,"identityMark":"10,2,3,1","community":"康惠园小区"}
     * imageGroup : [{"id":464,"image_url":"http://192.168.1.186:803/Files/13/img/13_b6b753d1-628e-4946-af0b-4c1b5f015b3f.jpg"},{"id":465,"image_url":"http://192.168.1.186:803/Files/13/img/13_1e16e674-2cec-4ab4-9622-64f26ea2d225.jpg"}]
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
         * HelpCount : 0
         * reward : 11
         * GPSXY : 116.433589,39.910508
         * GPSDesc : 北京
         * VehicleCardNum : 京J·N***B
         * VehicleName : null
         * ID : 1
         * seekHelpUserId : 179
         * helpPeopleUserId : 0
         * seek_State : 4
         * seek_Time : 1490322554
         * seek_Reminder : 您的救援信息已成功发送给紧急联系人、附近的人、救援机构、和环宇客服。客服会尽快和您联系
         * Seek_Voice : http://192.168.1.186:803/Files/13/voice/13_92701445-9791-42be-9278-495b16e2bef4.mp3
         * HR_Type : 0
         * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * nickName : 娃哈哈
         * trueName : 宁夏
         * RNA : 1
         * NCCS : 1
         * identityMark : 10,2,3,1
         * community : 康惠园小区
         */
        private String VR_Type;
        private String phone;

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

        private int HelpCount;
        private int reward;
        private String GPSXY;
        private String content;
        private String GPSDesc;
        private String VehicleCardNum;
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



        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

    public static class ImageGroupBean  implements Serializable{
        /**
         * id : 464
         * image_url : http://192.168.1.186:803/Files/13/img/13_b6b753d1-628e-4946-af0b-4c1b5f015b3f.jpg
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
