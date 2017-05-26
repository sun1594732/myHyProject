package com.hyzs.onekeyhelp.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.carresuce.view.CarResuceActivity;
import com.hyzs.onekeyhelp.contact.activity.LoginActivity;
import com.hyzs.onekeyhelp.family.circle.activity.CircleActivity;
import com.hyzs.onekeyhelp.family.circle.activity.CircleDetailActivity;
import com.hyzs.onekeyhelp.family.movable.activity.EventRegistActivity;
import com.hyzs.onekeyhelp.family.movable.activity.MovableListActivity;
import com.hyzs.onekeyhelp.family.movable.bean.MovableListBean;
import com.hyzs.onekeyhelp.home.activity.CircleSelectActivity;
import com.hyzs.onekeyhelp.home.activity.CommunityNoticeBaseActivity;
import com.hyzs.onekeyhelp.home.activity.HomeHospitalActivity;
import com.hyzs.onekeyhelp.home.bean.HomeFunctionBean;
import com.hyzs.onekeyhelp.home.forum.activity.ForumActivity;
import com.hyzs.onekeyhelp.lifehelp.view.activity.LifeHelpHomeActivity;
import com.hyzs.onekeyhelp.home.adapter.HeroPagerAdapter;
import com.hyzs.onekeyhelp.home.adapter.HomeActiveAdapter;
import com.hyzs.onekeyhelp.home.adapter.HomeChooseCommunityAdapter;
import com.hyzs.onekeyhelp.home.adapter.HomeCircleLvAdapter;
import com.hyzs.onekeyhelp.home.adapter.HomeDynamAdapter;
import com.hyzs.onekeyhelp.home.adapter.HomePostLvAdapter;
import com.hyzs.onekeyhelp.home.adapter.HomeRecyclerViewAdapter;
import com.hyzs.onekeyhelp.home.adapter.HomeRightSlideAdapter;
import com.hyzs.onekeyhelp.home.bean.HomeBannerBean;
import com.hyzs.onekeyhelp.home.bean.HomeCommunityDynamicListBean;
import com.hyzs.onekeyhelp.home.bean.HomeCommunityListBean;
import com.hyzs.onekeyhelp.home.bean.HomePageBean;
import com.hyzs.onekeyhelp.home.forum.activity.ForumDetailActivity;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.widget.ListViewForScrollView;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.module.housekeeping.activity.HousekeepingActivity;
import com.hyzs.onekeyhelp.module.record.activity.HomeRecordActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.news.BaseNewsActivity;
import com.hyzs.onekeyhelp.news.NewsWebActivity;
import com.hyzs.onekeyhelp.util.AppUtil;
import com.hyzs.onekeyhelp.util.FixedSpeedScroller;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.CarouselView;
import com.hyzs.onekeyhelp.widget.MyScrollView;
import com.hyzs.onekeyhelp.widget.RoundImageView;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.hyzs.onekeyhelp.R.id.fragment_home_rescue;


