package com.example.trailblazers.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trailblazers.database.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("SELECT * FROM users WHERE userName = :username LIMIT 1")
    User getUserByUserName(String username);

    @Query("SELECT * FROM users WHERE userID = :userId LIMIT 1")
    User getUserByUserId(int userId);

    @Query("SELECT * FROM users WHERE userName = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Query("SELECT * FROM users ORDER BY userID ASC")
    List<User> getAllUsers();

    @Query("DELETE FROM users WHERE userID = :userId")
    int deleteUserById(int userId);
}
