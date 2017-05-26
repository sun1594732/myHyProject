package com.hyzs.onekeyhelp.contact.bean;

import com.hyphenate.chat.EMMessage;

/**
 * Created by Administrator on 2017/3/17.
 */

public class ChatMessageBean {
    private int uId;
    private EMMessage msg;

    public ChatMessageBean(int uId, EMMessage msg) {
        this.uId = uId;
        this.msg = msg;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public EMMessage getMsg() {
        return msg;
    }

    public void setMsg(EMMessage msg) {
        this.msg = msg;
    }
}
