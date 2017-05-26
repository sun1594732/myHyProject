package com.hyzs.onekeyhelp.contact.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.MyApplication;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.adapter.ContactRecentLvAdapter;
import com.hyzs.onekeyhelp.contact.bean.RecentBatchBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;


public class ContactRecentFragment extends Fragment {
    private static final String TAG = "ContactRecentFragment";
    Context mContext;
    private ListView mLv;
    private List<EMMessage> list;
    private List<RecentBatchBean.UserContactListBatchQueryListBean> userList;
    ContactRecentLvAdapter adapter;
    StringBuilder sb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_contact_recent, null);
        assignView(view);
        initView();
        initListener();
        if (null == sb) {
            return view;
        }
        if (sb.length() == 0) {
            return view;
        }
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setUpView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            setUpView();
        }
        MobclickAgent.onPageStart("ConRecentFragment"); //统计页面，"MainScreen"为页面名称，可自定义

    }


    private void initListener() {
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int totalCount = MySharedPreferences.getInstance(mContext).getInt("notificationCount", 0);
                MySharedPreferences.getInstance(mContext).setString("Img", userList.get(position).getAvatar());
                TextView popCount = (TextView) view.findViewById(R.id.item_contact_recent_qipao);
                int popNumber = Integer.parseInt(popCount.getText().toString());
                if (totalCount != 0) {
                    ShortcutBadger.applyCount(MyApplication.getAppContext(), totalCount - popNumber);
                    MySharedPreferences.getInstance(mContext).setInt("notificationCount", totalCount - popNumber);
                }
                Intent intent = new Intent();
                intent.putExtra("one", userList.get(position).getTargetUserId() + "");
                intent.putExtra("userName", userList.get(position).getUserNick());
                intent.setClass(mContext, IntentChatActivity.class);
                mContext.startActivity(intent);
            }
        });
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                setUpView();
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

    private void initView() {

    }

    private void assignView(View view) {
        mLv = (ListView) view.findViewById(R.id.fragment_contact_recent_lv);

    }


    protected List<EMConversation> conversationList = new ArrayList<>();

    protected void setUpView() {
        conversationList.clear();
        conversationList.addAll(loadConversationList());
        refreshData1();
    }


    protected List<EMConversation> loadConversationList() {
        list = new ArrayList<>();
        sb = new StringBuilder();
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
                    if (conversation.getType() != EMConversation.EMConversationType.Chat) {
                        continue;
                    }

                    if (conversation.conversationId().equals("admin")) {
                        continue;
                    }

                    if (conversation.conversationId().equals("zhangzhen123")) {
                        continue;
                    }
                    sortList.add(new Pair<>(conversation.getLastMessage().getMsgTime(), conversation));
                    list.add(conversation.getLastMessage());
                    sb.append(conversation.conversationId() + ",");
                }
            }
        }
        List<EMConversation> list1 = new ArrayList<>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list1.add(sortItem.second);
        }
        return list1;
    }

    private void refreshData1() {
        if (!MySharedPreferences.isLogin(mContext)) {
            return;
        }
        if (sb.length() == 0) {
            if (userList != null) {
                userList.clear();
                adapter.notifyDataSetChanged();
            }
            return;
        }
        NetRequest.RecentListRequest(MySharedPreferences.getUserId(mContext), sb.deleteCharAt(sb.length() - 1).toString(), new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                userList = new Gson().fromJson(data, RecentBatchBean.class).getUserContactListBatchQueryList();
                sort();
                adapter = new ContactRecentLvAdapter(getActivity(), userList, conversationList);
                mLv.setAdapter(adapter);
            }
        });
    }

    private void sort() {
        if (conversationList == null || conversationList.size() == 0 || userList == null || userList.size() == 0) {
            return;
        }
        Collections.sort(conversationList, new Comparator<EMConversation>() {
            @Override
            public int compare(EMConversation o1, EMConversation o2) {
                long i = o2.getLastMessage().getMsgTime() - o1.getLastMessage().getMsgTime();
                return (int) i;
            }
        });
        sb.delete(0, sb.length());
        for (EMConversation e : conversationList) {
            sb.append(e.getLastMessage().getUserName() + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String[] uIds = sb.toString().split(",");
        List<RecentBatchBean.UserContactListBatchQueryListBean> list = new ArrayList<>();
        for (int i = 0; i < uIds.length; i++) {
            for (RecentBatchBean.UserContactListBatchQueryListBean bean : userList) {
                if (bean.getTargetUserId() == Integer.parseInt(uIds[i])) {
                    list.add(bean);
                    break;
                }
            }
        }
        userList.clear();
        userList.addAll(list);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ConRecentFragment");
    }

}