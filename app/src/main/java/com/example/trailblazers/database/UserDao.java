package com.example.trailblazers.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.trailblazers.database.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("SELECT * FROM users WHERE userName = :username LIMIT 1")
    User getUserByUserName(String username);

    @Query("SELECT * FROM users WHERE userID = :userId LIMIT 1")
    User getUserByUserId(int userId);

    @Query("SELECT * FROM users WHERE userName = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    // Admin feature: view all accounts
    @Query("SELECT * FROM users ORDER BY userID ASC")
    List<User> getAllUsers();

    // Admin feature: delete account by userID
    @Query("DELETE FROM users WHERE userID = :userId")
    int deleteUserById(int userId);
}