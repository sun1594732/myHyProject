package com.hyzs.onekeyhelp.family.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hyzs.onekeyhelp.R;
import com.hyzs.onekeyhelp.family.activity.FamilyAlbumPublishActivity;
import com.hyzs.onekeyhelp.family.adapter.FamilyAlbumAdapter;
import com.hyzs.onekeyhelp.family.albumimport.FamilyImportMineAlbumActivity;
import com.hyzs.onekeyhelp.family.bean.FamilyAlbumBean;
import com.hyzs.onekeyhelp.netRequest.PortUtil;
import com.hyzs.onekeyhelp.netRequest.nohttp.NetRequest;
import com.hyzs.onekeyhelp.util.MySharedPreferences;
import com.hyzs.onekeyhelp.util.ToastUtils;
import com.hyzs.onekeyhelp.util.UrlDecodeUtil;
import com.hyzs.onekeyhelp.widget.view.VRefresh;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * 家庭相册
 */
public class FamilyAlbumFragment extends Fragment {
    private ListView mLv;
    private VRefresh vRefresh;
    private List<FamilyAlbumBean.GroupAlbumDynamicListBean> list;
    private FamilyAlbumAdapter adapter;
    private ImageView mChoosePic, mImport;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_alibum, null);
        assignView(view);
        initView();
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        mImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FamilyImportMineAlbumActivity.class);
                intent.putExtra("familyId", getActivity().getIntent().getStringExtra("familyId"));
                startActivityForResult(intent, 200);
            }
        });
        mChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FamilyAlbumPublishActivity.class);
                intent.putExtra("familyId", getActivity().getIntent().getStringExtra("familyId"));
                startActivityForResult(intent, 100);

            }
        });
        vRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                vRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            initData();
        }
        if (requestCode == 200 && resultCode == RESULT_OK) {
            initData();
        }
    }

    private void initData() {
        Map<String, String> param = new ArrayMap<>();
        param.put("currId", MySharedPreferences.getUserId(getActivity()));
        param.put("targetGroupID", getActivity().getIntent().getStringExtra("familyId"));
        param.put("pageSize", "20");
        param.put("pageIndex", "1");
        NetRequest.ParamPostRequest(PortUtil.FamilyAlbum, param, new NetRequest.getDataCallback() {
            @Override
            public void getData(String data) {
                try {
                    FamilyAlbumBean bean = new Gson().fromJson(UrlDecodeUtil.urlCode(data), FamilyAlbumBean.class);
                    list.clear();
                    list.addAll(bean.getGroupAlbumDynamicList());
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    ToastUtils.showShort(getActivity(), "网络错误");
                }
            }
        });
    }

    private void initView() {
        vRefresh.setView(getActivity(), mLv);
        list = new ArrayList<>();
        adapter = new FamilyAlbumAdapter(list, getActivity());
        mLv.setAdapter(adapter);
    }

    private void assignView(View view) {
        mLv = (ListView) view.findViewById(R.id.activity_mine_album_lv);
        vRefresh = (VRefresh) view.findViewById(R.id.activity_mine_album_vRefresh);
        mChoosePic = (ImageView) view.findViewById(R.id.family_choose);
        mImport = (ImageView) view.findViewById(R.id.family_album_import);
    }
}
