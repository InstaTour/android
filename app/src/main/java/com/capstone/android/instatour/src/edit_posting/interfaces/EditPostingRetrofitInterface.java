package com.capstone.android.instatour.src.edit_posting.interfaces;


import com.capstone.android.instatour.src.edit_posting.models.BasicResponse;
import com.capstone.android.instatour.src.edit_posting.models.TestResponse;
import com.capstone.android.instatour.src.main.models.MainUserResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface EditPostingRetrofitInterface {
    @GET("posts")
    Call<TestResponse> getTestPost();

    @GET("users")
    Call<MainUserResponse> getUser();

    @POST("posts")
    Call<BasicResponse> postPosting(@Body JsonObject params);
}
