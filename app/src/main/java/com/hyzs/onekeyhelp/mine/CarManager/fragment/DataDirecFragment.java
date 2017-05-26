package com.hyzs.onekeyhelp.mine.CarManager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.mine.CarManager.activity.CarManagerActivity;

/**
 * Created by Administrator on 2017/3/27.
 */

public class DataDirecFragment extends Fragment implements View.OnClickListener,CarManagerActivity.SaveInterface {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_direc, null);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void save(int currPage) {

    }
}
