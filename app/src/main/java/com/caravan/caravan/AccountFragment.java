package com.caravan.caravan;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.caravan.caravan.DynamoCacheDB.DynamoCacheDAO;
import com.caravan.caravan.DynamoCacheDB.DynamoCacheDatabase;

public class AccountFragment extends Fragment {

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //loadBlueprintsAndLocations();
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

    private void loadBlueprintsAndLocations() {
        DynamoCacheDatabase database = DynamoCacheDatabase.getInMemoryInstance(getActivity());
        final DynamoCacheDAO dao = database.dynamoCacheDAO();

    }
}
