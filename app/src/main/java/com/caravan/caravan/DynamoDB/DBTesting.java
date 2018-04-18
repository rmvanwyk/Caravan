package com.caravan.caravan.DynamoDB;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.caravan.caravan.R;

public class DBTesting extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;

    public DBTesting () {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtesting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AWSMobileClient.getInstance().initialize(this).execute();
        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(
                        AWSMobileClient.getInstance().getConfiguration())
                .build();

        Button button = findViewById(R.id.button5);
        EditText text = findViewById(R.id.searchQuery);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = text.getText().toString();
                Log.d("EditText Result:", query);
                LocationsDO loc = new LocationsDO();
                loc.queryLocations(dynamoDBMapper, query);
            }
        });
    }
}

/*LocationsDO obj = new LocationsDO();
                obj.setLocationName(name);

                com.amazonaws.services.dynamodbv2.model.Condition rangeKeyCondition = new com.amazonaws.services.dynamodbv2.model.Condition()
                        .withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                        .withAttributeValueList(new AttributeValue().withS("Nashville"));

                DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                        .withHashKeyValues(obj)
                        .withRangeKeyCondition("locationCity", (com.amazonaws.services.dynamodbv2.model.Condition) rangeKeyCondition)
                        .withConsistentRead(false);

                PaginatedList<LocationsDO> result = dynamoDBMapper.query(LocationsDO.class, queryExpression);

                Gson gson = new Gson();
                StringBuilder stringBuilder = new StringBuilder();

                // Loop through query results
                for (int i = 0; i < result.size(); i++) {
                    String jsonFormOfItem = gson.toJson(result.get(i));
                    stringBuilder.append(jsonFormOfItem + "\n\n");
                }

                // Add your code here to deal with the data result
                Log.d("Query result: ", stringBuilder.toString());


                if (result.isEmpty()) {
                    // There were no items matching your query.
                }*/
