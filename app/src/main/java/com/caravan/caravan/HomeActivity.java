package com.caravan.caravan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class HomeActivity extends AppCompatActivity {
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        LombokTestClass t1 = new LombokTestClass();
        LombokTestClass t2 = new LombokTestClass("asdf", 2);
        Log.d(LOG_TAG, t1.getA() + "\t" + t1.getB());
        Log.d(LOG_TAG, t2.getA() + "\t" + t2.getB());
    }

    public void goToSignIn(View view) {
        Intent intent = new Intent(this, AuthenticatorActivity.class);
        startActivity(intent);
    }
}
