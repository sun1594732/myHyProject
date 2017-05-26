package com.hyzs.onekeyhelp.lifehelp.bean;

import java.util.List;

/**
 * Created by hyzs123 on 2017/3/29.
 */

public class HelpOtherDetailsHelMeBean {


    /**
     * mySeekHelpMoneyAllotList : [{"helpID":546,"seekID":18,"userid":747,"uesrname":"Uyyyu","face":"","RNA":1,"NCCS":1,"helpState":"已帮助","Help_WCRQ":"2017-03-28 ,9/5633710139180658127.jpg","RNA":1,"NCCS":1,"helpState":"已帮助","Help_WCRQ":"2017-03-28 15:29:35为您提供了帮助","AutoAllotMoney":0},{"helpID":96,"seekID":187-03-28 15:29:35为您提供了帮助","AutoAllotMoney":0},{"helpID":82,"seekID":18,"userid":162,"uesrname":"孙伟东162","face":"http://img.zglzx.com/default/2016/0415/t013ce300916004b321.jpg","RNA":1,"NCCS":1,"helpState":"已帮助","Help_WCRQ":"2017-03-28 15:29:35为您提供了帮助","AutoAllot
     * total : 533
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<MySeekHelpMoneyAllotListBean> mySeekHelpMoneyAllotList;

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

    public List<MySeekHelpMoneyAllotListBean> getMySeekHelpMoneyAllotList() {
        return mySeekHelpMoneyAllotList;
    }

    public void setMySeekHelpMoneyAllotList(List<MySeekHelpMoneyAllotListBean> mySeekHelpMoneyAllotList) {
        this.mySeekHelpMoneyAllotList = mySeekHelpMoneyAllotList;
    }

    public static class MySeekHelpMoneyAllotListBean {
        /**
         * helpID : 546
         * seekID : 18
         * userid : 747
         * uesrname : Uyyyu
         * face :
         * RNA : 1
         * NCCS : 1
         * helpState : 已帮助
         * Help_WCRQ : 2017-03-28 15:29:35为您提供了帮助
         * AutoAllotMoney : 0
         */

        private int helpID;
        private int seekID;
        private int userid;
        private String uesrname;
        private String face;
        private int RNA;
        private int NCCS;
        private String helpState;
        private String Help_WCRQ;
        private int AutoAllotMoney;

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

        public int getAutoAllotMoney() {
            return AutoAllotMoney;
        }

        public void setAutoAllotMoney(int AutoAllotMoney) {
            this.AutoAllotMoney = AutoAllotMoney;
        }
    }
}
