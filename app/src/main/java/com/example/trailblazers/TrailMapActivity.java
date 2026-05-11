package com.example.trailblazers;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.trailblazers.database.TrailDatabase;
import com.example.trailblazers.database.entities.Trail;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.util.ArrayList;
import java.util.List;

public class TrailMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button playButton, pauseButton, stopButton;
    private boolean isTracking = false;
    private boolean isPaused = false;
    private List<LatLng> pathPoints = new ArrayList<>();
    private Polyline polyline;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private static final int LOCATION_PERMISSION_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_map);

        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        stopButton = findViewById(R.id.stopButton);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        setupButtons();
        setupLocationCallback();
        requestLocationPermission();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        polyline = mMap.addPolyline(new PolylineOptions().width(10).color(Color.BLUE));
        
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void setupButtons() {
        playButton.setOnClickListener(v -> {
            isTracking = true;
            isPaused = false;
            playButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Run started", Toast.LENGTH_SHORT).show();
        });

        pauseButton.setOnClickListener(v -> {
            isPaused = !isPaused;
            pauseButton.setText(isPaused ? "Resume" : "Pause");
            Toast.makeText(this, isPaused ? "Paused" : "Resumed", Toast.LENGTH_SHORT).show();
        });

        stopButton.setOnClickListener(v -> {
            isTracking = false;
            saveTrail();
        });
    }

    private void setupLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
                    if (mMap != null) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 17f));
                    }
                    if (isTracking && !isPaused && polyline != null) {
                        pathPoints.add(point);
                        polyline.setPoints(pathPoints);
                    }
                }
            }
        };
    }

    private void startLocationUpdates() {
        LocationRequest request = LocationRequest.create()
                .setInterval(2000)
                .setFastestInterval(1000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(request, locationCallback, getMainLooper());
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        } else {
            startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }
    }

    private void saveTrail() {
        if (pathPoints.isEmpty()) {
            finish();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("loggedInUser", -1);

        Trail trail = new Trail();
        trail.setUserId(userId);
        trail.setTitle("My Run");
        trail.setJournal("Auto recorded run");
        trail.setPolyline(encodePath(pathPoints));

        new Thread(() -> {
            TrailDatabase.getDatabase(this).trailDao().insert(trail);
            runOnUiThread(() -> {
                Toast.makeText(this, "Trail saved!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }

    private String encodePath(List<LatLng> path) {
        StringBuilder sb = new StringBuilder();
        for (LatLng p : path) {
            sb.append(p.latitude).append(",").append(p.longitude).append(";");
        }
        return sb.toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
}
