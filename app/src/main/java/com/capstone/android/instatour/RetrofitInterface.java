package com.capstone.android.instatour;

import com.capstone.android.instatour.datas.TestData;
import com.capstone.android.instatour.responses.BasicResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RetrofitInterface {

    @GET("dev/posts")
    Observable<TestData> getTestPost();
}
