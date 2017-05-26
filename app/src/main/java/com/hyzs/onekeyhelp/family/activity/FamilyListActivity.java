package com.hyzs.onekeyhelp.family.activity;


import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.family.bean.FamilyAddMemberBean;
import com.hyzs.onekeyhelp.family.fragment.FamilyAlbumFragment;
import com.hyzs.onekeyhelp.family.fragment.FamilyCircleFragment;
import com.hyzs.onekeyhelp.family.fragment.FamilyMemberFragment;
import com.hyzs.onekeyhelp.family.fragment.FamilyRecordFragment;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.widget.CanBanScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class FamilyListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack, rightImg;
    private ImageView mScrollLine;
    private TextView title, mFamilyAlbum, mFamilyRecord, mFamilyCircle, mFamilyMember, add, delete;
    private LinearLayout llOption, llTab;
    private List<Fragment> fragmentList;
    private CanBanScrollViewPager viewPager;
    private TabFragmentPagerAdapter mAdapter;
    private int indicatorWidth;
    private int currentIndicatorLeft = 0;
    private FamilyMemberFragment familyMemberFragment;
    private TextView left;
    private TextView right;
    private LinearLayout bottom;
    private Message msg;


    @Override
    protected void assignView() {
        title = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        rightImg = (ImageView) findViewById(R.id.toolbar_right_img);
        mScrollLine = (ImageView) findViewById(R.id.scroll_line);
        mFamilyAlbum = (TextView) findViewById(R.id.activity_family_list_album);
        mFamilyRecord = (TextView) findViewById(R.id.activity_family_list_record);
        mFamilyCircle = (TextView) findViewById(R.id.activity_family_list_circle);
        mFamilyMember = (TextView) findViewById(R.id.activity_family_list_member);
        llOption = (LinearLayout) findViewById(R.id.activity_family_list_option);
        llTab = (LinearLayout) findViewById(R.id.activity_family_list_tab);
        viewPager = (CanBanScrollViewPager) findViewById(R.id.activity_family_list_viewpager);
        add = (TextView) findViewById(R.id.activity_family_list_add);
        delete = (TextView) findViewById(R.id.activity_family_list_delete);
        left = (TextView) findViewById(R.id.fragment_family_member_left);
        right = (TextView) findViewById(R.id.fragment_family_member_right);
        bottom = (LinearLayout) findViewById(R.id.fragment_family_member_bottom);

    }

    @Override
    protected void initView() {
        title.setText("家庭");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取屏幕信息
        indicatorWidth = dm.widthPixels / 4;// 指示器宽度为屏幕宽度的2/1
        ViewGroup.LayoutParams layoutParams = mScrollLine.getLayoutParams();
        layoutParams.width = indicatorWidth;
        mScrollLine.setLayoutParams(layoutParams);
        rightImg.setImageResource(R.mipmap.toolbar_right);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(4);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        rightImg.setOnClickListener(this);
        mFamilyAlbum.setOnClickListener(this);
        mFamilyRecord.setOnClickListener(this);
        mFamilyCircle.setOnClickListener(this);
        mFamilyMember.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                if (3 == position) {
                    rightImg.setVisibility(View.VISIBLE);
                } else rightImg.setVisibility(View.GONE);
                anim(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void anim(int position) {
        TranslateAnimation animation = new TranslateAnimation(currentIndicatorLeft, indicatorWidth * position,
                0f, 0f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(100);
        animation.setFillAfter(true);
        // 执行位移动画
        mScrollLine.startAnimation(animation);
        // 记录当前 下标的距最左侧的 距离
        currentIndicatorLeft = indicatorWidth * position;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_family_list;
    }

    @Override
    protected void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new FamilyAlbumFragment());
        fragmentList.add(new FamilyRecordFragment());
        fragmentList.add(new FamilyCircleFragment());
        familyMemberFragment = new FamilyMemberFragment();
        fragmentList.add(familyMemberFragment);
        mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_family_member_left:
                msg = new Message();
                msg.obj = left.getText().toString();
                msg.what = 3;
                familyMemberFragment.handler.sendMessage(msg);
                if (left.getText().equals("同意") & familyMemberFragment.delPosition == -1) {
                    setStatus();
                    return;
                }else{
                    setStatus();
                }
                break;
            case R.id.fragment_family_member_right:
                msg = new Message();
                msg.obj = right.getText().toString();
                msg.what = 4;
                familyMemberFragment.handler.sendMessage(msg);
                if (right.getText().equals("同意")) {
                    for (FamilyAddMemberBean.FamilySearchAddListBean b : familyMemberFragment.addBean.getFamilySearchAddList()) {
//                        if (b.isCheck() == 1) {
                            setStatus();
//                            break;
//                        }
                    }
                }else{
                    setStatus();
                }
                break;
            case R.id.activity_family_list_add:
                familyMemberFragment.handler.sendEmptyMessage(1);
                llOption.setVisibility(View.GONE);
                llTab.setVisibility(View.VISIBLE);
                left.setText("拒绝");
                right.setText("同意");
                bottom.setVisibility(View.VISIBLE);
                rightImg.setVisibility(View.GONE);
                title.setText("添加成员");
                break;
            case R.id.activity_family_list_delete:
                familyMemberFragment.handler.sendEmptyMessage(2);
                llOption.setVisibility(View.GONE);
                llTab.setVisibility(View.VISIBLE);
                right.setText("拒绝");
                left.setText("同意");
                bottom.setVisibility(View.VISIBLE);
                rightImg.setVisibility(View.GONE);
                title.setText("删除成员");
                break;
            case R.id.toolbar_right_img:
                if (!MySharedPreferences.isLogin(this)) {
                    return;
                }
                if (!getIntent().getStringExtra("userId").trim().equals(MySharedPreferences.getUserId(this))) {
                    Toast.makeText(this, "只有群主可以对成员进行操作", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (llTab.isShown()) {
                    llTab.setVisibility(View.GONE);
                    llOption.setVisibility(View.VISIBLE);
                    viewPager.setCanScroll(false);
                    mFamilyAlbum.setClickable(false);
                    mFamilyRecord.setClickable(false);
                    mFamilyCircle.setClickable(false);
                    mFamilyMember.setClickable(false);
                } else {
                    llOption.setVisibility(View.GONE);
                    llTab.setVisibility(View.VISIBLE);
                    viewPager.setCanScroll(true);
                    mFamilyAlbum.setClickable(true);
                    mFamilyRecord.setClickable(true);
                    mFamilyCircle.setClickable(true);
                    mFamilyMember.setClickable(true);
                }
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_family_list_album:
                viewPager.setCurrentItem(0);
                break;
            case R.id.activity_family_list_circle:
                viewPager.setCurrentItem(2);
                break;
            case R.id.activity_family_list_record:
                viewPager.setCurrentItem(1);
                break;
            case R.id.activity_family_list_member:
                viewPager.setCurrentItem(3);
                break;

        }
    }

    private void setStatus() {
        bottom.setVisibility(View.GONE);
        rightImg.setVisibility(View.VISIBLE);
        viewPager.setCanScroll(true);
        mFamilyAlbum.setClickable(true);
        mFamilyRecord.setClickable(true);
        mFamilyCircle.setClickable(true);
        mFamilyMember.setClickable(true);
        title.setText("家庭");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

        public TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg) {
            Fragment ft = null;
            ft = fragmentList.get(arg);
            return ft;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
