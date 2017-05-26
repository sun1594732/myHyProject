package com.hyzs.onekeyhelp.family.circle.activity;

import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;

public class CircleMovieDetailActivity extends BaseActivity implements View.OnClickListener {

    private VideoView videoView;
    private ImageView mImage, mPlay ,mBack;

    @Override
    protected void assignView() {
        videoView = (VideoView) this.findViewById(R.id.vv);
        mImage = (ImageView) findViewById(R.id.img);
        mPlay = (ImageView) findViewById(R.id.play);
        mBack = (ImageView) findViewById(R.id.back);
    }

    @Override
    protected void initView() {
        if (TextUtils.isEmpty(getIntent().getStringExtra("pic"))) {
            mImage.setImageResource(R.mipmap.replace_hybb);
        } else NetRequest.loadImg(this, getIntent().getStringExtra("pic"), mImage);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoPath(getIntent().getStringExtra("url"));
    }

    @Override
    protected void initListener() {
        mPlay.setOnClickListener(this);
        mBack.setOnClickListener(this);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_movie_detail;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                mImage.setVisibility(View.GONE);
                mPlay.setVisibility(View.GONE);
                videoView.start();
                videoView.requestFocus();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
