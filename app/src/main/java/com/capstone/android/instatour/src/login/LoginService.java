package com.capstone.android.instatour.src.login;


import com.capstone.android.instatour.src.login.interfaces.LoginActivityView;
import com.capstone.android.instatour.src.login.interfaces.LoginRetrofitInterface;
import com.capstone.android.instatour.src.login.models.TestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.capstone.android.instatour.src.ApplicationClass.getRetrofit;

class LoginService {
    private final LoginActivityView mLoginActivityView;

    LoginService(final LoginActivityView loginActivityView) {
        this.mLoginActivityView = loginActivityView;
    }

    void getTest() {
        final LoginRetrofitInterface loginRetrofitInterface = getRetrofit().create(LoginRetrofitInterface.class);

        loginRetrofitInterface.getTestPost().enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                final TestResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mLoginActivityView.validateFailure(null);
                    return;
                }

                mLoginActivityView.validateSuccess(defaultResponse.getData().getData());
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                mLoginActivityView.validateFailure(null);
            }
        });
    }
}
