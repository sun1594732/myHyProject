package com.hyzs.onekeyhelp.module.housekeeping.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.base.BaseActivity;
import com.hyzs.onekeyhelp.contact.bean.DataStatusBean;
import com.hyzs.onekeyhelp.family.circle.bean.PublishPicBean;
import com.hyzs.onekeyhelp.home.adapter.GridAdapter;
import com.hyzs.onekeyhelp.home.adapter.HomeChooseCommunityAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.adapter.ProjectListGridAdapter;
import com.hyzs.onekeyhelp.module.housekeeping.bean.PCCABean;
import com.hyzs.onekeyhelp.module.housekeeping.bean.ProjectListBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.BitmapUtil;
import com.hyzs.onekeyhelp.util.HelpDialog;
import com.hyzs.onekeyhelp.util.InPutUtils;
import com.hyzs.onekeyhelp.util.LogUtils;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ProgressDialog;
import com.hyzs.onekeyhelp.util.ToastSingleUtil;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.GridViewForScrollView;
import com.hyzs.onekeyhelp.widget.wheel.BirthDialog;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PersonAddActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTitle, mIdentityConfirm, mHomeTown, mBirth, mEdutation, mWorkStatus, mServiceType,
            mGetCode, mSubmit, mMapLocation, mInsertWorkExperience, mStartTime, mEndTime, mStartTime1, mEndTime1, mStartTime2,
            mEndTime2;
    private ImageView mBack;
    private ScrollView mLL;
    private ToggleButton mGender, mMarry;
    private EditText mName, mIdentityNumber, mServiceRange, mSalary, mPhone, mCode, mJobName, mJobPlace,
            mJobName1, mJobPlace1, mEmployeePhone1,mEmployeePhone, mJobName2, mJobPlace2, mEmployeePhone2;
    private ImageView mNopic, mPersonIcon;
    private GridViewForScrollView mGridView;
    private RecyclerView mRecyclerView;
    private PopupWindow pop;
    private Dialog mProgressDialog;
    private StringBuilder sb;
    private BirthDialog mBirthDialog;
    private List<String> pathList;
    private GridAdapter mGridAdapter;
    private String pointX, pointY, LOGOURL, pointInfo;
    private ProjectListGridAdapter adapter;
    private Dialog mChooseCommunity;
    private String homeTownNumber, workStatusNumber, educationNumber, serviceTypeNumber;
    private PCCABean pccaBean;
    private ViewStub workExperience2, workExperience3;
    private View mWrokExperience2, mWrokExperience3;
    private boolean workExperience2Inflate, workExperience3Inflate;
    private String[] split;
    private Timer timer;
    private int time;

    /**
     * 剩余时间定时器
     */
    public void setExpireTime() {
        if (timer != null) {
            timer.cancel();
        }
        time = 120;
        TimerTask task = new TimerTask() {
            public void run() {
                time--;
                if (time > 0) {
                    mHandler.sendEmptyMessage(RE_ISSUED_WAIT);
                } else {
                    if (timer != null) {
                        timer.cancel();
                    }
                    mHandler.sendEmptyMessage(RE_ISSUED);
                }
            }
        };
        timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

    /* 重发验证码等待 */
    private final int RE_ISSUED_WAIT = 3;
    /* 重发验证码 */
    private final int RE_ISSUED = 4;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RE_ISSUED_WAIT:
                    mGetCode.setText(time + "秒");
                    break;
                case RE_ISSUED:
                    mGetCode.setText(" 重发验证  ");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void assignView() {
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.toolbar_back);
        mHomeTown = (TextView) findViewById(R.id.activity_person_add_hometown);
        mBirth = (TextView) findViewById(R.id.activity_person_add_birth);
        mEdutation = (TextView) findViewById(R.id.activity_person_add_education);
        mWorkStatus = (TextView) findViewById(R.id.activity_person_add_workStatus);
        mServiceType = (TextView) findViewById(R.id.activity_person_add_serviceType);
        mGetCode = (TextView) findViewById(R.id.activity_person_add_getCode);
        mSubmit = (TextView) findViewById(R.id.activity_person_add_submit);
        mName = (EditText) findViewById(R.id.activity_person_add_name);
        mIdentityNumber = (EditText) findViewById(R.id.activity_person_add_identityNumber);
        mServiceRange = (EditText) findViewById(R.id.activity_person_add_serviceRange);
        mInsertWorkExperience = (TextView) findViewById(R.id.activity_person_add_insertWorkExperience);
        mSalary = (EditText) findViewById(R.id.activity_person_add_salary);
        mPhone = (EditText) findViewById(R.id.activity_person_add_phone);
        mCode = (EditText) findViewById(R.id.activity_person_add_code);
        mNopic = (ImageView) findViewById(R.id.activity_person_add_nonePic);
        mGender = (ToggleButton) findViewById(R.id.activity_person_add_gender);
        mMarry = (ToggleButton) findViewById(R.id.activity_person_add_marry);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_person_add_recycler);
        mIdentityConfirm = (TextView) findViewById(R.id.activity_person_add_identity_confirm);
        mMapLocation = (TextView) findViewById(R.id.activity_person_add_mapLocation);
        mPersonIcon = (ImageView) findViewById(R.id.activity_person_add_nonePic1);
        mLL = (ScrollView) findViewById(R.id.activity_person_add_identity);
        mGridView = (GridViewForScrollView) findViewById(R.id.activity_person_add_project);
        workExperience2 = (ViewStub) findViewById(R.id.work_experience2);
        workExperience3 = (ViewStub) findViewById(R.id.work_experience3);
        mStartTime = (TextView) findViewById(R.id.work_experience_startTime);
        mEndTime = (TextView) findViewById(R.id.work_experience_endTime);
        mJobName = (EditText) findViewById(R.id.work_experience_jobName);
        mJobPlace = (EditText) findViewById(R.id.work_experience_workPlace);
        mEmployeePhone = (EditText) findViewById(R.id.work_experience_employeePhone);
    }

    @Override
    protected void initView() {
        sb = new StringBuilder();
        mTitle.setText("申请加入");
        mBirthDialog = new BirthDialog(this);
        mProgressDialog = ProgressDialog.createProgressLoading(this);
        String systemTime = getSystemTime();
        split = systemTime.split("-");
        mBirthDialog.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGridAdapter = new GridAdapter(this);
        mRecyclerView.setAdapter(mGridAdapter);
        pop = HelpDialog.createDialogNoAlert(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                finish();
            }
        }, "申请成功，请耐心等待", R.mipmap.bingo);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mNopic.setOnClickListener(this);
        mPersonIcon.setOnClickListener(this);
        mMapLocation.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mHomeTown.setOnClickListener(this);
        mBirth.setOnClickListener(this);
        mEdutation.setOnClickListener(this);
        mWorkStatus.setOnClickListener(this);
        mServiceType.setOnClickListener(this);
        mGetCode.setOnClickListener(this);
        mStartTime.setOnClickListener(this);
        mEndTime.setOnClickListener(this);
        mIdentityConfirm.setOnClickListener(this);
        mInsertWorkExperience.setOnClickListener(this);
        mBirthDialog.setBirthdayListener(new BirthDialog.OnBirthListener() {
            @Override
            public void onClick(String year, String month, String day) {
                mBirth.setText(year + "-" + month + "-" + day);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_add;
    }

    @Override
    protected void initData() {
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", "0");
        param.put("pageSize", "99");
        param.put("pageIndex", "1");
        NetRequest.ParamPostRequest(PortUtil.LifeServiceSP, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                ProjectListBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), ProjectListBean.class);
                if (bean.getLifeServiceSP().size() != 0) {
                    adapter = new ProjectListGridAdapter(bean.getLifeServiceSP(), PersonAddActivity.this);
                    mGridView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_person_add_insertWorkExperience:
                if (!workExperience2Inflate) {
                    mWrokExperience2 = workExperience2.inflate();
                    mStartTime1 = (TextView) mWrokExperience2.findViewById(R.id.work_experience_startTime);
                    mEndTime1 = (TextView) mWrokExperience2.findViewById(R.id.work_experience_endTime);
                    mJobName1 = (EditText) mWrokExperience2.findViewById(R.id.work_experience_jobName);
                    mJobPlace1 = (EditText) mWrokExperience2.findViewById(R.id.work_experience_workPlace);
                    mEmployeePhone1 = (EditText) mWrokExperience2.findViewById(R.id.work_experience_employeePhone);
                    mStartTime1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BirthDialog bd = new BirthDialog(PersonAddActivity.this);
                            bd.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                            bd.setBirthdayListener(new BirthDialog.OnBirthListener() {
                                @Override
                                public void onClick(String year, String month, String day) {
                                    mStartTime1.setText(year + "-" + month + "-" + day);
                                }
                            });
                            bd.show();
                        }
                    });
                    mEndTime1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BirthDialog bd = new BirthDialog(PersonAddActivity.this);
                            bd.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                            bd.setBirthdayListener(new BirthDialog.OnBirthListener() {
                                @Override
                                public void onClick(String year, String month, String day) {
                                    mEndTime1.setText(year + "-" + month + "-" + day);
                                }
                            });
                            bd.show();
                        }
                    });
                    workExperience2Inflate = true;
                    return;
                }
                if (!workExperience3Inflate) {
                    mWrokExperience3 = workExperience3.inflate();
                    mStartTime2 = (TextView) mWrokExperience3.findViewById(R.id.work_experience_startTime);
                    mEndTime2 = (TextView) mWrokExperience3.findViewById(R.id.work_experience_endTime);
                    mJobName2 = (EditText) mWrokExperience3.findViewById(R.id.work_experience_jobName);
                    mJobPlace2 = (EditText) mWrokExperience3.findViewById(R.id.work_experience_workPlace);
                    mEmployeePhone2 = (EditText) mWrokExperience3.findViewById(R.id.work_experience_employeePhone);
                    mStartTime2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BirthDialog bd = new BirthDialog(PersonAddActivity.this);
                            bd.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                            bd.setBirthdayListener(new BirthDialog.OnBirthListener() {
                                @Override
                                public void onClick(String year, String month, String day) {
                                    mStartTime2.setText(year + "-" + month + "-" + day);
                                }
                            });
                            bd.show();
                        }
                    });
                    mEndTime2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BirthDialog bd = new BirthDialog(PersonAddActivity.this);
                            bd.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                            bd.setBirthdayListener(new BirthDialog.OnBirthListener() {
                                @Override
                                public void onClick(String year, String month, String day) {
                                    mEndTime2.setText(year + "-" + month + "-" + day);
                                }
                            });
                            bd.show();
                        }
                    });
                    workExperience3Inflate = true;
                }
                break;
            case R.id.activity_person_add_identity_confirm:
                mLL.setVisibility(View.GONE);
                break;
            case R.id.activity_person_add_nonePic:
                Intent intent = new Intent(this, AlbumActivity.class);
                intent.putExtra("limitCount", 3);
                startActivityForResult(intent, 100);
                break;
            case R.id.activity_person_add_nonePic1:
                Intent intent1 = new Intent(this, AlbumActivity.class);
                intent1.putExtra("limitCount", 1);
                startActivityForResult(intent1, 300);
                break;
            case R.id.activity_person_add_hometown:
                initHomeTownDialog();
                mChooseCommunity.show();
                break;
            case R.id.activity_person_add_birth:
                mBirthDialog.show();
                break;
            case R.id.activity_person_add_workStatus:
                initWorkStatusDialog();
                mChooseCommunity.show();
                break;
            case R.id.activity_person_add_serviceType:
                initServiceTypeDialog();
                mChooseCommunity.show();
                break;
            case R.id.activity_person_add_getCode:
                if (!InPutUtils.isMobilePhone(mPhone.getText().toString())) {
                    ToastSingleUtil.showToast(this, "请输入正确的手机号!");
                    return;
                }
                Map<String, String> param = new ArrayMap<>();
                param.put("phone", mPhone.getText().toString());
                param.put("type", "6");
                if (InPutUtils.replaceBlank(mGetCode.getText().toString()).equals("重发验证")
                        || InPutUtils.replaceBlank(mGetCode.getText().toString()).equals("获取验证码")) {
                    NetRequest.ParamDialogPostRequest(PortUtil.SendMessage, param, new NetRequest.getDataCallback() {
                        @Override
                        public void getData(String data) {
                        }
                    }, new NetRequest.getDataFailCallback1() {
                        @Override
                        public void getData(Exception error) {
                            ToastSingleUtil.showToast(PersonAddActivity.this, "发送失败");
                        }
                    });
                    setExpireTime();
                }
                break;
            case R.id.activity_person_add_submit:
                mProgressDialog.show();
                if (pathList != null) {
                    for (int i = 0; i < pathList.size(); i++) {
                        getPicPath(pathList.get(i), i);
                    }
                } else publishRequest();
                break;
            case R.id.activity_person_add_mapLocation:
                startActivityForResult(new Intent(this, MapViewActivity.class), 200);
                break;
            case R.id.activity_person_add_education:
                initEducationDialog();
                mChooseCommunity.show();
                break;
            case R.id.work_experience_startTime:
                BirthDialog bd1 = new BirthDialog(this);
                bd1.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                bd1.setBirthdayListener(new BirthDialog.OnBirthListener() {
                    @Override
                    public void onClick(String year, String month, String day) {
                        mStartTime.setText(year + "-" + month + "-" + day);
                    }
                });
                bd1.show();
                break;
            case R.id.work_experience_endTime:
                BirthDialog bd2 = new BirthDialog(this);
                bd2.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                bd2.setBirthdayListener(new BirthDialog.OnBirthListener() {
                    @Override
                    public void onClick(String year, String month, String day) {
                        mEndTime.setText(year + "-" + month + "-" + day);
                    }
                });
                bd2.show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.requestFocus();
        mTitle.isFocusableInTouchMode();
    }

    private String getSystemTime() {
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(l);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) { // 成功选择了照片。
                if (pathList == null) {
                    pathList = Album.parseResult(data);
                } else {
                    pathList.addAll(Album.parseResult(data));
                }
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtils.showShort(PersonAddActivity.this, "取消上传");
            }
        } else if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                pointX = data.getStringExtra("pointX");
                pointY = data.getStringExtra("pointY");
                pointInfo = data.getStringExtra("pointInfo");
                mMapLocation.setText(pointInfo);
            }
        } else if (requestCode == 300) {
            if (resultCode == RESULT_OK) {
                mPersonIcon.setImageBitmap(BitmapFactory.decodeFile(Album.parseResult(data).get(0)));
                getLogoPicPath(Album.parseResult(data).get(0));
            }
        }
    }

    private void handleSelectImage(List<String> pathList) {
        mGridAdapter.notifyDataSetChanged(pathList);
        if (pathList == null || pathList.size() == 0) {
            mNopic.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mNopic.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void getPicPath(String path, final int position) {
        byte[] datas = BitmapUtil.getImage(path);
        String encodeString = android.util.Base64.encodeToString(datas, Base64.DEFAULT);
        Map<String, String> param = new HashMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("FileBase64", encodeString);
        param.put("FileType", "0");
        NetRequest.PictureUpRequest(param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                PublishPicBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PublishPicBean.class);
                if (position == pathList.size() - 1) {
                    sb.append("{" + bean.getUrl() + "}");
                    publishRequest();
                } else {
                    sb.append("{" + bean.getUrl() + "},");
                }
            }
        });
    }

    private void getLogoPicPath(String path) {
        byte[] datas = BitmapUtil.getImage(path);
        String encodeString = android.util.Base64.encodeToString(datas, Base64.DEFAULT);
        Map<String, String> param = new HashMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("FileBase64", encodeString);
        param.put("FileType", "0");
        NetRequest.PictureUpRequest(param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                PublishPicBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PublishPicBean.class);
                LOGOURL = bean.getUrl();
            }
        });
    }

    private void publishRequest() {
        if (TextUtils.isEmpty(mName.getText().toString()) | TextUtils.isEmpty(mSalary.getText().toString())
                | TextUtils.isEmpty(mServiceRange.getText().toString()) | TextUtils.isEmpty(mIdentityNumber.getText().toString())) {
            ToastSingleUtil.showToast(this, "请完善信息");
            return;
        }
        JSONObject object = new JSONObject();
        try {
            object.put("name", mName.getText().toString());
            object.put("sex", mGender.isChecked() ? "男" : "女");
            object.put("ICN", mIdentityNumber.getText().toString());
            object.put("nativePlace", homeTownNumber);
            object.put("birthday", mBirth.getText().toString());
            object.put("maritalState", mMarry.isChecked() ? "未婚" : "已婚");
            object.put("cultureLevel", educationNumber);
            object.put("workState", workStatusNumber);
            object.put("serviceRangeDesc", mServiceRange.getText().toString());
            object.put("ICAffixList", sb.toString());
            object.put("sps", ProjectListGridAdapter.getProject());
            object.put("serviceType", serviceTypeNumber);
            object.put("lng", pointY);
            object.put("lat", pointX);
            object.put("validateCode", mCode.getText().toString());
            object.put("img", LOGOURL);
            object.put("phone", mPhone.getText().toString());
            object.put("msalary", mSalary.getText().toString());
            object.put("img", LOGOURL);
            JSONArray array = new JSONArray();
            JSONObject object1 = new JSONObject();
            object1.put("WE_BeginTime", mStartTime.getText().toString());
            object1.put("WE_EndTime", mEndTime.getText().toString());
            object1.put("WE_PositionName", mJobName.getText().toString());
            object1.put("WE_WorkOfCommunity", mJobPlace.getText().toString());
            object1.put("WE_WorkBossTel", mEmployeePhone.getText().toString());
            array.put(object1);
            if (workExperience2Inflate) {
                JSONObject object2 = new JSONObject();
                object2.put("WE_BeginTime", mStartTime1.getText().toString());
                object2.put("WE_EndTime", mEndTime1.getText().toString());
                object2.put("WE_PositionName", mJobName1.getText().toString());
                object2.put("WE_WorkOfCommunity", mJobPlace1.getText().toString());
                object2.put("WE_WorkBossTel", mEmployeePhone1.getText().toString());
                array.put(object2);
            }
            if (workExperience3Inflate) {
                JSONObject object2 = new JSONObject();
                object2.put("WE_BeginTime", mStartTime2.getText().toString());
                object2.put("WE_EndTime", mEndTime2.getText().toString());
                object2.put("WE_PositionName", mJobName2.getText().toString());
                object2.put("WE_WorkOfCommunity", mJobPlace2.getText().toString());
                object2.put("WE_WorkBossTel", mEmployeePhone2.getText().toString());
                array.put(object2);
            }
            object.put("workExperience", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(this));
        param.put("InputJson", UrlDecodeUtil.urlEnCode(object.toString()));
        NetRequest.ParamDialogPostRequest(PortUtil.LifeServicePersonalApplyJoin, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                LogUtils.e(UrlDecodeUtil.urlCode(data) + "----");
                DataStatusBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), DataStatusBean.class);
                if ("10000".equals(bean.getCode())) {
                    pop.showAtLocation(mTitle, Gravity.CENTER, 0, 0);
                } else ToastUtils.showShort(PersonAddActivity.this, bean.getMessage());
                mProgressDialog.dismiss();
            }
        }, new NetRequest.getDataFailCallback1() {
            @Override
            public void getData(Exception error) {
                mProgressDialog.dismiss();
            }
        });
    }

    private void initHomeTownDialog() {
        View DialogView = LayoutInflater.from(this).inflate(R.layout.dialog_only_lv, null);
        assignDialogView(DialogView);
        mChooseCommunity = new AlertDialog.Builder(this).create();
        mChooseCommunity.show();
        mChooseCommunity.getWindow().setContentView(DialogView);
        mChooseCommunity.dismiss();
    }

    private void assignDialogView(View dialogView) {
        final ListView lv = (ListView) dialogView.findViewById(R.id.dialog_only_list);
        Map<String, String> param = new ArrayMap<>();
        param.put("parentCode", "0");
        param.put("type", "0");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                homeTownNumber = pccaBean.getPcca().get(position).getCode() + "";
                mChooseCommunity.dismiss();
                mHomeTown.setText(pccaBean.getPcca().get(position).getName());
            }
        });
        NetRequest.ParamPostRequest(PortUtil.PCCA, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    pccaBean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), PCCABean.class);
                    List<String> resource = new ArrayList<String>();
                    for (int i = 0; i < pccaBean.getPcca().size(); i++) {
                        resource.add(pccaBean.getPcca().get(i).getName());
                    }
                    lv.setAdapter(new HomeChooseCommunityAdapter(resource, PersonAddActivity.this));
                } catch (Exception e) {
                }
            }
        });
    }

    private void initWorkStatusDialog() {
        View DialogView = LayoutInflater.from(this).inflate(R.layout.dialog_only_lv, null);
        assignWorkStatusDialogView(DialogView);
        mChooseCommunity = new AlertDialog.Builder(this).create();
        mChooseCommunity.show();
        mChooseCommunity.getWindow().setContentView(DialogView);
        mChooseCommunity.dismiss();
    }

    private void assignWorkStatusDialogView(View dialogView) {
        final ListView lv = (ListView) dialogView.findViewById(R.id.dialog_only_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    workStatusNumber = position + "";
                    mWorkStatus.setText("在职");
                } else {
                    workStatusNumber = position + "";
                    mWorkStatus.setText("离职");
                }
                mChooseCommunity.dismiss();
            }
        });
        List<String> resource = new ArrayList<String>();
        resource.add("在职");
        resource.add("离职");
        lv.setAdapter(new HomeChooseCommunityAdapter(resource, PersonAddActivity.this));
    }

    private void initServiceTypeDialog() {
        View DialogView = LayoutInflater.from(this).inflate(R.layout.dialog_only_lv, null);
        assignServiceTypeDialogView(DialogView);
        mChooseCommunity = new AlertDialog.Builder(this).create();
        mChooseCommunity.show();
        mChooseCommunity.getWindow().setContentView(DialogView);
        mChooseCommunity.dismiss();
    }

    private void assignServiceTypeDialogView(View dialogView) {
        final ListView lv = (ListView) dialogView.findViewById(R.id.dialog_only_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    serviceTypeNumber = position + "";
                    mServiceType.setText("全职");
                } else {
                    serviceTypeNumber = position + "";
                    mServiceType.setText("兼职");
                }
                mChooseCommunity.dismiss();
            }
        });
        List<String> resource = new ArrayList<>();
        resource.add("全职");
        resource.add("兼职");
        lv.setAdapter(new HomeChooseCommunityAdapter(resource, PersonAddActivity.this));
    }

    private void initEducationDialog() {
        View DialogView = LayoutInflater.from(this).inflate(R.layout.dialog_only_lv, null);
        assignEducationDialogView(DialogView);
        mChooseCommunity = new AlertDialog.Builder(this).create();
        mChooseCommunity.show();
        mChooseCommunity.getWindow().setContentView(DialogView);
        mChooseCommunity.dismiss();
    }

    private void assignEducationDialogView(View dialogView) {
        final ListView lv = (ListView) dialogView.findViewById(R.id.dialog_only_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mEdutation.setText("本科以上");
                        break;
                    case 1:
                        mEdutation.setText("本科");
                        break;
                    case 2:
                        mEdutation.setText("大专");
                        break;
                    case 3:
                        mEdutation.setText("高中");
                        break;
                    case 4:
                        mEdutation.setText("初中");
                        break;
                    case 5:
                        mEdutation.setText("小学");
                        break;
                    case 6:
                        mEdutation.setText("其他");
                        break;
                }
                educationNumber = position + "";
                mChooseCommunity.dismiss();
            }
        });
        List<String> resource = new ArrayList<String>();
        resource.add("本科以上");
        resource.add("本科");
        resource.add("大专");
        resource.add("高中");
        resource.add("初中");
        resource.add("小学");
        resource.add("其他");
        lv.setAdapter(new HomeChooseCommunityAdapter(resource, PersonAddActivity.this));
    }
}
