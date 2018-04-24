package com.caravan.caravan.DynamoDB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Synchronous implementation for the DynamoDB table access using the Document API.
 */
public class DatabaseAccess {
    /**
     * The Amazon Cognito POOL_ID to use for authentication and authorization.
     */
    private final String COGNITO_POOL_ID = "us-east-1:08e3fa0c-40eb-4531-98d5-e546df81b7ab";

    /**
     * The AWS Region that corresponds to the POOL_ID above
     */
    private final Regions COGNITO_REGION = Regions.US_EAST_1;

    /**
     * The name of the DynamoDB table used to store data.
     */
    private final String CURATED_TABLE = "caravan-mobilehub-2012693532-Curated";
    private final String USER_TABLE = "caravan-mobilehub-2012693532-User";

    /**
     * The types of data to be accessed.
     */
    private final String LOCATION_TYPE = "location";
    private final String BLUEPRINT_TYPE = "blueprint";
    private final String NEIGHBORHOOD_TYPE = "neighborhood";
    private final String CITY_TYPE = "city";
    private final String CURATED_COLLECTION = "curated";
    private final String USER_COLLECTION = "user";
    private final String USER_TYPE_INDEX = "uIDplusType";
    private final String H_TYPE_R_CITY_INDEX = "HtypeRcity";

    /**
     * The Android calling context
     */
    private Context context;

    /**
     * The Cognito Credentials Provider
     */
    private CognitoCachingCredentialsProvider credentialsProvider;

    /**
     * A connection to the DynamoDD service
     */
    private AmazonDynamoDBClient dbClient;
    private DynamoDBMapper dbMapper;

    /**
     * A reference to the DynamoDB table used to store data
     */
    private Table dbTable;

    /**
     * This class is a singleton - storage for the current instance.
     */
    private static volatile DatabaseAccess instance;

    /**
     * The data returned by the AsyncTasks
     */
    public List<Object> results;
    public List<UserDO> userdoResults;
    public List<CuratedDO> curateddoResults;
    public UserDO userDataObject;
    public CuratedDO curatedDataObject;

    /**
     * Creates a new DatabaseAccess instance.
     * @param context the calling context
     */
    private DatabaseAccess(Context context) {
        this.context = context;

        // Create a new credentials provider
        credentialsProvider = new CognitoCachingCredentialsProvider(context, COGNITO_POOL_ID, COGNITO_REGION);

        // Create a connection to the DynamoDB service
        dbClient = new AmazonDynamoDBClient(credentialsProvider);

        //Create a new DynamoDB Data Mapper
        this.dbMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dbClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        //Initialize the results List
        this.results = new ArrayList<>();
        this.userdoResults = new ArrayList<>();
        this.curateddoResults = new ArrayList<>();
        this.userDataObject = null;
        this.curatedDataObject = null;

        Picasso.Builder pb = new Picasso.Builder(context);
        Picasso P = pb.build();
        Picasso.setSingletonInstance(P);
    }

    public static synchronized DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    private UserDO convertCuratedDOToUserDO(CuratedDO C) {
        UserDO user = new UserDO();

        user.setUserId(credentialsProvider.getCachedIdentityId());
        user.setId(C.getId());
        user.setName(C.getName());
        user.setType(C.getType());
        user.setCity(C.getCity());
        user.setFollowerCount(C.getFollowerCount());
        if(C.getLocationList() != null) user.setLocationList(C.getLocationList());
        if(C.getAddress() != null) user.setAddress(C.getAddress());
        if(C.getDescription() != null) user.setDescription((C.getDescription()));
        if(C.getFoodDrinkRecommendation() != null) user.setFoodDrinkRecommendation(C.getFoodDrinkRecommendation());
        if(C.getPhoneNumber() != null) user.setPhoneNumber(C.getPhoneNumber());
        if(C.getPricePoint() != null) user.setPricePoint(C.getPricePoint());
        if(C.getTimeOfDay() != null) user.setTimeOfDay(C.getTimeOfDay());
        if(C.getWebsite() != null) user.setWebsite(C.getWebsite());

        return user;
    }

    public void userSaveBlueprint(CuratedDO BP) {
        final UserDO blueprint = convertCuratedDOToUserDO(BP);
        blueprint.setFollowerCount(BP.getFollowerCount()+1);
        BP.setFollowerCount(BP.getFollowerCount()+1);

        new Thread(() -> {
            dbMapper.save(blueprint);
            dbMapper.save(BP);
            // Item saved
        }).start();
    }

