package com.caravan.caravan;

import android.content.Context;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;




public class AWSProvider {
    private static AWSProvider instance = null;
    private Context context;
    private AWSConfiguration awsConfiguration;
    private PinpointManager pinpointManager = null;

    // Declare DynamoDBMapper and AmazonDynamoDBClient private variables
    // to support data access methods
    private AmazonDynamoDBClient dbClient = null;
    private DynamoDBMapper dbMapper = null;

    public static AWSProvider getInstance() {
        return instance;
    }

    /*public DynamoDBMapper getDynamoDBMapper() {
        if (dbMapper == null) {
            final AWSCredentialsProvider cp = getIdentityManager().getCredentialsProvider();
            dbClient = new AmazonDynamoDBClient(cp);
            dbMapper = DynamoDBMapper.builder()
                    .awsConfiguration(getConfiguration())
                    .dynamoDBClient(dbClient)
                    .build();
        }
        return dbMapper;
    }*/
}
