package com.example.sangtran.popularmoviesv1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Sang Tran on 2/3/2017.
 */

public class SimpleViewHolder extends RecyclerView.ViewHolder {
    private TextView label;

    public SimpleViewHolder(View v) {
        super(v);
        label = (TextView) v.findViewById(R.id.text1);
    }

    public TextView getLabel() {
        return label;
    }

    public void setLabel(TextView label) {
        this.label = label;
    }
}
