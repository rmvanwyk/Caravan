package com.caravan.caravan.RecentHistoryDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {RecentHistoryItem.class}, version = 1)
@TypeConverters({RecentHistoryConverters.class})
public abstract class RecentHistoryDatabase extends RoomDatabase {
    public abstract RecentHistoryDAO recentHistoryDAO();

    private static RecentHistoryDatabase INSTANCE;
    private static RecentHistoryDatabase IN_MEMORY_INSTANCE;

    public static RecentHistoryDatabase getInstance(Context context) {
        if(INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecentHistoryDatabase.class, "recent_history_db").allowMainThreadQueries().build();
        return INSTANCE;
    }

    public static RecentHistoryDatabase getInMemoryInstance(Context context) {
        if(IN_MEMORY_INSTANCE == null)
            IN_MEMORY_INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), RecentHistoryDatabase.class).allowMainThreadQueries().build();
        return IN_MEMORY_INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public static void destroyInMemoryInstance() {
        IN_MEMORY_INSTANCE = null;
    }
}
