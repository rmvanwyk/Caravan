package com.caravan.caravan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.DatabaseAccess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meaghan on 4/19/18.
 */

public class LocationDetailActivity extends Activity {
    private CuratedDO m_location;
    private DatabaseAccess m_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        String loc= getIntent().getStringExtra("location");
        //m_location = m_db.getCuratedItem("location", loc);

        TextView name = (TextView) findViewById(R.id.loc_name);
        TextView details = (TextView) findViewById(R.id.loc_details);
        TextView description = (TextView) findViewById(R.id.loc_desc);
        TextView recommendation = (TextView) findViewById(R.id.loc_rec);

        name.setText(m_location.getName());
        details.setText((m_location.getTimeOfDay()).concat(" - ").concat(m_location.getPricePoint()));
        description.setText(m_location.getDescription());
        recommendation.setText(m_location.getFoodDrinkRecommendation());
    }

    private void saveClick() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        saveLocDialog save = saveLocDialog.newInstance(m_location.getName());
        save.show(ft, "dialog");
    }

}
