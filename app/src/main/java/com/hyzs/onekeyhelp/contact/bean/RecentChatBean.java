package com.hyzs.onekeyhelp.contact.bean;

/**
 * Created by Administrator on 2017/3/17.
 */

public class RecentChatBean {
    private String uId;
    private long time;

    @Override
    public String toString() {
        return "RecentChatBean{" +
                "uId='" + uId + '\'' +
                ", time=" + time +
                '}';
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
