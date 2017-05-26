package com.hyzs.onekeyhelp;


import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.widget.pinnedimage.GestureImageView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class ShowImageActivity extends BaseActivity {

    private ViewPager vp;
    private ArrayList<String> pathList;
    private ShowImagePagerAdapter pagerAdapter;
    @Override
    protected void assignView() {
        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        vp = (ViewPager) findViewById(R.id.activity_show_image_vp);
        pagerAdapter = new ShowImagePagerAdapter(getImageViews());
        vp.setAdapter(pagerAdapter);
        vp.setCurrentItem(getIntent().getIntExtra("position",0));
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_image;
    }

    @Override
    protected void initData() {

    }

    public List<GestureImageView> getImageViews() {
        List<GestureImageView>imgs = new ArrayList<>();
        pathList = getIntent().getStringArrayListExtra("url");
        for (String path:pathList) {
            GestureImageView iv = new GestureImageView(this);
            NetRequest.loadImg(this,path,iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            imgs.add(iv);
        }
        return imgs;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ShowImageActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }
    public void onPause() {

        super.onPause();

        MobclickAgent.onPageEnd("ShowImageActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
}
