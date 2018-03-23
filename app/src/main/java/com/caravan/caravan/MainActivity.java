package com.caravan.caravan;

/**
 * Created by meaghan on 3/22/18.
 */
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textExplore;
    private TextView textSearch;
    private TextView textBluePrints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textExplore = (TextView) findViewById(R.id.text_explore);
        textSearch = (TextView) findViewById(R.id.text_search);
        textBluePrints = (TextView) findViewById(R.id.text_myBlueprints);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_explore:
                                textExplore.setVisibility(View.VISIBLE);
                                textSearch.setVisibility(View.GONE);
                                textBluePrints.setVisibility(View.GONE);
                                break;
                            case R.id.action_search:
                                textExplore.setVisibility(View.GONE);
                                textSearch.setVisibility(View.VISIBLE);
                                textBluePrints.setVisibility(View.GONE);
                                break;
                            case R.id.action_my_blueprints:
                                textExplore.setVisibility(View.GONE);
                                textSearch.setVisibility(View.GONE);
                                textBluePrints.setVisibility(View.VISIBLE);
                                break;
                        }
                        return false;
                    }
                });
    }
}