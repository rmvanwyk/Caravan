package com.caravan.caravan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.DatabaseAccess;
import com.caravan.caravan.DynamoDB.UserDO;

import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {
    private LayoutInflater inflater;
    private DatabaseAccess m_db;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        m_db = DatabaseAccess.getInstance(getActivity());


        super.onCreate(savedInstanceState);

        // this.loadContent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //Set Up Blueprints
        LinearLayout block = (LinearLayout) rootView.findViewById(R.id.home_curated_blockA).findViewById(R.id.home_curated_container);

        try {
            CuratedDO bp = (CuratedDO)m_db.getItem("The Architect", "blueprint", "curated");
            if(bp!= null){
                TextView title = (TextView) block.findViewById(R.id.home_curated_thumbnailA).findViewById(R.id.home_curated_textA);
                title.setText(bp.getName());
                ImageView img = block.findViewById(R.id.home_curated_thumbnailA).findViewById(R.id.home_curated_imageA);

                CuratedDO firstLoc = (CuratedDO) m_db.getItem(bp.getLocationList().get(0), "location", "curated");
                String image = firstLoc.getImageList().get(0);
                m_db.getImage(getActivity(), img, image);


                img.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(getActivity(), BlueprintDetailActivity.class);
                        i.putExtra("blueprint", "The Architect");
                        startActivity(i);

                    }
                });
            }
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            CuratedDO bp = (CuratedDO)m_db.getItem("The Muralist", "blueprint", "curated");
            if(bp!= null){
                TextView title = (TextView) block.findViewById(R.id.home_curated_thumbnailB).findViewById(R.id.home_curated_textB);
                title.setText(bp.getName());
                ImageView img = block.findViewById(R.id.home_curated_thumbnailB).findViewById(R.id.home_curated_imageB);

                CuratedDO firstLoc = (CuratedDO) m_db.getItem(bp.getLocationList().get(0), "location", "curated");
                String image = firstLoc.getImageList().get(0);
                m_db.getImage(getActivity(), img, image);




                img.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(getActivity(), BlueprintDetailActivity.class);
                        i.putExtra("blueprint", "The Muralist");
                        startActivity(i);

                    }
                });

            }
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            CuratedDO bp = (CuratedDO)m_db.getItem("The Romantic", "blueprint", "curated");
            if(bp!= null){
                TextView title = (TextView) block.findViewById(R.id.home_curated_thumbnailC).findViewById(R.id.home_curated_textC);
                title.setText(bp.getName());
                ImageView img = block.findViewById(R.id.home_curated_thumbnailC).findViewById(R.id.home_curated_imageC);

                CuratedDO firstLoc = (CuratedDO) m_db.getItem(bp.getLocationList().get(0), "location", "curated");
                String image = firstLoc.getImageList().get(0);
                m_db.getImage(getActivity(), img, image);

                img.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(getActivity(), BlueprintDetailActivity.class);
                        i.putExtra("blueprint", "The Romantic");
                        startActivity(i);

                    }
                });

            }
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }



        //Set Up Locations
        block = (LinearLayout) rootView.findViewById(R.id.home_loc_blockA).findViewById(R.id.home_loc_container);

        try {
            CuratedDO loc = (CuratedDO)m_db.getItem("Her Bookshop", "location", "curated");
            if(loc!= null){
                TextView title = (TextView) block.findViewById(R.id.home_loc_thumbnailA).findViewById(R.id.home_loc_textA);
                title.setText(loc.getName());
                ImageView img = block.findViewById(R.id.home_loc_thumbnailA).findViewById(R.id.home_loc_imageA);
                String image = loc.getImageList().get(0);
                m_db.getImage(getActivity(), img, image);

                img.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(getActivity(), LocationDetailActivity.class);
                        i.putExtra("location", "Her Bookshop");
                        startActivity(i);

                    }
                });

            }
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            CuratedDO loc = (CuratedDO)m_db.getItem("404 Hotel", "location", "curated");
            if(loc!= null){
                TextView title = (TextView) block.findViewById(R.id.home_loc_thumbnailB).findViewById(R.id.home_loc_textB);
                title.setText(loc.getName());
                ImageView img = block.findViewById(R.id.home_loc_thumbnailB).findViewById(R.id.home_loc_imageB);
                String image = loc.getImageList().get(0);
                m_db.getImage(getActivity(), img, image);

                img.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(getActivity(), LocationDetailActivity.class);
                        i.putExtra("location", "404 Hotel");
                        startActivity(i);

                    }
                });

            }
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            CuratedDO loc = (CuratedDO)m_db.getItem("3 Crow", "location", "curated");
            if(loc!= null){
                TextView title = (TextView) block.findViewById(R.id.home_loc_thumbnailC).findViewById(R.id.home_loc_textC);
                title.setText(loc.getName());
                ImageView img = block.findViewById(R.id.home_loc_thumbnailC).findViewById(R.id.home_loc_imageC);
                String image = loc.getImageList().get(0);
                m_db.getImage(getActivity(), img, image);

                img.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(getActivity(), LocationDetailActivity.class);
                        i.putExtra("location", "3 Crow");
                        startActivity(i);

                    }
                });

            }
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            CuratedDO loc= (CuratedDO)m_db.getItem("Attaboy", "location", "curated");
            if(loc!= null){
                TextView title = (TextView) block.findViewById(R.id.home_loc_thumbnailD).findViewById(R.id.home_loc_textD);
                title.setText(loc.getName());
                ImageView img = block.findViewById(R.id.home_loc_thumbnailD).findViewById(R.id.home_loc_imageD);
                String image = loc.getImageList().get(0);
                m_db.getImage(getActivity(), img, image);

                img.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(getActivity(), LocationDetailActivity.class);
                        i.putExtra("location", "Attaboy");
                        startActivity(i);

                    }
                });

            }
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }



        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}