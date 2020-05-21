/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.TopMovieList.presentation.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.TopMovieList.R;
import com.example.TopMovieList.Singletons;
import com.example.TopMovieList.presentation.controller.HomePageController;
import com.example.TopMovieList.presentation.model.Movie;


public class HomePageActivity extends AppCompatActivity {

    public HomePageController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        final Movie movie = new Movie("-1",null,null,null,null,null,null,null,null,null);

        controller = new HomePageController(this, Singletons.getGson(), Singletons.getSharedPreferences(getApplicationContext()));

        controller.onStart(movie);

    }


}

