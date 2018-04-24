package com.caravan.caravan;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.caravan.caravan.DynamoDB.DatabaseAccess;
import com.caravan.caravan.DynamoDB.UserDO;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class DBTesting extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;
    List<Document> memos;
    ImageView imageView;

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
                List<UserDO> result = task.getAllUserBlueprints();
                //while (result == null) {}
                if (result != null) {
                    Log.d("In For! ", String.valueOf(result.size()));
                    for (int i = 0; i < result.size(); i++) {
                        Log.d("Found User BP: ", result.get(i).getName());
                    }
                }
                else Log.d("Oh No! Result Set Was Empty! ", "##################");

                /*CuratedDO C = new CuratedDO();
                C.setName("HiThisIsATest1");
                C.setFollowerCount(17);
                C.setType("blueprint");
                C.setCity("Nashville");
                C.setLocationList(new ArrayList<>());
                task.userSaveBlueprint(C);*/

                /*UserDO U = null;
                try {
                    task.getItem("HiThisIsATest1", "blueprint", "user");
                    U = task.userDataObject;
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (U != null) {
                    Log.d("GetItem Name: ", U.getName());
                    task.addLocationToBlueprint(U, "BestLocationEVER");
                }
                else Log.d("GetItem Name: ", "##### NULL #####");*/

                /*CuratedDO C = new CuratedDO();

                C.setName("HiThisIsATest1");
                C.setFollowerCount(17);
                C.setType("blueprint");
                C.setCity("Nashville");
                C.setLocationList(new ArrayList<>());
                task.userSaveBlueprint(C);*/

                task.getImage(getApplicationContext(), imageView,"https://s3.amazonaws.com/caravan-userfiles-mobilehub-2012693532/public/finalSplash-01.png");

                //task.Query("Lo");

                //Log.d("Query Results: ", String.valueOf(task.results.size()));

           }
       });

    }

    private void initView() {
        imageView = findViewById(R.id.imageView6675);
        Log.d("View Initialized: ", "##########");
    }

private Target target = new Target() {
@Override
public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

        imageView.setImageBitmap(bitmap);
        }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

public void onBitmapFailed(Drawable errorDrawable) {
        imageView.setImageDrawable(errorDrawable);
        }

@Override
public void onPrepareLoad(Drawable placeHolderDrawable) {
        imageView.setImageDrawable(placeHolderDrawable);
        }
        };

}