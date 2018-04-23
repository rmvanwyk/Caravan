package com.caravan.caravan;

import android.app.Activity;
import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.DatabaseAccess;
import java.util.List;

/**
 * Created by meaghan on 4/15/18.
 */

public class BlueprintDetailActivity extends Activity{
    private LayoutInflater inflater;
    private CuratedDO _blueprint;
    private DatabaseAccess _db;

    //@Override
    protected void onCreate(Bundle savedInstanceState, CuratedDO bp){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blueprint_details);
        this._blueprint = bp;
        this.loadLocations();
    }
    protected void loadLocations () {

        List<String> locList = this._blueprint.getLocationList();
        LinearLayout locContainer = (LinearLayout)findViewById(R.id.loc_container);
        CuratedDO loc;
        View locView;
        for (int i = 0; i < locList.size(); i++) {
            locView = inflater.inflate(R.layout.item_location_overview, locContainer, false);
            loc = _db.getCuratedItem(locList.get(i), "location");
            TextView name = (TextView) locView.findViewById(R.id.overview_name);
            ImageView image = (ImageView) locView.findViewById(R.id.overview_image);
            TextView description = (TextView) locView.findViewById(R.id.overview_desc);

            locContainer.addView(locView);
        }
    }
}
