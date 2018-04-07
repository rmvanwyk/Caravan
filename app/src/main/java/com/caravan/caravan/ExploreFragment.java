package com.caravan.caravan;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Get the intent, verify the action and get the query
        /*Intent intent = getActivity().getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("Intent", query);
            ArrayList<SearchTest.Location> results = doMySearch(query);
            LocationAdapter adapter = new LocationAdapter(getActivity(), results);
            ListView listview = (ListView) getView().findViewById(R.id.list);
            listview.setAdapter(adapter);
        }*/
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);

        /*menu.clear();
        inflater.inflate(R.menu.options_menu, menu);

        //Get the search view and set the searchable config
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchActionBarItem).getActionView();
        // Assumes current activity is the searchable activity
        MenuItem item = menu.add("Search");
        SearchView sv = new SearchView(getActivity().getActionBar().getThemedContext());
        item.setActionView(sv);
        item.setIcon(R.drawable.ic_search_white_24px);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW
                | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true); // Auto-complete
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                          }
                                      }
        );*/
    }
}
