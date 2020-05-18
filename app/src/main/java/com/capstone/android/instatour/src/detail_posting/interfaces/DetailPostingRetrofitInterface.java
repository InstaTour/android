package com.capstone.android.instatour.src.detail_posting.interfaces;


import com.capstone.android.instatour.src.detail_posting.models.TestResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface DetailPostingRetrofitInterface {
    @GET("dev/posts")
    Call<TestResponse> getTestPost();
}
