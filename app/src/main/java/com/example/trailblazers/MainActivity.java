package com.example.trailblazers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trailblazers.database.TrailDatabase;
import com.example.trailblazers.database.entities.User;

public class MainActivity extends AppCompatActivity {
    //saved storage file
    // to stay consistant PREF_NAME will be used instead of TrailBlazersPrefs
    private static final String PREF_NAME = "loginPrefs";
    //key used to store logged-in user id in SharedPreferences
    private static final String USER_KEY = "loggedInUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fixed login
        new Thread(() -> {
            TrailDatabase db = TrailDatabase.getDatabase(this);

            if (db.userDao().getUserByUserId(1) == null) {
                db.userDao().insert(new User("testuser1", "testuser1", false));
                db.userDao().insert(new User("admin2", "admin2", true));
            }

        }).start();

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

        //ui setup
        Button loginBtn = findViewById(R.id.loginButton);
        Button createAccountBtn = findViewById(R.id.createAccountButton);

        //when login button clicked, will send user to login page
        loginBtn.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));

        //when create account button clicked, will send user to create account page
        createAccountBtn.setOnClickListener(v -> startActivity(new Intent(this, CreateAccountActivity.class)));
    }
}
