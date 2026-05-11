package com.example.trailblazers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trailblazers.database.TrailDatabase;
import com.example.trailblazers.database.entities.User;
import com.example.trailblazers.database.UserDao;

public class LandingPageActivity extends AppCompatActivity {
    private static final String PREF_NAME = "loginPrefs";
    private static final String USER_KEY = "loggedInUser";

    private TextView usernameTextView;
    private TextView adminStatusTextView;
    private Button adminButton;
    private SharedPreferences prefs;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        usernameTextView = findViewById(R.id.usernameTextView);
        adminStatusTextView = findViewById(R.id.adminStatusTextView);
        adminButton = findViewById(R.id.adminButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        int userId = prefs.getInt(USER_KEY, -1);

        if (userId == -1) {
            redirectToMain();
            return;
        }

        new Thread(() -> {
            UserDao userDao = TrailDatabase.getDatabase(this).userDao();
            User currentUser = userDao.getUserByUserId(userId);

            runOnUiThread(() -> {
                if (currentUser == null) {
                    logoutUser();
                } else {
                    updateUI(currentUser);
                }
            });
        }).start();

        logoutButton.setOnClickListener(v -> logoutUser());

        findViewById(R.id.viewTrailsButton).setOnClickListener(v ->
                startActivity(new Intent(this, TrailActivity.class))
        );

        findViewById(R.id.addTrailButton).setOnClickListener(v ->
                startActivity(new Intent(this, AddTrailActivity.class))
        );

        findViewById(R.id.startRunButton).setOnClickListener(v ->
                startActivity(new Intent(this, TrailMapActivity.class))
        );

        adminButton.setOnClickListener(v -> startActivity(new Intent(this, AdminActivity.class)));
    }

    private void updateUI(User user) {
        usernameTextView.setText("Welcome, " + user.getUserName());
        if (user.isAdmin()) {
            adminStatusTextView.setText("Admin User");
            adminButton.setVisibility(View.VISIBLE);
        } else {
            adminStatusTextView.setText("Regular User");
            adminButton.setVisibility(View.INVISIBLE);
        }
    }

    private void logoutUser() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(USER_KEY);
        editor.apply();
        redirectToMain();
    }

    private void redirectToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
