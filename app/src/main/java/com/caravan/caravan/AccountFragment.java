package com.caravan.caravan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.caravan.caravan.DynamoCacheDB.DynamoCacheDAO;
import com.caravan.caravan.DynamoCacheDB.DynamoCacheDatabase;
import com.caravan.caravan.DynamoCacheDB.Entity.BlueprintLocation;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprint;
import com.caravan.caravan.DynamoCacheDB.Entity.Location;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprint;
import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.UserDO;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class AccountFragment extends ListFragment {

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Activity parentActivity = getActivity();
        BottomNavigationView bottomNavigationView = parentActivity.findViewById(R.id.bottom_navigation);
        setHasOptionsMenu(true);
        loadBlueprintsAndLocations();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.account_options_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.signOutActionBarItem) {
            final IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
            if (identityManager.isUserSignedIn()) {
                identityManager.signOut();
            }
            if (!(getActivity() instanceof NavBarActivity)) {
                return true;
            }
            NavBarActivity navBarActivity = (NavBarActivity) getActivity();
            navBarActivity.setFragmentToFrame(navBarActivity.getHomeFragment());
            BottomNavigationView bottomNavigationView = navBarActivity.findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.navbar_home);
            return false;
        }
        return true;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Object clicked = getListView().getItemAtPosition(position);
        if (clicked instanceof CuratedDO) {
            CuratedDO recent = (CuratedDO) clicked;
            Intent i;
            switch (recent.getType()) {
                case "blueprint":
                    i = new Intent(getActivity(), BlueprintDetailActivity.class);
                    i.putExtra("blueprint", (String) recent.getName());
                    startActivity(i);
                    break;
                case "location":
                    i = new Intent(getActivity(), LocationDetailActivity.class);
                    i.putExtra("location", (String) recent.getName());
                    startActivity(i);
                    break;
            }
        }
        else {
            UserDO recent = (UserDO) clicked;
            Intent i = new Intent(getActivity(), BlueprintDetailActivity.class);
            i.putExtra("blueprint", (String) recent.getName());
            startActivity(i);
        }
    }

    private List<CuratedDO> loadLocationsForBlueprintDisplay(String blueprintID, int option) {
        DynamoCacheDatabase database = DynamoCacheDatabase.getInMemoryInstance(getActivity());
        final DynamoCacheDAO dao = database.dynamoCacheDAO();
        if (option == 0) {
            List<BlueprintLocation> dbLocations = dao.getLocationsFromUserBlueprintId(blueprintID);
            List<CuratedDO> locationsOfBlueprint = new ArrayList<>();
            for (int i = 0; i < dbLocations.size(); i++) {
                locationsOfBlueprint.add(dbLocations.get(i).getCuratedDO());
            }
            return locationsOfBlueprint;
        }
        else {
            List<BlueprintLocation> dbLocations = dao.getLocationsFromCuratedBlueprintId(blueprintID);
            List<CuratedDO> locationsOfBlueprint = new ArrayList<>();
            for (int i = 0; i < dbLocations.size(); i++) {
                locationsOfBlueprint.add(dbLocations.get(i).getCuratedDO());
            }
            return locationsOfBlueprint;
        }
    }

    private void loadBlueprintsAndLocations() {
        DynamoCacheDatabase database = DynamoCacheDatabase.getInMemoryInstance(getActivity());
        final DynamoCacheDAO dao = database.dynamoCacheDAO();
        List<UserBlueprint> userBlueprints = dao.getAllUserBlueprints();
        List<CuratedBlueprint> curatedBlueprints = dao.getAllCuratedBlueprints();
        List<Location> locations = dao.getAllLocations();
        ArrayList<Object> resultList = new ArrayList<>();
        if (!userBlueprints.isEmpty()) {
            resultList.add("Your Created Blueprints");
            for (int i = 0; i < userBlueprints.size(); i++) {
                List<BlueprintLocation> locs = dao.getLocationsFromUserBlueprintId(userBlueprints.get(i).getId());
                UserDO temp = userBlueprints.get(i).getUserDO();
                List<String> tempLocList = new ArrayList<>();
                for (int j = 0; j < locs.size(); j++) {
                    tempLocList.add(temp.getName());
                }
                temp.setLocationList(tempLocList);
                resultList.add(userBlueprints.get(i).getUserDO());
            }
        }
        if (!curatedBlueprints.isEmpty()) {
            resultList.add("Your Saved Blueprints");
            for (int i = 0; i < curatedBlueprints.size(); i++) {
                List<BlueprintLocation> locs = dao.getLocationsFromCuratedBlueprintId(curatedBlueprints.get(i).getId());
                CuratedDO temp = curatedBlueprints.get(i).getCuratedDO();
                List<String> tempLocList = new ArrayList<>();
                for (int j = 0; j < locs.size(); j++) {
                    tempLocList.add(temp.getName());
                }
                temp.setLocationList(tempLocList);
                resultList.add(curatedBlueprints.get(i).getCuratedDO());
            }
        }
        if (!locations.isEmpty()) {
            resultList.add("Your Saved Locations");
            for (int i = 0; i < locations.size(); i++) {
                resultList.add(locations.get(i).getCuratedDO());
            }
        }
        for (int i = 0; i < resultList.size(); i++) {
            Object item = resultList.get(i);
            if (item instanceof UserDO) {
                UserDO newItem = (UserDO) item;
                Log.d("RL-N:", newItem.getName());
                Log.d("RL-T", newItem.getType());
            }

        }

        setListAdapter(new AccountAdapter(getActivity(), resultList));
    }
}
