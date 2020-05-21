/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.mobileproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HomePageActivity extends AppCompatActivity {

    private Button buttonListShow ;
    private Button buttonWatchList;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        sharedPreferences=getSharedPreferences("application_movie", Context.MODE_PRIVATE);
        gson = new GsonBuilder().setLenient().create();

        buttonListShow = findViewById(R.id.buttonListMovie);
        buttonWatchList = findViewById(R.id.buttonWatchList);

        setButtonListener();

    }

    // Set the listeners of the different home page buttons
    private void setButtonListener() {
        //Set a listener to go to the mainActivity which display the list of movies
        buttonListShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePageActivity.this, MainListActivity.class);
                startActivity(intent);

            }
        });
        //Set a listener to go to the watch list which display all the movies added by the user
        buttonWatchList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePageActivity.this, WatchListActivity.class);
                Movie movie = new Movie("-1",null,null,null,null,null,null,null,null,null);
                String jsonString = gson.toJson(movie);
                sharedPreferences
                        .edit()
                        .putString(Constants.KEY_MOVIE_FROM_DETAILS_TO_WATCHLIST, jsonString)
                        .apply();

                startActivity(intent);

            }
        });
    }
}

