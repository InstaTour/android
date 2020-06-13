package com.capstone.android.instatour.src.search_detail;


import android.util.Log;

import com.capstone.android.instatour.src.search_detail.interfaces.SearchDetailActivityView;
import com.capstone.android.instatour.src.search_detail.interfaces.SearchDetailRetrofitInterface;
import com.capstone.android.instatour.src.search_detail.models.BasicResponse;
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
                    Log.i("SDVSDV", "SDVsdv");
                    Log.i("SDVSDV", response.message());
                    mSearchDetailActivityView.validateFailure(null);
                    return;
                }

                mSearchDetailActivityView.validateSuccess(defaultResponse);
            }

            @Override
            public void onFailure(Call<SearchDetailResponse> call, Throwable t) {
                Log.i("SDVsdv", t.getMessage());
                mSearchDetailActivityView.validateFailure(null);
            }
        });
    }

    void postHeart(String id) {
        final SearchDetailRetrofitInterface searchDetailRetrofitInterface = getRetrofit().create(SearchDetailRetrofitInterface.class);

        searchDetailRetrofitInterface.postHeart(id).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                final BasicResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    Log.i("SDVSDV", "SDVsdv");
                    Log.i("sadvsadvads", response.message());
                    mSearchDetailActivityView.validateHeartFailure(null);
                    return;
                }

                mSearchDetailActivityView.validateHeartSuccess(response.message());
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                Log.i("SVDsDV", t.getMessage());
                mSearchDetailActivityView.validateHeartFailure(null);
            }
        });
    }

    void deleteHeart(String id) {
        final SearchDetailRetrofitInterface searchDetailRetrofitInterface = getRetrofit().create(SearchDetailRetrofitInterface.class);

        searchDetailRetrofitInterface.deleteHeart(id).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                final BasicResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mSearchDetailActivityView.validateHeartFailure(null);
                    return;
                }

                mSearchDetailActivityView.validateHeartSuccess(response.message());
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                mSearchDetailActivityView.validateHeartFailure(null);
            }
        });
    }
}
