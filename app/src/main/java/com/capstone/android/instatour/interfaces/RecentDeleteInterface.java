package com.capstone.android.instatour.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.capstone.android.instatour.datas.RecentData;

import java.util.List;

@Dao
public interface RecentDeleteInterface {
    void delete(RecentData data);
}
