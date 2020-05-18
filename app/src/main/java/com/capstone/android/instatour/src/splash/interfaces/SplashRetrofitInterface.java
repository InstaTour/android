package com.capstone.android.instatour.src.splash.interfaces;


import com.capstone.android.instatour.src.splash.models.TestResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface SplashRetrofitInterface {
    @GET("dev/posts")
    Call<TestResponse> getTestPost();
}
