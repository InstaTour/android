package com.capstone.android.instatour.src.main;


import com.capstone.android.instatour.src.main.interfaces.MainActivityView;
import com.capstone.android.instatour.src.main.interfaces.MainRetrofitInterface;
import com.capstone.android.instatour.src.main.models.MainTopClickResponse;
import com.capstone.android.instatour.src.main.models.MainTopReviewerResponse;
import com.capstone.android.instatour.src.main.models.MainUserResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.capstone.android.instatour.src.ApplicationClass.getRetrofit;

class MainService {
    private final MainActivityView mMainActivityView;

    MainService(final MainActivityView mainActivityView) {
        this.mMainActivityView = mainActivityView;
    }

    void getUser() {
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);

        mainRetrofitInterface.getUser().enqueue(new Callback<MainUserResponse>() {
            @Override
            public void onResponse(Call<MainUserResponse> call, Response<MainUserResponse> response) {
                final MainUserResponse mainUserResponse = response.body();
                if (mainUserResponse == null) {
                    mMainActivityView.validateUserFailure(null);
                    return;
                }

                mMainActivityView.validateUserSuccess(mainUserResponse);
            }

            @Override
            public void onFailure(Call<MainUserResponse> call, Throwable t) {
                mMainActivityView.validateUserFailure(null);
            }
        });
    }

    void getTopReviwer() {
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);

        mainRetrofitInterface.gerTopReviewr().enqueue(new Callback<MainTopReviewerResponse>() {
            @Override
            public void onResponse(Call<MainTopReviewerResponse> call, Response<MainTopReviewerResponse> response) {
                final MainTopReviewerResponse mainTopReviewerResponse = response.body();

                if (mainTopReviewerResponse == null) {
                    mMainActivityView.validateTopReviwerFailure(null);
                    return;
                }

                mMainActivityView.validateTopReviwerSuccess(mainTopReviewerResponse);
            }

            @Override
            public void onFailure(Call<MainTopReviewerResponse> call, Throwable t) {
                mMainActivityView.validateTopReviwerFailure(null);
            }
        });
    }

    void getTopMonth() {
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);

        mainRetrofitInterface.getTopMonthClick().enqueue(new Callback<MainTopClickResponse>() {
            @Override
            public void onResponse(Call<MainTopClickResponse> call, Response<MainTopClickResponse> response) {
                final MainTopClickResponse mainTopClickResponse = response.body();

                if (mainTopClickResponse == null) {
                    mMainActivityView.validateTopClickMonthFailure(null);
                    return;
                }

                mMainActivityView.validateTopClickMonthSuccess(mainTopClickResponse);
            }

            @Override
            public void onFailure(Call<MainTopClickResponse> call, Throwable t) {
                mMainActivityView.validateTopClickMonthSuccess(null);
            }
        });
    }

    void getTopWeek() {
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);

        mainRetrofitInterface.getTopWeekClick().enqueue(new Callback<MainTopClickResponse>() {
            @Override
            public void onResponse(Call<MainTopClickResponse> call, Response<MainTopClickResponse> response) {
                final MainTopClickResponse mainTopClickResponse = response.body();

                if (mainTopClickResponse == null) {
                    mMainActivityView.validateTopClickWeekFailure(null);
                    return;
                }

                mMainActivityView.validateTopClickWeekSuccess(mainTopClickResponse);
            }

            @Override
            public void onFailure(Call<MainTopClickResponse> call, Throwable t) {
                mMainActivityView.validateTopClickWeekFailure(null);
            }
        });
    }
}
