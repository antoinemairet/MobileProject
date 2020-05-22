/*
 * MIT License
 * Copyright (c) 2020. Antoine Mairet
 */

package com.example.TopMovieList.data;

import com.example.TopMovieList.presentation.model.RestMoviesResponse;

public interface MovieCallback {

        void onSuccess(RestMoviesResponse response);
        void onFailed();

}
