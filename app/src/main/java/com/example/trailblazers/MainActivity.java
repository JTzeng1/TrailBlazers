package com.example.trailblazers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //CONSTANTS
    //saved storage file
    private static final String PREF_NAME = "TrailBlazersPrefs";
    //key used to store user id in storage
    private static final String USER_KEY = "loggedInUser";

    //ON-CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //checks if user is already logged in
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //get stored user id, -1 meaning no user is logged in
        int userId = prefs.getInt(USER_KEY, -1);

        if (userId != -1) {
            //sends to landing page if user is logged in
            startActivity(new Intent(this, LandingPageActivity.class));
            //closes activity so user can't return
            finish();
            return;
        }

        //login button
        Button loginBtn = findViewById(R.id.loginButton);
        //register new account button
        Button createAccountBtn = findViewById(R.id.createAccountButton);

        //when clicked will send user to login page
        loginBtn.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class))
        );

        //when clicked will send user to create account page
        createAccountBtn.setOnClickListener(v ->
                startActivity(new Intent(this, CreateAccountActivity.class))
        );
    }
}