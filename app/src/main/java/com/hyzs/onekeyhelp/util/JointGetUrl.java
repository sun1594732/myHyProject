package com.hyzs.onekeyhelp.util;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by hyzs123 on 2017/3/19.
 */

public class JointGetUrl {
    public static String getUrl(String urls,HashMap params) {

        String url = urls;

//添加url参数

        if(params !=null) {

            Iterator it = params.keySet().iterator();

            StringBuffer sb =null;

            while(it.hasNext()) {

                String key = (String) it.next();

                String value = (String) params.get(key);

                if(sb ==null) {

                    sb =new StringBuffer();

                    sb.append("?");

                }else{

                    sb.append("&");

                }

                sb.append(key);

                sb.append("=");

                sb.append(value);

            }

            url += sb.toString();

        }

        return url;

    }

}
