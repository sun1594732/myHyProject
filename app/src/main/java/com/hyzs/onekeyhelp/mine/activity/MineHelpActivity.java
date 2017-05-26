package com.hyzs.onekeyhelp.mine.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyzs.onekeyhelp.lifehelp.adapter.LiveHelpMeAdapter;
import com.hyzs.onekeyhelp.lifehelp.bean.LifeHelpMeBean;
import com.hyzs.onekeyhelp.lifehelp.view.activity.HelpOtherDetailsActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.lifehelp.view.activity.LifeHelpPublishActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.JointGetUrl;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;

public class MineHelpActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView mBack;
    private TextView mTitle;
    private TextView mToolbarRight;
    private ListView help_lv;
    private OkHttpClient okHttpClient;
    private Request request ;

    // 标志位，标志已经初始化完成。

    private String WEBUrl= PortUtil.MySeekHelpList;
    private LifeHelpMeBean lifeHelpBean;


    @Override
    protected void assignView() {
        okHttpClient=new OkHttpClient();
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarRight = (TextView) findViewById(R.id.toolbar_right);
        help_lv = (ListView) findViewById(R.id.activity_mine_help_lv);

    }

    @Override
    protected void initView() {
        mBack.setOnClickListener(this);
        mToolbarRight.setOnClickListener(this);
        mTitle.setText("我的求助");
        mToolbarRight.setVisibility(View.VISIBLE);
        mToolbarRight.setText("发布");
        help_lv.setOnItemClickListener(this);
    }










    /**
     * 获取服务器信息
     */
    private void getWebInfo2Adapter() {
        HashMap<String ,String > hms=new HashMap<>();
        hms.put("currId", MySharedPreferences.getUserId(this));
        hms.put("Community_id","20");//// TODO: 2017/4/9 小区ID 未修改
        hms.put("pageIndex","1");
        hms.put("pageSize",Integer.MAX_VALUE+"");

        WEBUrl= JointGetUrl.getUrl(WEBUrl,hms);


        request  = new Request.Builder().url(WEBUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                request=null;
            }

            @Override
            public void onResponse(Response response) throws IOException {
                request=null;
                try {
                    String str=response.body().string();
                    str= URLDecoder.decode(str,"utf-8");
                    Log.e("stes",str);
//                    lifeHelpBean=  new Gson().fromJson(str, LifeHelpMeBean.class);
//                    handler.sendEmptyMessage(1);


                } catch (IOException e) {
                    Toast.makeText(MineHelpActivity.this, "数据获取异常，请稍后重试", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MineHelpActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);
        getWebInfo2Adapter();
    }

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    help_lv.setAdapter(new LiveHelpMeAdapter(MineHelpActivity.this,lifeHelpBean));

                    break;
            }
        }
    };

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_help;
    }

    @Override
    protected void initData() {
        getWebInfo2Adapter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.toolbar_back :
                finish();
                break;
            case  R.id.toolbar_right ://发布
                Intent intent = new Intent(MineHelpActivity.this, LifeHelpPublishActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this, HelpOtherDetailsActivity.class);
        intent.putExtra("isOther",false);
        intent.putExtra("Seek_ID",lifeHelpBean.getMySeekHelpList().get(i).getSeek_ID()+"");
        intent.putExtra("ID",lifeHelpBean.getMySeekHelpList().get(i).getSeek_ID()+"");
        Log.e("numberID","---"+lifeHelpBean.getMySeekHelpList().get(i).getSeek_ID());
        intent.putExtra("Seek_UserID",lifeHelpBean.getMySeekHelpList().get(i).getSeek_UserID()+"");

        startActivity(intent);
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MineHelpActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
}
