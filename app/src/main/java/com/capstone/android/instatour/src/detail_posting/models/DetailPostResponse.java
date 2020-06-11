package com.capstone.android.instatour.src.detail_posting.models;

import com.google.gson.annotations.SerializedName;

public class DetailPostResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;


    @SerializedName("data")
    private DataResponse data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public DataResponse getData() {
        return data;
    }
}
