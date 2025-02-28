package com.example.mobilesuppproject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, UserSecurity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract ProductDao productDao();
    public abstract UserSecurityDao userSecurityDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "store_database"
            ).build();
        }
        return instance;
    }
}
