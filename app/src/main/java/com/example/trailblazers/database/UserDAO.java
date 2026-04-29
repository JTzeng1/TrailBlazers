package com.example.trailblazers.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.trailblazers.database.entities.User;

@Dao
public interface UserDAO {

    @Insert
    void insert(User user);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("SELECT * FROM users WHERE userName=:username LIMIT 1")
    LiveData<User> getUserByUserName(String username);


    @Query("SELECT * FROM users WHERE userID=:userId LIMIT 1")
    LiveData<User> getUserByUserId(int userId);


}