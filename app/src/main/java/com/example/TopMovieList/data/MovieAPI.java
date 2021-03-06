/*
 * MIT License
 * Copyright (c) 2020. Antoine Mairet
 */

package com.example.TopMovieList.data;

import com.example.TopMovieList.presentation.model.RestMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieAPI {

    // Define the path to find the list of movies, using my key
    @GET("/API/Top250Movies/k_O4bZ6v9Q")
    Call<RestMoviesResponse> getMoviesResponse();

}
