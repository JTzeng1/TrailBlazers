package com.example.trailblazers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.trailblazers.database.TrailDAO;
import com.example.trailblazers.database.TrailDatabase;
import com.example.trailblazers.database.entities.Trail;

import com.google.android.gms.location.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

public class TrailMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Trail trail;
    private FusedLocationProviderClient client;
    private LocationCallback locationCallback;
    private boolean isTracking = false;

    private List<LatLng> pathPoints = new ArrayList<>();
    private Polyline livePolyline;

    private static final int LOCATION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_map);

        client = LocationServices.getFusedLocationProviderClient(this);
        int trailId = getIntent().getIntExtra("trailID", -1);

        if (trailId == -1) {
            finish();
            return;
        }

        TrailDAO dao = TrailDatabase.getDatabase(this).trailDao();
        trail = dao.getTrailById(trailId);

        if (trail == null) {;
            finish();
            return;
        }

        setupMap();


        Button startBtn = findViewById(R.id.startButton);
        Button stopBtn = findViewById(R.id.stopButton);

        startBtn.setOnClickListener(v -> {
            if (!isTracking) {
                isTracking = true;
                startTracking();
            }
        });

        stopBtn.setOnClickListener(v -> {
            isTracking = false;

            if (client != null && locationCallback != null) {
                client.removeLocationUpdates(locationCallback);
            }

            Toast.makeText(this, "Tracking stopped", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (trail.getPolyline() != null) {
            List<LatLng> points = PolyUtil.decode(trail.getPolyline());

            if (!points.isEmpty()) {
                mMap.addPolyline(new PolylineOptions().addAll(points).width(8));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 13f));
            }
        }
        Toast.makeText(this, "MAP IS LOADED", Toast.LENGTH_SHORT).show();

    }



    private void startTracking() {

        if (mMap == null) {
            Toast.makeText(this, "Map not ready yet", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
            return;
        }

        LocationRequest request =
                new LocationRequest.Builder(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        2000
                ).setMinUpdateIntervalMillis(1000).build();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult result) {

                if (!isTracking) return;

                for (Location location : result.getLocations()) {

                    LatLng point = new LatLng(
                            location.getLatitude(),
                            location.getLongitude()
                    );

                    pathPoints.add(point);

                    if (livePolyline == null) {
                        livePolyline = mMap.addPolyline(new PolylineOptions().width(8));
                    }

                    livePolyline.setPoints(pathPoints);

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                }
            }
        };

        client.requestLocationUpdates(request, locationCallback, Looper.getMainLooper());
    }

}