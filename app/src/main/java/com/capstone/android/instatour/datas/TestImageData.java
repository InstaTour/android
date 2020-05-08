package com.capstone.android.instatour.datas;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TestImageData {
    @SerializedName("posts")
    private ArrayList<String> data;

    public ArrayList<String> getData() {
        return data;
    }
}
