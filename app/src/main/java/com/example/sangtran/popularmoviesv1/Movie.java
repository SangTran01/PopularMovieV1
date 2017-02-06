package com.example.sangtran.popularmoviesv1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sang Tran on 2017-01-11.
 */

public class Movie implements Parcelable {
    private String mId;
    private String mTitle;
    private String mDate;
    private String mOverview;
    private String mImagePath;
    private String mVoteAvg;
    private int mHasFavorite;

    public Movie(String id, String title, String date, String overview, String imagePath, String voteAvg) {
        mId = id;
        mTitle = title;
        mDate = date;
        mOverview = overview;
        mImagePath = imagePath;
        mVoteAvg = voteAvg;
        mHasFavorite = 0;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public String getVoteAvg() {
        return mVoteAvg;
    }

    public void setVoteAvg(String voteAvg) {
        mVoteAvg = voteAvg;
    }

    public int getHasFavorite() {
        return mHasFavorite;
    }

    public void setHasFavorite(int hasFavorite) {
        mHasFavorite = hasFavorite;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "mId='" + mId + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mDate='" + mDate + '\'' +
                ", mOverview='" + mOverview + '\'' +
                ", mImagePath='" + mImagePath + '\'' +
                ", mVoteAvg='" + mVoteAvg + '\'' +
                ", mHasFavorite=" + mHasFavorite +
                '}';
    }



    protected Movie(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mDate = in.readString();
        mOverview = in.readString();
        mImagePath = in.readString();
        mVoteAvg = in.readString();
        mHasFavorite = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mDate);
        dest.writeString(mOverview);
        dest.writeString(mImagePath);
        dest.writeString(mVoteAvg);
        dest.writeInt(mHasFavorite);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}