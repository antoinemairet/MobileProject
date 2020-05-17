package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showList();
        makeApiCall();
    }

    private void showList() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }// define an adapter
        mAdapter = new ListAdapter(input);
        recyclerView.setAdapter(mAdapter);
    }



    private void makeApiCall(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

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
                    Toast.makeText(getApplicationContext(),"API Success",Toast.LENGTH_SHORT).show();

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

    private void showError() {
        Toast.makeText(getApplicationContext(),"API Error",Toast.LENGTH_SHORT).show();
    }
}
