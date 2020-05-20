package com.capstone.android.instatour.src.signup;


import com.capstone.android.instatour.src.signup.interfaces.SignupActivityView;
import com.capstone.android.instatour.src.signup.interfaces.SignupRetrofitInterface;
import com.capstone.android.instatour.src.signup.models.TestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.capstone.android.instatour.src.ApplicationClass.getRetrofit;

class SignupService {
    private final SignupActivityView mSignupActivityView;

    SignupService(final SignupActivityView signupActivityView) {
        this.mSignupActivityView = signupActivityView;
    }

    void getTest() {
        final SignupRetrofitInterface signupRetrofitInterface = getRetrofit().create(SignupRetrofitInterface.class);

        signupRetrofitInterface.getTestPost().enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                final TestResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mSignupActivityView.validateFailure(null);
                    return;
                }

                mSignupActivityView.validateSuccess(defaultResponse.getData().getData());
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                mSignupActivityView.validateFailure(null);
            }
        });
    }
}
