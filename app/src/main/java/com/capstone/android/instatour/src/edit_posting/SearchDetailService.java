package com.capstone.android.instatour.src.edit_posting;


import com.capstone.android.instatour.src.edit_posting.interfaces.EditPostingActivityView;
import com.capstone.android.instatour.src.edit_posting.interfaces.EditPostingRetrofitInterface;
import com.capstone.android.instatour.src.edit_posting.models.TestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.capstone.android.instatour.src.ApplicationClass.getRetrofit;

class SearchDetailService {
    private final EditPostingActivityView mEditPostingActivityView;

    SearchDetailService(final EditPostingActivityView editPostingActivityView) {
        this.mEditPostingActivityView = editPostingActivityView;
    }

    void getTest() {
        final EditPostingRetrofitInterface editPostingRetrofitInterface = getRetrofit().create(EditPostingRetrofitInterface.class);

        editPostingRetrofitInterface.getTestPost().enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                final TestResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mEditPostingActivityView.validateFailure(null);
                    return;
                }

                mEditPostingActivityView.validateSuccess(defaultResponse.getData().getData());
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                mEditPostingActivityView.validateFailure(null);
            }
        });
    }
}
