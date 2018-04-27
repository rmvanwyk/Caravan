package com.caravan.caravan;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.DatabaseAccess;

import java.util.concurrent.ExecutionException;

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
        String loc = getIntent().getStringExtra("location");
        m_db = DatabaseAccess.getInstance(this);

        try {
            m_location = (CuratedDO) m_db.getItem(loc, "location", "curated");
            LinearLayout locView = (LinearLayout)findViewById(R.id.loc_scroll).findViewById(R.id.loc_detail_container);
            TextView name = (TextView) locView.findViewById(R.id.loc_title_bar).findViewById(R.id.loc_name);
            TextView details = (TextView) locView.findViewById(R.id.loc_details);
            TextView description = (TextView) locView.findViewById(R.id.loc_desc);
            TextView recommendation = (TextView) locView.findViewById(R.id.loc_rec);
            ImageView img = findViewById(R.id.loc_image);
            String image = m_location.getImageList().get(0);
            m_db.getImage(this, img, image);

            name.setText("\n".concat(m_location.getName()).concat("\n").concat(m_location.getAddress().concat("\n")));
            details.setText((m_location.getTimeOfDay()).concat(" - ").concat(m_location.getPricePoint()).concat("\n").concat(m_location.getPhoneNumber()).concat(" | ").concat(m_location.getWebsite()));
            description.setText(m_location.getDescription());
            recommendation.setText(m_location.getFoodDrinkRecommendation());

            ImageView save = findViewById(R.id.loc_save);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View image) {

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    // Create and show the dialog.
                    saveLocDialog newFragment = saveLocDialog.newInstance(m_location.getName());
                    newFragment.show(ft, "dialog");
                }
            });

        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }



}
