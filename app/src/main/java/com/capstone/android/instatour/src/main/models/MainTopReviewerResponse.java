package com.capstone.android.instatour.src.main.models;

import com.google.gson.annotations.SerializedName;

public class MainTopReviewerResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private TopReviwerResponse data;

    public String getMessage() {
        return message;
    }

    public TopReviwerResponse getData() {
        return data;
    }

    public int getCode() {
        return code;
    }
}
