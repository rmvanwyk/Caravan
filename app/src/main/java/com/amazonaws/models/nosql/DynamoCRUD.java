package com.amazonaws.models.nosql;

/**
 * Created by Zach on 3/25/18.
 */

public class DynamoCRUD {
    public void createCity(String id, String name) {
        final com.amazonaws.models.nosql.CitiesDO cityItem = new com.amazonaws.models.nosql.CitiesDO();

        cityItem.setCityId(id);
        cityItem.setName(name);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(cityItem);
                // Item saved
            }
        }).start();
    }


}
