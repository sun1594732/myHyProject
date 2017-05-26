package com.hyzs.onekeyhelp.mine.CarManager.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.CarManager.fragment.CarInfoFragment;
import com.hyzs.onekeyhelp.mine.CarManager.fragment.DataDirecFragment;
import com.hyzs.onekeyhelp.mine.CarManager.fragment.InsurInfoFragment;
import com.hyzs.onekeyhelp.widget.SyncHorizontalScrollView;
import com.umeng.analytics.MobclickAgent;

public class CarManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView title,right;
    private ImageView back;
    private RelativeLayout rl_nav;
    private SyncHorizontalScrollView mHsv;
    private RadioGroup rg_nav_content;
    private ImageView iv_nav_indicator;

    private ViewPager mViewPager;
    private int indicatorWidth; // 指示器宽度

    private LayoutInflater mInflater;
    private TabFragmentPagerAdapter mAdapter;
    private int currentIndicatorLeft = 0;
    public static String[] tabTitle = {"车辆信息", "车辆保险信息", "资料说明"}; // 标题
    public static int[] bitmaps = {R.mipmap.cheliangxinxi, R.mipmap.baoxian, R.mipmap.ziliao};

    public ProgressDialog PDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_manager);
        findViewById();
        initView();

//        progress_circle();
        getWebInfo2Adapter();
        setListener();
    }

    private void findViewById() {
        title = (TextView) findViewById(R.id.toolbar_title);
        right = (TextView) findViewById(R.id.toolbar_right);
        back = (ImageView) findViewById(R.id.toolbar_back);
        rl_nav = (RelativeLayout) findViewById(R.id.carmarange_rl_nav);
        mHsv = (SyncHorizontalScrollView) findViewById(R.id.carmarange_mHsv);
        rg_nav_content = (RadioGroup) findViewById(R.id.carmarange_rg_nav_content);
        iv_nav_indicator = (ImageView) findViewById(R.id.carmarange_iv_nav_indicator);

        mViewPager = (ViewPager) findViewById(R.id.mViewPager);


    }

    private void initView() {
        title.setText("车辆资料");
        right.setText("保存");
        right.setVisibility(View.VISIBLE);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取屏幕信息
        indicatorWidth = dm.widthPixels / 3;// 指示器宽度为屏幕宽度的2/1

        ViewGroup.LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
        cursor_Params.width = indicatorWidth;// 初始化滑动下标的宽
        iv_nav_indicator.setLayoutParams(cursor_Params);

        mHsv.setSomeParam(rl_nav, this,
                dm.widthPixels);
        // 获取布局填充器
        mInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        initNavigationHSV();


    }

    DataDirecFragment dataDirecFragment = new DataDirecFragment();
    InsurInfoFragment insurInfoFragment = new InsurInfoFragment();
    CarInfoFragment carInfoFragment = new CarInfoFragment();
    public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

        public TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg) {
            Fragment ft = null;
            switch (arg) {
                case 0:
                    ft = carInfoFragment;
                    break;
                case 1:
                    ft = insurInfoFragment;
                    break;
                case 2:
                    ft = dataDirecFragment;
                    break;

            }
            return ft;
        }

        @Override
        public int getCount() {
            return tabTitle.length;
        }
    }


    private void setListener() {
        back.setOnClickListener(this);
        right.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (2 == position) {
                    right.setVisibility(View.INVISIBLE);
                }else right.setVisibility(View.VISIBLE);
                if (rg_nav_content != null
                        && rg_nav_content.getChildCount() > position) {
                    ((RadioButton) rg_nav_content.getChildAt(position))
                            .performClick();
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        rg_nav_content
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        if (rg_nav_content.getChildAt(checkedId) != null) {

                            TranslateAnimation animation = new TranslateAnimation(
                                    currentIndicatorLeft,
                                    ((RadioButton) rg_nav_content
                                            .getChildAt(checkedId)).getLeft(),
                                    0f, 0f);
                            animation.setInterpolator(new LinearInterpolator());
                            animation.setDuration(100);
                            animation.setFillAfter(true);

                            // 执行位移动画
                            iv_nav_indicator.startAnimation(animation);

                            mViewPager.setCurrentItem(checkedId); // ViewPager
                            // 跟随一起 切换

                            // 记录当前 下标的距最左侧的 距离
                            currentIndicatorLeft = ((RadioButton) rg_nav_content
                                    .getChildAt(checkedId)).getLeft();

                            mHsv.smoothScrollTo(
                                    (checkedId > 1 ? ((RadioButton) rg_nav_content
                                            .getChildAt(checkedId)).getLeft()
                                            : 0)
                                            - ((RadioButton) rg_nav_content
                                            .getChildAt(1)).getLeft(),
                                    0);
                        }
                    }
                });

    }

    /**
     * 圆形加载对话框
     */
    public void progress_circle() {
        PDialog = new ProgressDialog(this);
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


    /**
     * 获取服务器信息
     */
    private void getWebInfo2Adapter() {
        mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
//        HashMap<String ,String > hms=new HashMap<>();
//        hms.put("currId","179");
//        hms.put("pageIndex","1");
//        hms.put("pageSize","9");
//        WEBUrl= JointGetUrl.getUrl(WEBUrl,hms);
//        Log.e("info",WEBUrl);
//        request  = new Request.Builder().url(WEBUrl).build();
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.e("infoErrorMessage",e.getMessage().toString());
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                request=null;
//                try {
//                    String str=response.body().string();
//                    str= URLDecoder.decode(str,"utf-8");
////                    lifeHelpOtherBean=  new Gson().fromJson(str, LifeHelpOtherBean.class);
////                    otherFragment=new HelpOtherFragment(LifeHelpHomeActivity.this,lifeHelpOtherBean);
//                    handler.sendEmptyMessage(1);//成功
//                } catch (IOException e) {
//                    Log.e("info", e.toString());
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
                    mViewPager.setAdapter(mAdapter);
                    PDialog.dismiss();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right:
                saveInterface.save(mViewPager.getCurrentItem());
                break;
        }
    }

    public static SaveInterface saveInterface;

    public void setSaveInterface(SaveInterface saveInterface) {
        this.saveInterface = saveInterface;
    }
    public static interface SaveInterface{
        public void save(int currPage);
    }

    private void initNavigationHSV() {

        rg_nav_content.removeAllViews();

        for (int i = 0; i < tabTitle.length; i++) {


            RadioButton rb = (RadioButton) getLayoutInflater().inflate(R.layout.nav_radiogroup_car_manarge, null);
            rb.setId(i);
            rb.setText(tabTitle[i]);
            Drawable mDarwable = this.getResources().getDrawable(bitmaps[i]);
            rb.setCompoundDrawablesWithIntrinsicBounds(null, mDarwable, null, null);
            rb.setLayoutParams(new ViewGroup.LayoutParams(indicatorWidth,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            rb.setBackgroundColor(getResources().getColor(R.color.color_f));
            rg_nav_content.addView(rb);
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("CarManagerActivity"); //统计页面，"MainScreen"为页面名称，可自定义
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("CarManagerActivity");
    }
}
