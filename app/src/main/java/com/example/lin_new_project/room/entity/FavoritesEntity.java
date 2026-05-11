package com.example.lin_new_project.room.entity;


import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.TypeConverters;

import com.example.lin_new_project.room.Converters;
import com.example.lin_new_project.room.data.FavoritesData;


@Entity(tableName = FavoritesEntity.FAVORITES,
        primaryKeys = {"favoriteParentUid", "favoriteType"},//primaryKeys 複合式主鍵
        indices = {@Index("favoriteParentUid")},//indices 索引專用的資料欄
        foreignKeys = @ForeignKey(entity = UserProfileEntity.class,//ForeignKey：update和delete時連動修改資料。entity掛在哪各資料表
                parentColumns = "uid",//parentColumns對應資料表的哪個欄位
                childColumns = "favoriteParentUid",//childColumns本身資料表使用的欄位去跟對應資料表關聯
                onDelete = CASCADE))//CASCADE表示當上面資料表被刪除時，對應的關聯資料表全部刪除
public class FavoritesEntity {
    public static final String FAVORITES = "favorites";
    public String favoriteName="";//名稱
    public int favoriteParentUid;//對應使用者的uid
    public int favoriteType;//Programs的code欄位
    private boolean isDefault=false;//用於首頁判斷是否需要我的最愛的符號
    @NonNull
    @TypeConverters(Converters.class)//借助裡面的JSONToUesrData方法進行UesrData轉換成字串寫入資料庫，UesrDataStringToJSON使用將JSON字串轉成UesrData物件
    public FavoritesData favoritesData;
    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }

    public int getFavoriteParentUid() {
        return favoriteParentUid;
    }

    public void setFavoriteParentUid(int favoriteParentUid) {
        this.favoriteParentUid = favoriteParentUid;
    }

    public int getFavoriteType() {
        return favoriteType;
    }

    public void setFavoriteType(int favoriteType) {
        this.favoriteType = favoriteType;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @NonNull
    public FavoritesData getFavoritesData() {
        return favoritesData;
    }

    public void setFavoritesData(@NonNull FavoritesData favoritesData) {
        this.favoritesData = favoritesData;
    }
}
