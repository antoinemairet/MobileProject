/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.TopMovieList.presentation.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import com.example.TopMovieList.Constants;
import com.example.TopMovieList.R;
import com.example.TopMovieList.presentation.model.Movie;
import com.example.TopMovieList.presentation.view.HomePageActivity;
import com.example.TopMovieList.presentation.view.MainListActivity;
import com.example.TopMovieList.presentation.view.WatchListActivity;
import com.google.gson.Gson;

public class HomePageController {

    private final SharedPreferences sharedPreferences;
    private final HomePageActivity view;
    private final Gson gson;

    // Provide a suitable constructor
    public HomePageController(HomePageActivity homePageActivity, Gson gson, SharedPreferences sharedPreferences){
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
        this.view = homePageActivity;
    }

    // When the activity Home Page is started
    public void onStart(final Movie movie){
            setButtonListShow();
            setButtonWatchList(movie);
    }

    // Set the action of the list show button
    private void setButtonListShow() {

        Button buttonListShow = view.findViewById(R.id.buttonListMovie);

        //Set a listener to go to the mainActivity which display the list of movies
        buttonListShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(view, MainListActivity.class);
                view.startActivity(intent);

            }
        });
    }

    // Set the action of the list show button
    private void setButtonWatchList(final Movie movie){

        Button buttonWatchList = view.findViewById(R.id.buttonWatchList);

        //Set a listener to go to the watch list which display all the movies added by the user
        buttonWatchList.setOnClickListener(new View.OnClickListener() {

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

}
