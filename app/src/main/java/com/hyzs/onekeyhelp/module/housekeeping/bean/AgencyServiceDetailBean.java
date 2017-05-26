package com.hyzs.onekeyhelp.module.housekeeping.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/7.
 */

public class AgencyServiceDetailBean {
    /**
     * basicInfo : {"id":2,"userid":1,"logo":"http://app.hyzsnt.com/file/img/ss.jpg","name":"嘻嘻哈哈机构","intro":"嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构凑够很多字，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构","tel":"010-055555","address":"北京市朝阳区xxxxxxx","distance":"0","publishTimeTimestamp":1494116556}
     * orgInfo : {"OSIA_BusinessLicense":"12342132121","OSIA_AffiList":"{http://app.hyzsnt.com/file/img/ss2.jpg},{http://app.hyzsnt.com/file/img/ss.jpg}","OSIA_SetUpDate":"1483200000","OSIA_SP":"保姆,扫地、抹桌子","OSIA_Desc":"北京市朝阳区xxxxxxx"}
     * serviceProject : [{"ID":0,"SPP_Img":"","SPP_OriginalCost":0,"SPP_Price":0,"SPP_Desc":"","SPP_Name":""}]
     * userComment : [{"userid":1,"uesrname":"嘻嘻哈哈","firstname":"哈哈嘻嘻","commentId":1,"commentTime":"1493952485","commentContent":"干活真不错,good","face":"http://app.hyzsnt.com/Files/1/Avatar/2017429/1_1d30862c-cb92-4bc8-bc34-e041f5b81232.jpg"},{"userid":2,"uesrname":"超导","firstname":"蒲方","commentId":1,"commentTime":"1493952485","commentContent":"干活真不错,good","face":"http://app.hyzsnt.com/Files/2/Avatar/2017424/2_18b66939-383e-43ad-aa00-b8a0cf4ffa21.jpg"}]
     * message : 正常
     * code : 10000
     */

    private BasicInfoBean basicInfo;
    private OrgInfoBean orgInfo;
    private String message;
    private String code;
    private List<ServiceProjectBean> serviceProject;
    private List<UserCommentBean> userComment;

    public BasicInfoBean getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfoBean basicInfo) {
        this.basicInfo = basicInfo;
    }

    public OrgInfoBean getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(OrgInfoBean orgInfo) {
        this.orgInfo = orgInfo;
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

    public static class BasicInfoBean implements Serializable{
        /**
         * id : 2
         * userid : 1
         * logo : http://app.hyzsnt.com/file/img/ss.jpg
         * name : 嘻嘻哈哈机构
         * intro : 嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构凑够很多字，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构，嘻嘻哈哈机构是专用的保姆机构
         * tel : 010-055555
         * address : 北京市朝阳区xxxxxxx
         * distance : 0
         * publishTimeTimestamp : 1494116556
         */

        private int id;
        private int userid;
        private String logo;
        private String name;
        private String intro;
        private String tel;
        private String address;
        private String distance;
        private int publishTimeTimestamp;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getPublishTimeTimestamp() {
            return publishTimeTimestamp;
        }

        public void setPublishTimeTimestamp(int publishTimeTimestamp) {
            this.publishTimeTimestamp = publishTimeTimestamp;
        }
    }

    public static class OrgInfoBean {
        /**
         * OSIA_BusinessLicense : 12342132121
         * OSIA_AffiList : {http://app.hyzsnt.com/file/img/ss2.jpg},{http://app.hyzsnt.com/file/img/ss.jpg}
         * OSIA_SetUpDate : 1483200000
         * OSIA_SP : 保姆,扫地、抹桌子
         * OSIA_Desc : 北京市朝阳区xxxxxxx
         */

        private String OSIA_BusinessLicense;
        private String OSIA_AffiList;
        private String OSIA_SetUpDate;
        private String OSIA_SP;
        private String OSIA_Desc;

        public String getOSIA_BusinessLicense() {
            return OSIA_BusinessLicense;
        }

        public void setOSIA_BusinessLicense(String OSIA_BusinessLicense) {
            this.OSIA_BusinessLicense = OSIA_BusinessLicense;
        }

        public String getOSIA_AffiList() {
            return OSIA_AffiList;
        }

        public void setOSIA_AffiList(String OSIA_AffiList) {
            this.OSIA_AffiList = OSIA_AffiList;
        }

        public String getOSIA_SetUpDate() {
            return OSIA_SetUpDate;
        }

        public void setOSIA_SetUpDate(String OSIA_SetUpDate) {
            this.OSIA_SetUpDate = OSIA_SetUpDate;
        }

        public String getOSIA_SP() {
            return OSIA_SP;
        }

        public void setOSIA_SP(String OSIA_SP) {
            this.OSIA_SP = OSIA_SP;
        }

        public String getOSIA_Desc() {
            return OSIA_Desc;
        }

        public void setOSIA_Desc(String OSIA_Desc) {
            this.OSIA_Desc = OSIA_Desc;
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
