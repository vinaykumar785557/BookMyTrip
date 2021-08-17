package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // binding activity with activity_splash

        // hiding action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Storing data into SharedPreferences
        sharedPreferences=getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        editor=sharedPreferences.edit();


       /*  created a new object of handler class and a runnable interface

        */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sharedPreferences.getBoolean("isLoggedIn",false))
                // this condition is true when user is already logged in and when running for first time
                {
                    // switching from one activity to another activity using intent
                    Intent i = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    // invoking second activity
                    startActivity(i);
                    // close the activity
                    finish();
                }
                else
                    // this condition executes when the user is log out
                {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2000); // wait for 2 seconds
    }
}
