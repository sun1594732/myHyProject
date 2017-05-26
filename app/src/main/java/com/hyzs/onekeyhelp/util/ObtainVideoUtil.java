package com.hyzs.onekeyhelp.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;

import com.hyzs.onekeyhelp.MyApplication;

import java.util.ArrayList;
import java.util.List;


public class ObtainVideoUtil {

    public static List<VideoInfo> method() {
        String progress[] = {

                MediaStore.Video.Media.DISPLAY_NAME,//视频的名字
                MediaStore.Video.Media.SIZE,//大小
                MediaStore.Video.Media.DURATION,//长度
                MediaStore.Video.Media.DATA,//播放地址
        };
        List<VideoInfo> list = new ArrayList<>();
        //获取数据提供者,this是上下文
        ContentResolver cr = MyApplication.getAppContext().getContentResolver();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//有sd卡的情况
            Cursor cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, progress, null, null, null);
            while (cursor.moveToNext()) {
// 到视频文件的信息
                String name = cursor.getString(0);//得到视频的名字
                long size = cursor.getLong(1);//得到视频的大小
                long durantion = cursor.getLong(2);//得到视频的时间长度
                String data = cursor.getString(3);//得到视频的路径，可以转化为uri进行视频播放
//使用静态方法获取视频的缩略图
                Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(data, MediaStore.Video.Thumbnails.MINI_KIND);
                VideoInfo videoInfo = new VideoInfo();
//创建视频信息对象
                videoInfo.setVideoName(name);
                videoInfo.setData(data);
                videoInfo.setDuration(durantion);
                videoInfo.setSize(size);
                videoInfo.setThumImage(thumbnail);
                list.add(videoInfo);
            }
        }
        //不论是否有sd卡都要查询手机内存
        Cursor cursor = cr.query(MediaStore.Video.Media.INTERNAL_CONTENT_URI, progress, null, null, null);
        while (cursor.moveToNext()) {
// 到视频文件的信息
            String name = cursor.getString(0);//得到视频的名字
            long size = cursor.getLong(1);//得到视频的大小
            long durantion = cursor.getLong(2);//得到视频的时间长度
            String data = cursor.getString(3);//得到视频的路径，可以转化为uri进行视频播放
//使用静态方法获取视频的缩略图
            Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(data, MediaStore.Video.Thumbnails.MINI_KIND);
            VideoInfo videoInfo = new VideoInfo();
//创建视频信息对象
            videoInfo.setData(data);
            videoInfo.setVideoName(name);
            videoInfo.setDuration(durantion);
            videoInfo.setSize(size);
            videoInfo.setThumImage(thumbnail);
            list.add(videoInfo);
        }
        return list;
    }
}
