package com.hyzs.onekeyhelp;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Pair;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;


public class InfoService extends Service {
    private static final String TAG = "InfoService";
    private Context appContext;
//    protected final static String[] msg_ch = {"发来一条消息", "发来一张图片", "发来一段语音", "发来位置信息", "发来一个视频", "发来一个文件",
//            "%1个联系人发来%2条消息"
//    };
    protected String packageName;
    protected static int notifyID = 0525; // start notification id
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private Intent msgIntent;
    protected long lastNotifiyTime;
    protected AudioManager audioManager;
    protected Vibrator vibrator;
    Ringtone ringtone = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = MyApplication.getAppContext();
        packageName = appContext.getApplicationInfo().packageName;
        audioManager = (AudioManager) appContext.getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) appContext.getSystemService(Context.VIBRATOR_SERVICE);
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(final List<EMMessage> list) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    vibrateAndPlayTone(list.get(list.size() - 1));
                                }
                            }).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent();
                        intent.setAction(MainActivity.ACTION_UPDATEUI);
                        int total = 0;
                        List<EMConversation> msg = loadConversationList();
                        for (int i = 0; i < msg.size(); i++) {
                            if (msg.get(i).getType() != EMConversation.EMConversationType.Chat) {
                                continue;
                            }
                            if (msg.get(i).conversationId().equals("admin")) {
                                continue;
                            }
                            total += msg.get(i).getUnreadMsgCount();
                        }
                        MySharedPreferences.getInstance(MyApplication.getAppContext()).setInt("notificationCount", total);
                        ShortcutBadger.applyCount(MyApplication.getAppContext(), total);
                        intent.putExtra("count",total);
                        //  发送更新radiobutton的广播
                        sendBroadcast(intent);
                        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        mBuilder = new NotificationCompat.Builder(appContext);
                        initBuilder();
                        if (!isAppRunningForeground(appContext)) {
                            msgIntent = appContext.getPackageManager().getLaunchIntentForPackage(packageName);
                            msgIntent.putExtra("from", "notification");
                            msgIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        } else {
                            msgIntent = new Intent(appContext, MainActivity.class);
                            msgIntent.putExtra("from", "notification");
                            msgIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                        PendingIntent pendingIntent = PendingIntent.getActivity(appContext, notifyID, msgIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        mBuilder.setContentIntent(pendingIntent);
                        mNotificationManager.notify(notifyID, mBuilder.build());
                    }
                }).start();
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        });
    }

    private void initBuilder() {
        mBuilder.setContentTitle("收到好友发来的消息")
                .setContentText("点击跳转到好友界面")
                .setTicker("收到新消息")
                .setSmallIcon(appContext.getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);
    }

    /**
     * vibrate and  play tone
     */
    public void vibrateAndPlayTone(EMMessage message) {
        if (message != null) {
            if (EaseCommonUtils.isSilentMessage(message)) {
                return;
            }
        }

        if (System.currentTimeMillis() - lastNotifiyTime < 1000) {
            // received new messages within 2 seconds, skip play ringtone
            return;
        }

        try {
            lastNotifiyTime = System.currentTimeMillis();

            // check if in silent mode
            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                EMLog.e(TAG, "in slient mode now");
                return;
            }
            EaseUI.EaseSettingsProvider settingsProvider = EaseUI.getInstance().getSettingsProvider();
            if (settingsProvider.isMsgVibrateAllowed(message)) {
                long[] pattern = new long[]{0, 180, 80, 120};
                vibrator.vibrate(pattern, -1);
            }

            if (settingsProvider.isMsgSoundAllowed(message)) {
                if (ringtone == null) {
                    Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    ringtone = RingtoneManager.getRingtone(appContext, notificationUri);
                    if (ringtone == null) {
                        EMLog.d(TAG, "cant find ringtone at:" + notificationUri.getPath());
                        return;
                    }
                }

                if (!ringtone.isPlaying()) {
                    String vendor = Build.MANUFACTURER;

                    ringtone.play();
                    // for samsung S3, we meet a bug that the phone will
                    // continue ringtone without stop
                    // so add below special handler to stop it after 3s if
                    // needed
                    if (vendor != null && vendor.toLowerCase().contains("samsung")) {
                        Thread ctlThread = new Thread() {
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                    if (ringtone.isPlaying()) {
                                        ringtone.stop();
                                    }
                                } catch (Exception e) {
                                }
                            }
                        };
                        ctlThread.run();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected List<EMConversation> loadConversationList() {
        List<EMMessage> list = new ArrayList<>();
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    if (conversation.conversationId().equals("zhangzhen123")) {
                        continue;
                    }
                    sortList.add(new Pair<>(conversation.getLastMessage().getMsgTime(), conversation));
                    list.add(conversation.getLastMessage());
                }
            }
        }
        List<EMConversation> list1 = new ArrayList<>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list1.add(sortItem.second);
        }
        return list1;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 判断app是否在前台
     *
     * @param context
     * @return
     */
    public static boolean isAppRunningForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
        if (runningAppProcessInfoList == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessInfoList) {
            if (processInfo.processName.equals(context.getPackageName())
                    && processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
