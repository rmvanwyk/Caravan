package com.caravan.caravan;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.UserDO;

import java.util.ArrayList;

/**
 * Created by rmvanwyk on 4/2/18.
 */

public class SearchResultsAdapter extends BaseAdapter {
    private ArrayList<Object> display_items;
    private static final int TYPE_LOCATION = 0;
    private static final int TYPE_CURATEDBLUE = 1;
    private static final int TYPE_CITY = 2;
    private static final int TYPE_HOOD = 3;
    private static final int TYPE_USERBLUE = 4;
    private static final int TYPE_USERLOC = 5;
    private static final int TYPE_DIVIDER = 6;
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
        return 7;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof CuratedDO) {
            CuratedDO item = (CuratedDO) getItem(position);
            if (item.getType().equals("location")) {
                return TYPE_LOCATION;
            }
            else if (item.getType().equals("blueprint")) {
                return TYPE_CURATEDBLUE;
            }
            else if (item.getType().equals("city")) {
                return TYPE_CITY;
            }
            else if (item.getType().equals("neighborhood")) {
                return TYPE_HOOD;
            }
        }
        else if (getItem(position) instanceof UserDO) {
            UserDO item = (UserDO) getItem(position);
            if (item.getType().equals("user")) {
                return TYPE_USERBLUE;
            }
        }
        else if (getItem(position) instanceof String){
             return TYPE_DIVIDER;
        }
        return 0;
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
                case TYPE_HOOD:
                    convertView = inflater.inflate(R.layout.item_city, parent, false);
                    break;
                case TYPE_CURATEDBLUE:
                    convertView = inflater.inflate(R.layout.item_blueprint, parent, false);
                    break;
                case TYPE_USERBLUE:
                    convertView = inflater.inflate(R.layout.item_blueprint, parent, false);
                    break;
            }
        }
        switch (type) {
            case TYPE_LOCATION: {
                CuratedDO location_obj = (CuratedDO) getItem(position);
                // Lookup view for data population
                TextView name = (TextView) convertView.findViewById(R.id.name);
                TextView city = (TextView) convertView.findViewById(R.id.city);
                TextView description = (TextView) convertView.findViewById(R.id.description);
                // Populate the data into the template view using the data object
                name.setText(location_obj.getName());
                city.setText(location_obj.getCity());
                description.setText(location_obj.getDescription());
                break; }
            case TYPE_CITY: {
                CuratedDO city_obj = (CuratedDO) getItem(position);
                TextView city = (TextView) convertView.findViewById(R.id.name);
                city.setText(city_obj.getName());
                break; }
            case TYPE_HOOD: {
                CuratedDO city_obj = (CuratedDO) getItem(position);
                TextView hood = (TextView) convertView.findViewById(R.id.name);
                hood.setText(city_obj.getName());
                break; }
            case TYPE_CURATEDBLUE: {
                CuratedDO blueprint_obj = (CuratedDO) getItem(position);
                TextView name = (TextView) convertView.findViewById(R.id.name);
                TextView city = (TextView) convertView.findViewById(R.id.city);
                TextView description = (TextView) convertView.findViewById(R.id.description);
                name.setText(blueprint_obj.getName());
                city.setText(blueprint_obj.getCity());
                description.setText(blueprint_obj.getDescription());
                break; }
            case TYPE_USERBLUE: {
                CuratedDO blueprint_obj = (CuratedDO) getItem(position);
                TextView name = (TextView) convertView.findViewById(R.id.name);
                TextView city = (TextView) convertView.findViewById(R.id.city);
                TextView description = (TextView) convertView.findViewById(R.id.description);
                name.setText(blueprint_obj.getName());
                city.setText(blueprint_obj.getCity());
                description.setText(blueprint_obj.getDescription());
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

