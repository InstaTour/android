package com.capstone.android.instatour.src.main.models;

import com.google.gson.annotations.SerializedName;

public class MainUserResponse {
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
