package com.example.sangtran.popularmoviesv1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Sang Tran on 2/3/2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    private TextView mAuthorName;
    private TextView mAuthorComment;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        mAuthorName = (TextView) itemView.findViewById(R.id.review_author_name);
        mAuthorComment = (TextView) itemView.findViewById(R.id.review_comment);
    }

    public TextView getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(TextView authorName) {
        mAuthorName = authorName;
    }

    public TextView getAuthorComment() {
        return mAuthorComment;
    }

    public void setAuthorComment(TextView authorComment) {
        mAuthorComment = authorComment;
    }
}
