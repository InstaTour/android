package com.capstone.android.instatour.src.main;


import com.capstone.android.instatour.src.main.interfaces.MainActivityView;
import com.capstone.android.instatour.src.main.interfaces.MainRetrofitInterface;
import com.capstone.android.instatour.src.main.models.TestResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.capstone.android.instatour.src.ApplicationClass.getRetrofit;

class MainService {
    private final MainActivityView mMainActivityView;

    MainService(final MainActivityView mainActivityView) {
        this.mMainActivityView = mainActivityView;
    }

    void getTest() {
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);

        mainRetrofitInterface.getTestPost().enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                final TestResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mMainActivityView.validateFailure(null);
                    return;
                }

                mMainActivityView.validateSuccess(defaultResponse.getData().getData());
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                mMainActivityView.validateFailure(null);
            }
        });
    }
}
