package com.capstone.android.instatour.src.detail_posting.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostResponse {
    @SerializedName("date")
    private String date;

    @SerializedName("img_url")
    private ArrayList<String> imgList;

    @SerializedName("content")
    private String content;

    public String getDate() {
        return date;
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public String getContent() {
        return content;
    }
}
