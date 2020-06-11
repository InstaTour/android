package com.capstone.android.instatour.src.signup.interfaces;


import com.capstone.android.instatour.src.signup.models.TestResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface SignupRetrofitInterface {
    @GET("posts")
    Call<TestResponse> getTestPost();
}
