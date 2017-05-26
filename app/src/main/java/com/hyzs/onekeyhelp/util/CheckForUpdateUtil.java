//package com.hyzs.onekeyhelp.util;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Environment;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.hyzs.onekeyhelp.R;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//public class CheckForUpdateUtils {
//    private Activity context;
//    private Boolean isSelfUpdate;
//    private android.os.Handler installHandler;
//    private AlertDialog dialog1;
//    private ProgressBar progressBar;
//    private static final String saveFileName = "studentplatform.apk";
//    private CheckForUpdateCallBack checkForUpdateCallBack;
//
//    public CheckForUpdateUtils(Activity context, Boolean isSelfUpdate, CheckForUpdateCallBack checkForUpdateCallBack) {
//        this.context = context;
//        this.isSelfUpdate = isSelfUpdate;
//        this.checkForUpdateCallBack = checkForUpdateCallBack;
//        installHandler = new android.os.Handler();
//    }
//
//    public void getAPPVersion() {
//        String strUrl = ApiUrl.UPDATE_APP_URL;
//        HttpRequestUtils.getDataRequest(strUrl, ApiID.APIID_UPDATE_APP, context, new HttpRequestUtils.RequestCallBack() {
//            @Override
//            public void onError() {
//                if (isSelfUpdate) {
//                    ToastShowUtils.showCommonErrorMsg(context);
//                }
//            }
//
//            @Override
//            public void onResponse(Object response, HeadersResponse headersResponse) {
//                if (response != null && response instanceof UpdateAppResponseInfo) {
//                    UpdateAppResponseInfo updateAppResponse = (UpdateAppResponseInfo) response;
//                    int msg = updateAppResponse.getmMsgCode();
//                    if (msg == 1001) {
//                        int newVersionCode = updateAppResponse.getmStrUpdateCode();
//                        int versionCode = getAppVerCode();
//                        if (versionCode >= newVersionCode) {
//                            if (isSelfUpdate) {
//                                Toast.makeText(context, "已是最新版本，不需要更新！", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            checkForUpdateCallBack.onGetNewVersion();
//                            String updateDescription = updateAppResponse.getmStrUpdateDescriptions();
//                            String downloadUrl = updateAppResponse.getmStrDownloadUrl();
//                            int updateType = updateAppResponse.getmUpdateStatus();
//                            CommonLOG.e("updateType", "updateType:" + updateType);
//                            if (updateType == NetWorkCode.FORCEUPDATEAPP) {
//                                CommonLOG.e("dialog", "dialog:" + updateType);
//                                showSingleBtnDialog(downloadUrl, updateDescription);
//                            } else if (updateType == NetWorkCode.UNFORCEUPDATEAPP) {
//                                showDoubleBtnDialog(downloadUrl, updateDescription);
//                            }
//                        }
//
//                    } else {
//                        if (isSelfUpdate) {
//                            ToastShowUtils.showErrorMessage(context, updateAppResponse.getmStrErrorMessage());
//                        }
//                    }
//                } else {
//                    if (isSelfUpdate) {
//                        ToastShowUtils.showCommonErrorMsg(context);
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * 显示单个按钮的自定义dialog用以强制下载
//     *
//     * @param strUrl
//     * @param describe
//     */
//    private void showSingleBtnDialog(final String strUrl, String describe) {
//        DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        };
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View alertDialogView = View.inflate(context, R.layout.dialog_self_singlebtn, null);
//        builder.setCancelable(false).setOnKeyListener(keylistener);
//        final AlertDialog dialog = builder.create();
//        dialog.setView(alertDialogView, 0, 0, 0, 0);
//        TextView dialogTitle = (TextView) alertDialogView.findViewById(R.id.dialog_singlebtn_title);
//        TextView dialogMsg = (TextView) alertDialogView.findViewById(R.id.dialog_singlebtn_msg);
//        Button btnYes = (Button) alertDialogView.findViewById(R.id.dialog_singlebtn_yes);
//        dialogTitle.setText("版本更新");
//        dialogMsg.setText("检测到新版本\n" + describe);
//        btnYes.setText("更新");
//        btnYes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkForUpdateCallBack.onConfirmClick();
//                dialog.dismiss();
//                downFile(strUrl);
//            }
//        });
//        dialog.show();
//    }
//
//    /**
//     * 显示多个按钮的自定义对话框用以非强制下载
//     *
//     * @param strUrl
//     * @param describe
//     */
//    private void showDoubleBtnDialog(final String strUrl, String describe) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View alertDialogView = View.inflate(context, R.layout.dialog_self_define, null);
//        final AlertDialog dialog = builder.create();
//        dialog.setView(alertDialogView, 0, 0, 0, 0);
//        TextView dialogTitle = (TextView) alertDialogView.findViewById(R.id.dialog_self_title);
//        TextView dialogMsg = (TextView) alertDialogView.findViewById(R.id.dialog_self_msg);
//        Button btnCancel = (Button) alertDialogView.findViewById(R.id.dialog_self_cancel);
//        Button btnYes = (Button) alertDialogView.findViewById(R.id.dialog_self_yes);
//        dialogTitle.setText("版本更新");
//        dialogMsg.setText("检测到新版本\n" + describe);
//        btnYes.setText("更新");
//        btnCancel.setText("暂不更新");
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                checkForUpdateCallBack.onCancelClick();
//            }
//        });
//        btnYes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                downFile(strUrl);
//                checkForUpdateCallBack.onConfirmClick();
//            }
//        });
//        dialog.show();
//    }
//
//    private int mCurrentSize = 0;
//    private int mUpdateTotalSize = 0;
//    private FileOutputStream outputStream;
//    private InputStream inputStream;
//
//    private void downFile(final String strUrl) {
//        showProgressDialog();
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//
//                try {
//                    HttpURLConnection urlConnection = null;
//                    inputStream = null;
//                    outputStream = null;
//                    URL url = new URL(strUrl);
//                    urlConnection = (HttpURLConnection) url.openConnection();
//                    urlConnection.setRequestProperty("User-Agent",
//                            "PacificHttpClient");
//                    if (mCurrentSize > 0) {
//                        urlConnection.setRequestProperty("RANGE", "bytes="
//                                + mCurrentSize + "-");
//                    }
//                    mUpdateTotalSize = urlConnection.getContentLength();
//                    progressBar.setMax(mUpdateTotalSize);
//                    inputStream = urlConnection.getInputStream();
//
//                    if (inputStream != null) {
//                        File file = new File(Environment.getExternalStorageDirectory(), saveFileName);
//                        outputStream = new FileOutputStream(file);
//                        int count = 0;
//                        int in = -1;
//                        byte buf[] = new byte[1024];
//                        while ((in = inputStream.read(buf)) != -1) {
//                            outputStream.write(buf, 0, in);
//                            count += in;
//                            if (mUpdateTotalSize > 0) {
//                                progressBar.setProgress(count);
//                            }
//                        }
//                    }
//
//                    outputStream.flush();
//                    if (outputStream != null) {
//                        outputStream.close();
//                    }
//                    if (inputStream != null) {
//                        inputStream.close();
//                    }
//                    // 下载完成通知安装
//                    updateDown();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//
//    }
//
//    private void updateDown() {
//        installHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                dialog1.dismiss();
//                installApk();
//            }
//        });
//    }
//
//    private void installApk() {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(new File(Environment
//                        .getExternalStorageDirectory(), saveFileName)),
//                "application/vnd.android.package-archive");
//        context.startActivity(intent);
//
//    }
//
//    private void showProgressDialog() {
//        DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        };
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//        View progressDialog = View.inflate(context, R.layout.dialog_self_progress, null);
//        builder1.setOnKeyListener(keylistener).setCancelable(false);
//        dialog1 = builder1.create();
//        dialog1.setView(progressDialog, 0, 0, 0, 0);
//        TextView progressTitle = (TextView) progressDialog.findViewById(R.id.dialog_progress_title);
//        TextView progressMsg = (TextView) progressDialog.findViewById(R.id.dialog_progress_msg);
//        progressBar = (ProgressBar) progressDialog.findViewById(R.id.dialog_progress_progressbar);
//        progressTitle.setText("版本更新");
//        progressMsg.setText("下载中...");
//        dialog1.show();
//    }
//
//    private int getAppVerCode() {
//        int versionCode = -1;
//        try {
//            String pkName = context.getPackageName();
//            versionCode = context.getPackageManager().getPackageInfo(pkName, 0).versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return versionCode;
//    }
//}