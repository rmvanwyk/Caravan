package com.caravan.caravan.DynamoCacheDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Blueprint.class, BlueprintLocationRelationship.class, Location.class}, version = 1)
public abstract class DynamoCacheDatabase extends RoomDatabase {
    public abstract DynamoCacheDAO dynamoCacheDAO();

    private static DynamoCacheDatabase INSTANCE;
    private static DynamoCacheDatabase IN_MEMORY_INSTANCE;

    public static DynamoCacheDatabase getInstance(Context context) {
        if(INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DynamoCacheDatabase.class, "").build();
        return INSTANCE;
    }

    public static DynamoCacheDatabase getInMemoryInstance(Context context) {
        if(IN_MEMORY_INSTANCE == null)
            IN_MEMORY_INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), DynamoCacheDatabase.class).build();
        return IN_MEMORY_INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public static void destroyInMemoryInstance() {
        IN_MEMORY_INSTANCE = null;
    }
}