    public void userSaveLocation(CuratedDO LOC) {
        final UserDO blueprint = convertCuratedDOToUserDO(LOC);
        new Thread(() -> {
            dbMapper.save(blueprint);
            // Item saved
        }).start();
    }

    public void createBlueprint(String name) {
        final UserDO blueprint = new UserDO();
        List<String> locationList = new ArrayList<>();

        blueprint.setUserId(credentialsProvider.getCachedIdentityId());
        blueprint.setId(UUID.randomUUID().toString());
        blueprint.setName(name);
        blueprint.setType(BLUEPRINT_TYPE);
        blueprint.setLocationList(locationList);

        new Thread(() -> {
            dbMapper.save(blueprint);
            // Item saved
        }).start();
    }

    public void addLocationToBlueprint(UserDO U, String locationName) {
        List<String> newList = U.getLocationList();
        newList.add(locationName);
        U.setLocationList(newList);

        new Thread(() -> {
            dbMapper.save(U);
            // Item saved
        }).start();
    }

    public void renameUserBlueprint(UserDO U, String newName) {
        U.setName(newName);
        new Thread(() -> {
            dbMapper.save(U);
            // Item saved
        }).start();
    }

    public void deleteBlueprint(String name) {
        new Thread(() -> {
            final UserDO u = new UserDO();

            u.setUserId(credentialsProvider.getCachedIdentityId());
            u.setName(name);
            u.setType(BLUEPRINT_TYPE);

            dbMapper.delete(u);
            // Item saved
        }).start();
    }

    public void deleteLocation(String name) {
        new Thread(() -> {
            final UserDO u = new UserDO();

            u.setUserId(credentialsProvider.getCachedIdentityId());
            u.setName(name);
            u.setType(LOCATION_TYPE);

            dbMapper.delete(u);
            // Item saved
        }).start();
    }

    public List<CuratedDO> getBlueprintLocations(Object obj) throws IllegalAccessException, InstantiationException {
        List<String> locationList = new ArrayList<>();
        List<CuratedDO> results= new ArrayList<>();
        if (obj instanceof CuratedDO) {
            CuratedDO p = (CuratedDO) obj;
            locationList = p.getLocationList();
            for(int i = 0; i < locationList.size(); i++) {
                results.add((CuratedDO) getItem(locationList.get(i), LOCATION_TYPE, CURATED_COLLECTION));
            }
        }
        else if (obj instanceof UserDO) {
            UserDO u = (UserDO) obj;
            locationList = u.getLocationList();
            for(int i = 0; i < locationList.size(); i++) {
                results.add((CuratedDO) getItem(locationList.get(i), LOCATION_TYPE, CURATED_COLLECTION));
            }
        }
        return results;
    }

    public List<UserDO> getAllUserBlueprints() {
        UserDO obj = new UserDO();
        obj.setUserId(credentialsProvider.getCachedIdentityId());

        Condition rangeKeyCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(BLUEPRINT_TYPE));

        Map<String, Condition> rangeConds = new HashMap<>();
        rangeConds.put("type", rangeKeyCondition);

        DynamoDBQueryExpression<UserDO> queryExpression = new DynamoDBQueryExpression<>();
        queryExpression.setHashKeyValues(obj);
        queryExpression.setRangeKeyConditions(rangeConds);
        queryExpression.withIndexName(USER_TYPE_INDEX);
        queryExpression.withConsistentRead(false);

