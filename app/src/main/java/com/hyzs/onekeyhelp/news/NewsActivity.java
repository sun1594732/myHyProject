package com.hyzs.onekeyhelp.news;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;


import com.hyzs.onekeyhelp.home.bean.NewsListBean;
import com.hyzs.onekeyhelp.util.JointGetUrl;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.HashMap;

public class NewsActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, VRefresh.OnLoadListener {

    private ImageView mBack;
    private TextView toolbar_title;
    private ListView news_list_item;
    private VRefresh superRefreshLayout;

    private OkHttpClient okHttpClient;
    private Request request ;
    private HashMap hms;
    private int pindex=1;
    private NewsListBean newsBean;
    private ProgressDialog PDialog;
    private NewAdapter newAdapter;
    private String NewUrl="http://appserver.hy-bb.cn/News/appnewslist";
    @Override
    protected void assignView() {
        progress_circle();
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        news_list_item = (ListView) findViewById(R.id.news_list_item);
        superRefreshLayout = (VRefresh) findViewById(R.id.superRefreshLayout);
        okHttpClient=new OkHttpClient();
    }

    @Override
    protected void initView() {
        toolbar_title.setText("热点新闻");
        mBack.setOnClickListener(this);
        news_list_item.setOnItemClickListener(this);
        superRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light);
        superRefreshLayout.setProgressViewOffset(false, 0, 48);
        superRefreshLayout.setColorSchemeResources( android.R.color.holo_blue_bright);
        superRefreshLayout.setSize(0);//0和1  圆形进度条两种不同效果 0刚开始有渲染效果
        superRefreshLayout.setView(this, news_list_item);//设置嵌套的子view -listview
        superRefreshLayout.setMoreData(true);//设置是否还有数据可加载(一般根据服务器反回来决定)


    }

    @Override
    protected void initListener() {

        superRefreshLayout.setOnRefreshListener(this);
        superRefreshLayout.setOnLoadListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void initData() {


        requesData(1);



    }

    /**
     *  请求数据
     * @param type  1是刷新，2是加载
     */
    private void requesData(int type) {
        hms=new HashMap<>();
        hms.put("id","98");// 98固定值
        hms.put("pindex",pindex+"");
        hms.put("psize","10");
        requestMyData(hms,NewUrl,type);
    }


    /**
     *   联网请求数据
     *
     */
    private void requestMyData(HashMap hms, String MWEBUrl, final int type) {


        MWEBUrl = JointGetUrl.getUrl(MWEBUrl, hms);

        request = new Request.Builder().url(MWEBUrl).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
//                Log.e("info",response.body().string());
                if(type==1){
                    newsBean=new Gson().fromJson(response.body().string(), NewsListBean.class);
                    newAdapter=new NewAdapter(NewsActivity.this,newsBean);
                }else{
                    NewsListBean newsBeanto =new Gson().fromJson(response.body().string(),NewsListBean.class);

                    newsBean.getNewsListByClassify().addAll(newsBeanto.getNewsListByClassify());

                }
                handler.sendEmptyMessage(type);
            }
        });
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1:

                    news_list_item.setAdapter(newAdapter );
                    PDialog.dismiss();
                    superRefreshLayout.setRefreshing(false);
                    break;
                case 2:
                    newAdapter.notifyDataSetChanged();

                    superRefreshLayout.setLoading(false);
                    break;

            }


        }

    };






        @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back:
                finish();
                break;

        }
    }

    /**
     * 圆形加载对话框

     */
    public void progress_circle(){
        PDialog  = new ProgressDialog(this);
        // 进度条为水平旋转
        PDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置点击返回不能取消
        PDialog.setCancelable(true);
        //设置触摸对话框以外的区域不会消失
        PDialog.setCanceledOnTouchOutside(false);
        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        PDialog.setIcon(R.mipmap.ic_launcher);
        // 设置标题
        PDialog.setTitle("提示");
        PDialog.setMessage("正在努力加载...");

        PDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                // dimiss的监听
            }
        });
        PDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //cancel
            }
        });
        PDialog.show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,NewsWebActivity.class);
        intent.putExtra("newID",newsBean.getNewsListByClassify().get(position).getId()+"");
        startActivity(intent);

    }



    @Override
    public void onRefresh() {
        pindex=1;
        requesData(1);
    }

    @Override
    public void onLoadMore() {
        ++pindex;
        requesData(2);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}
