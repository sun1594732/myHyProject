package com.hyzs.onekeyhelp.module.housekeeping.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/7.
 */

public class MineServiceDetailBean {

    /**
     * personalInfo : {"recommendIndex":0,"distance":"0","face":"http://app.hyzsnt.com/files/avarta/jj.jpg","tag":"月嫂,四年经验,全职","PSIA_NativePlace":"嘻嘻哈哈","id":1,"LS_UserId":1,"PSIA_ICN":"341230199905063610","PSIA_Phone":"","PSIA_SP":"保姆,扫地、抹桌子","PSIA_ServiceType":0,"age":18,"PSIA_ServiceRangeDesc":"北京朝阳","PSIA_MaritalState":"未婚  ","PSIA_CultureLevel":"本科以上","workAge":"1年以内","PSIA_WorkState":"在职"}
     * workExperience : [{"ID":1,"WE_BeginTime":"1490976000","WE_EndTime":"1493568000","WE_PositionName":"保姆","WE_WorkOfCommunity":"华夏小区","WE_WorkBossTel":"12313312312","WE_CheckState":0,"WE_AddTime":"1494113291"},{"ID":2,"WE_BeginTime":"1490976000","WE_EndTime":"1493568000","WE_PositionName":"保姆","WE_WorkOfCommunity":"华夏小区","WE_WorkBossTel":"12313312312","WE_CheckState":0,"WE_AddTime":"1494113291"},{"ID":3,"WE_BeginTime":"1490976000","WE_EndTime":"1493568000","WE_PositionName":"保姆","WE_WorkOfCommunity":"华夏小区","WE_WorkBossTel":"12313312312","WE_CheckState":0,"WE_AddTime":"1494113291"}]
     * serviceProject : [{"ID":0,"SPP_Img":"","SPP_OriginalCost":0,"SPP_Price":0,"SPP_Desc":"","SPP_Name":""}]
     * userComment : [{"userid":1,"uesrname":"嘻嘻哈哈","firstname":"哈哈嘻嘻","commentId":1,"commentTime":"1493952485","commentContent":"干活真不错,good","face":"http://app.hyzsnt.com/Files/1/Avatar/2017429/1_1d30862c-cb92-4bc8-bc34-e041f5b81232.jpg"},{"userid":2,"uesrname":"超导","firstname":"蒲方","commentId":1,"commentTime":"1493952485","commentContent":"干活真不错,good","face":"http://app.hyzsnt.com/Files/2/Avatar/2017424/2_18b66939-383e-43ad-aa00-b8a0cf4ffa21.jpg"}]
     * message : 正常
     * code : 10000
     */

    private PersonalInfoBean personalInfo;
    private String message;
    private String code;
    private List<WorkExperienceBean> workExperience;
    private List<ServiceProjectBean> serviceProject;
    private List<UserCommentBean> userComment;

