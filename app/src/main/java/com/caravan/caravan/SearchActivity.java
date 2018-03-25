package com.caravan.caravan;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

/**
 * Created by rmvanwyk on 3/24/18.
 */

public class SearchActivity extends MainActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
/*
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation, menu);

    SearchManager searchManager =
            (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView =
            (SearchView) menu.findItem(R.id.activity_search).getActionView();
    searchView.setSearchableInfo(
            searchManager.getSearchableInfo(getComponentName()));

    return true;
    }*/
}
