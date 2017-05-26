package com.hyzs.onekeyhelp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.hyzs.onekeyhelp.carresuce.view.CarResuceActivity;
import com.hyzs.onekeyhelp.contact.activity.ContactSearchActivity;
import com.hyzs.onekeyhelp.help.activity.HelpActivity;
import com.hyzs.onekeyhelp.lifehelp.view.activity.LifeHelpHomeActivity;
import com.hyzs.onekeyhelp.mine.activity.MineBillActivity;
import com.hyzs.onekeyhelp.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

public class MyJPushReceiver extends BroadcastReceiver {
    private static final String TAG = "tag";
    private static JSONObject json;
    private Intent i;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
//        Log.e(TAG, "[MyReceiver] 接收 - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            LogUtils.e("---------------" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            i = new Intent(context, MainActivity.class);
            try {
                json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String str = json.toString().replaceAll("'", "\"");
            str = str.substring(str.lastIndexOf("{"),str.indexOf("}")+1);
            try {
                JSONObject object = new JSONObject(str);
                String type = object.getString("g");
                switch (type) {
                    case "0.1":  //首页
                        i.setClass(context, MainActivity.class);
                        break;
                    case "1.1":
                        i.setClass(context, HelpActivity.class);
                        break;
                    case "1.2":
                        i.setClass(context, HelpActivity.class);
                        break;
                    case "1.3":
                        i.setClass(context, HelpActivity.class);
                        break;
                    case "2.1":
                        i.setClass(context, LifeHelpHomeActivity.class);
                        break;
                    case "2.2":
                        i.setClass(context, LifeHelpHomeActivity.class);
                        break;
                    case "2.3":
                        i.setClass(context, LifeHelpHomeActivity.class);
                        break;
                    case "3.1":
                        i.setClass(context, CarResuceActivity.class);
                        break;
                    case "3.2":
                        i.setClass(context, CarResuceActivity.class);
                        break;
                    case "3.3":
                        i.setClass(context, CarResuceActivity.class);
                        break;
                    case "4.1":
                        i.setClass(context, MainActivity.class);
                        i.putExtra("notification",111+"");
                        break;
                    case "4.2":
                        i.setClass(context, ContactSearchActivity.class);
                        break;
                    case "4.3":
                        i.setClass(context, MainActivity.class);
                        i.putExtra("notification",111+"");
                        break;
                    case "5.1":
                        i.setClass(context, MineBillActivity.class);
                        break;
                    case "5.2":
                        i.setClass(context, MineBillActivity.class);
                        break;
                    case "5.3":
                        i.setClass(context, MineBillActivity.class);
                        break;
                    case "5.4":
                        i.setClass(context, MineBillActivity.class);
                        break;
                }
            } catch (JSONException e) {
                LogUtils.e("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
            }
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                }
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
//        if (MainActivity.isForeground) {
//            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            context.sendBroadcast(msgIntent);
//        }
    }
}
//参数解释：
//        go：参数名
//        {}：参数体（以json格式，可直接转模）
//        t:通知类型
//        g:通知跳转页
//        m:跳转参数
//
//        参数详细解释：
//        通知类型：
//        0.x 首页
//        1.x一键帮助
//        2.x生活求助
//        3.x车辆救援
//        4.x通讯录
//        5.x账户

//        通知跳转页：
//        0.x
//        0.1:跳转至首页
//        1.x
//        1.1:跳转至我的帮助详情
//        1.2:跳转至我的求助详情
//        1.3:跳转至我的帮助详情
//        2.x
//        2.1:跳转至我的帮助详情
//        2.2:跳转至我的求助详情
//        2.3:跳转至我的帮助详情
//        3.x
//        3.1:跳转至我的帮助详情
//        3.2:跳转至我的求助详情
//        3.3:跳转至我的帮助详情
//        4.x
//        4.1:跳转至通讯录好友列表，并定位到此用户
//        4.2:跳转至通讯录添加页面，显示等待验证列表
//        4.3:跳转至通讯录好友列表
//        5.x
//        5.1:跳转至账单》消费记录列表
//        5.2:跳转至账单》充值记录列表
//        5.3:跳转至账单》消费记录列表
//        5.4:跳转至账单》消费记录列表

//        跳转参数：
//        0.x :无
//        1.x
//        1.1:我的帮助ID
//        1.2:我的求助ID
//        1.3:我的帮助ID
//        2.x
//        1.1:我的帮助ID
//        1.2:我的求助ID
//        1.3:我的帮助ID
//        3.x
//        1.1:我的帮助ID
//        1.2:我的求助ID
//        1.3:我的帮助ID
//        4.x
//        4.1:此条通知中提示的新添加的好友id
//        4.2:添加我为好友的用户id
//        4.3:添加我为紧急联系的好友id
//        5.x
//        5.1:消费记录id
//        5.2:充值/提现记录id
//        5.3:消费记录id
//        5.4:消费记录id