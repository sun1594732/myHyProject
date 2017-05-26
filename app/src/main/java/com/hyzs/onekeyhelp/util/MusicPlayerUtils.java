package com.hyzs.onekeyhelp.util;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hyzs123 on 2017/3/26.
 * 音乐播放工具类
 */

public class MusicPlayerUtils implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener {

    public MediaPlayer mediaPlayer; // 媒体播放器

    private Context mContext;



    public MusicPlayerUtils(Context mContext){
        this.mContext=mContext;
        MusicPlayerUtils();
    }



    // 初始化播放器
    public void MusicPlayerUtils( ) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
        } catch (Exception e) {
            Toast.makeText(mContext, "播放器初始化失败，请检查手机麦克风权限", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public void play() {
        mediaPlayer.start();
    }

    /**
     *
     * @param url
     *            url地址
     */
    public boolean playUrl(final String url) {

     
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    URL urlS;
//                    try {
//                        urlS = new URL(url);
//                        InputStream in = urlS.openStream();
//                    } catch (Exception e1) {
//                        handler.sendEmptyMessage(1);
//                        urlS = null;
//                        return;
//                    }

                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepareAsync();

                } catch (Exception e) {
                    handler.sendEmptyMessage(1);

                }
            }


        }).run();
        return true;
        }

    
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
        }
    };






    // 暂停
    public void pause() {
        mediaPlayer.pause();
    }

    // 停止
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // 播放准备
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        Log.e("mediaPlayer", "onPrepared");
    }



    /**
     * 缓冲更新
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }




    public  boolean checkURL(String url){
        boolean value=false;
        try {
            HttpURLConnection conn=(HttpURLConnection)new URL(url).openConnection();
            int code=conn.getResponseCode();
            if(code!=200){
                value=false;
            }else{
                value=true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
