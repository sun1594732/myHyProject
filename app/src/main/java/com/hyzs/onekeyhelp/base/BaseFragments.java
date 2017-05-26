package com.hyzs.onekeyhelp.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.Map;


/**
 * Created by 14369 on 2016-09-09.
 */

public abstract class BaseFragments extends Fragment {

	protected LoadingPage mLoadingPage;
	private Handler mHandler = new Handler();
//	public TradeActivity mActivity;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mLoadingPage = new LoadingPage(getActivity()) {
			@Override
			public int layoutId() {
				return getLayoutId();
			}

			@Override
			protected void onSuccess(ResultState resultState, View successView) {
				initView(successView);
				initData(resultState.getContent());
			}

			@Override
			protected Map<String, String> params() {
				return getParams();
			}

			@Override
			protected String url() {
				return getUrl();
			}
		};
		return mLoadingPage;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				showLoadingPage();
			}
		}, 0);
	}

	public void showLoadingPage() {
		mLoadingPage.show();
	}

	protected void initView(View contentView) {
	}

	protected abstract Map<String, String> getParams();

	protected abstract void initData(String content);

	protected abstract String getUrl();

	public abstract int getLayoutId();

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
//		this.mActivity = (TradeActivity)context;
	}
}
