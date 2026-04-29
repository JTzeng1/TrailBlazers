package com.example.trailblazers.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE userID = :userId LIMIT 1")
    User getUserById(int userId);

    @Query("SELECT * FROM users WHERE userName = :username LIMIT 1")
    User getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE userName = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Query("SELECT COUNT(*) FROM users")
    int getUserCount();
}