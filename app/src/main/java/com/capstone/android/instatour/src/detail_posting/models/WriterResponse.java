package com.capstone.android.instatour.src.detail_posting.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WriterResponse {
    @SerializedName("profile")
    private String profile;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("posting")
    private int posting;

    public String getProfile() {
        return profile;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPosting() {
        return posting;
    }
}
