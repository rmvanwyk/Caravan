package com.caravan.caravan.DynamoCacheDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprint;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprintLocationPairing;
import com.caravan.caravan.DynamoCacheDB.Entity.Location;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprint;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprintLocationPairing;

import java.util.List;

@Dao
public interface DynamoCacheDAO {

    /*
        The following five methods insert or update one row at a time to the table that matches the parameter type.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCuratedBlueprint(CuratedBlueprint curatedBlueprint);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCuratedBlueprintLocationPairing(CuratedBlueprintLocationPairing pairing);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertLocation(Location location);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUserBlueprint(UserBlueprint userBlueprint);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUserBlueprintLocationPairing(UserBlueprintLocationPairing pairing);


    /*
        The following five methods insert multiple rows at a time to the table that matches the
        parameter type. Any row you attempt to insert that has a primary key which is already in the table
        will replace the existing entry.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCuratedBlueprints(List<CuratedBlueprint> curatedBlueprint);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCuratedBlueprintLocationPairings(List<CuratedBlueprintLocationPairing> pairings);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertLocations(List<Location> locations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUserBlueprints(List<UserBlueprint> userBlueprints);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUserBlueprintLocationPairing(List<UserBlueprintLocationPairing> pairings);


    /*
        The next three methods are used to get a specific row from the table specified by the return type
        based on the provided id.
     */

    @Query("select * from locations where id=:id")
    public Location getLocationById(String id);

    @Query("select * from curated_blueprints where id=:id")
    public CuratedBlueprint getCuratedBlueprintById(String id);

    @Query("select * from user_blueprints where id=:id")
    public UserBlueprint getUserBlueprintById(String id);


    /*
        The next three methods are used to get all rows from the table specified by the return type.
     */

    @Query("select * from locations")
    public List<Location> getAllLocations();

    @Query("select * from curated_blueprints")
    public List<CuratedBlueprint> getAllCuratedBlueprints();

    @Query("select * from user_blueprints")
    public List<UserBlueprint> getAllUserBlueprints();


    /*
        The next two methods return a list of the location objects associated with the id provided.
        It is important that the id provided by of the blueprint type specified by the method signature
        (i.e. CuratedBlueprint id for #getLocationsFromCuratedBlueprintId and UserBlueprint id for
        #getLocationsFromUserBlueprintId).
     */

    @Query("select * from locations where id in "
            + "(select location_id from curated_parings inner join curated_blueprints "
            + "on curated_blueprints.id = curated_parings.blueprint_id where :id = curated_blueprints.id)")
    public List<Location> getLocationsFromCuratedBlueprintId(String id);

    @Query("select * from locations where id in "
            + "(select location_id from user_pairings inner join user_blueprints "
            + "on user_pairings.blueprint_id = user_blueprints.id where :id = user_blueprints.id)")
    public List<Location> getLocationsFromUserBlueprintId(String id);


    /*
        Returns the set of locations not used in a stored blueprint. In other words, those locations saved
        individually from a blueprint. I don't think we should need to use this query. I think we should
        display all locations on the locations part of the account page (i.e. Locations saved individually
        and those saved as part of a blueprint). That would mean that I would prefer using
        #getAllUserBlueprints() for this purpose.
     */

    @Query("select * from locations where id not in "
            + "(select location_id from user_pairings) "
            + "and id not in (select location_id from curated_parings)")
    public List<Location> getLocationsNotInBlueprints();
}
