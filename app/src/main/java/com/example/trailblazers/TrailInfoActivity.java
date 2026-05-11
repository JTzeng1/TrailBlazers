package com.example.trailblazers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TrailInfoActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_info);

        TextView title = findViewById(R.id.title);
        TextView details = findViewById(R.id.details);
        TextView journal = findViewById(R.id.journal);

        Intent intent = getIntent();

        String t = intent.getStringExtra("title");
        double d = intent.getDoubleExtra("distance", 0);
        long time = intent.getLongExtra("time", 0);
        String j = intent.getStringExtra("journal");

        title.setText(t != null ? t : "No Title");
        details.setText(d + " miles • " + Timer.formatTime(time));
        journal.setText(j != null ? j : "");
    }
}
