package com.example.trailblazers.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.trailblazers.database.entities.Trail;

import java.util.List;

@Dao
public interface TrailDAO {

    @Insert
    long insert(Trail trail);

    @Query("SELECT * FROM trails WHERE userId = :userId")
    List<Trail> getTrailsByUserId(int userId);

    @Query("SELECT * FROM trails WHERE trailID = :id LIMIT 1")
    Trail getTrailById(int id);

    // Admin feature: view all trails/runs
    @Query("SELECT * FROM trails ORDER BY trailID ASC")
    List<Trail> getAllTrails();

    // Admin feature: delete one trail/run
    @Query("DELETE FROM trails WHERE trailID = :trailId")
    int deleteTrailById(int trailId);

    // Admin feature: delete all trails/runs for one user
    @Query("DELETE FROM trails WHERE userId = :userId")
    int deleteTrailsByUserId(int userId);
}