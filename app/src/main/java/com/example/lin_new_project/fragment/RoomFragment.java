package com.example.lin_new_project.fragment;


import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.lin_new_project.MyApplication.getInstance;
import static com.example.lin_new_project.MyApplication.mDatabaseManager;
import static com.example.lin_new_project.fun.CommonUtils.getColorByVERSION;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.R;
import com.example.lin_new_project.adapter.UserAdapter;
import com.example.lin_new_project.databinding.FragmentRoomBinding;
import com.example.lin_new_project.fun.RecyclerViewMethed;
import com.example.lin_new_project.room.DatabaseCallback;
import com.example.lin_new_project.room.data.HistoryData;
import com.example.lin_new_project.room.data.UserData;
import com.example.lin_new_project.room.entity.FavoritesEntity;
import com.example.lin_new_project.room.entity.HistoryEntity;
import com.example.lin_new_project.room.entity.UserProfileEntity;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class RoomFragment extends BaseBindingFragment<FragmentRoomBinding> {
    private UserProfileEntity userProfileEntity;
    private UserAdapter userAdapter;
    @Override
    protected FragmentRoomBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentRoomBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        RecyclerViewMethed.init_VERTICAL(getActivity(), getBinding().recyclerView,
                true, 0, getColorByVERSION(R.color.white), true, true);
        userAdapter=new UserAdapter(getContext(),new ArrayList<>());
        userAdapter.setOnItemClickListener(userProfile -> {
            userProfileEntity=userProfile;
            getBinding().btnDelete.setVisibility(VISIBLE);
            getBinding().tvUser.setVisibility(VISIBLE);
//            getBinding().btnInsertFavorites.setVisibility(VISIBLE);
//            getBinding().btnInsertHistory.setVisibility(VISIBLE);
            getBinding().recyclerView.setVisibility(INVISIBLE);
            getBinding().tvUser.setText(new Gson().toJson(userProfileEntity));

        });
        getBinding().recyclerView.setAdapter(userAdapter);
        getBinding().btnInsert.setOnClickListener(v->insertUserProfileEntity());
        getBinding().btnQuery.setOnClickListener(v->getUserProfileEntityALL());
        getBinding().btnDelete.setOnClickListener(v->deleteUserProfile());
        getBinding().btnClearTable.setOnClickListener(v->clearTable());
        getBinding().btnInsertFavorites.setOnClickListener(v->insertFavoritesEntity());
        getBinding().btnInsertHistory.setOnClickListener(v->insertHistoryEntity());

    }


    private void insertUserProfileEntity() {
        UserProfileEntity userProfileEntity = new UserProfileEntity();
        UserData userData = new UserData();
        userData.setUserName("AAA");
        userData.setUserType(0);
        userData.setImageName("aasd");
        userProfileEntity.setUesrData(userData);
        mDatabaseManager().insertUserProfileEntity(userProfileEntity, new DatabaseCallback<UserProfileEntity>() {
            @Override
            public void onAdded(long rowId) {
                super.onAdded(rowId);
//                uid = (int) rowId;//rowId不一定是UID
                setLog("新增使用者資料,onAdded,rowId:" + rowId);
            }
        });
    }
    private void getUserProfileEntityALL() {
        mDatabaseManager().getUserProfilesALL(new DatabaseCallback<UserProfileEntity>() {
            @Override
            public void onDataLoadedList(List<UserProfileEntity> lists) {
                super.onDataLoadedList(lists);
                setLog( "讀取UserProfileEntityALL,onDataLoadedList,size:" + lists.size());
                for (int i = 0; i < lists.size(); i++) {
                    UserProfileEntity userProfile = lists.get(i);
//                    if (i == 0) {
//                        userProfileEntity = userProfile;
//                        uid=userProfileEntity.getUid();
////                        MmkvManager.setUserProfileEntityBean(userProfileEntity);
//                    }
                    setLog( "使用者資料:" + new Gson().toJson(userProfile));
                }
                getBinding().recyclerView.setVisibility(VISIBLE);
                userAdapter.setArray(lists);
            }

            @Override
            public void onError(String e) {
                super.onError(e);
                setLog("onError" + e);
            }
        });
    }
    private void deleteUserProfile() {
        if (userProfileEntity == null) return;
        mDatabaseManager().deleteUserProfile(userProfileEntity, new DatabaseCallback<UserProfileEntity>() {
            @Override
            public void onDeleted() {
                super.onDeleted();
                getBinding().btnDelete.setVisibility(INVISIBLE);
                getBinding().tvUser.setVisibility(INVISIBLE);
//                getBinding().btnInsertFavorites.setVisibility(INVISIBLE);

                setLog( "onDeleted");
            }
        });
    }
    private void insertHistoryEntity() {
        if (userProfileEntity == null) return;
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setHistoryParentUid(userProfileEntity.getUid());
        HistoryData historyData = new HistoryData();
        historyData.setHistoryName("HHH");
        historyData.setProgramId(2026);
        historyEntity.setHistoryData(historyData);
        mDatabaseManager().insertHistoryEntity(historyEntity, new DatabaseCallback<HistoryEntity>() {
            @Override
            public void onAdded(long rowId) {
                super.onAdded(rowId);
                setLog(  "新增歷史紀錄,onAdded,rowId:" + rowId);
            }
        });
    }
    private void insertFavoritesEntity() {
        if (userProfileEntity == null) return;
        FavoritesEntity favoritesEntity = new FavoritesEntity();
        favoritesEntity.setFavoriteParentUid(userProfileEntity.getUid());
        mDatabaseManager().insertFavoritesEntity(favoritesEntity, new DatabaseCallback<FavoritesEntity>() {
            @Override
            public void onAdded(long rowId) {
                super.onAdded(rowId);
                setLog(  "新增我的最愛,onAdded,rowId:" + rowId);
            }
        });
    }
    private void clearTable() {
        mDatabaseManager().clearTable(new DatabaseCallback() {
            @Override
            public void onDeleted() {
                userProfileEntity = null;
                setLog( "清空資料庫資料,onDeleted:清空完成");
            }
        });
    }
}