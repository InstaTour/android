package com.capstone.android.instatour.src.main.models;

import com.google.gson.annotations.SerializedName;

public class TestResponse {
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
}
