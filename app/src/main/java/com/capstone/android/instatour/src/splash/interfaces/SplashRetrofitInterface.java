package com.capstone.android.instatour.src.splash.interfaces;


import com.capstone.android.instatour.src.splash.models.SplashResponse;
import com.capstone.android.instatour.src.splash.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface SplashRetrofitInterface {
    @GET("users")
    Call<SplashResponse> getUser();
}
