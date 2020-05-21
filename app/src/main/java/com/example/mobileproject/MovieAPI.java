/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.mobileproject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieAPI {

    // Define the path to find the list of movies, using my key
    @GET("/API/Top250Movies/k_O4bZ6v9Q")
    Call<RestMoviesResponse> getMoviesResponse();

}
