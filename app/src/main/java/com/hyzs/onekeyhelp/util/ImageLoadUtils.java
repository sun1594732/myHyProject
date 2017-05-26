package com.hyzs.onekeyhelp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.hyzs.onekeyhelp.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by hyzs123 on 2017/3/21.
 * @action 图片加载工具类
 *
 */

public class ImageLoadUtils {

    private static  ImageLoader imageLoader;
    private static   DisplayImageOptions options;

    public static void setImageForUrl(String url, ImageView imageView){



        if(imageLoader!=null){

            imageLoader.displayImage(url, imageView,options);
        }else {
            DestOption();
            imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(url, imageView,options);
        }

//        if(imageLoader.loadImageSync(url)==null){
//            imageView.setVisibility(View.GONE);
//        }else{
//            imageView.setVisibility(View.VISIBLE);
//        }


    }

    public static  void  DestOption(){
         options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.replace_hybb)
                .showImageOnFail(R.mipmap.replace_hybb)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }












}
