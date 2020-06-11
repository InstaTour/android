package com.capstone.android.instatour.src.main.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopReviwerResponse {
    @SerializedName("users")
    private ArrayList<UserResponse> user;

    public ArrayList<UserResponse> getUser() {
        return user;
    }
}
