package com.hyzs.onekeyhelp.contact.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.activity.ContactSearchActivity;
import com.hyzs.onekeyhelp.contact.adapter.PinnedLvAdapter;
import com.hyzs.onekeyhelp.contact.bean.ContactListEntity;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.ConfirmDialog;
import com.hyzs.onekeyhelp.util.EditNotInputSpace;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MyAsycnTask;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;
import com.hyzs.onekeyhelp.util.OkHttpUtil;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.MySideBar;
import com.hyzs.onekeyhelp.widget.PinnedHeaderListView;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.hyzs.onekeyhelp.widget.MySideBar.TEXT_ALIGN_CENTER;


public class ContactListFragment extends Fragment implements TextWatcher, MyAsycnTask.MyCallBack, View.OnClickListener, MySideBar.OnSelectIndexItemListener, AdapterView.OnItemLongClickListener {
    private static final String TAG = "ContactListFragment";
    PinnedLvAdapter adapter;
    PinnedHeaderListView pinnedHeaderListView;
    MySideBar sideBar;
    private char number[] = {'#', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private int sectionArray[] = new int[27];
    private EditText mEdit;
    private ImageView mSearch;
    String searchChar = null;
    private List<ContactListEntity.ContactListBean> list;
    PopupWindow longKickPop;
    MySharedPreferences mySharePreferences;
    Context mContext;
    int SectionPosition, ItemPosition, TotalPosition;
    List<List<ContactListEntity.ContactListBean>> resource = new ArrayList<>();
    private LinearLayout mAdd;
    private PopupWindow removePop;
    private TextView setUrgentContact;
    private CircleImageView mUserIcon;
    private TextView mUserName, mAddPop;
    private TextView mUserPhone;
    private Dialog confirmDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, null);
        mContext = getActivity();
        assignView(view);
        initView();
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        MobclickAgent.onPageStart("ContactList" + "Fragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            initData();
    }

    private void initView() {
        sideBar.setTextColor(Color.BLACK);
        sideBar.setTextAlign(TEXT_ALIGN_CENTER);
        sideBar.setMaxOffset(36);
        sideBar.setTextAlign(TEXT_ALIGN_CENTER);
        sideBar.setLazyRespond(true);
    }

