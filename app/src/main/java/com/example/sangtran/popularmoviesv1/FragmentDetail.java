package com.example.sangtran.popularmoviesv1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sang Tran on 2/3/2017.
 */

public class FragmentDetail extends Fragment {

    private final static String LOG_TAG = FragmentDetail.class.getSimpleName();

    private RecyclerViewAdapter mRecyclerViewAdapter;

    private static final int TRAILER_LOADER_ID = 2;
    private static final int REVIEW_LOADER_ID = 3;

    private final static String PARCE_KEY = "key";
    private final static String API_KEY = "093a106eeee012b0db5cc9b16e64950f";


    ArrayList<Object> mItems;
    private Movie mMovie;

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String TRAILERS = "trailers";
    private final static String REVIEWS = "reviews";

    private LoaderManager.LoaderCallbacks<List<Trailer>> dataTrailerLoaderListener
            = new LoaderManager.LoaderCallbacks<List<Trailer>>() {

        @Override
        public Loader<List<Trailer>> onCreateLoader(int id, Bundle args) {
            Log.e(LOG_TAG, "TRAILER onCreateLoader STARTED");
            Uri baseUri = Uri.parse(BASE_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendPath(mMovie.getId());
            uriBuilder.appendPath(TRAILERS);
            uriBuilder.appendQueryParameter("api_key", API_KEY);

            Log.e(LOG_TAG, "Built TRAILER URI " + uriBuilder.toString());

            return new TrailerLoader(getActivity(), uriBuilder.toString());
        }

        @Override
        public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> data) {
            Log.d(LOG_TAG, "TRAILER ON LOAD FINISHED STARTED");
            // Clear the adapter of previous movie data
            //mRecyclerViewAdapter.clearAdapter();
            // If there is a valid list of {@link Movie}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                //put list<Trailer> in ArrayList<Object>
                mItems.addAll(data);
                mRecyclerViewAdapter.notifyDataSetChanged();
            }

            for (int i = 0; i < mItems.size(); i++) {
                Log.e(LOG_TAG, "items are " + mItems.get(i));
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Trailer>> loader) {
            Log.d(LOG_TAG, "TRAILER ON LOAD RESET STARTED");
            // Clear the adapter of previous movie data
            mRecyclerViewAdapter.clearAdapter();
        }
    };

    //Review LoaderCallback
    private LoaderManager.LoaderCallbacks<List<Review>> dataReviewLoaderListener
            = new LoaderManager.LoaderCallbacks<List<Review>>() {

        @Override
        public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
            Log.e(LOG_TAG, "REVIEW onCreateLoader STARTED");
            Uri baseUri = Uri.parse(BASE_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendPath(mMovie.getId());
            uriBuilder.appendPath(REVIEWS);
            uriBuilder.appendQueryParameter("api_key", API_KEY);

            Log.e(LOG_TAG, "Built REVIEW URI " + uriBuilder.toString());

            return new ReviewLoader(getActivity(), uriBuilder.toString());
        }

        @Override
        public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {
            Log.d(LOG_TAG, "REVIEW ON LOAD FINISHED STARTED");
            // Clear the adapter of previous movie data
            //mReviewAdapter.clear();
            // If there is a valid list of {@link Movie}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mItems.addAll(data);
                mRecyclerViewAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Review>> loader) {
            Log.d(LOG_TAG, "REVIEW ON LOAD RESET STARTED");
            // Clear the adapter of previous movie data
            mRecyclerViewAdapter.clearAdapter();
        }
    };

    @Override
    public void onStart() {
        Log.e(LOG_TAG, "Fragement Detail on start started");
        super.onStart();
        restartLoader();
    }

    //helper method to restart Loader
    private void restartLoader() {
        //Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getActivity().getLoaderManager();
        loaderManager.restartLoader(TRAILER_LOADER_ID, null, dataTrailerLoaderListener);
        loaderManager.restartLoader(REVIEW_LOADER_ID, null, dataReviewLoaderListener);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "ON CREATE DETAIL FRAGMENT STARTED");
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            mMovie = bundle.getParcelable(PARCE_KEY);
        }

        mItems = new ArrayList<>();
        mItems.add(mMovie);

        Log.e(LOG_TAG, "Movie Id: " + mMovie.getId());
        Log.e(LOG_TAG, "Passed MOVIE IS " + mMovie);

        // Lookup the recyclerview in activity layout
        RecyclerView recyclerViewMovie = (RecyclerView) view.findViewById(R.id.rvMovie);

        // Create adapter passing in the sample user data
        mRecyclerViewAdapter = new RecyclerViewAdapter(getActivity(), mItems);
        // Attach the adapter to the recyclerview to populate items
        recyclerViewMovie.setAdapter(mRecyclerViewAdapter);
        // Set layout manager to position the items
        recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getActivity().getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(TRAILER_LOADER_ID, null, dataTrailerLoaderListener);
            loaderManager.initLoader(REVIEW_LOADER_ID, null, dataReviewLoaderListener);
        }
        // That's all!
        return view;
    }
}
