package com.caravan.caravan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.DatabaseAccess;
import com.caravan.caravan.DynamoDB.UserDO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DBTesting extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;
    List<Document> memos;
    ImageView imageView;

    public DBTesting() {
    }

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
        //Context context = ;

        Button button = findViewById(R.id.button5);
        ImageView imageView = (ImageView) findViewById(R.id.imageView6675);
        /*Picasso.Builder pb = new Picasso.Builder(this);
        Picasso pic = pb.build();
        Picasso.setSingletonInstance(pic);
        //Picasso.get().load("https://s3.amazonaws.com/caravan-userfiles-mobilehub-2012693532/public/finalSplash-01.png").fit().into(imageView);
        */
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatabaseAccess task = DatabaseAccess.getInstance(DBTesting.this);

                /*for (int i = 0; i < 10; i++) {
                    String bpName = "UserTest"+i;
                    task.createBlueprint(bpName);
                    Log.d("Creating Blueprint: ", bpName);
                }*/
                /*List<UserDO> result = task.getAllUserBlueprints();
                //while (result == null) {}
                if (result != null) {
                    Log.d("In For! ", String.valueOf(result.size()));
                    for (int i = 0; i < result.size(); i++) {
                        Log.d("Found User BP: ", result.get(i).getName());
                    }
                }
                else Log.d("Oh No! Result Set Was Empty! ", "##################");*/


                /*List<Object> curatedResults = new ArrayList<>();

                Future<List<Object>> future = task.Query("Lo");
                try {
                    curatedResults = future.get();
                } catch (ExecutionException e) {

                } catch (InterruptedException e) {
                    future.cancel(true);
                }*/
                /*Log.d("Found Curated Results: ", String.valueOf(curatedResults.size()));
                if(curatedResults.size() == 0) {
                    curatedResults = task.results;
                    Log.d("Found Curated Results IF: ", String.valueOf(curatedResults.size()));
                }

                for (int i = 0; i < curatedResults.size(); i++) {
                    if (curatedResults.get(i) instanceof CuratedDO) {
                        CuratedDO result = (CuratedDO) curatedResults.get(i);
                        Log.d("Found Curated Object: ", result.getName());
                    } else {
                        UserDO result = (UserDO) curatedResults.get(i);
                        Log.d("Found User Object: ", result.getName());
                    }
                }*/


                /*CuratedDO C = new CuratedDO();
                C.setName("HiThisIsATest1");
                C.setFollowerCount(17);
                C.setType("blueprint");
                C.setCity("Nashville");
                C.setLocationList(new ArrayList<>());
                task.userSaveBlueprint(C);*/

                UserDO U = null;
                Object obj = null;
                try {
                    obj = task.getItem("Yazoo Brewery", "location", "curated");
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                if (obj instanceof CuratedDO) {
                    CuratedDO result = CuratedDO.class.cast(obj);
                    if (result != null) Log.d("Found Curated Object: ", result.toString());
                    if (result.getType() != null) Log.d("Type: ", result.getType());
                    if (result.getName() != null) Log.d("Name: ", result.getName());
                    if (result.getCity() != null) Log.d("City: ", result.getCity());
                    if (result.getDescription() != null) Log.d("Desc.: ", result.getDescription());
                    if (result.getImageList() != null && result.getImageList().size() > 0) Log.d("imgList: ", result.getImageList().get(0));
                    //if (result.getType() == "location") Log.d("Checking imgList: ", result.getImageList().toString());
                }
                else {
                    UserDO result = (UserDO) obj;
                    if (result != null) Log.d("Found User Object:17 ", result.getName());
                    U = result;
                }
                //else Log.d("GetItem Name: ", "##### NULL #####");

                /*CuratedDO C = new CuratedDO();

                C.setName("HiThisIsATest1");
                C.setFollowerCount(17);
                C.setType("blueprint");
                C.setCity("Nashville");
                C.setLocationList(new ArrayList<>());
                task.userSaveBlueprint(C);*/

                task.getImage(getApplicationContext(), imageView, "https://s3.amazonaws.com/caravan-userfiles-mobilehub-2012693532/public/finalSplash-01.png");

                //task.Query("Lo");

                //Log.d("Query Results: ", String.valueOf(task.results.size()));

            }
        });

    }
}