package com.hyzs.onekeyhelp.video;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ObtainVideoUtil;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.util.VideoInfo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.path;


public class VideoChooseActivity extends BaseActivity implements View.OnClickListener {
    private GridView mGv;
    private ImageView mBack;
    private TextView mToolBarRight, mTitle;
    private List<VideoInfo> videoInfoList;
    Dialog mProgressDialog;

    @Override
    protected void assignView() {
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolBarRight = (TextView) findViewById(R.id.toolbar_right);
        mGv = (GridView) findViewById(R.id.activity_videoChoose_gv);
    }

    @Override
    protected void initView() {
        mProgressDialog = ProgressDialog.createProgressLoading(VideoChooseActivity.this);
        videoInfoList = ObtainVideoUtil.method();
        mToolBarRight.setText("上传");
        mTitle.setText("上传视频");
        mToolBarRight.setVisibility(View.GONE);
        mGv.setAdapter(new VideoChooseAdapter(videoInfoList, this));
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mToolBarRight.setOnClickListener(this);
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                mProgressDialog.show();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            upLoadByCommonPost(position);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();

                try {
                    NetRequest.mp4Utils(MySharedPreferences.getUserId(VideoChooseActivity.this), videoInfoList.get(position).getData(), new NetRequest.getDataCallback() {
                        @Override
                        public void getData(String data) {
                            LogUtils.e(UrlDecodeUtil.urlCode(data));
                            try {
                                PublishPicBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PublishPicBean.class);
                                getIntent().putExtra("pic", videoInfoList.get(position).getThumImage());
                                getIntent().putExtra("url", bean.getUrl());
                                setResult(RESULT_OK, getIntent());
                                mProgressDialog.dismiss();
                                finish();
                            } catch (Exception e) {

                            }
                        }
                    }, new NetRequest.getMessageFailCallback1() {
                        @Override
                        public void getData(String data) {
                            LogUtils.e("videochoose" + UrlDecodeUtil.urlCode(data));
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_choose;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:

                break;
        }
    }

    private void upLoadByCommonPost(int position) throws IOException {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        URL url = new URL(PortUtil.VideoStreamUpLoad);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url
                .openConnection();
        httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
        // 允许输入输出流
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        // 使用POST方法
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("Charset", "UTF-8");
        httpURLConnection.setRequestProperty("Content-Type",
                "multipart/form-data;boundary=" + boundary);

        DataOutputStream dos = new DataOutputStream(
                httpURLConnection.getOutputStream());
        dos.writeBytes(twoHyphens + boundary + end);
        dos.writeBytes("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                + videoInfoList.get(position).getData().substring(videoInfoList.get(position).getData().lastIndexOf("/") + 1) + "\"" + end);
        dos.writeBytes(end);

        FileInputStream fis = new FileInputStream(videoInfoList.get(position).getData());
        byte[] buffer = new byte[8192]; // 8k
        int count = 0;
        // 读取文件
        while ((count = fis.read(buffer)) != -1) {
            dos.write(buffer, 0, count);
        }
        fis.close();
        dos.writeBytes(end);
        dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
        dos.flush();
        InputStream is = httpURLConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String result = br.readLine();
        LogUtils.e("--------------------------------" + result);
        dos.close();
        is.close();
    }
}
