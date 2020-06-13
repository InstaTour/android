package com.capstone.android.instatour.src.detail_posting;


import android.util.Log;

import com.capstone.android.instatour.src.detail_posting.interfaces.DetailPostingActivityView;
import com.capstone.android.instatour.src.detail_posting.interfaces.DetailPostingRetrofitInterface;
import com.capstone.android.instatour.src.detail_posting.models.DetailPostResponse;
import com.capstone.android.instatour.src.search_detail.models.BasicResponse;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.capstone.android.instatour.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.capstone.android.instatour.src.ApplicationClass.getRetrofit;

class DetailPostingService {
    private final DetailPostingActivityView mDetailPostingActivityView;

    DetailPostingService(final DetailPostingActivityView detailPostingActivityView) {
        this.mDetailPostingActivityView = detailPostingActivityView;
    }

    void getDetailPost(String id) {
        final DetailPostingRetrofitInterface detailPostingRetrofitInterface = getRetrofit().create(DetailPostingRetrofitInterface.class);

        detailPostingRetrofitInterface.getDetailPost(id).enqueue(new Callback<DetailPostResponse>() {
            @Override
            public void onResponse(Call<DetailPostResponse> call, Response<DetailPostResponse> response) {
                final DetailPostResponse detailPostResponse = response.body();
                if (detailPostResponse == null) {
                    mDetailPostingActivityView.validateDetailPostFailure(null);
                    return;
                }

                mDetailPostingActivityView.validateDetailPostSuccess(detailPostResponse);
            }

            @Override
            public void onFailure(Call<DetailPostResponse> call, Throwable t) {
                mDetailPostingActivityView.validateDetailPostFailure(null);
            }
        });
    }

    void putStars(String id, float rate) {
        final DetailPostingRetrofitInterface detailPostingRetrofitInterface = getRetrofit().create(DetailPostingRetrofitInterface.class);

        JsonObject params = new JsonObject();
        params.addProperty("rates", (double) rate);

        detailPostingRetrofitInterface.putRates(id, params).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                final BasicResponse basicResponse = response.body();
                if (basicResponse == null) {
                    mDetailPostingActivityView.validateRateFailure(null);
                    return;
                }

                Log.i("DVSSDVsd", String.valueOf(basicResponse.getCode()));
                mDetailPostingActivityView.validateRateSuccess(basicResponse.getMessage());
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                mDetailPostingActivityView.validateRateFailure(null);
            }
        });
    }

}
