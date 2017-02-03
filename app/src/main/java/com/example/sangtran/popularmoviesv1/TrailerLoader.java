package com.example.sangtran.popularmoviesv1;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Sang Tran on 1/31/2017.
 */

public class TrailerLoader extends AsyncTaskLoader<List<Trailer>> {
    /** Tag for log messages */
    private static final String LOG_TAG = MovieLoader.class.getName();

    //Query Url
    private String mUrl;

    public TrailerLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    //This method triggers the loadInBackground method!
    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<Trailer> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Trailer> trailerList = QueryUtils.fetchTrailerData(mUrl);
        return trailerList;
    }
}
