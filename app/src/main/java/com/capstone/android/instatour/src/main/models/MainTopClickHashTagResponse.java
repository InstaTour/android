package com.capstone.android.instatour.src.main.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainTopClickHashTagResponse {
    @SerializedName("img_url")
    private String img_url;

    @SerializedName("views")
    private int views;

    @SerializedName("id")
    private String id;

    @SerializedName("relatives")
    private ArrayList<String> relatives;

    @SerializedName("apx_num")
    private int apx_num;

    public String getImg_url() {
        return img_url;
    }

    public int getViews() {
        return views;
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getRelatives() {
        return relatives;
    }

    public int getApx_num() {
        return apx_num;
    }
}
