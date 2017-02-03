package com.example.sangtran.popularmoviesv1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sang Tran on 2/2/2017.
 */

public class OverviewViewHolder extends RecyclerView.ViewHolder {

    private TextView mMovieName;
    private ImageView mMoviePoster;
    private TextView mDate;
    private TextView mVoteAvg;
    private TextView mPlotSynopsis;

    public OverviewViewHolder(View itemView) {
        super(itemView);
        mMovieName = (TextView) itemView.findViewById(R.id.movie_title);
        mMoviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);
        mDate = (TextView) itemView.findViewById(R.id.movie_date);
        mVoteAvg = (TextView) itemView.findViewById(R.id.movie_vote_average);
        mPlotSynopsis = (TextView) itemView.findViewById(R.id.movie_plot);
    }

    public TextView getMovieName() {
        return mMovieName;
    }

    public void setMovieName(TextView movieName) {
        mMovieName = movieName;
    }

    public ImageView getMoviePoster() {
        return mMoviePoster;
    }

    public void setMoviePoster(ImageView moviePoster) {
        mMoviePoster = moviePoster;
    }

    public TextView getDate() {
        return mDate;
    }

    public void setDate(TextView date) {
        mDate = date;
    }

    public TextView getVoteAvg() {
        return mVoteAvg;
    }

    public void setVoteAvg(TextView voteAvg) {
        mVoteAvg = voteAvg;
    }

    public TextView getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public void setPlotSynopsis(TextView plotSynopsis) {
        mPlotSynopsis = plotSynopsis;
    }
}
