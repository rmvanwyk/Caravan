package com.caravan.caravan;

import android.app.Activity;
import android.os.AsyncTask;
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
import android.widget.ListView;
import com.caravan.caravan.DynamoDB.Table;
import com.caravan.caravan.RecentHistoryDB.RecentHistoryDAO;
import com.caravan.caravan.RecentHistoryDB.RecentHistoryDatabase;
import com.caravan.caravan.RecentHistoryDB.RecentHistoryItem;
import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.DatabaseAccess;
import com.caravan.caravan.DynamoDB.UserDO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ExploreFragment extends ListFragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private Activity parentActivity;
    private BottomNavigationView bottomNavigationView;
    final private int cacheSize = 5;
    public ExploreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = getActivity();
        bottomNavigationView = parentActivity.findViewById(R.id.bottom_navigation);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceBundle) {
        super.onActivityCreated(savedInstanceBundle);

        getListView().setOnScrollListener(new OnScrollStateChangedImpl());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.explore_options_menu, menu);
        MenuItem item = menu.findItem(R.id.searchActionBarItem);
        item.setOnActionExpandListener(this);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public ArrayList<Object> doMySearch(String query){
        ArrayList<CuratedDO> locationList = new ArrayList<>();
        ArrayList<CuratedDO> cityList = new ArrayList<>();
        ArrayList<CuratedDO> blueprintList = new ArrayList<>();
        ArrayList<CuratedDO> neighborhoodList = new ArrayList<>();
        ArrayList<UserDO> userBList = new ArrayList<>();
        ArrayList<UserDO> userLList = new ArrayList<>();
        List<Object> curatedResults = new ArrayList<>();
        DatabaseAccess task = DatabaseAccess.getInstance(getActivity());
        Future<List<Object>> future = task.Query(query);
        try {
            curatedResults = future.get();
        }
        catch (ExecutionException e) {

        }
        catch (InterruptedException e) {
            future.cancel(true);
        }
        Log.d("EF results:", Integer.toString(curatedResults.size()));
        boolean hasLocation = false, hasCity = false, hasUserBlueprint = false, hasNeighborhood = false,
        hasCuratedBlueprint = false, hasUserLocation = false;
        for (int i = 0; i < curatedResults.size(); i++) {
            if (curatedResults.get(i) instanceof CuratedDO) {
                CuratedDO result = (CuratedDO) curatedResults.get(i);
                if (result.getType().equals("blueprint")) {
                    blueprintList.add(result);
                    hasCuratedBlueprint = true;
                } else if (result.getType().equals("location")) {
                    locationList.add(result);
                    hasLocation = true;
                } else if (result.getType().equals("city")) {
                    cityList.add(result);
                    hasCity = true;
                } else if (result.getType().equals("neighborhood")){
                    neighborhoodList.add(result);
                    hasNeighborhood = true;
                }
            } else {
                UserDO result = (UserDO) curatedResults.get(i);
                if (result.getType().equals("blueprint")) {
                    userBList.add(result);
                    hasUserBlueprint = true;
                }
            }
            if (i > 12) {
                break;
            }
        }
        ArrayList<Object> resultsList = new ArrayList<>();
        if (hasLocation) {
            resultsList.add("Caravan Locations");
            resultsList.addAll(locationList);
        }
        if (hasUserLocation) {
            resultsList.add("User Locations");
            resultsList.addAll(userLList);
        }
        if (hasCuratedBlueprint) {
            resultsList.add("Caravan Blueprints");
            resultsList.addAll(blueprintList);
            }
        if (hasUserBlueprint) {
            resultsList.add("User Blueprints");
            resultsList.addAll(userBList);
        }
        if (hasCity) {
            resultsList.add("Cities");
            resultsList.addAll(cityList);
            }
        if (hasNeighborhood) {
            resultsList.add("Neighborhoods");
            resultsList.addAll(neighborhoodList);
        }
        if (resultsList.isEmpty()){
            resultsList.add("Your search returned no results!");
        }
            return resultsList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query == null || query.length() == 0) {
            Log.d("Zero Text", "triggered");
            loadRecentSearchHistory();
        }
        else if (query.length() > 0){
            setListAdapter(new SearchResultsAdapter(getActivity(), doMySearch(query)));
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText == null || newText.length() == 0) {
            Log.d("Zero Text", "triggered");
            loadRecentSearchHistory();
        }
        else if (newText.length() > 2){
            setListAdapter(new SearchResultsAdapter(getActivity(), doMySearch(newText)));
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
        Object clicked = getListView().getItemAtPosition(position);
        new CacheHistory().execute(clicked);
        if (clicked instanceof CuratedDO) {
            CuratedDO recent = (CuratedDO) clicked;
            switch (recent.getType()) {
                case "blueprint":
                    //startActivity(new Intent(this, BlueprintDetailActivity(recent));
                case "location":
                    //startActivity(new Intent(this, LocationDetailActivity(recent));
                case "city":
                    //startActivity(new Intent(this, DisplayCityDetailActivity(recent));
                case "neighborhood":
                    //startActivity(new Intent(this, NeighborhoodDetailActivity(recent));
            }
        }
        else {
            UserDO recent = (UserDO) clicked;
            if (recent.getType().equals("location")) {
                //startActivity(new Intent(this, LocationDetailActivity(recent));
            } else {
                //startActivity(new Intent(this, BlueprintDetailActivity(recent));
            }
        }
    }

    private class CacheHistory extends AsyncTask<Object, Void, Void>{
        RecentHistoryDatabase database = RecentHistoryDatabase.getInMemoryInstance(getActivity());
        final RecentHistoryDAO dao = database.recentHistoryDAO();
        @Override
        protected Void doInBackground(Object...clicked) {
            List<RecentHistoryItem> existing = dao.loadRecentHistory();
            if (clicked[0] instanceof CuratedDO) {
                CuratedDO recent = (CuratedDO) clicked[0];
                for (RecentHistoryItem item : existing){
                    if (item.getId().equals(recent.getName())) {
                        Log.d("cached", "REPEATED");
                        return null;
                    }
                }
                switch (recent.getType()) {
                    case "blueprint": {
                        if (dao.getTableSize() < cacheSize){
                            dao.insertItem(new RecentHistoryItem(recent.getName(), new Date(), Table.blueprint));
                        }
                        else {
                            dao.updateRecentHistory(recent.getName(), new Date(), Table.blueprint);
                        }
                        break;
                    }
                    case "location": {
                        if (dao.getTableSize() < cacheSize) {
                            dao.insertItem(new RecentHistoryItem(recent.getName(), new Date(), Table.location));
                        } else {
                            dao.updateRecentHistory(recent.getName(), new Date(), Table.location);
                        }
                        break;
                    }
                    case "city": {
                        if (dao.getTableSize() < cacheSize) {
                            dao.insertItem(new RecentHistoryItem(recent.getName(), new Date(), Table.city));
                        } else {
                            dao.updateRecentHistory(recent.getName(), new Date(), Table.city);
                        }
                        break;
                    }
                    case "neighborhood": {
                        if (dao.getTableSize() < cacheSize) {
                            dao.insertItem(new RecentHistoryItem(recent.getName(), new Date(), Table.neighborhood));
                        } else {
                            dao.updateRecentHistory(recent.getName(), new Date(), Table.neighborhood);
                        }
                        break;
                    }
                }
                Log.d("cached", recent.getName());
            }
            else {
                UserDO recent = (UserDO) clicked[0];
                for (RecentHistoryItem item : existing){
                    if (item.getId().equals(recent.getName())) {
                        return null;
                    }
                }
                if (recent.getType().equals("blueprint")) {
                    RecentHistoryItem item = new RecentHistoryItem(recent.getUserId(), new Date(), Table.userblueprint);
                    if (dao.getTableSize() < cacheSize) {
                        dao.insertItem(item);
                    } else {
                        dao.updateRecentHistory(recent.getUserId(), new Date(), Table.userblueprint);
                    }
                }
                else {
                    if (dao.getTableSize() < cacheSize) {
                        dao.insertItem(new RecentHistoryItem(recent.getId(), new Date(), Table.location));
                    } else {
                        dao.updateRecentHistory(recent.getName(), new Date(), Table.location);
                    }
                }
            }

            return null;
        }
    }

    private void loadRecentSearchHistory() {
        Log.d("LRSH", "Top");
        RecentHistoryDatabase database = RecentHistoryDatabase.getInMemoryInstance(getActivity());
        final RecentHistoryDAO dao = database.recentHistoryDAO();
        List<RecentHistoryItem> cached = dao.loadRecentHistory();
        if (cached == null) {
            return;
        }
        ArrayList<Object> displayList = new ArrayList<>();
        displayList.add("Recently Viewed:");
        DatabaseAccess task = DatabaseAccess.getInstance(getActivity());
        for (int i = 0; i < cached.size(); i++) {
            Object display = null;
            if (cached.get(i).getTable().equals(Table.blueprint)) {
                Future<CuratedDO> item = task.getCuratedItem("blueprint", cached.get(i).getId());
                try {
                    display = (CuratedDO) item.get();
                } catch (ExecutionException | InterruptedException e) {

                }
            } else if (cached.get(i).getTable().equals(Table.location)) {
                Future<CuratedDO> item = task.getCuratedItem("location", cached.get(i).getId());
                try {
                    display = (CuratedDO) item.get();
                } catch (ExecutionException | InterruptedException e) {

                }
            } else if (cached.get(i).getTable().equals(Table.city)) {
                Future<CuratedDO> item = task.getCuratedItem("city", cached.get(i).getId());
                try {
                    display = (CuratedDO) item.get();
                } catch (ExecutionException | InterruptedException e) {

                }
            } else if (cached.get(i).getTable().equals(Table.neighborhood)) {
                Future<CuratedDO> item = task.getCuratedItem("neighborhood", cached.get(i).getId());
                try {
                    display = (CuratedDO) item.get();
                } catch (ExecutionException | InterruptedException e) {

                }
            } else if (cached.get(i).getTable().equals(Table.userlocation)) {
                Future<UserDO> item = task.getUserItem(cached.get(i).getId());
                try {
                    display = (UserDO) item.get();
                } catch (ExecutionException | InterruptedException e) {

                }
            } else {
                Future<UserDO> item = task.getUserItem(cached.get(i).getId());
                try {
                    display = (UserDO) item.get();
                } catch (ExecutionException | InterruptedException e) {

                }
            }
            if (display != null) {
                displayList.add(display);
                Log.d("DISPLAY CASH:", "item added");
            }
        }
        if (displayList.size() > 1) {
            setListAdapter(new SearchResultsAdapter(getActivity(), displayList));
        }
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
