package com.example.lin_new_project.room.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.lin_new_project.room.Converters;
import com.example.lin_new_project.room.data.UserData;


@Entity(tableName = UserProfileEntity.USER_PROFILE, indices = {@Index("uid")})//indices 索引專用的資料欄
public class UserProfileEntity {
    //@Ignore 說明如果用加上這個 代表資料表不會有此欄位
    public static final String USER_PROFILE = "user_profile";
    @PrimaryKey(autoGenerate = true)
    private int uid;//主鍵
    @NonNull
    @TypeConverters(Converters.class)//借助裡面的JSONToUesrData方法進行UesrData轉換成字串寫入資料庫，UesrDataStringToJSON使用將JSON字串轉成UesrData物件
    public UserData userData;


    public UserProfileEntity() {
    }
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }



    @NonNull
    public UserData getUesrData() {
        return userData;
    }

    @NonNull
    public void setUesrData(@NonNull UserData userData) {
        this.userData = userData;
    }

}
