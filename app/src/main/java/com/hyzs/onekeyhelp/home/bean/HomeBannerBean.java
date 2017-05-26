package com.hyzs.onekeyhelp.home.bean;

import java.util.List;

/**
 * Created by wubin on 2017/3/27.
 */

public class HomeBannerBean {

    /**
     * data : [{"title":"湖北侦破特大跨国系列电信诈骗案","url":"http://www.hy-bb.cn/appweb/newscontent.aspx?id=8029","img":"http://img.zglzxf.com/default/2016/1201/5190698107371594087.jpg"},{"title":"泰国民众给猴子摆宴席 水果大餐成食物大战","url":"http://www.hy-bb.cn/appweb/newscontent.aspx?id=8010","img":"http://img.zglzxf.com/default/2016/1201/4658746740402615877.jpg"},{"title":"便民|智能化管理\u201c一键帮助\u201d正式进入生活社区","url":"http://www.hy-bb.cn/appweb/newscontent.aspx?id=7367","img":"http://img.zglzxf.com/default/2016/1119/5360430747432798684.jpg"}]
     * code : 0
     * error :
     */

    private int code;
    private String error;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 湖北侦破特大跨国系列电信诈骗案
         * url : http://www.hy-bb.cn/appweb/newscontent.aspx?id=8029
         * img : http://img.zglzxf.com/default/2016/1201/5190698107371594087.jpg
         */

        private String title;
        private String url;
        private String img;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
