package com.capstone.android.instatour.src.main.models;

import com.google.gson.annotations.SerializedName;

public class MainTopClickResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private MainTopClickDataResponse data;

    public String getMessage() {
        return message;
    }

    public MainTopClickDataResponse getData() {
        return data;
    }

    public int getCode() {
        return code;
    }
}
