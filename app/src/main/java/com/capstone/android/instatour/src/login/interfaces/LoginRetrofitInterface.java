package com.capstone.android.instatour.src.login.interfaces;


import com.capstone.android.instatour.src.login.models.TestResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface LoginRetrofitInterface {
    @GET("dev/posts")
    Call<TestResponse> getTestPost();
}
