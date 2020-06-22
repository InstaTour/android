package com.capstone.android.instatour.src.CheckPost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.CheckPost.adapters.PostingAdapter;
import com.capstone.android.instatour.src.CheckPost.interfaces.CheckPostActivityView;
import com.capstone.android.instatour.src.CheckPost.models.CheckResponse;
import com.capstone.android.instatour.src.detail_posting.DetailPostingPostingActivity;
import com.capstone.android.instatour.src.edit_posting.EditPostingActivity;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;

public class CheckPostlActivity extends BaseActivity implements CheckPostActivityView {

    private TextView mTvTitle;
    private RecyclerView mRVPosting;
    private PostingAdapter mPostingAdpater;
    private Activity activity;
    private  int id =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_post);

        initViews();
        init();
        initAdpater();
        getPost();
    }

    public void init() {
        activity = this;
        mPostingAdpater = new PostingAdapter(activity);

        id = getIntent().getIntExtra("id", 0);

        if(id == 0) {
            mTvTitle.setText("내가 쓴 글");
        }
        else {
            mTvTitle.setText("찜 목록");
        }
    }

    @Override
    public void initViews() {
        mTvTitle = findViewById(R.id.check_post_location_tv);
        mRVPosting = findViewById(R.id.check_post_grid_rv);
    }

    public void initAdpater() {
        mRVPosting.setLayoutManager(new GridLayoutManager(this, 3));
        mRVPosting.setAdapter(mPostingAdpater);
    }

    private void getPost() {
        showProgressDialog();

        final CheckPostService mainService = new CheckPostService(this);

        String idString = "";
        if(id ==0) {
            idString = "posting";
        }
        else {
            idString = "heart";
        }
        mainService.getSearch(idString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_post_back_iv:
                overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
                finish();
                break;
        }
    }

    @Override
    public void validateSuccess(CheckResponse response) {
        hideProgressDialog();

        mPostingAdpater.setListData(response.getData().getPosts());
        mPostingAdpater.notifyDataSetChanged();
    }

    @Override
    public void validateFailure(@Nullable String message) {
        hideProgressDialog();
        System.out.println(message);
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }
}
