package com.capstone.android.instatour.src.detail_posting.interfaces;


import com.capstone.android.instatour.src.detail_posting.models.DetailPostResponse;
import com.capstone.android.instatour.src.search_detail.models.BasicResponse;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface DetailPostingRetrofitInterface {
    @GET("posts/{id}")
    Call<DetailPostResponse> getDetailPost(@Path("id") String id);

    @PUT("posts/{id}/rates")
    Call<BasicResponse> putRates(@Path("id") String id, @Body JsonObject params);
}
