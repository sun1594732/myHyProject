package com.hyzs.onekeyhelp.family.movable.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/23.
 */

public class MovableDetailsBean {

    /**
     * eventRegistrationDetails : {"ER_Title":"本周31日全天寻好朋友组团爬长城","ER_SponsorType":0,"ER_Datetime":"1490161609","ER_Content":"早上爬山 中午在山上吃饭。晚上回家早上爬山 中午在山上吃饭 晚上回家","ER_Event_Start":"1490716800","ER_Event_End":"1490803200","ER_Registration_End":"1490868000","ER_Address":"长城脚下","StateCode":2,"ER_Registration_Max":20,"ER_Route":"山下门口-上山-下山","ER_Condition":"20-40岁 男女不限","ER_Equipment":" 所需装备 所需装备 所需装备 所需装备 所需装备 所需装备 ","ER_Attention":"  这里填写注意事项  这里填写注意事项  这里填写注意事项  这里填写注意事项  v这里填写注意事项","ApplyCount":1,"userID":179,"nickName":"娃哈哈","trueName":"宁夏","avatar":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","RNA":1,"NCCS":1,"identityMark":"10,2,3,1","community":"康惠园小区"}
     * imageGroup : [{"id":13,"image_url":"Files/179/img/179_dd9956d4-9676-40b1-b52b-2c98b674ff80.jpg"},{"id":14,"image_url":"Files/179/img/179_8840196c-e3d6-4934-8e1e-ec8ed8a1fff0.jpg"}]
     * message : 正常
     * code : 10000
     */

    private EventRegistrationDetailsBean eventRegistrationDetails;
    private String message;
    private String code;
    private List<ImageGroupBean> imageGroup;

    public EventRegistrationDetailsBean getEventRegistrationDetails() {
        return eventRegistrationDetails;
    }

