package com.hyzs.onekeyhelp.netRequest.nohttp;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.InputStreamBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;


public class NetRequest {
    private static RequestQueue queue = NoHttp.newRequestQueue();
    private static MySharedPreferences mySharedPreferences;
    private static Dialog ErrorDialog, LoadingDialog;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ErrorDialog.dismiss();
        }
    };

    public interface getDataCallback {
        void getData(String data);
    }

    public interface getDataFailCallback {
        void getData();
    }

    public interface getDataFailCallback1 {
        void getData(Exception error);
    }

    public interface getMessageFailCallback1 {
        void getData(String data);
    }

    public static void resumeImg(Context context) {
        if (context != null) {
            Glide.with(context).resumeRequestsRecursive();
        }
    }

    public static void stopImg(Context context) {
        if (context != null) {
            Glide.with(context).pauseRequestsRecursive();
        }
    }

    public static void loadImg(Context context, String url, ImageView img) {
        if (img != null && !TextUtils.isEmpty(url)) {
            if (context != null) {
                Glide.with(context).load(url).crossFade().dontAnimate().error(R.mipmap.replace_hybb).into(img);
            }
        }
    }


    public static void DeleteContact(final Context context, String url, String targetId, final getDataCallback callback) {
        mySharedPreferences = MySharedPreferences.getInstance(context);
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.UserContactListDelete + "?currId=" + mySharedPreferences.getString("uid") + "&targetUserId=" + targetId, RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }

    public static void SearchPostNetResult(final Context context, String url, String userId, String trueNameKeyWords, String phoneKeyWords, int index, final getDataCallback callback) {
        mySharedPreferences = MySharedPreferences.getInstance(context);
        url = PortUtil.ContactSearchAddList + "?currId=" + userId + "&trueNameKeyWords=" + trueNameKeyWords + "&phoneKeyWords=" + phoneKeyWords + "&pageSize=20&pageIndex=" + index;
        Request<String> postRequest = NoHttp.createStringRequest(url, RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {
//                dialog.dismiss();
            }
        });
    }

    //搜索添加请求
    public static void SearchAddContact(String uid, String phone, String name, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.FriendAddCheck + "?currId=" + uid + "&targetUserId=0&groupName=1&reqType=0&addType=1&phone=" + phone + "&trueName=" + name, RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }

    //圈子列表页数据请求
    public static void CircleListRequest(String currId, String type, String community, String page, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.CircleList + "?currId=" + currId + "&circle_type=" + type + "&Community_id=" + community + "&pageSize=10&pageIndex=" + page, RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }


    //圈子列表页数据请求
    public static void NewsListRequest(String url, final getDataCallback callback, final getDataFailCallback failCallback) {
        Request<String> postRequest = NoHttp.createStringRequest(url, RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                Log.e("what", "开始请求");

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                failCallback.getData();
            }


            @Override
            public void onFinish(int what) {

            }
        });
    }


    //圈子列表页数据请求
    public static void NewsListRequest(final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest("", RequestMethod.POST);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }


    //圈子列表页数据请求
    public static void CircleListRequestMy(String currId, String url, Map params, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(url, RequestMethod.POST);
        postRequest.add(params);

        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }


    public static void CommonUseListRequestMy(String url, Map params, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(url, RequestMethod.POST);
        postRequest.add(params);

        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }

    //圈子发布数据请求
    public static void CirclePublishRequest(Map<String, String> param, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.CirclePublish, RequestMethod.POST);
        postRequest.add(param);
        CallServer.getInstance().add(1, postRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                callback.getData(response.get().toString());
            }

            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //圈子发布数据请求
    public static void rescuePublishRequest(Map<String, String> param, final getDataCallback callback, final getMessageFailCallback1 failCallback1) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.PublishOrAppendVehicleRescue, RequestMethod.POST);
        postRequest.add(param);
        CallServer.getInstance().add(1, postRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                callback.getData(response.get().toString());
            }

            @Override
            public void onFailed(int what, Response response) {
                failCallback1.getData(response.get().toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    public static void CircleCommentRequest(String uid, String circle_Id, String commentContent, String circle_Voice, String circle_AffixImgList, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.CircleComment + "?currId=" + uid + "&circle_Id=" + circle_Id + "&commentContent=" + commentContent + "&circle_Voice=" + circle_Voice + "&circle_AffixImgList=" + circle_AffixImgList, RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }

    public static void ParamGetRequest(String url,final getDataCallback callback){
        Request<String> postRequest = NoHttp.createStringRequest(url, RequestMethod.GET);
        postRequest.setConnectTimeout(10000);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    public static void ParamPostRequest(String url, Map<String, String> param, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(url, RequestMethod.POST);
        postRequest.add(param);
        postRequest.setConnectTimeout(10000);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    public static void ParamPostQueueRequest(String url, Map<String, String> param, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(url, RequestMethod.POST);
        postRequest.add(param);
        postRequest.setConnectTimeout(10000);
        CallServer.getInstance().add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    public static void ParamDialogPostRequest(String url, Map<String, String> param, final getDataCallback callback, final getDataFailCallback1 failCallback) {
        Request<String> postRequest = NoHttp.createStringRequest(url, RequestMethod.POST);
        postRequest.add(param);
        postRequest.setConnectTimeout(30000);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                failCallback.getData(response.getException());
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    public static void ParamFailMsgPostRequest(String url, Map<String, String> param, final getDataCallback callback, final getMessageFailCallback1 failCallback) {
        Request<String> postRequest = NoHttp.createStringRequest(url, RequestMethod.POST);
        postRequest.add(param);
        postRequest.setConnectTimeout(30000);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                failCallback.getData(response.get());
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //图片上传
    public static void PictureUpRequest(Map<String, String> param, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.DoUpLoad, RequestMethod.POST);
        postRequest.add(param);
        CallServer.getInstance().add(0, postRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                callback.getData(response.get().toString());
            }

            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    //图片上传
    public static void PictureUpRequestForMp3(Map<String, String> param, InputStream os, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.DoUpLoad, RequestMethod.POST);
        postRequest.add(param);


        CallServer.getInstance().add(0, postRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                callback.getData(response.get().toString());
            }

            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

    }


    //紧急联系人添加请求
    public static void UrgentContacttRequest(String currId, String targetUserId, String Status, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.SetUrgencyContact + "?currId=" + currId + "&targetUserId=" + targetUserId + "&Status=" + Status, RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }

    //音频文件流上传
    public static void mp3Utils(String id, String filePath, final getDataCallback callback) throws FileNotFoundException {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.StreamUpLoad + "?currid=" + id, RequestMethod.POST);
        File file = new File(filePath);

        /* FileInputSteam 输入流的对象 */
        FileInputStream fis = new FileInputStream(file);

        postRequest.setDefineRequestBody(fis, "multipart/form-data");

        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {


            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(UrlDecodeUtil.urlCode(response.get()));

            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //视频文件流上传
    public static void mp4Utils(String id, String filePath, final getDataCallback callback, final getMessageFailCallback1 failCallback1 ) throws FileNotFoundException {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.VideoStreamUpLoad + "?currid=" + id, RequestMethod.POST);
        File file = new File(filePath);
        FileBinary binary = new FileBinary(file,filePath);
        postRequest.add("file",binary);
        binary.setUploadListener(0, new OnUploadListener() {
            @Override
            public void onStart(int what) {
                LogUtils.e("--1--"+what);
            }

            @Override
            public void onCancel(int what) {
                LogUtils.e("--2--"+what);
            }

            @Override
            public void onProgress(int what, int progress) {
                LogUtils.e("----"+what+"   "+progress);
            }

            @Override
            public void onFinish(int what) {
                LogUtils.e("--3--"+what);
            }

            @Override
            public void onError(int what, Exception exception) {
                LogUtils.e("--4--"+what+"   "+exception.getMessage());
            }
        });
        /* FileInputSteam 输入流的对象 */
//        FileInputStream fis = new FileInputStream(file);
//        postRequest.setDefineRequestBody(fis, "UTF-8");

        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {


            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(UrlDecodeUtil.urlCode(response.get()));

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                failCallback1.getData(response.get());

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //    加入圈子请求
    public static void ContactListSearchNetResult(Context context, String searchKey, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.UserContactList + "?currId=" + MySharedPreferences.getUserId(context) + "&searchKeyWords=" + searchKey + "&pageIndex=1&pageSize=999", RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //圈子详情请求
    public static void CircleDetailRequest(String currId, String circleId, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.CircleCommentUser + "?currId=" + currId + "&circle_id=" + circleId + "&pageSize=10&pageIndex=1", RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }

    //圈子详情评论请求
    public static void CircleDetailCommentRequest(String currId, String circleId, String page, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.CircleCommentList + "?currId=" + currId + "&circle_id=" + circleId + "&pageSize=10&pageIndex=" + page, RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }

    public static void CircleJoinRequest(Context context, String circleId, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.CircleJoin + "?currId=" + MySharedPreferences.getUserId(context) + "&circle_Id=" + circleId, RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //最近联系数据请求
    public static void RecentListRequest(String uid, String batchUserIds, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.UserContactListBatchQuery + "?currId=" + uid + "&batchUserIds=" + batchUserIds, RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }


    //注册请求
    public static void RegisterRequest(Map<String, String> param, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.loginbyphone, RequestMethod.POST);
        postRequest.add(param);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }


    //重置密码验证码请求
    public static void GetCountMoney(String uid, final getDataCallback callback) {
        Request<String> postRequest = NoHttp.createStringRequest(PortUtil.balance + "?uid=" + uid, RequestMethod.GET);
        queue.add(1, postRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                callback.getData(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }


            @Override
            public void onFinish(int what) {

            }
        });
    }

}

