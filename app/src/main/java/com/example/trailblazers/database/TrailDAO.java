package com.example.trailblazers.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trailblazers.database.entities.Trail;

import java.util.List;

@Dao
public interface TrailDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Trail trail);

    @Update
    void update(Trail trail);

    @Delete
    void delete(Trail trail);

    @Query("SELECT * FROM trails WHERE userId = :userId")
    List<Trail> getTrailsByUserId(int userId);

    @Query("SELECT * FROM trails WHERE trailID = :id LIMIT 1")
    Trail getTrailById(int id);

    @Query("SELECT * FROM trails ORDER BY trailID ASC")
    List<Trail> getAllTrails();

    @Query("DELETE FROM trails WHERE trailID = :trailId")
    int deleteTrailById(int trailId);

    @Query("DELETE FROM trails WHERE userId = :userId")
    int deleteTrailsByUserId(int userId);
}
