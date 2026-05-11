package com.example.lin_new_project.room;

import androidx.room.TypeConverter;

import com.example.lin_new_project.room.data.FavoritesData;
import com.example.lin_new_project.room.data.HistoryData;
import com.example.lin_new_project.room.data.UserData;
import com.google.gson.Gson;


import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static UserData UesrDataStringToJSON(String userData){
        if (userData==null){
            return new UserData();
        }else {
            return new Gson().fromJson(userData, UserData.class);
        }
    }
    @TypeConverter
    public static String JSONToUesrData(UserData userData){
        if (userData ==null){
            return new Gson().toJson(new UserData());
        }else {
            return new Gson().toJson(userData);
        }
    }

    @TypeConverter
    public static HistoryData HistoryDataStringToJSON(String historyData){
        if (historyData==null){
            return new HistoryData();
        }else {
            return new Gson().fromJson(historyData,HistoryData.class);
        }
    }
    @TypeConverter
    public static String JSONToHistoryData(HistoryData historyData){
        if (historyData==null){
            return new Gson().toJson(new HistoryData());
        }else {
            return new Gson().toJson(historyData);
        }
    }


    @TypeConverter
    public static FavoritesData FavoritesDataStringToJSON(String favoritesData){
        if (favoritesData==null){
            return new FavoritesData();
        }else {
            return new Gson().fromJson(favoritesData, FavoritesData.class);
        }
    }
    @TypeConverter
    public static String JSONToFavoritesData(FavoritesData favoritesData){
        if (favoritesData==null){
            return new Gson().toJson(new FavoritesData());
        }else {
            return new Gson().toJson(favoritesData);
        }
    }
}