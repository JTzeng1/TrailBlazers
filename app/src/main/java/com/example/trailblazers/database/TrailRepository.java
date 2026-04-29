package com.example.trailblazers.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.trailblazers.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TrailRepository {
    private TrailDAO trailDAO;
    private UserDAO userDAO;
    private ArrayList<Trail> allTrails;
    private static TrailRepository repository;

    private TrailRepository (Application application) {
        TrailDatabase db = TrailDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.trailDAO = db.trailDAO();
        this.allTrails  = (ArrayList<Trail>)  this.trailDAO.getAllRecords();

    }

    public static TrailRepository  getRepository(Application application){
        if(repository != null) {
            return repository ;
        }
        Future<TrailRepository> future = TrailDatabase.databaseWriteExecutor.submit(
                new Callable<TrailRepository>() {
                    @Override
                    public TrailRepository call() throws Exception {
                        return new TrailRepository(application);
                    }
                }
        );

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem getting TrailRepository.");
        }
        return null;
    }

    public ArrayList<Trail> getAllLogs() {
        Future<ArrayList<Trail>> future = TrailDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Trail>>() {
                    @Override
                    public ArrayList<Trail> call() throws Exception {
                        return (ArrayList<Trail>)trailDAO.getAllRecords();
                    }
                });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting trails.");
        }
        return null;
    }


    public void insertTrail(Trail trail){
        TrailDatabase.databaseWriteExecutor.execute(() -> {
            trailDAO.insert(trail);
        });
    }


    public LiveData<List<Trail>> getTrails(int id){
        return trailDAO.getTrailsByUser(id);
    }


    public void insertUser(User... user) {
        TrailDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);

    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }


    public LiveData<List<Trail>> getAllLogsByUserIdLiveData(int loggedInUserId){
        return trailDAO.getRecordsByUserIdLiveData(loggedInUserId);
    }

    @Deprecated
    public ArrayList<Trail> getAllLogsByUserId(int userId) {
        Future<ArrayList<Trail>> future = TrailDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Trail>>() {
                    @Override
                    public ArrayList<Trail> call() throws Exception {
                        return (ArrayList<Trail>) trailDAO.getRecordsByUserId(userId);
                    }
                });
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all Trails");
        }
        return null;
    }




}
