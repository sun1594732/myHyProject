package com.hyzs.onekeyhelp.contact.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.bean.RecentBatchBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactRecentLvAdapter extends BaseAdapter {
    private Context context;
    private List<RecentBatchBean.UserContactListBatchQueryListBean> msgList;
    List<EMConversation> list;
    private MySharedPreferences mySharedPreferences;

    public ContactRecentLvAdapter(Context context, List<RecentBatchBean.UserContactListBatchQueryListBean> msgList,List<EMConversation> list) {
        this.context = context;
        this.msgList = msgList;
        this.list=list;
        mySharedPreferences = MySharedPreferences.getInstance(context);
    }

    @Override
    public int getCount() {
        return msgList.size();
    }

    @Override
    public RecentBatchBean.UserContactListBatchQueryListBean getItem(int position) {
        return msgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private EaseConversationList.EaseConversationListHelper cvsListHelper;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_contact_recent, null);
            holder = new ViewHolder();
            holder.image = (CircleImageView) view.findViewById(R.id.item_contact_recent_icon);
            holder.userName = (TextView) view.findViewById(R.id.item_contact_recent_userName);
            holder.time = (TextView) view.findViewById(R.id.item_contact_recent_time);
            holder.initiatedChat = (ImageView) view.findViewById(R.id.item_contact_recent_initiated_chat);
            holder.message = (TextView) view.findViewById(R.id.item_contact_recent_message);
            holder.qipao = (TextView) view.findViewById(R.id.item_contact_recent_qipao);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        final RecentBatchBean.UserContactListBatchQueryListBean bean = msgList.get(position);
        if (TextUtils.isEmpty(bean.getAvatar())) {
            holder.image.setImageResource(R.mipmap.icon_replace);
        }else{
            NetRequest.loadImg(context,bean.getAvatar(),holder.image);
        }
        final EMConversation conversation = list.get(position);
        holder.userName.setText(bean.getUserNick());
        if (0 == conversation.getUnreadMsgCount()) {
            holder.qipao.setVisibility(View.INVISIBLE);
        }else{
            holder.qipao.setText(conversation.getUnreadMsgCount()+"");
            holder.qipao.setVisibility(View.VISIBLE);
        }
        if (conversation.getAllMsgCount() != 0) {
            // show the content of latest message
            EMMessage lastMessage = conversation.getLastMessage();
            String content = null;
            if(cvsListHelper != null){
                content = cvsListHelper.onSetItemSecondaryText(lastMessage);
            }
            holder.message.setText(EaseSmileUtils.getSmiledText(context, EaseCommonUtils.getMessageDigest(lastMessage, (context))),
                    TextView.BufferType.SPANNABLE);
            if(content != null){
                holder.message.setText(content);
            }
            holder.time.setText(handleTime(lastMessage.getMsgTime()));

        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MineHomeActivity.class);
                intent.putExtra("targetUserId",bean.getTargetUserId()+"");
                context.startActivity(intent);
            }
        });
        holder.initiatedChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifyMsgCountUtil.notifyMsg(bean.getTargetUserId()+"");
                mySharedPreferences.setString("Img",bean.getAvatar());
                Intent intent = new Intent();
                intent.putExtra("one", bean.getTargetUserId() + "");
                intent.putExtra("userName", bean.getUserNick());
                intent.setClass(context, IntentChatActivity.class);
                context.startActivity(intent);
            }
        });
        return view;
    }

    private String toTime(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(l);
        return sdf.format(date);
    }

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    private static String formatDateTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return "今天 " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {

            return "昨天 " + time.split(" ")[1];
        } else {
            int index = time.indexOf("-") + 1;
            return time.substring(index, time.length());
        }
    }

    private String handleTime(long circle_dateTime) {
        long minute = (System.currentTimeMillis() - circle_dateTime) / 60000;
        if (minute > 60 && minute < 1440) {
            return minute / 60 + "小时前";
        } else if (minute > 1440) {
            return minute / 1440 + "天前";
        } else
            return minute + "分钟前";
    }


    class ViewHolder {
        CircleImageView image;
        TextView userName, message, time,qipao;
        ImageView initiatedChat;
    }
}
