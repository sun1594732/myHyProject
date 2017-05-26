package com.hyzs.onekeyhelp.home.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomePageBean {

    /**
     * communityHero : [{"helpPeople":[{"face":"http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg","seek_UserID":13,"Help_UserID":549}],"id":549,"uesrname":"东方少男","face":"http://img.zglzxf.com/default/2016/1116/5004600618800457910.png","identityMark":"1,2,10,8","RNA":1,"NCCS":1,"personalizedSignature":"","community":"康惠园小区","grassHero":"草根英雄","helpPeopleCount":26,"WatchlistCountToMe":0,"heroPosition":429}]
     * circleHot : [{"id":108,"Circle_Content":"The fact that ","Circle_AffixImgList":"{http://localhost:49902/Files/13/img/13_09b104f8-1632-4392-b0cc-0149e0119cc9.jpg},{http://localhost:49902/Files/13/img/13_46345be5-b768-4af4-a086-c92136a9aa17.jpg},{http://localhost:49902/Files/13/img/13_7ec0ef9c-fb80-48fc-828f-62299dd0e389.jpg},{http://localhost:49902/Files/13/img/13_9b260b38-ff99-4004-aeb3-ac771b8d8339.jpg}","Hot":5,"Circle_Count":11,"uesrname":"律政先锋","face":"http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg","Circle_UserID":13,"RNA":1,"NCCS":1,"identityMark":""}]
     * communityAnnouncement : [{"id":5206,"imgURL":"http://img.zglzxf.com/default/2016/0826/4925883439716297011.jpg","diffTime":"\u201c刚刚\u201d","jwh":"双桥居委会","jwhOfjq":"双桥街区","releaseTime":"1472179432","\u201ctitle\u201d":"\u201d标题标题\u201d"}]
     * message : 正常
     * code : 10000
     */

    private String message;
    private String code;
    private List<CommunityHeroBean> communityHero;
    private List<CircleHotBean> circleHot;
    private List<CommunityAnnouncementBean> communityAnnouncement;

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

    public List<CommunityHeroBean> getCommunityHero() {
        return communityHero;
    }

    public void setCommunityHero(List<CommunityHeroBean> communityHero) {
        this.communityHero = communityHero;
    }

    public List<CircleHotBean> getCircleHot() {
        return circleHot;
    }

    public void setCircleHot(List<CircleHotBean> circleHot) {
        this.circleHot = circleHot;
    }

    public List<CommunityAnnouncementBean> getCommunityAnnouncement() {
        return communityAnnouncement;
    }

    public void setCommunityAnnouncement(List<CommunityAnnouncementBean> communityAnnouncement) {
        this.communityAnnouncement = communityAnnouncement;
    }

    public static class CommunityHeroBean {
        /**
         * helpPeople : [{"face":"http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg","seek_UserID":13,"Help_UserID":549}]
         * id : 549
         * uesrname : 东方少男
         * face : http://img.zglzxf.com/default/2016/1116/5004600618800457910.png
         * identityMark : 1,2,10,8
         * RNA : 1
         * NCCS : 1
         * personalizedSignature :
         * community : 康惠园小区
         * grassHero : 草根英雄
         * helpPeopleCount : 26
         * WatchlistCountToMe : 0
         * heroPosition : 429
         */

        private int id;
        private String uesrname;
        private String face;
        private String identityMark;
        private int RNA;
        private int NCCS;
        private String personalizedSignature;
        private String community;
        private String grassHero;
        private int helpPeopleCount;
        private int WatchlistCountToMe;
        private int heroPosition;
        private List<HelpPeopleBean> helpPeople;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUesrname() {
            return uesrname;
        }

        public void setUesrname(String uesrname) {
            this.uesrname = uesrname;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getIdentityMark() {
            return identityMark;
        }

        public void setIdentityMark(String identityMark) {
            this.identityMark = identityMark;
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

        public String getPersonalizedSignature() {
            return personalizedSignature;
        }

        public void setPersonalizedSignature(String personalizedSignature) {
            this.personalizedSignature = personalizedSignature;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getGrassHero() {
            return grassHero;
        }

        public void setGrassHero(String grassHero) {
            this.grassHero = grassHero;
        }

        public int getHelpPeopleCount() {
            return helpPeopleCount;
        }

        public void setHelpPeopleCount(int helpPeopleCount) {
            this.helpPeopleCount = helpPeopleCount;
        }

        public int getWatchlistCountToMe() {
            return WatchlistCountToMe;
        }

        public void setWatchlistCountToMe(int WatchlistCountToMe) {
            this.WatchlistCountToMe = WatchlistCountToMe;
        }

        public int getHeroPosition() {
            return heroPosition;
        }

        public void setHeroPosition(int heroPosition) {
            this.heroPosition = heroPosition;
        }

        public List<HelpPeopleBean> getHelpPeople() {
            return helpPeople;
        }

        public void setHelpPeople(List<HelpPeopleBean> helpPeople) {
            this.helpPeople = helpPeople;
        }

        public static class HelpPeopleBean {
            /**
             * face : http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg
             * seek_UserID : 13
             * Help_UserID : 549
             */

            private String face;
            private int seek_UserID;
            private int Help_UserID;

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }

            public int getSeek_UserID() {
                return seek_UserID;
            }

            public void setSeek_UserID(int seek_UserID) {
                this.seek_UserID = seek_UserID;
            }

            public int getHelp_UserID() {
                return Help_UserID;
            }

            public void setHelp_UserID(int Help_UserID) {
                this.Help_UserID = Help_UserID;
            }
        }
    }

    public static class CircleHotBean {
        /**
         * id : 108
         * Circle_Content : The fact that
         * Circle_AffixImgList : {http://localhost:49902/Files/13/img/13_09b104f8-1632-4392-b0cc-0149e0119cc9.jpg},{http://localhost:49902/Files/13/img/13_46345be5-b768-4af4-a086-c92136a9aa17.jpg},{http://localhost:49902/Files/13/img/13_7ec0ef9c-fb80-48fc-828f-62299dd0e389.jpg},{http://localhost:49902/Files/13/img/13_9b260b38-ff99-4004-aeb3-ac771b8d8339.jpg}
         * Hot : 5
         * Circle_Count : 11
         * uesrname : 律政先锋
         * face : http://img.zglzxf.com/default/2016/0423/5207452880681096468.jpg
         * Circle_UserID : 13
         * RNA : 1
         * NCCS : 1
         * identityMark :
         */

        private int id;
        private String Circle_Content;
        private String Circle_AffixImgList;
        private int Hot;
        private int Circle_Count;
        private String uesrname;
        private String face;
        private int Circle_UserID;
        private int RNA;
        private int NCCS;
        private String identityMark;
        private String CircleName;

        public String getCircleName() {
            return CircleName;
        }

        public void setCircleName(String circleName) {
            CircleName = circleName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCircle_Content() {
            return Circle_Content;
        }

        public void setCircle_Content(String Circle_Content) {
            this.Circle_Content = Circle_Content;
        }

        public String getCircle_AffixImgList() {
            return Circle_AffixImgList;
        }

        public void setCircle_AffixImgList(String Circle_AffixImgList) {
            this.Circle_AffixImgList = Circle_AffixImgList;
        }

        public int getHot() {
            return Hot;
        }

        public void setHot(int Hot) {
            this.Hot = Hot;
        }

        public int getCircle_Count() {
            return Circle_Count;
        }

        public void setCircle_Count(int Circle_Count) {
            this.Circle_Count = Circle_Count;
        }

        public String getUesrname() {
            return uesrname;
        }

        public void setUesrname(String uesrname) {
            this.uesrname = uesrname;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public int getCircle_UserID() {
            return Circle_UserID;
        }

        public void setCircle_UserID(int Circle_UserID) {
            this.Circle_UserID = Circle_UserID;
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
    }

    public static class CommunityAnnouncementBean {
        /**
         * id : 5206
         * imgURL : http://img.zglzxf.com/default/2016/0826/4925883439716297011.jpg
         * diffTime : “刚刚”
         * jwh : 双桥居委会
         * jwhOfjq : 双桥街区
         * releaseTime : 1472179432
         * “title” : ”标题标题”
         */

        private int id;
        private String imgURL;
        private String diffTime;
        private String jwh;
        private String jwhOfjq;
        private String releaseTime;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgURL() {
            return imgURL;
        }

        public void setImgURL(String imgURL) {
            this.imgURL = imgURL;
        }

        public String getDiffTime() {
            return diffTime;
        }

        public void setDiffTime(String diffTime) {
            this.diffTime = diffTime;
        }

        public String getJwh() {
            return jwh;
        }

        public void setJwh(String jwh) {
            this.jwh = jwh;
        }

        public String getJwhOfjq() {
            return jwhOfjq;
        }

        public void setJwhOfjq(String jwhOfjq) {
            this.jwhOfjq = jwhOfjq;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String title() {
            return title;
        }

        public void title(String title) {
            this.title = title;
        }
    }
}
