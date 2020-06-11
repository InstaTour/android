package com.capstone.android.instatour.src.main.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainTopClickDataResponse {
    @SerializedName("hashtags")
    private ArrayList<MainTopClickHashTagResponse> hashtags;

    public ArrayList<MainTopClickHashTagResponse> getHashtags() {
        return hashtags;
    }
}
