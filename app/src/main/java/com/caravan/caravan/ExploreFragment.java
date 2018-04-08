package com.caravan.caravan;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class ExploreFragment extends ListFragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private Activity parentActivity;
    private BottomNavigationView bottomNavigationView;

    private ArrayAdapter<SearchTest.Location> adapter;

    public ExploreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d("asdf", getListView().toString());
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = getActivity();
        bottomNavigationView = parentActivity.findViewById(R.id.bottom_navigation);
        setHasOptionsMenu(true);
        loadRecentSearchHistory();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceBundle) {
        super.onActivityCreated(savedInstanceBundle);
        getListView().setOnScrollListener(new OnScrollStateChangedImpl());
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
        MenuItem item = menu.findItem(R.id.searchActionBarItem);
        item.setOnActionExpandListener(this);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText == null || newText.length() == 0) {
            loadRecentSearchHistory();
        }
        else {
            //Should be replaced by call to DynamoDB to get list based on search query; hardcoded for now
            setListAdapter(new LocationAdapter(getActivity(), doMySearch(newText)));
        }
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        bottomNavigationView.setVisibility(View.INVISIBLE);
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        bottomNavigationView.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //update recent browsing history
        //load activity corresponding to item clicked
    }

    private void loadRecentSearchHistory() {
        //Should be replaced by recent search history cache pulled from database; hardcoded for now
        ArrayList<SearchTest.Location> recentSearches = new ArrayList<>();
        recentSearches.add(new SearchTest.Location("Nashville", "Tennessee", "name1", "type1"));
        recentSearches.add(new SearchTest.Location("Denver", "Colorado", "name2", "type2"));
        recentSearches.add(new SearchTest.Location("Portland", "Oregon", "name3", "place3"));
        recentSearches.add(new SearchTest.Location("San Diego", "California", "name4", "place4"));
        recentSearches.add(new SearchTest.Location("Chicago", "Illinois", "name5", "place5"));
        recentSearches.add(new SearchTest.Location("Boston", "Massachusetts", "name6", "place6"));
        recentSearches.add(new SearchTest.Location("Atlanta", "Georgia", "name7", "place7"));
        adapter = new LocationAdapter(getActivity(), recentSearches);
        setListAdapter(adapter);

    }

    //Created to handle dynamic loading of content when ListView scrolls to bottom
    private class OnScrollStateChangedImpl implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (totalItemCount-firstVisibleItem == visibleItemCount) {
                //Function call to increase length of search suggestions
            }
        }
    }
}
