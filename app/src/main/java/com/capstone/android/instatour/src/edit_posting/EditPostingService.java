package com.capstone.android.instatour.src.edit_posting;


import android.util.Log;

import com.capstone.android.instatour.src.edit_posting.interfaces.EditPostingActivityView;
import com.capstone.android.instatour.src.edit_posting.interfaces.EditPostingRetrofitInterface;
import com.capstone.android.instatour.src.edit_posting.models.BasicResponse;
import com.capstone.android.instatour.src.edit_posting.models.TestResponse;
import com.capstone.android.instatour.src.main.interfaces.MainRetrofitInterface;
import com.capstone.android.instatour.src.main.models.MainUserResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.capstone.android.instatour.src.ApplicationClass.getRetrofit;

class EditPostingService {
    private final EditPostingActivityView mEditPostingActivityView;

    EditPostingService(final EditPostingActivityView editPostingActivityView) {
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

    void getUser() {
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);

        mainRetrofitInterface.getUser().enqueue(new Callback<MainUserResponse>() {
            @Override
            public void onResponse(Call<MainUserResponse> call, Response<MainUserResponse> response) {
                final MainUserResponse mainUserResponse = response.body();
                if (mainUserResponse == null) {
                    mEditPostingActivityView.validateUserFailure(null);
                    return;
                }

                mEditPostingActivityView.validateUserSuccess(mainUserResponse);
            }

            @Override
            public void onFailure(Call<MainUserResponse> call, Throwable t) {
                mEditPostingActivityView.validateUserFailure(null);
            }
        });
    }

    void postPoting(String location, String section, String contnet, ArrayList<String> imgList) throws JSONException {
        final EditPostingRetrofitInterface editPostingRetrofitInterface = getRetrofit().create(EditPostingRetrofitInterface.class);

        JsonObject params = new JsonObject();
        params.addProperty("location", location);
        params.addProperty("content", contnet);
        params.addProperty("section", section);


        JsonArray jsonArray = new JsonArray();
        for(int i=0;i<imgList.size();i++){
            jsonArray.add(imgList.get(i));
        }
        params.add("img_url", jsonArray);
        params.addProperty("img_url", jsonArray.toString());

        Log.i("SDVsd", params.toString());

        editPostingRetrofitInterface.postPosting(params).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                final BasicResponse basicResponse = response.body();
                Log.i("DVsavsadvads", String.valueOf(response.code()));
                Log.i("SDVSDVds", response.message());
                Log.i("SDVSDVds", String.valueOf(response.body()));
                Log.i("SDVsDVds", String.valueOf(response.errorBody()));
                if (basicResponse == null) {
                    Log.i("EDITPOSTINGTEST1", "123");
                    Log.i("EDITPOSTINGTEST2", String.valueOf(response.code()));

                    mEditPostingActivityView.validatePostingFailure(null);
                    return;
                }

                mEditPostingActivityView.validatePostingSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                System.out.println(t.getMessage());
                mEditPostingActivityView.validatePostingFailure(null);
            }
        });
    }
}
