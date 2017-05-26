package com.hyzs.onekeyhelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;

public class IntentGroupChatActivity extends AppCompatActivity implements EaseChatFragment.EaseChatFragmentHelper {
    private static final String TAG = "IntentGroupChatActivity";
    MySharedPreferences mySharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_chat_layout);
        mySharedPreferences = MySharedPreferences.getInstance(this);
        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        args.putString(EaseConstant.EXTRA_USER_ID,getIntent().getStringExtra("groupId") );
        args.putString("userName",getIntent().getStringExtra("groupName"));
        chatFragment.setArguments(args);
        chatFragment.setChatFragmentListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.chat_fram, chatFragment).commit();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("IntentChatActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("IntentChatActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    /**
     * 设置消息属性
     * @param message
     */
    @Override
    public void onSetMessageAttributes(EMMessage message) {
    }

    /**
     * 进入聊天细节
     */
    @Override
    public void onEnterToChatDetails() {
        Toast.makeText(this, "群组详情", Toast.LENGTH_SHORT).show();
    }

    /**
     * 点击头像
     * @param username
     */
    @Override
    public void onAvatarClick(String username) {
        if (!MySharedPreferences.isLogin(this)) {
            ToastUtils.showShort(this, "请先登录.");
            return;
        }
        Intent intent = new Intent(this, MineHomeActivity.class);
        intent.putExtra("targetUserId", username);
        startActivity(intent);
    }

    /**
     * 长按头像
     * @param username
     */
    @Override
    public void onAvatarLongClick(String username) {
//        Toast.makeText(this, "长按头像", Toast.LENGTH_SHORT).show();
    }

    /**
     * 点击气泡
     * @param message
     * @return
     */
    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    /**
     * 长按气泡
     * @param message
     */
    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    /**
     * 扩展菜单项点击,如果你想覆盖,返回true
     * 设置拓展消息点击事件
     * @param itemId
     * @param view
     * @return
     */
    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    /**
     * 设置自定义的聊天行提供者
     * @return
     */
    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }
}