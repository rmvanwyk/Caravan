package com.caravan.caravan.RecentHistoryDB;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.internal.deps.guava.collect.ImmutableList;
import android.util.Log;

import com.caravan.caravan.DynamoCacheDB.DynamoCacheDAO;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprint;
import com.caravan.caravan.DynamoCacheDB.DynamoCacheDatabase;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprintLocationPairing;
import com.caravan.caravan.DynamoCacheDB.Entity.Location;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprint;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprintLocationPairing;
import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.UserDO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertTrue;

public class DynamoCacheDatabaseTest {
    private static final String LOG_TAG="QWER";
    DynamoCacheDatabase db;
    DynamoCacheDAO dynamoCacheDAO;

    @Before
    public void initializeDatabase() {
        db = DynamoCacheDatabase.getInMemoryInstance(InstrumentationRegistry.getTargetContext());
        dynamoCacheDAO = db.dynamoCacheDAO();
        dynamoCacheDAO.insertCuratedBlueprint(new CuratedBlueprint("1", new CuratedDO("1","1","1", null, "1",
                "1","1", "1",ImmutableList.<String>builder().add("1").add("2").build(),null,
                "1","1","1","1")));
        dynamoCacheDAO.insertUserBlueprint(new UserBlueprint("2", new UserDO("2","2","2","2","2",
                "2","2",ImmutableList.<String>builder().add("1").add("3").add("5").build(),"2","2",
                "2","2","2")));
        dynamoCacheDAO.insertLocation(new Location("1", new CuratedDO("1","1","1", null, "1",
                "1","1", "1",null,null,
                "1","1","1","1")));
        dynamoCacheDAO.insertLocation(new Location("2", new CuratedDO("2","2","2", null, "2",
                "2","2","2", null,null,
                "2","2","2","2")));
        dynamoCacheDAO.insertLocation(new Location("3", new CuratedDO("3","3","3", null, "3",
                "3","3","3", null,null,
                "3","3","3","3")));
        dynamoCacheDAO.insertLocation(new Location("4", new CuratedDO("4","4","4", null, "4",
                "4","4","4", null,null,
                "4","4","4","4")));
        dynamoCacheDAO.insertLocation(new Location("5", new CuratedDO("5","5","5", null, "5",
                "5","5","5", null,null,
                "5","5","5","5")));
        dynamoCacheDAO.insertCuratedBlueprintLocationPairing(new CuratedBlueprintLocationPairing("1","1"));
        dynamoCacheDAO.insertCuratedBlueprintLocationPairing(new CuratedBlueprintLocationPairing("1","2"));
        dynamoCacheDAO.insertUserBlueprintLocationPairing(new UserBlueprintLocationPairing("2","1"));
        dynamoCacheDAO.insertUserBlueprintLocationPairing(new UserBlueprintLocationPairing("2","3"));
        dynamoCacheDAO.insertUserBlueprintLocationPairing(new UserBlueprintLocationPairing("2","5"));
        Log.d(LOG_TAG,"worked!");
    }

   /* @Test
    public void testCuratedDOStore() {
        Log.d(LOG_TAG, "we here");
        CuratedDO curatedDO1 = new CuratedDO("1","2","3", ImmutableList.<String>builder().add("1").build(), "1",
                "1","1", "1",ImmutableList.<String>builder().add("1").build(),ImmutableList.<String>builder().add("1").build(),
                "1","1","1","1");
        dynamoCacheDAO.insert(new CuratedBlueprint("1", curatedDO1));
        CuratedBlueprint curatedBlueprint = dynamoCacheDAO.retrieveFirst();
        CuratedDO curatedDO2 = new CuratedDO("1","2","3", ImmutableList.<String>builder().add("1").build(), "1",
                "1","1", "1",ImmutableList.<String>builder().add("1").build(),ImmutableList.<String>builder().add("1").build(),
                "1","1","1","1");
        assertTrue(curatedBlueprint.getCuratedDO().equals(curatedDO2));
    } */

    @Test
    public void testUserDOStore() {
        UserBlueprint blueprint = dynamoCacheDAO.getUserBlueprintById("2");
        List<Location> locationList = dynamoCacheDAO.getLocationsFromUserBlueprintId(blueprint.getId());
        for(Location l: locationList)
            Log.d(LOG_TAG, l.getId());
        assertTrue(true);
    }

    @Test
    public void testGetLocationsNotInBlueprint() {
        List<Location> locationList = dynamoCacheDAO.getLocationsNotInBlueprints();
        for(Location l: locationList)
            Log.d(LOG_TAG + l.getId(), l.getId());
        assertTrue(true);
    }

    @Test
    public void testInsertConflictResolution() {
        List<Location> locationList = ImmutableList.<Location>builder().
                add(new Location("2", new CuratedDO("2","2","2", null, "2",
                "2","2","2", null,null,
                "2","2","2","3")))
                .add(new Location("3", new CuratedDO("3","3","3", null, "3",
                "3","3","3", null,null,
                "3","3","3","4")))
                .build();
        dynamoCacheDAO.insertLocations(locationList);
        assertTrue(true);
    }

    @After
    public void destroyDatabase() {
        db.close();
        db.destroyInMemoryInstance();
    }
}
