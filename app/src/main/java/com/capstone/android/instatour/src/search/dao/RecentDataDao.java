package com.capstone.android.instatour.src.search.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.capstone.android.instatour.src.search.models.RecentData;

import java.util.List;

@Dao
public interface RecentDataDao {

    @Query("SELECT * FROM RECENTDATA ORDER BY id DESC")
    LiveData<List<RecentData>> getAll();

    @Insert
    void insert(RecentData data);

    @Update
    void update(RecentData data);

    @Delete
    void delete(RecentData data);
}
