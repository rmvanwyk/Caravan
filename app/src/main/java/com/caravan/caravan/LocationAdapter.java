package com.caravan.caravan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rmvanwyk on 4/2/18.
 */

public class LocationAdapter extends ArrayAdapter<SearchTest.Location> {
    public LocationAdapter(Context context, ArrayList<SearchTest.Location> places) {
        super(context, 0, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SearchTest.Location place = (SearchTest.Location) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView city = (TextView) convertView.findViewById(R.id.city);
        TextView state = (TextView) convertView.findViewById(R.id.state);
        TextView type = (TextView) convertView.findViewById(R.id.type);
        // Populate the data into the template view using the data object
        name.setText(place.name);
        city.setText(place.city);
        state.setText(place.state);
        type.setText(place.type);
        // Return the completed view to render on screen
        return convertView;
    }
}

