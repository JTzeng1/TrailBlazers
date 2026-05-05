package com.example.trailblazers.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.trailblazers.database.entities.Trail;

import java.util.List;

@Dao

public interface TrailDAO {
    @Insert
    void insert(Trail trail);

    @Query("SELECT * FROM trails WHERE userId = :userId")
    List<Trail> getTrailsByUserId(int userId);

    @Query("DELETE FROM trails")
    void deleteAll();

}