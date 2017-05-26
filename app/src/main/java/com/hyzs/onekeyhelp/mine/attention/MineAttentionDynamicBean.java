package com.hyzs.onekeyhelp.mine.attention;

import java.util.List;

/**
 * Created by wubin on 2017/5/19.
 */

public class MineAttentionDynamicBean {

    /**
     * myFavoritesList : [{"userid":4,"face":"头像","uesrname":"张三","commounity":"小区","t_ID":4,"t_TypeID":"1","t_TypeName":"活动","t_AffixImgList":"{http://www.ss.com/ss.jpg},{http://www.ss.com/ss.jpg}","t_Voice":"语音","t_Content":"集合，坐火车去泰安爬山","t_DateTime":"1493011086","m_ID":"2","m_TargetType":"2","m_AddTime":"2","m_Type":"2"}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<MyFavoritesListBean> myFavoritesList;

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

    public List<MyFavoritesListBean> getMyFavoritesList() {
        return myFavoritesList;
    }

    public void setMyFavoritesList(List<MyFavoritesListBean> myFavoritesList) {
        this.myFavoritesList = myFavoritesList;
    }

    public static class MyFavoritesListBean {
        /**
         * userid : 4
         * face : 头像
         * uesrname : 张三
         * commounity : 小区
         * t_ID : 4
         * t_TypeID : 1
         * t_TypeName : 活动
         * t_AffixImgList : {http://www.ss.com/ss.jpg},{http://www.ss.com/ss.jpg}
         * t_Voice : 语音
         * t_Content : 集合，坐火车去泰安爬山
         * t_DateTime : 1493011086
         * m_ID : 2
         * m_TargetType : 2
         * m_AddTime : 2
         * m_Type : 2
         */

        private int userid;
        private String face;
        private String uesrname;
        private String commounity;
        private int t_ID;
        private int newInfoCount;
        private String t_TypeID;
        private String t_TypeName;
        private String t_AffixImgList;
        private String t_Voice;
        private String t_Content;
        private String t_DateTime;
        private String m_ID;
        private String m_TargetType;
        private String m_AddTime;
        private String m_Type;

        public int getNewInfoCount() {
            return newInfoCount;
        }

        public void setNewInfoCount(int newInfoCount) {
            this.newInfoCount = newInfoCount;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
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

        public String getCommounity() {
            return commounity;
        }

        public void setCommounity(String commounity) {
            this.commounity = commounity;
        }

        public int getT_ID() {
            return t_ID;
        }

        public void setT_ID(int t_ID) {
            this.t_ID = t_ID;
        }

        public String getT_TypeID() {
            return t_TypeID;
        }

        public void setT_TypeID(String t_TypeID) {
            this.t_TypeID = t_TypeID;
        }

        public String getT_TypeName() {
            return t_TypeName;
        }

        public void setT_TypeName(String t_TypeName) {
            this.t_TypeName = t_TypeName;
        }

        public String getT_AffixImgList() {
            return t_AffixImgList;
        }

        public void setT_AffixImgList(String t_AffixImgList) {
            this.t_AffixImgList = t_AffixImgList;
        }

        public String getT_Voice() {
            return t_Voice;
        }

        public void setT_Voice(String t_Voice) {
            this.t_Voice = t_Voice;
        }

        public String getT_Content() {
            return t_Content;
        }

        public void setT_Content(String t_Content) {
            this.t_Content = t_Content;
        }

        public String getT_DateTime() {
            return t_DateTime;
        }

        public void setT_DateTime(String t_DateTime) {
            this.t_DateTime = t_DateTime;
        }

        public String getM_ID() {
            return m_ID;
        }

        public void setM_ID(String m_ID) {
            this.m_ID = m_ID;
        }

        public String getM_TargetType() {
            return m_TargetType;
        }

        public void setM_TargetType(String m_TargetType) {
            this.m_TargetType = m_TargetType;
        }

        public String getM_AddTime() {
            return m_AddTime;
        }

        public void setM_AddTime(String m_AddTime) {
            this.m_AddTime = m_AddTime;
        }

        public String getM_Type() {
            return m_Type;
        }

        public void setM_Type(String m_Type) {
            this.m_Type = m_Type;
        }
    }
}
