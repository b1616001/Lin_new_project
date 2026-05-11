package com.example.lin_new_project.room.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import com.example.lin_new_project.room.Converters;
import com.example.lin_new_project.room.data.HistoryData;

import java.io.Serializable;

@Entity(tableName = HistoryEntity.HISTORY,
        indices = {@Index("historyParentUid")},//indices 索引專用的資料欄
        foreignKeys = @ForeignKey(entity = UserProfileEntity.class,//ForeignKey：update和delete時連動修改資料。entity掛在哪各資料表
                parentColumns = "uid",//parentColumns對應資料表的哪個欄位
                childColumns = "historyParentUid",//childColumns本身資料表使用的欄位去跟對應資料表關聯
                onDelete = CASCADE))//CASCADE表示當上面資料表被刪除時，對應的關聯資料表全部刪除

public class HistoryEntity implements Serializable {
    public static final String HISTORY = "history";
    @PrimaryKey(autoGenerate = true)
    private int uid;
    public int historyParentUid;//儲存user_profile資料表的uid
    @NonNull
    @TypeConverters(Converters.class)//借助裡面的JSONToHistoryData方法進行UesrData轉換成字串寫入資料庫，TemplateDataStringToJSON使用將JSON字串轉成UesrData物件
    private HistoryData historyData;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getHistoryParentUid() {
        return historyParentUid;
    }

    public void setHistoryParentUid(int historyParentUid) {
        this.historyParentUid = historyParentUid;
    }

    public HistoryData getHistoryData() {
        return historyData;
    }

    public void setHistoryData(HistoryData historyData) {
        this.historyData = historyData;
    }
}