    private void initListener() {
        EditNotInputSpace.setEditTextInhibitInputSpace(mEdit);
        mUserIcon.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        sideBar.setOnSelectIndexItemListener(this);
        mEdit.addTextChangedListener(this);
        pinnedHeaderListView.setOnItemLongClickListener(this);
        pinnedHeaderListView.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {

                ContactListEntity.ContactListBean bean = resource.get(section).get(position);
                mySharePreferences.setString("FriendsName", bean.getUserNick());
                mySharePreferences.setString("Img", bean.getAvatar());
                NotifyMsgCountUtil.notifyMsg(bean.getTargetUserId() + "");
                Intent intent = new Intent();
                intent.putExtra("one", bean.getTargetUserId() + "");
                intent.putExtra("userName", bean.getUserNick());
                intent.setClass(mContext, IntentChatActivity.class);
                mContext.startActivity(intent);
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });
    }

    private void assignView(View view) {
        mEdit = (EditText) view.findViewById(R.id.fragment_contact_edit);
        mSearch = (ImageView) view.findViewById(R.id.fragment_contact_search);
        pinnedHeaderListView = (PinnedHeaderListView) view.findViewById(R.id.pinnedListView);
        sideBar = (MySideBar) view.findViewById(R.id.sideBar);
        mAdd = (LinearLayout) view.findViewById(R.id.fragment_contact_list_add);
        mUserIcon = (CircleImageView) view.findViewById(R.id.fragment_contact_list_icon);
        mUserName = (TextView) view.findViewById(R.id.fragment_contact_list_name);
        mUserPhone = (TextView) view.findViewById(R.id.fragment_contact_list_phone);
        mAddPop = (TextView) view.findViewById(R.id.fragment_contact_list_pop);
    }

    private void initData() {
        mySharePreferences = MySharedPreferences.getInstance(getActivity());
        if (!MySharedPreferences.isLogin(mContext)) {
            return;
        }
        MyAsycnTask asycnTask = new MyAsycnTask(mContext, this);
        asycnTask.execute("GET", PortUtil.UserContactList + "?currId=" + MySharedPreferences.getUserId(mContext) + "&searchKeyWords=&pageIndex=1&pageSize=999");
        Map<String, String> param = new ArrayMap<>();
        param.put("action", "Get_UserVerify");
        param.put("currId", MySharedPreferences.getUserId(mContext));
        NetRequest.ParamPostRequest(PortUtil.VersionUpdate, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(UrlDecodeUtil.urlCode(data));
                    if ("10000".equals(jsonObject.getString("code"))) {
                        int popCount = jsonObject.getInt("v_code");
                        if (popCount > 0) {
                            mAddPop.setText(popCount + "");
                            mAddPop.setVisibility(View.VISIBLE);
                        } else mAddPop.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private int getNumber(String target) {
        char tar = target.toCharArray()[0];
        int num = 0;
        int total = 0;
        for (int i = 0; i < number.length; i++) {
            if (number[i] == tar) {
                num = i;
            }
        }
        for (int i = 0; i < num; i++) {
            total += sectionArray[i] + 1;
        }
        return total;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_contact_list_icon:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    ToastUtils.showShort(getActivity(), "请先登录.");
                    return;
                }
                Intent intent = new Intent(getActivity(), MineHomeActivity.class);
                intent.putExtra("targetUserId", MySharedPreferences.getUserId(getActivity()));
                startActivity(intent);
                break;
            case R.id.fragment_contact_search:
                mEdit.getText().clear();
                break;
            case R.id.longkick_pop_delete:
                clickType = "删除";
                confirmDialog = ConfirmDialog.createProgressLoading(mContext, "是否删除此联系人", "删除", "取消", ContactListFragment.this);
                confirmDialog.show();
                break;
            case R.id.fragment_contact_list_add:
                if (!MySharedPreferences.isLogin(getActivity())) {
                    ToastUtils.showShort(getActivity(), "您还未登录");
                    return;
                }
                startActivity(new Intent(getActivity(), ContactSearchActivity.class));
                break;
            case R.id.longkick_pop_remove:
                if (removePop == null) {
                    initRemovePop();
                }
                longKickPop.dismiss();
                removePop.showAtLocation(pinnedHeaderListView, Gravity.BOTTOM, 0, mySharePreferences.getInt("height", 150));
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 0.7f;
                getActivity().getWindow().setAttributes(params);
                break;
            case R.id.longkick_pop_back:
                longKickPop.dismiss();
                break;
            case R.id.remove_family:
                groupIndex = 0;
                groupTitle = "添加至家人分组";
                moveGroup();
                break;
            case R.id.remove_frient:
                groupIndex = 1;
                groupTitle = "添加至朋友分组";
                moveGroup();
                break;
            case R.id.remove_neighbor:
                groupIndex = 2;
                groupTitle = "添加至邻居分组";
                moveGroup();
                break;
            case R.id.longkick_pop_setUrgent:
                setHelp();
                break;
            case R.id.longkick_pop_info:
                Intent intent1 = new Intent(getActivity(), MineHomeActivity.class);
                intent1.putExtra("targetUserId", resource.get(SectionPosition).get(ItemPosition).getTargetUserId() + "");
                getActivity().startActivity(intent1);
                longKickPop.dismiss();
                break;
            case R.id.dialog_confirm_left:
                if (clickType.equals("删除")) {
                    deleteItem();
                }
                confirmDialog.dismiss();
                break;
            case R.id.dialog_confirm_right:
                switch (clickType) {
                    case "紧急":
                        setHelpStatus();
                        break;
                    case "删除":
                        break;
                    case "移动":
                        removeItem(groupTitle, groupIndex);
                        break;
                }
                confirmDialog.dismiss();
                break;
        }
    }

    private void moveGroup() {
        clickType = "移动";
        confirmDialog = ConfirmDialog.createProgressLoading(mContext, groupTitle, "取消", "移动", ContactListFragment.this);
        confirmDialog.show();
    }

    private int groupIndex = 0;
    private String groupTitle = "";
    private String clickType = "";

    private void setHelpStatus() {
        if ("否".equals(resource.get(SectionPosition).get(ItemPosition).getUrgentFlag().trim())) {
            NetRequest.UrgentContacttRequest(mySharePreferences.getString("uid"), resource.get(SectionPosition).get(ItemPosition).getTargetUserId() + "", "Y", new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                    if ("10000".equals(bean.getCode())) {
                        ToastUtils.showShort(getActivity(), "设置为紧急联系人成功");
                        resource.get(SectionPosition).get(ItemPosition).setUrgentFlag("是");
                    } else ToastUtils.showShort(getActivity(), bean.getMessage() + "");
                    longKickPop.dismiss();
                }
            });
        } else {
            NetRequest.UrgentContacttRequest(mySharePreferences.getString("uid"), resource.get(SectionPosition).get(ItemPosition).getTargetUserId() + "", "N", new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                    if ("10000".equals(bean.getCode())) {
                        ToastUtils.showShort(getActivity(), "取消紧急联系人成功");
                        resource.get(SectionPosition).get(ItemPosition).setUrgentFlag("否");
                    } else ToastUtils.showShort(getActivity(), bean.getMessage() + "");
                    longKickPop.dismiss();
                }
            });
        }
    }

    private void setHelp() {
        clickType = "紧急";
        if (resource.get(SectionPosition).get(ItemPosition).getUrgentFlag().equals("是")) {
            confirmDialog = ConfirmDialog.createProgressLoading(mContext, "取消紧急联系人", "取消", "确认", ContactListFragment.this);
        } else {
            confirmDialog = ConfirmDialog.createProgressLoading(mContext, "设为紧急联系人", "取消", "确认", ContactListFragment.this);
        }
        confirmDialog.show();
    }

    private void removeItem(final String grouping, final int groupName) {
        String url = PortUtil.MovieToGroup + "?currId=" + MySharedPreferences.getUserId(mContext) + "&targetUserId=" + resource.get(SectionPosition).get(ItemPosition).getTargetUserId() + "&groupName=" + groupName;
        OkHttpUtil.newInstamce().getAsynHttp(url, new OkHttpUtil.HttpCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) {
                try {
                    String s = URLDecoder.decode(response.body().string(), "UTF-8");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            removePop.dismiss();
                            Toast.makeText(mContext, "成功将联系人" + grouping, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void deleteItem() {

        NetRequest.DeleteContact(getActivity(), "", resource.get(SectionPosition).get(ItemPosition).getTargetUserId() + "", new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                longKickPop.dismiss();
                resource.get(SectionPosition).remove(ItemPosition);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onSelectIndexItem(String index) {
        pinnedHeaderListView.setSelection(getNumber(index));
    }


    @Override
    public void SendResult(String result) {
        int A = 0, B = 0, C = 0, D = 0, E = 0, F = 0, G = 0, H = 0, I = 0, J = 0, K = 0, L = 0, M = 0, N = 0, O = 0, P = 0, Q = 0, R = 0, S = 0, T = 0, U = 0, V = 0, W = 0, X = 0, Y = 0, Z = 0, num = 0;
        Gson gson = new Gson();
        try {
            ContactListEntity bean = gson.fromJson(result, ContactListEntity.class);
            if (TextUtils.isEmpty(MySharedPreferences.getInstance(getActivity()).getString("myImg"))) {
                mUserIcon.setImageResource(com.hyzs.onekeyhelp.R.mipmap.icon_replace);
            } else
                NetRequest.loadImg(getActivity(), MySharedPreferences.getInstance(getActivity()).getString("myImg"), mUserIcon);
            mUserName.setText(bean.getUserNick());
            mUserPhone.setText("已绑定电话： " + bean.getPhone());
            list = bean.getContactList();
            for (int i = 0; i < list.size(); i++) {
                switch (list.get(i).getSortFirstChar().trim()) {
                    case "A":
                    case "a":
                        A += 1;
                        break;
                    case "B":
                    case "b":
                        B += 1;
                        break;
                    case "C":
                    case "c":
                        C += 1;
                        break;
                    case "D":
                    case "d":
                        D += 1;
                        break;
                    case "E":
                    case "e":
                        E += 1;
                        break;
                    case "F":
                    case "f":
                        F += 1;
                        break;
                    case "g":
                    case "G":
                        G += 1;
                        break;
                    case "H":
                    case "h":
                        H += 1;
                        break;
                    case "I":
                    case "i":
                        I += 1;
                        break;
                    case "J":
                    case "j":
                        J += 1;
                        break;
                    case "K":
                    case "k":
                        K += 1;
                        break;
                    case "L":
                    case "l":
                        L += 1;
                        break;
                    case "M":
                    case "m":
                        M += 1;
                        break;
                    case "N":
                    case "n":
                        N += 1;
                        break;
                    case "O":
                    case "o":
                        O += 1;
                        break;
                    case "P":
                    case "p":
                        P += 1;
                        break;
                    case "Q":
                    case "q":
                        Q += 1;
                        break;
                    case "R":
                    case "r":
                        R += 1;
                        break;
                    case "S":
                    case "s":
                        S += 1;
                        break;
                    case "T":
                    case "t":
                        T += 1;
                        break;
                    case "U":
                    case "u":
                        U += 1;
                        break;
                    case "V":
                    case "v":
                        V += 1;
                        break;
                    case "W":
                    case "w":
                        W += 1;
                        break;
                    case "X":
                    case "x":
                        X += 1;
                        break;
                    case "Y":
                    case "y":
                        Y += 1;
                        break;
                    case "Z":
                    case "z":
                        Z += 1;
                        break;
                    default:
                        num += 1;
                        break;
                }
            }
            sectionArray[0] = num;
            sectionArray[1] = A;
            sectionArray[2] = B;
            sectionArray[3] = C;
            sectionArray[4] = D;
            sectionArray[5] = E;
            sectionArray[6] = F;
            sectionArray[7] = G;
            sectionArray[8] = H;
            sectionArray[9] = I;
            sectionArray[10] = J;
            sectionArray[11] = K;
            sectionArray[12] = L;
            sectionArray[13] = M;
            sectionArray[14] = N;
            sectionArray[15] = O;
            sectionArray[16] = P;
            sectionArray[17] = Q;
            sectionArray[18] = R;
            sectionArray[19] = S;
            sectionArray[20] = T;
            sectionArray[21] = U;
            sectionArray[22] = V;
            sectionArray[23] = W;
            sectionArray[24] = X;
            sectionArray[25] = Y;
            sectionArray[26] = Z;

            resource = fillResource();
            adapter = new PinnedLvAdapter(getActivity(), resource);
            pinnedHeaderListView.setAdapter(adapter);
        } catch (Exception e) {
//            Toast.makeText(mContext, "网络未连接，请检查网络！", Toast.LENGTH_SHORT).show();
        }
    }


    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchChar = mEdit.getText().toString();
        fuzzySearch(searchChar);
//

//
//        List<ContactListEntity.ContactListBean> searchList = null;
//        List<List<ContactListEntity.ContactListBean>> searchList1 = new ArrayList<>();
//        Pattern pattern = Pattern.compile("^[a-zA-Z]");
//        Pattern numPattern = Pattern.compile("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$");
//        if (!TextUtils.isEmpty(searchChar)) {
//            if (searchChar.length() == 1 & searchChar.matches(pattern.pattern())) {
//                int num = getNum(searchChar);
//                if (searchChar.matches(pattern.pattern()) & num != -1) {
//                    for (int i = 0; i < sectionArray.length; i++) {
//                        searchList = new ArrayList<>();
//                        if (i == num) {
//                            for (ContactListEntity.ContactListBean bean : resource.get(num)) {
//                                searchList.add(bean);
//                            }
//                        }
//                        searchList1.add(searchList);
//                    }
//                    adapter = new PinnedLvAdapter(mContext, searchList1);
//                    pinnedHeaderListView.setAdapter(adapter);
//                }
//            } else {
//                boolean flag = false;
//                if (searchChar.matches(numPattern.pattern())) {
//                    List<List<ContactListEntity.ContactListBean>> lists = new ArrayList<>();
//                    List<ContactListEntity.ContactListBean> beanList = null;
//                    for (List<ContactListEntity.ContactListBean> list : resource) {
//                        beanList = new ArrayList<>();
//                        for (ContactListEntity.ContactListBean bean : list) {
//                            if (searchChar.trim().equals(bean.getPhone())) {
//                                flag = true;
//                                beanList.add(bean);
//                            }
//                        }
//                        lists.add(list);
//                    }
//                    if (flag) {
//                        adapter = new PinnedLvAdapter(mContext, lists);
//                        pinnedHeaderListView.setAdapter(adapter);
//                    } else {
//                        Toast.makeText(mContext, "没有该用户", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    List<List<ContactListEntity.ContactListBean>> lists = new ArrayList<>();
//                    List<ContactListEntity.ContactListBean> beanList = null;
//                    for (List<ContactListEntity.ContactListBean> list : resource) {
//                        beanList = new ArrayList<>();
//                        for (ContactListEntity.ContactListBean bean : list) {
//                            if (bean.getUserNick().contains(searchChar.trim())) {
//                                flag = true;
//                                beanList.add(bean);
//                            }
//                        }
//                        lists.add(list);
//                    }
//                    if (flag) {
//                        adapter.setContactBeanList(lists);
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            searchChar = mEdit.getText().toString().substring(0, 1);
//
////            if (searchChar.matches(pattern.pattern())) {
////                pinnedHeaderListView.setSelection(getNumber(searchChar.toUpperCase()));
////            }
//        } else {
//            pinnedHeaderListView.setSelection(0);
//        }
    }

    private void fuzzySearch(String searchChar) {
        if (searchChar.length() == 0) {
            adapter = new PinnedLvAdapter(mContext, resource);
            pinnedHeaderListView.setAdapter(adapter);
            return;
        }
        List<List<ContactListEntity.ContactListBean>> fuzzyList = new ArrayList<>();
        for (List<ContactListEntity.ContactListBean> l : resource) {
            List<ContactListEntity.ContactListBean> fuzzyList1 = new ArrayList<>();
            for (ContactListEntity.ContactListBean b : l) {
                if (searchChar.length() == 1) {
                    if (b.getSortFirstChar().toUpperCase().trim().equals(searchChar.toUpperCase())) {
                        fuzzyList1.add(b);
                        continue;
                    }
                }
                if (b.getUserNick().contains(searchChar) || b.getTrueName().contains(searchChar) || b.getPhone().contains(searchChar)) {
                    fuzzyList1.add(b);
                }
            }
            fuzzyList.add(fuzzyList1);
        }
        adapter = new PinnedLvAdapter(mContext, fuzzyList);
        pinnedHeaderListView.setAdapter(adapter);
    }


    public void afterTextChanged(Editable s) {

    }

    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != view.getTag() && "1".equals(view.getTag())) {
            return false;
        }
        SectionPosition = adapter.getSectionForPosition(position);
        ItemPosition = adapter.getPositionInSectionForPosition(position);
        TotalPosition = position - SectionPosition + 1;
        if (longKickPop == null) {
            initPop();
        }
        longKickPop.showAtLocation(pinnedHeaderListView, Gravity.BOTTOM, 0, mySharePreferences.getInt("height", 150));
        if ("否".equals(resource.get(SectionPosition).get(ItemPosition).getUrgentFlag().trim())) {
            setUrgentContact.setText("设为紧急联系人");
        } else {
            setUrgentContact.setText("取消紧急联系人");
        }
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.7f;
        getActivity().getWindow().setAttributes(params);
        return true;
    }

    public void initPop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_longkick_pop, null, false);   //获取pop视图
//        int width = (int) DensityUtils.px2dp(getActivity(), 1000);
        TextView delete = (TextView) view.findViewById(R.id.longkick_pop_delete);
        TextView remove = (TextView) view.findViewById(R.id.longkick_pop_remove);
        TextView back = (TextView) view.findViewById(R.id.longkick_pop_back);
        TextView info = (TextView) view.findViewById(R.id.longkick_pop_info);
        setUrgentContact = (TextView) view.findViewById(R.id.longkick_pop_setUrgent);
        longKickPop = new PopupWindow(view, 1000, ActionBar.LayoutParams.WRAP_CONTENT, true);
        longKickPop.setTouchable(true); //设置PopupWindow可触摸
        longKickPop.setOutsideTouchable(true);
        longKickPop.setBackgroundDrawable(new BitmapDrawable());
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        info.setOnClickListener(this);
        longKickPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
        remove.setOnClickListener(this);
        setUrgentContact.setOnClickListener(this);
    }

    private void initRemovePop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_remove_pop, null, false);