public class HomeFragment extends Fragment implements View.OnClickListener, MyScrollView.OnScrollListener {
    private TextView forum, news, resuce, help, circle, service, active, community, troop, post, healthInfo, healthIssue,
            healthQA, weekendLife, seeingDisplay, travel;
    View mPostView, mActiveView, mCircleView, mDynamicListView, mHelpView;
    private ViewStub mPostViewStub, mActiveViewStub, mCircleViewStub, mDynamicListViewStub, mHelpViewStub;
    private MyScrollView mScroll;
    private CircleImageView mUserIcon, helpBadge, resuceBadge, circleBadge, newsBadge, activeBadge, communityBadge,
            serviceBadge, forumBadge, troopBadge, postBadge, healthIssueBadge, healthQABadge, healthInfoBadge, weekendLifeBadge, seeingDisplayBadge, travelBadge;
    int ScrollHeight, mChooseCommunityPosition = 0, mDynamicIndex = 1, scrollPressPoint, scrollUpPoint;
    long HomeCommunityDynamicListTime;
    boolean isLoadingMoreData, isNeedLoading = true;
    private CarouselView mViewPager;
    private TextView mToolbarLeft, mTitle;
    private ImageView mBack, mToolBarRight, mScrollTopBtn;
    List<HomePageBean.CommunityHeroBean> list;
    HomePageBean bean;
    SlidingMenu sm;
    MySharedPreferences mySharedPreferences;
    float start = 0;
    private Dialog mChooseCommunity;
    private HomeCommunityListBean CommunityListBean;
    private homeCallBack callBack;
    private HomeBannerBean bannerBean;
    private VRefresh mRefresh;
    Dialog progressDialog, mCheckedDialog;
    private PopupWindow pop;
    private ProgressBar progressBar;
    private String APKUrl = null;
    MovableListBean movableListBean;
    private ViewPager mHeroViewPager;
    private List<HomeCommunityDynamicListBean.FamilyDynamicListBean> dynamicList;
    private HomeDynamAdapter dynamAdapter;
    private Handler heroHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mHeroViewPager.getCurrentItem() == bean.getCommunityHero().size() - 1) {
                mHeroViewPager.setCurrentItem(0);
                heroHandler.sendEmptyMessageDelayed(0, 4000);
                return;
            }
            mHeroViewPager.setCurrentItem(mHeroViewPager.getCurrentItem() + 1);
            heroHandler.sendEmptyMessageDelayed(0, 4000);
        }
    };

    private Handler UpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Map<String, String> param = new ArrayMap<>();
            param.put("action", "Get_AppVersion");
            param.put("v_code", AppUtil.getVersionCode(getActivity()) + "");
            NetRequest.ParamPostRequest(PortUtil.VersionUpdate, param, new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    try {
                        JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                        if ("10000".equals(jsonObject.getString("code"))) {
                            APKUrl = jsonObject.getString("v_path");
                            mCheckedDialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slidingmenu, null);
        sm = (SlidingMenu) view.findViewById(R.id.sm);
        sm.setMode(SlidingMenu.RIGHT);
        sm.setSecondaryMenu(R.layout.activity_home_right_slide);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        sm.setSecondaryBehindWidth(width);
        View rightView = sm.getSecondaryMenu();
        View LeftView = sm.getContent();
        assignView(LeftView);
        assignRightView(rightView);
        ScrollHeight = getScrollHeight(mScroll);
        HomeCommunityDynamicListTime = System.currentTimeMillis() / 1000;
        initView();
        initBanner();
        initListener();
        UpdateHandler.sendEmptyMessageDelayed(0, 2000);
        return view;
    }

    private void assignRightView(View rightView) {
        int[] res = {R.mipmap.classify_qiuzhu, R.mipmap.classify_car, R.mipmap.classify_jingcha, R.mipmap.classify_coffee, R.mipmap.classify_camera, R.mipmap.classify_badmintom
                , R.mipmap.classify_kalaok, R.mipmap.classify_yiliao, R.mipmap.classify_peple, R.mipmap.classify_cat, R.mipmap.classify_service, R.mipmap.classify_tree, R.mipmap.classify_highheel
                , R.mipmap.classify_gonggao, R.mipmap.classify_news, R.mipmap.classify_ren, R.mipmap.classify_yaos, R.mipmap.classify_maizi, R.mipmap.classify_duihua, R.mipmap.classify_fengguang};
        mySharedPreferences = MySharedPreferences.getInstance(getActivity());
        ImageView mBack = (ImageView) rightView.findViewById(R.id.toolbar_back);
        TextView mTitle = (TextView) rightView.findViewById(R.id.toolbar_title);
        mTitle.setText("全部分类");
        GridView mRecyclerView = (GridView) rightView.findViewById(R.id.activity_home_right_slide_recycler);
        HomeRightSlideAdapter adapter = new HomeRightSlideAdapter(getActivity(), res);
        mRecyclerView.setAdapter(adapter);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.toggle();
            }
        });
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        start = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (event.getX() - start > 150) {
                            sm.toggle();
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void initView() {
        mBack.setVisibility(View.GONE);
        mRefresh.setView(getActivity(), mScroll);
        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.mipmap.home_arrow_down);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTitle.setCompoundDrawables(null, null, drawable, null);
        mToolBarRight.setVisibility(View.VISIBLE);
        mToolBarRight.setImageResource(R.mipmap.fenlei);
        initDialog();
        initDownLoadDialog();
        mPostView = mPostViewStub.inflate();
        mActiveView = mActiveViewStub.inflate();
        mCircleView = mCircleViewStub.inflate();
//        mHelpView = mHelpViewStub.inflate();
        mDynamicListView = mDynamicListViewStub.inflate();
        progressDialog = ProgressDialog.createProgressLoading(getActivity());
        initData();
    }

    private void initData() {
        if (isNeedLoading) {
            progressDialog.show();
        }
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.isLogin(getActivity()) ? MySharedPreferences.getUserId(getActivity()) : "0");
        param.put("community", TextUtils.isEmpty(mySharedPreferences.getString("communityId")) ? "0" : mySharedPreferences.getString("communityId"));
        NetRequest.ParamDialogPostRequest(PortUtil.HomePage, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), HomePageBean.class);
                    initHero();
                    initFunctionView();
                    initPostView();
                    initActiveView();
                    initCircleView();
                    initDynamicListView();
                } catch (Exception e) {
                } finally {
                    if (mRefresh.isRefreshing()) {
                        mRefresh.setRefreshing(false);
                    }
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {
                ToastUtils.showShort(getActivity(), "加载失败,请重新刷新");
                if (mRefresh.isRefreshing()) {
                    mRefresh.setRefreshing(false);
                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });

    }

    private void initFunctionView() {
        if (MySharedPreferences.isLogin(getActivity())) {
            Map<String, String> param = new ArrayMap<>();
            param.put("currId", MySharedPreferences.getUserId(getActivity()));
            param.put("ut", HomeCommunityDynamicListTime + "");
            param.put("ct", System.currentTimeMillis() / 1000 + "");
            NetRequest.ParamPostRequest(PortUtil.HomePageButtonNewInfoStatistics, param, new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    HomeFunctionBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), HomeFunctionBean.class);
                    for (int i = 0; i < bean.getHomePageButtonNewInfoStatistics().size(); i++) {
                        HomeFunctionBean.HomePageButtonNewInfoStatisticsBean b = bean.getHomePageButtonNewInfoStatistics().get(i);
                        switch (b.getMoudel()) {
                            case 1:
                                if (b.getNewInfo() != 0) {
                                    helpBadge.setVisibility(View.VISIBLE);
                                }
                                break;
                            case 2:
                                if (b.getNewInfo() != 0) {
                                    resuceBadge.setVisibility(View.VISIBLE);
                                }
                                break;
                            case 3:
                                if (b.getNewInfo() != 0) {
                                    circleBadge.setVisibility(View.VISIBLE);
                                }
                                break;
                            case 4:
                                if (b.getNewInfo() != 0) {
                                    newsBadge.setVisibility(View.VISIBLE);
                                }
                                break;
                            case 5:
                                if (b.getNewInfo() != 0) {
                                    activeBadge.setVisibility(View.VISIBLE);
                                }
                                break;
                            case 6:
                                if (b.getNewInfo() != 0) {
                                    serviceBadge.setVisibility(View.VISIBLE);
                                }
                                break;
                        }
                    }
                }
            });
        }
    }

    private void initHero() {
        list = bean.getCommunityHero();
        List<View> views = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            View view = initHeroView(list.get(i));
            views.add(view);
        }
        mHeroViewPager.setAdapter(new HeroPagerAdapter(views, getActivity()));
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mHeroViewPager.getContext(),
                    new AccelerateInterpolator());
            field.set(mHeroViewPager, scroller);
            scroller.setmDuration(500);
        } catch (Exception e) {
        }
