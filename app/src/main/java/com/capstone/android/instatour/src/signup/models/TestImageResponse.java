package com.capstone.android.instatour.src.signup.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TestImageResponse {
    @SerializedName("posts")
    private ArrayList<String> data;

    public ArrayList<String> getData() {
        return data;
    }
}
