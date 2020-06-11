package com.capstone.android.instatour.src.main.interfaces;


import com.capstone.android.instatour.src.main.models.MainTopClickResponse;
import com.capstone.android.instatour.src.main.models.MainTopReviewerResponse;
import com.capstone.android.instatour.src.main.models.MainUserResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface MainRetrofitInterface {
    @GET("users")
    Call<MainUserResponse> getUser();

    @GET("stats/posting")
    Call<MainTopReviewerResponse> gerTopReviewr();

    @GET("stats/click?date=30&skip=0&limit=5")
    Call<MainTopClickResponse> getTopMonthClick();

    @GET("stats/click?date=7&skip=0&limit=5")
    Call<MainTopClickResponse> getTopWeekClick();
}
