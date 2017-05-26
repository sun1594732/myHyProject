package com.hyzs.onekeyhelp.family.circle.bean;

import java.util.List;

/**
 * Created by wubin on 2017/3/29.
 */

public class CircleTypeBean {

    /**
     * circleTypes : [{"ID":1,"CircleName":"兴趣圈"},{"ID":2,"CircleName":"家庭圈"},{"ID":6,"CircleName":"医疗圈"},{"ID":3,"CircleName":"社区圈"},{"ID":4,"CircleName":"救援圈"},{"ID":5,"CircleName":"宠物圈"},{"ID":7,"CircleName":"监护圈"},{"ID":8,"CircleName":"服务圈"},{"ID":9,"CircleName":"旅游圈"},{"ID":10,"CircleName":"购物圈"},{"ID":11,"CircleName":"拼车搭伴"}]
     * total : 11
     * message : 正常
     * code : 10000
     */

    private int total;
    private String message;
    private String code;
    private List<CircleTypesBean> circleTypes;

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

    public List<CircleTypesBean> getCircleTypes() {
        return circleTypes;
    }

    public void setCircleTypes(List<CircleTypesBean> circleTypes) {
        this.circleTypes = circleTypes;
    }

    public static class CircleTypesBean {
        /**
         * ID : 1
         * CircleName : 兴趣圈
         */

        private int ID;
        private String CircleName;
        private String logo;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getCircleName() {
            return CircleName;
        }

        public void setCircleName(String CircleName) {
            this.CircleName = CircleName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
