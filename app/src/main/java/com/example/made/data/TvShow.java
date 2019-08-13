package com.example.made.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TvShow implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("poster_path")
    private String image;
    @SerializedName("backdrop_path")
    private String thumbnail;
    @SerializedName("overview")
    private String overview;
    @SerializedName("first_air_date")
    private String release_date;
    @SerializedName("episode_run_time")
    private int[] runtime;
    @SerializedName("status")
    private String status;

    public TvShow() {
    }

    protected TvShow(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.thumbnail = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.runtime = in.createIntArray();
        this.status = in.readString();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return "https://image.tmdb.org/t/p/w185/" + image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return "https://image.tmdb.org/t/p/w185/" + thumbnail;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOverview() {
        return overview;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int[] getRuntime() {
        return runtime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setRuntime(int[] runtime) {
        this.runtime = runtime;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.thumbnail);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeIntArray(this.runtime);
        dest.writeString(this.status);
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}