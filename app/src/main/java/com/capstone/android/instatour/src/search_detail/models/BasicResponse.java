package com.capstone.android.instatour.src.search_detail.models;

import com.google.gson.annotations.SerializedName;

public class BasicResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private TestImageResponse data;

    public String getMessage() {
        return message;
    }

    public TestImageResponse getData() {
        return data;
    }

    public int getCode() {
        return code;
    }
}
