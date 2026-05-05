package com.example.trailblazers;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trailblazers.database.entities.Trail;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.trailblazers.database.TrailDAO;
import com.example.trailblazers.database.TrailDatabase;
import com.example.trailblazers.databinding.ActivityAddTrailBinding;

public class AddTrailActivity extends AppCompatActivity {
    private ActivityAddTrailBinding binding;
    private TrailDAO trailDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inflate binding (replaces setContentView + findViewById)
        binding = ActivityAddTrailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        trailDao = TrailDatabase.getDatabase(this).trailDao();

        binding.saveButton.setOnClickListener(v -> saveTrail());
    }

    private void saveTrail() {

        SharedPreferences prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("loggedInUser", -1);

        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // get values directly from UI
        String title = binding.titleInput.getText().toString().trim();
        String distanceStr = binding.distanceInput.getText().toString().trim();
        String timeStr = binding.timeInput.getText().toString().trim();
        String journal = binding.journalInput.getText().toString().trim();

        // validation (VERY important)
        if (title.isEmpty() || distanceStr.isEmpty() || timeStr.isEmpty()) {
            Toast.makeText(this, "Fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double distance = Double.parseDouble(distanceStr);
        long time = Long.parseLong(timeStr);

        // create object
        Trail trail = new Trail();
        trail.setUserId(userId);
        trail.setTitle(title);
        trail.setDistance(distance);
        trail.setTime(time);
        trail.setTrailJournal(journal);

        trailDao.insert(trail);

        finish(); // go back to list
    }
}