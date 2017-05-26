package com.hyzs.onekeyhelp.observe;

/**
 * Created by wubin on 2017/4/17.
 */

public interface Watcher {
    //再定义一个用来获取更新信息接收的方法
    public void updateNotify(Content content);
}
