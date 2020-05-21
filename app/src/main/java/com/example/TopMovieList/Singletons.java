/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.TopMovieList;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.TopMovieList.data.MovieAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static SharedPreferences sharedPreferencesInstance;
    private static MovieAPI movieApiInstance;
    private static Gson gsonInstance;

    public static SharedPreferences getSharedPreferences(Context context){
        if(sharedPreferencesInstance == null){
            sharedPreferencesInstance = context.getSharedPreferences("application_movie", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }

    public static Gson getGson(){
        if(gsonInstance == null) {
            gsonInstance = new GsonBuilder().setLenient().create();
        }
        return gsonInstance;
    }

    public static MovieAPI getMovieAPI(){
        if(movieApiInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            movieApiInstance = retrofit.create(MovieAPI.class);
        }
        return movieApiInstance;
    }

}