    public void setEventRegistrationDetails(EventRegistrationDetailsBean eventRegistrationDetails) {
        this.eventRegistrationDetails = eventRegistrationDetails;
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

    public static class EventRegistrationDetailsBean {
        /**
         * ER_Title : 本周31日全天寻好朋友组团爬长城
         * ER_SponsorType : 0
         * ER_Datetime : 1490161609
         * ER_Content : 早上爬山 中午在山上吃饭。晚上回家早上爬山 中午在山上吃饭 晚上回家
         * ER_Event_Start : 1490716800
         * ER_Event_End : 1490803200
         * ER_Registration_End : 1490868000
         * ER_Address : 长城脚下
         * StateCode : 2
         * ER_Registration_Max : 20
         * ER_Route : 山下门口-上山-下山
         * ER_Condition : 20-40岁 男女不限
         * ER_Equipment :  所需装备 所需装备 所需装备 所需装备 所需装备 所需装备
         * ER_Attention :   这里填写注意事项  这里填写注意事项  这里填写注意事项  这里填写注意事项  v这里填写注意事项
         * ApplyCount : 1
         * userID : 179
         * nickName : 娃哈哈
         * trueName : 宁夏
         * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * RNA : 1
         * NCCS : 1
         * identityMark : 10,2,3,1
         * community : 康惠园小区
         */

        private String ER_Title;
        private int ER_SponsorType;
        private String ER_Datetime;
        private String ER_Registration_Start;
        private String ER_Content;
        private String ER_Event_Start;
        private String ER_Event_End;
        private String ER_Registration_End;
        private String ER_Address;
        private int StateCode;
        private int ER_Registration_Max;
        private String ER_Route;
        private String ER_Condition;
        private String ER_Equipment;
        private String ER_Attention;
        private int ApplyCount;
        private int userID;
        private String nickName;
        private String trueName;
        private String avatar;
        private int RNA;
        private int NCCS;
        private String identityMark;
        private String community;
        private int CurrUserApplyState;

        public String getER_Registration_Start() {
            return ER_Registration_Start;
        }

        public void setER_Registration_Start(String ER_Registration_Start) {
            this.ER_Registration_Start = ER_Registration_Start;
        }

        public int getCurrUserApplyState() {
            return CurrUserApplyState;
        }

        public void setCurrUserApplyState(int currUserApplyState) {
            CurrUserApplyState = currUserApplyState;
        }

        @Override
        public String toString() {
            return "EventRegistrationDetailsBean{" +
                    "ER_Title='" + ER_Title + '\'' +
                    ", ER_SponsorType=" + ER_SponsorType +
                    ", ER_Datetime='" + ER_Datetime + '\'' +
                    ", ER_Content='" + ER_Content + '\'' +
                    ", ER_Event_Start='" + ER_Event_Start + '\'' +
                    ", ER_Event_End='" + ER_Event_End + '\'' +
                    ", ER_Registration_End='" + ER_Registration_End + '\'' +
                    ", ER_Address='" + ER_Address + '\'' +
                    ", StateCode=" + StateCode +
                    ", ER_Registration_Max=" + ER_Registration_Max +
                    ", ER_Route='" + ER_Route + '\'' +
                    ", ER_Condition='" + ER_Condition + '\'' +
                    ", ER_Equipment='" + ER_Equipment + '\'' +
                    ", ER_Attention='" + ER_Attention + '\'' +
                    ", ApplyCount=" + ApplyCount +
                    ", userID=" + userID +
                    ", nickName='" + nickName + '\'' +
                    ", trueName='" + trueName + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", RNA=" + RNA +
                    ", NCCS=" + NCCS +
                    ", identityMark='" + identityMark + '\'' +
                    ", community='" + community + '\'' +
                    '}';
        }

        public String getER_Title() {
            return ER_Title;
        }

        public void setER_Title(String ER_Title) {
            this.ER_Title = ER_Title;
        }

        public int getER_SponsorType() {
            return ER_SponsorType;
        }

        public void setER_SponsorType(int ER_SponsorType) {
            this.ER_SponsorType = ER_SponsorType;
        }

        public String getER_Datetime() {
            return ER_Datetime;
        }

        public void setER_Datetime(String ER_Datetime) {
            this.ER_Datetime = ER_Datetime;
        }

        public String getER_Content() {
            return ER_Content;
        }

        public void setER_Content(String ER_Content) {
            this.ER_Content = ER_Content;
        }

        public String getER_Event_Start() {
            return ER_Event_Start;
        }

        public void setER_Event_Start(String ER_Event_Start) {
            this.ER_Event_Start = ER_Event_Start;
        }

        public String getER_Event_End() {
            return ER_Event_End;
        }

        public void setER_Event_End(String ER_Event_End) {
            this.ER_Event_End = ER_Event_End;
        }

        public String getER_Registration_End() {
            return ER_Registration_End;
        }

        public void setER_Registration_End(String ER_Registration_End) {
            this.ER_Registration_End = ER_Registration_End;
        }

        public String getER_Address() {
            return ER_Address;
        }

        public void setER_Address(String ER_Address) {
            this.ER_Address = ER_Address;
        }

        public int getStateCode() {
            return StateCode;
        }

        public void setStateCode(int StateCode) {
            this.StateCode = StateCode;
        }

        public int getER_Registration_Max() {
            return ER_Registration_Max;
        }

        public void setER_Registration_Max(int ER_Registration_Max) {
            this.ER_Registration_Max = ER_Registration_Max;
        }

        public String getER_Route() {
            return ER_Route;
        }

        public void setER_Route(String ER_Route) {
            this.ER_Route = ER_Route;
        }

        public String getER_Condition() {
            return ER_Condition;
        }

        public void setER_Condition(String ER_Condition) {
            this.ER_Condition = ER_Condition;
        }

        public String getER_Equipment() {
            return ER_Equipment;
        }

        public void setER_Equipment(String ER_Equipment) {
            this.ER_Equipment = ER_Equipment;
        }

        public String getER_Attention() {
            return ER_Attention;
        }

        public void setER_Attention(String ER_Attention) {
            this.ER_Attention = ER_Attention;
        }

        public int getApplyCount() {
            return ApplyCount;
        }

        public void setApplyCount(int ApplyCount) {
            this.ApplyCount = ApplyCount;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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
         * id : 13
         * image_url : Files/179/img/179_dd9956d4-9676-40b1-b52b-2c98b674ff80.jpg
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
