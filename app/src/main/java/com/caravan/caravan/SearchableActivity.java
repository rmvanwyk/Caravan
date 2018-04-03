package com.caravan.caravan;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.amazonaws.mobile.auth.core.IdentityManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rmvanwyk on 3/24/18.
 */

public class SearchableActivity extends AppCompatActivity {
    Button backButton;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("Intent", query);
            ArrayList<SearchTest.Location> results = doMySearch(query);
            LocationAdapter adapter = new LocationAdapter(this, results);
            ListView listview = (ListView) findViewById(R.id.list);
            listview.setAdapter(adapter);
        }
    }

    public ArrayList<SearchTest.Location> doMySearch(String query){
        SearchTest test = new SearchTest();
        ArrayList<SearchTest.Location> results = new ArrayList<>();
        Log.d("do", Integer.toString(test.size));

        int i = 0;
        while (i < test.size) {
            if (query.equals(test.locations.get(i).name)) {
                Log.d("SearchTest","added " + test.locations.get(i).name);
                results.add(test.locations.get(i));
            }
            else if (query.equals(test.locations.get(i).state)) {
                Log.d("SearchTest","added " + test.locations.get(i).name);
                results.add(test.locations.get(i));
            }
            else if (query.equals(test.locations.get(i).city)) {
                Log.d("SearchTest","added " + test.locations.get(i).name);
                results.add(test.locations.get(i));
            }
            else if (query.equals(test.locations.get(i).type)) {
                Log.d("SearchTest","added " + test.locations.get(i).name);
                results.add(test.locations.get(i));
            }
            i++;
        }
        return results;
    }


    @Override
    @TargetApi(11)
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the options menu from XML
        getMenuInflater().inflate(R.menu.options_menu, menu);

        backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener((buttonView) -> {
            startActivity(new Intent(this, NavBarActivity.class));
            });

        //Get the search view and set the searchable config
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchActionBarItem).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true); // Auto-complete
        return true;
    }
}
