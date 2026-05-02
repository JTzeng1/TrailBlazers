package com.example.trailblazers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trailblazers.database.TrailDatabase;
import com.example.trailblazers.database.entities.User;
import com.example.trailblazers.database.UserDao;

public class LandingPageActivity extends AppCompatActivity {
    //saved storage file
    private static final String PREF_NAME = "loginPrefs";
    //key used to store logged-in user id in SharedPreferences
    private static final String USER_KEY = "loggedInUser";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        //ui setup
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        TextView adminStatusTextView = findViewById(R.id.adminStatusTextView);
        Button adminButton = findViewById(R.id.adminButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        //checks if user is already logged in
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //get stored user id, -1 meaning no user is logged in
        int userId = prefs.getInt(USER_KEY, -1);

        if (userId == -1) {
            //redirects user to main activity if user is not logged in
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        //get user from database
        UserDao userDao = TrailDatabase.getDatabase(this).userDao();
        User currentUser = userDao.getUserByUserId(userId);

        //if saved id doesn't match database, clear login and send user back
        if (currentUser == null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(USER_KEY);
            editor.apply();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        //display username: "Welcome, USER"
        usernameTextView.setText("Welcome, " + currentUser.getUserName());

        //checks if user is admin
        if (currentUser.isAdmin()) {
            //shows admin button and changes user status to "Admin User"
            adminStatusTextView.setText("Admin User");
            adminButton.setVisibility(View.VISIBLE);
        } else {
            //hides admin button and changes user status to "Regular User"
            adminStatusTextView.setText("Regular User");
            adminButton.setVisibility(View.INVISIBLE);
        }

        //logs user out and sends back to main activity
        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(USER_KEY);
            editor.apply();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        //admin button
        adminButton.setOnClickListener(v -> {
            //TODO: Add functionality to admin button
        });
    }
}
