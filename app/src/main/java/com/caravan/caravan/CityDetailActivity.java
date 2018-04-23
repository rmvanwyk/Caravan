package com.caravan.caravan;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caravan.caravan.DynamoDB.CuratedDO;

import java.util.concurrent.BlockingDeque;

/**
 * Created by meaghan on 4/22/18.
 */

public class CityDetailActivity extends Activity{
    private LayoutInflater inflater;
    protected void onCreate(Bundle savedInstanceState, CuratedDO bp){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);
    }

    private void loadLayout(){
        //add curated guides
        LinearLayout blockContainer = (LinearLayout)findViewById(R.id.city_block_container);
        CuratedDO guide;
        View thumbnail;
        thumbnail = inflater.inflate(R.layout.item_thumbnail, blockContainer, false);
        TextView description = (TextView) thumbnail.findViewById(R.id.thumbnail_text);
        blockContainer.addView(thumbnail);
        //add neighborhoods

        //add locations
    }
}
