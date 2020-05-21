/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.TopMovieList.presentation.view;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import com.example.TopMovieList.presentation.model.Movie;
import com.example.TopMovieList.R;
import com.squareup.picasso.Picasso;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>  {

    private ArrayList<Movie> values;
    private OnItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        TextView txtHeader;
        TextView txtFooter;
        ImageView mImageView;
        View layout;

        ViewHolder(View v) {

            super(v);
            layout = v;
            txtHeader = v.findViewById(R.id.firstLine);
            txtFooter = v.findViewById(R.id.secondLine);
            mImageView = v.findViewById(R.id.icon) ;

        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(ArrayList<Movie> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // create a new view
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //Get the current movie
        final Movie currentMovie = values.get(position);

        //We set its title and its rank
        holder.txtHeader.setText(currentMovie.getTitle());
        holder.txtFooter.setText("Rank : "+currentMovie.getRank());

        // We set the image from the URL address using Picasso
        Picasso.get().load(currentMovie.getImage()).resize(500,500).into(holder.mImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(currentMovie);
            }
        });

    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return values.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Movie item);
    }

}
