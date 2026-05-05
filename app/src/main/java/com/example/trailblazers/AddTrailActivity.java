package com.example.trailblazers;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
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
        String hoursStr = binding.hoursInput.getText().toString().trim();
        String minutesStr = binding.minutesInput.getText().toString().trim();
        String secondsStr = binding.secondsInput.getText().toString().trim();
        String journal = binding.journalInput.getText().toString().trim();

        if (title.isEmpty() || distanceStr.isEmpty() ||
                hoursStr.isEmpty() || minutesStr.isEmpty() || secondsStr.isEmpty()) {

            Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        int hours;
        int minutes;
        int seconds;
        double distance;

        try {
            hours = Integer.parseInt(hoursStr);
            minutes = Integer.parseInt(minutesStr);
            seconds = Integer.parseInt(secondsStr);
            distance = Double.parseDouble(distanceStr);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter a valid time", Toast.LENGTH_SHORT).show();
            return;
        }
        long timeInSeconds = (hours * 3600L) + (minutes * 60L) + seconds;

        // create object
        Trail trail = new Trail();
        trail.setUserId(userId);
        trail.setTitle(title);
        trail.setDistance(distance);
        trail.setTime(timeInSeconds);
        trail.setJournal(journal);

        long savedTrailId = trailDao.insert(trail);
//        Intent intent = new Intent(this, TrailMapActivity.class);
//        intent.putExtra("trailID", (int) savedTrailId);
//        startActivity(intent);
        startActivity(new Intent(this, TrailActivity.class));


        finish(); // go back to list
    }
}