package com.example.sangtran.popularmoviesv1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_detail, new FragmentDetail())
                .commit();

//        mTrailerAdapter = new TrailerAdapter(this, new ArrayList<Trailer>());
//
//        //Find trailers listview
//        ListView trailerListView = (ListView) findViewById(R.id.listview_trailers);
//        ViewGroup trailerHeader = (ViewGroup) getLayoutInflater().inflate(R.layout.listview_trailer_header, trailerListView, false);
//
//        //setup Header Views
//        //find views
//        TextView titleTextView = (TextView) trailerHeader.findViewById(R.id.movie_title);
//        TextView dateTextView = (TextView) trailerHeader.findViewById(R.id.movie_date);
//        TextView voteAvgTextView = (TextView) trailerHeader.findViewById(R.id.movie_vote_average);
//        TextView plotTextView = (TextView) trailerHeader.findViewById(R.id.movie_plot);
//        ImageView posterImageView = (ImageView) trailerHeader.findViewById(R.id.movie_poster);
//
//        titleTextView.setText(mMovie.getTitle());
//        dateTextView.setText(mMovie.getDate());
//        voteAvgTextView.setText(mMovie.getVoteAvg());
//        plotTextView.setText(mMovie.getOverview());
//
//        //Picasso to easily load album art thumbnails into your views
//        //Picasso will handle loading the images on a background thread,
//        //image decompression and caching the images.
//        Picasso.with(DetailActivity.this).load(mMovie.getImagePath()).into(posterImageView);
//
//        trailerListView.addHeaderView(trailerHeader);
//        trailerListView.setAdapter(mTrailerAdapter);
//
//        //REVIEW PORTION
//        mReviewAdapter = new ReviewAdapter(this, new ArrayList<Review>());
//
//        ListView reviewListView = (ListView) findViewById(R.id.listview_reviews);
//        ViewGroup reviewHeader = (ViewGroup) getLayoutInflater().inflate(R.layout.listview_reviews_header, reviewListView, false);
//
//        reviewListView.addHeaderView(reviewHeader);
//        reviewListView.setAdapter(mReviewAdapter);
    }
}
