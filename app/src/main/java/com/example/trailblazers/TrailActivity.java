package com.example.trailblazers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trailblazers.database.TrailDAO;
import com.example.trailblazers.database.TrailDatabase;
import com.example.trailblazers.database.entities.Trail;

import java.util.ArrayList;
import java.util.List;

public class TrailActivity extends AppCompatActivity {
    private TrailDAO trailDao;
    private List<Trail> trails = new ArrayList<>();
    private RecyclerView recyclerView;
    private TrailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trail_activity);

        trailDao = TrailDatabase.getDatabase(this).trailDao();
        recyclerView = findViewById(R.id.trailRecyclerView);
        Button addButton = findViewById(R.id.addTrailButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        addButton.setOnClickListener(v -> startActivity(new Intent(this, AddTrailActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTrails();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadTrails() {
        SharedPreferences prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("loggedInUser", -1);

        new Thread(() -> {
            List<Trail> result = trailDao.getTrailsByUserId(userId);
            runOnUiThread(() -> {
                trails.clear();
                if (result != null) {
                    trails.addAll(result);
                }

                if (adapter == null) {
                    adapter = new TrailAdapter(trails, trail -> {
                        Intent intent = new Intent(this, TrailInfoActivity.class);
                        intent.putExtra("trailID", trail.getTrailID());
                        intent.putExtra("title", trail.getTitle());
                        intent.putExtra("distance", trail.getDistance());
                        intent.putExtra("time", trail.getTime());
                        intent.putExtra("journal", trail.getJournal());
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            });
        }).start();
    }
}
