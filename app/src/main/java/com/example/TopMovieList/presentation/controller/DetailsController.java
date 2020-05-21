/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.TopMovieList.presentation.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.TopMovieList.Constants;
import com.example.TopMovieList.presentation.model.Movie;
import com.example.TopMovieList.presentation.view.DetailsActivity;
import com.example.TopMovieList.presentation.view.WatchListActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class DetailsController {

    private final SharedPreferences sharedPreferences;
    private final DetailsActivity view;
    private String jsonMovie;
    private final Gson gson;

    public DetailsController(DetailsActivity detailsActivity, Gson gson, SharedPreferences sharedPreferences) {
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
        this.view = detailsActivity;
    }

    public void setButtonAddWatchList(Button buttonAddWatchListShow, final Movie movie) {

        buttonAddWatchListShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view, WatchListActivity.class);

                String jsonString = gson.toJson(movie);
                sharedPreferences
                        .edit()
                        .putString(Constants.KEY_MOVIE_FROM_DETAILS_TO_WATCHLIST, jsonString)
                        .apply();

                view.startActivity(intent);

            }
        });
    }

    // Check if the movie is already in the watch list, if it is we removed the button to add it and display a message
    public void testAlreadyInWatchList(ArrayList<Movie> watchList, Movie movie, TextView alreadyAdded, Button buttonAddWatchListShow) {
        if(watchList != null){

            for (Movie m : watchList) {

                if (movie.getId().equals(m.getId())) { // Already in the watchList
                    buttonAddWatchListShow.setVisibility(View.GONE);
                    alreadyAdded.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    // Fetch the object movie selected by the user the cache
    public Movie fetchMovieFromPreviousActivity() {
        jsonMovie = sharedPreferences.getString(Constants.KEY_MOVIE_FROM_MAIN_TO_DETAILS,null);
        if(jsonMovie == null) {
            return null;
        }else {
            return gson.fromJson(jsonMovie, Movie.class);
        }

    }

    // get the watch list with sharedPreferences
    public ArrayList<Movie> getWatchList() {

        jsonMovie = sharedPreferences.getString(Constants.KEY_MOVIE_WATCHLIST, null);

        if(jsonMovie != null) {

            Type listType = new TypeToken<ArrayList<Movie>>() {}.getType();
            return gson.fromJson(jsonMovie, listType);

        }
        return new ArrayList<>();
    }
}
