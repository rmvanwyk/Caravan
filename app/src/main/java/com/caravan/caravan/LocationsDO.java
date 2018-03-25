package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "caravan-mobilehub-2012693532-Locations")

public class LocationsDO {
    private String _locationId;
    private String _locationType;
    private String _locationName;
    private String _timeOfDay;

    @DynamoDBHashKey(attributeName = "locationId")
    @DynamoDBAttribute(attributeName = "locationId")
    public String getLocationId() {
        return _locationId;
    }

    public void setLocationId(final String _locationId) {
        this._locationId = _locationId;
    }
    @DynamoDBRangeKey(attributeName = "locationType")
    @DynamoDBIndexHashKey(attributeName = "locationType", globalSecondaryIndexName = "type")
    public String getLocationType() {
        return _locationType;
    }

    public void setLocationType(final String _locationType) {
        this._locationType = _locationType;
    }
    @DynamoDBAttribute(attributeName = "locationName")
    public String getLocationName() {
        return _locationName;
    }

    public void setLocationName(final String _locationName) {
        this._locationName = _locationName;
    }
    @DynamoDBIndexHashKey(attributeName = "timeOfDay", globalSecondaryIndexName = "tod")
    public String getTimeOfDay() {
        return _timeOfDay;
    }

    public void setTimeOfDay(final String _timeOfDay) {
        this._timeOfDay = _timeOfDay;
    }

}
