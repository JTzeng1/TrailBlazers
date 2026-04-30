package com.example.trailblazers.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.trailblazers.database.entities.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class TrailDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static TrailDatabase INSTANCE;

    public static TrailDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TrailDatabase.class,
                    "trailblazers_db"
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}