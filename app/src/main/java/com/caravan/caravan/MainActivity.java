package com.caravan.caravan;

/**
 * Created by meaghan on 3/22/18.
 */
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public int currentViewID;

    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        bottomNavigationView.postDelayed(() -> {
            switch (item.getItemId()) {
                case R.id.action_explore:
                    //startActivity(new Intent(this, ExploreActivity.class));
                    currentViewID = R.id.activity_explore;
                    break;
                case R.id.action_search:
                    startActivity(new Intent(this, SearchActivity.class));
                    currentViewID = R.id.activity_search;
                    break;
                case R.id.action_my_blueprints:
                    //startActivity(new Intent(this, BlueprintActivity.class));
                    currentViewID = R.id.activity_blueprint;
                    break;
            }
            finish();
        }, 300);
        return true;
    }

    private void updateNavigationBarState(){
        selectBottomNavigationBarItem(currentViewID);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

}