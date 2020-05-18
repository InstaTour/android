package com.capstone.android.instatour.src.search.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RecentData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String location;
    private String count;

    public RecentData(String location, String count) {
        this.location = location;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
