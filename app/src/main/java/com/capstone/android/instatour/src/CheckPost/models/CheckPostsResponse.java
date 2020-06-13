package com.capstone.android.instatour.src.CheckPost.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CheckPostsResponse {
    @SerializedName("posts")
    private ArrayList<CheckPostDataResponse> posts;

    public ArrayList<CheckPostDataResponse> getPosts() {
        return posts;
    }
}
