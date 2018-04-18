package com.caravan.caravan.DynamoDB;

import android.util.Log;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.Map;

@DynamoDBTable(tableName = "caravan-mobilehub-2012693532-Neighborhoods")

public class NeighborhoodDO {
    private String _neighborhoodName;
    private String _neighborhoodCity;
    private Map<String, String> _blueprintList;
    private Map<String, String> _locationList;

    public NeighborhoodDO(String key, String range) {
        this.setNeighborhoodName(key);
        this.setNeighborhoodCity(range);
    }

    public NeighborhoodDO() {}

    private DynamoDBMapper dynamoDBMapper;

    @DynamoDBHashKey(attributeName = "neighborhoodName")
    @DynamoDBAttribute(attributeName = "neighborhoodName")
    public String getNeighborhoodName() {
        return _neighborhoodName;
    }

    public void setNeighborhoodName(final String _neighborhoodName) {
        this._neighborhoodName = _neighborhoodName;
    }
    @DynamoDBRangeKey(attributeName = "neighborhoodCity")
    @DynamoDBAttribute(attributeName = "neighborhoodCity")
    public String getNeighborhoodCity() {
        return _neighborhoodCity;
    }

    public void setNeighborhoodCity(final String _neighborhoodCity) {
        this._neighborhoodCity = _neighborhoodCity;
    }
    @DynamoDBAttribute(attributeName = "blueprintList")
    public Map<String, String> getBlueprintList() {
        return _blueprintList;
    }

    public void setBlueprintList(final Map<String, String> _blueprintList) {
        this._blueprintList = _blueprintList;
    }
    @DynamoDBAttribute(attributeName = "locationList")
    public Map<String, String> getLocationList() {
        return _locationList;
    }

    public void setLocationList(final Map<String, String> _locationList) {
        this._locationList = _locationList;
    }

    public void read(String name, String city) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                NeighborhoodDO neighborhoodItem = dynamoDBMapper.load(
                        NeighborhoodDO.class,
                        name,       // Partition key (hash key)
                        city);    // Sort key (range key)

                // Item read
                Log.d("Neighborhood Item:", neighborhoodItem.toString());
            }
        }).start();
    }
}
