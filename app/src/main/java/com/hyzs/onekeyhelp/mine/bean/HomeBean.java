package com.hyzs.onekeyhelp.mine.bean;

import java.util.List;

/**
 * Created by wubin on 2017/3/25.
 */

public class HomeBean {

    /**
     * helpPeploes : [{"uid":179,"avatar":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg"}]
     * albums : [{"photoId":1,"photoUrl":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg"}]
     * circle : [{"id":110,"Circle_UserID":179,"Circle_Type":1,"Circle_Content":"34","Circle_DateTime":"1490346939","CircleName":"兴趣"},{"id":106,"Circle_UserID":179,"Circle_Type":1,"Circle_Content":"223","Circle_DateTime":"1490256954","CircleName":"兴趣"},{"id":103,"Circle_UserID":179,"Circle_Type":1,"Circle_Content":"4546","Circle_DateTime":"1490235418","CircleName":"兴趣"}]
     * identityMark : 10,2,3,1
     * community : 康惠园小区
     * eventDynamic : [{"commentID":29,"eventID":15,"ED_Content":"不咯血","ER_Address":"长城脚下","ED_Datetime":"1490343552"},{"commentID":28,"eventID":15,"ED_Content":"1234564","ER_Address":"长城脚下","ED_Datetime":"1490271754"},{"commentID":27,"eventID":15,"ED_Content":"差室内设计","ER_Address":"长城脚下","ED_Datetime":"1490271709"}]
     * uid : 179
     * trueName : 宁夏
     * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
     * sex : 暂无
     * province : 北京市
     * city : 北京市
     * district : 朝阳区
     * personalizedSignature :
     * phone : 15910745659
     * qrCodeUrl :
     * isExists : 未添加
     * RNA : 1
     * NCCS : 1
     * weekRespondHelpTotal : 28
     * heroListPosition : 9999
     * message : 正常
     * code : 10000
     */

    private String identityMark;
    private String watchId;
    private String community;
    private int uid;
    private String trueName;
    private String nickName;
    private String avatar;
    private String sex;
    private String province;
    private String city;
    private String district;
    private String personalizedSignature;
    private String phone;
    private String qrCodeUrl;
    private String isExists;
    private int RNA;
    private int NCCS;
    private int weekRespondHelpTotal;
    private int heroListPosition;
    private String message;
    private String code;
    private List<HelpPeploesBean> helpPeploes;
    private List<AlbumsBean> albums;
    private List<CircleBean> circle;
    private List<EventDynamicBean> eventDynamic;
    private int isConcern;
    private String grassHero;


    public String getWatchId() {
        return watchId;
    }

    public void setWatchId(String watchId) {
        this.watchId = watchId;
    }

    public String getGrassHero() {
        return grassHero;
    }

    public void setGrassHero(String grassHero) {
        this.grassHero = grassHero;
    }

    public int getIsConcern() {
        return isConcern;
    }

    public void setIsConcern(int isConcern) {
        this.isConcern = isConcern;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getIsExists() {
        return isExists;
    }

    public void setIsExists(String isExists) {
        this.isExists = isExists;
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

    public int getWeekRespondHelpTotal() {
        return weekRespondHelpTotal;
    }

    public void setWeekRespondHelpTotal(int weekRespondHelpTotal) {
        this.weekRespondHelpTotal = weekRespondHelpTotal;
    }

    public int getHeroListPosition() {
        return heroListPosition;
    }

    public void setHeroListPosition(int heroListPosition) {
        this.heroListPosition = heroListPosition;
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

    public List<HelpPeploesBean> getHelpPeploes() {
        return helpPeploes;
    }

    public void setHelpPeploes(List<HelpPeploesBean> helpPeploes) {
        this.helpPeploes = helpPeploes;
    }

    public List<AlbumsBean> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumsBean> albums) {
        this.albums = albums;
    }

    public List<CircleBean> getCircle() {
        return circle;
    }

    public void setCircle(List<CircleBean> circle) {
        this.circle = circle;
    }

    public List<EventDynamicBean> getEventDynamic() {
        return eventDynamic;
    }

    public void setEventDynamic(List<EventDynamicBean> eventDynamic) {
        this.eventDynamic = eventDynamic;
    }

    public static class HelpPeploesBean {
        /**
         * uid : 179
         * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         */

        private int uid;
        private String avatar;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class AlbumsBean {
        /**
         * photoId : 1
         * photoUrl : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         */

        private int photoId;
        private String photoUrl;

        public int getPhotoId() {
            return photoId;
        }

        public void setPhotoId(int photoId) {
            this.photoId = photoId;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
    }

    public static class CircleBean {
        /**
         * id : 110
         * Circle_UserID : 179
         * Circle_Type : 1
         * Circle_Content : 34
         * Circle_DateTime : 1490346939
         * CircleName : 兴趣
         */

        private int id;
        private int Circle_UserID;
        private int Circle_Type;
        private String Circle_Content;
        private String Circle_DateTime;
        private String CircleName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCircle_UserID() {
            return Circle_UserID;
        }

        public void setCircle_UserID(int Circle_UserID) {
            this.Circle_UserID = Circle_UserID;
        }

        public int getCircle_Type() {
            return Circle_Type;
        }

        public void setCircle_Type(int Circle_Type) {
            this.Circle_Type = Circle_Type;
        }

        public String getCircle_Content() {
            return Circle_Content;
        }

        public void setCircle_Content(String Circle_Content) {
            this.Circle_Content = Circle_Content;
        }

        public String getCircle_DateTime() {
            return Circle_DateTime;
        }

        public void setCircle_DateTime(String Circle_DateTime) {
            this.Circle_DateTime = Circle_DateTime;
        }

        public String getCircleName() {
            return CircleName;
        }

        public void setCircleName(String CircleName) {
            this.CircleName = CircleName;
        }
    }

    public static class EventDynamicBean {
        /**
         * commentID : 29
         * eventID : 15
         * ED_Content : 不咯血
         * ER_Address : 长城脚下
         * ED_Datetime : 1490343552
         */

        private int commentID;
        private int eventID;
        private String ED_Content;
        private String ER_Address;
        private String ED_Datetime;

        public int getCommentID() {
            return commentID;
        }

        public void setCommentID(int commentID) {
            this.commentID = commentID;
        }

        public int getEventID() {
            return eventID;
        }

        public void setEventID(int eventID) {
            this.eventID = eventID;
        }

        public String getED_Content() {
            return ED_Content;
        }

        public void setED_Content(String ED_Content) {
            this.ED_Content = ED_Content;
        }

        public String getER_Address() {
            return ER_Address;
        }

        public void setER_Address(String ER_Address) {
            this.ER_Address = ER_Address;
        }

        public String getED_Datetime() {
            return ED_Datetime;
        }

        public void setED_Datetime(String ED_Datetime) {
            this.ED_Datetime = ED_Datetime;
        }
    }
}
