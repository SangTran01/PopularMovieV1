package com.example.sangtran.popularmoviesv1;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.net.URL;
import java.util.List;

import static com.example.sangtran.popularmoviesv1.QueryUtils.fetchMovieData;

/**
 * Created by Sang Tran on 2017-01-11.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    /** Tag for log messages */
    private static final String LOG_TAG = MovieLoader.class.getName();

    //Query Url
    private String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    //This method triggers the loadInBackground method!
    @Override
    protected void onStartLoading() {
        Log.d(LOG_TAG, "CALLED FROM movieloader.java on start loading");
        forceLoad();
    }


    @Override
    public List<Movie> loadInBackground() {
        Log.d(LOG_TAG, "CALLED FROM move loader.java load in background");
        if (mUrl == null) {
            return null;
        }

        List<Movie> moviesList = QueryUtils.fetchMovieData(mUrl);
        return moviesList;
    }
}
