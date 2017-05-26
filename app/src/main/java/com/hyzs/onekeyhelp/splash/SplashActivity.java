package com.hyzs.onekeyhelp.splash;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyzs.onekeyhelp.MainActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.util.AppUtil;
import com.hyzs.onekeyhelp.util.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.getBoolean;

public class SplashActivity extends BaseActivity {
    private ViewPager mViewPager;
    private List<ImageView> list;
    private TextView skip, mVersion;
    private RelativeLayout mStartLayout;
    int[] page = {R.mipmap.leader1, R.mipmap.leader2, R.mipmap.leader3, R.mipmap.leader4};
    private Handler hiddenStartHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    };

    @Override
    protected void assignView() {
        mViewPager = (ViewPager) findViewById(R.id.activity_splash_vp);
        skip = (TextView) findViewById(R.id.activity_splash_skip);
        mStartLayout = (RelativeLayout) findViewById(R.id.activity_main_start);
        mVersion = (TextView) findViewById(R.id.activity_main_start_version);
    }

    @Override
    protected void initView() {
        if (MySharedPreferences.getInstance(this).getInt("firstLogin", 0) == 1) {
            mVersion.setText("当前版本 :  v" + AppUtil.getVersionName(this));
            hiddenStartHandler.sendEmptyMessageDelayed(0, 500);
            mViewPager.setVisibility(View.GONE);
        } else mStartLayout.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                MySharedPreferences.getInstance(SplashActivity.this).setInt("firstLogin", 1);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < page.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(page[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            list.add(imageView);
            if (i == page.length - 1) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        MySharedPreferences.getInstance(SplashActivity.this).setInt("firstLogin", 1);
                        finish();
                    }
                });
            }
        }
        mViewPager.setAdapter(new MyPagerAdapter());
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }
    }
}
