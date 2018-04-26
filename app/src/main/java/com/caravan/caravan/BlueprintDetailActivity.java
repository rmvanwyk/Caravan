package com.caravan.caravan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.DatabaseAccess;
import com.caravan.caravan.DynamoDB.Table;
import com.caravan.caravan.DynamoDB.UserDO;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by meaghan on 4/15/18.
 */

public class BlueprintDetailActivity extends Activity{
    private LayoutInflater inflater;
    private CuratedDO c_blueprint = null;
    private UserDO u_blueprint = null;
    private DatabaseAccess m_db;

    //@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blueprint_details);
        m_db = DatabaseAccess.getInstance(this);
        inflater = new LayoutInflater(this) {
            @Override
            public LayoutInflater cloneInContext(Context newContext) {
                return null;
            }
        };
        String bp = getIntent().getStringExtra("blueprint");
        try {
            UserDO b_blueprint = null;

            c_blueprint= (CuratedDO) m_db.getItem(bp, "blueprint", "curated");

            if(c_blueprint ==null){
                u_blueprint = (UserDO) m_db.getItem(bp, "blueprint", "user");

            }
            this.loadLocations();
        }
            catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
    }
    protected void loadLocations () {
        List<String> locList;
        if (c_blueprint != null){
            locList = c_blueprint.getLocationList();

        }
        else {
            locList = u_blueprint.getLocationList();


        }
        LinearLayout locContainer = (LinearLayout) findViewById(R.id.loc_view).findViewById(R.id.loc_horizontal_container);
        CuratedDO loc;
        View locView;
        for (int i = 0; i < locList.size(); i++) {
            try {
                CuratedDO currentLocation = (CuratedDO) m_db.getItem(locList.get(i), "location", "curated");
                //locView = (LinearLayout)locContainer.findViewById(R.id.loc_component_container);
                //LinearLayout locView = (LinearLayout)findViewById(R.id.loc_scroll).findViewById(R.id.loc_detail_container);
                locView = LayoutInflater.from(this).inflate(R.layout.item_location_overview, locContainer, false);
                TextView name = (TextView) locView.findViewById(R.id.overview_name);
                ImageView img = (ImageView) locView.findViewById(R.id.overview_image);
                TextView description = (TextView) locView.findViewById(R.id.overview_desc);

                name.setText(currentLocation.getName());
                description.setText(currentLocation.getDescription());
                locContainer.addView(locView);

                String image = currentLocation.getImageList().get(0);


                m_db.getImage(this, img, image);



                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View image) {
                        Intent i = new Intent(BlueprintDetailActivity.this, LocationDetailActivity.class);
                        i.putExtra("location", (String) name.getText());
                        startActivity(i);
                    }
                });

            }
            catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
    }


        }
    }