//        首页社区明星轮播,刷新数据后防止多个handler轮播
        heroHandler.removeCallbacksAndMessages(null);
        heroHandler.sendEmptyMessageDelayed(0, 2000);
    }

    private View initHeroView(final HomePageBean.CommunityHeroBean bean) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_hero_vp, null);
        TextView mHeroName = (TextView) view.findViewById(R.id.fragment_home_hero_name);
        RoundImageView mHeroIcon = (RoundImageView) view.findViewById(R.id.fragment_home_hero_icon);
        TextView mHeroSign = (TextView) view.findViewById(R.id.fragment_home_hero_sign);
        TextView mHeroPosition = (TextView) view.findViewById(R.id.fragment_home_hero_position);
        TextView mHeroHelpCount = (TextView) view.findViewById(R.id.fragment_home_helpCount);
        TextView mHeroAttionCount = (TextView) view.findViewById(R.id.fragment_home_attentionCount);
        TextView mHeroCount = (TextView) view.findViewById(R.id.fragment_home_heroCount);
        mHeroCount.setText(bean.getHeroPosition() + "");
        mHeroSign.setText("个人签名 : " + bean.getPersonalizedSignature());
        mHeroAttionCount.setText(bean.getWatchlistCountToMe() + "");
        mHeroHelpCount.setText(bean.getHelpPeopleCount() + "");
        mHeroName.setText(bean.getUesrname());
        if (TextUtils.isEmpty(bean.getFace())) {
            mHeroIcon.setImageResource(R.mipmap.replace_hybb);
        } else NetRequest.loadImg(getActivity(), bean.getFace(), mHeroIcon);
        mHeroPosition.setText(" " + bean.getCommunity());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MineHomeActivity.class);
                intent.putExtra("targetUserId", bean.getId() + "");
                startActivity(intent);
            }
        });
        return view;
    }

    private void initBanner() {
        NetRequest.ParamPostRequest(PortUtil.HomeBanner, null, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    bannerBean = new Gson().fromJson(data, HomeBannerBean.class);
                    mViewPager.setAdapter(new CarouselView.Adapter() {
                        @Override
                        public boolean isEmpty() {
                            return false;
                        }

                        @Override
                        public View getView(int position) {
                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item, null);
                            ImageView imageView = (ImageView) view.findViewById(R.id.image);
                            NetRequest.loadImg(getActivity(), bannerBean.getData().get(position).getImg(), imageView);
                            return view;
                        }

                        @Override
                        public int getCount() {
                            return bannerBean.getData().size();
                        }
                    });
                } catch (Exception e) {
                }
            }
        });
    }


    private void initListener() {
        mUserIcon.setOnClickListener(this);
        mTitle.setOnClickListener(this);
        help.setOnClickListener(this);
        service.setOnClickListener(this);
        news.setOnClickListener(this);
        forum.setOnClickListener(this);
        resuce.setOnClickListener(this);
        active.setOnClickListener(this);
        circle.setOnClickListener(this);
        community.setOnClickListener(this);
        mToolbarLeft.setOnClickListener(this);
        mToolBarRight.setOnClickListener(this);
        mScrollTopBtn.setOnClickListener(this);
        troop.setOnClickListener(this);
        post.setOnClickListener(this);
        healthQA.setOnClickListener(this);
        healthIssue.setOnClickListener(this);
        healthInfo.setOnClickListener(this);
        weekendLife.setOnClickListener(this);
        seeingDisplay.setOnClickListener(this);
        travel.setOnClickListener(this);
        mScroll.setOnScrollListener(this);
        mScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        NetRequest.stopImg(getActivity());
                        scrollPressPoint = v.getScrollY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int scrollY = v.getScrollY();
                        scrollUpPoint = v.getScrollY();
                        int height = v.getHeight();
                        int scrollViewMeasuredHeight = mScroll.getChildAt(0).getMeasuredHeight();
//                        if (scrollUpPoint - scrollPressPoint > 0) {
                        if ((scrollY + height) >= scrollViewMeasuredHeight - 50) {
                            if (!isLoadingMoreData) {
                                isLoadingMoreData = true;
                                progressDialog.show();
                                DynamRequest();
//                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        NetRequest.resumeImg(getActivity());
                        break;
                }
                return false;
            }
        });
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh.setRefreshing(false);
                mDynamicIndex = 1;
                HomeCommunityDynamicListTime = System.currentTimeMillis() / 1000;
                isNeedLoading = true;
                initData();
            }
        });
    }

    private void assignView(View view) {
        pop = HelpDialog.createDialogNoAlert(getActivity(), this, "您还没有登录", 0);
        mScrollTopBtn = (ImageView) view.findViewById(R.id.fragment_home_scroll_top);
        mRefresh = (VRefresh) view.findViewById(R.id.home_refresh);
        mPostViewStub = (ViewStub) view.findViewById(R.id.fragment_home_viewstub_post);
        mActiveViewStub = (ViewStub) view.findViewById(R.id.fragment_home_viewstub_active);
        mCircleViewStub = (ViewStub) view.findViewById(R.id.fragment_home_viewstub_circle);
        mDynamicListViewStub = (ViewStub) view.findViewById(R.id.fragment_home_viewstub_news);
        mScroll = (MyScrollView) view.findViewById(R.id.fragment_home_scroll);
        mViewPager = (CarouselView) view.findViewById(R.id.fragment_home_vp);
        mHeroViewPager = (ViewPager) view.findViewById(R.id.fragment_home_hero_vp);
        mToolbarLeft = (TextView) view.findViewById(R.id.toolbar_left_text);
        mUserIcon = (CircleImageView) view.findViewById(R.id.toolbar_left_circle_img);
        mBack = (ImageView) view.findViewById(R.id.toolbar_back);
        mTitle = (TextView) view.findViewById(R.id.toolbar_title);
        mToolBarRight = (ImageView) view.findViewById(R.id.toolbar_right_img);
        help = (TextView) view.findViewById(R.id.fragment_home_help);
        community = (TextView) view.findViewById(R.id.fragment_home_community);
        news = (TextView) view.findViewById(R.id.fragment_home_news);
        forum = (TextView) view.findViewById(R.id.fragment_home_forum);
        resuce = (TextView) view.findViewById(R.id.fragment_home_rescue);
        circle = (TextView) view.findViewById(R.id.fragment_home_circle);
        active = (TextView) view.findViewById(R.id.fragment_home_active);
        service = (TextView) view.findViewById(R.id.fragment_home_service);
        troop = (TextView) view.findViewById(R.id.fragment_home_troop);
        healthInfo = (TextView) view.findViewById(R.id.fragment_home_healthInfo);
        healthIssue = (TextView) view.findViewById(R.id.fragment_home_healthIssue);
        post = (TextView) view.findViewById(R.id.fragment_home_post);
        healthQA = (TextView) view.findViewById(R.id.fragment_home_answer);
        weekendLife = (TextView) view.findViewById(R.id.fragment_home_life);
        seeingDisplay = (TextView) view.findViewById(R.id.fragment_home_display);
        travel = (TextView) view.findViewById(R.id.fragment_home_travel);
        helpBadge = (CircleImageView) view.findViewById(R.id.fragment_home_help_badge);
        resuceBadge = (CircleImageView) view.findViewById(R.id.fragment_home_rescue_badge);
        circleBadge = (CircleImageView) view.findViewById(R.id.fragment_home_circle_badge);
        newsBadge = (CircleImageView) view.findViewById(R.id.fragment_home_news_badge);
        activeBadge = (CircleImageView) view.findViewById(R.id.fragment_home_active_badge);
        communityBadge = (CircleImageView) view.findViewById(R.id.fragment_home_community_badge);
        serviceBadge = (CircleImageView) view.findViewById(R.id.fragment_home_service_badge);
        forumBadge = (CircleImageView) view.findViewById(R.id.fragment_home_forum_badge);
        troopBadge = (CircleImageView) view.findViewById(R.id.fragment_home_troop_badge);
        healthInfoBadge = (CircleImageView) view.findViewById(R.id.fragment_home_healthInfo_badge);
        healthIssueBadge = (CircleImageView) view.findViewById(R.id.fragment_home_healthIssue_badge);
        postBadge = (CircleImageView) view.findViewById(R.id.fragment_home_post_badge);
        healthQABadge = (CircleImageView) view.findViewById(R.id.fragment_home_answer_badge);
        weekendLifeBadge = (CircleImageView) view.findViewById(R.id.fragment_home_life_badge);
        seeingDisplayBadge = (CircleImageView) view.findViewById(R.id.fragment_home_display_badge);
        travelBadge = (CircleImageView) view.findViewById(R.id.fragment_home_travel_badge);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_home_scroll_top:
                mScroll.smoothScrollTo(0, 0);
                break;
            case R.id.help_pop_confirm:
                pop.dismiss();
                break;
            case R.id.toolbar_left_circle_img:
                callBack.getCallBack();
                break;
            case R.id.toolbar_title:
                mChooseCommunity.show();
                break;
            case R.id.fragment_home_active:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                Intent intent1 = new Intent(getActivity(), MovableListActivity.class);
                intent1.putExtra("title", "社区活动");
                intent1.putExtra("type", 5);
                startActivity(intent1);
                break;
            case R.id.fragment_home_news:
                startActivity(new Intent(getActivity(), BaseNewsActivity.class));
                break;
            case R.id.fragment_home_forum:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                startActivity(new Intent(getActivity(), ForumActivity.class));
                break;
            case fragment_home_rescue:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                startActivity(new Intent(getActivity(), CarResuceActivity.class));
                break;
            case R.id.fragment_home_help:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                startActivity(new Intent(getActivity(), LifeHelpHomeActivity.class));
                break;
            case R.id.fragment_home_circle:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                startActivity(new Intent(getActivity(), CircleSelectActivity.class));
                break;
            case R.id.fragment_home_community:
                Intent i = new Intent(getActivity(), HomeHospitalActivity.class);
                startActivity(i);
                break;
            case R.id.fragment_home_service:
                Intent i1 = new Intent(getActivity(), HousekeepingActivity.class);
                startActivity(i1);
                break;
            case R.id.fragment_home_troop:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                startActivity(new Intent(getActivity(), CarResuceActivity.class));
                break;
            case R.id.fragment_home_post:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                startActivity(new Intent(getActivity(), CommunityNoticeBaseActivity.class));
                break;
            case R.id.fragment_home_answer:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                Intent intent2 = new Intent(getActivity(), ForumActivity.class);
                intent2.putExtra("type", 1);
                startActivity(intent2);
                break;
            case R.id.fragment_home_healthIssue:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                Intent intent = new Intent(getActivity(), ForumActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            case R.id.fragment_home_display:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                Intent intent3 = new Intent(getActivity(), ForumActivity.class);
                intent3.putExtra("type", 2);
                startActivity(intent3);
                break;

            case R.id.fragment_home_healthInfo:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
//                Intent intent = new Intent(getActivity(), ForumActivity.class);
//                intent.putExtra("type", "0");
//                startActivity(intent);
                break;
            case R.id.fragment_home_life:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                Intent intent4 = new Intent(getActivity(), MovableListActivity.class);
                intent4.putExtra("type", 3);
                intent4.putExtra("title", "周末生活");
                startActivity(intent4);
                break;
            case R.id.fragment_home_travel:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                Intent intent5 = new Intent(getActivity(), MovableListActivity.class);
                intent5.putExtra("type", 4);
                intent5.putExtra("title", "周边组团游");
                startActivity(intent5);
                break;
            case R.id.toolbar_left_text:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.toolbar_right_img:
                sm.toggle();
                break;
        }
    }


    @Override
    public void onScroll(int scrollY) {

    }

    private void initCircleView() {
        TextView title = (TextView) mCircleView.findViewById(R.id.fragment_home_viewstub1_title);
        LinearLayout ll = (LinearLayout) mCircleView.findViewById(R.id.fragment_home_viewstub1_ll);
        View row = mCircleView.findViewById(R.id.fragment_home_viewstub1_row);
        View line = mCircleView.findViewById(R.id.fragment_home_viewstub1_line);
        title.setText("圈子人气榜");
        title.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_f57752));
        row.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_f57752));
        line.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_f57752));
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                startActivity(new Intent(getActivity(), CircleActivity.class));
            }
        });
        ListViewForScrollView mListView = (ListViewForScrollView) mCircleView.findViewById(R.id.fragment_home_viewstub1_lv);
        if (bean != null) {
            HomeCircleLvAdapter adapter = new HomeCircleLvAdapter(bean.getCircleHot(), getActivity());
            mListView.setAdapter(adapter);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                Intent intent = new Intent(getActivity(), CircleDetailActivity.class);
                intent.putExtra("id", bean.getCircleHot().get(position).getId() + "");
                startActivity(intent);
            }
        });
    }

    private void initPostView() {
        LinearLayout ll = (LinearLayout) mPostView.findViewById(R.id.fragment_home_viewstub1_ll);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CommunityNoticeBaseActivity.class));
            }
        });
        ListViewForScrollView mListView = (ListViewForScrollView) mPostView.findViewById(R.id.fragment_home_viewstub1_lv);
        if (bean != null) {
            int bgArray[] = {R.mipmap.sy_gonggao1, R.mipmap.sy_gonggao2, R.mipmap.sy_gonggao3, R.mipmap.sy_gonggao4, R.mipmap.sy_gonggao5};
            List<Integer> bgs = new ArrayList<>();
            for (int i = 0; i < bean.getCommunityAnnouncement().size(); i++) {
                bgs.add(bgArray[i]);
            }
            HomePostLvAdapter adapter = new HomePostLvAdapter(bean.getCommunityAnnouncement(), getActivity(), bgs);
            mListView.setAdapter(adapter);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> param = new ArrayMap<String, String>();
                param.put("currId", MySharedPreferences.getUserId(getActivity()));
                param.put("CR_ToID", bean.getCommunityAnnouncement().get(position).getId() + "");
                param.put("targetType", "0");
                NetRequest.ParamPostRequest(PortUtil.UserStatistics, param, new NetRequest.getDataCallback() {
                    @Override
                    public void getData(String data) {

                    }
                });
                Intent intent = new Intent(getActivity(), NewsWebActivity.class);
                intent.putExtra("communityNoticeID", bean.getCommunityAnnouncement().get(position).getId() + "");
                startActivity(intent);
            }
        });
    }

    private void initActiveView() {
        TextView title = (TextView) mActiveView.findViewById(R.id.fragment_home_viewstub1_title);
        LinearLayout ll = (LinearLayout) mActiveView.findViewById(R.id.fragment_home_viewstub1_ll);
        View row = mActiveView.findViewById(R.id.fragment_home_viewstub1_row);
        View line = mActiveView.findViewById(R.id.fragment_home_viewstub1_line);
        title.setText("社区活动");
        title.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_017bff));
        row.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_017bff));
        line.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_017bff));
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                Intent intent = new Intent(getActivity(), MovableListActivity.class);
                intent.putExtra("title", "社区活动");
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
        final ListViewForScrollView mListView = (ListViewForScrollView) mActiveView.findViewById(R.id.fragment_home_viewstub1_lv);
        Map<String, String> map = new ArrayMap<>();
        map.put("currId", MySharedPreferences.getUserId(getActivity()));
        map.put("event_type", "0");
        map.put("Community_id", MySharedPreferences.getCommunityId(getActivity()));
        map.put("pageSize", "5");
        map.put("pageIndex", "1");
        NetRequest.ParamPostRequest(PortUtil.EventRegistrationList, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    movableListBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), MovableListBean.class);
                    mListView.setAdapter(new HomeActiveAdapter(movableListBean.getEventRegistrationList(), getActivity()));
                } catch (Exception e) {
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                Intent intent = new Intent(getActivity(), EventRegistActivity.class);
                intent.putExtra("title", movableListBean.getEventRegistrationList().get(position).getER_Title());
                intent.putExtra("movableId", movableListBean.getEventRegistrationList().get(position).getID());
                startActivity(intent);
            }
        });
    }

    private void initDynamicListView() {
        TextView title = (TextView) mDynamicListView.findViewById(R.id.fragment_home_viewstub1_title);
        TextView more = (TextView) mDynamicListView.findViewById(R.id.fragment_home_viewstub1_more);
        LinearLayout ll = (LinearLayout) mDynamicListView.findViewById(R.id.fragment_home_viewstub1_ll);
        more.setVisibility(View.GONE);
        title.setText("社区动态");
        ListViewForScrollView mListView = (ListViewForScrollView) mDynamicListView.findViewById(R.id.fragment_home_viewstub1_lv);
        dynamicList = new ArrayList<>();
        dynamAdapter = new HomeDynamAdapter(dynamicList, getActivity());
        mListView.setAdapter(dynamAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!MySharedPreferences.isLogin(getActivity())) {
                    pop.showAtLocation(mRefresh, Gravity.CENTER, 0, 0);
                    return;
                }
                Intent intent = new Intent();
                switch (dynamicList.get(position).getR_type()) {
                    case 0:
                        intent.setClass(getActivity(), CircleDetailActivity.class);
                        intent.putExtra("id", dynamicList.get(position).getRecord_id() + "");
                        break;
                    case 1:
                        intent.setClass(getActivity(), ForumDetailActivity.class);
                        intent.putExtra("id", dynamicList.get(position).getRecord_id() + "");
                        break;
                    case 2:

                        break;
                }
                startActivity(intent);
            }
        });
        DynamRequest();
    }

    private void DynamRequest() {
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.isLogin(getActivity()) ? MySharedPreferences.getUserId(getActivity()) : "0");
        param.put("communityID", MySharedPreferences.getCommunityId(getActivity()));
        param.put("firstPageReqDateTime", HomeCommunityDynamicListTime + "");
        param.put("pageSize", "10");
        param.put("pageIndex", mDynamicIndex + "");
        NetRequest.ParamPostRequest(PortUtil.HomeCommunityDynamicList, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                HomeCommunityDynamicListBean dynamicBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), HomeCommunityDynamicListBean.class);
                if (dynamicBean.getFamilyDynamicList().size() == 0) {
                    ToastSingleUtil.showToast(getActivity(), "无更多社区动态内容");
                    isLoadingMoreData = true;
                    return;
                }
                dynamicList.addAll(dynamicBean.getFamilyDynamicList());
                dynamAdapter.notifyDataSetChanged();
                mDynamicIndex++;
                isLoadingMoreData = false;
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    public int getScrollHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        return height;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTitle.requestFocus();
        mTitle.setFocusableInTouchMode(true);
        if (MySharedPreferences.isLogin(getActivity())) {
            mUserIcon.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(MySharedPreferences.getInstance(getActivity()).getString("myImg"))) {
                mUserIcon.setImageResource(R.mipmap.icon_replace);
            } else
                NetRequest.loadImg(getActivity(), MySharedPreferences.getInstance(getActivity()).getString("myImg"), mUserIcon);
            mToolbarLeft.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(MySharedPreferences.getCommunityName(getActivity()))) {
                mTitle.setText(MySharedPreferences.getCommunityName(getActivity()));
            }
        } else {
            mTitle.setText("环宇帮帮");
            mUserIcon.setVisibility(View.GONE);
            mToolbarLeft.setVisibility(View.VISIBLE);
        }
        isNeedLoading = false;
        initData();
    }

    private void initDialog() {
        View DialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_choose_community, null);
        assignDialogView(DialogView);
        mChooseCommunity = new AlertDialog.Builder(getActivity()).create();
        mChooseCommunity.show();
        mChooseCommunity.getWindow().setContentView(DialogView);
        mChooseCommunity.dismiss();
    }

    private void assignDialogView(View dialogView) {
        TextView sure = (TextView) dialogView.findViewById(R.id.dialog_choose_community_sure);
        TextView cancel = (TextView) dialogView.findViewById(R.id.dialog_choose_community_cancel);
        final ListView lv = (ListView) dialogView.findViewById(R.id.dialog_choose_community_lv);
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.isLogin(getActivity()) ? MySharedPreferences.getUserId(getActivity()) : "0");
        param.put("dataCount", "10");
        param.put("locationX", MySharedPreferences.getInstance(getActivity()).getString("X"));
        param.put("locationY", MySharedPreferences.getInstance(getActivity()).getString("Y"));
        NetRequest.ParamPostRequest(PortUtil.GetCommunityList, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                CommunityListBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), HomeCommunityListBean.class);
                if (!MySharedPreferences.isLogin(getActivity())) {
                    mySharedPreferences.setString("communityId", CommunityListBean.getHomePageGetChangeCommunityList().get(mChooseCommunityPosition).getId() + "");
                    mySharedPreferences.setString("communityName", CommunityListBean.getHomePageGetChangeCommunityList().get(mChooseCommunityPosition).getC_Name());
                }
                List<String> resource = new ArrayList<String>();
                for (int i = 0; i < CommunityListBean.getHomePageGetChangeCommunityList().size(); i++) {
                    resource.add(CommunityListBean.getHomePageGetChangeCommunityList().get(i).getC_Name());
                }
                lv.setAdapter(new HomeChooseCommunityAdapter(resource, getActivity()));
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < lv.getAdapter().getCount(); i++) {
                    lv.getChildAt(i).setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_f));
                }
                view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_f4));
                mChooseCommunityPosition = position;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChooseCommunity.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitle.setText(CommunityListBean.getHomePageGetChangeCommunityList().get(mChooseCommunityPosition).getC_Name());
                mySharedPreferences.setString("communityId", CommunityListBean.getHomePageGetChangeCommunityList().get(mChooseCommunityPosition).getId() + "");
                mySharedPreferences.setString("communityName", CommunityListBean.getHomePageGetChangeCommunityList().get(mChooseCommunityPosition).getC_Name());
                initData();
                mChooseCommunity.dismiss();
            }
        });
    }

    public static interface homeCallBack {
        void getCallBack();
    }

    public void setCallBack(homeCallBack callBack) {
        this.callBack = callBack;
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
                    URL url = new URL(APKUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("User-Agent",
                            "PacificHttpClient");
                    if (mCurrentSize > 0) {
                        urlConnection.setRequestProperty("RANGE", "bytes="
                                + mCurrentSize + "-");
                    }
                    mUpdateTotalSize = urlConnection.getContentLength();
                    progressBar.setMax(mUpdateTotalSize);
                    inputStream = urlConnection.getInputStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    BufferedOutputStream bufferedOutputStream = null;
                    if (inputStream != null) {
                        File file = new File(Environment.getExternalStorageDirectory(), "hybb.apk");
                        outputStream = new FileOutputStream(file);
                        bufferedOutputStream = new BufferedOutputStream(outputStream);
                        int count = 0;
                        int in = -1;
                        byte buf[] = new byte[8 * 1024];
                        while ((in = bufferedInputStream.read(buf)) != -1) {
                            bufferedOutputStream.write(buf, 0, in);
                            count += in;
                            if (mUpdateTotalSize > 0) {
                                progressBar.setProgress(count);
                            }
                        }
                    }
                    bufferedOutputStream.flush();
                    if (outputStream != null) {
                        bufferedOutputStream.close();
                    }
                    if (inputStream != null) {
                        bufferedInputStream.close();
                    }
                    // 下载完成通知安装
                    mCheckedDialog.dismiss();
                    installApk();
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
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "hybb.apk")),
                "application/vnd.android.package-archive");
        getActivity().startActivity(intent);
    }

    private void initDownLoadDialog() {
        View DialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog_help_publish_photo, null);
        assignDownLoadDialogView(DialogView);
        mCheckedDialog = new AlertDialog.Builder(getActivity()).create();
        mCheckedDialog.setCanceledOnTouchOutside(false);
        mCheckedDialog.show();
        mCheckedDialog.getWindow().setContentView(DialogView);
        mCheckedDialog.dismiss();
    }

    private void assignDownLoadDialogView(View dialogView) {
        TextView sure = (TextView) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_sure);
        TextView cancel = (TextView) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_cancel);
        TextView text = (TextView) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_text);
        final LinearLayout ll = (LinearLayout) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_total);
        progressBar = (ProgressBar) dialogView.findViewById(R.id.layout_dialog_help_publish_photo_progress);
        text.setText("环宇帮帮有新版本，是否更新?");
        sure.setText("是");
        cancel.setText("否");
        cancel.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_9));
        sure.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_1ccd9b));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckedDialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                ll.setVisibility(View.GONE);
                downloadAPK();
            }
        });
    }
}
