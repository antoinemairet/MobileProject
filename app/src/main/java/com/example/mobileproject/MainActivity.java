package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String BASE_URL = "https://imdb-api.com/";
    private SharedPreferences sharedPreferences;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        sharedPreferences=getSharedPreferences("application_movie", Context.MODE_PRIVATE);
        List<Movie> movieList = getDataFromCache();
        if(movieList!= null) {
            showList(movieList);
        }else{
            makeApiCall();
        }
    }

    private List<Movie> getDataFromCache() {
        String jsonMovie = sharedPreferences.getString(Constants.KEY_MOVIE_LIST,null);

        if(jsonMovie == null) {
            return null;
        }else {
            Type listType = new TypeToken<List<Movie>>() {}.getType();
            return gson.fromJson(jsonMovie, listType);
        }
    }


    private void showList(List<Movie> listMovie) {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        mAdapter = new ListAdapter(listMovie);
        recyclerView.setAdapter(mAdapter);
    }



    private void makeApiCall(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MovieAPI movieApi = retrofit.create(MovieAPI.class);

        Call<RestMoviesResponse> call = movieApi.getMoviesResponse();
        call.enqueue(new Callback<RestMoviesResponse>() {
            @Override
            public void onResponse(Call<RestMoviesResponse> call, Response<RestMoviesResponse> response) {
                if(response.isSuccessful() && response.body() != null){

                    List<Movie> listMovie = response.body().items; //HERE
                    saveList(listMovie);
                    showList(listMovie);

                }else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestMoviesResponse> call, Throwable t) {
                showError();
            }
        });

    }

    private void saveList(List<Movie> listMovie) {
        String jsonString = gson.toJson(listMovie);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_MOVIE_LIST, jsonString)
                .apply();
        Toast.makeText(getApplicationContext(),"List saved",Toast.LENGTH_SHORT).show();
    }

    private void showError() {
        Toast.makeText(getApplicationContext(),"API Error",Toast.LENGTH_SHORT).show();
    }
}
