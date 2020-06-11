package com.capstone.android.instatour.src.search_detail.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchDetailResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;


    @SerializedName("data")
    private SearchDetailDataResponse data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public SearchDetailDataResponse getData() {
        return data;
    }
}
