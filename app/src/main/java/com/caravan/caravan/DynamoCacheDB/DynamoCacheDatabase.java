package com.caravan.caravan.DynamoCacheDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.caravan.caravan.DynamoCacheDB.Entity.BlueprintLocation;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprint;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprintLocationPairing;
import com.caravan.caravan.DynamoCacheDB.Entity.Location;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprint;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprintLocationPairing;

@Database(entities = {BlueprintLocation.class, CuratedBlueprint.class, CuratedBlueprintLocationPairing.class, Location.class, UserBlueprint.class, UserBlueprintLocationPairing.class}, version = 2)
@TypeConverters({DynamoCacheConverters.class})
public abstract class DynamoCacheDatabase extends RoomDatabase {
    public abstract DynamoCacheDAO dynamoCacheDAO();

    private static DynamoCacheDatabase INSTANCE;
    private static DynamoCacheDatabase IN_MEMORY_INSTANCE;

    public static DynamoCacheDatabase getInstance(Context context) {
        if(INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DynamoCacheDatabase.class, "dynamo_cache_db").allowMainThreadQueries().build();
        return INSTANCE;
    }

    public static DynamoCacheDatabase getInMemoryInstance(Context context) {
        if(IN_MEMORY_INSTANCE == null)
            IN_MEMORY_INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), DynamoCacheDatabase.class).allowMainThreadQueries().build();
        return IN_MEMORY_INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public static void destroyInMemoryInstance() {
        IN_MEMORY_INSTANCE = null;
    }
}
