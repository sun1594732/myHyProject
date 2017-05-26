package com.hyzs.onekeyhelp.family.movable.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.movable.bean.MovableDetailsBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.OkHttpUtil;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.CarouselView;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class MovableDetailsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MovableDetailsFragment";
    private Context mContext;
    private CircleImageView icon;
    private TextView sponsor, name, zheng, time, district, jinxingzhong, max, currNum, theme, stroke, mTime, startTime, endTime, address, route, condition, equip, note;
    private ImageView rna, juweihui;
    private Button signUp;
    private MovableDetailsBean bean;
    private okhttp3.Request request;
    private SliderLayout sliderLayout;
    private PagerIndicator pagerIndicator;
    private Dialog dialog;
    private CarouselView carouselView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movable_details, null);
        mContext = getActivity();
        assignView(view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        signUp.setOnClickListener(this);
        icon.setOnClickListener(this);
    }

    private void initData() {
        requestData();
    }

    private void requestData() {
        if (MySharedPreferences.isLogin(mContext)) {
            dialog.show();
            String url = PortUtil.EventRegistrationDetails+"?currId=" + MySharedPreferences.getUserId(mContext) + "&event_id=" + getActivity().getIntent().getIntExtra("movableId", 0);//
            request = new okhttp3.Request.Builder().url(url).build();
            new OkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "请求数据失败，请重试", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });
                    request = null;
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    request = null;
                    try {
                        String json = UrlDecodeUtil.urlCode(response.body().string());
                        bean = new Gson().fromJson(json, MovableDetailsBean.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initView();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        dialog.dismiss();
                    }
                }
            });
        } else {
            Toast.makeText(mContext, "您还没登录", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    private void initView() {
        MovableDetailsBean.EventRegistrationDetailsBean b = bean.getEventRegistrationDetails();
        if (!TextUtils.isEmpty(b.getAvatar()))
            NetRequest.loadImg(mContext, b.getAvatar(), icon);
        else icon.setImageResource(R.mipmap.icon_replace);
        if (0 == b.getER_SponsorType()) {
            sponsor.setVisibility(View.VISIBLE);
            juweihui.setVisibility(View.GONE);
        } else {
            sponsor.setVisibility(View.GONE);
            juweihui.setVisibility(View.VISIBLE);
        }
        name.setText(b.getNickName());
        if (0 == b.getNCCS()) {
            zheng.setVisibility(View.INVISIBLE);
        } else zheng.setVisibility(View.VISIBLE);
        if (0 == b.getRNA()) {
            rna.setVisibility(View.GONE);
        } else rna.setVisibility(View.VISIBLE);
        time.setText(getTime(Long.parseLong(b.getER_Datetime())));
        district.setText(b.getCommunity());
        switch (b.getStateCode()) {
            case 1:
                jinxingzhong.setText("审核中");
                jinxingzhong.setTextColor(ContextCompat.getColor(mContext, R.color.color_9));
                signUp.setText("活动审核中");
                signUp.setClickable(false);
                break;
            case 2:
                jinxingzhong.setText("筹备中");
                jinxingzhong.setTextColor(ContextCompat.getColor(mContext, R.color.color_9));
                signUp.setText("活动筹备中");
                signUp.setClickable(false);
                break;
            case 3:
                jinxingzhong.setText("报名进行中");
                jinxingzhong.setTextColor(ContextCompat.getColor(mContext, R.color.color_f7221d));
                if (0 == b.getCurrUserApplyState()) {
                    signUp.setText("我要报名");
                    signUp.setClickable(true);
                } else {
                    signUp.setText("已报名");
                    signUp.setClickable(false);
                }
                break;
            case 4:
                jinxingzhong.setText("活动进行中");
                jinxingzhong.setTextColor(ContextCompat.getColor(mContext, R.color.color_15b521));
                signUp.setText("活动进行中");
                signUp.setClickable(false);
                break;
            case 5:
                jinxingzhong.setText("活动未成行");
                jinxingzhong.setTextColor(ContextCompat.getColor(mContext, R.color.color_9));
                signUp.setText("活动未成形");
                signUp.setClickable(false);
                break;
            case 6:
                jinxingzhong.setText("报名被终止");
                jinxingzhong.setTextColor(ContextCompat.getColor(mContext, R.color.color_9));
                signUp.setText("报名被终止");
                signUp.setClickable(false);
                break;
            case 7:
                jinxingzhong.setText("圆满成功");
                jinxingzhong.setTextColor(ContextCompat.getColor(mContext, R.color.color_f7221d));
                signUp.setText("圆满成功");
                signUp.setClickable(false);
                break;
        }
        if (b.getUserID() == Integer.parseInt(MySharedPreferences.getUserId(mContext))) {
            signUp.setVisibility(View.GONE);
        }
        max.setText(b.getER_Registration_Max() + "");
        currNum.setText(b.getApplyCount() + "");
        theme.setText(b.getER_Title());
        stroke.setText(b.getER_Content());
        mTime.setText(getTime(Long.parseLong(b.getER_Event_Start())) + "至" + getTime(Long.parseLong(b.getER_Event_End())));
        startTime.setText(getEndTime(Long.parseLong(b.getER_Registration_Start())));
        endTime.setText(getEndTime(Long.parseLong(b.getER_Registration_End())));
        address.setText(b.getER_Address());
        route.setText(b.getER_Route());
        condition.setText(b.getER_Condition());
        equip.setText(b.getER_Equipment());
        note.setText(b.getER_Attention());
        initSlider();
    }
    private LayoutInflater mInflater;
    private void initSlider() {
        if (bean.getImageGroup().size() == 0) {
            carouselView.setVisibility(View.GONE);
            return;
        }
        carouselView.setVisibility(View.VISIBLE);
        carouselView.setAdapter(new CarouselView.Adapter() {
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public View getView(int position) {
                View view = mInflater.inflate(R.layout.item,null);
                ImageView imageView = (ImageView) view.findViewById(R.id.image);
                NetRequest.loadImg(mContext,bean.getImageGroup().get(position).getImage_url(),imageView);
                return view;
            }

            @Override
            public int getCount() {
                return bean.getImageGroup().size();
            }
        });








//        if (bean.getImageGroup().size() == 0) {
//            sliderLayout.setVisibility(View.GONE);
//            return;
//        }
//        sliderLayout.setVisibility(View.INVISIBLE);
//        sliderLayout.removeAllSliders();
//        for (MovableDetailsBean.ImageGroupBean imgBean : bean.getImageGroup()) {
//            TextSliderView textSliderView = new TextSliderView(mContext);
//            BaseSliderView.ScaleType scaleType = BaseSliderView.ScaleType.CenterInside;
//            textSliderView
//                    .image(imgBean.getImage_url())
//                    .setScaleType(BaseSliderView.ScaleType.Fit).setScaleType(scaleType);
//            sliderLayout.addSlider(textSliderView);
//        }
//        sliderLayout.setCustomIndicator(pagerIndicator);
//
//
////        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
////        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
////        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
//        sliderLayout.setCustomAnimation(new DescriptionAnimation());
////        sliderLayout.setDuration(5000);
//        sliderLayout.startAutoCycle(5000, 5000, true);
//        if (0 != bean.getImageGroup().size())
//            sliderLayout.setCurrentPosition(0);
//        sliderLayout.setVisibility(View.VISIBLE);
    }


    private String getTime(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(l * 1000);
    }

    private String getEndTime(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(l * 1000);
    }

    private void assignView(View view) {
        mInflater = LayoutInflater.from(mContext);
        carouselView = (CarouselView) view.findViewById(R.id.CarouselView);
        startTime = (TextView) view.findViewById(R.id.movable_details_regist_start);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        pagerIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        sponsor = (TextView) view.findViewById(R.id.movable_details_sponsor);
        name = (TextView) view.findViewById(R.id.movable_details_name);
        zheng = (TextView) view.findViewById(R.id.movable_details_zheng);
        time = (TextView) view.findViewById(R.id.movable_details_time);
        district = (TextView) view.findViewById(R.id.movable_details_district);
        jinxingzhong = (TextView) view.findViewById(R.id.movable_details_jinxingzhong);
        max = (TextView) view.findViewById(R.id.movable_details_people_max);
        currNum = (TextView) view.findViewById(R.id.movable_details_curr_num);
        theme = (TextView) view.findViewById(R.id.movable_details_theme);
        stroke = (TextView) view.findViewById(R.id.movable_details_stroke);
        mTime = (TextView) view.findViewById(R.id.movable_details_m_time);
        endTime = (TextView) view.findViewById(R.id.movable_details_regist_end);
        address = (TextView) view.findViewById(R.id.movable_details_address);
        route = (TextView) view.findViewById(R.id.movable_details_route);
        condition = (TextView) view.findViewById(R.id.movable_details_condition);
        equip = (TextView) view.findViewById(R.id.movable_details_equip);
        note = (TextView) view.findViewById(R.id.movable_details_note);
        icon = (CircleImageView) view.findViewById(R.id.movable_details_icon);
        rna = (ImageView) view.findViewById(R.id.movable_details_rna);
        juweihui = (ImageView) view.findViewById(R.id.movable_details_juweihui);
        signUp = (Button) view.findViewById(R.id.movable_details_sign_up);
        dialog = ProgressDialog.createProgressLoading(mContext);
        signUp.setClickable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movable_details_sign_up:
                if (MySharedPreferences.isLogin(mContext)) {
                    dialog.show();
                    final String url = PortUtil.ActivityApply+"?currId=" + MySharedPreferences.getUserId(mContext) + "&activity_id=" + getActivity().getIntent().getIntExtra("movableId", 0) + "&community_id="+MySharedPreferences.getCommunityId(getActivity());
                    OkHttpUtil.newInstamce().getAsynHttp(url, new OkHttpUtil.HttpCallback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) {
                            String s = "";
                            try {
                                s = response.body().string();
                                if (!TextUtils.isEmpty(s)) {
                                    String json = UrlDecodeUtil.urlCode(s);
                                    JSONObject jsonObject = new JSONObject(json);
                                    if ("10000".equals(jsonObject.getString("code"))) {
                                        getActivity()
                                                .runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(mContext, "报名成功", Toast.LENGTH_SHORT).show();
                                                        signUp.setText("已报名");
                                                        signUp.setClickable(false);
                                                        initData();
                                                    }
                                                });
                                    } else {
                                        getActivity()
                                                .runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(mContext, "报名未开始或已结束", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                dialog.dismiss();
                            }
                        }
                    });
                }
                break;
            case R.id.movable_details_icon:
                if (bean == null) {
                    return;
                }
                Intent intent = new Intent(getActivity(), MineHomeActivity.class);
                intent.putExtra("targetUserId", bean.getEventRegistrationDetails().getUserID()+"");
                startActivity(intent);
                break;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MovableDetailsFragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MovableDetailsFragment");
    }

}
