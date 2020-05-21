/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.TopMovieList.presentation.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import com.example.TopMovieList.Constants;
import com.example.TopMovieList.presentation.model.Movie;
import com.example.TopMovieList.presentation.view.DetailsActivity;
import com.example.TopMovieList.presentation.view.WatchListActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class WatchListController {

    private final SharedPreferences sharedPreferences;
    private final WatchListActivity view;
    private final Gson gson;
    private String jsonMovie;
    private String jsonString;

    public WatchListController(WatchListActivity watchListActivity, Gson gson, SharedPreferences sharedPreferences){
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
        this.view = watchListActivity;

    }

    public ArrayList<Movie> ifNewMovieInWatchList(ArrayList<Movie> watchListMovie) {

        jsonMovie = sharedPreferences.getString(Constants.KEY_MOVIE_FROM_DETAILS_TO_WATCHLIST,null);
        Movie movie = gson.fromJson(jsonMovie, Movie.class);
        if(movie.getId().equals("-1")) { // From the home page without a movie to add

            return watchListMovie;

        }else { // From details with a movie to add

            watchListMovie = getWatchList();
            watchListMovie.add(movie);
        }
        return watchListMovie;
    }

    // Fetch the watch list from the cache
    public ArrayList<Movie> getWatchList() {

        jsonMovie = sharedPreferences.getString(Constants.KEY_MOVIE_WATCHLIST,null);
        if(jsonMovie == null) { // Empty watch list

            return new ArrayList<>();

        }else { // Watch list not empty

            Type listType = new TypeToken<ArrayList<Movie>>() {}.getType();
            return gson.fromJson(jsonMovie, listType);
        }
    }

    // Function for save the list using sharedPreference
    public void saveWatchList(ArrayList<Movie> watchListMovie) {

        jsonString = gson.toJson(watchListMovie);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_MOVIE_WATCHLIST, jsonString)
                .apply();
    }

    public void onItemClick(Movie item) {

        Intent intent = new Intent(view, DetailsActivity.class);

        //Save the movie in the cache
        jsonString = gson.toJson(item);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_MOVIE_FROM_MAIN_TO_DETAILS, jsonString)
                .apply();

        // Start of the DetailsActivity
        view.startActivity(intent);
    }

    public void deleteMovieFromWatchList(Movie item, ArrayList<Movie> values) {
        values.remove(item);
        //Save the new watch list, without the movie deleted
        String jsonString = gson.toJson(values);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_MOVIE_WATCHLIST,jsonString)
                .apply();

    }
}

