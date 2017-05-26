package com.hyzs.onekeyhelp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.activity.LoginActivity;
import com.hyzs.onekeyhelp.family.circle.activity.CircleActivity;

/**
 * 常用App的工具类，包含版本号、版本名称、安装的应用程序ICON
 *
 * @author Administrator
 */
public class AppUtil {
    private Context context;


    public AppUtil(Context context) {
        this.context = context;
    }

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPacketName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取VersionName(版本名称)
     *
     * @param context
     * @return 失败时返回""
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = getPackageManager(context);
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPacketName(context), 0);
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取VersionCode(版本号)
     *
     * @param context
     * @return 失败时返回-1
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = getPackageManager(context);
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPacketName(context), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取所有安装的应用程序,不包含系统应用
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getInstalledPackages(Context context) {
        PackageManager packageManager = getPackageManager(context);
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<PackageInfo> packageInfoList = new ArrayList<PackageInfo>();
        for (int i = 0; i < packageInfos.size(); i++) {
            if ((packageInfos.get(i).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {//系统应用
                packageInfoList.add(packageInfos.get(i));
            }
        }
        return packageInfoList;
    }

    /**
     * 获取应用程序的icon图标
     *
     * @param context
     * @return 当包名错误时，返回null
     */
    public static Drawable getApplicationIcon(Context context) {
        PackageManager packageManager = getPackageManager(context);
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPacketName(context), 0);
            return packageInfo.applicationInfo.loadIcon(packageManager);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 启动安装应用程序
     *
     * @param activity
     * @param path     应用程序路径
     */
    public static void installApk(Activity activity, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)),
                "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

    /**
     * 获取PackageManager对象
     *
     * @param context
     * @return
     */
    private static PackageManager getPackageManager(Context context) {
        return context.getPackageManager();
    }

    public void checkVersion(View parent) {
        if (getVersionCode(context) == 2) {
//            mCheckedDialog.show();
        }
    }

    private int mCurrentSize = 0;
    private int mUpdateTotalSize = 0;
    private FileOutputStream outputStream;
    private InputStream inputStream;

    public void downloadAPK() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    HttpURLConnection urlConnection = null;
                    inputStream = null;
                    outputStream = null;
                    URL url = new URL("http://app.hyzsnt.com/Files/apk/app-release.apk");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("User-Agent",
                            "PacificHttpClient");
                    if (mCurrentSize > 0) {
                        urlConnection.setRequestProperty("RANGE", "bytes="
                                + mCurrentSize + "-");
                    }
                    mUpdateTotalSize = urlConnection.getContentLength();
//                    progressBar.setMax(mUpdateTotalSize);
                    inputStream = urlConnection.getInputStream();

                    if (inputStream != null) {
                        File file = new File(Environment.getExternalStorageDirectory(), "hybb");
                        outputStream = new FileOutputStream(file);
                        int count = 0;
                        int in = -1;
                        byte buf[] = new byte[1024];
                        while ((in = inputStream.read(buf)) != -1) {
                            outputStream.write(buf, 0, in);
                            count += in;
                            if (mUpdateTotalSize > 0) {
//                                progressBar.setProgress(count);
                            }
                        }
                    }

                    outputStream.flush();
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    // 下载完成通知安装
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void installApk() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "hybb")),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


}