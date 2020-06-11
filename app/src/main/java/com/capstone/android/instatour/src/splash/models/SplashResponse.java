package com.capstone.android.instatour.src.splash.models;

import com.google.gson.annotations.SerializedName;

public class SplashResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private DataResponse data;

    public String getMessage() {
        return message;
    }

    public DataResponse getData() {
        return data;
    }

    public int getCode() {
        return code;
    }
}
