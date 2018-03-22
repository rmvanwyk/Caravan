package com.caravan.caravan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.amazonaws.mobile.auth.core.IdentityManager;


public class NextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
    }

    public void signUserOut(View view) {
        final IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
        if (identityManager.isUserSignedIn()) {
            identityManager.signOut();
        }
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
