package com.example.lin_new_project.room;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.MaybeObserver;

public class DatabaseCallback<T> {


    public void onDataLoadedList(List<T> lists) {

     }

    public void onDataLoadedList(T testEntity) {

    }

    public void onAdded(long rowId) {

    }

    public void onQueryAll() {

    }

    public void onDeleted() {

    }

    public void onUpdated() {

    }

    public void onError(@NonNull Throwable e) {

    }
    public void onError(String e) {

    }
    public void onCount(Integer i) {

    }

    public void onNoData() {

    }


}
