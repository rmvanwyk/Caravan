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

public class CityDetailActivity extends Activity {
    private LayoutInflater inflater;
    private DatabaseAccess m_db;
    private String m_city;
    private CuratedDO m_city_obj;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        m_db = DatabaseAccess.getInstance(this);

        String m_city = getIntent().getStringExtra("city");
        try {
            m_city_obj = (CuratedDO) m_db.getItem(m_city, "city", "curated");
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_city_details);
        loadContent();
    }

    private void loadContent(){
        LinearLayout root = (LinearLayout)findViewById(R.id.vertical_scroll).findViewById(R.id.vertical_scroll_container);
        LinearLayout n_container = (LinearLayout)root.findViewById(R.id.city_neighborhood_block).findViewById(R.id.city_neighborhood_block_container);
        //load neighborhoods
        List<String>neighborhoods = m_city_obj.getNeighborhoodList();
        try{
            View thumbnail = n_container.findViewById(R.id.n_thumbnailA);
            ImageView img = thumbnail.findViewById(R.id.city_neighborhood_imageA);
            //IMAGE CALL
            TextView txt = thumbnail.findViewById(R.id.city_neighborhood_textA);
            txt.setText(neighborhoods.get(0));
            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(CityDetailActivity.this, NeighborhoodDetailActivity.class);
                    i.putExtra("neighborhood", neighborhoods.get(0));
                    startActivity(i);

                }
            });
            thumbnail = n_container.findViewById(R.id.n_thumbnailB);
             img = thumbnail.findViewById(R.id.city_neighborhood_imageB);
            //IMAGE CALL
            txt = thumbnail.findViewById(R.id.city_neighborhood_textB);
            txt.setText(neighborhoods.get(1));
            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(CityDetailActivity.this, NeighborhoodDetailActivity.class);
                    i.putExtra("neighborhood", neighborhoods.get(1));
                    startActivity(i);

                }
            });




            thumbnail = n_container.findViewById(R.id.n_thumbnailC);
            img = thumbnail.findViewById(R.id.city_neighborhood_imageC);
            //IMAGE CALL
            txt = thumbnail.findViewById(R.id.city_neighborhood_textC);
            txt.setText(neighborhoods.get(2));

            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(CityDetailActivity.this, NeighborhoodDetailActivity.class);
                    i.putExtra("neighborhood", neighborhoods.get(2));
                    startActivity(i);

                }
            });


            thumbnail = n_container.findViewById(R.id.n_thumbnailD);
            img = thumbnail.findViewById(R.id.city_neighborhood_imageD);
            //IMAGE CALL
            txt = thumbnail.findViewById(R.id.city_neighborhood_textD);
            txt.setText(neighborhoods.get(3));

            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(CityDetailActivity.this, NeighborhoodDetailActivity.class);
                    i.putExtra("neighborhood", neighborhoods.get(3));
                    startActivity(i);

                }
            });


        }
        catch(IndexOutOfBoundsException e){}

        //load blueprints
        LinearLayout b_container = (LinearLayout)root.findViewById(R.id.city_curated_blockA).findViewById(R.id.city_blockA_container);
        List<String>blueprints = m_city_obj.getBlueprintList();


        try{
            View thumbnail = b_container.findViewById(R.id.b_thumnailA);
            ImageView img = thumbnail.findViewById(R.id.city_curated_imageA);
            //IMAGE CALL
            TextView txt = thumbnail.findViewById(R.id.city_neighborhood_textA);
            txt.setText(blueprints.get(0));

            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(CityDetailActivity.this, BlueprintDetailActivity.class);
                    i.putExtra("blueprint", blueprints.get(0));
                    startActivity(i);

                }
            });


            thumbnail = b_container.findViewById(R.id.b_thumbnailB);
            img = thumbnail.findViewById(R.id.city_curated_imageB);
            //IMAGE CALL
            txt = thumbnail.findViewById(R.id.city_curated_textB);
            txt.setText(blueprints.get(1));

            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(CityDetailActivity.this, BlueprintDetailActivity.class);
                    i.putExtra("blueprint", blueprints.get(1));
                    startActivity(i);

                }
            });

            thumbnail = b_container.findViewById(R.id.b_thumbnailC);
            img = thumbnail.findViewById(R.id.city_curated_imageC);
            //IMAGE CALL
            txt = thumbnail.findViewById(R.id.city_curated_textC);
            txt.setText(blueprints.get(2));

            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(CityDetailActivity.this, BlueprintDetailActivity.class);
                    i.putExtra("blueprint", blueprints.get(2));
                    startActivity(i);

                }
            });

            thumbnail = b_container.findViewById(R.id.b_thumbnailD);
            img = thumbnail.findViewById(R.id.city_curated_imageD);
            //IMAGE CALL
            txt = thumbnail.findViewById(R.id.city_curated_textD);
            txt.setText(blueprints.get(3));


            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(CityDetailActivity.this, BlueprintDetailActivity.class);
                    i.putExtra("blueprint", blueprints.get(3));
                    startActivity(i);

                }
            });

        }
        catch(IndexOutOfBoundsException e){}

        //load locations
        List<String>locations = m_city_obj.getLocationList();
        LinearLayout l_container = (LinearLayout)root.findViewById(R.id.city_loc_blockA).findViewById(R.id.city_loc_container);

        try{
            View thumbnail = l_container.findViewById(R.id.l_thumbnailA);
            ImageView img = thumbnail.findViewById(R.id.city_loc_imageA);

            CuratedDO loc = (CuratedDO) m_db.getItem(locations.get(0), "location", "curated");
            String image = loc.getImageList().get(0);
            m_db.getImage(this, img, image);


            TextView txt = thumbnail.findViewById(R.id.city_loc_textA);
            txt.setText(blueprints.get(0));

            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(CityDetailActivity.this, LocationDetailActivity.class);
                    i.putExtra("location", locations.get(0));
                    startActivity(i);

                }
            });

            thumbnail = l_container.findViewById(R.id.l_thumbnailB);
            img = thumbnail.findViewById(R.id.city_loc_imageB);

            loc = (CuratedDO) m_db.getItem(locations.get(1), "location", "curated");
            image = loc.getImageList().get(1);
            m_db.getImage(this, img, image);

            txt = thumbnail.findViewById(R.id.city_loc_textB);
            txt.setText(blueprints.get(1));


            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(CityDetailActivity.this, LocationDetailActivity.class);
                    i.putExtra("location", locations.get(1));
                    startActivity(i);

                }
            });

            thumbnail = l_container.findViewById(R.id.l_thumbnailC);
            img = thumbnail.findViewById(R.id.neighborhood_loc_imageC);

            loc = (CuratedDO)m_db.getItem(locations.get(2), "location", "curated");
            image = loc.getImageList().get(2);
            m_db.getImage(this, img, image);

            txt = thumbnail.findViewById(R.id.neighborhood_loc_textC);
            txt.setText(blueprints.get(2));


            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(CityDetailActivity.this, LocationDetailActivity.class);
                    i.putExtra("location", locations.get(2));
                    startActivity(i);

                }
            });

            thumbnail = l_container.findViewById(R.id.l_thumbnailD);
            img = thumbnail.findViewById(R.id.city_loc_imageD);

            loc = (CuratedDO)m_db.getItem(locations.get(3), "location", "curated");
            image = loc.getImageList().get(3);
            m_db.getImage(this, img, image);

            txt = thumbnail.findViewById(R.id.city_loc_textD);
            txt.setText(blueprints.get(3));

            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(CityDetailActivity.this, LocationDetailActivity.class);
                    i.putExtra("location", locations.get(3));
                    startActivity(i);

                }
            });


        }
        catch(IndexOutOfBoundsException e){}
        catch(NullPointerException e) {}
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


    }

}

