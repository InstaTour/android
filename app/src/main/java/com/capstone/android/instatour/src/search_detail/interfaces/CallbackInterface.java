package com.capstone.android.instatour.src.search_detail.interfaces;


import com.capstone.android.instatour.src.search_detail.models.SearchDetailResponse;
import com.capstone.android.instatour.src.search_detail.models.TestResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface CallbackInterface {
    void click(String id);
    void heart(String id, boolean select);
}
