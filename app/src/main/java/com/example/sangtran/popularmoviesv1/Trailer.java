package com.example.sangtran.popularmoviesv1;

import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.name;

/**
 * Created by Sang Tran on 1/31/2017.
 */

public class Trailer implements Parcelable {
    private String mName;
    private String mSite;

    public Trailer(String name, String site) {
        mName = name;
        mSite = site;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        mSite = site;
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "mName='" + mName + '\'' +
                ", mSite='" + mSite + '\'' +
                '}';
    }

    protected Trailer(Parcel in) {
        mName = in.readString();
        mSite = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mSite);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}