    public PersonalInfoBean getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfoBean personalInfo) {
        this.personalInfo = personalInfo;
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

    public List<WorkExperienceBean> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(List<WorkExperienceBean> workExperience) {
        this.workExperience = workExperience;
    }

    public List<ServiceProjectBean> getServiceProject() {
        return serviceProject;
    }

    public void setServiceProject(List<ServiceProjectBean> serviceProject) {
        this.serviceProject = serviceProject;
    }

    public List<UserCommentBean> getUserComment() {
        return userComment;
    }

    public void setUserComment(List<UserCommentBean> userComment) {
        this.userComment = userComment;
    }

    public static class PersonalInfoBean implements Serializable{
        /**
         * recommendIndex : 0
         * distance : 0
         * face : http://app.hyzsnt.com/files/avarta/jj.jpg
         * tag : 月嫂,四年经验,全职
         * PSIA_NativePlace : 嘻嘻哈哈
         * id : 1
         * LS_UserId : 1
         * PSIA_ICN : 341230199905063610
         * PSIA_Phone :
         * PSIA_SP : 保姆,扫地、抹桌子
         * PSIA_ServiceType : 0
         * age : 18
         * PSIA_ServiceRangeDesc : 北京朝阳
         * PSIA_MaritalState : 未婚
         * PSIA_CultureLevel : 本科以上
         * workAge : 1年以内
         * PSIA_WorkState : 在职
         */
        private int recommendIndex;
        private String distance;
        private String face;
        private String tag;
        private String PSIA_NativePlace;
        private int id;
        private int LS_UserId;
        private String PSIA_ICN;
        private String PSIA_Phone;
        private String PSIA_SP;
        private int PSIA_ServiceType;
        private int age;
        private String PSIA_ServiceRangeDesc;
        private String PSIA_MaritalState;
        private String PSIA_CultureLevel;
        private String workAge;
        private String PSIA_WorkState;
        private String name;
        private String msalary;

        public int getRecommendIndex() {
            return recommendIndex;
        }

        public void setRecommendIndex(int recommendIndex) {
            this.recommendIndex = recommendIndex;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getPSIA_NativePlace() {
            return PSIA_NativePlace;
        }

        public void setPSIA_NativePlace(String PSIA_NativePlace) {
            this.PSIA_NativePlace = PSIA_NativePlace;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLS_UserId() {
            return LS_UserId;
        }

        public void setLS_UserId(int LS_UserId) {
            this.LS_UserId = LS_UserId;
        }

        public String getPSIA_ICN() {
            return PSIA_ICN;
        }

        public void setPSIA_ICN(String PSIA_ICN) {
            this.PSIA_ICN = PSIA_ICN;
        }

        public String getPSIA_Phone() {
            return PSIA_Phone;
        }

        public void setPSIA_Phone(String PSIA_Phone) {
            this.PSIA_Phone = PSIA_Phone;
        }

        public String getPSIA_SP() {
            return PSIA_SP;
        }

        public void setPSIA_SP(String PSIA_SP) {
            this.PSIA_SP = PSIA_SP;
        }

        public int getPSIA_ServiceType() {
            return PSIA_ServiceType;
        }

        public void setPSIA_ServiceType(int PSIA_ServiceType) {
            this.PSIA_ServiceType = PSIA_ServiceType;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getPSIA_ServiceRangeDesc() {
            return PSIA_ServiceRangeDesc;
        }

        public void setPSIA_ServiceRangeDesc(String PSIA_ServiceRangeDesc) {
            this.PSIA_ServiceRangeDesc = PSIA_ServiceRangeDesc;
        }

        public String getPSIA_MaritalState() {
            return PSIA_MaritalState;
        }

        public void setPSIA_MaritalState(String PSIA_MaritalState) {
            this.PSIA_MaritalState = PSIA_MaritalState;
        }

        public String getPSIA_CultureLevel() {
            return PSIA_CultureLevel;
        }

        public void setPSIA_CultureLevel(String PSIA_CultureLevel) {
            this.PSIA_CultureLevel = PSIA_CultureLevel;
        }

        public String getWorkAge() {
            return workAge;
        }

        public void setWorkAge(String workAge) {
            this.workAge = workAge;
        }

        public String getPSIA_WorkState() {
            return PSIA_WorkState;
        }

        public void setPSIA_WorkState(String PSIA_WorkState) {
            this.PSIA_WorkState = PSIA_WorkState;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMsalary() {
            return msalary;
        }

        public void setMsalary(String msalary) {
            this.msalary = msalary;
        }
    }

    public static class WorkExperienceBean {
        /**
         * ID : 1
         * WE_BeginTime : 1490976000
         * WE_EndTime : 1493568000
         * WE_PositionName : 保姆
         * WE_WorkOfCommunity : 华夏小区
         * WE_WorkBossTel : 12313312312
         * WE_CheckState : 0
         * WE_AddTime : 1494113291
         */

        private int ID;
        private String WE_BeginTime;
        private String WE_EndTime;
        private String WE_PositionName;
        private String WE_WorkOfCommunity;
        private String WE_WorkBossTel;
        private int WE_CheckState;
        private String WE_AddTime;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getWE_BeginTime() {
            return WE_BeginTime;
        }

        public void setWE_BeginTime(String WE_BeginTime) {
            this.WE_BeginTime = WE_BeginTime;
        }

        public String getWE_EndTime() {
            return WE_EndTime;
        }

        public void setWE_EndTime(String WE_EndTime) {
            this.WE_EndTime = WE_EndTime;
        }

        public String getWE_PositionName() {
            return WE_PositionName;
        }

        public void setWE_PositionName(String WE_PositionName) {
            this.WE_PositionName = WE_PositionName;
        }

        public String getWE_WorkOfCommunity() {
            return WE_WorkOfCommunity;
        }

        public void setWE_WorkOfCommunity(String WE_WorkOfCommunity) {
            this.WE_WorkOfCommunity = WE_WorkOfCommunity;
        }

        public String getWE_WorkBossTel() {
            return WE_WorkBossTel;
        }

        public void setWE_WorkBossTel(String WE_WorkBossTel) {
            this.WE_WorkBossTel = WE_WorkBossTel;
        }

        public int getWE_CheckState() {
            return WE_CheckState;
        }

        public void setWE_CheckState(int WE_CheckState) {
            this.WE_CheckState = WE_CheckState;
        }

        public String getWE_AddTime() {
            return WE_AddTime;
        }

        public void setWE_AddTime(String WE_AddTime) {
            this.WE_AddTime = WE_AddTime;
        }
    }

    public static class ServiceProjectBean {
        /**
         * ID : 0
         * SPP_Img :
         * SPP_OriginalCost : 0
         * SPP_Price : 0
         * SPP_Desc :
         * SPP_Name :
         */

        private int ID;
        private String SPP_Img;
        private int SPP_OriginalCost;
        private int SPP_Price;
        private String SPP_Desc;
        private String SPP_Name;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getSPP_Img() {
            return SPP_Img;
        }

        public void setSPP_Img(String SPP_Img) {
            this.SPP_Img = SPP_Img;
        }

        public int getSPP_OriginalCost() {
            return SPP_OriginalCost;
        }

        public void setSPP_OriginalCost(int SPP_OriginalCost) {
            this.SPP_OriginalCost = SPP_OriginalCost;
        }

        public int getSPP_Price() {
            return SPP_Price;
        }

        public void setSPP_Price(int SPP_Price) {
            this.SPP_Price = SPP_Price;
        }

        public String getSPP_Desc() {
            return SPP_Desc;
        }

        public void setSPP_Desc(String SPP_Desc) {
            this.SPP_Desc = SPP_Desc;
        }

        public String getSPP_Name() {
            return SPP_Name;
        }

        public void setSPP_Name(String SPP_Name) {
            this.SPP_Name = SPP_Name;
        }
    }

    public static class UserCommentBean {
        /**
         * userid : 1
         * uesrname : 嘻嘻哈哈
         * firstname : 哈哈嘻嘻
         * commentId : 1
         * commentTime : 1493952485
         * commentContent : 干活真不错,good
         * face : http://app.hyzsnt.com/Files/1/Avatar/2017429/1_1d30862c-cb92-4bc8-bc34-e041f5b81232.jpg
         */

        private int userid;
        private String uesrname;
        private String firstname;
        private int commentId;
        private String commentTime;
        private String commentContent;
        private String face;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUesrname() {
            return uesrname;
        }

        public void setUesrname(String uesrname) {
            this.uesrname = uesrname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
