package com.example.trailblazers.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class TrailDatabase extends RoomDatabase {

    // Connects your DAO
    public abstract UserDao userDao();

    // Singleton instance
    private static TrailDatabase INSTANCE;

    public static TrailDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            TrailDatabase.class,
                            "trailblazers_db"
                    )
                    .allowMainThreadQueries() // OK for this class project
                    .build();
        }
        return INSTANCE;
    }
}
