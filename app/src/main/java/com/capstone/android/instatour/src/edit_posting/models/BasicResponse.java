package com.capstone.android.instatour.src.edit_posting.models;

import com.google.gson.annotations.SerializedName;

public class BasicResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("code")
    private int code;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