//        int width = (int) DensityUtils.px2dp(getActivity(), 1000);
        removePop = new PopupWindow(view, 1000, ActionBar.LayoutParams.WRAP_CONTENT, true);
        removePop.setTouchable(true); //设置PopupWindow可触摸
        removePop.setOutsideTouchable(true);
        removePop.setBackgroundDrawable(new BitmapDrawable());
        removePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
        TextView family = (TextView) view.findViewById(R.id.remove_family);
        TextView friend = (TextView) view.findViewById(R.id.remove_frient);
        TextView neighbor = (TextView) view.findViewById(R.id.remove_neighbor);
        family.setOnClickListener(this);
        friend.setOnClickListener(this);
        neighbor.setOnClickListener(this);
    }

    public List<List<ContactListEntity.ContactListBean>> fillResource() {
        int flag = 0;
        List<List<ContactListEntity.ContactListBean>> resource = new ArrayList<>();
        List<ContactListEntity.ContactListBean> list1 = null;
        for (int i = 0; i < sectionArray.length; i++) {
            list1 = new ArrayList<>();
            for (int j = 0; j < sectionArray[i]; j++) {
                list1.add(list.get(flag + j));
            }
            flag += sectionArray[i];
            boolean add = resource.add(list1);
        }
        return resource;
    }

    public int getNum(String s) {
        int mark = -1;
        for (int i = 0; i < number.length; i++) {
            if (s.equalsIgnoreCase(number[i] + "")) {
                return i;

            }
        }
        return mark;
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ContactList" + "Fragment");
    }

}