package com.capstone.android.instatour.src.detail_posting;


import com.capstone.android.instatour.src.detail_posting.interfaces.DetailPostingActivityView;
import com.capstone.android.instatour.src.detail_posting.interfaces.DetailPostingRetrofitInterface;
import com.capstone.android.instatour.src.detail_posting.models.TestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.capstone.android.instatour.src.ApplicationClass.getRetrofit;

class DetailPostingService {
    private final DetailPostingActivityView mDetailPostingActivityView;

    DetailPostingService(final DetailPostingActivityView detailPostingActivityView) {
        this.mDetailPostingActivityView = detailPostingActivityView;
    }

    void getTest() {
        final DetailPostingRetrofitInterface detailPostingRetrofitInterface = getRetrofit().create(DetailPostingRetrofitInterface.class);

        detailPostingRetrofitInterface.getTestPost().enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                final TestResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mDetailPostingActivityView.validateFailure(null);
                    return;
                }

                mDetailPostingActivityView.validateSuccess(defaultResponse.getData().getData());
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                mDetailPostingActivityView.validateFailure(null);
            }
        });
    }
}
