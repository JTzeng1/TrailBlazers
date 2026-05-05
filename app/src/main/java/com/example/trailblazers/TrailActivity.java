package com.example.trailblazers;

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

import java.util.List;

public class TrailActivity extends AppCompatActivity {
    private TrailDAO trailDao;
    private List<Trail> trails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trail_activity);
        trailDao = TrailDatabase.getDatabase(this).trailDao();

        SharedPreferences prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("loggedInUser", -1);
        trails = trailDao.getTrailsByUserId(userId);

        RecyclerView recyclerView = findViewById(R.id.trailRecyclerView);
        Button addButton = findViewById(R.id.addTrailButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TrailAdapter(trails));

        addButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AddTrailActivity.class));
        });




    }


}
