package com.hyzs.onekeyhelp.contact.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.contact.adapter.ContactTelImportLvAdapter;
import com.hyzs.onekeyhelp.contact.bean.ContactsBean;
import com.hyzs.onekeyhelp.contact.bean.TelContactBean;
import com.hyzs.onekeyhelp.contact.bean.TelImportBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.InPutUtils;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.key;
import static com.hyzs.onekeyhelp.R.mipmap.phone;

public class ContactTelImportActivity extends BaseActivity implements View.OnClickListener {
    // TODO: 2017/4/19 服务器接受4MB内容，手机号过多StringBuilder的长度会过长导致报错，需要分页
    private TextView mTitle;
    private ImageView mBack;
    private ListView mLv;
    private ContactTelImportLvAdapter adapter;
    private static final int BAIDU_READ_PHONE_STATE = 100;
    private List<TelContactBean> list, resource, newResource;
    private TelImportBean bean;
    private VRefresh vRefresh;
    private boolean isUrgentStyle;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list = getSystemContactInfos();
            resource = new ArrayList<>();
            checkTel();
        }
    };

    @Override
    protected void assignView() {
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mLv = (ListView) findViewById(R.id.activity_contact_tel_import_lv);
        vRefresh = (VRefresh) findViewById(R.id.activity_contact_tel_import_refresh);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}
                        , BAIDU_READ_PHONE_STATE);
            } else {
                handler.sendEmptyMessage(1);
            }
        } else {
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "无访问权限", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                handler.sendEmptyMessage(1);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initView() {
        mTitle.setText("本机导入");
        isUrgentStyle = getIntent().getBooleanExtra("isUrgent",false);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        vRefresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_tel_import;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    private static final String[] CONTACTOR_ION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    public List<TelContactBean> getSystemContactInfos() {
        List<TelContactBean> infos = new ArrayList<TelContactBean>();
        Cursor cursor = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, CONTACTOR_ION, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                TelContactBean info = new TelContactBean();
                String contactName = cursor.getString(0);
                String phoneNumber = cursor.getString(1);
                info.setContactName(contactName);
                info.setPhoneNumber(phoneNumber);
                infos.add(info);
                info = null;
            }
            cursor.close();
        }
        return infos;
    }

    /**
     * 获取手机联系人信息
     *
     * @return List<HashMap<String, String>>
     */
    public List<HashMap<String, String>> findContacts() {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
// 查询联系人
        Cursor contactsCursor = this.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.PhoneLookup.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
// 姓名的索引
        int nameIndex = 0;
// 联系人姓名
        String name = null;
// 联系人头像ID
        String photoId = null;
// 联系人的ID索引值
        String contactsId = null;
// 查询联系人的电话号码
        Cursor phoneCursor = null;
        while (contactsCursor.moveToNext()) {
            nameIndex = contactsCursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            name = contactsCursor.getString(nameIndex);
            photoId = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.PhoneLookup.PHOTO_ID));
            contactsId = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts._ID));
            phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactsId, null, null);
// 遍历联系人号码（一个人对应于多个电话号码）
            while (phoneCursor.moveToNext()) {
                HashMap<String, String> phoneMap = new HashMap<String, String>();
// 添加联系人姓名
                phoneMap.put("name", name);
// 添加联系人头像
                phoneMap.put("photo", photoId);
// 添加电话号码
                phoneMap.put("phone", phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
// 添加号码类型（住宅电话、手机号码、单位电话等）
                phoneMap.put("type", getString(ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(phoneCursor.getInt(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)))));
                list.add(phoneMap);
            }
            phoneCursor.close();
        }
        contactsCursor.close();
        return list;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ContactTelImPortActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ContactTelImPortActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    public void checkTel() {
        StringBuilder sb = new StringBuilder();
        if (list.size() == 0) {
            return;
        }
        for (TelContactBean bean : list) {
            String number = bean.getPhoneNumber();
            number = number.replaceAll("^(\\+86)", "").replaceAll("^(86)", "").replaceAll("-", "").replaceAll(" ","").trim();
            if (InPutUtils.isMobilePhone(number)) {
                sb.append(number + "|");
                bean.setPhoneNumber(number);
                resource.add(bean);
            }
        }
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(ContactTelImportActivity.this));
        param.put("str", sb.toString().substring(0, sb.length() - 1));
        NetRequest.ParamPostRequest(PortUtil.TelImport, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), TelImportBean.class);
                for (int i = 0; i < bean.getContactPhoneImportCheck().size(); i++) {
                    TelImportBean.ContactPhoneImportCheckBean bean1 = bean.getContactPhoneImportCheck().get(i);
                    TelContactBean map = resource.get(i);
                    map.setIsExist(bean1.getIsExists() + "");
                    map.setId(bean1.getUserId() + "");
                }
                adapter = new ContactTelImportLvAdapter(ContactTelImportActivity.this, resource,isUrgentStyle);
                mLv.setAdapter(adapter);
            }
        });
    }
}
