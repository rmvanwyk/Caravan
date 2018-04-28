package com.caravan.caravan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.caravan.caravan.DynamoCacheDB.DynamoCacheDAO;
import com.caravan.caravan.DynamoCacheDB.DynamoCacheDatabase;
import com.caravan.caravan.DynamoCacheDB.Entity.BlueprintLocation;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprint;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprintLocationPairing;
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
    private String m_guideName;
    //private DynamoCacheDatabase dao = DynamoCacheDatabase.getInMemoryInstance(this);

    @Override
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
                m_guideName =u_blueprint.getName();
            }
            else{
                m_guideName = c_blueprint.getName();
            }
            TextView title = findViewById(R.id.title_blueprint);
            title.setText(m_guideName);
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
                try {
                    name.setText(currentLocation.getName());
                    description.setText(currentLocation.getDescription());
                    locContainer.addView(locView);

                String image = currentLocation.getImageList().get(0);


                m_db.getImage(this, img, image);



                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View image) {

                        Intent i = new Intent(BlueprintDetailActivity.this, LocationDetailActivity.class);
                        i.putExtra("location", (String) currentLocation.getName());
                        startActivity(i);
                    }
                });
                }
                catch (NullPointerException e ){}

            }
            catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            ImageView heart = findViewById(R.id.imageView2);
            Context c = this;
            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View image) {
                    IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
                    if(c_blueprint != null && identityManager != null && identityManager.isUserSignedIn() == true) {
                        DynamoCacheDAO dao = DynamoCacheDatabase.getInMemoryInstance(c).dynamoCacheDAO();
                        if (dao.getCuratedBlueprintById(c_blueprint.getName()) == null) {
                            heart.setImageResource(R.drawable.ic_favorite_white_24px);
                            heart.setColorFilter(ContextCompat.getColor(BlueprintDetailActivity.this, R.color.Pink));
                            dao.insertCuratedBlueprint(new CuratedBlueprint(c_blueprint.getName(), c_blueprint));
                            for (CuratedDO location : m_db.getBlueprintLocations(c_blueprint)) {
                                if(location != null) {
                                    if(dao.getBlueprintLocationById(location.getName()) == null)
                                        dao.insertBlueprintLocation(new BlueprintLocation(location.getName(), location));
                                    dao.insertCuratedBlueprintLocationPairing(new CuratedBlueprintLocationPairing(c_blueprint.getName(),location.getName()));
                                }
                            }
                            m_db.userSaveBlueprint(c_blueprint);
                        }
                        String logTag = "BLUEPRINT_DETAIL_ACTIVITY_CACHE";
                        Log.d(logTag, "Locations:");
                        for(BlueprintLocation location: dao.getAllBlueprintLocations())
                            Log.d(logTag, location.getId());
                        Log.d(logTag, "Blueprints:");
                        for(CuratedBlueprint blueprint: dao.getAllCuratedBlueprints())
                            Log.d(logTag,blueprint.getId());
                        Log.d(logTag,"Pairings");
                        for(CuratedBlueprintLocationPairing pairing: dao.getAllCuratedBlueprintLocationPairings())
                            Log.d(logTag,"BlueprintId: "+pairing.getBlueprint_id()+" LocationId: " + pairing.getLocation_id());
                    }
                }
            });

        }
/*
        private void cacheUserBlueprint(UserDO blueprint, String firstLoc) {
            DynamoCacheDatabase database = DynamoCacheDatabase.getInMemoryInstance(getActivity());
            final DynamoCacheDAO dao = database.dynamoCacheDAO();
            UserBlueprint cachedBlueprint = new UserBlueprint(blueprint.getId(),blueprint);
            List<UserBlueprint> existing = dao.getAllUserBlueprints();
            boolean exists = false;
            for (int i = 0; i < existing.size(); i++) {
                if (existing.get(i).getId().equals(cachedBlueprint.getId())) {
                    exists = true;
                }
            }
            if (!exists) {
                dao.insertUserBlueprint(cachedBlueprint);
            }
            DatabaseAccess task = DatabaseAccess.getInstance(getActivity());
            Future<CuratedDO> item = task.getCuratedItem("location", firstLoc);
            CuratedDO location = new CuratedDO();
            try {
                location = item.get();
            } catch (ExecutionException | InterruptedException e) {

            }

            BlueprintLocation blueprintLocation = new BlueprintLocation(location.getName(), location);
            if (dao.getLocationById(location.getName()) == null) {
                dao.insertBlueprintLocation(blueprintLocation);
            }
            UserBlueprintLocationPairing pair = new UserBlueprintLocationPairing(cachedBlueprint.getId(), blueprintLocation.getId());
            List<UserBlueprintLocationPairing> pairExisting =  dao.getAllUserBlueprintLocationPairings();
            boolean pairExists = false;
            for (int i = 0; i < pairExisting.size(); i++) {
                Log.d("SLRcontents:", pairExisting.get(i).getBlueprint_id());
                Log.d("SLRcontents:", pairExisting.get(i).getLocation_id());
                if (existing.get(i).equals(pair)) {
                    Log.d("Pair", "exists");
                    pairExists = true;
                }
            }
            if (!pairExists) {
                dao.insertUserBlueprintLocationPairing(pair);
            }
        }
 */

        }
    }

