package com.hyzs.onekeyhelp.lifehelp.bean;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/19.
 */

public class LifeHelpMeBean  {


    /**
     * mySeekHelpList : [{"Seek_ID":20,"Seek_UserID":179,"Seek_Text":"测试生活求助文本内容","Seek_Voice":"","Seek_AffixImgList":"{http://192.168.1.186:803/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a655.jpg},{http://192.168.1.186:803/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a656.jpg}","Seek_Time":"1489817996","Seek_State":4,"HelpCount":"3","CommunityName":"","GrassrootsHero":"","uesrname":"娃哈哈","firstname":"宁夏","face":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","RNA":1,"NCCS":1}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<MySeekHelpListBean> mySeekHelpList;

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

    public List<MySeekHelpListBean> getMySeekHelpList() {
        return mySeekHelpList;
    }

    public void setMySeekHelpList(List<MySeekHelpListBean> mySeekHelpList) {
        this.mySeekHelpList = mySeekHelpList;
    }

    public static class MySeekHelpListBean {
        /**
         * Seek_ID : 20
         * Seek_UserID : 179
         * Seek_Text : 测试生活求助文本内容
         * Seek_Voice :
         * Seek_AffixImgList : {http://192.168.1.186:803/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a655.jpg},{http://192.168.1.186:803/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a656.jpg}
         * Seek_Time : 1489817996
         * Seek_State : 4
         * HelpCount : 3
         * CommunityName :
         * GrassrootsHero :
         * uesrname : 娃哈哈
         * firstname : 宁夏
         * face : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * RNA : 1
         * NCCS : 1
         */

        private int Seek_ID;
        private int Seek_UserID;
        private String Seek_Text;
        private String Seek_Voice;
        private String Seek_AffixImgList;
        private long Seek_Time;
        private int Seek_State;
        private String HelpCount;
        private String CommunityName;
        private String GrassrootsHero;
        private String uesrname;
        private String firstname;
        private String face;
        private int RNA;
        private int NCCS;

        public int getSeek_ID() {
            return Seek_ID;
        }

        public void setSeek_ID(int Seek_ID) {
            this.Seek_ID = Seek_ID;
        }

        public int getSeek_UserID() {
            return Seek_UserID;
        }

        public void setSeek_UserID(int Seek_UserID) {
            this.Seek_UserID = Seek_UserID;
        }

        public String getSeek_Text() {
            return Seek_Text;
        }

        public void setSeek_Text(String Seek_Text) {
            this.Seek_Text = Seek_Text;
        }

        public String getSeek_Voice() {
            return Seek_Voice;
        }

        public void setSeek_Voice(String Seek_Voice) {
            this.Seek_Voice = Seek_Voice;
        }

        public String getSeek_AffixImgList() {
            return Seek_AffixImgList;
        }

        public void setSeek_AffixImgList(String Seek_AffixImgList) {
            this.Seek_AffixImgList = Seek_AffixImgList;
        }

        public long getSeek_Time() {
            return Seek_Time;
        }

        public void setSeek_Time(long Seek_Time) {
            this.Seek_Time = Seek_Time;
        }

        public int getSeek_State() {
            return Seek_State;
        }

        public void setSeek_State(int Seek_State) {
            this.Seek_State = Seek_State;
        }

        public String getHelpCount() {
            return HelpCount;
        }

        public void setHelpCount(String HelpCount) {
            this.HelpCount = HelpCount;
        }

        public String getCommunityName() {
            return CommunityName;
        }

        public void setCommunityName(String CommunityName) {
            this.CommunityName = CommunityName;
        }

        public String getGrassrootsHero() {
            return GrassrootsHero;
        }

        public void setGrassrootsHero(String GrassrootsHero) {
            this.GrassrootsHero = GrassrootsHero;
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

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
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
    }
}
