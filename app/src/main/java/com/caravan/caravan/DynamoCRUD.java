package com.caravan.caravan;

import android.util.Log;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Zach on 3/25/18.
 */

public class DynamoCRUD {

    public DynamoCRUD () {

    }
    private DynamoDBMapper dynamoDBMapper;

    public void create(Class<Object> type, String key, String range) throws NoSuchMethodException, InvocationTargetException {
        Object obj = null;
        try {
            obj = type.getDeclaredConstructor(String.class).newInstance(key,range);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Object finalObj = obj;
        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(finalObj);
                // Item saved
            }
        }).start();
    }

    public void read(Class type, String key, String range) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Object obj = null;
                try {
                    obj = type.getDeclaredConstructor(String.class).newInstance(key,range);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                obj = dynamoDBMapper.load(
                        type,
                        key,       // Partition key (hash key)
                        range);    // Sort key (range key)

                // Item read
                Log.d("Read Object:", obj.toString());
            }
        }).start();
    }

    public void update(Class<Object> type, String key, String range) {
        Object obj = null;
        try {
            obj = type.getDeclaredConstructor(String.class).newInstance(key,range);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        Object finalObj = obj;

        new Thread(new Runnable() {
            @Override
            public void run() {

                dynamoDBMapper.save(finalObj);

                // Item updated
            }
        }).start();
    }

    public void delete(Class<Object> type, String key, String range) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Object obj = null;
                try {
                    obj = type.getDeclaredConstructor(String.class).newInstance(key,range);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                Object finalObj = obj;
                dynamoDBMapper.delete(finalObj);

                // Item deleted
            }
        }).start();
    }


    /*
    public void queryCities() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                CitiesDO city = new CitiesDO();
                city.setCityId(identityManager.getCachedUserID());
                city.setName("Article1");

                Condition rangeKeyCondition = new Condition()
                        .withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                        .withAttributeValueList(new AttributeValue().withS("Trial"));

                DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                        .withHashKeyValues("Id")
                        .withRangeKeyCondition("articleId", rangeKeyCondition)
                        .withConsistentRead(false);

                PaginatedList<CitiesDO> result = dynamoDBMapper.query(CitiesDO.class, queryExpression);

                Gson gson = new Gson();
                StringBuilder stringBuilder = new StringBuilder();

                // Loop through query results
                for (int i = 0; i < result.size(); i++) {
                    String jsonFormOfItem = gson.toJson(result.get(i));
                    stringBuilder.append(jsonFormOfItem + "\n\n");
                }

                // Add your code here to deal with the data result
                Log.d("Query result: ", stringBuilder.toString());

                if (result.isEmpty()) {
                    // There were no items matching your query.
                }
            }
        }).start();
    }*/



}