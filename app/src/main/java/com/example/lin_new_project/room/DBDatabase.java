package com.example.lin_new_project.room;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.lin_new_project.room.entity.FavoritesEntity;
import com.example.lin_new_project.room.entity.HistoryEntity;
import com.example.lin_new_project.room.entity.UserProfileEntity;


@Database(entities = { UserProfileEntity.class, FavoritesEntity.class, HistoryEntity.class},
        version = 3)
@TypeConverters({Converters.class})
public abstract class DBDatabase extends RoomDatabase {
    public abstract DBDao dbDao();


//以下 此區塊式資料庫更新範本

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE test ADD COLUMN wattAccumulate REAL NOT NULL DEFAULT 0");
//            database.execSQL("ALTER TABLE test ADD COLUMN wattFrequency INTEGER NOT NULL DEFAULT 0");
//            database.execSQL("ALTER TABLE test ADD COLUMN workoutMonth INTEGER NOT NULL DEFAULT 0");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //當添加int 類型數據時，需要添加默認值
//            database.execSQL("ALTER TABLE user_profile ADD COLUMN sleepMode INTEGER NOT NULL DEFAULT 0");
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE user_profile ADD COLUMN passCode TEXT");
        }
    };

    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE user_profile ADD COLUMN passcodeOn INTEGER NOT NULL DEFAULT 0");
        }
    };



//    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE expense ADD COLUMN itemName TEXT");
//            database.execSQL("ALTER TABLE expense ADD COLUMN itemNo TEXT");
//            database.execSQL("ALTER TABLE expense ADD COLUMN itemQuantity INTEGER NOT NULL DEFAULT 0");
//            //當添加int 類型數據時，需要添加默認值
//            database.execSQL("ALTER TABLE expense ADD COLUMN itemPrice INTEGER NOT NULL DEFAULT 0");
//        }
//    };
//
//    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            //當添加int 類型數據時，需要添加默認值
//            database.execSQL("ALTER TABLE expense ADD COLUMN itemTotal INTEGER NOT NULL DEFAULT 0");
//        }
//    };
}
