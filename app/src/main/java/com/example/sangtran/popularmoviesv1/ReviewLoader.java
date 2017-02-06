package com.example.sangtran.popularmoviesv1;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Sang Tran on 2/1/2017.
 */

public class ReviewLoader extends AsyncTaskLoader<List<Review>>{

    private final String LOG_TAG = ReviewLoader.class.getSimpleName();
    private String mUrl;

    public ReviewLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    //This method triggers the loadInBackground method!
    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<Review> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Review> reviewList = QueryUtils.fetchReviewData(mUrl);
        return reviewList;
    }
}
