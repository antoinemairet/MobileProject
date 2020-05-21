/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.TopMovieList.presentation.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.TopMovieList.R;
import com.example.TopMovieList.presentation.model.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.ViewHolder>{

    private OnItemClickListener listener;
    private ArrayList<Movie> values;
    private Button deleteButton;

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtHeader;
        ImageView mImageView;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = v.findViewById(R.id.firstLine);
            deleteButton = v.findViewById(R.id.buttonDelete);
            mImageView = v.findViewById(R.id.icon) ;
            
        }
        
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public WatchListAdapter(ArrayList<Movie> myDataset, OnItemClickListener listener) {
 
        this.values = myDataset;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public WatchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout_watch_list, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Movie currentMovie = values.get(position);
        holder.txtHeader.setText(currentMovie.getTitle());

        // We set the image from the URL address using Picasso
        Picasso.get().load(currentMovie.getImage()).resize(500,500).into(holder.mImageView);

        // Set the listener of the button delete
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,values.size());
                listener.deleteMovie(currentMovie, values);


            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(currentMovie);
            }
        });

    }

    public interface OnItemClickListener {
        void onItemClick(Movie item);
        void deleteMovie(Movie item, ArrayList<Movie> values);
    }


    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return values.size();
    }


}
