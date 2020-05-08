package com.capstone.android.instatour.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.InstaTourApp;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.adapters.DetailPostingImageAdapter;
import com.capstone.android.instatour.adapters.DetailPostingRelatedAdapter;
import com.capstone.android.instatour.adapters.RecentSearchAdpater;
import com.capstone.android.instatour.datas.RecentData;
import com.capstone.android.instatour.datas.TestData;
import com.capstone.android.instatour.utils.SpaceItemDecoration;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.capstone.android.instatour.InstaTourApp.catchAllThrowable;
import static com.capstone.android.instatour.InstaTourApp.httpChange;


public class DetailPostingActivity extends SuperActivity implements View.OnClickListener {

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
        initAdpater();
        activity = this;

        initAdpater();
        getTmpHttp();
    }

    public void initAdpater() {
        activity = this;
        mImageAdapter = new DetailPostingImageAdapter(this);
        mVPImage.setAdapter(mImageAdapter);
        mVPImage.setOrientation(mVPImage.ORIENTATION_HORIZONTAL);
//        mVPImage.setPageTransformer(new MarginPageTransformer(50));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRelatedAdpater = new DetailPostingRelatedAdapter(this);
        mRVRelate.setAdapter(mRelatedAdpater);
        mRVRelate.setLayoutManager(linearLayoutManager);
        SpaceItemDecoration dividerItemDecoration = new SpaceItemDecoration(50);
        mRVRelate.addItemDecoration(dividerItemDecoration);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    void initViews() {
        mVPImage = findViewById(R.id.detail_posting_main_image_vp);
        mRVRelate = findViewById(R.id.detail_posting_related_rv);
        mIvNickname = findViewById(R.id.detail_posting_user_iv);
        mIvFirst = findViewById(R.id.detail_posting_first_iv);
        mIvSecond = findViewById(R.id.detail_posting_second_iv);
    }

    public void getTmpHttp() {
        InstaTourApp.getRetrofitMethod(getApplicationContext()).getTestPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TestData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        showProgressDialog();
                    }

                    @Override
                    public void onNext(final TestData res) {
                        Log.i("YES","SUCCESS");


                        mRelatedAdpater.setListData(res.getData().getData());
                        mRelatedAdpater.notifyDataSetChanged();

                        mImageAdapter.setListData(res.getData().getData());
                        mImageAdapter.notifyDataSetChanged();

                        Glide.with(activity)
                                .load(R.drawable.insta_background)
                                .fitCenter()
                                .circleCrop()
                                .into(mIvFirst);

                        Glide.with(activity)
                                .load(R.drawable.white_background)
                                .fitCenter()
                                .circleCrop()
                                .into(mIvSecond);


                        Glide.with(activity)
                                .load(httpChange(res.getData().getData().get(0)))
                                .fitCenter()
                                .circleCrop()
                                .into(mIvNickname);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("YES","SUCCESS1");
                        Toast.makeText(getApplicationContext(), catchAllThrowable(getApplicationContext(), e), Toast.LENGTH_SHORT).show();

                        dismissProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("YES","SUCCESS2");
                        dismissProgressDialog();
                    }
                });
    }
}