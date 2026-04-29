package com.example.trailblazers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //saved storage file
    private static final String PREF_NAME = "TrailBlazersPrefs";
    //key used to store logged-in user id in SharedPreferences
    private static final String USER_KEY = "loggedInUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //checks if user is already logged in
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //get stored user id, -1 meaning no user is logged in
        int userId = prefs.getInt(USER_KEY, -1);

        if (userId != -1) {
            //redirects user to landing page if user is already logged in
            startActivity(new Intent(this, LandingPageActivity.class));
            finish();
            return;
        }

        Button loginBtn = findViewById(R.id.loginButton);
        Button createAccountBtn = findViewById(R.id.createAccountButton);

        //when login button clicked, will send user to login page
        loginBtn.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class))
        );

        //when create account button clicked, will send user to create account page
        createAccountBtn.setOnClickListener(v ->
                startActivity(new Intent(this, CreateAccountActivity.class))
        );
    }
}