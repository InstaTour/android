package com.capstone.android.instatour.src.edit_posting.interfaces;


import com.capstone.android.instatour.src.edit_posting.models.TestResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface EditPostingRetrofitInterface {
    @GET("dev/posts")
    Call<TestResponse> getTestPost();
}
