package com.hyzs.onekeyhelp.contact.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.MyApplication;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.contact.adapter.ContactSearchCheckLvAdapter;
import com.hyzs.onekeyhelp.contact.adapter.ContactSearchLvAdapter;
import com.hyzs.onekeyhelp.contact.adapter.ContactTelImportLvAdapter;
import com.hyzs.onekeyhelp.contact.around.activity.AroundActivity;
import com.hyzs.onekeyhelp.contact.bean.ContactSearchBean;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.contact.bean.TelContactBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.ConfirmDialog;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.hyzs.onekeyhelp.util.UrlDecodeUtil.urlCode;


public class ContactSearchActivity extends BaseActivity implements View.OnKeyListener, View.OnClickListener, TextWatcher {
    private static Context context;
    private static EditText mSearchEdit;
    private EditText mDialogNameEdit;
    private EditText mDialogTelEdit;
    private LinearLayout mTelImport, mSearchAround;
    private static LinearLayout mNoSearch;
    private static List<ContactSearchBean.ContactSearchResultListBean> list;
    private static List<ContactSearchBean.ContactSearchResultListBean> checkList;
    private static ContactSearchLvAdapter adapter;
    private static ContactSearchCheckLvAdapter checkAdapter;
    private static ListView mLv;
    private static ListView mCheckLv;
    private VRefresh refresh;
    private TextView mTitle, addByHand, mDialogPositive, mDialogNegative;
    private ImageView mBack, mClear;
    private Dialog addHandDialog = null;
    static MySharedPreferences mySharedPreferences;
    private boolean isUrgentStyle;  //设置紧急联系人方式添加好友
    private static int index = 1;
    private static boolean refreshFlag = false;
    private static TextView mSearchText;
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initdata();
                    Toast.makeText(context, "好友请求已发送！", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
//                    if (checkAdapter != null)
//                        checkAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private Dialog dialog;

    @Override
    protected void assignView() {
        context = this;
        refresh = (VRefresh) findViewById(R.id.activity_around_refresh);
        mSearchEdit = (EditText) findViewById(R.id.activity_contact_search_edit);
        mTelImport = (LinearLayout) findViewById(R.id.telephone_import);
        mSearchAround = (LinearLayout) findViewById(R.id.around_search);
        mNoSearch = (LinearLayout) findViewById(R.id.activity_contact_search_noResult);
        mLv = (ListView) findViewById(R.id.activity_contact_search_result_lv);
        mCheckLv = (ListView) findViewById(R.id.activity_contact_search_check_lv);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mClear = (ImageView) findViewById(R.id.activity_contact_search_clear);
        addByHand = (TextView) findViewById(R.id.contact_search_add_hand);
        mSearchText = (TextView) findViewById(R.id.activity_contact_search_text);
        refresh.setView(this, mCheckLv);
    }

    @Override
    protected void initView() {
        mTitle.setText("搜索");
        isUrgentStyle = getIntent().getBooleanExtra("isUrgent", false);
        dialog = ConfirmDialog.createConfirmLoading(context, "同时设置为紧急联系人", "取消", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend(mDialogTelEdit.getText().toString(), mDialogNameEdit.getText().toString(), "0");
                dialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend(mDialogTelEdit.getText().toString(), mDialogNameEdit.getText().toString(), "1");
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void initListener() {
        mSearchEdit.setOnKeyListener(this);
        mSearchEdit.addTextChangedListener(this);
        addByHand.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mClear.setOnClickListener(this);
        mTelImport.setOnClickListener(this);
        mSearchAround.setOnClickListener(this);
        refresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                index++;
                refreshFlag = true;
                initData2();
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                index = 1;
                refreshFlag = false;
                initData2();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_search;
    }

    @Override
    protected void initData() {

    }

    private void initData2() {
        checkList = new ArrayList<>();
        mySharedPreferences = MySharedPreferences.getInstance(ContactSearchActivity.this);
        NetRequest.SearchPostNetResult(context, PortUtil.SearchContact, mySharedPreferences.getString("uid"), "", "", index, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                checkList = new Gson().fromJson(urlCode(data), ContactSearchBean.class).getContactSearchResultList();
                initCheckListView();
            }
        });
    }

    private static List<ContactSearchBean.ContactSearchResultListBean> list1 = new ArrayList<>();

    private void initCheckListView() {
        refresh.setLoading(false);
        refresh.setRefreshing(false);
        if (refreshFlag) {
        } else {
            list1.clear();
        }
        list1.addAll(checkList);
        if (checkAdapter == null) {
            if (list1.size() == 0) {
                mNoSearch.setVisibility(View.GONE);
                mLv.setVisibility(View.GONE);
            } else {
                mNoSearch.setVisibility(View.GONE);
                mLv.setVisibility(View.VISIBLE);
                checkAdapter = new ContactSearchCheckLvAdapter(list1, context);
                mCheckLv.setAdapter(checkAdapter);
            }
        } else {
            checkAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkAdapter = null;
    }

    protected static void initdata() {
        checkList = new ArrayList<>();
        mySharedPreferences = MySharedPreferences.getInstance(MyApplication.getAppContext());
        NetRequest.SearchPostNetResult(context, PortUtil.SearchContact, mySharedPreferences.getString("uid"), "", "", index, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                checkList = new Gson().fromJson(urlCode(data), ContactSearchBean.class).getContactSearchResultList();
                list1.clear();
                list1.addAll(checkList);
                if (list1.size() == 0) {
                    mNoSearch.setVisibility(View.VISIBLE);
                    mLv.setVisibility(View.GONE);
                } else {
                    mNoSearch.setVisibility(View.GONE);
                    mLv.setVisibility(View.VISIBLE);
                    checkAdapter = new ContactSearchCheckLvAdapter(list1, context);
                    mCheckLv.setAdapter(checkAdapter);
                }
            }
        });
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(ContactSearchActivity.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            search();
        }
        return false;
    }

