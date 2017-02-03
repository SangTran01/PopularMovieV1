package com.example.sangtran.popularmoviesv1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    /**
     * Constant value for the movie loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int MOVIE_LOADER_ID = 1;

    private MovieAdapter mMovieAdapter;

    private final static String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String POPULAR = "popular";
    private final static String TOP_RATED = "top_rated";
    //TODO remove my API KEY
    private final static String API_KEY = "093a106eeee012b0db5cc9b16e64950f";

    private final static String PARCE_KEY = "key";

    //Remove implement to declare its own variable
    private LoaderManager.LoaderCallbacks<List<Movie>> dataMovieLoaderListener
            = new LoaderManager.LoaderCallbacks<List<Movie>>() {
        @Override
        public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
            Log.e(LOG_TAG, "MOVIE onCreateLoader STARTED");
            Uri baseUri = Uri.parse(MOVIE_BASE_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();

            //Use SharedPrefs here
            //get sortOrder pref and join to uri path
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String sortOrder = prefs.getString(getString(R.string.pref_movie_key),
                    getString(R.string.pref_movie_default));

            uriBuilder.appendPath(sortOrder);
            uriBuilder.appendQueryParameter("api_key", API_KEY);

            Log.e(LOG_TAG, "Built URI " + uriBuilder.toString());

            return new MovieLoader(getApplicationContext(), uriBuilder.toString());
        }

        @Override
        public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
            Log.d(LOG_TAG, "ON LOAD FINISHED STARTED");
            // Clear the adapter of previous movie data
            mMovieAdapter.clear();
            // If there is a valid list of {@link Movie}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mMovieAdapter.addAll(data);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Movie>> loader) {
            Log.d(LOG_TAG, "ON LOAD RESET STARTED");
            // Clear the adapter of previous movie data
            mMovieAdapter.clear();
        }
    };
    //helper method to restart Loader
    private void restartLoader() {
        //Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(MOVIE_LOADER_ID, null, dataMovieLoaderListener);
    }

    @Override
    protected void onStart() {
        Log.e(LOG_TAG, "ON START STARTED");
        super.onStart();
        //call do in background again
        restartLoader();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "ON CREATE STARTED");

        // Find a reference to the {@link ListView} in the layout
        final GridView gridView = (GridView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mMovieAdapter = new MovieAdapter(this, new ArrayList<Movie>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        gridView.setAdapter(mMovieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get currentMovie
                Movie currentMovie = (Movie) gridView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(PARCE_KEY, currentMovie);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MOVIE_LOADER_ID, null, dataMovieLoaderListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //inflate the settings menu in detail view
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
