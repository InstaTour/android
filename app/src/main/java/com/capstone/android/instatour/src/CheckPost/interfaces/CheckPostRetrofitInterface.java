package com.capstone.android.instatour.src.CheckPost.interfaces;


import com.capstone.android.instatour.src.CheckPost.models.CheckResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface CheckPostRetrofitInterface {
    @GET("users/{id}")
    Call<CheckResponse> getPost(@Path("id") String id);

}
