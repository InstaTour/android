package com.capstone.android.instatour.src.detail_posting.interfaces;


import com.capstone.android.instatour.src.detail_posting.models.DetailPostResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface DetailPostingRetrofitInterface {
    @GET("posts/{id}")
    Call<DetailPostResponse> getDetailPost(@Path("id") String id);
}
