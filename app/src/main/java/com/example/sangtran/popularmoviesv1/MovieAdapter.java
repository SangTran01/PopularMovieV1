package com.example.sangtran.popularmoviesv1;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Sang Tran on 2017-01-11.
 */

public class MovieAdapter extends ArrayAdapter<Movie>{
    private final static String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View listItemView, ViewGroup parent) {

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Movie currentMovie = getItem(position);
        String imagePath = "";

        if (currentMovie.getImagePath() != null) {
            imagePath = currentMovie.getImagePath();
        }

        ImageView moviePosterImageView = (ImageView) listItemView.findViewById(R.id.item_imageview);
        //Picasso to easily load album art thumbnails into your views
        //Picasso will handle loading the images on a background thread,
        //image decompression and caching the images.
        Picasso.with(parent.getContext()).load(imagePath).into(moviePosterImageView);

        return listItemView;
    }


}
