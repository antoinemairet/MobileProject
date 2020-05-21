/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.mobileproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class WatchListActivity extends AppCompatActivity {

    private ArrayList<Movie> watchListMovie = new ArrayList<>();
    private WatchListAdapter.RecyclerViewClickListener listener;
    private static SharedPreferences sharedPreferences;
    private TextView txtHowAddToTheList;
    private TextView txtEmptyList;
    private String jsonMovie;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_watch_list);
        txtEmptyList = findViewById(R.id.txtWatchListEmpty);
        txtHowAddToTheList = findViewById(R.id.txtHowAddMovie);

        sharedPreferences=getSharedPreferences("application_movie", Context.MODE_PRIVATE);
        gson = new GsonBuilder().setLenient().create();

        watchListMovie = getWatchList();
        watchListMovie = ifNewMovieInWatchList(); // Add a new movie if user add one

        saveWatchList(watchListMovie); // Save the modification of the watch list

        watchListMovie = getWatchList(); // get the watch list

        displayOfWatchListActivity();

    }

    // If the watch list is not empty will remove some textView displaying list empty
    // And display the watch list if its not empty
    private void displayOfWatchListActivity() {

        if(watchListMovie != null) {
            txtEmptyList.setVisibility(View.GONE);
            txtHowAddToTheList.setVisibility(View.GONE);
            showWatchList(watchListMovie);
        }else{
            Toast.makeText(getApplicationContext(),"WatchList Null",Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<Movie> ifNewMovieInWatchList() {
        jsonMovie = sharedPreferences.getString(Constants.KEY_MOVIE_FROM_DETAILS_TO_WATCHLIST,null);
        Movie movie = gson.fromJson(jsonMovie, Movie.class);
        if(movie.getId().equals("-1")) {

            Log.d("TAG", "JsonMovie null");
            return watchListMovie;

        }else {
            Log.d("TAG", "JsonMovie non null");

            if(movie == null){Log.d("TAG", "Movie null");}
            Log.d("TAG", "Apres");
            watchListMovie = getWatchList();

            watchListMovie.add(movie);
        }

        return watchListMovie;
    }

    // Show the list of movies using an adapter
    private void showWatchList(ArrayList<Movie> watchListMovie) {

        RecyclerView recyclerView = findViewById(R.id.recyclerViewWatchList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        WatchListAdapter mAdapter = new WatchListAdapter(watchListMovie, listener);
        recyclerView.setAdapter(mAdapter);

    }
    private ArrayList<Movie> getWatchList() {
        jsonMovie = sharedPreferences.getString(Constants.KEY_MOVIE_WATCHLIST,null);
        Log.d("TAG", jsonMovie);
        if(jsonMovie == null) {
            Log.d("TAG", "jsonMovie NULL");
            return new ArrayList<>();
        }else {
            Log.d("TAG", "jsonMovie NON NULL");
            Type listType = new TypeToken<ArrayList<Movie>>() {}.getType();
            return gson.fromJson(jsonMovie, listType);
        }
    }
    // Function for save the list using sharedPreference
    private void saveWatchList(ArrayList<Movie> watchListMovie) {
        String jsonString = gson.toJson(watchListMovie);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_MOVIE_WATCHLIST, jsonString)
                .apply();
        //Toast.makeText(WatchListActivity.this,"List saved",Toast.LENGTH_SHORT).show();
    }

    public WatchListActivity(WatchListAdapter.RecyclerViewClickListener listener) {
        this.listener = listener;
    }

    public WatchListActivity(){

    }
}
