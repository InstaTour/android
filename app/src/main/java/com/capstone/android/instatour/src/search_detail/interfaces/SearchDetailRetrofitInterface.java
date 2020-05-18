package com.capstone.android.instatour.src.search_detail.interfaces;


import com.capstone.android.instatour.src.search_detail.models.TestResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface SearchDetailRetrofitInterface {
    @GET("dev/posts")
    Call<TestResponse> getTestPost();
}
