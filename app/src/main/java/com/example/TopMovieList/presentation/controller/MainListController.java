/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.TopMovieList.presentation.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.example.TopMovieList.Constants;
import com.example.TopMovieList.Singletons;
import com.example.TopMovieList.presentation.model.Movie;
import com.example.TopMovieList.presentation.model.RestMoviesResponse;
import com.example.TopMovieList.presentation.view.DetailsActivity;
import com.example.TopMovieList.presentation.view.MainListActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainListController {

    private SharedPreferences sharedPreferences;
    private ArrayList<Movie> listMovie;
    private MainListActivity view;
    private String jsonString;
    private Gson gson;

    // Provide a suitable constructor
    public MainListController(MainListActivity mainListActivity,Gson gson, SharedPreferences sharedPreferences) {
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
        this.view = mainListActivity;
    }

    // When the activity MainList is started
    public void onStart(){

        //Fetch the list saved in the cache
        listMovie = getDataFromCache();

        // If we found nothing in the cache we call the API to fill the list otherwise we display it
        if(listMovie!= null) {
            view.showList(listMovie);
        }else{
            makeApiCall();
        }
    }

    // Call the API in order to fill our list of movies
    private void makeApiCall(){

        Call<RestMoviesResponse> call = Singletons.getMovieAPI().getMoviesResponse();
        call.enqueue(new Callback<RestMoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<RestMoviesResponse> call, @NonNull Response<RestMoviesResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    listMovie = response.body().items;
                    saveList(listMovie);
                    view.showList(listMovie);
                }else{
                    view.showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestMoviesResponse> call, @NonNull Throwable t) {
                view.showError();
            }
        });

    }

    // Function for save the list using sharedPreferences
    private void saveList(List<Movie> listMovie) {
        jsonString = gson.toJson(listMovie);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_MOVIE_LIST, jsonString)
                .apply();
    }

    // Function used to fetch data from the cache and return it as list of movies
    private ArrayList<Movie> getDataFromCache() {

        String jsonMovie = sharedPreferences.getString(Constants.KEY_MOVIE_LIST,null);
        if(jsonMovie == null) {
            return null;
        }else {
            Type listType = new TypeToken<List<Movie>>() {}.getType();
            return gson.fromJson(jsonMovie, listType);
        }
    }

    // Function which start details activity with the information of the movie selected
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

}
