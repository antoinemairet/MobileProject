/*
 * MIT License
 * Copyright (c) 2020. Antoine Mairet
 */

package com.example.TopMovieList.data;

import android.content.SharedPreferences;

import com.example.TopMovieList.presentation.model.RestMoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private MovieAPI movieAPI;
    private SharedPreferences sharedPreferences;

    public MovieRepository(MovieAPI movieAPI, SharedPreferences sharedPreferences) {
        this.movieAPI = movieAPI;
        this.sharedPreferences = sharedPreferences;
    }

    public void getMoviesResponse(final MovieCallback callback){
        movieAPI.getMoviesResponse().enqueue(new Callback<RestMoviesResponse>() {
            @Override
            public void onResponse(Call<RestMoviesResponse> call, Response<RestMoviesResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    callback.onSuccess(response.body());
                }else{
                    callback.onFailed();
                }
            }

            @Override
            public void onFailure(Call<RestMoviesResponse> call, Throwable t) {
                callback.onFailed();
            }
        });
    }
}
