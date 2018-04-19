package com.caravan.caravan;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    protected List<Object> results;
    protected String item;

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
    }

    public static synchronized DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
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

    public void addLocationToBlueprint(String blueprintName, String locationName) {
        final UserDO[] blueprint = {new UserDO()};

        new Thread(() -> {
            blueprint[0] = dbMapper.load(
                    UserDO.class,
                    credentialsProvider.getCachedIdentityId(),
                    blueprintName);
        }).start();

        List<String> newList = blueprint[0].getLocationList();
        newList.add(locationName);
        blueprint[0].setLocationList(newList);

        new Thread(() -> {
            dbMapper.save(blueprint[0]);
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

    public List<String> getAllUserBlueprints() {
        // Create a table reference
        return new ArrayList<>();
    }

    public String getItem(String name, String type, String collection) {
        GetItemAsyncTask task = new GetItemAsyncTask(name, type, collection);
        task.execute();
        return this.item;
    }

    private void handleItemString(String s) {
        this.item = s;
    }

    private CuratedDO getCuratedItem(String Type, String Name) {
        dbTable = Table.loadTable(dbClient, CURATED_TABLE);
        CuratedDO s = null;
        try {
            s = dbMapper.load(CuratedDO.class, Type, Name);
        } catch (Exception e) {
            Log.d("ASYNC TASK ERROR: ", e.toString());
        }
        return s;
    }

    private UserDO getUserItem(String Type, String Name) {
        dbTable = Table.loadTable(dbClient, USER_TABLE);
        UserDO s = null;
        try {
            s = dbMapper.load(UserDO.class, Type, Name);
        } catch (Exception e) {
            Log.d("ASYNC TASK ERROR: ", e.toString());
        }
        return s;
    }

    private class GetItemAsyncTask extends AsyncTask<Void, Void, Object> {

        private final String Name;
        private final String Type;
        private final String Collection;

        GetItemAsyncTask (String query, String type, String collection) {
            Name = query;
            Type = type;
            Collection = collection;
        }

        @Override
        protected Object doInBackground(Void... params) {
            Log.d("doInBackground: ", Name);
            // Create a table reference
            Object s = null;
            if (Collection == "curated") s = getCuratedItem(Type, Name);
            if (Collection == "user") s = getUserItem(Type, Name);
            return s;
        }

        //@Override
        protected void onPostExecute(String s) {
            handleItemString(s);
        }
    }

    public void Query(String Q) {
        GetQueryAsyncTask task = new GetQueryAsyncTask(Q);
        task.execute();
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
            Log.d("ASYNC TASK ERROR: ", e.toString());
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
            Log.d("ASYNC TASK ERROR: ", e.toString());
        }
        return result;
    }

    private void populateList(List<Object> memos) {
        this.results = memos;
        Log.d("populateList: ", String.valueOf(memos.size()));
        for (int i = 0; i < this.results.size(); i++) {
            Log.d("Query result: ", this.results.get(i).toString());
        }
    }

    private class GetQueryAsyncTask extends AsyncTask<Void, Void, List<Object>> {

        private final String Q;

        GetQueryAsyncTask (String query) {
            Q = query;
        }

        @Override
        protected List<Object> doInBackground(Void... params) {
            Log.d("doInBackground: ", Q);
            List<Object> newList = new ArrayList<>();
            newList.addAll(buildCuratedQuery(Q, LOCATION_TYPE));
            newList.addAll(buildCuratedQuery(Q, BLUEPRINT_TYPE));
            newList.addAll(buildCuratedQuery(Q, CITY_TYPE));
            newList.addAll(buildCuratedQuery(Q, NEIGHBORHOOD_TYPE));
            newList.addAll(buildUserQuery(Q, BLUEPRINT_TYPE));
            newList.addAll(buildUserQuery(Q, LOCATION_TYPE));
            return newList;
        }

        //@Override
        protected void onPostExecute(List<Object> newList) {
            Log.d("onPostExecute: SOA:", String.valueOf(newList.size()));
            if (newList.size() != 0) {
                populateList(newList);
            }
        }
    }
}
