package com.hyzs.onekeyhelp.carresuce.bean;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/25.
 */

public class MyResuceItemBean {

    /**
     * myVehicleRescueList : [{"imageGroup":[{"ed_id":1,"image_url":"http://localhost:49902/Files/179/img/179_1b8dd1ec-e6b4-4b36-94d5-3907076d1bd8.jpg"},{"ed_id":1,"image_url":"http://localhost:49902/Files/179/img/179_6e2dfec8-7560-4509-ab42-5df2b53ff167.jpg"}],"ID":1,"phone":"1555555","VR_Type":0,"HelpCount":0,"reward":0,"GPSXY":"116.433589,39.910508","GPSDesc":"北京站","VehicleCardNum":"京J·N***B","content":"京A·1***8","seekHelpUserId":179,"seek_State":4,"seek_Time":"1490322554","seek_Reminder":"您的救援信息已成功发送给紧急联系人、附近的人、救援机构、和环宇客服。客服会尽快和您联系","Seek_Voice":"","avatar":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","nickName":"娃哈哈","trueName":"宁夏","RNA":1,"NCCS":1,"identityMark":"10,2,3,1","community":"康惠园小区"}]
     * total : 6
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<MyVehicleRescueListBean> myVehicleRescueList;

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

    public List<MyVehicleRescueListBean> getMyVehicleRescueList() {
        return myVehicleRescueList;
    }

    public void setMyVehicleRescueList(List<MyVehicleRescueListBean> myVehicleRescueList) {
        this.myVehicleRescueList = myVehicleRescueList;
    }

    public static class MyVehicleRescueListBean {
        /**
         * imageGroup : [{"ed_id":1,"image_url":"http://localhost:49902/Files/179/img/179_1b8dd1ec-e6b4-4b36-94d5-3907076d1bd8.jpg"},{"ed_id":1,"image_url":"http://localhost:49902/Files/179/img/179_6e2dfec8-7560-4509-ab42-5df2b53ff167.jpg"}]
         * ID : 1
         * phone : 1555555
         * VR_Type : 0
         * HelpCount : 0
         * reward : 0
         * GPSXY : 116.433589,39.910508
         * GPSDesc : 北京站
         * VehicleCardNum : 京J·N***B
         * content : 京A·1***8
         * seekHelpUserId : 179
         * seek_State : 4
         * seek_Time : 1490322554
         * seek_Reminder : 您的救援信息已成功发送给紧急联系人、附近的人、救援机构、和环宇客服。客服会尽快和您联系
         * Seek_Voice :
         * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * nickName : 娃哈哈
         * trueName : 宁夏
         * RNA : 1
         * NCCS : 1
         * identityMark : 10,2,3,1
         * community : 康惠园小区
         */

        private int ID;
        private String phone;
        private int VR_Type;
        private int HelpCount;
        private int reward;
        private String GPSXY;
        private String GPSDesc;
        private String VehicleCardNum;
        private String content;
        private int seekHelpUserId;
        private int seek_State;
        private long seek_Time;
        private String seek_Reminder;
        private String Seek_Voice;
        private String avatar;
        private String nickName;
        private String trueName;
        private int RNA;
        private int NCCS;
        private String identityMark;
        private String community;
        private List<ImageGroupBean> imageGroup;

        @Override
        public String toString() {
            return "MyVehicleRescueListBean{" +
                    "ID=" + ID +
                    ", phone='" + phone + '\'' +
                    ", VR_Type=" + VR_Type +
                    ", HelpCount=" + HelpCount +
                    ", reward=" + reward +
                    ", GPSXY='" + GPSXY + '\'' +
                    ", GPSDesc='" + GPSDesc + '\'' +
                    ", VehicleCardNum='" + VehicleCardNum + '\'' +
                    ", content='" + content + '\'' +
                    ", seekHelpUserId=" + seekHelpUserId +
                    ", seek_State=" + seek_State +
                    ", seek_Time=" + seek_Time +
                    ", seek_Reminder='" + seek_Reminder + '\'' +
                    ", Seek_Voice='" + Seek_Voice + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", trueName='" + trueName + '\'' +
                    ", RNA=" + RNA +
                    ", NCCS=" + NCCS +
                    ", identityMark='" + identityMark + '\'' +
                    ", community='" + community + '\'' +
                    ", imageGroup=" + imageGroup +
                    '}';
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

        public int getSeekHelpUserId() {
            return seekHelpUserId;
        }

        public void setSeekHelpUserId(int seekHelpUserId) {
            this.seekHelpUserId = seekHelpUserId;
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
             * ed_id : 1
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
