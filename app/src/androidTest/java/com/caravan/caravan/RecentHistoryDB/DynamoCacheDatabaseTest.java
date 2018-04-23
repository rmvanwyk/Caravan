package com.caravan.caravan.RecentHistoryDB;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.internal.deps.guava.collect.ImmutableList;
import android.util.Log;

import com.caravan.caravan.DynamoCacheDB.DynamoCacheDAO;
import com.caravan.caravan.DynamoCacheDB.Entity.BlueprintLocation;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprint;
import com.caravan.caravan.DynamoCacheDB.DynamoCacheDatabase;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprintLocationPairing;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprint;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprintLocationPairing;
import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.UserDO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class DynamoCacheDatabaseTest {
    DynamoCacheDatabase db;
    DynamoCacheDAO dynamoCacheDAO;

    private static final BlueprintLocation BLUEPRINT_LOCATION_1 = new BlueprintLocation("1", new CuratedDO("1","1","1", null, "1",
            "1","1", "1",null,null,
            "1","1","1","1", 1));
    private static final BlueprintLocation BLUEPRINT_LOCATION_2 = new BlueprintLocation("2", new CuratedDO("2","2","2", null, "2",
            "2","2","2", null,null,
            "2","2","2","2", 1));
    private static final BlueprintLocation BLUEPRINT_LOCATION_3 = new BlueprintLocation("3", new CuratedDO("3","3","3", null, "3",
            "3","3","3", null,null,
            "3","3","3","3", 1));
    private static final BlueprintLocation BLUEPRINT_LOCATION_4 = new BlueprintLocation("4", new CuratedDO("4","4","4", null, "4",
            "4","4","4", null,null,
            "4","4","4","4", 1));
    private static final BlueprintLocation BLUEPRINT_LOCATION_5 = new BlueprintLocation("5", new CuratedDO("5","5","5", null, "5",
            "5","5","5", null,null,
            "5","5","5","5", 1));
    private static final BlueprintLocation NEW_BLUEPRINT_LOCATION_1 = new BlueprintLocation("1", new CuratedDO("1","1","1", null, "1",
            "1","1", "1",null,null,
            "1","1","2","2", 1));
    private static final BlueprintLocation NEW_BLUEPRINT_LOCATION_3 = new BlueprintLocation("3", new CuratedDO("3","3","3", null, "3",
            "3","3","3", null,null,
            "3","3","4","4", 1));
    private static final List<String> USER_BLUEPRINT_1_LOCATION_LIST = ImmutableList.<String>builder().add("1").add("3").add("5").build();
    private static final UserBlueprint USER_BLUEPRINT_1 = new UserBlueprint("1", new UserDO("1","1","1","1","1",
            "1","1",USER_BLUEPRINT_1_LOCATION_LIST,"1","1",
            "1","1","1", 1));
    private static final UserBlueprint NEW_USER_BLUEPRINT_1 = new UserBlueprint("1", new UserDO("1","2","2","2","2",
            "2","1",USER_BLUEPRINT_1_LOCATION_LIST,"2","2",
            "2","2","2", 1));
    private static final List<String> CURATED_BLUEPRINT_1_LOCATION_LIST = ImmutableList.<String>builder().add("1").add("2").build();
    private static final CuratedBlueprint CURATED_BLUEPRINT_1 = new CuratedBlueprint("1", new CuratedDO("1","1","1", null, "1",
            "1","1", "1",CURATED_BLUEPRINT_1_LOCATION_LIST,null,
            "1","1","1","1", 1));
    private static final CuratedBlueprint NEW_CURATED_BLUEPRINT_1 = new CuratedBlueprint("1", new CuratedDO("2","1","2", null, "2",
            "2","2", "1",CURATED_BLUEPRINT_1_LOCATION_LIST,null,
            "2","2","2","2", 1));


    @Before
    public void initializeDatabase() {
        /*
            Use #getMemoryInstance when testing. #getInstance returns the file-system database, and any operations done on
            this instance will persist.
         */
        db = DynamoCacheDatabase.getInMemoryInstance(InstrumentationRegistry.getTargetContext());
        dynamoCacheDAO = db.dynamoCacheDAO();
        dynamoCacheDAO.insertCuratedBlueprint(CURATED_BLUEPRINT_1);
        dynamoCacheDAO.insertUserBlueprint(USER_BLUEPRINT_1);
        dynamoCacheDAO.insertBlueprintLocation(BLUEPRINT_LOCATION_1);
        dynamoCacheDAO.insertBlueprintLocation(BLUEPRINT_LOCATION_2);
        dynamoCacheDAO.insertBlueprintLocation(BLUEPRINT_LOCATION_3);
        dynamoCacheDAO.insertBlueprintLocation(BLUEPRINT_LOCATION_4);
        dynamoCacheDAO.insertBlueprintLocation(BLUEPRINT_LOCATION_5);
        dynamoCacheDAO.insertCuratedBlueprintLocationPairing(new CuratedBlueprintLocationPairing("1","1"));
        dynamoCacheDAO.insertCuratedBlueprintLocationPairing(new CuratedBlueprintLocationPairing("1","2"));
        dynamoCacheDAO.insertUserBlueprintLocationPairing(new UserBlueprintLocationPairing("1","1"));
        dynamoCacheDAO.insertUserBlueprintLocationPairing(new UserBlueprintLocationPairing("1","3"));
        dynamoCacheDAO.insertUserBlueprintLocationPairing(new UserBlueprintLocationPairing("1","5"));
    }

    @Test
    public void testGetLocationsFromCuratedBlueprintIdQuery() {
        boolean locationsAreCorrect = true;
        List<BlueprintLocation> associatedBlueprints = dynamoCacheDAO.getLocationsFromCuratedBlueprintId(CURATED_BLUEPRINT_1.getId());
        for(BlueprintLocation location: associatedBlueprints)
            if(!CURATED_BLUEPRINT_1_LOCATION_LIST.contains(location.getId()))
                locationsAreCorrect = false;
        assertTrue(locationsAreCorrect);
    }

    @Test
    public void testGetLocationsFromUserBlueprintIdQuery() {
        boolean locationsAreCorrect = true;
        List<BlueprintLocation> associatedBlueprints = dynamoCacheDAO.getLocationsFromUserBlueprintId(USER_BLUEPRINT_1.getId());
        for(BlueprintLocation location: associatedBlueprints)
            if(!USER_BLUEPRINT_1_LOCATION_LIST.contains(location.getId()))
                locationsAreCorrect = false;
        assertTrue(locationsAreCorrect);
    }

    @Test
    public void testInsertOnConflictReplace() {
        List<BlueprintLocation> locationList = ImmutableList.<BlueprintLocation>builder()
                .add(NEW_BLUEPRINT_LOCATION_1)
                .add(NEW_BLUEPRINT_LOCATION_3)
                .build();
        dynamoCacheDAO.insertBlueprintLocations(locationList);
        assertEquals(dynamoCacheDAO.getBlueprintLocationById(NEW_BLUEPRINT_LOCATION_1.getId()).getCuratedDO().getPhoneNumber(), NEW_BLUEPRINT_LOCATION_1.getCuratedDO().getPhoneNumber());
        assertEquals(dynamoCacheDAO.getBlueprintLocationById(NEW_BLUEPRINT_LOCATION_3.getId()).getCuratedDO().getPhoneNumber(), NEW_BLUEPRINT_LOCATION_3.getCuratedDO().getPhoneNumber());
    }

    @Test
    public void testCascadeDeleteForCuratedBlueprints() {
        CuratedBlueprint toDelete = CURATED_BLUEPRINT_1;
        assertNotNull(dynamoCacheDAO.getCuratedBlueprintLocationPairingsFromBlueprintId(toDelete.getId()));
        dynamoCacheDAO.deleteRowFromCuratedBlueprintsTable(toDelete.getId());
        assertNull(dynamoCacheDAO.getCuratedBlueprintLocationPairingsFromBlueprintId(toDelete.getId()));
    }

    @Test
    public void testCascadeDeleteForUserBlueprints() {
        UserBlueprint toDelete = USER_BLUEPRINT_1;
        assertNotNull(dynamoCacheDAO.getUserBlueprintLocationPairingsFromBlueprintId(toDelete.getId()));
        dynamoCacheDAO.deleteRowFromUserBlueprintsTable(toDelete.getId());
        assertNull(dynamoCacheDAO.getUserBlueprintLocationPairingsFromBlueprintId(toDelete.getId()));
    }

    @Test
    public void testCascadeDeleteOnInsertConflictReplaceForCuratedBlueprints() {
        assertNotNull(dynamoCacheDAO.getCuratedBlueprintLocationPairingsFromBlueprintId(NEW_CURATED_BLUEPRINT_1.getId()));
        dynamoCacheDAO.insertCuratedBlueprint(NEW_CURATED_BLUEPRINT_1);
        assertNull(dynamoCacheDAO.getCuratedBlueprintLocationPairingsFromBlueprintId(NEW_CURATED_BLUEPRINT_1.getId()));
    }

    @Test
    public void testCascadeDeleteOnInsertConflictReplaceForUserBlueprints() {
        assertNotNull(dynamoCacheDAO.getUserBlueprintLocationPairingsFromBlueprintId(NEW_USER_BLUEPRINT_1.getId()));
        dynamoCacheDAO.insertUserBlueprint(NEW_USER_BLUEPRINT_1);
        assertNull(dynamoCacheDAO.getUserBlueprintLocationPairingsFromBlueprintId(NEW_USER_BLUEPRINT_1.getId()));
    }

    @After
    public void destroyDatabase() {
        db.close();
        db.destroyInMemoryInstance();
    }
}
