package com.hyzs.onekeyhelp.util;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Administrator on 2017/3/11.
 */

public class MyAsycnTask extends AsyncTask<String,Integer,String> {
    private static final String TAG = "MyAsycnTask";
    public MyCallBack callBack;
    Context mContext;
    Thread Thread;


    public interface MyCallBack{
        public void SendResult(String result);


    }

    /**
     * @param context
     * @param callBack
     */
    public MyAsycnTask(Context context, MyCallBack callBack) {
        super();
        this.callBack=callBack;
        this.mContext=context;
    }
    @Override
    protected void onPreExecute() {

    }
    @Override
    protected String doInBackground(String... params) {

        String result = "";

        /**
         * 0.method
         * 1.url
         * 2.param
         */
        try {

            if(params[0].equals("GET")){
                result = HttpUtil.GetRequet(params[1]);
            }else{
                result = HttpUtil.PostRequest(params[1], params[2]);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = ""+e.getMessage();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        callBack.SendResult(result);

        super.onPostExecute(result);
    }
}
