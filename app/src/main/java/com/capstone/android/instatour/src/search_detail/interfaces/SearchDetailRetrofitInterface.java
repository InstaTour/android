package com.capstone.android.instatour.src.search_detail.interfaces;


import com.capstone.android.instatour.src.search_detail.models.BasicResponse;
import com.capstone.android.instatour.src.search_detail.models.SearchDetailResponse;
import com.capstone.android.instatour.src.search_detail.models.TestResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface SearchDetailRetrofitInterface {
    @GET("posts")
    Call<TestResponse> getTestPost();

    @GET("search")
    Call<SearchDetailResponse> getPosts(@Query("location") String location, @Query("section") String section,
                                        @Query("skip") int skip, @Query("limit") int limit);

    @POST("posts/{id}/heart")
    Call<BasicResponse> postHeart(@Path("id") String id);

    @DELETE("posts/{id}/heart")
    Call<BasicResponse> deleteHeart(@Path("id") String id);
}
