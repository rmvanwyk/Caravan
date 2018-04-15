package com.caravan.caravan.RecentHistoryDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {RecentHistoryItem.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class RecentHistoryDatabase extends RoomDatabase {
    public abstract RecentHistoryDAO recentHistoryDAO();

    private static RecentHistoryDatabase INSTANCE;

    public static RecentHistoryDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            return Room.inMemoryDatabaseBuilder(context.getApplicationContext(), RecentHistoryDatabase.class).build();
        }
        return INSTANCE;
    }

    public static void destoryInstance() {
        INSTANCE = null;
    }
}
