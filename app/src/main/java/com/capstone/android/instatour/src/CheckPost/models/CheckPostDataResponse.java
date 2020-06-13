package com.capstone.android.instatour.src.CheckPost.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CheckPostDataResponse {
    @SerializedName("img_url")
    private ArrayList<String> imgUrl;

    @SerializedName("id")
    private String id;

    public ArrayList<String> getImgUrl() {
        return imgUrl;
    }

    public String getId() {
        return id;
    }
}
