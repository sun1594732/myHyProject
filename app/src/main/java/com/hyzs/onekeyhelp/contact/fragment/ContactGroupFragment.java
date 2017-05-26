package com.hyzs.onekeyhelp.contact.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.IntentChatActivity;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.contact.adapter.MyExpandableListViewAdapter;
import com.hyzs.onekeyhelp.contact.bean.ContactListEntity;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.contact.bean.GroupUserBean;
import com.hyzs.onekeyhelp.contact.bean.IdentityTypeBean;
import com.hyzs.onekeyhelp.mine.activity.MineHomeActivity;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.ConfirmDialog;
import com.hyzs.onekeyhelp.util.DensityUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.NotifyMsgCountUtil;
import com.hyzs.onekeyhelp.util.OkHttpUtil;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ContactGroupFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ContactGroupFragment";
    private Context mContext;
    private ExpandableListView mLv;
    private MyExpandableListViewAdapter adapter;
    MySharedPreferences mySharePreferences;
    private List<String> group_list;
    private List<List<ContactListEntity.ContactListBean>> topItemList;
    private List<List<ContactListEntity.ContactListBean>> bottomItemList;

    int SectionPosition, ItemPosition, TotalPosition;
    PopupWindow longKickPop;
    private PopupWindow removePop;
    private IdentityTypeBean bean;
    private Dialog confirmDialog;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    list2.clear();
                    list2.addAll(0, topItemList);
                    list2.addAll(list2.size(), bottomItemList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private List<String> list1;
    private List<List<ContactListEntity.ContactListBean>> list2;
    private TextView setUrgent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_contact_group, null);
        assignView(view);
        initListener();
        initView();
        return view;
    }

    private void initListener() {
        mLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if ((int) view.getTag(R.id.contact_search_add_hand) == -1) {
                    return false;
                }
                SectionPosition = (int) view.getTag(R.id.contact_list);
                ItemPosition = (int) view.getTag(R.id.contact_search_add_hand);
                if (null != view.getTag() && "1".equals(view.getTag())) {
                    return false;
                }
                if (SectionPosition == 3) {
                    clickType = "取消";
                    confirmDialog = ConfirmDialog.createProgressLoading(mContext, "取消紧急联系人", "取消", "确认", ContactGroupFragment.this);
                    confirmDialog.show();
                    return true;
                }
                if (SectionPosition > 3) {
                    return false;
                }
                if (longKickPop == null) {
                    initPop();
                }
                if (list2.get(SectionPosition).get(ItemPosition).getUrgentFlag().equals("否")) {
                    setUrgent.setText("设为紧急联系人");
                } else {
                    setUrgent.setText("取消紧急联系人");
                }
                longKickPop.showAtLocation(mLv, Gravity.BOTTOM, 0, mySharePreferences.getInt("height", 150));
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 0.7f;
                getActivity().getWindow().setAttributes(params);
                return true;
            }
        });
        mLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if ((int) v.getTag(R.id.contact_search_add_hand) == -1) {
                    return false;
                }
                int sectionPosition = (int) v.getTag(R.id.contact_list);
                int itemPosition = (int) v.getTag(R.id.contact_search_add_hand);
                if (null != v.getTag() && "1".equals(v.getTag())) {
                    return false;
                }

                MySharedPreferences.getInstance(mContext).setString("FriendsName", list2.get(sectionPosition).get(itemPosition).getUserNick());
                String i;
                int s1 = list2.get(sectionPosition).get(itemPosition).getTargetUserId();
                i = Integer.toString(s1);
                NotifyMsgCountUtil.notifyMsg(i);
                Intent intent = new Intent();
                intent.putExtra("one", i);
                intent.putExtra("userName", list2.get(sectionPosition).get(itemPosition).getUserNick());
                intent.setClass(mContext, IntentChatActivity.class);
                mContext.startActivity(intent);
                return true;
            }
        });
    }

    public void initPop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_longkick_pop, null, false);   //获取pop视图
        TextView delete = (TextView) view.findViewById(R.id.longkick_pop_delete);
        TextView remove = (TextView) view.findViewById(R.id.longkick_pop_remove);
        TextView info = (TextView) view.findViewById(R.id.longkick_pop_info);
        view.findViewById(R.id.longkick_pop_ll1).setVisibility(View.GONE);
        view.findViewById(R.id.longkick_pop_ll2).setVisibility(View.GONE);
        setUrgent = (TextView) view.findViewById(R.id.longkick_pop_setUrgent);
        longKickPop = new PopupWindow(view, 1000, ActionBar.LayoutParams.WRAP_CONTENT, true);
        longKickPop.setTouchable(true); //设置PopupWindow可触摸
        longKickPop.setOutsideTouchable(true);
        longKickPop.setBackgroundDrawable(new BitmapDrawable());
        delete.setOnClickListener(this);
        info.setOnClickListener(this);
        setUrgent.setOnClickListener(this);
        if (list2.get(SectionPosition).get(ItemPosition).getUrgentFlag().equals("否")) {
            setUrgent.setText("设为紧急联系人");
        } else {
            setUrgent.setText("取消紧急联系人");
        }
        longKickPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
        remove.setOnClickListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initData();
        }
    }

    private void initData() {
        list1.clear();
        list2.clear();
        adapter.notifyDataSetChanged();
        group_list = new ArrayList<>();
        topItemList = new ArrayList<>();
        bottomItemList = new ArrayList<>();
        group_list.add("家庭成员");
        group_list.add("朋友");
        group_list.add("邻居");
        group_list.add("紧急联系人");
        if (!MySharedPreferences.isLogin(mContext)) {
            return;
        }
        String url1 = PortUtil.UserContactList;
        Map<String, String> m = new HashMap<>();
        m.put("currId", MySharedPreferences.getUserId(mContext));
        m.put("searchKeyWords", "");
        m.put("pageIndex", "1");
        m.put("pageSize", "999");
        NetRequest.ParamDialogPostRequest(url1, m, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    ContactListEntity bean = new Gson().fromJson(data, ContactListEntity.class);
                    initAdapterResource(bean.getContactList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {

            }
        });

        final String url = PortUtil.baseUrl + "ContactService.asmx/IdentityTypeList";
        Map<String, String> map = new HashMap<>();
        map.put("currId", MySharedPreferences.getUserId(mContext));
        map.put("pageIndex", "1");
        map.put("pageSize", "999");
        NetRequest.ParamDialogPostRequest(url, map, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), IdentityTypeBean.class);
                    getFriends();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {

            }
        });
    }

    private void getFriends() {
        final int[] num = {0};
        final String userUrl = PortUtil.baseUrl + "ContactService.asmx/IdentityMemberListByClassify";
        for (int i = 0; i < bean.getIdentityTypeList().size(); i++) {
            group_list.add(bean.getIdentityTypeList().get(i).getIdentityName());
            Map<String, String> map = new HashMap<>();
            map.put("currId", MySharedPreferences.getUserId(mContext));
            map.put("classifyId", bean.getIdentityTypeList().get(i).getId() + "");
            map.put("pageIndex", "1");
            map.put("pageSize", "999");
            final int finalI = i;
            NetRequest.ParamDialogPostRequest(userUrl, map, new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    try {
                        initItemData(finalI, new Gson().fromJson(UrlDecodeUtil.urlCode(data), GroupUserBean.class).getIdentityMemberListByClassify(), bean.getIdentityTypeList().get(finalI).getIdentityName());
                        num[0]++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (num[0] == 3) {
                            addList();
                        }
                    }
                }
            }, new NetRequest.getDataFailCallback1() {
                @Override
                public void getData(Exception error) {
                    error.printStackTrace();
                }
            });
        }
        addGroupList();
    }

    private void addGroupList() {
        list1.clear();
        list1.addAll(group_list);
        adapter.notifyDataSetChanged();
    }

    Map<Integer, List<ContactListEntity.ContactListBean>> mMap = new HashMap();

    private void initItemData(int index, List<GroupUserBean.IdentityMemberListByClassifyBean> list, String groupName) {
        List<ContactListEntity.ContactListBean> list1 = new ArrayList<>();
        for (GroupUserBean.IdentityMemberListByClassifyBean bean : list) {
            ContactListEntity.ContactListBean bean1 = new ContactListEntity.ContactListBean();
            bean1.setIdentityMark(bean.getIdentityMark());
            bean1.setAvatar(bean.getFace());
            bean1.setUid(Integer.parseInt(MySharedPreferences.getUserId(mContext)));
            bean1.setTargetUserId(bean.getUserid());
            bean1.setRelationGroup(groupName);
            bean1.setTrueName(bean.getFirstname());
            bean1.setUserNick(bean.getUesrname());
            bean1.setRemarkName("");
            bean1.setUrgentFlag("否");
            bean1.setPhone("");
            bean1.setSortFirstChar("");
            bean1.setNCCS(bean.getNCCS());
            bean1.setRNA(bean.getRNA());
            list1.add(bean1);
        }
        mMap.put(index, list1);
    }

    private void addList() {
        bottomItemList.clear();
        bottomItemList.add(mMap.get(0));
        bottomItemList.add(mMap.get(1));
        bottomItemList.add(mMap.get(2));
        handler.removeMessages(1);
        handler.sendEmptyMessage(1);
    }

    private void initAdapterResource(List<ContactListEntity.ContactListBean> bean) {
        List<ContactListEntity.ContactListBean> family = new ArrayList<>();
        List<ContactListEntity.ContactListBean> friend = new ArrayList<>();
        List<ContactListEntity.ContactListBean> neighbor = new ArrayList<>();
        List<ContactListEntity.ContactListBean> urgent = new ArrayList<>();
        for (int i = 0; i < bean.size(); i++) {
            if ("是".equals(bean.get(i).getUrgentFlag().trim())) {
                urgent.add(bean.get(i));
            }
            switch (bean.get(i).getRelationGroup().trim()) {
                case "家人":
                    family.add(bean.get(i));
                    break;
                case "邻居":
                    neighbor.add(bean.get(i));
                    break;
                case "朋友":
                    friend.add(bean.get(i));
                    break;
            }
        }
        topItemList.clear();
        topItemList.add(family);
        topItemList.add(friend);
        topItemList.add(neighbor);
        topItemList.add(urgent);
        handler.removeMessages(1);
        handler.sendEmptyMessage(1);
    }

    private void initView() {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        adapter = new MyExpandableListViewAdapter(getActivity(), list1, list2);
        mLv.setGroupIndicator(null);
        mLv.setAdapter(adapter);
//        mLv.expandGroup(0);

    }

    private void assignView(View view) {
        mySharePreferences = MySharedPreferences.getInstance(getActivity());
        mLv = (ExpandableListView) view.findViewById(R.id.fragment_contact_group_lv);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.longkick_pop_info:
                Intent intent1 = new Intent(getActivity(), MineHomeActivity.class);
                intent1.putExtra("targetUserId", list2.get(SectionPosition).get(ItemPosition).getTargetUserId() + "");
                getActivity().startActivity(intent1);
                longKickPop.dismiss();
                break;
            case R.id.longkick_pop_delete:
                deleteItem();
                break;
            case R.id.longkick_pop_remove:
                if (removePop == null) {
                    initRemovePop();
                }
                longKickPop.dismiss();
                removePop.showAtLocation(mLv, Gravity.BOTTOM, 0, mySharePreferences.getInt("height", 150));
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 0.7f;
                getActivity().getWindow().setAttributes(params);
                break;
            case R.id.remove_family:
                removeItem(0);
                break;
            case R.id.remove_frient:
                removeItem(1);
                break;
            case R.id.remove_neighbor:
                removeItem(2);
                break;
            case R.id.dialog_confirm_left:
                confirmDialog.dismiss();
                break;
            case R.id.dialog_confirm_right:
                switch (clickType) {
                    case "取消":
                        cancel();
                        break;
                    case "设置":
                        setHelpStatus();
                        break;
                }
                confirmDialog.dismiss();
                break;
            case R.id.longkick_pop_setUrgent:
                setHelp();
                break;
        }
    }

    private void setHelpStatus() {
        if ("否".equals(list2.get(SectionPosition).get(ItemPosition).getUrgentFlag().trim())) {
            NetRequest.UrgentContacttRequest(mySharePreferences.getString("uid"), list2.get(SectionPosition).get(ItemPosition).getTargetUserId() + "", "Y", new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                    if ("10000".equals(bean.getCode())) {
                        ToastUtils.showShort(getActivity(), "设置为紧急联系人成功");
                        list2.get(SectionPosition).get(ItemPosition).setUrgentFlag("是");
                        list2.get(3).add(list2.get(SectionPosition).get(ItemPosition));
                        adapter.notifyDataSetChanged();
                    } else ToastUtils.showShort(getActivity(), bean.getMessage());
                    longKickPop.dismiss();
                }
            });
        } else {
            NetRequest.UrgentContacttRequest(mySharePreferences.getString("uid"), list2.get(SectionPosition).get(ItemPosition).getTargetUserId() + "", "N", new NetRequest.getDataCallback() {
                @Override
                public void getData(String data) {
                    DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                    if ("10000".equals(bean.getCode())) {
                        ToastUtils.showShort(getActivity(), "取消紧急联系人成功");
                        list2.get(SectionPosition).get(ItemPosition).setUrgentFlag("否");
                        list2.get(3).remove(list2.get(SectionPosition).get(ItemPosition));
                        adapter.notifyDataSetChanged();
                    } else ToastUtils.showShort(getActivity(), "取消紧急联系人失败");
                    longKickPop.dismiss();
                }
            });
        }
    }

    private void cancel() {
        NetRequest.UrgentContacttRequest(mySharePreferences.getString("uid"), list2.get(SectionPosition).get(ItemPosition).getTargetUserId() + "", "N", new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                if ("10000".equals(bean.getCode())) {
                    ToastUtils.showShort(getActivity(), "取消紧急联系人成功");
                    boolean flag = false;
                    for (int j = 0; j < list2.size(); j++) {
                        if (flag) {
                            break;
                        }
                        for (int i = 0; i < list2.get(j).size(); i++) {
                            if (list2.get(j).get(i).getTargetUserId() == list2.get(SectionPosition).get(ItemPosition).getTargetUserId()) {
                                list2.get(j).get(i).setUrgentFlag("否");
                                flag = true;
                                break;
                            }
                        }
                    }
                    list2.get(SectionPosition).remove(ItemPosition);
                    adapter.notifyDataSetChanged();
                } else ToastUtils.showShort(getActivity(), "取消紧急联系人失败");
            }
        });
    }

    String clickType = "";

    private void setHelp() {
        clickType = "设置";
        if (list2.get(SectionPosition).get(ItemPosition).getUrgentFlag().equals("是")) {
            confirmDialog = ConfirmDialog.createProgressLoading(mContext, "取消紧急联系人", "取消", "确认", ContactGroupFragment.this);
        } else {
            confirmDialog = ConfirmDialog.createProgressLoading(mContext, "设为紧急联系人", "取消", "确认", ContactGroupFragment.this);
        }
        confirmDialog.show();
    }


    private void removeItem(int groupName) {
        if (!MySharedPreferences.isLogin(getActivity())) {
            ToastUtils.showShort(getActivity(), "您还未登录");
            return;
        }
        String url = PortUtil.MovieToGroup + "?currId=" + MySharedPreferences.getUserId(getActivity()) + "&targetUserId=" + list2.get(SectionPosition).get(ItemPosition).getTargetUserId() + "&groupName=" + groupName;
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
                            initData();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteItem() {
        NetRequest.DeleteContact(getActivity(), "", list2.get(SectionPosition).get(ItemPosition).getTargetUserId() + "", new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                longKickPop.dismiss();
                list2.get(SectionPosition).remove(ItemPosition);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initRemovePop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_remove_pop, null, false);
        int width = (int) DensityUtils.px2dp(getActivity(), 1000);
        removePop = new PopupWindow(view, width, ActionBar.LayoutParams.WRAP_CONTENT, true);
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ContactGroup" + "Fragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ContactGroup" + "Fragment");
    }

}
