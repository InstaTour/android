package com.capstone.android.instatour.datas;

import com.google.gson.annotations.SerializedName;

public class RecentData {
    private String location;
    private String count;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
