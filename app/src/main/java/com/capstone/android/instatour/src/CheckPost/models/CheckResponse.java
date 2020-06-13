package com.capstone.android.instatour.src.CheckPost.models;

import com.google.gson.annotations.SerializedName;

public class CheckResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;


    @SerializedName("data")
    private CheckPostsResponse data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public CheckPostsResponse getData() {
        return data;
    }
}