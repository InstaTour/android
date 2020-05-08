package com.capstone.android.instatour.datas;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TestData {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private TestImageData data;

    public String getMessage() {
        return message;
    }

    public TestImageData getData() {
        return data;
    }
}
