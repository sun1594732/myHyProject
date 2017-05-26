package com.hyzs.onekeyhelp.news;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.home.bean.NewsListBean;
import com.hyzs.onekeyhelp.util.ImageLoadUtils;
import com.hyzs.onekeyhelp.widget.RoundImageView;

public class NewAdapter extends BaseAdapter {

    private Context mContext;
    private NewsListBean newsBean;
    private NewsListBean.NewsListByClassifyBean dataBean;

    public NewAdapter(Context mContext, NewsListBean newsBean) {
        this.mContext = mContext;
        this.newsBean = newsBean;
    }

    @Override
    public int getCount() {
        return newsBean.getNewsListByClassify().size();
    }

    @Override
    public Object getItem(int position) {
        return newsBean.getNewsListByClassify().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        dataBean = newsBean.getNewsListByClassify().get(position);
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_news, null);
            vh = new ViewHolder();
            vh.news_headImage = (RoundImageView) convertView.findViewById(R.id.news_headimage);
            vh.news_browse_number = (TextView) convertView.findViewById(R.id.tv_news_browse_number);
            vh.news_time = (TextView) convertView.findViewById(R.id.tv_news_time);
            vh.news_intro = (TextView) convertView.findViewById(R.id.tv_news_intro);
            vh.news_where = (TextView) convertView.findViewById(R.id.tv_news_where);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (TextUtils.isEmpty(dataBean.getImgURL())) {
            vh.news_headImage.setVisibility(View.GONE);
        } else
            ImageLoadUtils.setImageForUrl(dataBean.getImgURL(), vh.news_headImage);
        vh.news_time.setText(dataBean.getDiffTime());
        vh.news_where.setText(dataBean.getResource());
        vh.news_browse_number.setText(dataBean.getViewnum() + "");
        vh.news_intro.setText(dataBean.getTitle() + "");
        return convertView;
    }

    private class ViewHolder {
        RoundImageView news_headImage;
        TextView news_time;
        TextView news_intro;
        TextView news_browse_number;
        TextView news_where;
    }
}
