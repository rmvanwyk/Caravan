package com.caravan.caravan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rmvanwyk on 4/2/18.
 */

public class SearchResultsAdapter extends BaseAdapter {
    private ArrayList<Object> display_items;
    private static final int TYPE_LOCATION = 0;
    private static final int TYPE_GUIDE = 1;
    private static final int TYPE_CITY = 2;
    private static final int TYPE_DIVIDER = 3;
    private LayoutInflater inflater;

    public SearchResultsAdapter(Context context, ArrayList<Object> search_results) {
        //super(context, 0, search_results);
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.display_items = search_results;
        }

    @Override
    public int getCount() {
        return display_items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return display_items.get(position);
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof LocationsDO) {
            return TYPE_LOCATION;
        } else if (getItem(position) instanceof BlueprintsDO) {
            return TYPE_GUIDE;
        } else if (getItem(position) instanceof CitiesDO) {
            return TYPE_CITY;
        } else {
            return TYPE_DIVIDER;
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) != TYPE_DIVIDER);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        int type = getItemViewType(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            switch (type) {
                case TYPE_DIVIDER:
                    convertView = inflater.inflate(R.layout.row_header, parent, false);
                    break;
                case TYPE_LOCATION:
                    convertView = inflater.inflate(R.layout.item_location, parent, false);
                    break;
                case TYPE_CITY:
                    convertView = inflater.inflate(R.layout.item_city, parent, false);
                    break;
                case TYPE_GUIDE:
                    convertView = inflater.inflate(R.layout.item_blueprint, parent, false);
                    break;
            }
        }
        switch (type) {
            case TYPE_LOCATION: {
                LocationsDO location_obj = (LocationsDO) getItem(position);
                // Lookup view for data population
                TextView name = (TextView) convertView.findViewById(R.id.name);
                TextView city = (TextView) convertView.findViewById(R.id.city);
                TextView description = (TextView) convertView.findViewById(R.id.description);
                // Populate the data into the template view using the data object
                name.setText(location_obj._locationName);
                city.setText(location_obj._locationCity);
                description.setText(location_obj._description);
                break; }
            case TYPE_CITY: {
                CitiesDO city_obj = (CitiesDO) getItem(position);
                TextView city = (TextView) convertView.findViewById(R.id.name);
                city.setText(city_obj._cityName);
                break; }
            case TYPE_GUIDE: {
                BlueprintsDO blueprint_obj = (BlueprintsDO) getItem(position);
                TextView name = (TextView) convertView.findViewById(R.id.name);
                TextView city = (TextView) convertView.findViewById(R.id.city);
                TextView description = (TextView) convertView.findViewById(R.id.description);
                name.setText(blueprint_obj._blueprintName);
                city.setText(blueprint_obj._blueprintCity);
                description.setText(blueprint_obj._description);
                break; }
            case TYPE_DIVIDER: {
                TextView title = (TextView) convertView.findViewById(R.id.headerTitle);
                String titleString = (String) getItem(position);
                title.setText(titleString);
                break; }
        }
        // Return the completed view to render on screen
        return convertView;
    }
}

