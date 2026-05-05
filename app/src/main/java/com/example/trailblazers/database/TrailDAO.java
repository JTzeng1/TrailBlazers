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

}