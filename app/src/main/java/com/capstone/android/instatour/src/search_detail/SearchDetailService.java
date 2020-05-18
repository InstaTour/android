package com.capstone.android.instatour.src.search_detail;


import com.capstone.android.instatour.src.search_detail.interfaces.SearchDetailActivityView;
import com.capstone.android.instatour.src.search_detail.interfaces.SearchDetailRetrofitInterface;
import com.capstone.android.instatour.src.search_detail.models.TestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.capstone.android.instatour.src.ApplicationClass.getRetrofit;

class SearchDetailService {
    private final SearchDetailActivityView mSearchDetailActivityView;

    SearchDetailService(final SearchDetailActivityView searchDetailActivityView) {
        this.mSearchDetailActivityView = searchDetailActivityView;
    }

    void getTest() {
        final SearchDetailRetrofitInterface searchDetailRetrofitInterface = getRetrofit().create(SearchDetailRetrofitInterface.class);

        searchDetailRetrofitInterface.getTestPost().enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                final TestResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mSearchDetailActivityView.validateFailure(null);
                    return;
                }

                mSearchDetailActivityView.validateSuccess(defaultResponse.getData().getData());
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                mSearchDetailActivityView.validateFailure(null);
            }
        });
    }
}
