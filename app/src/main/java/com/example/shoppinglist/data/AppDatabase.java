package com.example.shoppinglist.data;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ShopItemDbModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;
    public static final String DB_NAME = "shop_item.db";

    public static AppDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    AppDatabase.class,
                    DB_NAME
            )
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    abstract ShopListDao shopListDao();
}
