package com.caravan.caravan;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.amazonaws.mobile.auth.core.IdentityManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavBarActivity extends AppCompatActivity {
    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.main_frame) FrameLayout frameLayout;
    @Inject AccountFragment accountFragment;
    @Inject ExploreFragment exploreFragment;
    @Inject HomeFragment homeFragment;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);
        ButterKnife.bind(this);

        //TO-DO: Fix injection with dagger 2.0
        exploreFragment = new ExploreFragment();
        homeFragment = new HomeFragment();
        accountFragment = new AccountFragment();
        setFragmentToFrame(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener((@NonNull MenuItem menuItem) ->
        {
            switch (menuItem.getItemId()) {
                case R.id.navbar_home:
                    setFragmentToFrame(homeFragment);
                    return true;
                case R.id.navbar_explore:
                    setFragmentToFrame(exploreFragment);
                    //startActivity(new Intent(this, SearchableActivity.class));
                    return true;
                case R.id.navbar_account:
                    if(!isSignedIn()) {
                        startActivity(new Intent(this, AuthenticatorActivity.class));
                    }
                    else {
                        setFragmentToFrame(accountFragment);
                    }
                    return true;
                default:
                    return false;
            }
        });
    }

    void setFragmentToFrame(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    private boolean isSignedIn() {
        IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
        if(identityManager == null)
            return false;
        return identityManager.isUserSignedIn();
    }
}