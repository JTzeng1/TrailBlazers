package com.example.trailblazers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trailblazers.database.TrailDatabase;
import com.example.trailblazers.database.entities.User;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_NAME = "loginPrefs";
    private static final String USER_KEY = "loggedInUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(() -> {
            TrailDatabase db = TrailDatabase.getDatabase(this);
            if (db.userDao().getUserByUserId(1) == null) {
                db.userDao().insert(new User("testuser1", "testuser1", false));
                db.userDao().insert(new User("admin2", "admin2", true));
            }
        }).start();

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        int userId = prefs.getInt(USER_KEY, -1);

        if (userId != -1) {
            startActivity(new Intent(this, LandingPageActivity.class));
            finish();
            return;
        }

        Button loginBtn = findViewById(R.id.loginButton);
        Button createAccountBtn = findViewById(R.id.createAccountButton);

        loginBtn.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        createAccountBtn.setOnClickListener(v -> startActivity(new Intent(this, CreateAccountActivity.class)));
    }
}
