package com.example.trailblazers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trailblazers.database.TrailDatabase;
import com.example.trailblazers.database.entities.Trail;
import com.example.trailblazers.database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private static final String PREF_NAME = "loginPrefs";
    private static final String USER_KEY = "loggedInUser";

    private TrailDatabase database;
    private ListView usersListView;
    private ListView trailsListView;
    private EditText deleteUserEditText;
    private EditText deleteTrailEditText;
    private Button deleteUserButton;
    private Button deleteTrailButton;
    private Button backButton;
    private int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        database = TrailDatabase.getDatabase(this);

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        loggedInUserId = prefs.getInt(USER_KEY, -1);

        usersListView = findViewById(R.id.usersListView);
        trailsListView = findViewById(R.id.trailsListView);
        deleteUserEditText = findViewById(R.id.deleteUserEditText);
        deleteTrailEditText = findViewById(R.id.deleteTrailEditText);
        deleteUserButton = findViewById(R.id.deleteUserButton);
        deleteTrailButton = findViewById(R.id.deleteTrailButton);
        backButton = findViewById(R.id.adminBackButton);

        checkAdminAndLoadData();

        deleteUserButton.setOnClickListener(v -> deleteUser());
        deleteTrailButton.setOnClickListener(v -> deleteTrail());
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LandingPageActivity.class));
            finish();
        });
    }

    private void checkAdminAndLoadData() {
        new Thread(() -> {
            if (loggedInUserId == -1) {
                runOnUiThread(this::exitDueToAuth);
                return;
            }

            User currentUser = database.userDao().getUserByUserId(loggedInUserId);
            if (currentUser == null || !currentUser.isAdmin()) {
                runOnUiThread(this::exitDueToAuth);
            } else {
                runOnUiThread(() -> {
                    loadUsers();
                    loadTrails();
                });
            }
        }).start();
    }

    private void exitDueToAuth() {
        Toast.makeText(this, "Admin access required.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void loadUsers() {
        new Thread(() -> {
            List<User> users = database.userDao().getAllUsers();
            ArrayList<String> userDisplayList = new ArrayList<>();
            for (User user : users) {
                userDisplayList.add(
                        "User ID: " + user.getUserID()
                                + "\nUsername: " + user.getUserName()
                                + "\nAdmin: " + user.isAdmin()
                );
            }
            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        userDisplayList
                );
                usersListView.setAdapter(adapter);
            });
        }).start();
    }

    private void loadTrails() {
        new Thread(() -> {
            List<Trail> trails = database.trailDao().getAllTrails();
            ArrayList<String> trailDisplayList = new ArrayList<>();
            for (Trail trail : trails) {
                trailDisplayList.add(
                        "Trail ID: " + trail.getTrailID()
                                + "\nUser ID: " + trail.getUserId()
                                + "\nTitle: " + trail.getTitle()
                                + "\nDistance: " + trail.getDistance()
                                + "\nTime: " + trail.getTime()
                                + "\nJournal: " + trail.getJournal()
                );
            }
            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        trailDisplayList
                );
                trailsListView.setAdapter(adapter);
            });
        }).start();
    }

    private void deleteUser() {
        String input = deleteUserEditText.getText().toString().trim();
        if (input.isEmpty()) {
            Toast.makeText(this, "Enter a user ID.", Toast.LENGTH_SHORT).show();
            return;
        }
        int userId;
        try {
            userId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid user ID.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userId == loggedInUserId) {
            Toast.makeText(this, "You cannot delete your own admin account while logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        final int targetId = userId;
        new Thread(() -> {
            User userToDelete = database.userDao().getUserByUserId(targetId);
            if (userToDelete == null) {
                runOnUiThread(() -> Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show());
                return;
            }
            database.trailDao().deleteTrailsByUserId(targetId);
            int deletedUsers = database.userDao().deleteUserById(targetId);
            runOnUiThread(() -> {
                if (deletedUsers > 0) {
                    Toast.makeText(this, "User and their trails deleted.", Toast.LENGTH_SHORT).show();
                    deleteUserEditText.setText("");
                    loadUsers();
                    loadTrails();
                } else {
                    Toast.makeText(this, "User delete failed.", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private void deleteTrail() {
        String input = deleteTrailEditText.getText().toString().trim();
        if (input.isEmpty()) {
            Toast.makeText(this, "Enter a trail ID.", Toast.LENGTH_SHORT).show();
            return;
        }
        int trailId;
        try {
            trailId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid trail ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        final int targetId = trailId;
        new Thread(() -> {
            Trail trailToDelete = database.trailDao().getTrailById(targetId);
            if (trailToDelete == null) {
                runOnUiThread(() -> Toast.makeText(this, "Trail not found.", Toast.LENGTH_SHORT).show());
                return;
            }
            int deletedTrails = database.trailDao().deleteTrailById(targetId);
            runOnUiThread(() -> {
                if (deletedTrails > 0) {
                    Toast.makeText(this, "Trail deleted.", Toast.LENGTH_SHORT).show();
                    deleteTrailEditText.setText("");
                    loadTrails();
                } else {
                    Toast.makeText(this, "Trail delete failed.", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}
