package com.capstone.android.instatour.src.main.interfaces;


import com.capstone.android.instatour.src.main.models.TestResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MainRetrofitInterface {
    @GET("dev/posts")
    Call<TestResponse> getTestPost();
}
