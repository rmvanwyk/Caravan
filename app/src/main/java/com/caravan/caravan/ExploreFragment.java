package com.caravan.caravan;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
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
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.caravan.caravan.DynamoDB.Table;
import com.caravan.caravan.RecentHistoryDB.RecentHistoryDAO;
import com.caravan.caravan.RecentHistoryDB.RecentHistoryDatabase;
import com.caravan.caravan.RecentHistoryDB.RecentHistoryItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ExploreFragment extends ListFragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private Activity parentActivity;
    private BottomNavigationView bottomNavigationView;
    private ArrayList<Object> resultsList = new ArrayList<>();
    //private ArrayAdapter<LocationsDO> adapter;

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

    private ArrayList<Object> initCity(String cityData) {
        ArrayList<Object> cityList = new ArrayList<>();
        Log.d("cityData", cityData);
        cityList.add("Cities");
        try {
            JSONObject jsonResponse = new JSONObject(cityData);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("cities");
            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String name = jsonChildNode.optString("_cityName");
                String id = jsonChildNode.optString("_cityId");
                JSONArray blueprintJSON = jsonChildNode.optJSONArray("_blueprintList");
                ArrayList<String> blueprintList = new Gson().fromJson(blueprintJSON.toString(), new TypeToken<List<Object>>(){}.getType());
                Log.d("list", blueprintList.toString());
                Log.d("list size", Integer.toString(blueprintList.size()));
                //cityList.add(new CitiesDO(id, name, blueprintList));
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return cityList;
    }

    private ArrayList<Object> initBlueprint(String blueprintData) {
        ArrayList<Object> blueprintList = new ArrayList<>();
        Log.d("blueprintData", blueprintData);
        blueprintList.add("Blueprints");
        try {
            JSONObject jsonResponse = new JSONObject(blueprintData);
            JSONArray jsonMainNode = jsonResponse.getJSONArray("blueprints");
            Log.d("jsonL", Integer.toString(jsonMainNode.length()));
            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String city = jsonChildNode.optString("_blueprintCity");
                Double followers = jsonChildNode.optDouble("_blueprintFollowerCount");
                String id = jsonChildNode.optString("_blueprintId");
                String name = jsonChildNode.optString("_blueprintName");
                String description = jsonChildNode.optString("_description");
                JSONArray locationJSON = jsonChildNode.optJSONArray("_locationList");
                ArrayList<String> locationsList = new Gson().fromJson(locationJSON.toString(), new TypeToken<List<Object>>(){}.getType());
                //blueprintList.add(new BlueprintsDO(name, city, followers, id, locationsList, description));
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return blueprintList;
    }

    private ArrayList<Object> initLocations(String locationsData) {
        ArrayList<Object> locationList = new ArrayList<>();
        Log.d("locationsData", locationsData);
        locationList.add("Locations");
        try{
            JSONObject jsonResponse = new JSONObject(locationsData);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("location");
            Log.d("jsonL", Integer.toString(jsonMainNode.length()));
            for(int i = 0; i<jsonMainNode.length();i++){
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String city = jsonChildNode.optString("_locationCity");
                String address = jsonChildNode.optString("_address");
                String description = jsonChildNode.optString("_description");
                String email = jsonChildNode.optString("_email");
                String name = jsonChildNode.optString("_locationName");
                String id = jsonChildNode.optString("_locationId");
                String phone_number = jsonChildNode.optString("_phoneNumber");
                String price_point = jsonChildNode.optString("_pricePoint");
                String time = jsonChildNode.optString("_timeOfDay");
                String website = jsonChildNode.optString("_website");
                String recommendation = jsonChildNode.optString("_foodDrinkRecommendation");
                JSONArray hoodJSON = jsonChildNode.optJSONArray("_neighborhoodList");
                ArrayList<String> hoodList = new Gson().fromJson(hoodJSON.toString(), new TypeToken<List<Object>>(){}.getType());
                JSONArray blueprintJSON = jsonChildNode.optJSONArray("_blueprintList");
                ArrayList<String> blueprintList = new Gson().fromJson(blueprintJSON.toString(), new TypeToken<List<Object>>(){}.getType());
                //locationList.add(new LocationsDO(name, city, address, description, email, recommendation,
                                                 //id, phone_number, price_point, time, website));
            }
        }
        catch(JSONException e){
            Toast.makeText(getApplicationContext(), "Error"+e.toString(), Toast.LENGTH_SHORT).show();
        }
        return locationList;
    }

    public ArrayList<Object> doMySearch(String query){
        AWSMobileClient.getInstance().initialize(getActivity()).execute();
        Log.d("DMS query", query);
        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        DynamoDBMapper dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(
                        AWSMobileClient.getInstance().getConfiguration())
                .build();
        //LocationsDO loc = new LocationsDO();
        //CitiesDO cit = new CitiesDO();
        //BlueprintsDO blu = new BlueprintsDO();


        //cit.queryCities(dynamoDBMapper, query);
        //blu.queryBlueprints(dynamoDBMapper, query);
        //loc.queryLocations(dynamoDBMapper, query);
        String location_results = "{\"location\":[{\"_address\":\"2813 West End Ave, Nashville, TN 37203\"" +
                                                    ",\"_blueprintList\":[\"The Penny Pincher\"]" +
                                                    ",\"_description\":\"Three Brother\u0027s is a laid-back artful cafe, serving Dozen Bakery pastries and Bongo Java\u0027s coffee. \"" +
                                                    ",\"_email\":\"tjwilt@gmail.com (TJ)\"" +
                                                    ",\"_foodDrinkRecommendation\":\"They typically feature a seasonal drink that\u0027s always worth a shot!\"" +
                                                    ",\"_locationCity\":\"Nashville\"" +
                                                    ",\"_locationId\":\"ed481577-01aa-4f43-8d53-15714df5bedb\"" +
                                                    ",\"_locationName\":\"Three Brother\u0027s Coffee\"" +
                                                    ",\"_neighborhoodList\":[\"East Hood\"]" +
                                                    ",\"_phoneNumber\":\"615- 835-2166\"" +
                                                    ",\"_pricePoint\":\"$\"" +
                                                    ",\"_timeOfDay\":\"breakfast, early evening\"" +
                                                    ",\"_website\":\"https://threebroscoffee.com/menu/\"}]}";
        String city_results = "{\"cities\":[{\"_cityName\":\"Nashville\"," +
                "\"_blueprintList\":[\"The Blogger\"," +
                "\"The Penny Pincher\"," +
                "\"The Health Nut\"," +
                "\"The Socialite\"," +
                "\"The Bachelorette\"," +
                "\"The Soul Searcher\"," +
                "\"The Eclectic\"]}]}";
        String blueprint_results = "{\"blueprints\":[{\"_blueprintCity\": \"Nashville\"," +
                "  \"_blueprintFollowerCount\": 0," +
                "  \"_blueprintName\": \"The Architect\"," +
                "  \"_description\": \"For those with an eye for design. A guide built for the visually inclined: award-winning restaurant interiors, iconic landmarks, and hotels with history.\",\n" +
                "  \"_locationList\": [" +
                "    \"Butchertown Hall\"," +
                "    \"Old Glory\"," +
                "    \"Frothy Monkey The Nations\"," +
                "    \"5th and Taylor\"," +
                "    \"Nisolo\"," +
                "    \"Noelle\"," +
                "    \"Belle Meade Plantation\"" +
                "  ]}]}";
        //HANDLE NULL result strings here ***
        ArrayList<Object> locationList = new ArrayList<>();
        ArrayList<Object> cityList = new ArrayList<>();
        ArrayList<Object> blueprintList = new ArrayList<>();
        boolean hasLocation = false;
        boolean hasCity = false;
        boolean hasBlueprint = false;
        if (city_results != null && !city_results.isEmpty()) {
            cityList = initCity(city_results);
            hasCity = true;
        }
        if (location_results != null && !location_results.isEmpty()) {
            locationList = initLocations(location_results);
            hasLocation = true;
        }
        if (blueprint_results != null && !blueprint_results.isEmpty()) {
            blueprintList = initBlueprint(blueprint_results);
            hasBlueprint = true;
        }
        ArrayList<Object> resultsList = new ArrayList<>();
        if (hasLocation) {
            for (int i = 0; i < locationList.size(); i++) {
                resultsList.add(locationList.get(i));
            }
        }
        if (hasBlueprint) {
            for (int i = 0; i < blueprintList.size(); i++) {
                resultsList.add(blueprintList.get(i));
            }
        }
        if (hasCity) {
            for (int i = 0; i < cityList.size(); i++) {
                resultsList.add(cityList.get(i));
            }
        }
        if (!hasBlueprint && !hasCity && !hasLocation)
            resultsList.add("Your search returned no results!");
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
    public boolean onQueryTextChange(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        if(newText == null || newText.length() == 0) {
            loadRecentSearchHistory();
        }
        else {
            //Should be replaced by call to DynamoDB to get list based on search query; hardcoded for now
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
        //update recent browsing history
        //load activity corresponding to item clicked
    }

    private void loadRecentSearchHistory() {
        //Should be replaced by recent search history cache pulled from database; hardcoded for now
        ArrayList<Object> recentSearches = new ArrayList<>();
        //recentSearches.add(new LocationsDO("Nashville", "name1"));
        //recentSearches.add(new LocationsDO("Denver", "name2"));
        //recentSearches.add(new LocationsDO("Portland","name3"));
        //recentSearches.add(new LocationsDO("San Diego","name4"));
        //recentSearches.add(new LocationsDO("Chicago","name5"));
        setListAdapter(new SearchResultsAdapter(getActivity(), recentSearches));
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
