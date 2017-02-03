package com.example.sangtran.popularmoviesv1;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import static java.security.AccessController.getContext;
import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Sang Tran on 2/2/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
                OverviewViewHolder vh1 = (OverviewViewHolder) viewHolder;
                Movie movie = (Movie) items.get(position);
                vh1.getMovieName().setText(movie.getTitle());

                //Picasso to easily load album art thumbnails into your views
                //Picasso will handle loading the images on a background thread,
                //image decompression and caching the images.
                Picasso.with(mContext).load(movie.getImagePath()).into(vh1.getMoviePoster());

                vh1.getDate().setText(movie.getDate());
                vh1.getVoteAvg().setText(movie.getVoteAvg());
                vh1.getPlotSynopsis().setText(movie.getOverview());
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
