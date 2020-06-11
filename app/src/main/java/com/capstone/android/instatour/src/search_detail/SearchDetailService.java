package com.capstone.android.instatour.src.search_detail;


import com.capstone.android.instatour.src.search_detail.interfaces.SearchDetailActivityView;
import com.capstone.android.instatour.src.search_detail.interfaces.SearchDetailRetrofitInterface;
import com.capstone.android.instatour.src.search_detail.models.SearchDetailResponse;
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


    void getSearch(String location, String section, int skip, int limit) {
        final SearchDetailRetrofitInterface searchDetailRetrofitInterface = getRetrofit().create(SearchDetailRetrofitInterface.class);

        searchDetailRetrofitInterface.getPosts(location, section, skip, limit).enqueue(new Callback<SearchDetailResponse>() {
            @Override
            public void onResponse(Call<SearchDetailResponse> call, Response<SearchDetailResponse> response) {
                final SearchDetailResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mSearchDetailActivityView.validateFailure(null);
                    return;
                }

                mSearchDetailActivityView.validateSuccess(defaultResponse);
            }

            @Override
            public void onFailure(Call<SearchDetailResponse> call, Throwable t) {
                mSearchDetailActivityView.validateFailure(null);
            }
        });
    }
}
