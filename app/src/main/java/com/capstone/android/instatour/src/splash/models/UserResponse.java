package com.capstone.android.instatour.src.splash.models;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("profile")
    String profile;

    @SerializedName("nickname")
    String nickname;

    @SerializedName("posting")
    int posting;

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
