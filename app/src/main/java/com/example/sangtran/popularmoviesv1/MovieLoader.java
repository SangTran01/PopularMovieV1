package com.example.sangtran.popularmoviesv1;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.sangtran.popularmoviesv1.data.MovieContract.MovieEntry;

import static android.os.Build.VERSION_CODES.M;
import static com.example.sangtran.popularmoviesv1.QueryUtils.fetchMovieData;

/**
 * Created by Sang Tran on 2017-01-11.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = MovieLoader.class.getName();

    //Query Url
    private String mUrl;

    private Context mContext;

    public MovieLoader(Context context, String url) {
        super(context);
        mContext = context;
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

        List<Movie> moviesList = new ArrayList<Movie>();

        //Check if Favorites is checked in settings
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        Boolean checked = preferences.getBoolean(mContext.getString(R.string.pref_favorite_key),
                Boolean.parseBoolean(mContext.getString(R.string.pref_favorite_default)));

        if (checked) {
            Log.e(LOG_TAG, "Favorite is checked");

            Cursor cursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI,
                    null, null, null, null);

            try {
                while (cursor.moveToNext()) {
                    String movieTitle = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_TITLE));
                    String movieId = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ID));
                    String movieImage = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_IMAGE));
                    int movieFavorited = cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_FAVORITE));
                    String movieDate = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_DATE));
                    String movieDesc = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_DESCRIPTION));
                    String movieVoteAvg = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_VOTE_AVG));

                    moviesList.add(new Movie(movieId, movieTitle, movieDate, movieDesc, movieImage, movieVoteAvg));
                }
            } finally {
                cursor.close();
            }
            return moviesList;
        }
        Log.e(LOG_TAG, "Favorite is NOT checked");
        moviesList = QueryUtils.fetchMovieData(mUrl);
        return moviesList;
    }
}
