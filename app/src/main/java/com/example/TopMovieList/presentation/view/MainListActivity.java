/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.TopMovieList.presentation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.TopMovieList.Constants;
import com.example.TopMovieList.presentation.model.Movie;
import com.example.TopMovieList.data.MovieAPI;
import com.example.TopMovieList.R;
import com.example.TopMovieList.presentation.model.RestMoviesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainListActivity extends AppCompatActivity  {

    private ListAdapter.RecyclerViewClickListener listener;
    private SharedPreferences sharedPreferences;
    private ArrayList<Movie> listMovie;
    private String jsonString;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("application_movie", Context.MODE_PRIVATE);
        gson = new GsonBuilder().setLenient().create();

        //Fetch the list saved in the cache
        listMovie = getDataFromCache();

        // If we found nothing in the cache we call the API to fill the list otherwise we display it
        if(listMovie!= null) {
            showList(listMovie);
        }else{
            makeApiCall();
        }
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

    // Display the list of movies using an adapter
    private void showList(ArrayList<Movie> listMovie) {

        setOnClickListener();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ListAdapter mAdapter = new ListAdapter(listMovie, listener);
        recyclerView.setAdapter(mAdapter);

    }

    // Function which start details activity with the information of the movie selected
    private void setOnClickListener() {

        listener = new ListAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(MainListActivity.this, DetailsActivity.class);

                //Give all the information of a particular movie to the next activity: DetailsActivity
                Movie movie = new Movie(listMovie.get(position).getId(),listMovie.get(position).getRank(),
                                null,listMovie.get(position).getTitle(),listMovie.get(position).getFullTitle(),
                                listMovie.get(position).getYear(),listMovie.get(position).getImage(),listMovie.get(position).getCrew(),
                        listMovie.get(position).getImDbRating(),null);

                //Save the movie in the cache
                jsonString = gson.toJson(movie);
                sharedPreferences
                        .edit()
                        .putString(Constants.KEY_MOVIE_FROM_MAIN_TO_DETAILS, jsonString)
                        .apply();

                // Start of the DetailsActivity
                startActivity(intent);
            }
        };

    }

    // Call the API in order to fill our list of movies
    private void makeApiCall(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MovieAPI movieApi = retrofit.create(MovieAPI.class);

        Call<RestMoviesResponse> call = movieApi.getMoviesResponse();
        call.enqueue(new Callback<RestMoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<RestMoviesResponse> call, @NonNull Response<RestMoviesResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    listMovie = response.body().items;
                    saveList(listMovie);
                    showList(listMovie);
                }else{
                    showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestMoviesResponse> call, @NonNull Throwable t) {
                showError();
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
        Toast.makeText(getApplicationContext(),"List saved",Toast.LENGTH_SHORT).show();
    }

    // Show an error when we can't access to the API
    private void showError() {
        Toast.makeText(getApplicationContext(),"API Error",Toast.LENGTH_SHORT).show();
    }



}
