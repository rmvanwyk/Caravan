package com.caravan.caravan;

/**
 * Created by meaghan on 3/22/18.
 */
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//AWS & DynamoDB imports
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public int currentViewID;

    protected BottomNavigationView bottomNavigationView;
    // Declare a DynamoDBMapper object
    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // AWSMobileClient enables AWS user credentials to access your table
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {

            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {

                // Add code to instantiate a AmazonDynamoDBClient
                AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
                this.dynamoDBMapper = DynamoDBMapper.builder()
                        .dynamoDBClient(dynamoDBClient)
                        .awsConfiguration(
                                AWSMobileClient.getInstance().getConfiguration())
                        .build();

            }
        }).execute();

        /*

        IMPORTANT NOTE!

        Use Asynchronous Calls to DynamoDB

        Since calls to DynamoDB are synchronous, they don't belong on your UI thread.
        Use an asynchronous method like the Runnable wrapper to call DynamoDBObjectMapper in a separate thread.

        Runnable runnable = new Runnable() {
            public void run() {
                //DynamoDB calls go here
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();

         */

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        bottomNavigationView.postDelayed(() -> {
            switch (item.getItemId()) {
                case R.id.action_explore:
                    //startActivity(new Intent(this, ExploreActivity.class));
                    currentViewID = R.id.activity_explore;
                    break;
                case R.id.action_search:
                    startActivity(new Intent(this, SearchActivity.class));
                    currentViewID = R.id.activity_search;
                    break;
                case R.id.action_my_blueprints:
                    //startActivity(new Intent(this, BlueprintActivity.class));
                    currentViewID = R.id.activity_blueprint;
                    break;
            }
            finish();
        }, 300);
        return true;
    }

    private void updateNavigationBarState(){
        selectBottomNavigationBarItem(currentViewID);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

}