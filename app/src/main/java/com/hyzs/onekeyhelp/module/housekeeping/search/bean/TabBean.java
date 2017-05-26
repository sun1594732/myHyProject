package com.hyzs.onekeyhelp.module.housekeeping.search.bean;

/**
 * Created by Administrator on 2017/5/10.
 */

public class TabBean {
    private int img;
    private String tab;
    private int isCheck;

    public TabBean(int img, String tab, int isCheck) {
        this.img = img;
        this.tab = tab;
        this.isCheck = isCheck;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public TabBean(int img, String tab) {
        this.img = img;
        this.tab = tab;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }
}
