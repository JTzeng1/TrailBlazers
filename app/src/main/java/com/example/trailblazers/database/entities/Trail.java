package com.example.trailblazers.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trails")
public class Trail {
    @PrimaryKey(autoGenerate = true)
    private int trailID;

    private int userId;

    private String title;
    private double distance;
    private long time;
    private String trailJournal;
    private String polyline;


    public int getTrailID() {
        return trailID;
    }

    public void setTrailID(int trailID) {
        this.trailID = trailID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTrailJournal() {
        return trailJournal;
    }

    public void setTrailJournal(String trailJournal) {
        this.trailJournal = trailJournal;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }
}