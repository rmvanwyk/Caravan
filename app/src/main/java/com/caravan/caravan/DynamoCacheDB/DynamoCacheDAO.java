package com.caravan.caravan.DynamoCacheDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprint;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprintLocationPairing;
import com.caravan.caravan.DynamoCacheDB.Entity.BlueprintLocation;
import com.caravan.caravan.DynamoCacheDB.Entity.Location;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprint;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprintLocationPairing;

import java.util.List;

@Dao
public interface DynamoCacheDAO {

    /*
        The following six methods insert or update one row at a time to the table that matches the parameter type.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBlueprintLocation(BlueprintLocation blueprintLocation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCuratedBlueprint(CuratedBlueprint curatedBlueprint);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCuratedBlueprintLocationPairing(CuratedBlueprintLocationPairing pairing);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocation(Location location);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserBlueprint(UserBlueprint userBlueprint);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserBlueprintLocationPairing(UserBlueprintLocationPairing pairing);


    /*
        The following six methods insert multiple rows at a time to the table that matches the
        parameter type. Any row you attempt to insert that has a primary key which is already in the table
        will replace the existing entry.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBlueprintLocations(List<BlueprintLocation> blueprintLocations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCuratedBlueprints(List<CuratedBlueprint> curatedBlueprint);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCuratedBlueprintLocationPairings(List<CuratedBlueprintLocationPairing> pairings);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocations(List<Location> locations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserBlueprints(List<UserBlueprint> userBlueprints);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserBlueprintLocationPairing(List<UserBlueprintLocationPairing> pairings);


    /*
        The following six methods delete all rows from the specified table.
     */

    @Query("delete from blueprint_locations")
    void deleteBlueprintLocationsTable();

    @Query("delete from curated_blueprints")
    void deleteCuratedBlueprintsTable();

    @Query("delete from curated_parings")
    void deleteCuratedPairingsTable();

    @Query("delete from locations")
    void deleteLocationsTable();

    @Query("delete from user_blueprints")
    void deleteUserBlueprintsTable();

    @Query("delete from user_pairings")
    void deleteUserPairingsTable();


    /*
        The following four methods are to delete a single line from the table specified in the query.
        It is important to note that the pairings tables are not included here. Deletion from these tables
        is handled automatically through cascade delete and cascade update

        NOTE:the pairings tables do not get updated when a Location or Blueprint gets updated, but the
        pairings associated with the updated entity will be deleted. That means you'll need to manually enter
        the new location-blueprint pairings after updating either entity.
     */

    @Query("delete from blueprint_locations where id=:id")
    void deleteRowFromBlueprintLocationsTable(String id);

    @Query("delete from curated_blueprints where id=:id")
    void deleteRowFromCuratedBlueprintsTable(String id);

    @Query("delete from locations where id=:id")
    void deleteRowFromLocationsTable(String id);

    @Query("delete from user_blueprints where id=:id")
    void deleteRowFromUserBlueprintsTable(String id);


    /*
        The next three methods are used to get a specific row from the table specified by the return type
        based on the provided id.
     */

    @Query("select * from locations where id=:id")
    Location getLocationById(String id);

    @Query("select * from curated_blueprints where id=:id")
    CuratedBlueprint getCuratedBlueprintById(String id);

    @Query("select * from user_blueprints where id=:id")
    UserBlueprint getUserBlueprintById(String id);


    /*
        The next three methods are used to get all rows from the table specified by the return type.
     */

    @Query("select * from locations")
    List<Location> getAllLocations();

    @Query("select * from curated_blueprints")
    List<CuratedBlueprint> getAllCuratedBlueprints();

    @Query("select * from user_blueprints")
    List<UserBlueprint> getAllUserBlueprints();

    /*
        The next two methods return a list of the location objects associated with the blueprint id provided.
        It is important that the id provided is of the blueprint type specified by the method signature
        (i.e. CuratedBlueprint id for #getLocationsFromCuratedBlueprintId and UserBlueprint id for
        #getLocationsFromUserBlueprintId).
     */

    @Query("select * from blueprint_locations where id in "
            + "(select location_id from curated_parings inner join curated_blueprints "
            + "on curated_blueprints.id = curated_parings.blueprint_id where :id = curated_blueprints.id)")
    List<BlueprintLocation> getLocationsFromCuratedBlueprintId(String id);

    @Query("select * from blueprint_locations where id in "
            + "(select location_id from user_pairings inner join user_blueprints "
            + "on user_pairings.blueprint_id = user_blueprints.id where :id = user_blueprints.id)")
    List<BlueprintLocation> getLocationsFromUserBlueprintId(String id);

    /*
       The following methods should only have to be used in testing. If these end up being used in application code,
       then delete this comment.
    */
    @Query("select * from blueprint_locations where id=:id")
    BlueprintLocation getBlueprintLocationById(String id);

    @Query("select * from curated_parings where blueprint_id=:blueprintId")
    CuratedBlueprintLocationPairing getCuratedBlueprintLocationPairingsFromBlueprintId(String blueprintId);
}
