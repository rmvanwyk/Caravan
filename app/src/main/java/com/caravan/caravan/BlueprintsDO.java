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

@DynamoDBTable(tableName = "caravan-mobilehub-2012693532-Blueprints")

public class BlueprintsDO {
    private String _blueprintId;
    private String _blueprintType;

    @DynamoDBHashKey(attributeName = "blueprintId")
    @DynamoDBAttribute(attributeName = "blueprintId")
    public String getBlueprintId() {
        return _blueprintId;
    }

    public void setBlueprintId(final String _blueprintId) {
        this._blueprintId = _blueprintId;
    }
    @DynamoDBRangeKey(attributeName = "blueprintType")
    @DynamoDBIndexHashKey(attributeName = "blueprintType", globalSecondaryIndexName = "bptype")
    public String getBlueprintType() {
        return _blueprintType;
    }

    public void setBlueprintType(final String _blueprintType) {
        this._blueprintType = _blueprintType;
    }

}
