package com.caravan.caravan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.DatabaseAccess;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by meaghan on 4/22/18.
 */

public class NeighborhoodDetailActivity extends Activity{
    private LayoutInflater inflater;
    private DatabaseAccess m_db;
    private String m_neighborhood;
    private CuratedDO m_neighborhood_obj;
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        m_db =  DatabaseAccess.getInstance(this);

        m_neighborhood = getIntent().getStringExtra("neighborhood");


        setContentView(R.layout.activity_neighborhood_details);

        try {
            m_neighborhood_obj = (CuratedDO) m_db.getItem(m_neighborhood, "neighborhood", "curated");
            loadContent();
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void loadContent(){
        LinearLayout container = (LinearLayout)findViewById(R.id.neighborhood_view).findViewById(R.id.neighborhood_curated_blockA).findViewById(R.id.neighborhood_curated_container);
        List<String> blueprints = m_neighborhood_obj.getBlueprintList();
        try{
            View thumbnail = container.findViewById(R.id.neighborhood_blueprint_thumbnailA);
            ImageView img = thumbnail.findViewById(R.id.neighborhood_curated_imageA);
            //IMAGE CALL
            TextView txt = thumbnail.findViewById(R.id.neighborhood_curated_textA);
            txt.setText(blueprints.get(0));

            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(NeighborhoodDetailActivity.this, BlueprintDetailActivity.class);
                    i.putExtra("blueprint", blueprints.get(0));
                    startActivity(i);

                }
            });


            thumbnail = container.findViewById(R.id.neighborhood_blueprint_thumbnailB);
            img = thumbnail.findViewById(R.id.neighborhood_curated_imageB);
            //IMAGE CALL
            txt = thumbnail.findViewById(R.id.neighborhood_curated_textB);
            txt.setText(blueprints.get(1));

            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(NeighborhoodDetailActivity.this, BlueprintDetailActivity.class);
                    i.putExtra("blueprint", blueprints.get(1));
                    startActivity(i);

                }
            });

            thumbnail = container.findViewById(R.id.neighborhood_blueprint_thumbnailC);
            img = thumbnail.findViewById(R.id.neighborhood_curated_imageC);
            //IMAGE CALL
            txt = thumbnail.findViewById(R.id.neighborhood_curated_textC);
            txt.setText(blueprints.get(2));

            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(NeighborhoodDetailActivity.this, BlueprintDetailActivity.class);
                    i.putExtra("blueprint", blueprints.get(2));
                    startActivity(i);

                }
            });

            thumbnail = container.findViewById(R.id.neighborhood_blueprint_thumbnailD);
            img = thumbnail.findViewById(R.id.neighborhood_curated_imageD);
            //IMAGE CALL
            txt = thumbnail.findViewById(R.id.neighborhood_curated_textD);
            txt.setText(blueprints.get(3));


            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(NeighborhoodDetailActivity.this, BlueprintDetailActivity.class);
                    i.putExtra("blueprint", blueprints.get(3));
                    startActivity(i);

                }
            });

        }
        catch(IndexOutOfBoundsException e){}

        /*
        *LOCATIONS
         */

        List<String>locations = m_neighborhood_obj.getLocationList();
        container = (LinearLayout)findViewById(R.id.neighborhood_view).findViewById(R.id.neighborhood_loc_blockA).findViewById(R.id.neighborhood_loc_container);
        try{
            View thumbnail = container.findViewById(R.id.neighborhood_loc_thumbnailA);
            ImageView img = thumbnail.findViewById(R.id.neighborhood_loc_imageA);

            CuratedDO loc = (CuratedDO) m_db.getItem(locations.get(0), "location", "curated");
            String image = loc.getImageList().get(0);
            m_db.getImage(this, img, image);

            TextView txt = thumbnail.findViewById(R.id.neighborhood_loc_textA);
            txt.setText(blueprints.get(0));

            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(NeighborhoodDetailActivity.this, LocationDetailActivity.class);
                    i.putExtra("location", locations.get(0));
                    startActivity(i);

                }
            });

            thumbnail = container.findViewById(R.id.neighborhood_loc_thumbnailB);
            img = thumbnail.findViewById(R.id.neighborhood_loc_imageB);

            loc = (CuratedDO) m_db.getItem(locations.get(1), "location", "curated");
            image = loc.getImageList().get(0);
            m_db.getImage(this, img, image);

            txt = thumbnail.findViewById(R.id.neighborhood_loc_textB);
            txt.setText(blueprints.get(1));


            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(NeighborhoodDetailActivity.this, LocationDetailActivity.class);
                    i.putExtra("location", locations.get(1));
                    startActivity(i);

                }
            });

            thumbnail = container.findViewById(R.id.neighborhood_loc_thumbnailC);
            img = thumbnail.findViewById(R.id.neighborhood_loc_imageC);

            loc = (CuratedDO) m_db.getItem(locations.get(2), "location", "curated");
            image = loc.getImageList().get(2);
            m_db.getImage(this, img, image);


            txt = thumbnail.findViewById(R.id.neighborhood_loc_textC);
            txt.setText(blueprints.get(2));


            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(NeighborhoodDetailActivity.this, LocationDetailActivity.class);
                    i.putExtra("location", locations.get(2));
                    startActivity(i);

                }
            });

            thumbnail = container.findViewById(R.id.neighborhood_loc_thumbnailD);
            img = thumbnail.findViewById(R.id.neighborhood_loc_imageD);

            loc = (CuratedDO) m_db.getItem(locations.get(3), "location", "curated");
            image = loc.getImageList().get(3);
            m_db.getImage(this, img, image);

            txt = thumbnail.findViewById(R.id.neighborhood_loc_textD);
            txt.setText(blueprints.get(3));

            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(NeighborhoodDetailActivity.this, LocationDetailActivity.class);
                    i.putExtra("location", locations.get(3));
                    startActivity(i);

                }
            });

        }
        catch(IndexOutOfBoundsException e){}
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }




    }




    }

