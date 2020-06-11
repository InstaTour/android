package com.capstone.android.instatour.src.detail_posting;


import com.capstone.android.instatour.src.detail_posting.interfaces.DetailPostingActivityView;
import com.capstone.android.instatour.src.detail_posting.interfaces.DetailPostingRetrofitInterface;
import com.capstone.android.instatour.src.detail_posting.models.DetailPostResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
}
