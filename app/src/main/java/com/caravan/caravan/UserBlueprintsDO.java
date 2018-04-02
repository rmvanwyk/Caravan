package com.caravan.caravan;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "caravan-mobilehub-2012693532-UserBlueprints")

public class UserBlueprintsDO {

    public UserBlueprintsDO(String key, String range) {
        //this.setLocationName(key);
        this.setBlueprintName(range);
    }

    public UserBlueprintsDO() {}

    private DynamoDBMapper dynamoDBMapper;

    private String _userId;
    private String _blueprintName;
    private String _blueprintCity;
    private Double _blueprintFollowerCount;
    private String _blueprintId;
    private Map<String, String> _locationList;
    private Map<String, String> _neighborhoodList;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "blueprintName")
    @DynamoDBAttribute(attributeName = "blueprintName")
    public String getBlueprintName() {
        return _blueprintName;
    }

    public void setBlueprintName(final String _blueprintName) {
        this._blueprintName = _blueprintName;
    }
    @DynamoDBAttribute(attributeName = "blueprintCity")
    public String getBlueprintCity() {
        return _blueprintCity;
    }

    public void setBlueprintCity(final String _blueprintCity) {
        this._blueprintCity = _blueprintCity;
    }
    @DynamoDBAttribute(attributeName = "blueprintFollowerCount")
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

}
