package com.capstone.android.instatour.src.search_detail.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostResponse {
    @SerializedName("rated")
    private int rated;

    @SerializedName("hearted")
    private boolean hearted;

    @SerializedName("img_url")
    private ArrayList<String> imgUrl;

    @SerializedName("id")
    private String id;

    public int getRated() {
        return rated;
    }

    public boolean isHearted() {
        return hearted;
    }

    public ArrayList<String> getImgUrl() {
        return imgUrl;
    }

    public String getId() {
        return id;
    }


}
