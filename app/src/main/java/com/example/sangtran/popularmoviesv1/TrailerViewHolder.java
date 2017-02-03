package com.example.sangtran.popularmoviesv1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Sang Tran on 2/3/2017.
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder {
    private TextView mTrailerName;
    private Button mPlayButton;

    public TrailerViewHolder(View itemView) {
        super(itemView);
        mTrailerName = (TextView) itemView.findViewById(R.id.trailer_name);
        mPlayButton = (Button) itemView.findViewById(R.id.trailer_play_button);
    }

    public TextView getTrailerName() {
        return mTrailerName;
    }

    public void setTrailerName(TextView trailerName) {
        mTrailerName = trailerName;
    }

    public Button getPlayButton() {
        return mPlayButton;
    }

    public void setPlayButton(Button playButton) {
        mPlayButton = playButton;
    }
}
