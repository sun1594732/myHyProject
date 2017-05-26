package com.hyzs.onekeyhelp.util;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyzs.onekeyhelp.MyApplication;

import me.leolin.shortcutbadger.ShortcutBadger;


public class NotifyMsgCountUtil {


    public NotifyMsgCountUtil() {
    }

    public static void notifyMsg(String id) {
        MySharedPreferences mySharedPreferences = MySharedPreferences.getInstance(MyApplication.getAppContext());
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(id);
        if (conversation!=null) {
        //   获取环信此用户未读消息并更新未读消息数量
        int totalCount = mySharedPreferences.getInt("notificationCount", 0);
        int count = conversation.getUnreadMsgCount();
        if (count != 0)
        {
            ShortcutBadger.applyCount(MyApplication.getAppContext(), totalCount - count);
            mySharedPreferences.setInt("notificationCount", totalCount - count);
        }
        }
    }
}
