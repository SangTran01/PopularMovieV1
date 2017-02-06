package com.example.sangtran.popularmoviesv1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sangtran.popularmoviesv1.data.MovieContract.MovieEntry;
import static android.R.attr.version;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Sang Tran on 2/4/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL," +
                MovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL," +
                MovieEntry.COLUMN_MOVIE_DATE + " TEXT NOT NULL," +
                MovieEntry.COLUMN_MOVIE_DESCRIPTION + " TEXT NOT NULL," +
                MovieEntry.COLUMN_MOVIE_IMAGE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_VOTE_AVG + " TEXT NOT NULL," +
                MovieEntry.COLUMN_MOVIE_FAVORITE + " INTEGER NOT NULL DEFAULT 0" +
                " );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
