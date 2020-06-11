package com.capstone.android.instatour.src.search_detail.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchDetailDataResponse {
    @SerializedName("posts")
    private ArrayList<PostResponse> posts;

    @SerializedName("num")
    private int num;

    @SerializedName("apx_num")
    private int apx_num;

    @SerializedName("relatives")
    private ArrayList<String> relatives;

    public ArrayList<PostResponse> getPosts() {
        return posts;
    }

    public int getNum() {
        return num;
    }

    public int getApx_num() {
        return apx_num;
    }

    public ArrayList<String> getRelatives() {
        return relatives;
    }
}
