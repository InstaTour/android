package com.capstone.android.instatour.src.splash;


import com.capstone.android.instatour.src.splash.interfaces.SplashActivityView;
import com.capstone.android.instatour.src.splash.interfaces.SplashRetrofitInterface;
import com.capstone.android.instatour.src.splash.models.TestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.capstone.android.instatour.src.ApplicationClass.getRetrofit;

class SplashDetailService {
    private final SplashActivityView mSplashActivityView;

    SplashDetailService(final SplashActivityView splashActivityView) {
        this.mSplashActivityView = splashActivityView;
    }

    void getTest() {
        final SplashRetrofitInterface splashRetrofitInterface = getRetrofit().create(SplashRetrofitInterface.class);

        splashRetrofitInterface.getTestPost().enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                final TestResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mSplashActivityView.validateFailure(null);
                    return;
                }

                mSplashActivityView.validateSuccess(defaultResponse.getData().getData());
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                mSplashActivityView.validateFailure(null);
            }
        });
    }
}
