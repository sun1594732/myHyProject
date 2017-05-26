package com.hyzs.onekeyhelp.family.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.module.record.bean.RecordLvBean;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MusicPlayerUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.widget.GIFview;

import java.text.SimpleDateFormat;
import java.util.List;

public class FamilyRecordAdapter extends BaseAdapter {
    private MusicPlayerUtils musicPlayerUtils;
    private Context context;
    private List<RecordLvBean.ServiceClassBean> list;
    private List<RecordLvBean.UserinfosBean> userInfoList;
    private final int MYRECORD = 0, OTHERRECORD = 1;

    public FamilyRecordAdapter(Context context, List<RecordLvBean.ServiceClassBean> list, List<RecordLvBean.UserinfosBean> userInfoList) {
        this.context = context;
        this.list = list;
        musicPlayerUtils = new MusicPlayerUtils(context);
        this.userInfoList = userInfoList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (MySharedPreferences.getUserId(context).equals(list.get(position).getVA_UserID() + "")) {
            return MYRECORD;
        }
        return OTHERRECORD;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderLeft holderLeft;
        final ViewHolderRight holderRight;
        int layoutType = getItemViewType(position);
        if (layoutType == MYRECORD) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_record_lv, null);
                holderRight = new ViewHolderRight();
                holderRight.icon = (ImageView) convertView.findViewById(R.id.item_record_lv_icon);
                holderRight.audioArea = (RelativeLayout) convertView.findViewById(R.id.item_record_lv_audio);
                holderRight.time = (TextView) convertView.findViewById(R.id.item_record_lv_time);
                holderRight.totalTime = (TextView) convertView.findViewById(R.id.item_record_lv_totalTime);
                holderRight.mGif = (GIFview) convertView.findViewById(R.id.item_record_lv_gif);
                convertView.setTag(holderRight);
            } else holderRight = (ViewHolderRight) convertView.getTag();
            final RecordLvBean.ServiceClassBean bean = list.get(position);
            RecordLvBean.UserinfosBean infoList = userInfoList.get(position);
            holderRight.mGif.setMovieResource(R.mipmap.youbofang);
            holderRight.mGif.setPaused(true);
            if (!TextUtils.isEmpty(infoList.getFace())) {
                NetRequest.loadImg(context, infoList.getFace(), holderRight.icon);
            }
            holderRight.totalTime.setText(bean.getVA_Time() + "s");
            holderRight.time.setText(getTime(Long.parseLong(bean.getVA_AddDate())));
            ViewGroup.LayoutParams params = holderRight.audioArea.getLayoutParams();
            if (bean.getVA_Time() < 6) {
                params.width = 200;
            } else if (bean.getVA_Time() > 5 && bean.getVA_Time() < 10) {
                params.width = 300;
            } else if (bean.getVA_Time() > 9 && bean.getVA_Time() < 15) {
                params.width = 400;
            } else params.width = 500;
            holderRight.audioArea.setLayoutParams(params);
            holderRight.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holderRight.audioArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (musicPlayerUtils.mediaPlayer.isPlaying()) {
                        return;
                    }
                    if (!TextUtils.isEmpty(bean.getVA_Path())) {
                        musicPlayerUtils.playUrl(bean.getVA_Path());
                        holderRight.mGif.setPaused(false);
                        musicPlayerUtils.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                holderRight.mGif.setMovieTime(0);
                                holderRight.mGif.setPaused(true);
                            }
                        });
                    }
                }
            });
        } else if (layoutType == OTHERRECORD) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_record_lv_left, null);
                holderLeft = new ViewHolderLeft();
                holderLeft.icon = (ImageView) convertView.findViewById(R.id.item_record_lv_icon);
                holderLeft.audioArea = (RelativeLayout) convertView.findViewById(R.id.item_record_lv_audio);
                holderLeft.time = (TextView) convertView.findViewById(R.id.item_record_lv_time);
                holderLeft.totalTime = (TextView) convertView.findViewById(R.id.item_record_lv_totalTime);
                holderLeft.mGif = (GIFview) convertView.findViewById(R.id.item_record_lv_gif);
                convertView.setTag(holderLeft);
            } else holderLeft = (ViewHolderLeft) convertView.getTag();
            final RecordLvBean.ServiceClassBean bean = list.get(position);
            RecordLvBean.UserinfosBean infoList = userInfoList.get(position);
            holderLeft.mGif.setMovieResource(R.mipmap.zuobofang);
            holderLeft.mGif.setPaused(true);
            if (!TextUtils.isEmpty(infoList.getFace())) {
                NetRequest.loadImg(context, infoList.getFace(), holderLeft.icon);
            }
            holderLeft.totalTime.setText(bean.getVA_Time() + "s");
            holderLeft.time.setText(getTime(Long.parseLong(bean.getVA_AddDate())));
            ViewGroup.LayoutParams params = holderLeft.audioArea.getLayoutParams();
            if (bean.getVA_Time() < 6) {
                params.width = 200;
            } else if (bean.getVA_Time() > 5 && bean.getVA_Time() < 10) {
                params.width = 300;
            } else if (bean.getVA_Time() > 9 && bean.getVA_Time() < 15) {
                params.width = 400;
            } else params.width = 500;
            holderLeft.audioArea.setLayoutParams(params);
            holderLeft.audioArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (musicPlayerUtils.mediaPlayer.isPlaying()) {
                        return;
                    }
                    if (!TextUtils.isEmpty(bean.getVA_Path())) {
                        musicPlayerUtils.playUrl(bean.getVA_Path());
                        holderLeft.mGif.setPaused(false);
                        musicPlayerUtils.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                holderLeft.mGif.setMovieTime(0);
                                holderLeft.mGif.setPaused(true);
                            }
                        });
                    }
                }
            });
        }
        return convertView;
    }

    class ViewHolderLeft {
        TextView totalTime, time;
        ImageView icon;
        RelativeLayout audioArea;
        GIFview mGif;
    }

    class ViewHolderRight {
        TextView totalTime, time;
        ImageView icon;
        RelativeLayout audioArea;
        GIFview mGif;
    }

    private String getTime(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(l * 1000);
    }
}
