package com.capstone.android.instatour.src.CheckPost;


import android.util.Log;

import com.capstone.android.instatour.src.CheckPost.interfaces.CheckPostActivityView;
import com.capstone.android.instatour.src.CheckPost.interfaces.CheckPostRetrofitInterface;
import com.capstone.android.instatour.src.CheckPost.models.CheckResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.capstone.android.instatour.src.ApplicationClass.getRetrofit;

class CheckPostService {
    private final CheckPostActivityView mCheckPostActivityView;

    CheckPostService(final CheckPostActivityView checkPostActivityView) {
        this.mCheckPostActivityView = checkPostActivityView;
    }


    void getSearch(String id) {
        final CheckPostRetrofitInterface checkPostRetrofitInterface = getRetrofit().create(CheckPostRetrofitInterface.class);

        checkPostRetrofitInterface.getPost(id).enqueue(new Callback<CheckResponse>() {
            @Override
            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                final CheckResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    Log.i("SDFSDVSD", "SDVSDVSVD");
                    mCheckPostActivityView.validateFailure(null);
                    return;
                }

                System.out.print(response.body());
                System.out.print(response.code());

                mCheckPostActivityView.validateSuccess(defaultResponse);
            }

            @Override
            public void onFailure(Call<CheckResponse> call, Throwable t) {
                System.out.println(t.getMessage());
                mCheckPostActivityView.validateFailure(null);
            }
        });
    }
}
