package com.hyzs.onekeyhelp.mine.activity.bean;

import java.util.List;

public class ExpenseBean {
    /**
     * accountConsumptionList : [{"id":24,"money":-1.4,"addtime":"1492596203","type":0},{"id":23,"money":-1.5,"addtime":"1492568363","type":0},{"id":22,"money":0,"addtime":"1492504746","type":2},{"id":16,"money":-1.5,"addtime":"1492496575","type":0},{"id":15,"money":-1.3,"addtime":"1492488100","type":0},{"id":14,"money":-1.2,"addtime":"1492487925","type":0},{"id":13,"money":-1.2,"addtime":"1492487066","type":0},{"id":11,"money":-0.6,"addtime":"1492429394","type":0},{"id":10,"money":-0.6,"addtime":"1492425100","type":0},{"id":9,"money":-0.6,"addtime":"1492424791","type":0},{"id":8,"money":-0.5,"addtime":"1492424052","type":0}]
     * total : 11
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<AccountConsumptionListBean> accountConsumptionList;

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

    public List<AccountConsumptionListBean> getAccountConsumptionList() {
        return accountConsumptionList;
    }

    public void setAccountConsumptionList(List<AccountConsumptionListBean> accountConsumptionList) {
        this.accountConsumptionList = accountConsumptionList;
    }

    public static class AccountConsumptionListBean {
        /**
         * id : 24
         * money : -1.4
         * addtime : 1492596203
         * type : 0
         */

        private int id;
        private double money;
        private String addtime;
        private int type;
        private int which;

        public int getWhich() {
            return which;
        }

        public void setWhich(int which) {
            this.which = which;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    /**
     * accountConsumptionList : [{"id":3,"money":0,"addtime":"1492421765","type":0}]
     * total : 7
     * message : 正常
     * code : 10000
     */

}
