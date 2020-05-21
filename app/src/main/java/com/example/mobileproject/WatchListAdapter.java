/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.mobileproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.ViewHolder>{
    private ArrayList<Movie> values;
    private RecyclerViewClickListener listener;
    private Button deleteButton;
    private Gson gson;
    private SharedPreferences sharedPreferences;


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtHeader;
        ImageView mImageView;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = v.findViewById(R.id.firstLine);
            deleteButton = v.findViewById(R.id.buttonDelete);
            mImageView = v.findViewById(R.id.icon) ;
            gson = new GsonBuilder().setLenient().create();
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    WatchListAdapter(ArrayList<Movie> myDataset, RecyclerViewClickListener listener) {
        values = myDataset;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public WatchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout_watch_list, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Movie currentMovie = values.get(position);
        holder.txtHeader.setText(currentMovie.getTitle());

        // We set the image from the URL address using Picasso
        Picasso.get().load(currentMovie.getImage()).resize(500,500).into(holder.mImageView);

        // Set the listener of the button delete
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
                values.remove(currentMovie);

            }
        });

    }


    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return values.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
    private void remove(int position) {
        //sharedPreferences=getSharedPreferences("application_movie2", Context.MODE_PRIVATE);

        values.remove(position);
        notifyItemRemoved(position);
        String jsonString = gson.toJson(values);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_MOVIE_WATCHLIST, jsonString)
                .apply();
    }
}
