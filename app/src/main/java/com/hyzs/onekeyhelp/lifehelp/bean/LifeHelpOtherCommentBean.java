package com.hyzs.onekeyhelp.lifehelp.bean;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/21.
 */

public class LifeHelpOtherCommentBean {


    /**
     * lifeSeekHelpComments : [{"SC_SeekID":18,"Seek_AffixImgList":"","Seek_Voice":"","SC_Type":2,"SC_Count":0,"SC_Content":"thank you ,people","SC_DateTime":"1489646723","SC_State":"2","SC_UserID":179,"face":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","uesrname":"娃哈哈","firstname":"宁夏","Publisher":1},{"SC_SeekID":18,"Seek_AffixImgList":"","Seek_Voice":"","SC_Type":2,"SC_Count":0,"SC_Content":"测试2","SC_DateTime":"1489648588","SC_State":"2","SC_UserID":188,"face":"http://img.zglzxf.com/default/2016/0615/5617480022369682741.png","uesrname":"Immannuel","firstname":"朱月","Publisher":0},{"SC_SeekID":20,"Seek_AffixImgList":"{http://192.168.1.186:803/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a655.jpg},{http://192.168.1.186:803/Files/179/img/179_ab9d5d3f-81d6-44a8-a336-28168ea1a656.jpg}","Seek_Voice":"","SC_Type":2,"SC_Count":0,"SC_Content":"真棒，棒棒哒","SC_DateTime":"1489818548","SC_State":"2","SC_UserID":179,"face":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","uesrname":"娃哈哈","firstname":"宁夏","Publisher":1},{"SC_SeekID":20,"Seek_AffixImgList":"","Seek_Voice":"","SC_Type":2,"SC_Count":0,"SC_Content":"1212","SC_DateTime":"1490093959","SC_State":"2","SC_UserID":179,"face":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","uesrname":"娃哈哈","firstname":"宁夏","Publisher":1},{"SC_SeekID":20,"Seek_AffixImgList":"","Seek_Voice":"","SC_Type":2,"SC_Count":0,"SC_Content":"无敌棒棒糖","SC_DateTime":"1490094352","SC_State":"2","SC_UserID":179,"face":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","uesrname":"娃哈哈","firstname":"宁夏","Publisher":1},{"SC_SeekID":20,"Seek_AffixImgList":"","Seek_Voice":"","SC_Type":2,"SC_Count":0,"SC_Content":"超级无敌棒棒糖！","SC_DateTime":"1490094610","SC_State":"2","SC_UserID":179,"face":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","uesrname":"娃哈哈","firstname":"宁夏","Publisher":1},{"SC_SeekID":20,"Seek_AffixImgList":"","Seek_Voice":"","SC_Type":2,"SC_Count":0,"SC_Content":"6666","SC_DateTime":"1490096818","SC_State":"2","SC_UserID":179,"face":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","uesrname":"娃哈哈","firstname":"宁夏","Publisher":1},{"SC_SeekID":20,"Seek_AffixImgList":"","Seek_Voice":"","SC_Type":2,"SC_Count":0,"SC_Content":"123","SC_DateTime":"1490100730","SC_State":"2","SC_UserID":179,"face":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","uesrname":"娃哈哈","firstname":"宁夏","Publisher":1},{"SC_SeekID":20,"Seek_AffixImgList":"","Seek_Voice":"","SC_Type":2,"SC_Count":0,"SC_Content":"123","SC_DateTime":"1490144974","SC_State":"2","SC_UserID":179,"face":"http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg","uesrname":"娃哈哈","firstname":"宁夏","Publisher":1}]
     * total : 16
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<LifeSeekHelpCommentsBean> lifeSeekHelpComments;

    @Override
    public String toString() {
        return "LifeHelpOtherCommentBean{" +
                "total=" + total +
                ", message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", lifeSeekHelpComments=" + lifeSeekHelpComments +
                '}';
    }

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

    public List<LifeSeekHelpCommentsBean> getLifeSeekHelpComments() {
        return lifeSeekHelpComments;
    }

    public void setLifeSeekHelpComments(List<LifeSeekHelpCommentsBean> lifeSeekHelpComments) {
        this.lifeSeekHelpComments = lifeSeekHelpComments;
    }

    public static class LifeSeekHelpCommentsBean {
        /**
         * SC_SeekID : 18
         * Seek_AffixImgList :
         * Seek_Voice :
         * SC_Type : 2
         * SC_Count : 0
         * SC_Content : thank you ,people
         * SC_DateTime : 1489646723
         * SC_State : 2
         * SC_UserID : 179
         * face : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
         * uesrname : 娃哈哈
         * firstname : 宁夏
         * Publisher : 1
         */

        private int SC_SeekID;
        private String Seek_AffixImgList;
        private String Seek_Voice;
        private int SC_Type;
        private int SC_Count;
        private String SC_Content;
        private long SC_DateTime;
        private String SC_State;
        private int SC_UserID;
        private String face;
        private String uesrname;
        private String firstname;
        private int Publisher;

        @Override
        public String toString() {
            return "LifeSeekHelpCommentsBean{" +
                    "SC_SeekID=" + SC_SeekID +
                    ", Seek_AffixImgList='" + Seek_AffixImgList + '\'' +
                    ", Seek_Voice='" + Seek_Voice + '\'' +
                    ", SC_Type=" + SC_Type +
                    ", SC_Count=" + SC_Count +
                    ", SC_Content='" + SC_Content + '\'' +
                    ", SC_DateTime=" + SC_DateTime +
                    ", SC_State='" + SC_State + '\'' +
                    ", SC_UserID=" + SC_UserID +
                    ", face='" + face + '\'' +
                    ", uesrname='" + uesrname + '\'' +
                    ", firstname='" + firstname + '\'' +
                    ", Publisher=" + Publisher +
                    '}';
        }

        public int getSC_SeekID() {
            return SC_SeekID;
        }

        public void setSC_SeekID(int SC_SeekID) {
            this.SC_SeekID = SC_SeekID;
        }

        public String getSeek_AffixImgList() {
            return Seek_AffixImgList;
        }

        public void setSeek_AffixImgList(String Seek_AffixImgList) {
            this.Seek_AffixImgList = Seek_AffixImgList;
        }

        public String getSeek_Voice() {
            return Seek_Voice;
        }

        public void setSeek_Voice(String Seek_Voice) {
            this.Seek_Voice = Seek_Voice;
        }

        public int getSC_Type() {
            return SC_Type;
        }

        public void setSC_Type(int SC_Type) {
            this.SC_Type = SC_Type;
        }

        public int getSC_Count() {
            return SC_Count;
        }

        public void setSC_Count(int SC_Count) {
            this.SC_Count = SC_Count;
        }

        public String getSC_Content() {
            return SC_Content;
        }

        public void setSC_Content(String SC_Content) {
            this.SC_Content = SC_Content;
        }

        public long getSC_DateTime() {
            return SC_DateTime;
        }

        public void setSC_DateTime(long SC_DateTime) {
            this.SC_DateTime = SC_DateTime;
        }

        public String getSC_State() {
            return SC_State;
        }

        public void setSC_State(String SC_State) {
            this.SC_State = SC_State;
        }

        public int getSC_UserID() {
            return SC_UserID;
        }

        public void setSC_UserID(int SC_UserID) {
            this.SC_UserID = SC_UserID;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
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

        public int getPublisher() {
            return Publisher;
        }

        public void setPublisher(int Publisher) {
            this.Publisher = Publisher;
        }
    }
}
