package com.hyzs.onekeyhelp.util;

import android.graphics.Bitmap;

/**
 * Created by wubin on 2017/5/15.
 */

public class VideoInfo {
    private String videoName;
    private String data;
    private long duration;
    private Bitmap thumImage;
    private long size;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Bitmap getThumImage() {
        return thumImage;
    }

    public void setThumImage(Bitmap thumImage) {
        this.thumImage = thumImage;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
