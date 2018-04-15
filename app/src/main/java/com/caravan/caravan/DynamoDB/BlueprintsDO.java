package com.caravan.caravan.DynamoDB;

import android.util.Log;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.Map;

@DynamoDBTable(tableName = "caravan-mobilehub-2012693532-Blueprints")

public class BlueprintsDO implements Explorable {
    private String _blueprintName;
    private String _blueprintCity;
    private Double _blueprintFollowerCount;
    private String _blueprintId;
    private Map<String, String> _locationList;
    private Map<String, String> _neighborhoodList;

    public BlueprintsDO(String key, String range) {
        this.setBlueprintName(key);
        this.setBlueprintCity(range);
    }

    public BlueprintsDO() {}

    private DynamoDBMapper dynamoDBMapper;

    @DynamoDBHashKey(attributeName = "blueprintName")
    @DynamoDBAttribute(attributeName = "blueprintName")
    public String getBlueprintName() {
        return _blueprintName;
    }

    public void setBlueprintName(final String _blueprintName) {
        this._blueprintName = _blueprintName;
    }
    @DynamoDBRangeKey(attributeName = "blueprintCity")
    @DynamoDBIndexHashKey(attributeName = "blueprintCity", globalSecondaryIndexName = "followers")
    public String getBlueprintCity() {
        return _blueprintCity;
    }

    public void setBlueprintCity(final String _blueprintCity) {
        this._blueprintCity = _blueprintCity;
    }
    @DynamoDBIndexRangeKey(attributeName = "blueprintFollowerCount", globalSecondaryIndexName = "followers")
    public Double getBlueprintFollowerCount() {
        return _blueprintFollowerCount;
    }

    public void setBlueprintFollowerCount(final Double _blueprintFollowerCount) {
        this._blueprintFollowerCount = _blueprintFollowerCount;
    }
    @DynamoDBAttribute(attributeName = "blueprintId")
    public String getBlueprintId() {
        return _blueprintId;
    }

    public void setBlueprintId(final String _blueprintId) {
        this._blueprintId = _blueprintId;
    }
    @DynamoDBAttribute(attributeName = "locationList")
    public Map<String, String> getLocationList() {
        return _locationList;
    }

    public void setLocationList(final Map<String, String> _locationList) {
        this._locationList = _locationList;
    }
    @DynamoDBAttribute(attributeName = "neighborhoodList")
    public Map<String, String> getNeighborhoodList() {
        return _neighborhoodList;
    }

    public void setNeighborhoodList(final Map<String, String> _neighborhoodList) {
        this._neighborhoodList = _neighborhoodList;
    }

    public void read(String name, String city) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                BlueprintsDO blueprintItem = dynamoDBMapper.load(
                        BlueprintsDO.class,
                        name,       // Partition key (hash key)
                        city);    // Sort key (range key)

                // Item read
                Log.d("Blueprint Item:", blueprintItem.toString());
            }
        }).start();
    }

    @Override
    public String getThumbnailPhoto() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";

    }
}

