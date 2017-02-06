package com.example.sangtran.popularmoviesv1;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static java.security.AccessController.getContext;
import static android.support.v4.content.ContextCompat.startActivity;

import com.example.sangtran.popularmoviesv1.data.MovieContract.MovieEntry;

/**
 * Created by Sang Tran on 2/2/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String LOG_TAG = RecyclerViewAdapter.class.getSimpleName();

    private List<Object> items;

    private final int OVERVIEW = 0, TRAILER = 1, REVIEW = 2;

    private Context mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(Context context, List<Object> items) {
        this.mContext = context;
        this.items = items;
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Movie) {
            return OVERVIEW;
        } else if (items.get(position) instanceof Trailer) {
            return TRAILER;
        } else if (items.get(position) instanceof Review) {
            return REVIEW;
        }
        return -1;
    }

    /**
     * This method creates different RecyclerView.ViewHolder objects based on the item view type.\
     *
     * @param viewGroup ViewGroup container for the item
     * @param viewType  type of view to be inflated
     * @return viewHolder to be inflated
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case OVERVIEW:
                View v1 = inflater.inflate(R.layout.overview_viewholder, viewGroup, false);
                viewHolder = new OverviewViewHolder(v1);
                break;
            case TRAILER:
                View v2 = inflater.inflate(R.layout.trailer_viewholder, viewGroup, false);
                viewHolder = new TrailerViewHolder(v2);
                break;
            case REVIEW:
                View v3 = inflater.inflate(R.layout.review_viewholder, viewGroup, false);
                viewHolder = new ReviewViewHolder(v3);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new SimpleViewHolder(v);
                break;
        }
        return viewHolder;
    }

    /**
     * This method internally calls onBindViewHolder(ViewHolder, int) to update the
     * RecyclerView.ViewHolder contents with the item at the given position
     * and also sets up some private fields to be used by RecyclerView.
     *
     * @param viewHolder The type of RecyclerView.ViewHolder to populate
     * @param position   Item position in the viewgroup.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case OVERVIEW:
                final OverviewViewHolder vh1 = (OverviewViewHolder) viewHolder;
                final Movie movie = (Movie) items.get(position);
                vh1.getMovieName().setText(movie.getTitle());

                //Picasso to easily load album art thumbnails into your views
                //Picasso will handle loading the images on a background thread,
                //image decompression and caching the images.
                Picasso.with(mContext).load(movie.getImagePath()).resize(300,450).into(vh1.getMoviePoster());

                vh1.getDate().setText(movie.getDate());
                vh1.getVoteAvg().setText(movie.getVoteAvg());
                vh1.getPlotSynopsis().setText(movie.getOverview());

                int movieFavorited = 0;


                Cursor cursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI,
                        null,
                        MovieEntry.COLUMN_MOVIE_ID + " =?",
                        new String[]{movie.getId()}, null);

                try {
                    while (cursor.moveToNext()) {
                        movieFavorited = cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_FAVORITE));
                        Log.d(LOG_TAG, "HAS FAVORITE IN DB IS? " + movieFavorited);
                    }
                } finally {
                    cursor.close();
                }

                if (movieFavorited == 1) {
                    vh1.getAddFavorite().setText("Unfavorite \nMovie");
                } else {
                    vh1.getAddFavorite().setText("Favorite \nMovie");
                }

                final int finalMovieFavorited = movieFavorited;

                vh1.getAddFavorite().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //set Favorite as true or 1 when clicked
                        //check if favorite or not
                        if (finalMovieFavorited == 0) {
                            movie.setHasFavorite(1);

                            ContentValues values = new ContentValues();
                            values.put(MovieEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
                            values.put(MovieEntry.COLUMN_MOVIE_FAVORITE, movie.getHasFavorite());
                            values.put(MovieEntry.COLUMN_MOVIE_VOTE_AVG, movie.getVoteAvg());
                            values.put(MovieEntry.COLUMN_MOVIE_IMAGE, movie.getImagePath());
                            values.put(MovieEntry.COLUMN_MOVIE_DATE, movie.getDate());
                            values.put(MovieEntry.COLUMN_MOVIE_DESCRIPTION, movie.getOverview());
                            values.put(MovieEntry.COLUMN_MOVIE_ID, movie.getId());

                            Uri uri = mContext.getContentResolver().insert(
                                    MovieEntry.CONTENT_URI, values);
                            vh1.getAddFavorite().setText("Unfavorite \nMovie");

                            Toast.makeText(mContext, "Movie favorited",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            movie.setHasFavorite(0);
                            int rowsDeleted = mContext.getContentResolver().delete(
                                    MovieEntry.CONTENT_URI,
                                    MovieEntry.COLUMN_MOVIE_ID + " =?",
                                    new String[]{movie.getId()});

                            if (rowsDeleted != 0) {
                                vh1.getAddFavorite().setText("Favorite \nMovie");
                                Toast.makeText(mContext, "Movie unfavorited", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                break;
            case TRAILER:
                TrailerViewHolder vh2 = (TrailerViewHolder) viewHolder;
                final Trailer trailer = (Trailer) items.get(position);
                vh2.getTrailerName().setText(trailer.getName());
                vh2.getPlayButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("Trailer Adapter", "Button clicked");
                        Log.e("Clicked URL Id", trailer.getSite());
                        watchYoutubeVideo(trailer.getSite());
                    }
                });
                break;
            case REVIEW:
                ReviewViewHolder vh3 = (ReviewViewHolder) viewHolder;
                Review review = (Review) items.get(position);
                vh3.getAuthorName().setText(review.getAuthor());
                vh3.getAuthorComment().setText(review.getContent());
                break;
            default:
                SimpleViewHolder vh = (SimpleViewHolder) viewHolder;
                vh.getLabel().setText((CharSequence) items.get(position));
                break;
        }
    }

    //helper method to clear adapter
    public void clearAdapter() {
        int size = this.items.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.items.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    //Helper method to open Youtube app onItemClick
    public void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(mContext, appIntent, null);
        } catch (ActivityNotFoundException ex) {
            startActivity(mContext, webIntent, null);
        }
    }
}
