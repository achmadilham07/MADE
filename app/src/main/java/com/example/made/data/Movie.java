package com.example.made.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
    @SerializedName("title")
    private String name;

    @SerializedName("poster_path")
    private String image;

    @SerializedName("backdrop_path")
    private String thumbnail;

    @SerializedName("overview")
    private String overview;

    public String getName() {
        return name;
    }

    public String getImage() {
        return "https://image.tmdb.org/t/p/w185/" + image;
    }

    public String getThumbnail() {
        return "https://image.tmdb.org/t/p/w185/" + thumbnail;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.thumbnail);
        dest.writeString(this.overview);
    }

    public Movie() {
    }

    private Movie(Parcel in) {
        this.name = in.readString();
        this.image = in.readString();
        this.thumbnail = in.readString();
        this.overview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}