package com.hyzs.onekeyhelp.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/11.
 */

public class HttpUtil  {

    private static final String TAG = "HttpUtil";


    // get 请求方式
    public static String GetRequet(String url) throws IOException {

        URL localurl =new URL(url);
        //Log.e(TAG, " result ==== "+ url);
        HttpURLConnection conn =(HttpURLConnection) localurl.openConnection();

        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        //conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
        conn.connect();

        InputStream is=conn.getInputStream();
        InputStreamReader isr=new InputStreamReader(is);
        BufferedReader br=new BufferedReader(isr);

        StringBuffer sb=new StringBuffer();
        String str="";
        for(int i=0;(str=br.readLine())!=null;i++){
            sb.append(str);
            //Log.e(TAG, "sb=========="+sb.toString());
        }
        return sb.toString();
    }


    /**
     * @param url
     * @param param
     *            参数
     * @throws IOException
     */
    // post 请求方式
    public static String PostRequest(String url,String param) throws IOException {
        // TODO Auto-generated method stub
        URL localurl=new URL(url);

        HttpURLConnection conn =(HttpURLConnection) localurl.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
        conn.connect();

        OutputStream os=conn.getOutputStream();
        DataOutputStream dos=new DataOutputStream(os);
        dos.writeBytes(param);

        InputStream is=conn.getInputStream();
        InputStreamReader isr=new InputStreamReader(is);

        BufferedReader br= new BufferedReader(isr);

        StringBuffer sb=new StringBuffer();
        String str="";
        for(int i=0;(str=br.readLine())!=null;i++){
            sb.append(str);
        }
        return sb.toString();
    }
//    public String PostRequest(Context mContext, Map<String, String> valueMap, String url) throws FileNotFoundException {
//         
//                    HttpClient client = new HttpClient();
//                    PostMethod filePost = new PostMethod(url);
//                    filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
//                    try {
//                                if(valueMap!=null){
//                                    for (Map.Entry<String, String> map : valueMap.entrySet()) {
//                                            if (null != map.getValue()) {
//                                                    filePost.addParameter(map.getKey(), map.getValue());
//                                                }
//                                        }
//                                    }
//                            int status = client.executeMethod(filePost);
//                            if (status != 200) {
//                                    return null;
//                                } else {
//                                    return filePost.getResponseBodyAsString();
//                                }
//                        } catch (HttpException e) {
//                            e.printStackTrace();
//                            return null;
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return null;
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            return null;
//                        }
//            }
//     
//             
//                private Context getApplicationContext() {
//                // TODO Auto-generated method stub
//                return null;
//            }

}
