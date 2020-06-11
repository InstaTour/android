package com.capstone.android.instatour.src.splash.models;

import com.google.gson.annotations.SerializedName;

public class DataResponse {
    @SerializedName("user")
    private UserResponse user;

    public UserResponse getUser() {
        return user;
    }
}