    private void search() {
        String text = mSearchEdit.getText().toString();
        String trueNameKeyWords = null;
        String phoneKeyWords = null;
        if (TextUtils.isEmpty(text)) {
            mClear.setVisibility(View.INVISIBLE);
            mNoSearch.setVisibility(View.GONE);
            mLv.setVisibility(View.GONE);
            mCheckLv.setVisibility(View.VISIBLE);
            mSearchText.setVisibility(View.GONE);
        } else {
            mClear.setVisibility(View.VISIBLE);
            if (textType(text)) {
                phoneKeyWords = text;
                trueNameKeyWords = "";
            } else {
                phoneKeyWords = "";
                trueNameKeyWords = text;
            }
            NetRequest.SearchPostNetResult(context, PortUtil.SearchContact, mySharedPreferences.getString("uid"), trueNameKeyWords, phoneKeyWords, index, new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    list = new Gson().fromJson(urlCode(data), ContactSearchBean.class).getContactSearchResultList();
                    if (list.size() == 0) {
                        mSearchText.setVisibility(View.GONE);
                        mNoSearch.setVisibility(View.VISIBLE);
                        mLv.setVisibility(View.GONE);
                        mCheckLv.setVisibility(View.GONE);
                    } else {
                        mSearchText.setVisibility(View.VISIBLE);
                        mNoSearch.setVisibility(View.GONE);
                        mLv.setVisibility(View.VISIBLE);
                        mCheckLv.setVisibility(View.GONE);
                        adapter = new ContactSearchLvAdapter(list, context, isUrgentStyle);
                        mLv.setAdapter(adapter);
                    }
                }
            });
        }
    }

    private static boolean textType(String text) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(text).matches();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_contact_search_clear:
                mSearchEdit.getText().clear();
                mCheckLv.setVisibility(View.VISIBLE);
                mNoSearch.setVisibility(View.GONE);
                mLv.setVisibility(View.GONE);
                break;
            case R.id.telephone_import:
                Intent intent1 = new Intent(this, ContactTelImportActivity.class);
                if (isUrgentStyle) {
                    intent1.putExtra("isUrgent",true);
                }
                startActivity(intent1);
                break;
            case R.id.around_search:
                Intent intent = new Intent(this, AroundActivity.class);
                if (isUrgentStyle) {
                    intent.putExtra("isUrgent",true);
                }
                startActivity(intent);
                break;
            case R.id.contact_search_add_hand:
                View DialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog_search_add_hand, null);
                assignDialogView(DialogView);
                addHandDialog = new AlertDialog.Builder(ContactSearchActivity.this).create();
                addHandDialog.show();
                addHandDialog.getWindow().setContentView(DialogView);
                addHandDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                break;
            case R.id.dialog_search_add_hand_negative:
                if (addHandDialog != null) {
                    addHandDialog.dismiss();
                }
                break;
            case R.id.dialog_search_add_hand_positive:
                addHandDialog.dismiss();
                if (isUrgentStyle) {
                    addFriend(mDialogTelEdit.getText().toString(), mDialogNameEdit.getText().toString(), "1");
                    return;
                }
                dialog.show();
                break;
        }
    }

    private void assignDialogView(View dialogView) {
        mDialogNameEdit = (EditText) dialogView.findViewById(R.id.dialog_search_add_hand_name);
        mDialogTelEdit = (EditText) dialogView.findViewById(R.id.dialog_search_add_hand_tel);
        mDialogPositive = (TextView) dialogView.findViewById(R.id.dialog_search_add_hand_positive);
        mDialogNegative = (TextView) dialogView.findViewById(R.id.dialog_search_add_hand_negative);
        mDialogNegative.setOnClickListener(this);
        mDialogPositive.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        search();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void onResume() {
        super.onResume();
        index = 1;
        refreshFlag = false;
        initData2();
        MobclickAgent.onPageStart("ContactSerchActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ContactSerchActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    private void addFriend(String number, String name, String type) {
        String url = PortUtil.FriendAddCheck;
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(context));
        map.put("targetUserId", "0");
        map.put("groupName", "1");
        map.put("reqType", "0");
        map.put("addType", "1");
        map.put("phone", number);
        map.put("trueName", name);
        map.put("isSetUrgencyContact", type);
        NetRequest.ParamPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if ("10000".equals(jsonObject.getString("code"))) {
                        ToastSingleUtil.showToast(context, "请求以发送，等待对方确认");
                    } else ToastSingleUtil.showToast(context, jsonObject.getString("message"));
                } catch (Exception e) {
                    ToastSingleUtil.showToast(context, "添加失败");
                    e.printStackTrace();
                }
            }
        });
    }
}
