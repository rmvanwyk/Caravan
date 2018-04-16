package com.caravan.caravan.DynamoDB;

import android.util.Log;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "caravan-mobilehub-2012693532-Cities")

public class CitiesDO extends DynamoCRUD {
    private String _cityId;
    private String _cityName;

    public CitiesDO(String key, String range) {
        this.setCityId(key);
        this.setCityName(range);
    }

    public CitiesDO() {}

    private DynamoDBMapper dynamoDBMapper;

    @DynamoDBHashKey(attributeName = "cityId")
    @DynamoDBAttribute(attributeName = "cityId")
    public String getCityId() {
        return _cityId;
    }

    public void setCityId(final String _cityId) {
        this._cityId = _cityId;
    }
    @DynamoDBRangeKey(attributeName = "cityName")
    @DynamoDBAttribute(attributeName = "cityName")
    public String getCityName() {
        return _cityName;
    }

    public void setCityName(final String _cityName) {
        this._cityName = _cityName;
    }

    public void read(String id, String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                CitiesDO cityItem = dynamoDBMapper.load(
                        CitiesDO.class,
                        id,       // Partition key (hash key)
                        name);    // Sort key (range key)

                // Item read
                Log.d("City Item:", cityItem.toString());
            }
        }).start();
    }
}
