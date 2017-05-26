package com.hyzs.onekeyhelp.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.util.VideoInfo;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by wubin on 2017/5/15.
 */

public class VideoChooseAdapter extends BaseAdapter {
    private List<VideoInfo> videoInfoList;
    private Context context;

    public VideoChooseAdapter(List<VideoInfo> videoInfoList, Context context) {
        this.videoInfoList = videoInfoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return videoInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_video_choose, null);
            holder.mImage = (ImageView) convertView.findViewById(R.id.img);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.mName = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        VideoInfo videoInfo = videoInfoList.get(position);
        holder.mImage.setImageBitmap(videoInfo.getThumImage());
        holder.time.setText(getTime(videoInfo.getDuration()/1000));
        holder.mName.setText(videoInfo.getSize()/1024/1024+" MB");
        return convertView;
    }

    class ViewHolder {
        ImageView mImage;
        TextView time, mName;
    }

    private String getTime(long l) {
        String time;
        if (l / 60 > 0) {
            if (l / 60 / 60 > 0) {
                time = l / 3600 + ":" + l / 60 % 60 + ":" + l % 60;
            } else time = l / 60 % 60 + ":" + l % 60;
        } else
            time = "00:" + l % 60;
        return time;
    }
}
