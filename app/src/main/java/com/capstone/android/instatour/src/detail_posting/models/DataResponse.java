package com.capstone.android.instatour.src.detail_posting.models;

import com.google.gson.annotations.SerializedName;

public class DataResponse {
    @SerializedName("post")
    private PostResponse post;

    @SerializedName("writer")
    private WriterResponse writer;

    @SerializedName("avg_rates")
    private double avgRates;

    @SerializedName("reviews")
    private int reviews;

    public PostResponse getPost() {
        return post;
    }

    public WriterResponse getWriter() {
        return writer;
    }

    public double getAvgRates() {
        return avgRates;
    }

    public int getReviews() {
        return reviews;
    }
}
