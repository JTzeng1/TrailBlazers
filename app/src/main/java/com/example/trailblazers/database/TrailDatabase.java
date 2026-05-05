package com.example.trailblazers.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.trailblazers.database.entities.Trail;
import com.example.trailblazers.database.entities.User;

@Database(entities = {User.class, Trail.class}, version = 3, exportSchema = false)
public abstract class TrailDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract TrailDAO trailDao();


    private static TrailDatabase INSTANCE;

    public static TrailDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TrailDatabase.class,
                    "trailblazers_db"
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return INSTANCE;
    }


}