package com.hyzs.onekeyhelp.help.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/4.
 */

public class BegDetailListBean {

    /**
     * complateSeekHelpUserListCommon : [{"helpID":226,"seekID":135,"userid":549,"uesrname":"东方少男","face":"http://img.zglzxf.com/default/2016/1116/5004600618800457910.png","RNA":1,"NCCS":1,"helpState":"已拒绝","Help_WCRQ":"2017-03-30 16:43:27","gotMoney":21,"gotIntegral":0}]
     * total : 1
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<ComplateSeekHelpUserListCommonBean> complateSeekHelpUserListCommon;

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

    public List<ComplateSeekHelpUserListCommonBean> getComplateSeekHelpUserListCommon() {
        return complateSeekHelpUserListCommon;
    }

    public void setComplateSeekHelpUserListCommon(List<ComplateSeekHelpUserListCommonBean> complateSeekHelpUserListCommon) {
        this.complateSeekHelpUserListCommon = complateSeekHelpUserListCommon;
    }

    public static class ComplateSeekHelpUserListCommonBean {
        /**
         * helpID : 226
         * seekID : 135
         * userid : 549
         * uesrname : 东方少男
         * face : http://img.zglzxf.com/default/2016/1116/5004600618800457910.png
         * RNA : 1
         * NCCS : 1
         * helpState : 已拒绝
         * Help_WCRQ : 2017-03-30 16:43:27
         * gotMoney : 21
         * gotIntegral : 0
         */

        private int helpID;
        private int seekID;
        private int userid;
        private String uesrname;
        private String face;
        private int RNA;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        private String phone;
        private int NCCS;
        private String helpState;
        private String Help_WCRQ;
        private int gotMoney;
        private int gotIntegral;

        public int getHelpID() {
            return helpID;
        }

        public void setHelpID(int helpID) {
            this.helpID = helpID;
        }

        public int getSeekID() {
            return seekID;
        }

        public void setSeekID(int seekID) {
            this.seekID = seekID;
        }

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

        public String getHelpState() {
            return helpState;
        }

        public void setHelpState(String helpState) {
            this.helpState = helpState;
        }

        public String getHelp_WCRQ() {
            return Help_WCRQ;
        }

        public void setHelp_WCRQ(String Help_WCRQ) {
            this.Help_WCRQ = Help_WCRQ;
        }

        public int getGotMoney() {
            return gotMoney;
        }

        public void setGotMoney(int gotMoney) {
            this.gotMoney = gotMoney;
        }

        public int getGotIntegral() {
            return gotIntegral;
        }

        public void setGotIntegral(int gotIntegral) {
            this.gotIntegral = gotIntegral;
        }
    }
}
