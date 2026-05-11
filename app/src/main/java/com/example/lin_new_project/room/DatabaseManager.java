package com.example.lin_new_project.room;




import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;


import com.example.lin_new_project.room.entity.FavoritesEntity;
import com.example.lin_new_project.room.entity.HistoryEntity;
import com.example.lin_new_project.room.entity.UserProfileEntity;

import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DatabaseManager {
    //    收合/展開所有程式碼區塊	Control+Shift+減號或 Control+Shift+加號
    private static DatabaseManager instance;
    private final DBDatabase db;
    private static final String DB_NAME = "lin_database";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable mDisposable;
    private final String TAG = "Room資料庫存取";

    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            synchronized (DBDatabase.class) {
                if (instance == null) {
                    instance = new DatabaseManager(context);
                }
            }
        }
        return instance;
    }

    public DatabaseManager(Context context) {
        db = Room.databaseBuilder(context, DBDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration() //清空，如果版本升級卻無addMigrations則將會刪除資料庫並重建
//                .addMigrations(MIGRATION_1_2) //升級
//                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
//                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
//                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                .build();
    }

    public void clearTable(final DatabaseCallback databaseCallback) {
        Completable.fromAction(db::clearAllTables)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onDeleted();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });
    }

    public void roomClear() {
        //acivity或Fragment销毁的时候，调用compositeDisposable.dispose()就可以切断所有订阅事件，防止内存泄漏。
        Log.d(TAG, "roomClear,Size:" + compositeDisposable.size());
        compositeDisposable.clear();//一次清除

    }

    public void cancel() {
        Log.d(TAG, "cancel");
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();//單一清除，比較適用於執行完某件事情後就結束，並不需要回傳資料的
        }
    }

    //這一區塊是處理UserProfiles----------------------------------------------------------------------


    public void getUserProfilesALL(@NonNull final DatabaseCallback<UserProfileEntity> databaseCallback) {
        Log.d(TAG, "getUserProfilesALL");
        Disposable d = db.dbDao()
                .getUserProfilesALL()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.d(TAG, "Throwable" + throwable.getMessage()))
                .subscribe(databaseCallback::onDataLoadedList);

        compositeDisposable.add(d);
    }

    public void getUserProfiles(int uid, @NonNull final DatabaseCallback<UserProfileEntity> databaseCallback) {
        Log.d(TAG, "getUserProfiles");
        Disposable d = db.dbDao()
                .getUserProfileEntity(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.d(TAG, "Throwable" + throwable.getMessage()))
                .subscribe(databaseCallback::onDataLoadedList);

        compositeDisposable.add(d);
    }

    public void insertUserProfileEntity(final UserProfileEntity userProfileEntity,
                                        final DatabaseCallback<UserProfileEntity> databaseCallback) {
        //這裡使用Completable是因為數據庫處理完後不發射數據，只處理onComplete和onError事件
        //Completable
        //適合用在執行的內容沒有回傳值，只要知道成功或失敗就好的時候，
        //例如更新個人資料。在callback有onComplete()和onError(Throwable e)兩個方法，
        //其中onComplete()是沒有參數的表示執行完不會有回傳值。
        //建立我們要執行的內容。
        AtomicLong rowId = new AtomicLong();
        Completable
                .fromAction(() -> rowId.set(db.dbDao().insertUserProfileEntity(userProfileEntity)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);

                        Log.d(TAG, "onSubscribe ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete ");
                        databaseCallback.onAdded(rowId.get());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError ");
                        databaseCallback.onError(e.getMessage());
                    }
                });
    }

    public void updateUserProfile(final UserProfileEntity userProfileEntity,
                                  final DatabaseCallback<UserProfileEntity> callback) {
        Completable.fromAction(() -> db.dbDao().updateUserProfile(userProfileEntity)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onComplete() {
                        callback.onUpdated();
                        Log.d(TAG, "onComplete: ");
                        cancel();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onSubscribe: ");
                        callback.onError(e.getMessage());
                    }
                });
    }

    public void deleteUserProfile(UserProfileEntity userProfileEntity,
                                  final DatabaseCallback<UserProfileEntity> databaseCallback) {
        Completable
                .fromAction(() -> db.dbDao().deleteUserProfile(userProfileEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onDeleted();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });
    }
    //----------------------------------------------------------------------------------------------

    //這一區塊是處理HistoryEntity----------------------------------------------------------------------
    public void getHistoryEntityALL(@NonNull final DatabaseCallback<HistoryEntity> databaseCallback) {
        Log.d(TAG, "getHistoryEntityALL");
        Disposable d = db.dbDao()
                .getHistoryEntityALL()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.d(TAG, "Throwable" + throwable.getMessage()))
                .subscribe(databaseCallback::onDataLoadedList);

        compositeDisposable.add(d);
    }
    public void getHistoryList(int historyParentUid,
                               final DatabaseCallback<HistoryEntity> callback) {
        Disposable d = db.dbDao()
                .getHistoryList(historyParentUid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(callback::onDataLoadedList);

        compositeDisposable.add(d);
    }
    public void getHistoryEntity(int historyParentUid, @NonNull final DatabaseCallback<HistoryEntity> databaseCallback) {
        Log.d(TAG, "getHistoryEntity");
        Disposable d = db.dbDao()
                .getHistoryEntity(historyParentUid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.d(TAG, "Throwable" + throwable.getMessage()))
                .subscribe(databaseCallback::onDataLoadedList);

        compositeDisposable.add(d);
    }

    public void getHistoryEntity_Uid(int uid, @NonNull final DatabaseCallback<HistoryEntity> databaseCallback) {
        Log.d(TAG, "getHistoryEntity_Uid");
        Disposable d = db.dbDao()
                .getHistoryEntity_Uid(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.d(TAG, "Throwable" + throwable.getMessage()))
                .subscribe(databaseCallback::onDataLoadedList);

        compositeDisposable.add(d);
    }

    public void insertHistoryEntity(final HistoryEntity historyEntity,
                                    final DatabaseCallback<HistoryEntity> databaseCallback) {
        //這裡使用Completable是因為數據庫處理完後不發射數據，只處理onComplete和onError事件
        //Completable
        //適合用在執行的內容沒有回傳值，只要知道成功或失敗就好的時候，
        //例如更新個人資料。在callback有onComplete()和onError(Throwable e)兩個方法，
        //其中onComplete()是沒有參數的表示執行完不會有回傳值。
        //建立我們要執行的內容。
        AtomicLong rowId = new AtomicLong();
        Completable
                .fromAction(() -> rowId.set(db.dbDao().insertHistoryEntity(historyEntity)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                        Log.d(TAG, "onSubscribe ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete ");
                        databaseCallback.onAdded(rowId.get());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError ");
                        databaseCallback.onError(e.getMessage());
                    }
                });
    }

    public void updateHistoryEntity(final HistoryEntity historyEntity,
                                    final DatabaseCallback<HistoryEntity> callback) {
        Completable.fromAction(() -> db.dbDao().updateHistoryEntity(historyEntity)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onComplete() {
                        callback.onUpdated();
                        Log.d(TAG, "onComplete: ");
                        cancel();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onSubscribe: ");
                        callback.onError(e.getMessage());
                    }
                });
    }

    public void deleteHistoryEntity(HistoryEntity historyEntity,
                                    final DatabaseCallback<HistoryEntity> databaseCallback) {
        Completable
                .fromAction(() -> db.dbDao().deleteHistoryEntity(historyEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onDeleted();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });
    }
    //----------------------------------------------------------------------------------------------

    //這一區塊是處理FavoritesEntity----------------------------------------------------------------------
    public void getFavoritesEntityALL(@NonNull final DatabaseCallback<FavoritesEntity> databaseCallback) {
        Log.d(TAG, "getFavoritesEntityALL");
        Disposable d = db.dbDao()
                .getFavoritesEntityALL()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.d(TAG, "Throwable" + throwable.getMessage()))
                .subscribe(databaseCallback::onDataLoadedList);

        compositeDisposable.add(d);
    }

    //從User的id對應到Favorite的id,取得Favorite的表
    public void getFavoriteFromUserProfile(int id,
                                           final DatabaseCallback<UserProfileAndFavorites> callback) {
        Disposable d = db.dbDao()
                .getFavoriteForUserProfile(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(callback::onDataLoadedList);

        compositeDisposable.add(d);
    }
    public void getFavoritesEntity(int favoriteParentUid, @NonNull final DatabaseCallback<FavoritesEntity> databaseCallback) {
        Log.d(TAG, "getFavoritesEntity");
        Disposable d = db.dbDao()
                .getFavoritesEntity(favoriteParentUid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.d(TAG, "Throwable" + throwable.getMessage()))
                .subscribe(databaseCallback::onDataLoadedList);

        compositeDisposable.add(d);
    }

    public void getFavoritesEntity(int favoriteParentUid, int favoriteType,
                                   @NonNull final DatabaseCallback<FavoritesEntity> databaseCallback) {
        Log.d(TAG, "getFavoritesEntity2");
        Disposable d = db.dbDao()
                .getFavoritesEntity(favoriteParentUid, favoriteType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.d(TAG, "Throwable" + throwable.getMessage()))
                .subscribe(databaseCallback::onDataLoadedList);

        compositeDisposable.add(d);
    }

    public void insertFavoritesEntity(final FavoritesEntity favoritesEntity,
                                      final DatabaseCallback<FavoritesEntity> databaseCallback) {
        //這裡使用Completable是因為數據庫處理完後不發射數據，只處理onComplete和onError事件
        //Completable
        //適合用在執行的內容沒有回傳值，只要知道成功或失敗就好的時候，
        //例如更新個人資料。在callback有onComplete()和onError(Throwable e)兩個方法，
        //其中onComplete()是沒有參數的表示執行完不會有回傳值。
        //建立我們要執行的內容。
        AtomicLong rowId = new AtomicLong();
        Completable
                .fromAction(() -> rowId.set(db.dbDao().insertFavoritesEntity(favoritesEntity)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                        Log.d(TAG, "onSubscribe ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete ");
                        databaseCallback.onAdded(rowId.get());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError ");
                        databaseCallback.onError(e.getMessage());
                    }
                });
    }

    public void updateFavoritesEntity(final FavoritesEntity favoritesEntity,
                                      final DatabaseCallback<FavoritesEntity> callback) {
        Completable.fromAction(() -> db.dbDao().updateFavoritesEntity(favoritesEntity)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onComplete() {
                        callback.onUpdated();
                        Log.d(TAG, "onComplete: ");
                        cancel();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onSubscribe: ");
                        callback.onError(e.getMessage());
                    }
                });
    }

    public void deleteFavoritesEntity(FavoritesEntity favoritesEntity,
                                      final DatabaseCallback<FavoritesEntity> databaseCallback) {
        Completable
                .fromAction(() -> db.dbDao().deleteFavoritesEntity(favoritesEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onDeleted();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });
    }
    //----------------------------------------------------------------------------------------------







    //----------------------------------------------------------------------------------------------

}
