package com.capstone.android.instatour.src;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.capstone.android.instatour.src.search.dao.RecentDataDao;
import com.capstone.android.instatour.src.search.models.RecentData;


@Database(entities = {RecentData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecentDataDao recentDataDao();

    private static AppDatabase INSTANCE;

    private static final Object sLock = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "recent-data")
                        .build();
            }
            return INSTANCE;
        }
    }
}

// 싱글 톤 적용
