package com.caravan.caravan.DynamoDB;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.DatabaseAccess;
import com.caravan.caravan.DynamoDB.UserDO;

import java.util.ArrayList;
import java.util.List;

public class DBTesting extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;
    List<Document> memos;

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
                DatabaseAccess task = DatabaseAccess.getInstance(DBTesting.this);

                /*for (int i = 0; i < 10; i++) {
                    String bpName = "UserTest"+i;
                    task.createBlueprint(bpName);
                    Log.d("Creating Blueprint: ", bpName);
                }*/
                List<UserDO> result = task.getAllUserBlueprints();
                //while (result == null) {}
                if (result != null) {
                    Log.d("In For! ", String.valueOf(result.size()));
                    for (int i = 0; i < result.size(); i++) {
                        Log.d("Found User BP: ", result.get(i).getName());
                    }
                }
                else Log.d("Oh No! Result Set Was Empty! ", "##################");

                CuratedDO C = new CuratedDO();
                C.setName("HiThisIsATest1");
                C.setFollowerCount(17);
                C.setType("blueprint");
                C.setCity("Nashville");
                C.setLocationList(new ArrayList<>());
                task.userSaveBlueprint(C);

                UserDO U = (UserDO) task.getItem("HiThisIsATest1", "blueprint", "user");
                if (U != null) {
                    Log.d("GetItem Name: ", U.getName());
                    task.addLocationToBlueprint(U, "BestLocationEVER");
                }
                else Log.d("GetItem Name: ", "##### NULL #####");


                //task.Query("Lo");

                //Log.d("Query Results: ", String.valueOf(task.results.size()));

            }
        });
    }
}