package com.example.trailblazers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.trailblazers.Timer;

import androidx.appcompat.app.AppCompatActivity;

public class TrailInfoActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_info);

        //connect the UI elements from the XML file to these variables
        TextView title = findViewById(R.id.title);
        TextView details = findViewById(R.id.details);
        TextView journal = findViewById(R.id.journal);

        //this intent has the trail info from the trail activity
        Intent intent = getIntent();

        String t = intent.getStringExtra("title");
        double d = intent.getDoubleExtra("distance", 0);
        long time = intent.getLongExtra("time", 0);
        String j = intent.getStringExtra("journal");

        //displays them in this format on the screen
        title.setText(t != null ? t : "No Title");
        details.setText(d + " miles • " + Timer.formatTime(time));
        journal.setText(j != null ? j : "");


    }


}
