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

@DynamoDBTable(tableName = "caravan-mobilehub-2012693532-Neighborhood")

public class NeighborhoodDO {
    private String _neighborhoodId;
    private String _neighborhoodName;

    @DynamoDBHashKey(attributeName = "neighborhoodId")
    @DynamoDBAttribute(attributeName = "neighborhoodId")
    public String getNeighborhoodId() {
        return _neighborhoodId;
    }

    public void setNeighborhoodId(final String _neighborhoodId) {
        this._neighborhoodId = _neighborhoodId;
    }
    @DynamoDBRangeKey(attributeName = "neighborhoodName")
    @DynamoDBIndexHashKey(attributeName = "neighborhoodName", globalSecondaryIndexName = "name")
    public String getNeighborhoodName() {
        return _neighborhoodName;
    }

    public void setNeighborhoodName(final String _neighborhoodName) {
        this._neighborhoodName = _neighborhoodName;
    }

}