        GetBPAsyncTask task = new GetBPAsyncTask(queryExpression);
        try {
            task.execute().get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return task.getList();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetBPAsyncTask extends AsyncTask<Void, Void, List<UserDO>> {

        private final DynamoDBQueryExpression<UserDO> expr;
        private final List<UserDO> list;

        GetBPAsyncTask (DynamoDBQueryExpression<UserDO> e) {
            expr = e;
            list = new ArrayList<>();
        }

        @Override
        protected List<UserDO> doInBackground(Void... params) {
            List<UserDO> result = null;
            try {
                result = dbMapper.query(UserDO.class, expr);
            } catch (Exception e) {
                Log.d("ASYNC TASK ERROR5: ", e.toString());
            }
            if (result != null) list.addAll(result);
            return result;
        }

        protected List<UserDO> getList() {
            return this.list;
        }
    }

    public Object getItem(String name, String type, String collection) throws InstantiationException, IllegalAccessException {
        GetItemAsyncTask task = new GetItemAsyncTask(name, type, collection);
        task.execute();
        if (userDataObject != null) {
            Log.d("getItemUDO:: ", userDataObject.getName());
            return userDataObject;
        }
        else if(curatedDataObject != null) {
            Log.d("getItemCDO:: ", curatedDataObject.getName());
            return curatedDataObject;
        }
        return null;
    }

    public Future<CuratedDO> getCuratedItem(String Type, String Name) {
        return executor.submit(() -> {
            CuratedDO s = null;
            try {
                s = dbMapper.load(CuratedDO.class, Type, Name);
            } catch (Exception e) {
                Log.d("ASYNC TASK ERROR: ", e.toString());
            }
            return s;
        });
    }

    public Future<UserDO> getUserItem(String Name) {
        return executor.submit(() -> {
            UserDO s = null;
            try {
                s = dbMapper.load(UserDO.class, credentialsProvider.getCachedIdentityId(), Name);
            } catch (Exception e) {
                Log.d("ASYNC TASK ERROR: ", e.toString());
            }
            Log.d("getItem:: ", s.getName());
            return s;
        });
    }

    private void handleObject(Object U) {
        if (U.getClass() == UserDO.class) {
            this.userDataObject = (UserDO) U;
            this.curatedDataObject = null;
        }
        if (U.getClass() == CuratedDO.class) {
            this.curatedDataObject = (CuratedDO) U;
            this.userDataObject = null;
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class GetItemAsyncTask extends AsyncTask<Void, Void, Object> {

        private final String Name;
        private final String Type;
        private final String Collection;

        GetItemAsyncTask (String query, String type, String collection) {
            Name = query;
            Type = type;
            Collection = collection;
            //Result = null;
        }

        @Override
        protected Object doInBackground(Void... params) {
            Log.d("doInBackground: ", Name);
            if (Collection == CURATED_COLLECTION) { return getCuratedItem(Type, Name); }
            if (Collection == USER_COLLECTION) { return getUserItem(Name); }
            return null;
        }

        @Override
        protected void onPostExecute(Object U) {
            handleObject(U);
        }
    }

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    public Future<List<Object>> Query(String Q) {
        return executor.submit(() -> {
            GetQueryAsyncTask task = new GetQueryAsyncTask(Q);
            task.execute();
            return this.results;
        });
    }

    private List<CuratedDO> buildCuratedQuery(String query, String type) {
        CuratedDO obj = new CuratedDO();
        obj.setType(type);

        Condition rangeKeyCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                .withAttributeValueList(new AttributeValue().withS(query));

        Map<String, Condition> rangeConds = new HashMap<>();
        rangeConds.put("name", rangeKeyCondition);

        DynamoDBQueryExpression<CuratedDO> queryExpression = new DynamoDBQueryExpression<>();
                queryExpression.setHashKeyValues(obj);
                queryExpression.setRangeKeyConditions(rangeConds);
                queryExpression.withConsistentRead(false);

        List<CuratedDO> result = null;
        try {
            result = dbMapper.query(CuratedDO.class, queryExpression);
        } catch (Exception e) {
            Log.d("ASYNC TASK ERROR Cur: ", e.toString());
        }
    return result;
    }

    private List<CuratedDO> buildCuratedQueryIndex(String query, String type, String index, String attr) {
        CuratedDO obj = new CuratedDO();
        obj.setType(type);

        Condition rangeKeyCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                .withAttributeValueList(new AttributeValue().withS(query));

        Map<String, Condition> rangeConds = new HashMap<>();
        rangeConds.put(attr, rangeKeyCondition);

        DynamoDBQueryExpression<CuratedDO> queryExpression = new DynamoDBQueryExpression<>();
        queryExpression.setHashKeyValues(obj);
        queryExpression.setRangeKeyConditions(rangeConds);
        queryExpression.withIndexName(index);
        queryExpression.withConsistentRead(false);

        List<CuratedDO> result = null;
        try {
            result = dbMapper.query(CuratedDO.class, queryExpression);
        } catch (Exception e) {
            Log.d("ASYNC TASK ERROR Cur: ", e.toString());
        }
        return result;
    }

    private List<UserDO> buildUserQuery(String query, String type) {
        UserDO obj = new UserDO();
        obj.setUserId(credentialsProvider.getCachedIdentityId());

        Condition rangeKeyCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                .withAttributeValueList(new AttributeValue().withS(query));

        Map<String, Condition> rangeConds = new HashMap<>();
        rangeConds.put("name", rangeKeyCondition);

        DynamoDBQueryExpression<UserDO> queryExpression = new DynamoDBQueryExpression<>();
        queryExpression.setHashKeyValues(obj);
        queryExpression.setRangeKeyConditions(rangeConds);
        queryExpression.withConsistentRead(false);

        List<UserDO> result = null;
        try {
            result = dbMapper.query(UserDO.class, queryExpression);
        } catch (Exception e) {
            Log.d("ASYNC TASK ERROR Us: ", e.toString());
        }
        return result;
    }

    private List<UserDO> buildUserQueryIndex(String query, String type, String index, String attr) {
        UserDO obj = new UserDO();
        obj.setType(type);

        Condition rangeKeyCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                .withAttributeValueList(new AttributeValue().withS(query));

        Map<String, Condition> rangeConds = new HashMap<>();
        rangeConds.put(attr, rangeKeyCondition);

        DynamoDBQueryExpression<UserDO> queryExpression = new DynamoDBQueryExpression<>();
        queryExpression.setHashKeyValues(obj);
        queryExpression.setRangeKeyConditions(rangeConds);
        queryExpression.withIndexName(index);
        queryExpression.withConsistentRead(false);

        List<UserDO> result = null;
        try {
            result = dbMapper.query(UserDO.class, queryExpression);
        } catch (Exception e) {
            Log.d("ASYNC TASK ERROR Us: ", e.toString());
        }
        return result;
    }

    private void populateList(List<Object> memos) {
        this.results = memos;
        for (int i = 0; i < memos.size(); i++) {
            if (memos.get(i) instanceof CuratedDO) {
                CuratedDO result = (CuratedDO) memos.get(i);
                Log.d("Found Curated Object: ", result.getName());
            } else {
                UserDO result = (UserDO) memos.get(i);
                Log.d("Found User Object: ", result.getName());
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetQueryAsyncTask extends AsyncTask<Void, Void, List<Object>> {

        private final String Q;

        GetQueryAsyncTask (String query) {
            Q = query;
        }

        @Override
        protected List<Object> doInBackground(Void... params) {
            Log.d("doInBackground: ", Q);
            List<Object> newList = new ArrayList<>();
            Optional.ofNullable(buildCuratedQuery(Q, LOCATION_TYPE)).ifPresent(newList::addAll);
            Optional.ofNullable(buildCuratedQuery(Q, BLUEPRINT_TYPE)).ifPresent(newList::addAll);
            Optional.ofNullable(buildCuratedQuery(Q, CITY_TYPE)).ifPresent(newList::addAll);
            Optional.ofNullable(buildCuratedQuery(Q, NEIGHBORHOOD_TYPE)).ifPresent(newList::addAll);
            Optional.ofNullable(buildCuratedQueryIndex(Q, LOCATION_TYPE, H_TYPE_R_CITY_INDEX, CITY_TYPE)).ifPresent(newList::addAll);
            Optional.ofNullable(buildCuratedQueryIndex(Q, BLUEPRINT_TYPE, H_TYPE_R_CITY_INDEX, CITY_TYPE)).ifPresent(newList::addAll);
            Optional.ofNullable(buildUserQuery(Q, BLUEPRINT_TYPE)).ifPresent(newList::addAll);
            Optional.ofNullable(buildUserQuery(Q, LOCATION_TYPE)).ifPresent(newList::addAll);
            Optional.ofNullable(buildUserQueryIndex(Q, LOCATION_TYPE, H_TYPE_R_CITY_INDEX, CITY_TYPE)).ifPresent(newList::addAll);

            return newList;
        }

        @Override
        protected void onPostExecute(List<Object> newList) {
            Log.d("onPostExecute: SOA:", String.valueOf(newList.size()));
            populateList(newList);

        }
    }


    //==============================================================================================
    // AMAZON S3 IMPLEMENTATION
    //==============================================================================================

    public void getImage(Context C, ImageView I, String imgURL) {
        Picasso.get().load(imgURL).fit().into(I);
    }

    public void getAllLocationImages(Context C, ImageView I, UserDO UDO) {
        List<String> list= UDO.getImageList();
        for (int i = 0; i < list.size(); i++) {
            Picasso.get().load(list.get(i)).fit().into(I);
        }
    }

    public void getAllLocationImages(Context C, ImageView I, CuratedDO CDO) {
        List<String> list= CDO.getImageList();
        for (int i = 0; i < list.size(); i++) {
            Picasso.get().load(list.get(i)).fit().into(I);
        }
    }
}
