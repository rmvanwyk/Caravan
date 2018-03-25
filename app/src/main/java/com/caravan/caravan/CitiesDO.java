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

@DynamoDBTable(tableName = "caravan-mobilehub-2012693532-Cities")

public class CitiesDO {
    private String _cityId;
    private String _name;

    @DynamoDBHashKey(attributeName = "cityId")
    @DynamoDBIndexRangeKey(attributeName = "cityId", globalSecondaryIndexName = "name")
    public String getCityId() {
        return _cityId;
    }

    public void setCityId(final String _cityId) {
        this._cityId = _cityId;
    }
    @DynamoDBRangeKey(attributeName = "name")
    @DynamoDBIndexHashKey(attributeName = "name", globalSecondaryIndexName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }

}
