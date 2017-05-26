package com.hyzs.onekeyhelp.module.record.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
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

public class HomeRecordLvAdapter extends BaseAdapter {
    private MusicPlayerUtils musicPlayerUtils;
    private Context context;
    private List<RecordLvBean.ServiceClassBean> list;

    public HomeRecordLvAdapter(Context context, List<RecordLvBean.ServiceClassBean> list) {
        this.context = context;
        this.list = list;
        musicPlayerUtils = new MusicPlayerUtils(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_record_lv, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.item_record_lv_icon);
            holder.audioArea = (RelativeLayout) convertView.findViewById(R.id.item_record_lv_audio);
            holder.time = (TextView) convertView.findViewById(R.id.item_record_lv_time);
            holder.totalTime = (TextView) convertView.findViewById(R.id.item_record_lv_totalTime);
            holder.mGif = (GIFview) convertView.findViewById(R.id.item_record_lv_gif);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        final RecordLvBean.ServiceClassBean bean = list.get(position);
        holder.mGif.setMovieResource(R.mipmap.youbofang);
        holder.mGif.setPaused(true);
        if (!TextUtils.isEmpty(MySharedPreferences.getInstance(context).getString("myImg"))) {
            NetRequest.loadImg(context, MySharedPreferences.getInstance(context).getString("myImg"), holder.icon);
        }
        holder.totalTime.setText(bean.getVA_Time() + "s");
        holder.time.setText(getTime(Long.parseLong(bean.getVA_AddDate())));
        ViewGroup.LayoutParams params = holder.audioArea.getLayoutParams();
        if (bean.getVA_Time() < 6) {
            params.width = 200;
        } else if (bean.getVA_Time() > 5 && bean.getVA_Time() < 10) {
            params.width = 300;
        } else if (bean.getVA_Time() > 9 && bean.getVA_Time() < 15) {
            params.width = 400;
        } else params.width = 500;
        holder.audioArea.setLayoutParams(params);
        holder.audioArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicPlayerUtils.mediaPlayer.isPlaying()) {
                    return;
                }
                if (!TextUtils.isEmpty(bean.getVA_Path())) {
                    musicPlayerUtils.playUrl(bean.getVA_Path());
                    holder.mGif.setPaused(false);
                    musicPlayerUtils.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            holder.mGif.setMovieTime(0);
                            holder.mGif.setPaused(true);
                        }
                    });
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
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
