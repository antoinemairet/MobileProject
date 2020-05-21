/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.mobileproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private static SharedPreferences sharedPreferences;
    private Button buttonAddWatchListShow;
    private ArrayList<Movie> watchList;
    public Movie movie;
    private Gson gson;
    private TextView fullTitle ;
    private TextView alreadyAdded;
    private TextView rank;
    private TextView crew;
    private TextView rating;
    private TextView year;
    private ImageView image ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details_movie);
        buttonAddWatchListShow = findViewById(R.id.AddWatchListButton);
        alreadyAdded = findViewById(R.id.txtAlreadyInWatchList);
        fullTitle = findViewById(R.id.fullTitle);
        rank = findViewById(R.id.rank);
        crew = findViewById(R.id.crew);
        rating = findViewById(R.id.rating);
        year = findViewById(R.id.year);
        image = findViewById(R.id.imageMovie);

        alreadyAdded.setVisibility(View.INVISIBLE);
        gson = new GsonBuilder().setLenient().create();
        sharedPreferences=getSharedPreferences("application_movie", Context.MODE_PRIVATE);

        watchList = getWatchList();
        movie = fetchMovieFromPreviousActivity();


        setDetails();
        testAlreadyInWatchList();
        setButtonAddWatchList();
    }

    private void setButtonAddWatchList() {
        buttonAddWatchListShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this,WatchListActivity.class);

                String jsonString = gson.toJson(movie);
                sharedPreferences
                        .edit()
                        .putString(Constants.KEY_MOVIE_FROM_DETAILS_TO_WATCHLIST, jsonString)
                        .apply();

                startActivity(intent);

            }
        });
    }

    // Check if the movie is already in the watch list, if it is we removed the button to add it and display a message
    private void testAlreadyInWatchList() {
        if(watchList != null){

            for (Movie m : watchList) {

                if (movie.getId().equals(m.getId())) { // Already in the watchList
                    buttonAddWatchListShow.setVisibility(View.GONE);
                    alreadyAdded.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    // Set all the details of the movie on the different text view
    private void setDetails() {
        // We set all the text fields
        fullTitle.setText(movie.getFullTitle());
        rank.setText(String.format("Rank: %s", movie.getRank()));
        crew.setText(String.format("Crew: %s", movie.getCrew()));
        rating.setText(String.format("Rating: %s", movie.getImDbRating()));
        year.setText(String.format("Year: %s", movie.getYear()));
        // We set the image from the URL address using Picasso
        Picasso.get().load(movie.getImage()).into(image);
    }

    // Fetch the object movie selected by the user the cache
    private Movie fetchMovieFromPreviousActivity() {
        String jsonMovie = sharedPreferences.getString(Constants.KEY_MOVIE_FROM_MAIN_TO_DETAILS,null);
        if(jsonMovie == null) {
            return null;
        }else {
            return gson.fromJson(jsonMovie, Movie.class);
        }

    }

    // get the watch list with sharedPreferences
    private ArrayList<Movie> getWatchList() {
        String jsonMovie = sharedPreferences.getString(Constants.KEY_MOVIE_WATCHLIST, null);

        //sharedPreferences.edit().clear().commit();
        if(jsonMovie != null) {

            Type listType = new TypeToken<ArrayList<Movie>>() {}.getType();

            return gson.fromJson(jsonMovie, listType);

        }
        return new ArrayList<>();
    }

}
