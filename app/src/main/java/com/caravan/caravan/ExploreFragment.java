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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExploreFragment extends ListFragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private Activity parentActivity;
    private BottomNavigationView bottomNavigationView;
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
        loadRecentSearchHistory();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceBundle) {
        super.onActivityCreated(savedInstanceBundle);

        getListView().setOnScrollListener(new OnScrollStateChangedImpl());
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
                } else {
                    neighborhoodList.add(result);
                    hasNeighborhood = true;
                }
            } else {
                UserDO result = (UserDO) curatedResults.get(i);
                if (result.getType().equals("blueprint")) {
                    userBList.add(result);
                    hasUserBlueprint = true;
                } else if (result.getType().equals("location")) {
                    userLList.add(result);
                    hasUserLocation = true;
                }
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
            resultsList.add(neighborhoodList);
        }
        if (resultsList.isEmpty()){
            resultsList.add("Your search returned no results!");
        }
            return resultsList;
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText == null || newText.length() == 0) {
            Log.d("Zero Text", "triggered");
            loadRecentSearchHistory();
        }
        else {
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
                        if (dao.getTableSize() < 5){
                            dao.insertItem(new RecentHistoryItem(recent.getName(), new Date(), Table.blueprint));
                        }
                        else {
                            dao.updateRecentHistory(recent.getName(), new Date(), Table.blueprint);
                        }
                        break;
                    }
                    case "location": {
                        if (dao.getTableSize() < 5) {
                            dao.insertItem(new RecentHistoryItem(recent.getName(), new Date(), Table.location));
                        } else {
                            dao.updateRecentHistory(recent.getName(), new Date(), Table.location);
                        }
                        break;
                    }
                    case "city": {
                        if (dao.getTableSize() < 5) {
                            dao.insertItem(new RecentHistoryItem(recent.getName(), new Date(), Table.city));
                        } else {
                            dao.updateRecentHistory(recent.getName(), new Date(), Table.city);
                        }
                        break;
                    }
                    case "neighborhood": {
                        if (dao.getTableSize() < 5) {
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
                    if (dao.getTableSize() < 5) {
                        dao.insertItem(item);
                    } else {
                        dao.updateRecentHistory(recent.getUserId(), new Date(), Table.userblueprint);
                    }
                }
                else {
                    if (dao.getTableSize() < 5) {
                        dao.insertItem(new RecentHistoryItem(recent.getId(), new Date(), Table.location));
                    } else {
                        dao.updateRecentHistory(recent.getName(), new Date(), Table.location);
                    }
                }
            }

            return null;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
         Object clicked = getListView().getItemAtPosition(position);
         new CacheHistory().execute(clicked);
         if (clicked instanceof CuratedDO) {
             CuratedDO recent = (CuratedDO) clicked;
             switch (recent.getType()) {
                 case "blueprint":
                     //startActivity(DisplayBlueprint(recent);
                 case "location":
                     //startActivity(DisplayLocation(recent);
                 case "city":
                     //startActivity(DisplayCity(recent);
                 case "neighborhood":
                     //startActivity(DisplayNeighborhood(recent);
                 }
             }
         else {
             UserDO recent = (UserDO) clicked;
             if (recent.getType().equals("location")) {
                 //startActivity(DisplayLocation(recent);
             } else {
                 //startActivity(DisplayBlueprint(recent);
             }
         }
    }

    private class LoadHistory extends AsyncTask<Void, Void,List<RecentHistoryItem>> {
        RecentHistoryDatabase database = RecentHistoryDatabase.getInMemoryInstance(getActivity());
        final RecentHistoryDAO dao = database.recentHistoryDAO();
        private List<RecentHistoryItem> cached;

        @Override
        protected List<RecentHistoryItem> doInBackground(Void... params) {
            return dao.loadRecentHistory();
        }

        @Override
        protected void onPostExecute(List<RecentHistoryItem> results) {
            this.cached = results;
        }

        private ExecutorService executor = Executors.newSingleThreadExecutor();
        public Future<List<RecentHistoryItem>> retrieveHistory() {
            return executor.submit(() -> {
                this.execute();
                return this.cached;
            });
        }
    }


    private void loadRecentSearchHistory() {
        Log.d("LRSH", "Top");
        Future<List<RecentHistoryItem>> cached = new LoadHistory().retrieveHistory();
        List<RecentHistoryItem> cachedResults = new ArrayList<>();
        try {
            cachedResults = cached.get();

        }
        catch (ExecutionException e) {

        }
        catch (InterruptedException e) {
            cached.cancel(true);
        }
        if (cachedResults == null) {
            Log.d("NO CACHED", "RESULTS BITCH");
            return;
        }
        else {
            Log.d("ITS LIT:", Integer.toString(cachedResults.size()));
        }
        ArrayList<Object> displayList = new ArrayList<>();
        DatabaseAccess task = DatabaseAccess.getInstance(getActivity());

        for (int i = 0; i < cachedResults.size(); i ++) {
            Future<List<Object>> future = task.Query(cachedResults.get(i).getId());
            try {
                if (future.get() instanceof CuratedDO) {
                    CuratedDO display = (CuratedDO) future.get();
                    displayList.add(display);
                    Log.d("DISPLAY CASH:", display.getName());
                }
                else {
                    UserDO display = (UserDO) future.get();
                    displayList.add(display);
                    Log.d("DISPLAY CASH:", display.getName());
                }
            } catch (ExecutionException e) {

            } catch (InterruptedException e) {
                future.cancel(true);
            }
        }
        setListAdapter(new SearchResultsAdapter(getActivity(), displayList));
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
