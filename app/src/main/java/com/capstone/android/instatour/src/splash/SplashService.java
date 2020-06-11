package com.capstone.android.instatour.src.splash;


import com.capstone.android.instatour.src.splash.interfaces.SplashActivityView;
import com.capstone.android.instatour.src.splash.interfaces.SplashRetrofitInterface;
import com.capstone.android.instatour.src.splash.models.SplashResponse;
import com.capstone.android.instatour.src.splash.models.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.capstone.android.instatour.src.ApplicationClass.getRetrofit;

class SplashService {
    private final SplashActivityView mSplashActivityView;

    SplashService(final SplashActivityView splashActivityView) {
        this.mSplashActivityView = splashActivityView;
    }

    void getTokenUser() {
        final SplashRetrofitInterface splashRetrofitInterface = getRetrofit().create(SplashRetrofitInterface.class);

        splashRetrofitInterface.getUser().enqueue(new Callback<SplashResponse>() {
            @Override
            public void onResponse(Call<SplashResponse> call, Response<SplashResponse> response) {
                final SplashResponse splashResponse = response.body();
                if (splashResponse == null) {
                    mSplashActivityView.validateFailure(null);
                    return;
                }

                mSplashActivityView.validateSuccess(splashResponse);
            }

            @Override
            public void onFailure(Call<SplashResponse> call, Throwable t) {
                mSplashActivityView.validateFailure(null);
            }
        });
    }
}
