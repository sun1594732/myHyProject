package com.hyzs.onekeyhelp.mine.CarManager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyzs.onekeyhelp.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2017/3/27.
 */

public class InsurInfoFragment extends Fragment {
    private static final String TAG = "InsurInfoFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_insur_info, null);
        return null;
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("InsurInfoFragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("InsurInfoFragment");
    }
}
