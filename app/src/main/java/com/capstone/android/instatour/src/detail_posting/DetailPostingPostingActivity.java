package com.capstone.android.instatour.src.detail_posting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.detail_posting.adapters.DetailPostingImageAdapter;
import com.capstone.android.instatour.src.detail_posting.adapters.DetailPostingRelatedAdapter;
import com.capstone.android.instatour.src.detail_posting.interfaces.DetailPostingActivityView;
import com.capstone.android.instatour.utils.SpaceItemDecoration;

import java.util.ArrayList;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;

public class DetailPostingPostingActivity extends BaseActivity implements DetailPostingActivityView {

    private Activity activity;
    private TextView mTvLocation, mTvSection, mTvReviewCount, mTvContent, mTvDate, mTvNickname, mTvPostingCount, mTvProgressBarCount;
    private ImageView mIvHeart, mIvStars, mIvNickname, mIvFirst, mIvSecond;
    private ProgressBar mPBManner;
    private ViewPager2 mVPImage;
    private RecyclerView mRVRelate;
    private DetailPostingImageAdapter mImageAdapter;
    private DetailPostingRelatedAdapter mRelatedAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_posting);

        initViews();
        init();
        initAdpater();
        tryGetTest();
    }

    public void initAdpater() {
        mImageAdapter = new DetailPostingImageAdapter(this);
        mVPImage.setAdapter(mImageAdapter);
        mVPImage.setOrientation(mVPImage.ORIENTATION_HORIZONTAL);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRelatedAdpater = new DetailPostingRelatedAdapter(this);
        mRVRelate.setAdapter(mRelatedAdpater);
        mRVRelate.setLayoutManager(linearLayoutManager);
        SpaceItemDecoration dividerItemDecoration = new SpaceItemDecoration(50);
        mRVRelate.addItemDecoration(dividerItemDecoration);
    }

    public void init() {
        activity = this;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void initViews() {
        mVPImage = findViewById(R.id.detail_posting_main_image_vp);
        mRVRelate = findViewById(R.id.detail_posting_related_rv);
        mIvNickname = findViewById(R.id.detail_posting_user_iv);
        mIvFirst = findViewById(R.id.detail_posting_first_iv);
        mIvSecond = findViewById(R.id.detail_posting_second_iv);
    }

    private void tryGetTest() {
        showProgressDialog();

        final DetailPostingService mainService = new DetailPostingService(this);
        mainService.getTest();
    }

    @Override
    public void validateSuccess(ArrayList<String> list) {
        hideProgressDialog();

        mRelatedAdpater.setListData(list);
        mRelatedAdpater.notifyDataSetChanged();

        mImageAdapter.setListData(list);
        mImageAdapter.notifyDataSetChanged();

        drawerCircleImage(httpChange(list.get(0).toString()));
    }

    @Override
    public void validateFailure(@Nullable String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }

    public void drawerCircleImage(String data) {
        Glide.with(activity)
                .load(R.drawable.main_color_circle_img)
                .fitCenter()
                .circleCrop()
                .into(mIvFirst);

        Glide.with(activity)
                .load(R.drawable.white_background)
                .fitCenter()
                .circleCrop()
                .into(mIvSecond);

        Glide.with(activity)
                .load(httpChange(data))
                .fitCenter()
                .circleCrop()
                .into(mIvNickname);
    }
}
